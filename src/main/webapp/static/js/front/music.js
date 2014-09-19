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

$(function() {
//	var playlist = [{
//		title : "Hidden",
//		artist : "Miaow",
//		mp3 : "http://jq22.qiniudn.com/i1.mp3",
//		//oga:"http://www.jplayer.org/audio/ogg/Miaow-02-Hidden.ogg",
//		poster : "http://33.media.tumblr.com/0b35eb42176eedbf4a96e52efa760875/tumblr_mxp7a0v3fr1rqx86wo1_500.png"
//	}, {
//		title : "Cro Magnon Man",
//		artist : "The Stark Palace",
//		mp3 : "http://jq22.qiniudn.com/i2.mp3",
//		//oga:"http://www.jplayer.org/audio/ogg/TSP-01-Cro_magnon_man.ogg",
//		poster : "http://33.media.tumblr.com/bf9dc125a47dcca91ce5b3575bc3ba92/tumblr_nbmb3j8nU51sq3g2zo1_500.png"
//	}, {
//		title : "Bubble",
//		m4a : "http://www.jplayer.org/audio/m4a/Miaow-07-Bubble.m4a",
//		oga : "http://www.jplayer.org/audio/ogg/Miaow-07-Bubble.ogg",
//		poster : "http://31.media.tumblr.com/810b1125a8b9e9f192d009ef58dceb07/tumblr_nbe8wsmKuz1rknpqyo1_500.jpg"
//	}];
//
//	myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
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
				var playlist = [{
					title : data.song,
					artist : data.singer,
					mp3 : data.mp3_url,
					ogg : data.mp3_url,
					poster : data.cover_url
				}];
				myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
				// 播放
				$("#jquery_jplayer").jPlayer("playHeadTime", 0);
				//$unique.music.loadAudio(data.mp3_url, data.song);
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
