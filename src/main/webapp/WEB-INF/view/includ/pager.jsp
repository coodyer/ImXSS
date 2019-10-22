<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<div class="am-fr">
	<c:if test="${!empty dataPager.data && dataPager.totalPage>1 }">
	<ul class="am-pagination tpl-pagination">
	<li class="t4">当前页:${dataPager.currPage }</li>
	<li class="t4">总页数:${dataPager.totalPage }</li>
			<li class="t4">总条数:${dataPager.totalRows }</li>
	</ul>
		<ul class="am-pagination tpl-pagination">
			<li ${dataPager.currPage==1?'class=\"am-disabled\"':'' }><a
				href="javascript:toPager(1)">«</a></li>
			<c:forEach var="i" begin="${dataPager.viewBegin }"
				end="${dataPager.viewEnd }">
				<li ${dataPager.currPage==i?'class=\"am-active\"':'' }><a
					href="javascript:toPager(${i })">${i }</a></li>
			</c:forEach>
			<li
				${dataPager.totalPage==dataPager.currPage?'class=\"am-disabled\"':'' }><a
				href="javascript:toPager(${dataPager.totalPage })">»</a></li>
		</ul>
		<input type="hidden" name="currPage" value="${dataPager.currPage }"
			id="currPage">
		<script>
			function toPager(page) {
				$("#currPage").val(page);
				document.dataForm.submit();
			}
		</script>
	</c:if>
</div>
<style>
.t4 {
	font-size: 1.2rem;
}
</style>