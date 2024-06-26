<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	function memberList() {
		location.href="memberList.mb"; 
	}
	
	function register(){
		location.href="insert.mb";
	}
</script>
<title>회원 로그인</title>
    <style>
        body {
            display: flex;
            justify-content: center; /* 수평 가운데 정렬 */
            align-items: center; /* 수직 가운데 정렬 */
            height: 100vh; /* 화면 전체 높이 */
            }
            th{
            	background-color: lightblue;
            }
    </style>
<form method="post" action="loginForm.mb">
<h2>회원 로그인</h2>
	<table border="1" height=300px width="500px">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="id" placeholder="아이디"></td>
		</tr>
		
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" placeholder="비밀번호"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="로그인">
				<input type="reset" value="취소">
				<input type="button" value="회원가입" onClick="register()">
				<input type="button" value="회원 목록보기" onClick="memberList()">
			</td>
		</tr>
	</table>
</form>
