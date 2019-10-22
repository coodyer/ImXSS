<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
<jsp:include page="../includ/header.jsp" />
</head>
<body data-type="index">

	<header class="am-topbar am-topbar-inverse admin-header">
		<jsp:include page="../includ/nav.jsp" />
	</header>

	<div class="tpl-page-container tpl-page-header-fixed">
		<div class="tpl-left-nav tpl-left-nav-hover">
			<jsp:include page="../includ/left.jsp" />
		</div>
		<div class="tpl-content-wrapper">
			<div class="tpl-content-page-title">用户首页</div>
			<ol class="am-breadcrumb">
				<li><a href="#" class="am-icon-home">ImXSS</a></li>
				<li class="am-active">用户首页</li>
			</ol>
			<div class="tpl-content-scope">
				<div class="note note-info">
					<p>ImXSS为国内最专业的Xss跨站脚本平台，包含各种人性化用户设置功能。</p>
					<p>可用于Xss跨站脚本漏洞测试，支持自建模块、自填发信邮箱、为收信来源地址定制模块、屏蔽来源地址等功能。</p>
					<p>新用户测试收信请使用正常的http链接，自测在收信过程中有包含任意信封参数。空信封将被系统拒绝。</p>
				</div>
			</div>

			<div class="row">
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat blue">
						<div class="visual">
							<i class="am-icon-comments-o"></i>
						</div>
						<div class="details">
							<div class="number">${letterNotReadedNum }</div>
							<div class="desc">未读信封</div>
						</div>
						<a class="more"
							href="${basePath }user/letter/letterCenter.${defSuffix}?isReaded=0">
							查看更多 <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat red">
						<div class="visual">
							<i class="am-icon-bar-chart-o"></i>
						</div>
						<div class="details">
							<div class="number">${letterNum }</div>
							<div class="desc">总信封数</div>
						</div>
						<a class="more"
							href="${basePath }user/letter/letterCenter.${defSuffix}">
							查看更多 <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat green">
						<div class="visual">
							<i class="am-icon-apple"></i>
						</div>
						<div class="details">
							<div class="number">${moduleNum }</div>
							<div class="desc">模块数</div>
						</div>
						<a class="more"
							href="${basePath }user/module/moduleCenter.${defSuffix}">
							查看更多 <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
				<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
					<div class="dashboard-stat purple">
						<div class="visual">
							<i class="am-icon-android"></i>
						</div>
						<div class="details">
							<div class="number">${projectNum }</div>
							<div class="desc">项目数</div>
						</div>
						<a class="more"
							href="${basePath }user/project/projectCenter.${defSuffix}">
							查看更多 <i class="m-icon-swapright m-icon-white"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd">
					<h3 class="am-panel-title">作者致言</h3>
				</div>
				<div class="am-panel-bd">
					<p>感谢大家使用这套系统，感谢大家默默无闻的支持，这将是我研发的动力。</p>
					<p>由于ImXSS前身版本的一些缺陷，笔者从零开始重新研发本套系统，正式上线将定名：ImXSS</p>
					<p>本次版本持有高效、稳定、低负载的特征，且前端页面可在手机上操作，修复了前版本的痛点</p>
					<p>请原谅笔者不会前端技术，本系统前端采用开源模板，各种抄改，拼凑起来的</p>
					<p>本系统为技术交流而生，永不收费，请勿用于非法用途，后果自负</p>
				</div>
				<div class="am-panel-footer">技术支持：QQ644556636</div>
			</div>
		</div>

	</div>


	<jsp:include page="../includ/js.jsp" />
</body>

</html>