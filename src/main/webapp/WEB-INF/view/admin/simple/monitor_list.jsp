<%@page import="org.coody.framework.util.DateUtils"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<!doctype html>
<html>

<head>
<jsp:include page="../../includ/header.jsp" />
<style>
table {
	font-size: 1.2rem !important;
}
td{
    max-width: 16rem !important;
}
.page-header-description {
	font-size: 1.4rem !important;
	margin: 0 0 0.3rem 0 !important;
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
			<div class="tpl-content-page-title">开发中心</div>
			<ol class="am-breadcrumb">
				<li><a href="#" class="am-icon-home">开发中心</a></li>
				<li class="am-active">监听管理</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 监听管理
					</div>
				</div>
				<div class="tpl-block">

					<div class="am-g">
						<div class="am-u-sm-12">
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th>方法</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty keys }">
										<tr class="even gradeC">
											<td colspan="2"><center>暂无数据</center></td>
										</tr>
									</c:if>
									<c:forEach items="${keys }" var="key">
										<tr class="even gradeC">
											<td>${key}</td>
											<td>
												<div class="tpl-table-black-operation">
													<a href="monitorInfo.${defSuffix }?key=${key }" class="">
														<i class="am-icon-archive"></i> 查看详情
													</a><br> <a href="javascript:cancelMonitor('${key }')" class="">
														<i class="am-icon-trash"></i> 取消监听
													</a>
												</div>
											</td>
										</tr>
									</c:forEach>
									<!-- more data -->
								</tbody>
							</table>
						</div>
					</div>



					<div class="tpl-alert"></div>
				</div>

			</div>

		</div>

	</div>
	<jsp:include page="../../includ/js.jsp" />
</body>
	<script>
		function cancelMonitor(key) {
			$.ajax({
				type : "POST",
				dataType : 'json',
				data : "isRun=0&key=" + key,
				url : 'startMonitor.${defSuffix}',
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