<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var contextPath='<%=request.getContextPath()%>';
	var uploadPath='/upload';
</script>
<div class="header">
	<div class="header-inner">
		<span class="logo"><a href="<%=request.getContextPath()%>/" class="logo"><img
				src="<%=request.getContextPath()%>/assets/img/logo.png"></a></span>
		<form action="<%=request.getContextPath()%>/search" method="get">
			<div class="header-search-wrapper">
				<input type="text" name="keyword" placeholder="请输入关键字">
			</div>
			<input class="btn-submit" type="submit" value="搜索" />
		</form>
		<c:if test="${not empty sessionScope.loginUser }">
			<div class="right-dropdown">
				<span class="avatar header-avatar dropdown-toggle" id="dropdownMenu1"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					<span class="avatar-wrapper "> <img src="/upload/${sessionScope.loginUser.avatarUrl }">
				</span> <span class="caret"></span>
				</span>
				<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
					<li><a>当前登录为<font style="font-weight: bolder">${sessionScope.loginUser.username }</font></a></li>
					<li role="separator" class="divider"></li>
					<li><a href="<%=request.getContextPath()%>/user/info.html">个人信息</a></li>
<!-- 					<li><a href="#">Another action</a></li> -->
<!-- 					<li><a href="#">Something else here</a></li> -->
					<li role="separator" class="divider"></li>
					<li><a href="<%=request.getContextPath()%>/logout">退出</a></li>
				</ul>
			</div>
		</c:if>
		<c:if test="${ empty sessionScope.loginUser }">
			<div class="login">
				<a href="<%=request.getContextPath()%>/login">登录</a>或 <a href="<%=request.getContextPath()%>/register">注册</a>
			</div>
		</c:if>
	</div>
</div>