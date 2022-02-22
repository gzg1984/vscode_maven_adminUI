/**
 * 
 */
/*		
 * 根据行号获取所有备注内容
 * @param lineNumber 所选择的行号
 */
function getLineComment(lineNumber,scopeId) {
	$.get(contextPath + '/comment/get/' + file_id + '/' + lineNumber, {},
			function(res) {
				$('#commentRepeat').empty();
				if (res.length > 0) {
					$('#commentRepeat').append(getCommentHtml(res,scopeId));
				}

			}, 'json');
}
/*
 * 根据数组类型自动判断,
 * 调用getCommentTemplate方法生成html
 */
function getCommentHtml(data,scopeId) {
	var html = "";
	if (data instanceof Array) {
		var collapsed = false;
		var scopeHtml="";
		for (var i = 0; i < data.length; i++) {
			var show = true;
			if (i > 3) { //如果备注数量超过3个,则剩下的折叠
				show = false;
				if (!collapsed) {
					collapsed = true;
				}
			}
			if(scopeId&&scopeId!=''&&scopeId==data[i].commentId){
				scopeHtml=getCommentTemplate(data[i], show,true);
			}else{
				html += getCommentTemplate(data[i], show);
			}
		}
		if (collapsed) { //需要折叠,添加展开按钮
			html += '<div class="comment-box expandable "><div class="pad-10 text-center">';
			html += '<span class="glyphicon glyphicon-chevron-down"></span></div></div>';
		}
		if(scopeHtml!=''){
			html=scopeHtml+html;
		}
	} else {
		html += getCommentTemplate(data, true);
	}
	return html;
}

/**
 * 用数据对象object 渲染html内容,
 * @param obj 要渲染的数据对象
 * @param show 如果为true, 则显示该备注,false则折叠
 */
function getCommentTemplate(obj, show,scoped) {
	var html = '';
	if(scoped){
		html = '<div class="comment-box scope">';
	}
	else if (!show) {
		html = '<div class="comment-box hidden-item hide">';
	} else {
		html = '<div class="comment-box">';
	}
	html += '<div class="box-head">';
	html += '<span class="avatar-wrapper pull-left"> ';
	html += '<img src="' + uploadPath + '/' + obj.user.avatarUrl + '">';
	html += '</span>';
	if (obj.user.nickname && obj.user.nickname != '') {
		html += '<p class="font-12 pad-20-right"><a href="'+contextPath+'/user/u/'+obj.user.userId+'">' + obj.user.nickname + '</a></p>';
	} else {
		html += '<p class="font-12 pad-20-right"><a href="'+contextPath+'/user/u/'+obj.user.userId+'">无名氏</a></p>';
	}
	html += '<p class="gray mar-0-bottom font-10 pad-20-right">' + obj.user.signature
			+ '</p>';
	html += '<i class="share fa fa-share-square-o pull-right" data-id="'+obj.commentId+'" data-line="'+obj.lineNumber+'"></i>';
	html += '</div>';
	html += '<div class="pad-10 gray">' + obj.content + '</div>';
	html += '<div class="box-footer pad-10 clearfix">';
	html += '<span class="avatar-wrapper pull-left"> ';
	html += '<img src="' + uploadPath + '/' + obj.user.avatarUrl + '">';
	html += '</span>';
	html += '<span class="date black pull-left">' + obj.relativeTime
			+ '</span>';
	if (obj.userHasThumbs == 0) {
		html += '<span class="pull-right thumbs-up gray " data-id="'
				+ obj.commentId + '"><span class="text">点赞</span><span';
	} else {
		html += '<span class="pull-right thumbs-up gray up" data-id="'
				+ obj.commentId + '"><span class="text">已赞</span><span';
	}
	html += ' class="num">' + obj.thumbsCount + '</span></span>';
	html += '</div>';
	html += '</div>';
	return html;
}
$('#commentForm').validate({
	rules : {
		content : {
			required : true,
			maxlength:200
		}
	},
	messages : {
		content : {
			required : '请输入备注内容！',
			maxlength: '请输入少于200字的内容!'
		
		}
	},
	submitHandler : function(form) {
		var params=$(form).serializeObject();
		$.post(contextPath+'/comment/add',params,function(res){
			if(res.success){
				var html=getCommentTemplate(res.comment,true);
				$('#commentRepeat').append(html);
				$(form).find('textarea').val('');
				$('.prettyprint ol>li:eq('+res.comment.lineNumber+')').addClass('hasComment');
				
			}else{
				alert(res.msg);
			}
		},'json');
		
	}
});

//展开被折叠的备注
$('#commentRepeat').on('click','.expandable',function(){
	$(this).toggleClass('open');
	if($(this).hasClass('open')){
		 $('#commentRepeat').find('.hidden-item').removeClass('hide');
	}else{
		 $('#commentRepeat').find('.hidden-item').addClass('hide');
	}
});
//点击底部头像展开备注者的全部信息
$('#commentRepeat').on('click','.box-footer .avatar-wrapper',function(){
	 $(this).closest('.comment-box').addClass('expanded');
});
//点击顶部头像收缩备注者的个人信息
$('#commentRepeat').on('click','.box-head .avatar-wrapper',function(){
	 $(this).closest('.comment-box').removeClass('expanded');
});
$('#commentRepeat').on('click','.box-head .share',function(){
	var commentId=$(this).data('id');
	var lineNumber=$(this).data('line');
	var url=window.location.protocol+'//'+window.location.hostname+':'+window.location.port+window.location.pathname;
    url=url+"#L"+(parseInt(lineNumber)+1)+","+commentId;
    $('#shareClipboard').val(url);
    $('#copyBtn').click();
});
//备注点赞
$('#commentRepeat').on('click','.thumbs-up',function(){
	if($(this).hasClass('up')){
		alert('已经赞过了');
		return;
	}
	var commentId=$(this).data('id');
	var thumbsDiv=$(this);
	$.post(contextPath+'/comment/thumbsup/'+commentId,{},function(res){
		if(res.success){
			thumbsDiv.addClass('up');
			var thumbsCount=parseInt(thumbsDiv.find('.num').text());
			thumbsDiv.find('.num').text(++thumbsCount);
			thumbsDiv.find('.text').text('已赞');
		}else{
			alert(res.msg);
		}
	},'json');
});