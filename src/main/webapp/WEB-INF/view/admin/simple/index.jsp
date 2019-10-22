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
				<li class="am-active">运行状态</li>
			</ol>
			<div class="tpl-portlet-components">
				<div class="portlet-title">
					<div class="caption font-green bold">
						<span class="am-icon-code"></span> 运行状态
					</div>
				</div>
				<div class="tpl-block">




					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
									<p class="page-header-description">常规信息</p>
								</div>
							</div>
							<table
								class="am-table am-table-striped am-table-hover table-main"
								id="example-r">
								<tbody>
									<tr class="gradeX">
										<td>用户名：<span class="t4">${curr_login_user.nickName }</span></td>
									</tr>
									<tr class="gradeX">
										<td>物理路径：<span class="t4"><%=SystemHandle.rootPath%></span></td>
									</tr>
									<tr class="gradeX">
										<td>身份过期：<span class="t4">30 分钟</span></td>
									</tr>
									<tr class="gradeX">
										<td>现在时间：<span class="t4"><%=DateUtils.toString(new Date(), DateUtils.DATETIME_PATTERN)%></span></td>
									</tr>
									<tr class="gradeX">
										<td>服务器域名：<span class="t4">${fn:replace(basePath,'/','') }</span></td>
									</tr>
									<tr class="gradeX">
										<td>服务器ID：<span class="t4"><%=SystemHandle.serverId%></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">系统信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<tbody>
										<tr class="gradeX">
											<td>设备名称：<span class="t4"><%=SystemHandle.systemInfo.getPcName()%></span></td>
											<td>系统名称：<span class="t4"><%=SystemHandle.systemInfo.getOsName()%></span></td>
										</tr>
										<tr class="gradeX">
											<td>系统架构：<span class="t4"><%=SystemHandle.systemInfo.getOsArch()%></span></td>
											<td>系统版本：<span class="t4"><%=SystemHandle.systemInfo.getOsVersion()%></span></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">用户信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<tbody>
										<tr>
											<td>用户名：<span class="t4"><%=SystemHandle.pcUserInfo.getUserName()%></span></td>
										</tr>
										<tr>
											<td>运行目录：<span class="t4"><%=SystemHandle.pcUserInfo.getUserDir()%></span></td>
										</tr>
										<tr>
											<td>主要目录：<span class="t4"><%=SystemHandle.pcUserInfo.getUserHome()%></span></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">程序信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<tbody>
										<tr class="gradeX">
											<td>总内存：<span class="t4"><%=SystemHandle.getJavaInfo().getJavaTotalMemory()%>MB</span></td>
											<td>可用内存：<span class="t4"><%=SystemHandle.getJavaInfo().getJavaFreeMemory()%>MB</span></td>
										</tr>
										<tr class="gradeX">
											<td>可用CPU：<span class="t4"><%=SystemHandle.getJavaInfo().getAvailableProcessors()%>个</span></td>
											<td>版本：<span class="t4"><%=SystemHandle.getJavaInfo().getJavaVersion()%></span></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>



					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">内存信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<tbody>
										<tr class="gradeX">
											<td>总内存：<span class="t4"><%=SystemHandle.memoryModel.getTotal()%>MB</span></td>
											<td>剩余内存：<span class="t4"><%=SystemHandle.memoryModel.getFree()%>MB</span></td>
										</tr>
										<tr class="gradeX">
											<td>交换区内存：<span class="t4"><%=SystemHandle.memoryModel.getSwapTotal()%>MB</span></td>
											<td>交换区剩余内存：<span class="t4"><%=SystemHandle.memoryModel.getSwapFree()%>MB</span></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>


					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">CPU信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<thead>
										<tr>
											<th class="table-title">厂商</th>
											<th class="table-title">架构</th>
											<th class="table-title">主频</th>
											<th class="table-title">使用率</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${cpuInfos }" var="cpu">
											<tr class="gradeX">
												<td><span class="t4">${cpu.vendor }</span></td>
												<td><span class="t4">${cpu.model }</span></td>
												<td><span class="t4">${cpu.mhz }Mhz</span></td>
												<td><span class="t4">${cpu.combined }</span></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="widget-head am-cf">
									<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
										<p class="page-header-description">磁盘信息</p>
									</div>
								</div>
								<table
									class="am-table am-table-striped am-table-hover table-main"
									id="example-r">
									<thead>
										<tr>
											<th class="am-hide-sm-only">盘符</th>
											<th>路径</th>
											<th>格式</th>
											<th>总量</th>
											<th>可用量</th>
											<th>使用率</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${diskInfos }" var="disk">
											<c:if test="${disk.total>0 }">
												<tr class="gradeX">
													<td class="am-hide-sm-only"><span class="t4">${disk.devName }</span></td>
													<td><span class="t4">${disk.dirName }</span></td>
													<td><span class="t4">${disk.sysTypeName }</span></td>
													<td><span class="t4">${disk.total }MB</span></td>
													<td><span class="t4">${disk.avail }MB</span></td>
													<td><span class="t4"> <fmt:formatNumber
																type="number" value="${disk.usePercent }" pattern="0.00"
																maxFractionDigits="2" />%
													</span></td>
												</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="am-g">
						<div class="am-u-sm-12">
							<div class="widget-head am-cf">
								<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
									<p class="page-header-description">网卡信息</p>
								</div>
							</div>
							<table
								class="am-table am-table-striped am-table-hover table-main">
								<thead>
									<tr>
										<th>设备名称</th>
										<th>IP地址</th>
										<th class="am-hide-sm-only">Mac地址</th>
										<th class="am-hide-sm-only">广播地址</th>
										<th>收包数</th>
										<th>发包数</th>
										<th class="am-hide-sm-only">收包大小</th>
										<th class="am-hide-sm-only">发包大小</th>
										<th class="am-hide-sm-only">收包错误数</th>
										<th class="am-hide-sm-only">发包错误数</th>
										<th class="am-hide-sm-only">收包丢弃数</th>
										<th class="am-hide-sm-only">发包丢弃数</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${netInfos }" var="net">
										<tr class="gradeX">
											<td><span class="t4">${net.equaName }</span></td>
											<td><span class="t4">${net.ipAddress }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.hwaddr }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.broadcast }</span></td>
											<td><span class="t4">${net.rxPackets }</span></td>
											<td><span class="t4">${net.txPackets }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.rxBytes }B</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.txBytes }B</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.rxErrors }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.txPackets }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.rxDropped }</span></td>
											<td class="am-hide-sm-only"><span class="t4">${net.txDropped }</span></td>
										</tr>
									</c:forEach>
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