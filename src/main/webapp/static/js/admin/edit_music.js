/**
 * 全局music
 */
$unique.admin.music = {};
var lrc;
$(function() {

	$("#uploadify").uploadify(
			{
				'uploader' : $unique.base + '/upload/temp_file',
				'swf' : $unique.cdn + '/static/uploadify/uploadify.swf',
				'cancelImg' : $unique.cdn
						+ '/static/uploadify/uploadify-cancel.png',
				'queueID' : 'fileQueue',// 与下面的id对应
				'queueSizeLimit' : 10,
				'fileExt' : '*.mp3',
				'fileSizeLimit' : '5MB',
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
						$('#upload_music_form').find('input[name="song"]').val(
								data.file_name);
						$('#uploadify').val('');
					}
				}
			});

	$("#uploadify2").uploadify(
			{
				'uploader' : $unique.base + '/upload/temp_file',
				'swf' : $unique.cdn + '/static/uploadify/uploadify.swf',
				'cancelImg' : $unique.cdn
						+ '/static/uploadify/uploadify-cancel.png',
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
						$('#upload_music_form')
								.find('input[name="cover_path"]').val(
										data.save_path).attr('readonly', true);
						$('#uploadify2').val('');
					}
				}
			});

	$('#upload_music_form').validator({
		rules : {
			mobile : [ /^1[3458]\d{9}$/, '请检查手机号格式' ]
		},
		fields : {
			song : 'required; length[1~50]',
			singer : 'length[1~50]',
			introduce : 'length[1~200]',
			song_path : 'required; ',
			cover_path : 'required; '
		},
		// 自定义用于当前实例的消息
		messages : {
			required : "请填写{0}"
		},
		valid : function(form) {
			var cids = [];
			$(form).find('button[name="cidbtn"][ok="1"]').each(function(k, v) {
				cids.push($(v).attr('cid'));
			});
			$(form).find('#cids').val(cids.join(','));
			var url = $unique.base + '/admin/music/save';
			var param = $(form).serializeArray();
			$.post(url, param, function(data) {
				if (data) {
					if (data === 'success') {
						$unique.alert('保存成功！');
						window.location.href = $unique.base + '/admin/music';
					} else {
						$unique.alert('保存失败！');
					}
				}
			});

		}
	});

	lrc = UE.getEditor('lrc', {
		toolbars : [ [ 'anchor', // 锚点
		'bold', // 加粗
		'indent', // 首行缩进
		'italic', // 斜体
		'underline', // 下划线
		'strikethrough', // 删除线
		'subscript', // 下标
		'superscript', // 上标
		'formatmatch', // 格式刷
		'source', // 源代码
		'blockquote', // 引用
		'pasteplain', // 纯文本粘贴模式
		'selectall', // 全选
		'preview', // 预览
		'horizontal', // 分隔线
		'removeformat', // 清除格式
		'time', // 时间
		'date', // 日期
		'unlink', // 取消链接
		'deletecaption', // 删除表格标题
		'inserttitle', // 插入标题
		'fontfamily', // 字体
		'fontsize', // 字号
		'paragraph', // 段落格式
		'simpleupload', // 单图上传
		'link', // 超链接
		'emotion', // 表情
		'searchreplace', // 查询替换
		'justifyleft', // 居左对齐
		'justifyright', // 居右对齐
		'justifycenter', // 居中对齐
		'justifyjustify', // 两端对齐
		'forecolor', // 字体颜色
		'backcolor', // 背景色
		'insertorderedlist', // 有序列表
		'insertunorderedlist', // 无序列表
		'fullscreen', // 全屏
		'directionalityltr', // 从左向右输入
		'directionalityrtl', // 从右向左输入
		'pagebreak', // 分页
		'imagecenter', // 居中
		'wordimage', // 图片转存
		'lineheight', // 行间距
		'edittip ', // 编辑提示
		'customstyle', // 自定义标题
		'autotypeset', // 自动排版
		'background', // 背景
		'template', // 模板
		'inserttable', // 插入表格
		] ],
		autoHeightEnabled : true,
		autoFloatEnabled : true,
		initialFrameHeight : 300
	});
});

$unique.admin.music.tabok = function(obj) {
	var ok = $(obj).attr('ok');
	if (ok === '1') {
		$(obj).attr('ok', '0');
	} else {
		$(obj).attr('ok', '1');
	}
	$(obj).find('i').toggle();
}
