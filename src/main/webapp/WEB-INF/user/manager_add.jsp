<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/admin/ace_head.jsp"%>
<title>管理后台</title>
</head>
<body class="no-skin">
	<%@ include file="/common/admin/nav.jsp"%>
	<div class="main-container" id="main-container">
		<jsp:include page="/getMenu.do" flush="true">
			<jsp:param name="menuName" value="project-upload" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li><a href="#">用户管理</a></li>
					<li class="active">新增管理员</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						新增管理员<small> <i class="ace-icon fa fa-angle-double-right"></i>
							新增管理员用户
						</small>
					</h1>
				</div>
				<div class="col-sm-12">
				<form id="managerForm" class="form-horizontal"
					role="form" action="<%=request.getContextPath() %>/admin/user/addManager.do" method="POST">
					<input type="hidden" id="adminId" name="adminId"
						value="${admin.adminId }">
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">管理员账号</label>
						<div class="col-sm-4">
							<input class="form-control" name="username" id="username" placeholder="请输入账号" type="text"
								 value="${admin.username }">
						</div>
						<div class="col-sm-6 help-block">请输入8~16位账号</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">登录密码</label>
						<div class="col-sm-4">
							<input class="form-control" name="password"  type="password" id="password"
								  value="${admin.password }">
						</div>
						<div class="col-sm-6 help-block">请设置6~20位密码,至少要包含一位数字和一位英文字符</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">确认密码</label>
						<div class="col-sm-4">
							<input class="form-control" name="password2"  type="password"
								  value="${admin.password }">
						</div>
						<div class="col-sm-6 help-block">请重复输入密码</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">超级管理员</label>
						<div class="col-sm-4">
							<label class="radio-inline"><input type="radio" name="isSuper" value="1">是</label>
							<label class="radio-inline"><input type="radio" name="isSuper" checked value="0">否</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="submit"  class="btn-success btn btn-sm">保存</button>
						</div>
						<div class="col-sm-4 ">
							<a href="<%=request.getContextPath() %>/admin/user/manager.do" class="btn-default btn btn-sm">返回</a>
						</div>
					</div>
					
				</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/common/admin/script.jsp"%>
	<script type="text/javascript">
		
		(function($ , undefined) {
			$.validator.addMethod("leaglePassword",function(value,element,params){  
		        var checkTitleEn =/^(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}$/;  
		        return checkTitleEn.test(value);  
		    },"密码不符合要求"); 
		})(window.jQuery);
		
		var errMsg='${errMsg}';
		if(errMsg!=''){
			alert(errMsg);
			window.location.href='/admin/user/manager.do';
		}
		$('#managerForm').validate({
			rules:{
				username: {
			        required: true,
			        maxlength: 16,
			        minlength:8,
			        remote: {
				        url: "/admin/user/checkAdminUsernameUnique.do",     //后台处理程序
				        type: "post",               //数据发送方式
				        dataType: "json",           //接受数据格式   
				        data: {                     //要传递的数据
				            username: function() {
				                return $("#username").val();
				            },
				        }
				    }
			      },
			    password:{
			    	required: true,
				    leaglePassword:true
			    },
			    password2:{
			    	required:true,
			    	equalTo:'#password'
			    }
			},
			messages:{
				username:{
					remote:'该账号已被使用,请使用其他账号.',
					required:'请输入账号',
					maxlength:'账号长度不得超过16位',
					minlength:'账号长度不得小于8位'
				},
				password:{
					required:'请输入密码',
					leaglePassword:'请设置6~20位密码,至少要包含一位数字和一位英文字符'
				},
				password2:{
					required:'请重复输入密码',
			    	equalTo:'两次输入的密码不一致'
				}
			},
		  submitHandler: function(form) {
			 form.submit();
		  }
		});
	</script>
</body>
</html>