/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : qiniu-cloud

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2014-09-26 10:12:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_active
-- ----------------------------
DROP TABLE IF EXISTS `t_active`;
CREATE TABLE `t_active` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '用户id',
  `code` varchar(50) NOT NULL COMMENT '激活码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已经激活',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_active
-- ----------------------------

-- ----------------------------
-- Table structure for t_album
-- ----------------------------
DROP TABLE IF EXISTS `t_album`;
CREATE TABLE `t_album` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '创建人',
  `name` varchar(50) NOT NULL COMMENT '相册名称',
  `path` varchar(100) NOT NULL COMMENT '所在路径',
  `pic_count` int(10) DEFAULT '0' COMMENT '图片数',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_album
-- ----------------------------

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `aid` int(10) DEFAULT NULL COMMENT '评论载体',
  `type` tinyint(4) NOT NULL COMMENT '消息类型',
  `uid` int(10) DEFAULT '0' COMMENT '评论人',
  `username` varchar(50) DEFAULT NULL COMMENT '匿名昵称',
  `ip` varchar(20) DEFAULT NULL COMMENT '评论人ip',
  `content` varchar(500) NOT NULL COMMENT '评论内容',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) DEFAULT NULL COMMENT '上传者uid',
  `path` varchar(255) NOT NULL COMMENT '文件路径',
  `type` tinyint(4) DEFAULT NULL COMMENT '文件类型1：文件 2：文件夹',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Records of t_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '用户主体',
  `follow_uid` int(10) NOT NULL COMMENT '关注主体',
  `type` tinyint(4) NOT NULL COMMENT '0：粉丝1：关注',
  `add_time` int(11) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='关注表';

-- ----------------------------
-- Records of t_follow
-- ----------------------------

-- ----------------------------
-- Table structure for t_mcat
-- ----------------------------
DROP TABLE IF EXISTS `t_mcat`;
CREATE TABLE `t_mcat` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '分类状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='音乐分类';

-- ----------------------------
-- Records of t_mcat
-- ----------------------------
INSERT INTO `t_mcat` VALUES ('1', '流行1', '1');
INSERT INTO `t_mcat` VALUES ('2', '伤感', '1');
INSERT INTO `t_mcat` VALUES ('3', '励志', '1');
INSERT INTO `t_mcat` VALUES ('4', '酒吧', '1');
INSERT INTO `t_mcat` VALUES ('5', '致青春', '1');
INSERT INTO `t_mcat` VALUES ('6', '网络', '1');
INSERT INTO `t_mcat` VALUES ('7', '轻音乐', '1');
INSERT INTO `t_mcat` VALUES ('8', '舞曲', '1');

-- ----------------------------
-- Table structure for t_music
-- ----------------------------
DROP TABLE IF EXISTS `t_music`;
CREATE TABLE `t_music` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '上传者uid',
  `singer` varchar(50) NOT NULL DEFAULT '' COMMENT '歌手名称',
  `song` varchar(100) NOT NULL COMMENT '歌曲名称',
  `song_path` varchar(200) NOT NULL COMMENT '歌曲路径',
  `cover_path` varchar(255) NOT NULL DEFAULT '' COMMENT '封面图',
  `introduce` varchar(255) DEFAULT NULL COMMENT '歌曲描述',
  `cids` varchar(200) DEFAULT NULL COMMENT '所属分类',
  `tags` varchar(200) DEFAULT NULL COMMENT '歌曲标签',
  `like_count` int(10) DEFAULT '0' COMMENT '喜欢次数',
  `download_count` int(10) DEFAULT '0' COMMENT '下载次数',
  `create_time` int(11) NOT NULL COMMENT '上传时间',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '音乐状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='音乐表';

