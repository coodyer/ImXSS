<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
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
				<li class="am-active">公共邮箱</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 发信邮箱列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button"
											onclick="location.href='emailEdit.${defSuffix }'"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 添加邮箱
										</button>
									</div>
								</div>
							</div>
							<!-- 搜索 -->
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-input-group am-input-group-sm">
									<input type="text" class="am-form-field" name="keyWorld" placeholder="输入关键字搜索"
										value="${keyWorld }"> <span class="am-input-group-btn"
										placeholder="请输入关键字">
										<button
											class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
											type="submit"></button>
									</span>
								</div>
							</div>
						</div>
						<div class="am-g">
							<div class="am-u-sm-12">
								<table
									class="am-table am-table-striped am-table-hover table-main">
									<thead>
										<tr>
											<th class="table-type am-hide-sm-only">服务器</th>
											<th class="table-title">邮箱</th>
											<c:if test="${curr_login_user.roleId==1 }">
												<th class="table-type am-hide-sm-only">密码</th>
											</c:if>
											<th class="table-date">量</th>
											<th class="table-set">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${dataPager.data }" var="email">
											<tr>
												<td class="am-hide-sm-only">${email.smtp }</td>
												<td>${email.email }</td>
												<c:if test="${curr_login_user.roleId==1 }">
													<td class="am-hide-sm-only">${email.password }</td>
												</c:if>
												<td>${empty email.sendNum?0:email.sendNum }份</td>
												<td>
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
																<button type="button"
																	onclick="location.href='emailEdit.${defSuffix }?id=${email.id}'"
																	class="am-btn am-btn-default am-btn-xs am-text-secondary">
																	<span class="am-icon-pencil-square-o"></span> 编辑
																</button>
																<button onclick="delData(${email.id})" type="button"
																	class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
																	<span class="am-icon-trash-o"></span> 删除
																</button>
														</div>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="am-cf">
									<jsp:include page="../../includ/pager.jsp" />
								</div>
								<hr>
							</div>

						</div>
					</div>
					<div class="tpl-alert"></div>
				</div>
			</form>
		</div>
	</div>


	<jsp:include page="../../includ/js.jsp" />
</body>
<script>
	function delData(emailId) {
		if(!confirm("数据删除后将无法恢复,确定执行此操作?")){
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id='+emailId,
			url : 'emailDel.${defSuffix}',
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
</script>
</html>