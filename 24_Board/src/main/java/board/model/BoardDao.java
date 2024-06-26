package board.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component
public class BoardDao {
	
	private String namespace = "board.model.Board";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public BoardDao() {
		System.out.println("BoardDao ������");
	}
	
	public List<BoardBean> getBoardList(Map<String, String> map, Paging pageInfo){
		List<BoardBean> lists = new ArrayList<BoardBean>();
		
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit());
		lists = sqlSessionTemplate.selectList(namespace + ".getBoardList", map, rowBounds);
		return lists;
	}//getBoardList
	
	public int getTotalCount(Map<String, String> map) {
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace + ".getTotalCount", map);
		return cnt;
	}//getTotalCount
	
	// �� �߰�
	public int insertArticle(BoardBean board) {
		int cnt = -1;	
		cnt = sqlSessionTemplate.insert(namespace + ".insertArticle", board); 	
		return cnt;
	}
	
	// �Խñ� �� ���� ��ȸ
	public BoardBean getBoardByNum(int num) {
		BoardBean board = null;
		board = sqlSessionTemplate.selectOne(namespace + ".getBoardByNum", num);
		return board;
	}
	
	// ����
	public int deleteBoard(int num) {
		int cnt = -1;
	    cnt = sqlSessionTemplate.delete(namespace + ".deleteBoard", num);
	    return cnt;
	}


	 // ��ȸ�� ���� �޼���
    public int increaseReadCount(int num) {
    	int cnt = -1;
    	cnt = sqlSessionTemplate.update(namespace + ".increaseReadCount", num);
        return cnt;
    }
    
    // ����
    public int updateBoard(BoardBean board) {
    	int cnt = -1;
    	cnt = sqlSessionTemplate.update(namespace + ".updateBoard", board);
    	return cnt;
    }
    
    public String getPassword(int num) {
        return sqlSessionTemplate.selectOne(namespace + ".getPassword", num);
    }
    
    // ��й�ȣ ��ġ�ϸ� ����
    public int deleterArticle(int num, String passwd) {
		int cnt = -1;
		String pw = sqlSessionTemplate.selectOne(namespace + ".getPassword", num);
        if (pw != null && pw.equals(passwd)) {
        	cnt = sqlSessionTemplate.delete(namespace + ".deleterArticle", num);
        } else {
            cnt = 0;
        }
		return cnt;
	}
    
    // ��� �ۼ� �� re_step ����
    public int replyArticle(BoardBean board) {
		int cnt = -1;
		cnt = sqlSessionTemplate.update(namespace + ".updateArticle" , board);
		return sqlSessionTemplate.insert(namespace + ".replyArticle" , board);
	}
}