-- ----------------------------
-- Records of t_music
-- ----------------------------
INSERT INTO `t_music` VALUES ('1', '1', '未知', 'wish.mp3', 'user/1/music/14099105233850.mp3', 'user/1/music_cover/14116307797473_cover.jpg', '女唱疯狂嗨翻全场重低音压', '', null, '17', '0', '1409910523', '1');
INSERT INTO `t_music` VALUES ('2', '1', '未知', '热播韩文超弹跳电嗨压潮爽', 'http://qqmp3.djwma.com/mp3/热播韩文超弹跳电嗨压潮爽鼓包房必备.mp3', '', '热播韩文超弹跳电嗨压潮爽', '8', null, '1', '0', '1410948011', '0');
INSERT INTO `t_music` VALUES ('3', '1', '未知', '女唱疯狂嗨翻全场重低音压', 'http://qqmp3.djwma.com/mp3/女唱疯狂嗨翻全场重低音压潮神鼓.mp3', '', '女唱疯狂嗨翻全场重低音压', '8', null, '4', '0', '1410948033', '1');
INSERT INTO `t_music` VALUES ('4', '1', '筷子兄弟', '小苹果dj版-筷子兄弟', 'http://qqmp3.djwma.com/mp3/小苹果dj版-筷子兄弟(你是我的小苹果).mp3', '', '小苹果dj版-筷子兄弟', '8,1', null, '7', '0', '1410948059', '1');
INSERT INTO `t_music` VALUES ('5', '1', '未知', '高端大气上档次的重鼓女唱', 'http://qqmp3.djwma.com/mp3/高端大气上档次的重鼓女唱超爽震撼音效.mp3', '', '高端大气上档次的重鼓女唱', '8,4', null, '1', '0', '1410948080', '1');
INSERT INTO `t_music` VALUES ('7', '1', 'DJ何鹏', '兄弟难当', 'http://qqmp3.djwma.com/mp3/杜歌-兄弟难当-DJ何鹏.mp3', 'http://res.qingting.fm/uploadfile/2014/0417/20140417012317924.jpg', '杜歌-兄弟难当', '8', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('8', '1', '丁少华', '爱的节奏', 'http://tingge.5nd.com/20060919//2014/2014-9-19/64227/1.Mp3', 'http://img.5nd.com/photo/2013album/20149/64227.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('9', '1', '邱永传', '十二年', 'http://tingge.5nd.com/20060919//2014/2014-9-18/62001/2.Mp3', '', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('10', '1', '魏新雨', '来自星星的你', 'http://tingge.5nd.com/20060919//2014/2014-9-15/64171/1.Mp3', 'http://img.5nd.com/photo/2013album/20149/64171.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('11', '1', '刘恩铄', '别让她走', 'http://tingge.5nd.com/20060919//2014/2014-8-28/63987/1.Mp3', 'http://img.5nd.com/photo/2013album/20148/63987.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('12', '1', '简红(蹇红)', '泡沫', 'http://tingge.5nd.com/20060919//2014/2014-5-8/62991/1.Mp3', 'http://img.5nd.com/photo/2013album/20145/62991.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('13', '1', '吴莫愁', '就现在', 'http://tingge.5nd.com/20060919//2010/2010b/2013-5-12/59753/1.Mp3', 'http://img.5nd.com/photo/2013album/20135/59753.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('14', '1', '温博', '胡杨姑娘', 'http://tingge.5nd.com/20060919//2014/2014-9-19/64230/1.Mp3', 'http://img.5nd.com/photo/2013album/20149/64230.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('15', '1', '陈兴瑜', '你是我的罗密欧', 'http://tingge.5nd.com/20060919//2010/2010b/2012-5-2/53765/1.Mp3', 'http://img.5nd.com/Photo/New/2010/2012-5-2/12291.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('16', '1', '蒋蒋', '忧伤歌者（Feat.雪无影）', 'http://tingge.5nd.com/20060919//2014/2014-9-4/64085/1.Mp3', 'http://img.5nd.com/photo/2013album/20149/64085.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('17', '1', '大哲', '曾经的大哥', 'http://tingge.5nd.com/20060919//2014/2014-8-3/63667/1.Mp3', 'http://img.5nd.com/photo/2013album/20148/63667.jpg', null, '1', null, '0', '0', '1411467000', '1');
INSERT INTO `t_music` VALUES ('18', '1', '姜鹏', '社会方方面面这一块儿', 'http://tingge.5nd.com/20060919//2014/2014-8-3/63672/1.Mp3', 'http://img.5nd.com/photo/2013album/20148/63672.jpg', null, '1', null, '0', '0', '1411467000', '1');

-- ----------------------------
-- Table structure for t_open
-- ----------------------------
DROP TABLE IF EXISTS `t_open`;
CREATE TABLE `t_open` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '1：qq 2：微博',
  `email` varchar(50) NOT NULL COMMENT '关联邮箱',
  `openid` varchar(32) NOT NULL COMMENT 'openid',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_open
-- ----------------------------

-- ----------------------------
-- Table structure for t_pic
-- ----------------------------
DROP TABLE IF EXISTS `t_pic`;
CREATE TABLE `t_pic` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '用户id',
  `name` varchar(100) DEFAULT NULL COMMENT '图片名称',
  `introduce` varchar(200) DEFAULT NULL COMMENT '图片描述',
  `path` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `album_id` int(10) NOT NULL COMMENT '所属相册',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '图片状态',
  `create_time` int(11) NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pic
-- ----------------------------

-- ----------------------------
-- Table structure for t_post
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `pid` int(10) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `uid` int(10) NOT NULL COMMENT '作者id',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `tags` varchar(255) DEFAULT NULL COMMENT '标签id',
  `allow_comment` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否允许评论',
  `hit` int(10) NOT NULL DEFAULT '0' COMMENT '阅读量',
  `post_time` int(11) NOT NULL COMMENT '发布时间',
  `last_time` int(11) NOT NULL COMMENT '最后更新时间',
  `is_pub` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1发布 2草稿',
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of t_post
-- ----------------------------
INSERT INTO `t_post` VALUES ('1', '1', '2', null, null, '1', '3', '0', '123123123', '1');

-- ----------------------------
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE `t_setting` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) DEFAULT NULL COMMENT '配置键',
  `value` text COMMENT '配置值',
  `desc` varchar(200) DEFAULT NULL COMMENT '配置描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_setting
-- ----------------------------
INSERT INTO `t_setting` VALUES ('1', 'admin_name', 'rex', '管理员帐号');
INSERT INTO `t_setting` VALUES ('2', 'admin_pass', '423757e56f4b31a29847a51c83f49ebc', '管理员密码');
INSERT INTO `t_setting` VALUES ('3', 'site_title', 'Youth Power，Cloud Era!', '站点标题');
INSERT INTO `t_setting` VALUES ('4', 'site_keywords', null, '站点关键字');
INSERT INTO `t_setting` VALUES ('5', 'site_desc', null, '站点描述');

-- ----------------------------
-- Table structure for t_special
-- ----------------------------
DROP TABLE IF EXISTS `t_special`;
CREATE TABLE `t_special` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) DEFAULT NULL,
  `title` varchar(50) NOT NULL COMMENT '专辑主题',
  `introduce` varchar(200) DEFAULT NULL COMMENT '专辑描述',
  `hit` int(10) NOT NULL DEFAULT '0' COMMENT '热度',
  `cover_small` varchar(255) DEFAULT NULL COMMENT '封面小图',
  `cover_pic` varchar(255) DEFAULT NULL COMMENT '专辑封面',
  `is_top` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `last_time` int(11) NOT NULL COMMENT '最后更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_special
