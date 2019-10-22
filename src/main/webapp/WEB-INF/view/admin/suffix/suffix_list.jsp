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
				<li><a href="#" class="am-icon-home">管理中心</a></li>
				<li class="am-active">后缀管理</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 后缀列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
									<button type="button"
											onclick="location.href='addSuffix.${defSuffix }'"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 添加后缀
										</button></div>
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
											<th class="table-id">后缀</th>
											<th class="table-title">状态</th>
											<th class="table-date">操作</th>
									</thead>
									<tbody>
										<c:if test="${empty dataPager.data }">
											<tr>
												<td colspan="3"><center>暂无数据</center></td>
											</tr>
										</c:if>
										<c:forEach items="${dataPager.data }" var="item">
											<tr>
												<td>${item.suffix }</td>
												<td><c:if test="${item.status==0 }">未开启</c:if> <c:if
														test="${item.status==1 }">已开启</c:if> <c:if
														test="${item.status==2 }">默认</c:if></td>
												<td><c:if
														test="${item.status==0 }">
														<a href="javascript:openSuffix('${item.suffix }',1)">开启 </a>
													</c:if> <c:if test="${item.status==1 }">
														<a href="javascript:openSuffix('${item.suffix }',0)">关闭 </a>
														<a href="javascript:openSuffix('${item.suffix }',2)">设为默认 </a>
													</c:if>
													<a href="javascript:delSuffix('${item.suffix }')">删除</a>
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
	function openSuffix(suffix,status) {
		if (!confirm("确定执行此操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'suffix='+suffix+'&status='+status,
			url : 'openSuffix.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					if(status==2){
						var href=location.href;
						href=href.replace('${defSuffix}',suffix);
						location.href=href;
						return;
					}
					location.reload(true);
				}
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
	function delSuffix(suffix) {
		if (!confirm("确定执行此操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'suffix='+suffix,
			url : 'delSuffix.${defSuffix}',
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