package board.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardReplyController {

    private final String command = "/reply.bd";
    private final String getPage = "BoardReplyForm";
    private final String gotoPage = "redirect:/list.bd";
    
    @Autowired
    BoardDao bdao;
    
    @RequestMapping(value=command, method=RequestMethod.GET)
    public String replyBoard(@RequestParam(value="ref", required = true) int ref,
                             @RequestParam(value="re_step", required = true) int re_step,
                             @RequestParam(value="re_level", required = true) int re_level,
                             @RequestParam(value="pageNumber", required = true) String pageNumber,
                             @RequestParam(value="whatColumn", required = false) String whatColumn,
                             @RequestParam(value="keyword", required = false) String keyword,
                             HttpSession session, Model model) {
        
        System.out.println("상세보기에서 넘어오는 re_step: " + re_step);
        System.out.println("상세보기에서 넘어오는 re_level: " + re_level);
        System.out.println("상세보기에서 넘어오는 whatColumn: " + whatColumn);
        System.out.println("상세보기에서 넘어오는 pageNumber: " + pageNumber);
        
        if (session.getAttribute("loginInfo") == null) { // login 실패
            session.setAttribute("destination", "redirect:/insert.bd"); // 로그인 페이지로 가더라도 원래 작업을 중단시키면 안되기 때문에 저장을 해놔야 한다.
            return "redirect:/loginForm.mb"; // 로그인 실패 시, 로그인 폼으로 이동
        } else {
            model.addAttribute("ref", ref);
            model.addAttribute("re_step", re_step);
            model.addAttribute("re_level", re_level);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            return getPage; // login 성공
        }
    }
    
    @RequestMapping(value = command, method = RequestMethod.POST)
    public ModelAndView doAction(@ModelAttribute("board") @Valid BoardBean board, BindingResult result,
                                 @RequestParam(value="ref", required = true) int ref,
                                 @RequestParam(value="re_step", required = true) int re_step,
                                 @RequestParam(value="re_level", required = true) int re_level,
                                 @RequestParam(value="pageNumber", required = false) String pageNumber,
                                 @RequestParam(value="whatColumn", required = false) String whatColumn,
                                 @RequestParam(value="keyword", required = false) String keyword,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
    
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("ref", ref);
            mav.addObject("re_step", re_step);
            mav.addObject("re_level", re_level);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage); 
            return mav;
        }
            
        int cnt = -1;
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        board.setIp(request.getRemoteAddr());
        
        cnt = bdao.replyArticle(board);
        
        if (cnt != -1) {    
            redirectAttributes.addAttribute("ref", ref);
            redirectAttributes.addAttribute("re_step", re_step);
            redirectAttributes.addAttribute("re_level", re_level);
            redirectAttributes.addAttribute("whatColumn", whatColumn);
            redirectAttributes.addAttribute("pageNumber", pageNumber);
            redirectAttributes.addAttribute("keyword", keyword);
            mav.setViewName(gotoPage); 
            System.out.println("삽입 성공");
        } else {
            mav.addObject("ref", ref);
            mav.addObject("re_step", re_step);
            mav.addObject("re_level", re_level);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage);
            System.out.println("삽입 실패");
        }
        return mav; 
    }
}
