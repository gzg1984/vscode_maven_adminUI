/**
 * 
 */
//代码页返回顶部
window.onscroll = function() {
	var t = document.documentElement.scrollTop || document.body.scrollTop;
	if (t >= 50) {
		document.getElementById("backToTop").style.display = "block";
	} else {
		document.getElementById("backToTop").style.display = "none";
	}
}
//返回顶部点击事件
$('#backToTop').click(function(){
	  $("body,html").animate({scrollTop:0},500);
});
PR.prettyPrint(); //初始化prettify
$(document).ready(function(){
	 
	//通用剪贴板
	
	 //代码行内关键字点击搜索
	 $('.prettyprint').on('click','span.keyword',function(){
		 window.location.href=contextPath+'/query/'+project_id+'?keyword='+$(this).text();
	 });
//	 alert('分享地址已经复制到您的剪贴板中！');
	 //获取该文件的所有备注
	 var lineHash=window.location.hash;
	 if(lineHash&&lineHash!=''){
		 var arr=window.location.hash.substr(2).split(',');
		 currLineNum=arr[0]-1;
		 var offsetTop=$('.prettyprint').find('li:eq('+(arr[0]-1)+')').offset().top;
		 $("body,html").animate({scrollTop:offsetTop},500);
		 scopeCommentId=arr[1];
		 $('.prettyprint ol>li:eq('+currLineNum+')').addClass('active');
		 $('#lineNumber').val(currLineNum);
	 }
	 
	 var lineScoped=false;
	 if(scopeCommentId&&scopeCommentId!=''){
		 getLineComment(currLineNum,scopeCommentId);
		 lineScoped=true;
	 }
//	 }else{
		 $.get(contextPath+'/comment/get/'+file_id,{},function(res){
//			$('#commentRepeat').empty();
			if(res.length>0){ //如果有备注,默认选择有备注的第一行
				var firstLineNumber=-1;
				var firlstData=[];
				for(var i=0;i<res.length;i++){
					var line=parseInt(res[i].lineNumber);
					$('.prettyprint ol>li:eq('+line+')').addClass('hasComment');
					if(!lineScoped){
						if(firstLineNumber<0){
							firstLineNumber=line;
						}
						if(firstLineNumber>line){
							firstLineNumber=line;
							firlstData=[];
						}
						if(firstLineNumber==line){
							firlstData.push(res[i]);
						}
					}
				}
				if(!lineScoped){
					$('#commentRepeat').empty();
					$('#commentRepeat').append(getCommentHtml(firlstData));
					$('.prettyprint ol>li:eq('+firstLineNumber+')').addClass('active');
					$('#lineNumber').val(firstLineNumber);
				}
			}else{ //如果当前页面没有备注时,输入按钮禁用
				$('#commentForm').find('button').prop('disabled',true);
			}
		 },'json');
//	 }
	 
	//添加和取消收藏
	 $('.add-fav').click(function(){
		var favIcon=$(this);
		if(!favIcon.hasClass('active')){
			$.get(contextPath+'/fav/'+project_id,{},function(res){
				if(res.success){
					favIcon.addClass('active');
				}else{
					alert(res.msg);
				}
			},'json');
		}
		else{
			$.get(contextPath+'/fav/cancel/'+project_id,{},function(res){
				if(res.success){
					favIcon.removeClass('active');
				}else{
					alert(res.msg);
				}
			},'json');
		}
	 });
	 
	 //代码行被选中时的事件
	 $('.prettyprint').on('click','ol li',function(){
	 	$(this).siblings().removeClass('active');
	 	$(this).addClass('active');
	 	var lineNumber=$(this).index();
	 	$('#lineNumber').val(lineNumber);
	 	getLineComment(lineNumber);
	 	$('#commentForm').find('button').prop('disabled',false);
	 	
	 });
	 var searchHighlight=$('.prettyprint').find('li').find('span.keyword.highlight');
	 if(searchHighlight.length>0){
		 var offsetTop=$(searchHighlight).closest('li').offset().top;
		 $("body,html").animate({scrollTop:offsetTop},500);
	 }
	 
});
