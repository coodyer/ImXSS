<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>用户登录 - ${setting.siteName }</title>
<meta name="description" content="${setting.description }">
<meta name="keywords" content="${setting.keywords }">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs//amazeui/2.7.2/css/amazeui.min.css" />
<link rel="stylesheet" href="${basePath }assets/css/other.min.css" />
<script src="//cdnjs.cloudflare.com/ajax/libs//jquery/3.2.1/jquery.min.js"></script>
<link rel="icon" href="${basePath }assets/img/favicon.png"
	type="image/png">
</head>
<body class="login-container">
	<div class="login-box">
		<div class="logo-img">
			<img src="${basePath }assets/img/logo.png" alt="" />
		</div>
		<form action="user/login.${defSuffix }" class="am-form line"
			data-am-validator id="dataform" onsubmit="submitForm();return false">
			<div class="am-form-group line">
				<label for="doc-vld-name-2"  class="ico-lable"><i class="am-icon-user"></i></label> <input
					type="text" id="email" name="email" minlength="3" maxlength="32"
					placeholder="输入邮箱（至少 3 个字符）" required />
			</div>

			<div class="am-form-group line"  class="ico-lable">
				<label for="doc-vld-email-2"><i class="am-icon-key"></i></label> <input
					type="password" id="userPwd" name="userPwd" minlength="6"
					maxlength="15" placeholder="输入密码" required />
			</div>
			<div class="am-form-group">
				<a class="toLogin" href="${basePath }reg.${defSuffix}">免费注册</a>
				 <a class="toLogin" href="${basePath }resetPwd.${defSuffix}">找回密码</a>
			</div>
			<button class="am-btn am-btn-secondary loginButton" type="submit">登录</button>
		</form>
	</div>
</body>
<script>
	function submitForm() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : '${basePath}user/login.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				if (json.code != 0) {
					localStorage.setItem("email", "");
					localStorage.setItem("userPwd", "");
					alert(json.msg);
					return;
				}
				localStorage.setItem("email", $("#email").val());
				localStorage.setItem("userPwd", $("#userPwd").val());
				location.href = "${basePath}user/index.${defSuffix}";
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}


	function autoLogin() {
		var email = localStorage.getItem("email");
		var userPwd = localStorage.getItem("userPwd");
		$("#email").val(email);
		$("#userPwd").val(userPwd);
		if (email != null && userPwd != null && userPwd != '' && email != '' && email != 'undefined' && userPwd != 'undefined') {
			$.ajax({
				type : "POST",
				dataType : 'json',
				data : "email=" + email + "&userPwd=" + userPwd,
				url : '${basePath}user/login.${defSuffix}',
				timeout : 60000,
				success : function(json) {
					if (json.code != 0) {
						return;
					}
					location.href = "${basePath}user/index.${defSuffix}";
				},
				error : function() {}
			});
		}
	}

	autoLogin();
</script>
<style>

.ico-lable{
	width: 25px!important;  
}
.ico-lable li{
	width: 25px!important;  
}
</style>
</html>
