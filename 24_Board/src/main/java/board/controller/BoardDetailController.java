package board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardDetailController {
	private final String command = "detail.bd";
	private final String getPage = "BoardDetailView";
	
	@Autowired
	private BoardDao bdao;
	
	@RequestMapping(value=command)
	public String detail(@RequestParam("num") int num,
			@RequestParam(value="whatColumn", required = false) String whatColumn,
			@RequestParam("pageNumber") String pageNumber,
			@RequestParam(value="keyword", required = false) String keyword,
			Model model) {
		
		// 조회수 증가 처리
        bdao.increaseReadCount(num);

        // 게시글 정보 가져오기
        BoardBean bb = bdao.getBoardByNum(num);
		
		model.addAttribute("board", bb);
		model.addAttribute("whatColumn", whatColumn);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageNumber", pageNumber);
		
		return getPage;
	}
}
