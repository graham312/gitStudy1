package board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardUpdateController {
    private final String command = "update.bd";
    private final String getPage = "BoardUpdateForm";
    private final String gotoPage = "redirect:/list.bd";

    @Autowired
    private BoardDao boardDao;

    @RequestMapping(value = command, method = RequestMethod.GET)
    public String updateForm(@RequestParam("num") int num,
                             @RequestParam("pageNumber") int pageNumber,
                             @RequestParam("whatColumn") String whatColumn,
                             @RequestParam("keyword") String keyword,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginInfo") == null) {
            // 로그인이 되어 있지 않은 경우
            session.setAttribute("destination", "redirect:/update.bd?num=" + num + 
                    "&pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword);
            return "redirect:/loginForm.mb"; // 로그인 폼으로 리다이렉트
        } else {
            // 로그인이 되어 있는 경우
            BoardBean board = boardDao.getBoardByNum(num);
            model.addAttribute("board", board);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            return getPage; // 수정 폼 페이지로 이동
        }
    }

    @RequestMapping(value = command, method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("board") @Valid BoardBean board, BindingResult result, 
                               @RequestParam("pageNumber") int pageNumber,
                               @RequestParam("whatColumn") String whatColumn,
                               @RequestParam("keyword") String keyword,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        System.out.println("check1");
        if (result.hasErrors()) {
            System.out.println("check2");
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage); // 유효성 검사 실패 시 다시 수정 폼 페이지로
            return mav;
        }

        // 현재 시간과 클라이언트 IP 설정
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        board.setIp(request.getRemoteAddr());

        // DB에 저장된 비밀번호와 비교
        BoardBean existingBoard = boardDao.getBoardByNum(board.getNum());
        if (!existingBoard.getPasswd().equals(board.getPasswd())) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('비밀번호가 일치하지 않습니다.'); history.go(-1);</script>");
            out.flush();
            return null;
        }

        // 게시물 업데이트
        int updateCount = boardDao.updateBoard(board);

        if (updateCount > 0) {
            mav.setViewName("redirect:/list.bd?pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword);
        } else {
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage); // 실패 시 다시 수정 폼 페이지로
        }

        return mav;
    }
}
