<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>找回密码 - ${setting.siteName }</title>
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
			<img src="${basePath }assets/img/logo.png" alt="" /> </div>
			<form action="user/login.${defSuffix }" class="am-form"
			data-am-validator id="dataform" onsubmit="submitForm();return false">
			<div class="am-form-group line">
				<label for="doc-vld-name-2" class="ico-lable"><i class="am-icon-user"></i></label> <input
					type="text" id="email" name="email" minlength="3" maxlength="32"
					placeholder="输入邮箱（至少 3 个字符）" required /> <input type="hidden"
					name="action" value="resetPwd">
			</div>
			<div class="am-form-group line">
				<label for="doc-vld-name-2" class="ico-lable"><i class="am-icon-comment"></i></label>
				<input class="verInput" type="tel" id="doc-vld-name-2"
					name="verCode" minlength="4" maxlength="6"
					placeholder="输入验证码（4-6 个字符）" required />
				<button type="button"
					class="am-btn am-btn-secondary am-round am-btn-sm verButton"
					onclick="sendCode()">发送</button>
			</div>
			<div class="am-form-group line">
				<label for="doc-vld-email-2" class="ico-lable"><i class="am-icon-key"></i></label> <input
					type="password" id="userPwd" name="userPwd" minlength="6"
					maxlength="15" placeholder="输入新密码" required />
			</div>
			<div class="am-form-group">
				<a class="toLogin" href="${basePath }login.${defSuffix}">返回登录</a>
			</div>
			<button class="am-btn am-btn-secondary loginButton" type="submit">确认</button>
		</form>
		<c:if test="${!empty emails&&setting.openReg==1 }">
			<div class="am-form-group line"></div>
			<div class="am-form-group line">
				<div class="doc-example">
					<div class="am-panel am-panel-default">
						<div class="am-panel-hd">
							<h3 class="am-panel-title">系统发信邮箱(请添加到白名单)</h3>
						</div>
						<ul class="am-list am-list-static">
							<c:forEach items="${emails }" var="email">
								<li>${email.email }</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</body>
<script>
	function submitForm() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : '${basePath}user/resetPwd.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				if (json.code != 0) {
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

	function sendCode() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : '${basePath}user/sendCode.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
			},
			error : function() {
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
.ico-lable{
	width: 25px!important;  
}
.ico-lable li{
	width: 25px!important;  
}
</style>
</html>
