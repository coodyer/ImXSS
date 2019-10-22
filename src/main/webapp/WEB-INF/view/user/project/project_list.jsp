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
				<li class="am-active">项目管理</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 项目列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button"
											onclick="location.href='projectEdit.${defSuffix }'"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 创建项目
										</button>
									</div>
								</div>
							</div>
							<!-- 搜索 -->
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-input-group am-input-group-sm">
									<input type="text" class="am-form-field" name="keyWorld" placeholder="输入关键字搜索"
										value="${keyWorld }"> <span class="am-input-group-btn">
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
											<th class="table-id am-hide-sm-only">ID</th>
											<th class="table-title">名称</th>
											<th class="table-type">模块</th>
											<th class="table-date">信封</th>
											<th class="table-date am-hide-sm-only">日期</th>
											<th class="table-set">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${empty dataPager.data }">
											<tr>
												<td colspan="6"><center>暂无数据</center></td>
											</tr>
										</c:if>
										<c:forEach items="${dataPager.data }" var="project">
											<tr>
												<td class="am-hide-sm-only">${project.id }</td>
												<td>${project.title }</td>
												<td>${project.moduleName }</td>
												<td>${project.letterNum }</td>
												<td class="am-hide-sm-only"><fmt:formatDate
														value="${project.updateTime }"
														pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>
													<div class="am-btn-toolbar">
														<div class="am-btn-group am-btn-group-xs">
															<c:if test="${project.userId==curr_login_user.id }">
																<button type="button"
																	onclick="location.href='projectEdit.${defSuffix }?projectId=${project.id}'"
																	class="am-btn am-btn-default am-btn-xs am-text-secondary">
																	<span class="am-icon-pencil-square-o"></span> 编辑
																</button>
																<button type="button" onclick="delData(${project.id})"
																	class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">
																	<span class="am-icon-trash-o"></span> 删除
																</button>
															</c:if>
															<c:if test="${project.userId!=curr_login_user.id }">
																==
															</c:if>
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
		if(!confirm("数据删除后将无法恢复,确定执行此操作?")){
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id='+id,
			url : 'projectDel.${defSuffix}',
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