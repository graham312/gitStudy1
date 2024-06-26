<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     	
<title>메인</title>
main.jsp<br>
<%
	String viewBoard = request.getContextPath() + "/list.bd";
	String viewMember = request.getContextPath() + "/memberList.mb";
%>

<a href="<%= viewBoard %>">게시판 목록 보기</a><br><br>
<a href="<%= viewMember %>">회원 목록 보기</a><br><br>

