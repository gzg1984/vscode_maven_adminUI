<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="gui" uri="http://framework.genesisdo.com/gui"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/admin/ace_head.jsp"%>
<title>管理后台</title>
</head>
<body class="no-skin">
	<%@ include file="/common/admin/nav.jsp"%>
	<div class="main-container" id="main-container">
		<jsp:include page="/admin/getMenu.do" flush="true">
			<jsp:param name="menuName" value="site-manage" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li><a href="#">站点管理</a></li>
					<li class="active">备份与还原</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						备份列表<small> <i class="ace-icon fa fa-angle-double-right"></i>
							查看网站备份
						</small>
						<button id="backupBtn" class="btn btn-primary btm-sm pull-right" onclick="newBackup(this)" type="button">新建备份</button>
					</h1>
				</div>
				<div class="col-sm-12 gui-datagrid">
					<div id="tb" class="gui-toolbar">
						<form data-role="filter">
							<div class="row">
								<div class="col-lg-2 col-md-2">
								</div>
<!-- 								<div class="col-lg-2 col-md-2"> -->
<!-- 									<input type="text" class="easyui-textbox" prompt="权威来源" style="width:100%" name="sourceFromFuzzy"> -->
<!-- 								</div> -->
								<div class="col-lg-2 gui-button-group pull-right">
<!-- 									<button type="button" data-role="search" class="btn btn-xs btn-primary">搜索</button> -->
<!-- 									<a href="/admin/project/upload.do" class="btn btn-xs btn-success">上传</a> -->
<!-- 									<button type="button"  data-role="edit"  class="btn btn-xs btn-default">编辑</button> -->
<!-- 									<button type="button"  data-role="delete"  class="btn btn-xs btn-danger">删除</button> -->
								</div>
							</div>
						</form>
					</div>
					<table class="table">
					<thead>
						<tr><th>备份名称</th><th>日期</th><th>操作</th><tr>
					</thead>
					<tbody>
						<c:forEach var="dir" items="${dirs }">
							<tr><td>${dir.backupName }</td><td>${dir.backupDate }</td><td><button type="button" class="btn-xs btn btn-danger restore-btn" onclick="restore('${dir.backupName }',this)">还原</button></td></tr>
						</c:forEach>
					</tbody>
					</table>
				
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/admin/script.jsp"%>
	<script type="text/javascript" src="/assets/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="/assets/js/upload.js"></script>
	<script type="text/javascript">
	function newBackup(btn){
		$(btn).html('<i class="fa fa-spinner fa-spin fa-fw"></i>正在备份');
		$(btn).prop('disabled',true);
		$('.restore-btn').prop('disabled',true);
		$.post(baseUrl+'/admin/site/addBackup.do',{},function(res){
			$(btn).html('新建备份');
			alert(res.msg);
			$(btn).prop('disabled',false);
			$('.restore-btn').prop('disabled',false);
			if(res.success){
				location.reload();
			}
		},'json');
	}
	function restore(backupName,btn){
		if(confirm('还原操作将覆盖现有数据，请慎重操作，确定要还原到'+backupName+'吗？')){
			$(btn).html('<i class="fa fa-spinner fa-spin fa-fw"></i>正在还原');
			$('.restore-btn').prop('disabled',true);
			$('#backupBtn').prop('disabled',true);
			$.post(baseUrl+'/admin/site/restore.do',{backupName:backupName},function(res){
				alert(res.msg);
				$(btn).html('还原');
				$('.restore-btn').prop('disabled',false);
				$('#backupBtn').prop('disabled',false);
			},'json');
		}
	}
	</script>
</body>
</html>