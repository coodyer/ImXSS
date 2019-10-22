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
				<li class="am-active">邀请码管理</li>
			</ol>
			<form name="dataForm" action="" method="post">
				<div class="tpl-portlet-components">
					<div class="portlet-title">
						<div class="caption font-green bold">
							<span class="am-icon-code"></span> 邀请码列表
						</div>
					</div>
					<div class="tpl-block">
						<div class="am-g">
							<div class="am-u-sm-12 am-u-md-6">
								<div class="am-btn-toolbar">
									<div class="am-btn-group am-btn-group-xs">
										<button type="button" onclick="javascript:createInvite()"
											class="am-btn am-btn-default am-btn-success">
											<span class="am-icon-plus"></span> 生成邀请码
										</button>
									</div>
								</div>
							</div>
							<!-- 搜索 -->
							<div class="am-u-sm-12 am-u-md-3">
								<div class="am-form-group">
									<select data-am-selected="{btnSize: 'sm'}"
										style="display: none;" name="status">
										<option value="-1">状态</option>
										<option value="0" ${0==status?'selected':'' }>未使用</option>
										<option value="1" ${1==status?'selected':'' }>已使用</option>
									</select>

								</div>
							</div>
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
											<th class="table-id">邀请码</th>
											<th class="table-title">状态</th>
											<th class="table-type am-hide-sm-only">注册用户</th>
											<th class="table-date am-hide-sm-only">更新时间</th>
									</thead>
									<tbody>
										<c:if test="${empty dataPager.data }">
											<tr>
												<td colspan="4"><center>暂无数据</center></td>
											</tr>
										</c:if>
										<c:forEach items="${dataPager.data }" var="item">
											<tr>
												<td>${item.inviteCode }</td>
												<td>${item.status==0?'未使用':'已使用' }</td>
												<td class="table-date am-hide-sm-only">${item.regUserEmail }</td>
												<td class="am-hide-sm-only"><fmt:formatDate
														value="${item.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
	function createInvite() {
		if (!confirm("确定执行此操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			url : 'createInvite.${defSuffix}',
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