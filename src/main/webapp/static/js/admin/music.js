/**
 * 全局music
 */
$unique.admin.music = {};
/**
 * 删除音乐
 */
$unique.admin.music.del = function(mid) {
	art.dialog({
		lock : true,
		content : '确定删除该音乐吗',
		icon : 'error',
		ok : function() {
			var url = $unique.base + '/admin/music/del';
			var param = {
				mid : mid
			};
			$.post(url, param, function(data) {
				if (data && data === 'success') {
					$unique.alert('删除成功！');
					window.location.href = $unique.base + '/admin/music';
				} else {
					$unique.alert('删除失败！');
				}
			}, 'text');
		},
		cancel : true
	});
}
