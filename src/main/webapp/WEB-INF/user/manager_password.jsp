<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
			<jsp:param name="menuName" value="home" />
		</jsp:include>
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><a href="/admin/index.do">首页</a></li>
					<li class="active">修改密码</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						修改密码<small> <i class="ace-icon fa fa-angle-double-right"></i>
							管理员修改密码
						</small>
					</h1>
				</div>
				<div class="col-sm-12">
				<form id="managerForm" class="form-horizontal"
					role="form" action="<%=request.getContextPath() %>/admin/user/updatePassword.do" method="POST">
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">原密码</label>
						<div class="col-sm-4">
							<input class="form-control" name="passwordOriginal"  type="password" >
							<c:if test="${not empty success && !success }">
								<label id="password-error" class="error" for="password">${errMsg }</label>							
							</c:if>
						</div>
						<div class="col-sm-6 help-block">请输入原始密码</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">新密码</label>
						<div class="col-sm-4">
							<input class="form-control" name="password"  type="password" id="password"
								  >
						</div>
						<div class="col-sm-6 help-block">请设置6~20位密码,至少要包含一位数字和一位英文字符</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right"
							for="form-field-2">确认新密码</label>
						<div class="col-sm-4">
							<input class="form-control" name="password2"  type="password">
						</div>
						<div class="col-sm-6 help-block">请重复输入密码</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-2">
							<button type="submit"  class="btn-success btn btn-sm">修改</button>
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
		var success='${success}';
		if(success=='true'){
			alert(errMsg);
			window.location.href='/admin/index.do';
		}
		$('#managerForm').validate({
			rules:{
				passwordOriginal: {
					required: true,
				    leaglePassword:true
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
				passwordOriginal:{
					required:'请输入原始密码',
					leaglePassword:'请设置6~20位密码,至少要包含一位数字和一位英文字符'
				},
				password:{
					required:'请输入新密码',
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