/**
 * 全局music
 */
var audioEl, titleEl, timeEl, rangeEl, playEl, volumeEl;
$(function() {
	$unique.music.initAudio();
});

/**
 * 播放MP3
 * 
 * @param mid
 */
$unique.music.playerMp3 = function(mid) {
	if (mid) {
		var url = $unique.root + '/get_music';
		var param = {
			mid : mid
		};
		$.get(url, param, function(data) {
			if (data) {
				// 播放
				$unique.music.loadAudio(data.mp3_url, data.song);
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

/**
 * 初始化音频设置
 */
$unique.music.initAudio = function() {
	var _audio;
	if (audioEl) {
		return;
	} // 如果存在,说明已经初始化
	if (window['Audio'] && (_audio = new Audio()).canPlayType('audio/mpeg')) {
		_audio.addEventListener('canplay', onCanPlay, false);
		_audio.addEventListener('play', onPlay, false);
		_audio.addEventListener('pause', onPause, false);
		_audio.addEventListener('ended', onEnded, false);
		_audio.addEventListener('error', onError, false);
		_audio.addEventListener('timeupdate', onTimeUpdate, false);
		_audio.volume = 0.5;
		document.getElementById('player').appendChild(_audio);

		audioEl = _audio;
		titleEl = document.getElementById('title');
		timeEl = document.getElementById('time');
		rangeEl = document.getElementById('range');
		playEl = document.getElementById('play');
		volumeEl = document.getElementById('volume');

		volumeEl.addEventListener('change', onVolumeChange, false);
		rangeEl.addEventListener('change', onRangeChange, false);
		playEl.addEventListener('click', onPlayButtonClick, false);
	} else {
		alert('Oops, nice browser.');
		return;
	}
}
/**
 * 加载mp3
 * 
 * @param url
 * @param title
 */
$unique.music.loadAudio = function(url, title) {
	if (!audioEl) {
		return;
	}
	var name = title || url.replace(/^.*\//, '').replace(/[#\?].*$/, '')
			|| 'Unknown';
	titleEl.innerHTML = '<i class="icon-music">&nbsp;</i>' + name;
	rangeEl.value = 0;
	rangeEl.disabled = true;
	timeEl.innerHTML = '--:--/--:--';
	playEl.innerHTML = '加载中';
	audioEl.autoplay = true;
	audioEl.src = url;
	// audioEl.load();
}
function onCanPlay() {
	rangeEl.disabled = false;
}
function onPlay() {
	playEl.innerHTML = '暂停';
}
function onPause() {
	playEl.innerHTML = '播放';
}
function onEnded() {
	audioEl.pause();
	audioEl.currentTime = 0;
}
function onError() {
	rangeEl.disabled = true;
	titleEl.innerHTML = '<span style="color:red">加载错误:' + titleEl.innerHTML
			+ '</span>';
	playEl.innerHTML = '已停止';
}
function onTimeUpdate() {
	var pos = audioEl.currentTime, dora = audioEl.duration;
	timeEl.innerHTML = formatTime(pos) + '/' + formatTime(dora);
	// console.info(pos,dora);
	if (isFinite(dora) && dora > 0) {
		rangeEl.value = pos / dora;
	}
}
function onVolumeChange() {
	if (!audioEl) {
		return;
	}
	audioEl.volume = volumeEl.value;
}
function onRangeChange() {
	if (!audioEl) {
		return;
	}
	var buf = audioEl.buffered.length ? audioEl.buffered.end(0) : 0, dora = audioEl.duration;
	if (isFinite(dora) && dora > 0) {
		var value = rangeEl.value, pos = value * dora;
		if (pos > buf) {
			pos = buf;
		}
		audioEl.currentTime = pos;
	}
}
function onPlayButtonClick() {
	if (!audioEl) {
		return;
	}
	if (audioEl.error) { // 加载错误
		return;
	} else if (audioEl.readyState < 2) { // 还没可以播放
		audioEl.autoplay ^= true; // 切换是否autoplay
	} else if (audioEl.paused) {
		audioEl.play();
	} else {
		audioEl.pause();
	}
}
function formatTime(sec) {
	if (!isFinite(sec) || sec < 0) {
		return '--:--';
	} else {
		var m = Math.floor(sec / 60), s = Math.floor(sec) % 60;
		return (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
	}
}