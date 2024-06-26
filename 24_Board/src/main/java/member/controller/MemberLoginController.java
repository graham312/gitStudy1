package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.model.MemberBean;
import member.model.MemberDao;

@Controller
public class MemberLoginController {

	private final String command = "/loginForm.mb";
	private final String getPage = "memberLoginForm";

	@Autowired
	MemberDao memberDao;

	//��ǰ�߰��ϱ� ��ư�� �����µ� �α��� ������ ���� �� 
	// productList.jsp=>�߰��ϱ�=>ProductInsertController=>redirect:/loginForm.mb
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String loginForm() {
		System.out.println(this.getClass()+" GET");
		return getPage;
	}

	//member\memberLoginForm.jsp���� �α��� Ŭ��(id:kim,password:1234)
	@RequestMapping(value = command, method = RequestMethod.POST)
	public ModelAndView loginForm(@ModelAttribute("member") MemberBean member, HttpSession session, HttpServletResponse response, Model model,
	                              @RequestParam(value = "pageNumber", required = false) String pageNumber) {

	    System.out.println(this.getClass() + " POST");
	    System.out.println("pageNumber:" + pageNumber);
	    
	    ModelAndView mav = new ModelAndView();
	    
	    // ID�� �������� ȸ�� ���� ��ȸ
	    MemberBean mb = memberDao.getMemberById(member.getId());  
	    System.out.println("mb:" + mb);

	    try {
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        if(mb == null){ // �ش� ���̵� �������� �ʴ´�.
	            out.println("<script>");
	            out.println("alert('�ش� ���̵�� �������� �ʽ��ϴ�.');");
	            out.println("</script>");
	            out.flush();
	            return new ModelAndView("memberLoginForm"); // �α��� ������ �ٽ� �̵�
	            
	        } else { // �ش� ���̵� �����Ѵ�.
	            if(mb.getPassword().equals(member.getPassword())) { // ��� ��ġ
	                
	                session.setAttribute("loginInfo", mb); // loginInfo: �α����� ����� ����
	                System.out.println("��� ��ġ");
	                System.out.println("destination:" + (String)session.getAttribute("destination"));
	                
	                return new ModelAndView((String)session.getAttribute("destination"));
	                
	            } else { // ��� ����ġ
	                System.out.println("��� ����ġ");
	                out.println("<script>");
	                out.println("alert('��� ����ġ�Դϴ�.');");
	                out.println("</script>");
	                out.flush();
	                return new ModelAndView("memberLoginForm"); // �α��� ������ �ٽ� �̵�
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return mav;
	}

}








