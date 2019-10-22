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
				<li><a href="#" class="am-icon-home">模板中心</a></li>
				<li class="am-active">模板管理</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 模板列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button"
											onclick="location.href='moduleEdit.${defSuffix }'"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 创建模板
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
								<table
									class="am-table am-table-striped am-table-hover table-main">
									<thead>
										<tr>
											<th class="table-id">ID</th>
											<th class="table-title">模块名</th>
											<th class="table-type am-hide-sm-only">所属用户</th>
											<th class="table-type am-hide-sm-only">描述</th>
											<th class="table-date am-hide-sm-only">修改日期</th>
											<th class="table-set">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${empty dataPager.data }">
											<tr>
												<td colspan="5"><center>暂无数据</center></td>
											</tr>
										</c:if>
										<c:forEach items="${dataPager.data }" var="module">
											<tr>
												<td>${module.id }</td>
												<td>${module.title }</td>
												<td class="table-type am-hide-sm-only">${module.userEmail }</td>
												<td class="am-hide-sm-only">${module.remark }</td>
												<td class="am-hide-sm-only"><fmt:formatDate
														value="${module.updateTime }"
														pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<button type="button"
																onclick="location.href='moduleEdit.${defSuffix }?moduleId=${module.id}'"
																class="am-btn am-btn-default am-btn-xs am-text-secondary">
																<span class="am-icon-pencil-square-o"></span> 编辑
															</button>
															<button type="button" onclick="delData(${module.id})"
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
	function delData(id) {
		if (!confirm("数据删除后将无法恢复,确定执行此操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id=' + id,
			url : 'moduleDel.${defSuffix}',
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