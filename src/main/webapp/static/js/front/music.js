/**
 * 全局music
 */
var cssSelector = {
	jPlayer : "#jquery_jplayer",
	cssSelectorAncestor : ".music-player"
};

var options = {
	swfPath : $unique.cdn + "/static/js/jq/Jplayer.swf",
	supplied : "ogv, m4v, oga, mp3"
};
var myPlaylist = null;
$unique.img = {};
$unique.img.loadind = function() {
	$("img").delayLoading({
		defaultImg : $unique.cdn + "/static/img/loading.jpg", // 预加载前显示的图片
		errorImg : "", // 读取图片错误时替换图片(默认：与defaultImg一样)
		imgSrcAttr : "originalSrc", // 记录图片路径的属性(默认：originalSrc，页面img的src属性也要替换为originalSrc)
		beforehand : 0, // 预先提前多少像素加载图片(默认：0)
		event : "scroll", // 触发加载图片事件(默认：scroll)
		duration : "normal", // 三种预定淡出(入)速度之一的字符串("slow", "normal", or
		// "fast")或表示动画时长的毫秒数值(如：1000),默认:"normal"
		container : window, // 对象加载的位置容器(默认：window)
		success : function(imgObj) {
		}, // 加载图片成功后的回调函数(默认：不执行任何操作)
		error : function(imgObj) {
		} // 加载图片失败后的回调函数(默认：不执行任何操作)
	});
}
$(function() {
	$unique.img.loadind();
	var playlist = [ {
		title : "Hidden",
		artist : "Miaow",
		mp3 : "http://jq22.qiniudn.com/i1.mp3",
		// oga:"http://www.jplayer.org/audio/ogg/Miaow-02-Hidden.ogg",
		poster : $unique.cdn + "/static/img/tumblr_mxp7a0v3fr1rqx86wo1_500.png"
	}, {
		title : "Cro Magnon Man",
		artist : "The Stark Palace",
		mp3 : "http://jq22.qiniudn.com/i2.mp3",
		// oga:"http://www.jplayer.org/audio/ogg/TSP-01-Cro_magnon_man.ogg",
		poster : $unique.cdn + "/static/img/tumblr_nbmb3j8nU51sq3g2zo1_500.png"
	} ];
	myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
	
	uParse('.lrc', {
	    rootPath: '../../ueditor'
	});
});

/**
 * 播放MP3
 * 
 * @param mid
 */
$unique.music.playerMp3 = function(mid) {
	if (mid) {
		var url = $unique.base + '/m/get_music';
		var param = {
			mid : mid
		};
		$.get(url, param, function(data) {
			if (data) {
				myPlaylist.setPlaylist([ {
					title : data.song,
					artist : data.singer,
					mp3 : data.mp3_url,
					poster : data.cover_url
				} ]);
				// 设置自动播放
				myPlaylist.option("autoPlay", true);
				myPlaylist.play(0);
				if(data.lrc && data.lrc != ''){
					$('#album-right').find('div.lrc').css('overflow-y', 'scroll').html(data.lrc);
				} else{
					$('#album-right').find('div.lrc').css('overflow','hidden').html('暂无歌词');
				}
			}
		}, 'json');
	}
}
/**
 * 喜欢
 * 
 * @param mid
 */
$unique.music.like = function(obj, mid) {
	var cls = $(obj).find('i').attr('class');
	if (mid && cls === 'icon-star-empty') {
		var url = $unique.root + '/m/hit';
		var param = {
			mid : mid,
			type : 1
		}
		// 点击+1
		$.get(url, param, function(data) {
			if (data && data === 'success') {
				var count = $('div#music-' + mid).find('span.like-count')
						.text();
				$('div#music-' + mid).find('span.like-count').text(
						parseInt(count) + 1);
				$(obj).find('i').removeClass('icon-star-empty').addClass(
						'icon-star');
			}
		});
	}
}
/**
 * 下载音乐
 * 
 * @param url
 */
