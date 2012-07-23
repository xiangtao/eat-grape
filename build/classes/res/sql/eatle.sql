/*
MySQL Backup
Source Server Version: 5.1.51
Source Database: eatledb
Date: 2012/7/17 02:08:29
*/


-- ----------------------------
--  Table structure for `t_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege`;
CREATE TABLE `t_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priv_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `meue_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `action` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `is_show` tinyint(4) DEFAULT '0',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限';

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `t_role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_privilege`;
CREATE TABLE `t_role_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `priv_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `priv_id` (`priv_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `FK_t_role_privlege_t_privilege` FOREIGN KEY (`priv_id`) REFERENCES `t_privilege` (`id`),
  CONSTRAINT `FK_t_role_privlege_t_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `pwd` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `type` tinyint(4) NOT NULL COMMENT '用户类型',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `FK_t_user_t_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `t_privilege` VALUES ('1','系统管理','系统管理','','系统管理','1',NULL),  ('2','商家管理','商家管理','','','1',NULL),  ('3','11','22','33','4','1','3'),  ('5','ttt','ttt','tt','tt',NULL,'2'),  ('6','dd','fdsf','fdsfd','fdsf','1','3'),  ('8','fdafd','fdsa','fdsa','fdsa','1','6'),  ('9','账号管理','账号管理','account','','1','1'),  ('10','资讯管理','资讯管理','','资讯管理','1',NULL),  ('11','222','333','333','33','1','1'),  ('13','333','2332','32323','3323','1','1'),  ('14','11','11','11','11','1','1'),  ('15','fdsa','fdd','dds','ss','1','1'),  ('16','11','dsads','dsad','dsad','1','1'),  ('17','323hhhhhhhhhhh','333hhhhhhhhhhhh','hhhhhhhhhhhhhh','hhhhhhhhhhhhh','1','1'),  ('18','房价开始','房价开始','房价开始','房价开始',NULL,'1'),  ('19','fuckyou','fuckyou','fuckyou','fuckyou','1','1'),  ('20','222ggggggggggg','23323','32323','33','1','1'),  ('21','zzzzzzzzz','zzz','zz','zz','1','1'),  ('22','bbbbbb','bbbb','bbbbbbbb','bbbbbbb','1','1');
INSERT INTO `t_role` VALUES ('2','超级管理员','我是超级管理员'),  ('3','编辑员','我是编辑员');
INSERT INTO `t_user` VALUES ('1','张慧华','555555555','333','2',NULL),  ('2','dsaf','ff3333333333','fff','2',NULL),  ('3','2121222','222','333333333','2',NULL),  ('4','张慧华','3323','3232','2',NULL);
