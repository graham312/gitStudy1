<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
BoardInsertForm.jsp<br>
<style>
	body{
		text-align: center;
	}
	table{
		margin: 5px auto;
	}
	.err{
		color: red;
	}
	th{
		background-color: lightblue;
	}
</style>  
<body>
	<h3>글쓰기(원글)</h3>
	<form:form commandName="board" action="insert.bd" method="post">
		<table border="1" height=400px width="700px">
			<tr>
				<th align="right" colspan="2">
					<a href="list.bd">글목록</a>
				</th>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="writer" maxlength="10" value="${board.writer }">
					<form:errors path="writer" cssClass="err"/>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" value="${board.subject }">
					<form:errors path="subject" cssClass="err"/>
				</td>
			</tr>
			<tr>
				<th>email</th>
				<td><input type="text" name="email" value="${board.email }">
					<form:errors path="email" cssClass="err"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" rows="15" cols="90">${board.content }</textarea>
					<form:errors path="content" cssClass="err"/>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="passwd" >
					<form:errors path="passwd" cssClass="err"/>
				</td>
			</tr>
			<tr>
				<th colspan="2" align="center">
					<input type="submit" value="글쓰기">
					<input type="reset" value="다시작성">
					<input type="button" value="목록보기" onClick="location='list.bd'">
				</th>
			</tr>
		</table>
	</form:form>
</body>