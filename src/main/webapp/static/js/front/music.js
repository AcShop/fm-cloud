/**
 * 全局music
 */
var cssSelector = {
	jPlayer : "#jquery_jplayer",
	cssSelectorAncestor : ".music-player"
};

var options = {
//	playlistOptions: {
//	    autoPlay: true
//	},
	swfPath : $unique.cdn + "/static/js/jq/Jplayer.swf",
	supplied : "ogv, m4v, oga, mp3"
};
var myPlaylist = null;

$(function() {
	var playlist = [{
		title : "Hidden",
		artist : "Miaow",
		mp3 : "http://jq22.qiniudn.com/i1.mp3",
		//oga:"http://www.jplayer.org/audio/ogg/Miaow-02-Hidden.ogg",
		poster : $unique.cdn + "/static/img/tumblr_mxp7a0v3fr1rqx86wo1_500.png"
	}, {
		title : "Cro Magnon Man",
		artist : "The Stark Palace",
		mp3 : "http://jq22.qiniudn.com/i2.mp3",
		//oga:"http://www.jplayer.org/audio/ogg/TSP-01-Cro_magnon_man.ogg",
		poster : $unique.cdn + "/static/img/tumblr_nbmb3j8nU51sq3g2zo1_500.png"
	}, {
		title : "Bubble",
		m4a : "http://www.jplayer.org/audio/m4a/Miaow-07-Bubble.m4a",
		oga : "http://www.jplayer.org/audio/ogg/Miaow-07-Bubble.ogg",
		poster : $unique.cdn + "/static/img/tumblr_nbe8wsmKuz1rknpqyo1_500.jpg"
	}];

	myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
});

/**
 * 播放MP3
 * 
 * @param mid
 */
$unique.music.playerMp3 = function(mid) {
	if (mid) {
		var url = $unique.base + '/get_music';
		var param = {
			mid : mid
		};
		$.get(url, param, function(data) {
			if (data) {
				myPlaylist.setPlaylist([{
	                title: data.song,
	                artist: data.singer,
	                mp3: data.mp3_url,
	                poster: data.cover_url
	            }]);
				//设置自动播放
				myPlaylist.option("autoPlay", true);
				myPlaylist.play(0);
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
		var url = $unique.root + '/hit';
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
				$(obj).find('i').removeClass('icon-star-empty').addClass('icon-star');
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
		var url = $unique.root + '/hit';
		var param = {
			mid : mid,
			type : 3
		}
		$.get(url, param, function(data) {
			window.open(downloadUrl);
		});
	}
}
