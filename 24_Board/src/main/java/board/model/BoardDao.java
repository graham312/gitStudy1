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
		System.out.println("BoardDao 생성자");
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
	
	// 글 추가
	public int insertArticle(BoardBean board) {
		int cnt = -1;	
		cnt = sqlSessionTemplate.insert(namespace + ".insertArticle", board); 	
		return cnt;
	}
	
	// 게시글 상세 정보 조회
	public BoardBean getBoardByNum(int num) {
		BoardBean board = null;
		board = sqlSessionTemplate.selectOne(namespace + ".getBoardByNum", num);
		return board;
	}
	
	// 삭제
	public int deleteBoard(int num) {
		int cnt = -1;
	    cnt = sqlSessionTemplate.delete(namespace + ".deleteBoard", num);
	    return cnt;
	}


	 // 조회수 증가 메서드
    public int increaseReadCount(int num) {
    	int cnt = -1;
    	cnt = sqlSessionTemplate.update(namespace + ".increaseReadCount", num);
        return cnt;
    }
    
    // 수정
    public int updateBoard(BoardBean board) {
    	int cnt = -1;
    	cnt = sqlSessionTemplate.update(namespace + ".updateBoard", board);
    	return cnt;
    }
    
    public String getPassword(int num) {
        return sqlSessionTemplate.selectOne(namespace + ".getPassword", num);
    }
    
    // 비밀번호 일치하면 삭제
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
    
    // 답글 작성 시 re_step 증가
    public int replyArticle(BoardBean board) {
		int cnt = -1;
		cnt = sqlSessionTemplate.update(namespace + ".updateArticle" , board);
		return sqlSessionTemplate.insert(namespace + ".replyArticle" , board);
	}
}
