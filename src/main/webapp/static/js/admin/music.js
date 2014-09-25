/**
 * 全局music
 */
$unique.admin.music = {};
$(function() {

	$("#uploadify").uploadify({
		'uploader' : $unique.base + '/upload/temp_file',
		'swf' : $unique.cdn + '/static/uploadify/uploadify.swf',
		'cancelImg' : $unique.cdn + '/static/uploadify/uploadify-cancel.png',
		'queueID' : 'fileQueue',// 与下面的id对应
		'queueSizeLimit' : 10,
		'fileExt' : '*.mp3',
		'fileTypeDesc' : '*.mp3',
		'fileExt' : '*.mp3',
		'method' : 'get',
		'auto' : true,
		'multi' : false,
		'simUploadLimit' : 1,
		'buttonText' : '选择音乐文件',
		'onUploadSuccess' : function(file, data, response) {// 上传完成时触发（每个文件触发一次）
			if (data) {
				data = eval("(" + data + ")");
				$('#upload_music_form').find('input[name="song_path"]')
						.val(data.save_path).attr('readonly', true);
				$('#upload_music_form').find('input[name="song"]').val(data.file_name);
				$('#uploadify').val('');
			}
		}
	});
	
	$("#uploadify2").uploadify({
		'uploader' : $unique.base + '/upload/temp_file',
		'swf' : $unique.cdn + '/static/uploadify/uploadify.swf',
		'cancelImg' : $unique.cdn + '/static/uploadify/uploadify-cancel.png',
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
				$('#upload_music_form').find('input[name="cover_path"]')
						.val(data.save_path).attr('readonly', true);
				$('#uploadify2').val('');
			}
		}
	});
	
	$('#upload_music_form').validator({
	    rules: {
	    	mobile: [/^1[3458]\d{9}$/, '请检查手机号格式']
	    },
	    fields: {
	    	song: 'required; length[1~50]',
	    	singer : 'length[1~50]',
	    	introduce : 'length[1~200]',
	    	song_path : 'required; ',
	    	cover_path : 'required; '
	    },
	    //自定义用于当前实例的消息
	    messages: {
	        required: "请填写{0}"
	    },
	    valid: function(form){
	    	var cids = [];
	    	$(form).find('button[name="cidbtn"][ok="1"]').each(function(k, v){
	    		cids.push($(v).attr('cid'));
	    	});
	    	$(form).find('#cids').val(cids.join(','));
	    	
	    	var url = $unique.base + '/admin/music/save';
			var param = $(form).serializeArray();
			$.post(url, param, function(data) {
				if(data){
					if(data === 'success'){
						$unique.alert('保存成功！');
						window.location.href = $unique.base + '/admin/music';
					} else{
						$unique.alert('保存失败！');
					}
				}
			});
			
	    }
	});
});

$unique.admin.music.tabok = function(obj){
	var ok = $(obj).attr('ok');
	if(ok === '1'){
		$(obj).attr('ok', '0');
	} else{
		$(obj).attr('ok', '1');
	}
	$(obj).find('i').toggle();
}
/**
 * 删除音乐
 */
$unique.admin.music.del = function(mid){
	art.dialog({
	    lock: true,
	    content: '确定删除该音乐吗',
	    icon: 'error',
	    ok: function () {
	    	var url = $unique.base + '/admin/music/del';
			var param = { mid : mid };
			$.post(url, param, function(data) {
				if(data && data === 'success'){
					$unique.alert('删除成功！');
					window.location.href = $unique.base + '/admin/music';
				} else{
					$unique.alert('删除失败！');
				}
			},'text');
	    },
	    cancel: true
	});
}

/**
 * 编辑音乐
 */
$unique.admin.music.edit = function(mid){}