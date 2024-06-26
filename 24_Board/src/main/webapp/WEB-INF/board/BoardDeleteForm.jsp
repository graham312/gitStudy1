<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file = "../common/common.jsp" %>
<h2></h2>
<form action="delete.bd" method="POST">
	<table border="1" align="center">
		<tr>
			<td>비밀번호를 입력해 주세요.</td>
		</tr>
		<tr>
			<td>
				비밀번호 : <input type="password" name="passwd">
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" name="num" value="${param.num }">
			    <input type="hidden" name="pageNumber" value="${param.pageNumber}">
			    <input type="hidden" name="whatColumn" value="${param.whatColumn}">
			    <input type="hidden" name="keyword" value="${param.keyword}">
				<input type="submit" value="글삭제">
				<input type="button" value="글목록" onClick="location.href='list.bd?whatColumn=${param.whatColumn}&keyword=${param.keyword}&pageNumber=${param.pageNumber}'">
			</td>
		</tr>
	</table>
</form>    
    
    
    