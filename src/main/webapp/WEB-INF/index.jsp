<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 下面这个单纯是头部格式，不涉及任何数据代码 -->
<%@ include file="/common/admin/ace_head.jsp"%>
<title>管理后台</title>
</head>
<body class="no-skin">
	 <%@ include file="/common/admin/nav.jsp"%>
	<div class="main-container" id="main-container">
	 	<jsp:include page="/getMenu.do" flush="true">
			<jsp:param name="menuName" value="home" />
		</jsp:include>
		
		<div class="main-content">
		
			<div class="breadcrumbs" id="breadcrumbs">
			
				<ul class="breadcrumb">
				<!-- 下面的链接实际上是指向全站的相对路径，而不是本工程下的class，所以绕了一圈 -->
					<li><a href="/admin/index">管理后台首页链接</a></li>
				</ul>
			</div>
			<div class="page-conten t">
					<h2 class="text-center">欢迎登录管理后台</h2>
			</div>
		</div>
	</div> 
	<!-- <%@ include file="/common/admin/script.jsp"%> -->
</body>
</html>