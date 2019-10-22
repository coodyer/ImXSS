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

.methodPara {
	color: #2c2f31;
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

.tpl-content-wrapper {
	line-height: 1.0!important;
}

.note {
	padding: 5px 15px 5px 5px !important;
}
fieldset{
    margin: 0 0 0.3rem 0 !important;
    margin-top: 0.3rem !important;
}
pre[class*=language-] {
     white-space: pre-wrap !important;
	 word-break: break-all !important;
}

code[class*=language-], pre[class*=language-] {
     white-space: pre-wrap !important;
	 word-break: break-all !important;
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
				<li class="am-active">资源管理</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 资源管理
					</div>
				</div>
				<div class="row" id="resourcesDiv">
					<div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
						<div class="widget am-cf">
							<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
								<div class="am-g">
									<fieldset>
										<div class="note note-info">${file }</div>
									</fieldset>
									<fieldset>
										<c:if test="${!empty context}">
											<form class="am-form am-form-horizontal"
												onsubmit="return false">
												<div class="am-form-group">
													<label for="doc-vld-ta-2"><small>文件内容：</small></label>
													<c:if test="${isMessyCode ||fn:length(context)>65536 }">
														<textarea id="doc-vld-ta-2" name="remark" rows="25">${context}</textarea>
													</c:if>
													<c:if test="${!isMessyCode && fn:length(context)<65536 }">
														<link rel="stylesheet"
															href="//cdnjs.cloudflare.com/ajax/libs//prism/1.8.1/themes/prism.min.css"
															data-noprefix />
														<script src="//cdnjs.cloudflare.com/ajax/libs//prism/1.8.0/prism.min.js"></script>
														<pre>
															<code class="language-java language-html">
${code}
															</code>
														</pre>
													</c:if>
												</div>
											</form>
										</c:if>
										<c:if test="${!empty classInfo }">
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
																		class="pub">${classInfo.modifier}&nbsp;${field.isFinal?'final':'' }&nbsp;${classInfo.isAbstract?'abstract':''}${classInfo.isInterface?'interface':''}${classInfo.isEnum?'enum':''}</span>&nbsp;${classInfo.name}
																		<c:if test="${!empty classInfo.superClass }">&nbsp;<b
																				class="pub">extends</b>&nbsp;${classInfo.superClass }</c:if>
																		<c:if test="${!empty classInfo.interfaces }">&nbsp;<b
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
												</thead>
											</table>

											<c:if test="${!empty classInfo.enumInfo }">
												<table class="am-table am-table-bordered">
													<c:forEach items="${classInfo.enumInfo }" var="enumInfo" varStatus="index">
														<tr>
															<td><form id="enumForm${index.index }"
																	onsubmit="return false">
																	<span class="blue">${enumInfo.key }</span>=<input
																		class="blue am-monospace" style="border-style:none;width:80%"	name="fieldValue" value="${fn:replace(enumInfo.value, '\"', '&quot;')}" />
															<input type="hidden" name="file" value="${file }">
															<input type="hidden" name="fieldName"
																			value="${enumInfo.key}"> <input
																			type="button" onclick="saveEnum(${index.index})"
																			value="保存"
																			class="am-btn am-btn-default am-btn-xs right">
																			</form>
															</td>
														</tr>
													</c:forEach>
												</table>
											</c:if>
											<table class="am-table am-table-bordered">
												<thead>
													<tr>
														<th>字段列表</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${empty classInfo.fields}">
														<tr>
															<td colspan="1" style="text-align:center ">暂无字段</td>
														</tr>
													</c:if>
													<c:forEach items="${classInfo.fields}" var="field"
														varStatus="index">
														<c:if
															test="${!fn:startsWith(field.fieldType.name,'org.springframework.cglib.proxy') && !fn:startsWith(field.fieldName,'CGLIB') && !fn:startsWith(field.fieldType.name,'javax.servlet')}">

															<tr>
																<td>
																	<form id="fieldForm${index.index }"
																		onsubmit="return false">
																		<div style="float: left">
																			<c:forEach items="${field.annotations }"
																				var="annotation">
																				<span class="annotation">@${annotation.annotationType }
																					<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>
																				</span>
																				<br>
																			</c:forEach>
																			<b class="pub">${(empty field.modifier)?'friendly':field.modifier}&nbsp;${field.isStatic?'static':'' }&nbsp;${field.isFinal?'final':'' }&nbsp;${field.fieldType.name}</b>
																			&nbsp;&nbsp;<span class="para">${field.fieldName }</span>
																			&nbsp;=&nbsp; <input class="blue am-monospace"
																				style="border-style:none" name="fieldValue"
																				value="${fn:length(field.stringValue)<10240?(field.stringValue==null?'null':fn:replace(field.stringValue, '\"', '&quot;')):'$(too long)' }">
																		</div>
																		<input type="hidden" name="file" value="${file }">
																		<input type="hidden" name="fieldName"
																			value="${field.fieldName }"> <input
																			type="button" onclick="saveField(${index.index})"
																			value="保存"
																			class="am-btn am-btn-default am-btn-xs right">
																	</form>
																</td>
															</tr>
														</c:if>
													</c:forEach>
												</tbody>
											</table>
											<table class="am-table
								 am-table-bordered">
												<thead>
													<tr>
														<th>方法列表</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${empty classInfo.methods}">
														<tr>
															<td colspan="1" style="text-align:center ">暂无方法</td>
														</tr>
													</c:if>
													<c:forEach items="${classInfo.methods}" var="method"
														varStatus="seq">
														<tr>
															<td>
																<form name="form${seq.index+1 }"
																	action="monitorInfo.${defSuffix}" method="POST">
																	<input type="hidden" name="file" value="${file }"><input
																		type="hidden" name="key" value="${method.key }">
																	<c:forEach items="${method.annotations }"
																		var="annotation">
																		<span class="annotation">@${annotation.annotationType }
																			<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>
																		</span>
																		<br>
																	</c:forEach>
																	<b class="pub">${(empty method.modifier)?'friendly':method.modifier}&nbsp;${method.isStatic?'static':'' }&nbsp;${method.isFinal?'final':'' }&nbsp;${method.isAbstract?'abstract':'' }&nbsp;${method.isSynchronized?'synchronized':'' }&nbsp;${method.returnType.name }</b>&nbsp;<span class="methodPara">${method.name }</span>(
																	<c:forEach items="${method.paramsType }" var="para"
																		varStatus="index">
																		<c:forEach items="${para.annotations }"
																			var="annotation">
																			<span class="annotation">@${annotation.annotationType }
																				<c:if test="${!empty annotation.fields }">
																(${annotation.fields })
															</c:if>
																			</span>
															&nbsp;
														</c:forEach> 
														<b class="methodPara">${para.fieldType }</b>&nbsp; <span class="para">${para.fieldName }</span>
																		<c:if
																			test="${index.index+1!=fn:length(method.paramsType) }">,</c:if>
																	</c:forEach>
																	); <a
																		href="javascript:document.form${seq.index+1 }.submit()"
																		class="am-btn am-btn-default am-btn-xs right">方法监听</a>
																</form>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
									</fieldset>
								</div>
							</div>
							<fieldset>
								<button type="button" class="am-btn am-btn-default am-fr right"
									onclick="javascript:history.back()">返回</button>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../includ/js.jsp" />
</body>
<script>
	function saveField(index) {
		var formName = "#fieldForm" + index;
		$.ajax({
			async : true,
			cache : false,
			type : "POST",
			dataType : 'json',
			data : $(formName).serialize(),
			url : 'modifyField.${defSuffix}',
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
	function saveEnum(index) {
		var formName = "#enumForm" + index;
		$.ajax({
			async : true,
			cache : false,
			type : "POST",
			dataType : 'json',
			data : $(formName).serialize(),
			url : 'modifyEnm.${defSuffix}',
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
</script>

</html>