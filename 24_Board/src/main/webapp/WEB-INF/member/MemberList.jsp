<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
MemberList.jsp<br>
<title>회원</title>
<style>
	table{
		text-align: center;
	}
	
	th{
		background-color: lightblue;
	}
	
		
</style>

<script type="text/javascript">

	function insert(){
		location.href="insert.mb";
	}
	
	function goUpdate(mnum, pageNumber, whatColumn, keyword){
	      location.href = "update.mb?mnum=" + mnum + "&pageNumber=" + pageNumber + "&whatColumn=" + whatColumn + "&keyword=" + keyword  ;
	}
	
</script>


<center>
<!-- 검색 기능 -->
<h2>회원 목록보기</h2>
<form action="memberList.mb" method="post">
	<select name="whatColumn">
		<option value="all">전체 검색</option>
		<option value="id">아이디</option>
		<option value="gender">성별</option>
		<option value="singer">가수</option>
	</select>
		<input type="text" name="keyword">
		<input type="submit" value="검색">
</form>
(전체 글 : ${pageInfo.totalCount})
<br><br>
<table border="1">
	<tr>
		<th colspan="10" align="right">
			<input type="button" value="추가하기" onClick="insert()">
		</th>
	</tr>
	
	<tr>
		<th>번호</th>
		<th>아이디</th>
		<th>비밀번호</th>
		<th>이름</th>
		<th>성별</th>
		<th>가수</th>
		<th>노래</th>
		<th>삭제</th>
		<th>수정</th>
	</tr>
	
	<c:forEach var="member" items="${memberLists }">
	<tr>
		<td>${member.mnum }</td>
		<td>${member.id }</td>
		<td>${member.password }</td>
		<td>${member.name }</td>
		<td>${member.gender }</td>
		<td>${member.singer }</td>
		<td>${member.song }</td>
		<td>
			<a href="delete.mb?mnum=${member.mnum}&pageNumber=${pageInfo.pageNumber}&whatColumn=${pageInfo.whatColumn}&keyword=${pageInfo.keyword}">삭제 </a>
		</td>
		<td>
			<input type="button" value="수정" onClick="goUpdate('${member.mnum}','${pageInfo.pageNumber}','${pageInfo.whatColumn}','${pageInfo.keyword}')">
		</td>
	</tr>
	
	</c:forEach>
</table>

${pageInfo.pagingHtml }

</center>