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
				<li><a href="#" class="am-icon-home">用户中心</a></li>
				<li><a href="#" class="am-icon-home">模板管理</a></li>
				<li class="am-active">模板编辑</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 编辑模块信息
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="submitForm();return false;">
								<input type="hidden" name="id" value="${moduleInfo.id }">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">所属用户
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											 placeholder="请填写模板名称(3-12)" minlength="2" readonly="readonly"
											maxlength="32" value="${empty moduleInfo.userEmail?'系统':moduleInfo.userEmail }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">模板名称
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="title" placeholder="请填写模板名称(3-12)" minlength="2"
											maxlength="32" value="${moduleInfo.title }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">模板描述
									</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input"
											name="remark" placeholder="请填写模板描述"  value="${moduleInfo.remark }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-intro" class="am-u-sm-3 am-form-label">模板代码</label>
									<div class="am-u-sm-9">
										<textarea class="" name="content" rows="10" id="user-name" minlength="6"
											placeholder="请输入模板内容">${moduleInfo.content }</textarea>
										<small>注："{api}"表示收信地址</small>
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
			url : 'moduleSave.${defSuffix}',
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