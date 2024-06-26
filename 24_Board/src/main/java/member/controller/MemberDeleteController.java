package member.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import member.model.MemberDao;

@Controller
public class MemberDeleteController {
	private final String command = "/delete.mb";
	private final String gotoPage = "redirect:/memberList.mb";
	
	@Autowired
	MemberDao mdao;
	
	@RequestMapping(command)
	public String delete(@RequestParam(value="mnum") int mnum,
			@RequestParam("pageNumber") int pageNumber,
			@RequestParam("whatColumn") String whatColumn,
			@RequestParam("keyword") String keyword,
			RedirectAttributes redirectAttributes) {

		System.out.println("삭제버튼 클릭하면 넘어오는 번호 : " + mnum);
		mdao.deleteMember(mnum); 
		
		try {
			String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
			String encodedWhatColumn = URLEncoder.encode(whatColumn, "UTF-8");

			String redirectUrl = gotoPage+"?pageNumber=" + pageNumber + "&whatColumn=" + encodedWhatColumn + "&keyword=" + encodedKeyword;
			return redirectUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("url인코딩 오류");
			return gotoPage;
		}
	}

}