package board.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardInsertController {
    private final String command = "insert.bd";
    private final String getPage = "BoardInsertForm";
    private final String gotoPage = "redirect:/list.bd";
    
    @Autowired
    private BoardDao bdao;
    
    @RequestMapping(value=command, method = RequestMethod.GET)
    public String insertForm(HttpSession session) {
        // 로그인 세션 정보 확인
        System.out.println("loginInfo:" + session.getAttribute("loginInfo"));
        if(session.getAttribute("loginInfo") == null) {
            // 로그인이 되어 있지 않은 경우
            session.setAttribute("destination", "redirect:/insert.bd");
            return "redirect:/loginForm.mb"; // 로그인 폼으로 리다이렉트
        } else {
            // 로그인이 되어 있는 경우
            return getPage; // 상품 등록 폼 페이지로 이동
        }
    }
    
    @RequestMapping(value=command, method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("board") @Valid BoardBean board, BindingResult result, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        if(result.hasErrors()) {
            mav.setViewName(getPage); // 유효성 검사 실패 시 다시 삽입 폼 페이지로
            return mav;
        }
        
        // 현재 시간을 reg_date에 설정
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        
        // 클라이언트의 IP 주소를 ip 필드에 설정
        board.setIp(request.getRemoteAddr());
        
        System.out.println(board.getWriter());
        System.out.println(board.getSubject());
        System.out.println(board.getEmail());
        System.out.println(board.getContent());
        System.out.println(board.getPasswd());
        System.out.println(board.getReg_date());
        System.out.println(board.getIp());
        
        // 게시물 삽입
        int cnt = bdao.insertArticle(board);
        if (cnt != -1) {
            mav.setViewName(gotoPage); // 성공 시 list
        } else {
            mav.addObject("message", "게시물 등록 실패");
            mav.setViewName(getPage); // 실패 시 다시 삽입 폼 페이지로
        }
        return mav;
    }
}
