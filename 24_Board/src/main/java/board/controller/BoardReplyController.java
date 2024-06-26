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
        
        System.out.println("�󼼺��⿡�� �Ѿ���� re_step: " + re_step);
        System.out.println("�󼼺��⿡�� �Ѿ���� re_level: " + re_level);
        System.out.println("�󼼺��⿡�� �Ѿ���� whatColumn: " + whatColumn);
        System.out.println("�󼼺��⿡�� �Ѿ���� pageNumber: " + pageNumber);
        
        if (session.getAttribute("loginInfo") == null) { // login ����
            session.setAttribute("destination", "redirect:/insert.bd"); // �α��� �������� ������ ���� �۾��� �ߴܽ�Ű�� �ȵǱ� ������ ������ �س��� �Ѵ�.
            return "redirect:/loginForm.mb"; // �α��� ���� ��, �α��� ������ �̵�
        } else {
            model.addAttribute("ref", ref);
            model.addAttribute("re_step", re_step);
            model.addAttribute("re_level", re_level);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("whatColumn", whatColumn);
            model.addAttribute("keyword", keyword);
            return getPage; // login ����
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
            System.out.println("���� ����");
        } else {
            mav.addObject("ref", ref);
            mav.addObject("re_step", re_step);
            mav.addObject("re_level", re_level);
            mav.addObject("pageNumber", pageNumber);
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("keyword", keyword);
            mav.setViewName(getPage);
            System.out.println("���� ����");
        }
        return mav; 
    }
}
