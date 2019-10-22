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

.page-header-description {
	font-size: 1.4rem !important;
	margin: 0 0 0.3rem 0 !important;
}

textarea {
	font-size: 1.2rem !important;
}

.page-header-description {
	font-size: 1.4rem !important;
	margin: 0 0 0.3rem 0 !important;
}

.t4 {
	font: 1.2rem 宋体;
	color: #800000;
}

.right {
	float: right;
}

.annotation {
	color: #949494;
}

.blue {
	color: blue;
}

.pub {
	color: #B74040;
}

.para {
	color: #776A6A;
}

body {
	font-size: 12px;
	word-break: break-all;
}

static.am-list-border>li {
	padding: 0.2rem;
}

.am-form input[type=number], .am-form input[type=search], .am-form input[type=text],
	.am-form input[type=password], .am-form input[type=datetime], .am-form input[type=datetime-local],
	.am-form input[type=date], .am-form input[type=month], .am-form input[type=time],
	.am-form input[type=week], .am-form input[type=email], .am-form input[type=url],
	.am-form input[type=tel], .am-form input[type=color], .am-form select,
	.am-form textarea, .am-form-field {
	font-size: 1.4rem;
}

td {
	max-width: 500px;
	word-break: break-all;
	min-width: 100px;
}

.admin-content {
	height: 80%;
}

footer {
	text-align: right;
	width: 100%;
	height: 25px;
}

hr, ol, p, pre, ul {
	margin: 0 0 0.6rem;
}

.am-padding {
	padding: 1.2rem;
}

.am-list, .am-topbar {
	margin-bottom: 0.6rem;
}

.am-panel {
	margin-bottom: 1px;
}

body, pre {
	line-height: 1.0;
}

.padding {
	padding: .6rem 1.25rem;
}

.tpl-block {
	font-size: 1.2rem !important;
}

.am-form-group {
	margin-bottom: 0.2rem !important;
}

label {
	margin-bottom: 1px !important;
}

