/**
 * 
 */
(function($ , undefined) {
	
	$.fn.extend({
		serializeObject: function() {
			if (this.length > 1) {
				return false;
			}
			var arr = this.serializeArray();
			var obj = new Object;
			$.each(arr, function(k, v) {
				obj[v.name] = v.value;
			});
			return obj;
		}
	});
	
	
	var activeLi = $('.gui-nav ul.nav-list').find('li.active');
	var closestLi = $(activeLi).parent().closest('li');
	$(activeLi).closest('ul').addClass('nav-show').show();
	$(closestLi).addClass('open');
	$(closestLi).parent().closest('li').addClass('open');
	$.extend({gui_datagrid_formatter:function(value,text,cssClass,callback){
		return '<button type="button" class="'+cssClass+'" onclick="'+callback+'(\''+value+'\')">'+text+'</button>';
	}});
	$.fn.extend({
		gui_datagrid:function(options,params){
			var gui_grid=$(this);
			var grid_table=gui_grid.find('.gui-gridtable');
			var getFirstSelected=function(row){
				
				if(row instanceof Array){
					if(row.length>0){
						return row[0];
					}
					else{
						return null;
					}
				}else if(row instanceof Object){
					return row;
				}
			};
			if(typeof  options =='object'){ //初始化
				var statement_id=gui_grid.find('.gui-gridtable').data('statement');
				var tableDefaultOps={rownumbers : true,
					fitCloumns:true,
					singleSelect : true,
					method : 'get',
					toolbar : '#tb',
					footer:'#ft',
					selectOnCheck:false,
					checkOnSelect:false,
					pagination : true,
					url:'/baseQuery/queryBeans.do?statementId='+statement_id,
					emptyMsg:'没有查询到任何数据'
				}
//				$.extend(tableDefaultOps,datagrid_options);
				grid_table.datagrid(tableDefaultOps);
				gui_grid.find('[data-role="search"]').click(function(){
					var params=gui_grid.find('[data-role="filter"]').serializeObject();
					grid_table.datagrid('reload',params);
				});
				gui_grid.find('[data-role="edit"]').click(function(){
					var row=grid_table.datagrid('getSelected');
					if(!row){
						row=grid_table.datagrid('getChecked');
					}
					var selected=getFirstSelected(row);
					if(selected){
						options.onEdit(row);
					}else{
						alert('请选择要编辑的行!');
					}
				});
				gui_grid.find('[data-role="delete"]').click(function(){
					var row=grid_table.datagrid('getSelected');
					if(!row){
						row=grid_table.datagrid('getChecked');
					}
					if(row instanceof Array){
						if(row.length>0){
							if(confirm('确定要删除吗?')){
								options.onDelete(row);
							}
						}
						else{
							alert('请选择要删除的数据!');
						}
					}else if(row instanceof Object){
						if(row){
							if(confirm('确定要删除吗?')){
								options.onDelete(row);
							}
						}
						else{
							alert('请选择要删除的数据!');
						}
					}
				});
			}else if(typeof options =='string'){
				grid_table.datagrid(options,params);
			}
			
		},
		
	});
	
})(window.jQuery);