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
        // �α��� ���� ���� Ȯ��
        System.out.println("loginInfo:" + session.getAttribute("loginInfo"));
        if(session.getAttribute("loginInfo") == null) {
            // �α����� �Ǿ� ���� ���� ���
            session.setAttribute("destination", "redirect:/insert.bd");
            return "redirect:/loginForm.mb"; // �α��� ������ �����̷�Ʈ
        } else {
            // �α����� �Ǿ� �ִ� ���
            return getPage; // ��ǰ ��� �� �������� �̵�
        }
    }
    
    @RequestMapping(value=command, method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("board") @Valid BoardBean board, BindingResult result, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        if(result.hasErrors()) {
            mav.setViewName(getPage); // ��ȿ�� �˻� ���� �� �ٽ� ���� �� ��������
            return mav;
        }
        
        // ���� �ð��� reg_date�� ����
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        
        // Ŭ���̾�Ʈ�� IP �ּҸ� ip �ʵ忡 ����
        board.setIp(request.getRemoteAddr());
        
        System.out.println(board.getWriter());
        System.out.println(board.getSubject());
        System.out.println(board.getEmail());
        System.out.println(board.getContent());
        System.out.println(board.getPasswd());
        System.out.println(board.getReg_date());
        System.out.println(board.getIp());
        
        // �Խù� ����
        int cnt = bdao.insertArticle(board);
        if (cnt != -1) {
            mav.setViewName(gotoPage); // ���� �� list
        } else {
            mav.addObject("message", "�Խù� ��� ����");
            mav.setViewName(getPage); // ���� �� �ٽ� ���� �� ��������
        }
        return mav;
    }
}
