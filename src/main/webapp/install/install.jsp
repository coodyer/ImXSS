<%@page import="com.imxss.web.install.InstallHandle"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%
	if (InstallHandle.isInstall()) {
		return;
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8
">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>系统安装 -ImXSS</title>
<meta name="description" content="${setting.description }">
<meta name="keywords" content="${setting.keywords }">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs//amazeui/2.7.2/css/amazeui.min.css" />
<link rel="stylesheet" href="${basePath }assets/css/other.min.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs//jquery/3.2.1/jquery.min.js"></script>
<link rel="icon" href="${basePath }assets/img/favicon.png"
	type="image/png">
</head>
<body class="login-container">
	<div class="login-box">
		<div class="logo-img">
			<center>
				<h1>ImXSS安装</h1>
			</center>
		</div>

		<form action="do_install.jsp" class="am-form" data-am-validator
			id="dataform" onsubmit="submitForm();return false">
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd">数据库配置</div>
				<div class="am-panel-bd">
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i
							class="am-icon-hourglass-half"></i></label> <input type="text" id="email"
							name="host" minlength="10" maxlength="36" placeholder="数据库IP:端口"
							required />
					</div>
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i class="am-icon-user"></i></label> <input
							type="text" id="email" name="dbUser" minlength="2" maxlength="36"
							placeholder="数据库用户名" required />
					</div>
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i class="am-icon-key"></i></label> <input
							type="text" id="email" name="dbPwd" minlength="2" maxlength="36"
							placeholder="数据库密码" required />
					</div>
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i class="am-icon-database"></i></label>
						<input type="text" id="email" name="dbName" minlength="2"
							maxlength="36" placeholder="数据库名" required />
					</div>
				</div>
			</div>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd">管理员配置</div>
				<div class="am-panel-bd">
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i class="am-icon-reply-all"></i></label>
						<input type="text" id="email" name="adminUser" minlength="8"
							maxlength="36" placeholder="管理员账号(邮箱)" required />
					</div>
					<div class="am-form-group line">
						<label for="doc-vld-name-2"><i class="am-icon-key"></i></label> <input
							type="text" id="email" name="adminPwd" minlength="6"
							maxlength="36" placeholder="管理员密码" required />
					</div>
				</div>
			</div>
			<button class="am-btn am-btn-secondary loginButton" type="submit"
				id="loginButton">安装</button>
		</form>
	</div>
</body>
<script>
	function submitForm() {
		$("#loginButton").attr({"disabled":"disabled"});
		$("#loginButton").html("安装中");
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'do_install.jsp',
			timeout : 1200000,
			success : function(json) {
				$("#loginButton").html("安装");
				$("#loginButton").removeAttr("disabled");
				if (json.code != 0) {
					alert(json.msg);
					return;
				}
				location.href = "${basePath}";
			},
			error : function() {
				$("#loginButton").removeAttr("disabled");
				$("#loginButton").html("安装");
				alert("系统繁忙");
			}
		});
	}
</script>
<style>
.login-container .login-box .am-form .am-form-group .verInput {
	width: 60%;
	float: left;
}

.verButton {
	width: 20%;
	float: right;
}

.toLogin {
	margin-right: 10px;
}

.doc-example {
	font-size: 1.2rem !important;
}

.login-container .login-box {
	width: auto !important;
	max-width: 360px !important;
}

.login-container .login-box .am-form .line {
	margin-bottom: 10px !important;
}

.login-container {
	margin-top: 30px !important;
}
</style>

</html>
