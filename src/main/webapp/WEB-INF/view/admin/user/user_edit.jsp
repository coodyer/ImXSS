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
				<li class="am-active">修改用户资料</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 修改用户资料
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="submitForm();return false;"
								enctype="multipart/form-data">

								<div class="am-form-group">
									<label for="user-weibo" class="am-u-sm-3 am-form-label">用户头像
									</label>
									<div class="am-u-sm-9">
										<div class="am-form-group am-form-file" style="width:160px">
											<div>
											<input type="hidden" value="${currUser.id }" name="id">
												<img class="am-circle" width="140px" height="140px"
													src="${currUser.logo }" id="showHead"
													onerror="this.src='${basePath}assets/img/userDefault.png'"
													alt="">
											</div>
											<button type="button" class="am-btn am-btn-danger am-btn-sm">
												<i class="am-icon-cloud-upload"></i> 上传头像
											</button>
											<input id="ajaxFile" type="file" name="ajaxFile" multiple=""
												onchange="fileUpload()"> <input id="logoInput"
												type="hidden" name="logo" value="${currUser.logo }">
										</div>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">用户邮箱
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											minlength="3"
											maxlength="16" value="${currUser.email }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">用户密码
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="userPwd" placeholder="请填写密码(留空不修改)" minlength="3"
											maxlength="16" value="">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">用户昵称
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="nickName" placeholder="请填写昵称" minlength="3"
											maxlength="32" value="${currUser.nickName }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">发信服务
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="smtp" placeholder="请填smtp服务器" minlength="6"
											maxlength="32" value="${currUser.smtp }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">发信账号
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="sendEmail" placeholder="请填写发信账号" minlength="5"
											maxlength="32" value="${currUser.sendEmail }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">发信密码
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="sendPwd" placeholder="请填写发信密码" minlength="6"
											maxlength="32" value="${currUser.sendPwd }">
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
			url : 'userSave.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					history.go(-1);
				}
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}

	function fileUpload() {
		var formData = new FormData($("#dataform")[0]);
		$.ajax({
			url : '${basePath}user/uploadHead.${defSuffix}',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			dataType : 'json',
			success : function(data) {
				alert(data.msg);
				if (data.code == 0) {
					$("#logoInput").val(data.datas);
					var imgSrc = data.datas + "?"
					+ Math.round(Math.random() * 1000000);
					setTimeout(function() {
						document.getElementById("showHead").src = imgSrc;
					}, 0);

				}
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
</script>
</html>