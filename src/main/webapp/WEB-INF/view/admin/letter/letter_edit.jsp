<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
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
				<li><a href="#" class="am-icon-home">信封中心</a></li>
				<li><a href="#" class="am-icon-home">信封管理</a></li>
				<li class="am-active">信封查看</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 查看信封信息
					</div>
				</div>
				<div class="tpl-block">
					<div class="am-g">
						<div class="tpl-form-body tpl-form-line">
							<form class="am-form tpl-form-line-form" id="dataform"
								onsubmit="return false;">
								<c:if test="${!empty letterInfo.projectName }">
									<div class="am-form-group">
										<label for="user-name" class="am-u-sm-3 am-form-label">所属项目</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												value="${letterInfo.projectName }">
										</div>
									</div>
								</c:if>
								<c:if test="${!empty letterInfo.moduleName }">
									<div class="am-form-group">
										<label for="user-name" class="am-u-sm-3 am-form-label">所属模板</label>
										<div class="am-u-sm-9">
											<input type="text" class="tpl-form-input" id="user-name"
												value="${letterInfo.moduleName }">
										</div>
									</div>
								</c:if>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">IP地址</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											value="${letterInfo.ip } ${letterInfo.ipInfo}">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">来源地址</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											value="${letterInfo.refUrl }">
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-name" class="am-u-sm-3 am-form-label">收信时间</label>
									<div class="am-u-sm-9">
										<input type="text" class="tpl-form-input" id="user-name"
											value="<fmt:formatDate
														value="${letterInfo.updateTime }"
														pattern="yyyy-MM-dd HH:mm:ss" />">
									</div>
								</div>
								<c:forEach items="${letterParas }" var="para">
									<div class="am-form-group">
										<label for="user-name" class="am-u-sm-3 am-form-label">${para.paraName }</label>
										<div class="am-u-sm-9">
											<c:if test="${fn:length(para.paraValue)<'200' && !fn:contains(para.paraValue,';') }">
												<input type="text" class="tpl-form-input" id="user-name"
													value="${para.paraValue }">
											</c:if>
											<c:if test="${fn:length(para.paraValue)>'200' || fn:contains(para.paraValue,';') }">
												<textarea class="" rows="${fn:length(para.paraValue)/70+1 }" id="user-intro">${para.paraValue}</textarea>
											</c:if>
										</div>
									</div>
								</c:forEach>
								<div class="am-form-group">
									<div class="am-u-sm-9 am-u-sm-push-3">
										<button type="button" onclick="history.back()"
											class="am-btn am-btn-primary tpl-btn-bg-color-success ">返回</button>
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
</script>
</html>