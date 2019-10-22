<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="am-topbar-brand">
	<a href="javascript:;" class="tpl-logo"> <img
		src="${basePath}assets/img/logo.png" alt="">
	</a>
</div>
<div class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right">
</div>
<button
	class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
	data-am-collapse="{target: '#topbar-collapse'}">
	<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
</button>

<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
	<ul
		class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
		<li class="am-hide-sm-only"><a href="javascript:;"
			id="admin-fullscreen" class="tpl-header-list-link"><span
				class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>

		<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
			<a class="am-dropdown-toggle tpl-header-list-link"
			href="javascript:;"> <span
				class="tpl-header-list-user-nick">${curr_login_user.nickName==null?curr_login_user.email:curr_login_user.nickName }</span><span
				class="tpl-header-list-user-ico"> <img
					src="${curr_login_user.logo }"
					onerror="this.src='${basePath}assets/img/userDefault.png'"></span>
		</a>
			<ul class="am-dropdown-content">
				<li><a href="${basePath }user/userSetting.${defSuffix}"><span class="am-icon-cog"></span>
						设置</a></li>
				<li><a href="javascript:loginOut()"><span class="am-icon-power-off"></span>
						退出</a></li>
			</ul>
		</li>
		<li><a href="javascript:loginOut()" class="tpl-header-list-link"><span
				class="am-icon-sign-out tpl-header-list-ico-out-size"></span></a></li>
	</ul>
</div>
<script>
	function loginOut(){
		localStorage.setItem("email", "");
		localStorage.setItem("userPwd", "");
		location.href="${basePath }user/loginOut.${defSuffix}";
	}
</script>