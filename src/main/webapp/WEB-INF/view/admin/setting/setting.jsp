<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<li><a href="#" class="am-icon-home">管理中心</a></li>
				<li class="am-active">系统设置</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 修改系统设置
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="submitForm();return false;"
								enctype="multipart/form-data">

								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">网站标题
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											minlength="3" maxlength="64" name="siteName"
											value="${setting.siteName }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">关键词
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											minlength="3" maxlength="128" name="keywords"
											value="${setting.keywords }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">描述
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											minlength="3" maxlength="256" name="description"
											value="${setting.description }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">版权信息
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											minlength="3" maxlength="64" name="copyright"
											value="${setting.copyright }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-intro" class="am-u-sm-3 am-form-label">开启注册</label>
									<div class="am-u-sm-9">
										<div class="tpl-switch">
											<input name="openReg" value="${setting.openReg }"
												type="hidden" id="openReg"> <input type="checkbox"
												style="width: 50px;height: 25px" id="openRegCheck"
												onchange="checkOpenReg()"
												class="ios-switch bigswitch tpl-switch-btn"
												${setting.openReg==1?'checked':'' }>
											<div class="tpl-switch-btn-view">
												<div></div>
											</div>
										</div>

									</div>
								</div>

								<div class="am-form-group">
									<label for="user-intro" class="am-u-sm-3 am-form-label">需邀请码</label>
									<div class="am-u-sm-9">
										<div class="tpl-switch">
											<input name="needInvite" value="${setting.openReg }"
												type="hidden" id="needInvite"> <input
												type="checkbox" style="width: 50px;height: 25px"
												id="needInviteCheck" onchange="checkNeedInvite()"
												class="ios-switch bigswitch tpl-switch-btn"
												${setting.needInvite==1?'checked':'' }>
											<div class="tpl-switch-btn-view">
												<div></div>
											</div>
										</div>

									</div>
								</div>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="submit"
											class="am-btn am-btn-primary tpl-btn-bg-color-success ">提交</button>
									</div>
								</div>
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
			url : 'settingSave.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.reload(true);
				}
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
	function checkNeedInvite() {
		if ($("#needInviteCheck").is(':checked')) {
			$("#needInvite").val(1);
		} else {
			$("#needInvite").val(0);
		}
	}

	function checkOpenReg() {
		if ($("#openRegCheck").is(':checked')) {
			$("#openReg").val(1);
		} else {
			$("#openReg").val(0);
		}
	}
</script>
</html>