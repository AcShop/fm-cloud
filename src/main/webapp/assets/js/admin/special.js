/**
 * 全局music
 */
$unique.admin.special = {};
$(function() {

	$("#uploadify").uploadify({
		'uploader' : $unique.base + '/upload/temp_file',
		'swf' : $unique.cdn + '/assets/uploadify/uploadify.swf',
		'cancelImg' : $unique.cdn + '/assets/uploadify/uploadify-cancel.png',
		'queueID' : 'fileQueue',// 与下面的id对应
		'queueSizeLimit' : 10,
		'fileSizeLimit' : '5MB',
		'fileExt' : '*.jpg;*.jpeg;*.png;',
		'fileTypeDesc' : '*.jpeg; *.jpg; *.png',
		'method' : 'get',
		'auto' : true,
		'multi' : false,
		'simUploadLimit' : 1,
		'buttonText' : '选择图片文件',
		'onUploadSuccess' : function(file, data, response) {// 上传完成时触发（每个文件触发一次）
			if (data) {
				data = eval("(" + data + ")");
				$('#upload_special_form').find('input[name="cover_small"]')
						.val(data.save_path).attr('readonly', true);
				$('#uploadify').val('');
			}
		}
	});
	
	$("#uploadify2").uploadify({
		'uploader' : $unique.base + '/upload/temp_file',
		'swf' : $unique.cdn + '/assets/uploadify/uploadify.swf',
		'cancelImg' : $unique.cdn + '/assets/uploadify/uploadify-cancel.png',
		'queueID' : 'fileQueue2',// 与下面的id对应
		'queueSizeLimit' : 10,
		'fileSizeLimit' : '5MB',
		'fileExt' : '*.jpg;*.jpeg;*.png;',
		'fileTypeDesc' : '*.jpeg; *.jpg; *.png',
		'method' : 'get',
		'auto' : true,
		'multi' : false,
		'simUploadLimit' : 1,
		'buttonText' : '选择图片文件',
		'onUploadSuccess' : function(file, data, response) {// 上传完成时触发（每个文件触发一次）
			if (data) {
				data = eval("(" + data + ")");
				$('#upload_special_form').find('input[name="cover_pic"]')
						.val(data.save_path).attr('readonly', true);
				$('#uploadify2').val('');
			}
		}
	});
	
	$('#upload_special_form').validator({
	    rules: {
	    	mobile: [/^1[3458]\d{9}$/, '请检查手机号格式']
	    },
	    display: function(el){
	        return el.getAttribute('placeholder') || '';
	    },
	    fields: {
	    	title: 'required; length[1~50]',
	    	introduce : 'required;; length[1~200]',
	    	cover_small : 'required; ',
	    	cover_pic : 'required; '
	    },
	    valid: function(form){
	    	var url = $unique.base + '/admin/special/save';
			var param = $(form).serializeArray();
			$.post(url, param, function(data) {
				if(data){
					if(data === 'success'){
						$unique.alert('保存成功！');
						window.location.href = $unique.base + '/admin/special';
					} else{
						$unique.alert('保存失败！');
					}
				}
			});
			
	    }
	});
});


/**
 * 禁用专辑
 */
$unique.admin.special.enable = function(sid, status){
	if(status == 1){
		var url = $unique.base + '/admin/special/enable';
		var param = { sid : sid, status : status };
		$.post(url, param, function(data) {
			if(data && data === 'success'){
				$unique.alert('禁用成功！');
				window.location.href = $unique.base + '/admin/special';
			} else{
				$unique.alert('禁用失败！');
			}
		},'text');
	} else{
		art.dialog({
		    lock: true,
		    content: '确定禁用该专辑吗',
		    icon: 'error',
		    ok: function () {
		    	var url = $unique.base + '/admin/special/enable';
				var param = { sid : sid, status : status };
				$.post(url, param, function(data) {
					if(data && data === 'success'){
						$unique.alert('禁用成功！');
						window.location.href = $unique.base + '/admin/special';
					} else{
						$unique.alert('禁用失败！');
					}
				},'text');
		    },
		    cancel: true
		});
	}
}
