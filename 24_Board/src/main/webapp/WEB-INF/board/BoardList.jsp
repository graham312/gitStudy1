<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
BoardList.jsp<br>
<title>게시판</title>
<script type="text/javascript">

	function insert(){
		location.href="insert.bd";
	}
</script>
<style>
	table{
		border-collapse: collapse;
		text-align: center;
	}
	th{
		background-color: lightblue;
	}
</style>
<center>
<!-- 검색 기능 -->
<h1 align="center">게시판 목록보기</h1>
<form action="list.bd" method="post">
	<select name="whatColumn">
		<option value="all">전체 검색</option>
		<option value="subject">제목</option>
		<option value="writer">작성자</option>
	</select>
		<input type="text" name="keyword">
		<input type="submit" value="검색">
</form>
(전체 글 : ${pageInfo.totalCount}) <br>
total : ${pageInfo.totalCount } <br>
beginrow : ${pageInfo.beginRow} <br>
<br><br>
<c:set var="row" value="${pageInfo.totalCount-pageInfo.beginRow+1}" />
<table border="1" height=300px width="700px">
	<tr>
		<th colspan="7" align="right">
			<input type="button" value="추가하기" onClick="insert()">
		</th>
	</tr>
	
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
		<th>IP</th>
	</tr>
	
	<c:forEach var="board" items="${boardLists }" varStatus="i">
	<tr>
		<td>
			${row}
			<c:set var="row" value="${row-1}" />
		</td>
		
		<td align="left">

    	   <c:if test="${board.re_level > 0}">
   			 <c:set var="wid" value="20 * ${board.re_level}"/>
   			 <img src="resources/images/level.gif" width="${wid}" height="15">
    		<img src="resources/images/re.gif">
			</c:if>
			 <c:if test="${board.readcount >= 10}">
				<img src="resources/images/hot.gif" height="15">
			 </c:if>
    	     <a href="detail.bd?num=${board.num}&pageNumber=${pageInfo.pageNumber}&whatColumn=${param.whatColumn}&keyword=${param.keyword}">${board.subject}</a>
			
			</td>
		
		<td>
			${board.writer }
		</td>
		
		
		<td>
			<fmt:formatDate value="${board.reg_date }" pattern="yyyy-MM-dd"/>
		</td>
		<td>${board.readcount }</td>
		
		<td>${board.ip }</td>
	</tr>
	
	</c:forEach>
</table>

${pageInfo.pagingHtml }

</center>