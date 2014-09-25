
$(function() {
	
});

$unique.admin.user = {};
/**
 * 保存用户
 */
$('#save_user_form').validator({
    rules: {
    	mobile: [/^1[3458]\d{9}$/, '请检查手机号格式']
    },
    fields: {
    	nickname: 'required; length[1~20]',
    	space_size : 'length[1~50]; integer[+];'
    },
    //自定义用于当前实例的消息
    messages: {
        required: "请填写{0}"
    },
    valid: function(form){
    	var url = $unique.base + '/admin/users/save';
		var param = $(form).serializeArray();
		$.post(url, param, function(data) {
			if(data){
				if(data === 'success'){
					$unique.alert('保存成功！');
					window.location.href = $unique.base + '/admin/users';
				} else{
					$unique.alert('保存失败！');
				}
			}
		},'text');
		
    }
});
/**
 * 删除用户
 */
$unique.admin.user.del = function(uid){
	art.dialog({
	    lock: true,
	    content: '确定删除该用户吗',
	    icon: 'error',
	    ok: function () {
	    	var url = $unique.base + '/admin/users/del';
			var param = { uid : uid };
			$.post(url, param, function(data) {
				if(data && data ==='success'){
					$unique.alert('删除成功！');
					window.location.reload();
				} else{
					$unique.alert('删除失败！');
				}
			},'text');
	    },
	    cancel: true
	});
}
