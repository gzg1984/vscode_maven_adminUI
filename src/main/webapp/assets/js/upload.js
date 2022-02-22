/**
 * 
 */
(function($ , undefined) {
	$.validator.addMethod("checkTitleEn",function(value,element,params){  
        var checkTitleEn =/^[A-Za-z0-9_\-]+$/;  
        return checkTitleEn.test(value);  
    },"项目存储路径不符合规则"); 
})(window.jQuery);
function checkFileExt(filename) {
	var flag = false; // 状态
	var arr = [ "jpg", "png", "gif" ];
	// 取出上传文件的扩展名
	var index = filename.lastIndexOf(".");
	var ext = filename.substr(index + 1);
	// 循环比较
	for (var i = 0; i < arr.length; i++) {
		if (ext == arr[i]) {
			flag = true; // 一旦找到合适的，立即退出循环
			break;
		}
	}
	// 条件判断
	if (flag) {
		document.write("文件名合法");
	} else {
		document.write("文件名不合法");
	}
}
function uploadProject(target){
	$('#progressBar').show();
	var params=$(target).serializeObject();
	var fileObj=$(target).find('input[type="file"]')[0].files[0];
	var FileController ='/admin/project/doUpload.do';
	var form = new FormData();
	form.append('zipfile',fileObj);
	form.append('title',params.title);
	form.append('titleEn',params.titleEn);
	form.append('desc',params.desc);
	form.append('sourceFrom',params.sourceFrom);
	var xhr = new XMLHttpRequest();  
    xhr.open("post", FileController, true);  
    xhr.onload = function () {  
        alert("上传完成!");
        location.href='/admin/project/list.do';
    };
    xhr.onerror=function(error){
    	console.debug(error);
    	alert("上传错误!");
    };
    // 实现进度条功能  
    xhr.upload.addEventListener("progress", progressFunction, false);  
    xhr.send(form);  
	
}
function progressFunction(evt) {  
    var progressBar = document.getElementById("progressBar");  
    var percentageDiv = document.getElementById("percentage");  
    if (evt.lengthComputable) {  
        progressBar.max = evt.total;  
        progressBar.value = evt.loaded;  
        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";  
    }  
}  