-- ----------------------------
INSERT INTO `t_special` VALUES ('1', '1', '洒洒的', '23432424似懂非懂司法所地方', '0', 'user/1/images/14116331325359.jpg', 'user/1/images/14116331325359.jpg', '1', '1411633132', '1411634754', '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `uid` int(10) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `reg_ip` varchar(20) DEFAULT NULL COMMENT '注册ip',
  `reg_time` int(11) DEFAULT NULL COMMENT '注册时间',
  `log_time` int(11) DEFAULT NULL COMMENT '最后登录时间',
  `msg_count` int(10) NOT NULL DEFAULT '0' COMMENT '发布消息数量',
  `fans_count` int(10) NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `follow_count` int(10) NOT NULL DEFAULT '0' COMMENT '关注数',
  `space_size` int(10) DEFAULT '100000000' COMMENT '用户空间大小',
  `use_size` int(10) DEFAULT '0' COMMENT '已经使用大小',
  `is_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是超级管理员',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '超级管理员', 'admin@qq.com', '5c2c4e44e67d866e1122013bb75bc66e', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '16507376', '1', '1');
INSERT INTO `t_user` VALUES ('2', '543658', '543658@qq.com', 'f6ecc1149789a5372ff764b3aa67230f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('3', '400649', '400649@qq.com', '0adaa0cb4bab8637a6a9153ec952eec4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('4', '649985', '649985@qq.com', 'd02837c8650a28232b989d9bed400f9f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('5', '548459', '548459@qq.com', '7810ee9c609ee14110be3e9798f05502', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('6', '550995', '550995@qq.com', 'abce054689db15ffdb678a367a797d4c', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('7', '478365', '478365@qq.com', '77425d59fd4a9d60f365c6ee0222af48', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('8', '350621', '350621@qq.com', 'ebd28bf48dbf332cd81301b66196b3e0', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('9', '314149', '314149@qq.com', '6c56002d732ad1ea319ff47a95fe6e33', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('10', '618452', '618452@qq.com', 'fcd1efcaf897788105982dd2b3175616', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('11', '896411', '896411@qq.com', '063be9a7615d1ca4ad8ed20dbff138d4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('12', '503486', '503486@qq.com', '12ed691bc7375a6ca2cb7b599e4a61cc', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('13', '970333', '970333@qq.com', '71f558488e9f4052fb0b1a342f9e800d', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('14', '989016', '989016@qq.com', 'ab91c0d5a20c6cdaa45d6c69af36c285', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('15', '287477', '287477@qq.com', 'aabfefea3da6c6e7579a2b861be6a202', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('16', '794300', '794300@qq.com', '74e9ea66525c77441d23b0668f35370c', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('17', '441509', '441509@qq.com', '2fc5966156531e4bc14e15c83ff83cf4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('18', '905972', '905972@qq.com', '3a85b396f49d2076895a6e506bc9b93f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('19', '060186', '060186@qq.com', 'e8bd927e4c0c784bf92c9d5d3447c76b', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('20', '618691', '618691@qq.com', '5a654ce845d3a6a8e0eb9137dbd85928', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('21', '876895', '876895@qq.com', '563a164776d6787a2fcaee1d596952fe', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('22', '006610', '006610@qq.com', '127793c0b22f56d479f7a319e0fb99f9', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('23', '399357', '399357@qq.com', 'a07afab005022af3e9c5ca35493b61ed', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('24', '647952', '647952@qq.com', 'fc7ec94080fb281744c7efd3184759e6', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('25', '688801', '688801@qq.com', 'fd565e8d91f2d21956a542417ab87063', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('26', '413781', '413781@qq.com', '150dfccabc669302f9b9d2de0f3e5359', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('27', '449270', '449270@qq.com', '2363dce159bae5d32c5890623a182383', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('28', '899259', '899259@qq.com', '94dccd083cd64d07d12c607ec711983d', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('29', '018076', '018076@qq.com', 'dba40fab062bc3991005240707fdfcf2', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('30', '406061', '406061@qq.com', '1c599d5b3198fc18a33c25c41611809c', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('31', '857755', '857755@qq.com', '5d830af88b6e04734f1f5e55e31c7cef', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('32', '469293', '469293@qq.com', '3d1830ce296d1b40fdd1dcb0f6d79d8a', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('33', '490138', '490138@qq.com', 'c933b6bd28ec8e8e7bb4ba100083eac3', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('34', '555528', '555528@qq.com', 'ba7b1bab5edd656906a7d3a26992d540', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('35', '367745', '367745@qq.com', '850374449e8df0953f7e6917cae64c68', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('36', '668917', '668917@qq.com', 'b8eaad758f3f57d06527ea8ffc82eef4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('37', '506588', '506588@qq.com', 'cf2aebc684c1378a58797c84f425732b', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('38', '523423', '523423@qq.com', 'cd740fe74fb586d70d77e7b1c14cfca0', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('39', '385645', '385645@qq.com', '46fbe57081c46cfe4bdb67273c67e350', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('40', '446272', '446272@qq.com', '59c356353030587ed576fb435f49d3e7', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('41', '941936', '941936@qq.com', '8a9c7c50c19a71f8ab4b4b9663305997', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('42', '196928', '196928@qq.com', '25f4b5266754cc152ac15d3a37835ca1', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('43', '729323', '729323@qq.com', 'b9cb7f21293ac9b4649a261caf154746', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('44', '596123', '596123@qq.com', 'c688add7ca2efb2558ff23fb8626598d', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('45', '725057', '725057@qq.com', '5bb48f53fc800e30485b0d8c1b058bca', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('46', '387527', '387527@qq.com', '87d592a9e09c55aaced9934928e7f5bd', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('47', '269043', '269043@qq.com', '2546f3bc1af95ae93dd7e0e971cbc451', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('48', '354155', '354155@qq.com', 'ebe2003c490da225712435ee8d6cbe9f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('49', '588857', '588857@qq.com', '50934dddd3e689d621e2195142ac89e1', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('50', '172337', '172337@qq.com', '95a9833b8575243823b5766ee3cddef1', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('51', '910738', '910738@qq.com', '5ef6c87381d978e2a9e4398d30b33e9c', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('52', '242799', '242799@qq.com', '56f8e6d29f2a43f7f9280ce4bcdbb6de', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('53', '244874', '244874@qq.com', 'ab89b66a88d124fc5cef3c7c9091fde5', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('54', '462506', '462506@qq.com', '80086bed28c0fbee2261053a204ad867', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('55', '282687', '282687@qq.com', '95ad1aac492f57c0ece37bb3a819093e', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('56', '937488', '937488@qq.com', '9a7d5a4a1a41302de966bd31ea0f3764', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('57', '807454', '807454@qq.com', 'c6dd59743d204b7961cf6534d2e0d1c6', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('58', '139886', '139886@qq.com', 'f8961d113da5b775ac6cd3f4df0c4aaa', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('59', '046947', '046947@qq.com', '61d133ec26b543e583ec946078eeb2c4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('60', '039411', '039411@qq.com', '9480c2407b7b006639ce5c8c8632470f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('61', '743807', '743807@qq.com', '92d5ba49a9353217377a3dbf3705df59', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('62', '094747', '094747@qq.com', 'a2243eca6957c7509c9718c9ce473137', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('63', '580872', '580872@qq.com', '80c0d1cbc40d74d67698911c4782d2e4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('64', '117038', '117038@qq.com', '961862c33eb08099a0160760e9a3fbac', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('65', '525345', '525345@qq.com', '8973e013d9347810c395bf9fe3aeb148', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('66', '572374', '572374@qq.com', 'f82ec74e2d731ba9ff8f85f21984444e', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('67', '924668', '924668@qq.com', '0fe476b322db7451b997b6716a56ff51', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('68', '980302', '980302@qq.com', 'bd8c9569a366ccb55d80a9f609a4b236', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('69', '012641', '012641@qq.com', '38e7c92e0fba1f9e8c743b85bba6e2fb', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('70', '725691', '725691@qq.com', 'b6d15df561e20b51bfaa3542f3c104a6', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('71', '371149', '371149@qq.com', '2e5000becf45361d4cd916bc991a45d2', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('72', '671332', '671332@qq.com', 'bd82162bf4082e923454f623d959e253', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('73', '215965', '215965@qq.com', '6eee33fadec4405c9d805674a6fe8591', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('74', '121670', '121670@qq.com', '08433b3f7a6da1e0305ff5e451ce6c37', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('75', '085647', '085647@qq.com', 'd73629835672a2b15f3085378de9dd05', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('76', '786999', '786999@qq.com', 'a42f910916edeee65d140160de15b4d0', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('77', '042468', '042468@qq.com', '84c91d1b3ecff7909a2ee92ee7871d62', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('78', '440084', '440084@qq.com', '72fab00a55f4604a513a7c401fc3cbdf', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('79', '140274', '140274@qq.com', '375833a6a3461bb8a5be9f14edaa8707', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('80', '115175', '115175@qq.com', '8b96df8f2cfdc79b8030bfc6665771e6', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('81', '249466', '249466@qq.com', 'a11617b6af2646c050a7bb4f54c264bf', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('82', '816655', '816655@qq.com', '668a0a25c25b6865600ef7816d1e3aba', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('83', '012996', '012996@qq.com', '9eff1533ae405ad2eca608870ef39e56', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('84', '765606', '765606@qq.com', '924b6540ec21fe7f461b4279d7f25ef3', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('85', '776740', '776740@qq.com', '8997ca9416e059acbdf94634d8d8cc52', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('86', '389578', '389578@qq.com', '794b9369c014f7020aded897d9366983', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('87', '426066', '426066@qq.com', '48b9c6a751aff7e1a4c0501850d73fa7', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('88', '930902', '930902@qq.com', '3f72c2ae8c5d3b37b5c3b53fe391c388', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('89', '749193', '749193@qq.com', 'dc492fd98db13d4233baa07111ab2538', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('90', '906972', '906972@qq.com', '59d583ddafb26bef1a59f556a32778f4', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('91', '782863', '782863@qq.com', '547ae0aedd168c56cadce85d4dc4814d', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('92', '110021', '110021@qq.com', 'a4dd15d3988ff5e69738866ff6e3a859', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('93', '470942', '470942@qq.com', 'b0cd7e4e21ade2b21f48f348218def45', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('94', '587337', '587337@qq.com', '39a09593bcfc98bdd5ea57e1c1db856f', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '1');
INSERT INTO `t_user` VALUES ('95', '784568', '784568@qq.com', '70299091d911155378f9dde7f9e96dc3', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '10240', '0', '0', '0');
INSERT INTO `t_user` VALUES ('96', '2', '133820@qq.com', 'a58e0a0b83c03cb49f41620a5d7990f8', '127.0.0.1', '1408585987', '1408585987', '0', '0', '0', '20240', '0', '0', '1');
