<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>

<c:forEach items="${menuMaps}" var="entry">
<div class="tpl-left-nav-title">${entry.key }</div>
<div class="tpl-left-nav-list">
	<ul class="tpl-left-nav-menu">
		<c:forEach items="${entry.value }" var="menu" varStatus="index">
			<li class="tpl-left-nav-item"><a href="#" id="menu${index.index }"
				class="nav-link tpl-left-nav-link-list"> <i
					class="am-icon-wpforms"></i> <span class="parent-menu">${menu.title }</span> <i
					class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
			</a> <c:if test="${!empty  menu.childMenus}">
					<ul class="tpl-left-nav-sub-menu" style="display: block;">

						<li><c:forEach items="${menu.childMenus }" var="chileMenu">
								<a href="${basePath}${chileMenu.url}.${defSuffix}" class="${fn:contains(uri,chileMenu.url)?'active':'' }"> <i
									class="am-icon-angle-right"></i> <span>${chileMenu.title }</span>
								</a>
								<c:if test="${fn:contains(uri,chileMenu.url)}">
									<script>
										$("#menu${index.index }").addClass("active");
									</script>
								</c:if>
							</c:forEach></li>
					</ul>
				</c:if></li>
		</c:forEach>

	</ul>
</div>

</c:forEach>
<style>
.tpl-left-nav-sub-menu a {
    padding: 4px 2px 2px 30px !important;
}
.tpl-left-nav-list ul li{
	line-height: 1.4!important;
}
.parent-menu{
	font-weight: 600!important;
}
</style>
