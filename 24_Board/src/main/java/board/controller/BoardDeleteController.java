package board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardDao;

@Controller
public class BoardDeleteController {
	
	@Autowired
	BoardDao boardDao;
	
	private final String command = "/delete.bd";
	private final String getPage = "BoardDeleteForm";
	private final String gotoPage = "redirect:/list.bd";
	
	@RequestMapping(value=command, method = RequestMethod.GET)
	public ModelAndView deleteForm(@RequestParam(value="num") int num,
	                        @RequestParam("pageNumber") int pageNumber,
	                        @RequestParam("whatColumn") String whatColumn,
	                        @RequestParam("keyword") String keyword,
	                        HttpSession session) {
	    System.out.println("GET");
	    System.out.println("num: " + num + ", pageNumber: " + pageNumber + 
	            ", whatColumn: " + whatColumn + ", keyword: " + keyword);
	    System.out.println("loginInfo:" + session.getAttribute("loginInfo"));
	    
	    ModelAndView mav = new ModelAndView();
	    
	    if (session.getAttribute("loginInfo") == null) {
	        // �α����� �Ǿ� ���� ���� ���
	        session.setAttribute("destination", "redirect:/delete.bd?num=" + num + "&pageNumber=" + pageNumber +
	                "&whatColumn=" + whatColumn + "&keyword=" + keyword);
	        mav.setViewName("redirect:/loginForm.mb"); // �α��� ������ �����̷�Ʈ
	    } else {
	        // �α����� �Ǿ� �ִ� ���
	        mav.addObject("num", num);
	        mav.addObject("pageNumber", pageNumber);
	        mav.addObject("whatColumn", whatColumn);
	        mav.addObject("keyword", keyword);
	        mav.setViewName(getPage); // ���� �� �������� �̵�
	    }
	    return mav;
	}

	@RequestMapping(value=command, method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value="num") int num,
						@RequestParam("pageNumber") int pageNumber,
						@RequestParam("whatColumn") String whatColumn,
						@RequestParam("keyword") String keyword,
						HttpServletRequest request,
						HttpServletResponse response) {
		System.out.println("POST");
		System.out.println("num: " + num + ", pageNumber: " + pageNumber + 
                ", whatColumn: " + whatColumn + ", keyword: " + keyword);
		
		ModelAndView mav = new ModelAndView();
		
		String passwd = request.getParameter("passwd");
		int cnt = boardDao.deleterArticle(num, passwd);

		if(cnt == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("<script>alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�.'); history.go(-1);</script>");
				out.flush();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// �˻� ���ǰ� ������ ��ȣ�� �����Ͽ� ���𷺼�
		mav.setViewName("redirect:/list.bd?pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword);
		return mav;
	}
}
