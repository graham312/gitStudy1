<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
BoardReplyForm.jsp<br>
<style>
    .err {
        color: red;
    }
    
    th{
    	background-color: lightblue;
    }
</style>
<h2>답글 화면 ${ref} ${re_step} ${re_level}</h2>
<center><h3>답글쓰기</h3></center>

<form:form commandName="board" method="post" name="myform" action="reply.bd">
    <input type="hidden" name="ref" value="${ref}">
    <input type="hidden" name="re_step" value="${re_step}">
    <input type="hidden" name="re_level" value="${re_level}">
    <input type="hidden" name="whatColumn" value="${whatColumn}">
    <input type="hidden" name="pageNumber" value="${pageNumber}">
    <input type="hidden" name="keyword" value="${keyword}">

    <table border=1 width=500 align="center">
        <tr>
            <th align=right colspan=2>
                <a href="list.bd?pageNumber=${param.pageNumber}" style="color: lightblue">글목록</a>
            </th>
        </tr>
        <tr>
            <th>이름</th>
            <td>
                <input type="text" name="writer" maxlength="10" value="${board.writer }">
                <form:errors path="writer" cssClass="err" />
            </td>
        </tr>
        <tr>
            <th>제목</th>
            <td><input type="text" name="subject" value="${board.subject }">
                <form:errors path="subject" cssClass="err" />
            </td>
        </tr>
        <tr>
            <th>email</th>
            <td><input type="text" name="email" value="${board.email }">
                <form:errors path="email" cssClass="err" />
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea name="content" rows="15" cols="90">${board.content }</textarea>
                <form:errors path="content" cssClass="err" />
            </td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td><input type="password" name="passwd">
                <form:errors path="passwd" cssClass="err" />
            </td>
        </tr>
        <tr>
            <th colspan=2 align="center">
                <input type="submit" value="글쓰기">
                <input type="reset" value="다시작성">
                <input type="button" value="목록보기" onclick="location.href='list.bd?pageNumber=${param.pageNumber}'">
            </th>
        </tr>
    </table>
</form:form>
