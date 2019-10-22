<%@page import="org.coody.framework.util.DateUtils"%>
<%@page import="java.util.Date"%>
<%@page import="org.coody.framework.core.system.SystemHandle"%>
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
				<li class="am-active">缓存管理</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 缓存管理
					</div>
				</div>
				<div class="tpl-block">




					<div class="am-g">
						<div class="widget-body  am-fr">
							<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
								<div
									class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
									<input type="text" class="am-form-field" placeholder="请输入缓存KEY"
										id="cacheKey"> <span class="am-input-group-btn">
										<button
											class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-trash"
											type="button" onclick="delCacheTrigger()">清理缓存</button>
									</span>
								</div>
							</div>
							<div class="widget-head am-cf">
								<div class="widget-title  am-cf"></div>
							</div>
							<div class="am-u-sm-12">
								<table width="100%"
									class="am-table am-table-compact am-table-striped tpl-table-black "
									id="example-r">
									<thead>
										<tr>
											<th>KEY</th>
											<th>数目</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${entitys }" var="entity">
											<tr class="even gradeC">
												<td>${entity.fieldValue }</td>
												<td>${entity.cacheNum }</td>
												<td>
													<div class="tpl-table-black-operation">
														<a href="javascript:delCache('${entity.fieldValue }')"
															class="tpl-table-black-operation"> <i
															class="am-icon-trash"></i> 清理缓存
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
					</div>
					</div>
				</div>
		</div>
	</div>
	<jsp:include page="../../includ/js.jsp" />
</body>
<script>

function delCache(key) {
		if (!confirm("该KEY所有缓存都将清理,是否继续?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'key=' + key,
			url : 'cacheClean.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
	function delCacheTrigger() {
		var key=$("#cacheKey").val();
		delCache(key);
	}
</script>
</html>