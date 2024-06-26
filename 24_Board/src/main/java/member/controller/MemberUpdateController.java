package member.controller;

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

import member.model.MemberBean;
import member.model.MemberDao;

@Controller
public class MemberUpdateController {
    
    private final String command = "update.mb";
    private final String getPage = "MemberUpdateForm";
    private final String gotoPage = "redirect:/memberList.mb";
    
    @Autowired
    MemberDao memberDao;
    
    // GET: memberList.jsp에서 수정 버튼 클릭시 => updateForm으로 이동
    @RequestMapping(value=command, method=RequestMethod.GET)
    public String update(@RequestParam("mnum") int mnum,
                         @RequestParam("whatColumn") String whatColumn,
                         @RequestParam("keyword") String keyword,
                         @RequestParam("pageNumber") int pageNumber,
                         Model model) {
        
        MemberBean member = memberDao.getMemberByNum(mnum);
        model.addAttribute("member", member);
        model.addAttribute("whatColumn", whatColumn);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageNumber", pageNumber);
        
        return getPage;
    }
    
    // POST: 수정 폼 제출 시
    @RequestMapping(value = command, method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("member")
    						@Valid MemberBean member, 
                               BindingResult result,
                               @RequestParam("whatColumn") String whatColumn,
                               @RequestParam("keyword") String keyword,
                               @RequestParam("pageNumber") int pageNumber) {
    
        ModelAndView mav = new ModelAndView();
        
        if(result.hasErrors()) {
            mav.setViewName(getPage);
            mav.addObject("whatColumn", whatColumn);
            mav.addObject("keyword", keyword);
            mav.addObject("pageNumber", pageNumber);
            return mav;
        }
        
        memberDao.updateMember(member);
        mav.addObject("whatColumn", whatColumn);
        mav.addObject("keyword", keyword);
        mav.addObject("pageNumber", pageNumber);

        mav.setViewName(gotoPage);
        
        return mav;
    }
}
