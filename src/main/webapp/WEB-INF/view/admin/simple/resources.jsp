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
.note {
    padding: 5px 15px 5px 5px !important;
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
				<div class="tpl-block">
					<div class="am-g">
						<div class="am-u-sm-12">
						<div class="note note-info">${currFile }</div>
							<table 
								class="am-table am-table-compact am-table-striped tpl-table-black "
								id="example-r">
								<thead>
									<tr>
										<th>文件名</th>
										<th>大小</th>
										<th>创建时间</th>
									</tr>
								</thead>
								<tbody>
									<tr class="even gradeC">
										<td colspan="3">
											<form method="post" name="fileFormBase"
												action="resources.${defSuffix }">
												<input type="hidden" name="file" value="${parentFile}" >
												<a href="javascript:document.fileFormBase.submit()"> <img
													width="30px" src="${basePath}assets/img/file.png" />上级目录
												</a>
											</form>
										</td>
									</tr>
									<c:forEach items="${files }" var="file" varStatus="index">
										<tr class="even gradeC">
											<td><c:if test="${file.type==0 }">
													<form method="post" name="fileForm${index.index }" action="?">
														<input type="hidden" name="file" value="${file.path }">
														<a
															href="javascript:document.fileForm${index.index}.submit()">
															<img width="30px"
															src="${basePath}assets/img/file.png" />${fn:replace(file.path, currFile, '')}
														</a>
													</form>
												</c:if> <c:if test="${file.type!=0 }">
													<form method="post" name="fileInfo${index.index }"
														action="resourcesInfo.${defSuffix }">
														<input type="hidden" name="file" value="${file.path }">
														<a
															href="javascript:document.fileInfo${index.index}.submit()">
															<img width="30px"
															src="${basePath}assets/img/${file.suffix=='class'?'java':'txt' }.png" />${fn:replace(file.path, currFile, '')}
														</a>
													</form>
												</c:if></td>
											<td>${file.size==null?'-':file.size }</td>
											<td><fmt:formatDate value="${file.time }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
</html>