$unique.music.download = function(mid, downloadUrl) {
	if (mid && downloadUrl) {
		var url = $unique.root + '/m/hit';
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
 * 标签播放
 */
$unique.music.tagclick = function(tag_type) {
	var url = '';
	// 随机播放
	if (tag_type == 0) {
		url = $unique.base + '/m/random';
		$.get(url, null, function(data) {
			if (data) {
				var playList = [];
				for (m in data) {
					var p = {
						title : data[m].song,
						artist : data[m].singer,
						mp3 : data[m].mp3_url,
						poster : data[m].cover_url
					};
					playList.push(p);
				}
				myPlaylist.setPlaylist(playList);
				// 设置自动播放
				myPlaylist.option("autoPlay", true);
				myPlaylist.play(0);
			}
		});
	}
	// 热门曲目
	if (tag_type == 1) {
		url = $unique.base + '/m/hot';
		$
				.get(
						url,
						null,
						function(r) {
							if (r) {
								var musicList = [];
								var data = r.results;
								for (m in data) {
									var mid = data[m].id;
									var cover_url = (null == data[m].cover_url || data[m].cover_url === '') ? './static/img/music/default-music.jpg'
											: data[m].cover_path;
									var introduce = (data[m].introduce && data[m].introduce != '') ? data[m].introduce
											: data[m].song;
									var inner = '	<div class="col-md-4 col-sm-6 col-lg-3 music" id="m-'
											+ mid
											+ '">'
											+ '			<div class="img-desc">'
											+ '				<img class="img-rounded" onclick="$unique.music.playerMp3('
											+ mid
											+ ')" title="'
											+ data[m].song
											+ '" originalSrc="'
											+ cover_url
											+ '">'
											+ '				<cite>'
											+ data[m].song
											+ '</cite>'
											+ '			</div>'
											+ '			<div class="card-heading">'
											+ '					<div class="profile" style="margin-top: 8px;">'
											+ '					<span title="有'
											+ data[m].like_count
											+ '人喜欢这首歌" onclick="$unique.music.like(this,'
											+ mid
											+ ')"><i class="icon-heart-empty"></i> <span class="like-count">1</span></span>&nbsp;&nbsp;'
											+ '				<span title="下载：'
											+ data[m].song
											+ '" onclick="$unique.music.download(\''
											+ mid
											+ ', '
											+ data[m].mp3_url
											+ '\')"><i class="icon-arrow-down"></i> 下载</span>&nbsp;&nbsp;'
											+ '				<span><i class="icon-time"></i> '
											+ data[m].date_zh
											+ '</span>'
											+ '					</div>'
											+ '				</div>'
											+ '			</div>';

									musicList.push(inner);
								}
								$('#music-list').html(musicList);
								$unique.img.loadind();
							}
						});
	}
	// 最新曲目
	if (tag_type == 2) {
		url = $unique.base + '/m/new_music';
		$
				.get(
						url,
						null,
						function(r) {
							if (r) {
								var musicList = [];
								var data = r.results;
								for (m in data) {
									var mid = data[m].id;
									var cover_url = (null == data[m].cover_url || data[m].cover_url === '') ? './static/img/music/default-music.jpg'
											: data[m].cover_path;
									var introduce = (data[m].introduce && data[m].introduce != '') ? data[m].introduce
											: data[m].song;
									var inner = '	<div class="col-md-4 col-sm-6 col-lg-3 music" id="m-'
											+ mid
											+ '">'
											+ '			<div class="img-desc">'
											+ '				<img class="img-rounded" onclick="$unique.music.playerMp3('
											+ mid
											+ ')" title="'
											+ data[m].song
											+ '" originalSrc="'
											+ cover_url
											+ '">'
											+ '				<cite>'
											+ data[m].song
											+ '</cite>'
											+ '			</div>'
											+ '			<div class="card-heading">'
											+ '					<div class="profile" style="margin-top: 8px;">'
											+ '					<span title="有'
											+ data[m].like_count
											+ '人喜欢这首歌" onclick="$unique.music.like(this,'
											+ mid
											+ ')"><i class="icon-heart-empty"></i> <span class="like-count">1</span></span>&nbsp;&nbsp;'
											+ '				<span title="下载：'
											+ data[m].song
											+ '" onclick="$unique.music.download(\''
											+ mid
											+ ', '
											+ data[m].mp3_url
											+ '\')"><i class="icon-arrow-down"></i> 下载</span>&nbsp;&nbsp;'
											+ '				<span><i class="icon-time"></i> '
											+ data[m].date_zh
											+ '</span>'
											+ '					</div>'
											+ '				</div>'
											+ '			</div>';
									musicList.push(inner);
								}
								$('#music-list').html(musicList);
								$unique.img.loadind();
							}
						});
	}
	// 个性化标签
	if (tag_type == 3) {

	}
	// 音乐专辑
	if (tag_type == 4) {

	}
}