.am-btn {
	line-height: 0.8rem !important;
}
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
				<li class="am-active">方法调试</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 方法调试
					</div>
				</div>
				<div class="tpl-block" id="resourcesDiv">
					<div class="am-g">
						<div class="am-u-sm-12">
							<table class="am-table am-table-bordered">
								<thead>
									<tr>
										<th><div class="am-form-group" id="classInfo">
												<c:forEach items="${classInfo.annotations }"
													var="annotation">
													<span class="annotation">@${annotation.annotationType }
														<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>

													</span>
													<br>
												</c:forEach>
												<label for="doc-vld-ta-2"><small><span
														class="pub">${classInfo.modifier}&nbsp;class&nbsp;${field.isFinal?'final':'' }&nbsp;${classInfo.isAbstract?'abstract':''}${classInfo.isInterface?'interface':''}${classInfo.isEnum?'enum':''}</span>&nbsp;${classInfo.name}
														<c:if test="${!empty classInfo.superClass }">&nbsp;<b
																class="pub">extends</b>&nbsp;${classInfo.superClass }</c:if> <c:if
															test="${!empty classInfo.interfaces }">&nbsp;<b
																class="pub">implements</b>&nbsp;
												<c:forEach items="${classInfo.interfaces }" var="interf"
																varStatus="index">
												${interf.name }
												<c:if
																	test="${index.index+1!=fn:length(classInfo.interfaces) }">,</c:if>
															</c:forEach>
														</c:if> </small></label>
											</div></th>
									</tr>

									<tr>
										<th><div class="am-form-group" id="classInfo">
												<c:forEach items="${method.annotations }" var="annotation">
													<span class="annotation">@${annotation.annotationType }
														<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>

													</span>
													<br>
												</c:forEach>
												<b class="pub">${method.modifier }&nbsp;${method.isStatic?'static':'' }&nbsp;${method.isFinal?'final':'' }&nbsp;${method.isAbstract?'abstract':'' }&nbsp;${method.isSynchronized?'synchronized':'' }</b>&nbsp;${method.returnType.name }&nbsp;${method.name }(
												<c:forEach items="${method.paramsType }" var="para"
													varStatus="index">
													<c:forEach items="${para.annotations }" var="annotation">
														<span class="annotation">@${annotation.annotationType }
															<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>
														</span>
															&nbsp;
														</c:forEach> 
														${para.fieldType }&nbsp; <span class="para">${para.fieldName }</span>
													<c:if test="${index.index+1!=fn:length(method.paramsType)}">,</c:if>
												</c:forEach>
												); <a href="javascript:location.reload(true);"
													class="am-btn am-btn-default am-btn-xs right">拉取数据</a>
												<p class="right">&nbsp;</p>
												<a href="javascript:starMonitor('${key }',${isRun==1?0:1 })"
													class="am-btn am-btn-default am-btn-xs right"
													id="starMonitor">${isRun==1?'取消监听':'开始监听' }</a>
												<p class="right">&nbsp;</p>
											</div></th>
									</tr>
								</thead>
							</table>
							<form class="am-form am-form-horizontal" method="post"
								id="debugForm0">
								<input name="key" value="${key }" type="hidden">
								<div class="am-panel am-panel-default">
									<div class="am-panel-hd am-cf"
										data-am-collapse="{target: '#collapse-panel-5'}">
										方法调试<span class="am-icon-chevron-down am-fr"></span>
									</div>
									<div id="collapse-panel-5" class="padding am-collapse">
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="width: 100%;"><small>入参：<a
													href="javascript:monitorDebug(0);"
													class="am-btn am-btn-default am-btn-xs right">执行</a></small></label>
											<textarea name="input">${initParas }</textarea>
										</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2"><small>出参：</small></label>
											<textarea name="result" id="result0"></textarea>
										</div>
									</div>
								</div>
							</form>
							<hr>
							<c:forEach items="${monitors }" var="monitor" varStatus="seq">
								<form class="am-form am-form-horizontal" method="post"
									id="debugForm${seq.index+1 }">
									<input name="key" value="${key }" type="hidden">
									<div class="am-panel am-panel-default">
										<div class="am-panel-hd am-cf"
											data-am-collapse="{target: '#collapse-panel-li${seq.index+1 }'}">
											方法执行记录(
											<fmt:formatDate value="${monitor.runTime }"
												pattern="yyyy-MM-dd HH:mm:ss" />
											) <span class="am-icon-chevron-down am-fr"></span>
										</div>
										<div id="collapse-panel-li${seq.index+1 }" class="padding am-collapse am-in padding">
											<div class="am-form-group">
												<label for="doc-vld-name-2" style="width: 100%;"><small>入参(<fmt:formatDate
															value="${monitor.runTime }"
															pattern="yyyy-MM-dd HH:mm:ss:SSS" />)：<a
														href="javascript:monitorDebug(${seq.index+1 });"
														class="am-btn am-btn-default am-btn-xs right">DeBUG</a></small></label>
												<textarea name="input">${monitor.input }</textarea>
											</div>
											<div class="am-form-group">
												<label for="doc-vld-name-2"><small>出参(<fmt:formatDate
															value="${monitor.resultTime }"
															pattern="yyyy-MM-dd HH:mm:ss:SSS" />)：
												</small></label>
												<textarea name="remark" id="result${seq.index+1 }">${monitor.output }</textarea>
											</div>
										</div>
									</div>
								</form>
							</c:forEach>
							<fieldset>
								<button type="button" class="am-btn am-btn-default am-fr right"
									onclick="javascript:history.back()">返回</button>
							</fieldset>
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

	$(function() {
		var html = $("#resourcesDiv").html();
		while (html.indexOf("  ") > -1) {
			html = html.replace("  ", "");
		}
		while (html.indexOf("&nbsp;&nbsp;") > -1) {
			html = html.replace("&nbsp;&nbsp;", "&nbsp;");
		}
		$("#resourcesDiv").html(html.toString());
	});

	function starMonitor(key, isRun) {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'key=' + key + '&isRun=' + isRun,
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

	function monitorDebug(index) {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#debugForm" + index).serialize(),
			url : 'monitorDebug.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				$("#result" + index).html(json.datas);
				alert(json.msg);
			},
			error : function() {
				alert("系统繁忙");
			}
		});
	}
</script>
</html>