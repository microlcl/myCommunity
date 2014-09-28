<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:if test="${not empty user.id}">

	<div id="leftbar" class="span2">
		<h1>业务</h1>
		<div class="submenu">
			<a id="media-tab" href="${ctx}/media/list?search_mediaType=picture">功能1</a>
		</div>
	
	</div>
</c:if>