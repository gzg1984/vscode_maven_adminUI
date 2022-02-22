<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/admin/ace_head.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/prettify/prettify.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/project.css">
<title>管理后台</title>
</head>
<body class="no-skin">
	<%@ include file="/common/admin/nav.jsp"%>
	<div class="main-container" id="main-container">
		<jsp:include page="/admin/getMenu.do" flush="true">
			<jsp:param name="menuName" value="project-upload" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li><a href="/admin/project/list.do">工程管理</a></li>
					<li class="active">${project.title }</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						${project.title } <small> <i class="ace-icon fa fa-angle-double-right"></i>
							${project.desc }
						</small>
					</h1>
				</div>
				<div class="col-sm-12 admin-explorer">
				<jsp:include page="/explorer/${project.titleEn }">
					<jsp:param value="${prefix }" name="prefix"/>
				</jsp:include>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/admin/script.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath() %>/assets/prettify/prettify.js"></script>
	<script>
		$(document).ready(function(){
			 PR.prettyPrint();
			 $('.prettyprint').on('click','ol li',function(){
			 	$(this).siblings().removeClass('active');
			 	$(this).addClass('active');
			 });
		});
	</script>	
</body>
</html>