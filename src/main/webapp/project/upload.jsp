<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
			<jsp:param name="menuName" value="project-upload" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li><a href="#">项目管理</a></li>
					<li class="active">上传项目</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						工程管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
							创建工程
						</small>
					</h1>
				</div>
				<div class="col-sm-12">
				<sf:form modelAttribute="project" class="form-horizontal"
					role="form" action="${url }" method="POST">
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">工程名称</label>
						<div class="col-sm-4">
							<input class="form-control" name="title"  placeholder="请输入项目中文名称" type="text"
								 id="title" value="${project.title }">
						</div>
						<div class="col-sm-6 help-block">不得超过20个字符</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">存储路径</label>
						<div class="col-sm-4">
							<input class="form-control" name="titleEn" placeholder="请输入项目存储路径名称" type="text"
								 id="titleEn" value="${project.titleEn }">
						</div>
						<div class="col-sm-6 help-block">只允许英文字符,数字和 _- ,不得超过50个字符</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">权威来源</label>
						<div class="col-sm-4">
							<input class="form-control" name="sourceFrom" placeholder="请输入项目的权威来源" type="text"
								 id="sourceFrom" value="${project.sourceFrom }">
						</div>
						<div class="col-sm-6 help-block"> </div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">项目描述</label>
						<div class="col-sm-4">
							<textarea rows="3" name="desc" class="form-control" placeholder="请描述项目"></textarea>
						</div>
						<div class="col-sm-6 help-block">内容长度请限制在200字以内 </div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">工程文件</label>
						<div class="col-sm-4">
							<input  name="zipfile"  type="file" id="file" >
							<progress  id="progressBar" style="width:90%;display:none" value="0" max="100"></progress>  
							<span id="percentage"  style="width:10%"></span> 
						</div>
						<div class="col-sm-6 help-block">只允许zip,tar.gz压缩格式,大小不得超过1GB </div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="submit"  class="btn-success btn btn-sm">上传</button>
						</div>
						<div class="col-sm-4 ">
							<a href="<%=request.getContextPath() %>/admin/project/list.do" class="btn-default btn btn-sm">返回</a>
						</div>
					</div>
					
				</sf:form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/admin/script.jsp"%>
	<script type="text/javascript" src="<%=contextPath %>/assets/js/ace/elements.fileinput.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/assets/js/upload.js"></script>
	<script type="text/javascript">
		
		
		
		$('#file').ace_file_input({
			no_file:'请选择文件',
			btn_choose:'选择',
			btn_change:'更换',
			droppable:false,
			onchange:null,
			thumbnail:false, //| true | large
			//whitelist:'gif|png|jpg|jpeg'
			blacklist:'exe|rar'
			//onchange:''
			//
		});
		$('#project').validate({
			rules:{
				title: {
			        required: true,
			        maxlength: 20
			      },
			    titleEn:{
			    	required: true,
				    maxlength: 50,
				    checkTitleEn:true,
				    remote: {
				        url: "/admin/project/checkTitleEnUnique.do",     //后台处理程序
				        type: "post",               //数据发送方式
				        dataType: "json",           //接受数据格式   
				        data: {                     //要传递的数据
				            titleEn: function() {
				                return $("#titleEn").val();
				            }
				        }
				    }
			    },
			    sourceFrom:{
			    	required:true
			    }
			},
			messages:{
				titleEn:{
					remote:'该路径已被使用,请使用其他路径名称.'
				}
			},
		  submitHandler: function(form) {
			  uploadProject(form);
		  }
		});
	</script>
</body>
</html>