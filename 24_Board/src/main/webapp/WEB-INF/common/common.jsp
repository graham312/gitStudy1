<%@page import="member.model.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- common.jsp<br> -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- MemberLoginController : session.setAttribute("loginInfo", mb);  -->

접속자 아이디1 : <%= session.getAttribute("loginInfo") %> <br>
접속자 아이디2 : ${loginInfo.id}<br>
접속자 아이디3 : ${sessionScope.loginInfo.id}<br>
<%-- 접속자 아이디4 :<%= ((MemberBean)session.getAttribute("loginInfo")).getId() %> --%>

<br><br>
<!-- 
session.setAttribute(loginInfo:로그인한 사람의 정보)
session.setAttribute(destination:redirect:/insert.prd)
 -->
<a href="main.jsp">시작페이지</a> <br>
<a href="logout.jsp">로그아웃</a> <br>



