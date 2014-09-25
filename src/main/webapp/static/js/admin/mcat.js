
$unique.admin.mcat = {};

$unique.admin.mcat.open = function(){
	
}

/**
 * 编辑分类名称
 */
$unique.admin.mcat.edit = function(obj){
	var td = $(obj).parents('tr').find('td:eq(1)');
	td.find('span').hide();
	td.find('div').show();
}
/**
 * 修改分类名称
 */
$unique.admin.mcat.save = function(id, obj){
	var name = $(obj).parent('div').find('input:eq(0)').val();
	var url = $unique.base + '/admin/mcat/save';
	var param = {id : id, name : name};
	$.post(url, param, function(data) {
		if(data){
			if(data === 'success'){
				$unique.alert('保存成功！');
				window.location.href = $unique.base + '/admin/mcat';
			} else{
				$unique.alert('保存失败！');
			}
		}
	},'text');
}