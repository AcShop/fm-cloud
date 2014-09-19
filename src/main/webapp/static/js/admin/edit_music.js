$(function() {
	$('#submit_btn').click(function() {
		var url = $unique.base + "/admin/music/save";
		var paramter = $('#upload_music_form')
				.serializeArray();
		$.post(url, paramter, function(data) {
			if (data == 'success') {
				alert('保存成功！');
			} else if (data == 'error') {
				alert('保存失败！');
			}
		}, "text");
	});

	$("#uploadify").uploadify({
		'uploader' : $unique.base + '/upload/temp_file',
		'swf' : $unique.cdn + '/static/uploadify/uploadify.swf',
		'cancelImg' : $unique.cdn + '/static/uploadify/uploadify-cancel.png',
		'queueID' : 'fileQueue',// 与下面的id对应
		'queueSizeLimit' : 5,
		'fileintroduce' : 'mp3文件',
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
});