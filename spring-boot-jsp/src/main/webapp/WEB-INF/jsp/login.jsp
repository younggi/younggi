<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>
	<form action="/login" method="post">
		<input type="text" name="id">
		<br>
		<input type="password" name="password">
		<br>
		<input type="submit" value="login">
		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="redirect" value="${redirect}">
	</form>
</body>
</html>