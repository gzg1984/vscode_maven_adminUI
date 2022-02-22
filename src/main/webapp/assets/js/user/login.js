(function($ , undefined) {
	$.validator.addMethod("leaglePassword",function(value,element,params){  
        var checkTitleEn =/^(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}$/;  
        return checkTitleEn.test(value);  
    },"密码不符合要求"); 
})(window.jQuery);

$('#loginForm').validate({
	rules : {
		username : {
			required : true
		},
		password : {
			required : true
		}
	},
	messages : {
		username : {
			required : '请输入账号！'
		},
		password : {
			required : '密码不能为空！'
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});

$('#registerForm').validate({
	rules : {
		username : {
			required : true,
			minlength: 8,
			maxlength:16
		},
		password : {
			required : true,
			leaglePassword:true
		},
		passwordConfirm:{
	    	required:true,
	    	equalTo:'#password'
	    },
		email:{
			required:true,
			email:true
		}
	},
	messages : {
		username : {
			required : '请输入账号！',
			minlength:'长度应大于8位',
			maxlength:'长度不得超过16位'
				
		},
		password : {
			required : '密码不能为空！',
			
		},
		passwordConfirm:{
			equalTo:'两次密码输入不一致'
		},email:{
			required:'邮箱地址不能为空!',
			email:'请输入正确的邮箱格式'
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});

$('#infoForm').validate({
	rules : {
		email : {
			required : true,
			email:true
		},
		nickname : {
			minlength: 2,
			maxlength:12
		}
	},
	messages : {
		nickname : {
			minlength : '请输入2个字符以上的昵称',
			maxlength:'昵称不能超过12个字符'
		},email:{
			required:'邮箱地址不能为空!',
			email:'请输入正确的邮箱格式'
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});
$('#reset').validate({
	rules : {
		password : {
			required : true,
			leaglePassword:true
		},
		passwordConfirm:{
	    	required:true,
	    	equalTo:'#password'
	    }
	},
	messages : {
		
		password : {
			required : '密码不能为空！'
		}
		,passwordConfirm:{
			required:'请确认新密码!',
			equalTo:'两次密码输入不一致'
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});