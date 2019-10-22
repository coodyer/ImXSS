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
				<li><a href="#" class="am-icon-home">项目中心</a></li>
				<li class="am-active">模块定制</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 模块定制列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button"
											onclick="location.href='projectModuleCustomEdit.${defSuffix }?projectId=${projectInfo.id }'"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 添加定制
										</button>
									</div>
								</div>
							</div>
							<!-- 搜索 -->
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-input-group am-input-group-sm">
									<input type="text" class="am-form-field" name="keyWorld"
										placeholder="输入关键字搜索" value="${keyWorld }"> <span
										class="am-input-group-btn">
										<button
											class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
											type="submit"></button>
									</span>
								</div>
							</div>
						</div>
						<div class="am-g">
							<div class="am-u-sm-12">
								<c:if test="${!empty dataPager.data }">
									<table
										class="am-table am-table-striped am-table-hover table-main">
										<thead>
											<tr>
												<th>匹配方式</th>
												<th>匹配值</th>
												<th>模块</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${empty dataPager.data }">
												<tr>
													<td colspan="4"><center>暂无数据</center></td>
												</tr>
											</c:if>
											<c:forEach items="${dataPager.data }" var="mapping">
												<tr>
													<td style="max-width: 15%;word-break:break-all">${mapping.type==1?'来源地址':'IP' }</td>
													<td style="max-width: 50%;word-break:break-all">${mapping.mapping }</td>
													<td>${mapping.moduleName }</td>
													<td style="max-width: 20%;word-break:break-all">
														<div class="am-btn-toolbar">
															<div class="am-btn-group am-btn-group-xs">
																<c:if test="${mapping.userId==curr_login_user.id }">
																	<button type="button"
																		onclick="location.href='projectModuleCustomEdit.${defSuffix }?id=${mapping.id }'"
																		class="am-btn am-btn-default am-btn-xs am-text-secondary">
																		编辑</button>
																	<button type="button"
																		onclick="delData('${mapping.id }')"
																		class="am-btn am-btn-default am-btn-xs am-text-danger">
																		删除</button>
																</c:if>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
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
	function delData(id) {
		if (!confirm("数据删除后将无法恢复,确定执行此操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id=' + id,
			url : 'projectModuleCustomDel.${defSuffix}',
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