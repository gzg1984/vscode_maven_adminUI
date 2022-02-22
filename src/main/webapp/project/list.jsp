<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="gui" uri="http://framework.genesisdo.com/gui"%>
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
			<jsp:param name="menuName" value="project-list" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li><a href="#">工程管理</a></li>
					<li class="active">工程列表</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						工程列表<small> <i class="ace-icon fa fa-angle-double-right"></i>
							管理已添加的工程
						</small>
					</h1>
				</div>
				<div class="col-sm-12 gui-datagrid">
					<div id="tb" class="gui-toolbar">
						<form data-role="filter">
							<div class="row">
								<div class="col-lg-2 col-md-2">
									<input type="text" class="easyui-textbox" prompt="关键字查询" style="width:100%" name="keywordFuzzy">
								</div>
								<div class="col-lg-2 col-md-2">
									<input type="text" class="easyui-textbox" prompt="权威来源" style="width:100%" name="sourceFromFuzzy">
								</div>
								<div class="col-lg-2 gui-button-group pull-right">
									<button type="button" data-role="search" class="btn btn-xs btn-primary">搜索</button>
									<a href="/admin/project/upload.do" class="btn btn-xs btn-success">上传</a>
<!-- 									<button type="button"  data-role="edit"  class="btn btn-xs btn-default">编辑</button> -->
									<button type="button"  data-role="delete"  class="btn btn-xs btn-danger">删除</button>
								</div>
							</div>
						</form>
					</div>
					<table class="gui-gridtable" data-statement="project.queryAllProject">
						<thead>
					        <tr>
					          <gui:th checkbox="true" type="com.genesisdo.chinalxr.project.pojo.ProjectBaseVO"/>
					          <th data-options="field:'_detail',formatter:function(value,row){return $.gui_datagrid_formatter(row.titleEn,'详情','btn btn-xs btn-default','toDetail');}"></th>
					        </tr>
					    </thead>
					</table>
				
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/admin/script.jsp"%>
	<script type="text/javascript" src="/assets/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="/assets/js/upload.js"></script>
	<script type="text/javascript">
	function toDetail(root){
		window.location.href=baseUrl+'/admin/project/detail/'+root;
	}
	$('.gui-datagrid').gui_datagrid({
		onDelete:function(row){
			var idArray='';
			if(row instanceof Array){
				var ids=[];
				for(var i=0;i<row.length;i++){
					ids.push(row[i].projectId);
				}
				idArray=ids.join(',');
			}else if(row instanceof Object){
				idArray=row.projectId;
			}
			if(idArray!=''){
				$.post(baseUrl+'/admin/project/doDelete.do',{ids:idArray},function(res){
					alert(res.msg);
					if(res.success){
						$('.gui-datagrid').gui_datagrid('reload');
					}
				},'json');
			}else{
				alert('请选择要删除的数据!');
			}
			
		},onEdit:function(row){
			if(row){
				window.location.href='/admin/project/edit.do?pid='+row.projectId;
			}
		}
	});
	</script>
</body>
</html>