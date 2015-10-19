<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- 	<meta http-equiv="X-UA-Compatible" content="IE=10"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Cache-control" content="private">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache-control" content="no-store">
<meta http-equiv="X-Content-Type-Options" content="nosniff" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<link href="css/common.min.css"  rel="stylesheet"/>
<link href="css/layout.min.css"  rel="stylesheet"/>
<link href="css/ui.min.css"  rel="stylesheet"/>
<link href="css/master-ui.min.css"  rel="stylesheet"/>
<title>Login Page</title>
</head>
<body background="images/bg_login.png">
	<div class="login">
	<div class="login_page_wrap">
	<div class="login_page">
	<div class="lp_inner">
	<h1 class="lp_header">
		<span><img src="images/bg_login_tit.png"></span>
	</h1>
	<div class="login_wrap">
	<form name="login" action="/login" method="post">
		<fieldset class="login_form">
			<legend class="blind"></legend>
			<div class="login_row">
			<input type="text" name="id">
			</div>
			<div class="login_row">
			<input type="password" name="password">
			</div>
			<div class="login_check">
			<input type="checkbox" id="checkbox_01" class="CheckBoxClass">
			<label for="checkbox_01" class="CheckBoxLabelClass cblc_type02 LabelSelected">Save User ID</label>
			</div>
			<div class="logins_btn">
				<a id="login_btn" class="btn_login" onclick="login" value="login">Login</a>
			</div>
			
		</fieldset>
 		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="redirect" value="${redirect}">
	</form>
	</div>
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
		login_btn.onclick=function() {
			document.login.submit();
		}
	</script>
</body>
</html>