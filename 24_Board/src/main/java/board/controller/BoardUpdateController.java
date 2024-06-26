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
            // �α����� �Ǿ� ���� ���� ���
            session.setAttribute("destination", "redirect:/update.bd?num=" + num + 
                    "&pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword);
            return "redirect:/loginForm.mb"; // �α��� ������ �����̷�Ʈ
        } else {
            // �α����� �Ǿ� �ִ� ���
            BoardBean board = boardDao.getBoardByNum(num);
            model.addAttribute("board", board);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            return getPage; // ���� �� �������� �̵�
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
            mav.setViewName(getPage); // ��ȿ�� �˻� ���� �� �ٽ� ���� �� ��������
            return mav;
        }

        // ���� �ð��� Ŭ���̾�Ʈ IP ����
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        board.setIp(request.getRemoteAddr());

        // DB�� ����� ��й�ȣ�� ��
        BoardBean existingBoard = boardDao.getBoardByNum(board.getNum());
        if (!existingBoard.getPasswd().equals(board.getPasswd())) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�.'); history.go(-1);</script>");
            out.flush();
            return null;
        }

        // �Խù� ������Ʈ
        int updateCount = boardDao.updateBoard(board);

        if (updateCount > 0) {
            mav.setViewName("redirect:/list.bd?pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword);
        } else {
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage); // ���� �� �ٽ� ���� �� ��������
        }

        return mav;
    }
}
