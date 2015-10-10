<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Permission Page</title>
</head>
<body>
	<form action="/permission" method="post">
		<input type="checkbox" name="user" value="user" checked>User Info		
		<br>
		<input type="checkbox" name="group" value="group">Group Info
		<br>
		<input type="submit" value="permit">
		<input type="submit" value="cancel">
		<input type="hidden" name="redirect" value="${redirect}">
	</form>
</body>
</html>