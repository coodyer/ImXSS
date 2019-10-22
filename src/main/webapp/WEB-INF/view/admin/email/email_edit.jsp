<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!doctype html>
<html>

<head>
<jsp:include page="../../includ/header.jsp" />
</head>

<body data-type="index">


	<header class="am-topbar am-topbar-inverse admin-header">
		<jsp:include page="../../includ/nav.jsp" />
	</header>

	<div class="tpl-page-container tpl-page-header-fixed">
		<div class="tpl-left-nav tpl-left-nav-hover">
			<jsp:include page="../../includ/left.jsp" />
		</div>





		<div class="tpl-content-wrapper">
			<ol class="am-breadcrumb">
				<li><a href="#" class="am-icon-home">公共信息</a></li>
				<li><a href="#" class="am-icon-home">邮箱管理</a></li>
				<li class="am-active">邮箱编辑</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 编辑邮箱信息
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="submitForm();return false;">
								<input type="hidden" name="id" value="${email.id }">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">服务器
										</label>
										<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="smtp" placeholder="请填写smtp服务器(3-32)" minlength="3"
											maxlength="32" value="${email.smtp }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">发信账号
										</label>
										<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="email" placeholder="请填写发信账号" minlength="6"
											maxlength="32" value="${email.email }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">发信密码
										</label>
										<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="password" placeholder="请填写发信密码" minlength="6"
											maxlength="32" value="${email.password }">
									</div>
								</div>
								<c:if test="${curr_login_user.roleId==1 }">
									<div class="am-form-group">
										<div class="am-u-sm-9 am-u-sm-push-3">
											<button type="submit"
												class="am-btn am-btn-primary tpl-btn-bg-color-success ">提交</button>
										</div>
									</div>
								</c:if>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="../../includ/js.jsp" />
</body>
<script>
	function submitForm() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'emailSave.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href = document.referrer;
				}
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
</script>
</html>