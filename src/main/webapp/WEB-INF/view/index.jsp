<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>${setting.siteName }</title>
<meta name="keywords" content="${setting.keywords }" />
<meta name="description" content="${setting.description }" />
<!-- Bootstrap Core CSS -->
<link href="//apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="${basePath }assets/css/landing-page.min.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="icon" href="${basePath }assets/img/favicon.png" type="image/png" >
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="//oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="//oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top topnav"
		role="navigation">
		<div class="container topnav">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand topnav" href="#">404实验室 Xss 运营管理平台</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#about">关于ImXSS</a></li>
					<li><a href="#services">系统说明</a></li>
					<li><a href="#contact">联系Leader</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<!-- Header -->
	<a name="about"></a>
	<div class="intro-header">
		<div class="container">

			<div class="row">
				<div class="col-lg-12">
					<div class="intro-message">
						<h1>ImXSS</h1>
						<h3>一个专业的xss渗透测试平台,PROWER BY Coody</h3>
						<hr class="intro-divider">
						<ul class="list-inline intro-social-buttons">
							<li><a href="${basePath }login.${defSuffix}"
								class="btn btn-default btn-lg"><i
									class="fa fa-twitter fa-fw"></i> <span class="network-name">登录</span></a>
							</li>
							<li><a href="${basePath }reg.${defSuffix}"
								class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i>
									<span class="network-name">注册</span></a></li>
							<li><a
								href="tencent://message/?uin=644556636&Site=ImXSS&menu=yes"
								class="btn btn-default btn-lg"><i
									class="fa fa-linkedin fa-fw"></i> <span class="network-name">联系</span>Leader</a>
							</li>
						</ul>
					</div>
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.intro-header -->

	<!-- Page Content -->

	<a name="services"></a>
	<div class="content-section-a">

		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						404实验室 Xss WebAPP:<br>
					</h2>
					<p class="lead">
						Prower By <a target="_blank"
							href="http://404test.com">404实验室</a>
						最新XSS安全测试,专业打造XSS漏洞测试系统。为广大网络安全人员提供XSS渗透测试平台
					</p>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6">
					<img class="img-responsive" src="${basePath }assets/img/ipad.png"
						alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-a -->

	<div class="content-section-b">
		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						我们并不知道:<a href="http://user.qzone.qq.com/644556636">Coody</a>
					</h2>
					<p class="lead">
						其实 <a target="_blank" href="//user.qzone.qq.com/644556636">Coody</a>根本不会技术,
						这个团队也是非常脆弱,他们写代码都是copy来的,以至于做完了整套XSS系统,都不知道这copy来的是什么语言
					</p>
				</div>
				<div class="col-lg-5 col-sm-pull-6  col-sm-6">
					<img class="img-responsive" src="${basePath }assets/img/dog.png"
						alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-b -->

	<div class="content-section-a">

		<div class="container">

			<div class="row">
				<div class="col-lg-5 col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						Coody --BY:404实验室
					</h2>
					<p class="lead">
						我们是来自全国各地的 网络安全不知名小啰啰,
						我们刚刚小学毕业,我们都不懂技术,不懂安全,甚至连自己做的系统都不会使用.
					</p>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6">
					<img class="img-responsive" src="${basePath }assets/img/phones.png"
						alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-a -->

	<a name="contact"></a>
	<div class="banner">

		<div class="container">

			<div class="row">
				<div class="col-lg-6">
					<h2>Email:root@404test.com</h2>
				</div>
				<div class="col-lg-6">
					<ul class="list-inline banner-social-buttons">
						<li><a href="${basePath }login.${defSuffix}"
							class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i>
								<span class="network-name">登录</span></a></li>
						<li><a href="${basePath }reg.${defSuffix}"
							class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i>
								<span class="network-name">注册</span></a></li>
						<li><a
							href="tencent://message/?uin=644556636&Site=ImXSS&menu=yes"
							class="btn btn-default btn-lg"><i
								class="fa fa-linkedin fa-fw"></i> <span class="network-name">联系作者</span></a>
						</li>
					</ul>
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.banner -->

	<!-- Footer -->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<ul class="list-inline">
						<li><a href="#">首页</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#about">关于Coody</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#services">系统说明</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#contact">联系Leader</a></li>
					</ul>
					<p class="copyright text-muted small">${setting.copyright }</p>
				</div>
			</div>
		</div>
	</footer>
	<!-- jQuery -->
	<script src="//apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="//apps.bdimg.com/libs/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
	<style>
	
	body{word-break:break-all;}
	</style>
</html>
