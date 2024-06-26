package member.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component("MemberDao")
public class MemberDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	private String namespace = "member.model.Member";
	
	public MemberDao() {
		System.out.println("MemberDao 생성자");
	}
	
	public List<MemberBean> getMemberList(Map<String, String> map, Paging pageInfo){
		List<MemberBean> lists = new ArrayList<MemberBean>();
		
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit());
		lists = sqlSessionTemplate.selectList(namespace + ".getMemberList", map, rowBounds);
		return lists;
	}
	
	public int getTotalCount(Map<String, String> map) {
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace + ".getTotalCount", map);
		return cnt;
	}
	
	public int insertMember(MemberBean member) {
		int cnt = -1;
		try {
			cnt = sqlSessionTemplate.insert(namespace + ".insertMember", member);
		}catch(DuplicateKeyException e) {
			System.out.println("아이디 중복");
			cnt = -3;
		}
		return cnt;
	}// insertMember
	
	public int deleteMember(int mnum) {
		int cnt = -1;
		cnt = sqlSessionTemplate.delete(namespace + ".deleteMember", mnum);
		return cnt;
	}//deleteMember
	
	public MemberBean getMemberById(String id) {
	    MemberBean member = null;
	    member = sqlSessionTemplate.selectOne(namespace + ".getMemberById", id);
	    return member;
	}//getMemberById
	
	public int updateMember(MemberBean member) {
		int cnt = -1;
		cnt = sqlSessionTemplate.update(namespace + ".updateMember", member);
		return cnt;

	}//updateMember

	public MemberBean getMemberByNum(int mnum) {
		MemberBean member = null;
		member = sqlSessionTemplate.selectOne(namespace + ".getMemberByNum", mnum);
		return member;
	}//getMemberByNum

}
