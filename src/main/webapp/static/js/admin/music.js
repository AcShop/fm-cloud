/**
 * 全局music
 */
$unique.admin.music = {};
$(function() {
});
/**
 * 删除音乐
 */
$unique.admin.music.del = function(mid){
	
	if (mid && downloadUrl) {
		var url = $unique.base + '/hit';
		var param = {
			mid : mid,
			type : 3
		}
		$.get(url, param, function(data) {
			window.open(downloadUrl);
		});
	}
}
/**
 * 编辑音乐
 */
$unique.admin.music.edit = function(mid){}