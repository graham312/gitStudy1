<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<style>
    table {
        margin: 5px auto;
        text-align: center;
    }
    th{
    	background-color: lightblue;
    }
</style>

<center>
    <h2>글 내용 보기</h2>
    <form action="detail.bd" method="post">
        <table border="1" height="200px" width="500px">
            <tr>
                <th>번호</th>
                <td>${board.num}</td>
                <th>조회수</th>
                <td>${board.readcount}</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${board.writer}</td>
                <th>작성일</th>
                <td>
                    <fmt:formatDate value="${board.reg_date}" pattern="yyyy-MM-dd HH:mm" />
                </td>
            </tr>
            <tr>
                <th>제목</th>
                <td colspan="5">${board.subject}</td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="5">${board.content}</td>
            </tr>
            <tr>
                <th colspan="5">
                	<input type="hidden" name="num" value="${board.num}">
                    <input type="button" value="수정하기" onClick="location.href='update.bd?num=${board.num }&whatColumn=${param.whatColumn}&keyword=${param.keyword}&pageNumber=${param.pageNumber}'">
                    <input type="button" value="삭제하기" onClick="location.href='delete.bd?num=${board.num }&whatColumn=${param.whatColumn}&keyword=${param.keyword}&pageNumber=${param.pageNumber}'">
                    <input type="button" value="답글쓰기" onClick="location.href='reply.bd?ref=${board.ref }&re_step=${board.re_step }&re_level=${board.re_level }&whatColumn=${param.whatColumn}&keyword=${param.keyword}&pageNumber=${param.pageNumber}'">
	  				<input type="button" value="목록보기" onClick="location.href='list.bd?num=${board.num }&whatColumn=${param.whatColumn}&keyword=${param.keyword}&pageNumber=${param.pageNumber}'">
                </th>
            </tr>
        </table>
    </form>
</center>
