/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : myadmin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-08-24 11:38:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '36dab768-0af7-4fe3-8720-4e43c0ac4cb2', '一年级', '36dab768-0af7-4fe3-8720-4e43c0ac4cb2', '0', '1');
INSERT INTO `classes` VALUES ('2', '029356e2-b186-43f0-be27-f692d587fc64', '二年级', '029356e2-b186-43f0-be27-f692d587fc64', '0', '2');
INSERT INTO `classes` VALUES ('3', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81', '三年级', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81', '0', '3');
INSERT INTO `classes` VALUES ('4', '4b643923-a2c6-409d-99d3-331bfd0cd9a8', '四年级', '4b643923-a2c6-409d-99d3-331bfd0cd9a8', '0', '4');
INSERT INTO `classes` VALUES ('5', 'b75856f5-86c0-4938-9d42-d4dbcad3426e', '五年级', 'b75856f5-86c0-4938-9d42-d4dbcad3426e', '0', '5');
INSERT INTO `classes` VALUES ('6', 'c3af3707-8706-476b-85af-7b092bde12a0', '六年级', 'c3af3707-8706-476b-85af-7b092bde12a0', '0', '6');
INSERT INTO `classes` VALUES ('7', 'a6932451-7c4c-43f8-aa0e-25dc0df4ab22', '数学培优班', '36dab768-0af7-4fe3-8720-4e43c0ac4cb2#a6932451-7c4c-43f8-aa0e-25dc0df4ab22', '1', '1');
INSERT INTO `classes` VALUES ('8', 'ea30885f-0071-4682-a681-1c644502e298', '数学培优班', '029356e2-b186-43f0-be27-f692d587fc64#ea30885f-0071-4682-a681-1c644502e298', '2', '1');
INSERT INTO `classes` VALUES ('9', '8e2c6f36-1b2d-4b8f-9e59-ed9291dad545', '数学同步班', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81#8e2c6f36-1b2d-4b8f-9e59-ed9291dad545', '3', '1');
INSERT INTO `classes` VALUES ('10', '21f3139d-acd9-4c25-9ac9-2f4a95958d2b', '数学培优班', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81#21f3139d-acd9-4c25-9ac9-2f4a95958d2b', '3', '2');
INSERT INTO `classes` VALUES ('11', '01d1c803-7a25-43f9-8c86-4675278b0890', '数学同步班', '4b643923-a2c6-409d-99d3-331bfd0cd9a8#01d1c803-7a25-43f9-8c86-4675278b0890', '4', '1');
INSERT INTO `classes` VALUES ('12', '9cd23a01-0a70-4da0-9acd-55060e65159e', '数学培优班', '4b643923-a2c6-409d-99d3-331bfd0cd9a8#9cd23a01-0a70-4da0-9acd-55060e65159e', '4', '2');
INSERT INTO `classes` VALUES ('13', 'd0f94242-9d19-4a13-8a2a-e378eb43bcf6', '数学同步班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#d0f94242-9d19-4a13-8a2a-e378eb43bcf6', '5', '1');
INSERT INTO `classes` VALUES ('14', 'c0284d5a-8f2b-4c6e-993b-382de7ae32da', '数学培优班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#c0284d5a-8f2b-4c6e-993b-382de7ae32da', '5', '2');
INSERT INTO `classes` VALUES ('15', '95446448-dc84-4579-9f31-c31489931f62', '数学集训班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#95446448-dc84-4579-9f31-c31489931f62', '5', '3');
INSERT INTO `classes` VALUES ('16', '4315a20b-e7c8-40db-b35d-92452616f6b3', '数学同步班', 'c3af3707-8706-476b-85af-7b092bde12a0#4315a20b-e7c8-40db-b35d-92452616f6b3', '6', '1');
INSERT INTO `classes` VALUES ('17', 'cd299d6f-cb98-448a-8b54-40af14fa9f2f', '数学培优班', 'c3af3707-8706-476b-85af-7b092bde12a0#cd299d6f-cb98-448a-8b54-40af14fa9f2f', '6', '2');
INSERT INTO `classes` VALUES ('18', '7def8b2b-03e2-4564-9177-3703876f1f2e', '语文国学班', '36dab768-0af7-4fe3-8720-4e43c0ac4cb2#7def8b2b-03e2-4564-9177-3703876f1f2e', '1', '2');
INSERT INTO `classes` VALUES ('19', '299531c3-c81c-4013-8566-af3ccf56596b', '语文国学班', '029356e2-b186-43f0-be27-f692d587fc64#299531c3-c81c-4013-8566-af3ccf56596b', '2', '2');
INSERT INTO `classes` VALUES ('20', '665bc24c-b242-4483-a825-7c71ba5bcd71', '语文阅读班', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81#665bc24c-b242-4483-a825-7c71ba5bcd71', '3', '3');
INSERT INTO `classes` VALUES ('21', '083fe0f6-d553-41b0-873a-dd798de31dde', '语文写作班', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81#083fe0f6-d553-41b0-873a-dd798de31dde', '3', '4');
INSERT INTO `classes` VALUES ('22', 'a6d201cc-2bf2-4b8a-9e6b-751dcf257a59', '语文阅读班', '4b643923-a2c6-409d-99d3-331bfd0cd9a8#a6d201cc-2bf2-4b8a-9e6b-751dcf257a59', '4', '3');
INSERT INTO `classes` VALUES ('23', '6dcc60fa-afc7-4663-8240-66479cc1dda3', '语文写作班', '4b643923-a2c6-409d-99d3-331bfd0cd9a8#6dcc60fa-afc7-4663-8240-66479cc1dda3', '4', '4');
INSERT INTO `classes` VALUES ('24', '0f25e797-d45d-4f77-8029-ee6af02777d7', '语文阅读班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#0f25e797-d45d-4f77-8029-ee6af02777d7', '5', '4');
INSERT INTO `classes` VALUES ('25', '2ac06bf8-3d90-48b4-86b3-f897013e517c', '语文写作班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#2ac06bf8-3d90-48b4-86b3-f897013e517c', '5', '5');
INSERT INTO `classes` VALUES ('26', '6bcbb980-1413-417b-a996-7d40e71c8583', '语文阅读班', 'c3af3707-8706-476b-85af-7b092bde12a0#6bcbb980-1413-417b-a996-7d40e71c8583', '6', '4');
INSERT INTO `classes` VALUES ('27', '36f8a8f0-771b-4821-867a-4919a3834d44', '语文写作班', 'c3af3707-8706-476b-85af-7b092bde12a0#36f8a8f0-771b-4821-867a-4919a3834d44', '6', '5');
INSERT INTO `classes` VALUES ('28', '24ffdf05-325c-43d4-9f3f-58d92a5e4aac', '英语同步班', '2e62c7db-6275-4ee7-a2a4-3cfb56b73a81#24ffdf05-325c-43d4-9f3f-58d92a5e4aac', '3', '5');
INSERT INTO `classes` VALUES ('29', '31033b13-2715-4642-aa5a-39dd1a93e87d', '英语同步班', '4b643923-a2c6-409d-99d3-331bfd0cd9a8#31033b13-2715-4642-aa5a-39dd1a93e87d', '4', '5');
INSERT INTO `classes` VALUES ('30', 'c65ace1f-2e9a-4e4d-9ddb-2d21268b32eb', '英语同步班', 'c3af3707-8706-476b-85af-7b092bde12a0#c65ace1f-2e9a-4e4d-9ddb-2d21268b32eb', '6', '6');
INSERT INTO `classes` VALUES ('31', '592cb6eb-b709-4424-95ed-0ea56939cf6a', '英语同步班', 'b75856f5-86c0-4938-9d42-d4dbcad3426e#592cb6eb-b709-4424-95ed-0ea56939cf6a', '5', '6');
INSERT INTO `classes` VALUES ('32', 'f7d7f34e-687c-476f-957a-e3454b754c0b', '数学集训班', 'c3af3707-8706-476b-85af-7b092bde12a0#f7d7f34e-687c-476f-957a-e3454b754c0b', '6', '3');

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of knowledge
-- ----------------------------
INSERT INTO `knowledge` VALUES ('6', '2ca3e236-dc1f-4145-bdde-bb81afbfb264', '第一章 速算与巧算', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#2ca3e236-dc1f-4145-bdde-bb81afbfb264', '5', '1', null);
INSERT INTO `knowledge` VALUES ('4', 'dd276665-fe98-4aea-ab55-6d85b5d384a7', '小学数学奥数知识体系', 'dd276665-fe98-4aea-ab55-6d85b5d384a7', '0', '1', null);
INSERT INTO `knowledge` VALUES ('5', '249300a5-86cb-42ca-b0f0-260ec04e8185', '第一部分 计算', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185', '4', '1', null);
INSERT INTO `knowledge` VALUES ('7', '514865a4-f998-46e0-9468-76c2aaf549d2', '整数型', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#2ca3e236-dc1f-4145-bdde-bb81afbfb264#514865a4-f998-46e0-9468-76c2aaf549d2', '6', '1', '0');
INSERT INTO `knowledge` VALUES ('8', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', '分数型', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#2ca3e236-dc1f-4145-bdde-bb81afbfb264#67a74d7e-8990-41a8-9569-5a6e8af3a8cb', '6', '2', '0');
INSERT INTO `knowledge` VALUES ('9', 'a5f34f48-f027-40aa-89dd-38bd2e6b23ef', '小数型', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#2ca3e236-dc1f-4145-bdde-bb81afbfb264#a5f34f48-f027-40aa-89dd-38bd2e6b23ef', '6', '3', '0');
INSERT INTO `knowledge` VALUES ('10', 'c18076c4-b93b-4131-92de-49b547a24cab', '第二章 比较大小', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab', '5', '2', '0');
INSERT INTO `knowledge` VALUES ('11', '377177cf-85cd-4e7c-9165-2bee96c94a3c', '化小数法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#377177cf-85cd-4e7c-9165-2bee96c94a3c', '10', '1', '0');
INSERT INTO `knowledge` VALUES ('12', '3fcbb713-aa82-43a6-bec1-d2bd09718ed8', '通分法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#3fcbb713-aa82-43a6-bec1-d2bd09718ed8', '10', '2', '0');
INSERT INTO `knowledge` VALUES ('13', '4e3fb0cb-fdcc-4b3d-94e4-e824e3bf01ce', '作商法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#4e3fb0cb-fdcc-4b3d-94e4-e824e3bf01ce', '10', '3', '0');
INSERT INTO `knowledge` VALUES ('14', 'd3c9e371-34c8-4161-85d9-c9febfbce481', '作差法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#d3c9e371-34c8-4161-85d9-c9febfbce481', '10', '4', '0');
INSERT INTO `knowledge` VALUES ('15', '925e9287-3b83-4396-b207-1b54fc5ac6f5', '比倒法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#925e9287-3b83-4396-b207-1b54fc5ac6f5', '10', '5', '0');
INSERT INTO `knowledge` VALUES ('16', 'a9f9057a-afa6-4135-a16d-8da8f9a3321b', '公式法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#c18076c4-b93b-4131-92de-49b547a24cab#a9f9057a-afa6-4135-a16d-8da8f9a3321b', '10', '6', '0');
INSERT INTO `knowledge` VALUES ('17', 'a00131e8-5e5c-42f0-93a0-8a8b85c320bd', '第三章 估算问题', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#a00131e8-5e5c-42f0-93a0-8a8b85c320bd', '5', '3', '0');
INSERT INTO `knowledge` VALUES ('18', '1758a8ef-7101-49a4-8e9a-a17f6a3676bf', '整体放缩', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#a00131e8-5e5c-42f0-93a0-8a8b85c320bd#1758a8ef-7101-49a4-8e9a-a17f6a3676bf', '17', '1', '0');
INSERT INTO `knowledge` VALUES ('19', 'a9e52cf0-9377-48ee-a743-a95c773fb7a6', '分组放缩', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#a00131e8-5e5c-42f0-93a0-8a8b85c320bd#a9e52cf0-9377-48ee-a743-a95c773fb7a6', '17', '2', '0');
INSERT INTO `knowledge` VALUES ('20', '71b75e9d-d2a5-4dcc-8344-05070fe678e0', '经典三步', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#a00131e8-5e5c-42f0-93a0-8a8b85c320bd#71b75e9d-d2a5-4dcc-8344-05070fe678e0', '17', '3', '0');
INSERT INTO `knowledge` VALUES ('21', '4ceab842-432e-4a9e-884b-687d682801dc', '第四章 定义新运算', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#249300a5-86cb-42ca-b0f0-260ec04e8185#4ceab842-432e-4a9e-884b-687d682801dc', '5', '4', '0');
INSERT INTO `knowledge` VALUES ('22', '4ff0e781-357d-4938-934f-1446db870ff8', '第二部分 数论', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8', '4', '2', '0');
INSERT INTO `knowledge` VALUES ('23', '94defafb-6d8c-4c29-a455-e568120ae45c', '第一章 除尽', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#94defafb-6d8c-4c29-a455-e568120ae45c', '22', '1', '0');
INSERT INTO `knowledge` VALUES ('24', 'b9ed05cf-103c-422f-831f-eefc7eb5a57b', '整除问题', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#94defafb-6d8c-4c29-a455-e568120ae45c#b9ed05cf-103c-422f-831f-eefc7eb5a57b', '23', '1', '0');
INSERT INTO `knowledge` VALUES ('25', 'f560172e-5bde-4dc7-8ecb-7d8d256e2e40', '约倍问题', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#94defafb-6d8c-4c29-a455-e568120ae45c#f560172e-5bde-4dc7-8ecb-7d8d256e2e40', '23', '2', '0');
INSERT INTO `knowledge` VALUES ('26', 'f2186129-fc11-4c2a-94f1-a102cc731605', '第二章 除不尽', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#f2186129-fc11-4c2a-94f1-a102cc731605', '22', '2', '0');
INSERT INTO `knowledge` VALUES ('27', '009c4105-b1a8-4dd1-ad5a-0de16b718f13', '带余除法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#f2186129-fc11-4c2a-94f1-a102cc731605#009c4105-b1a8-4dd1-ad5a-0de16b718f13', '26', '1', '0');
INSERT INTO `knowledge` VALUES ('28', '1c8c5546-bef8-40ff-ae09-90eecb7f756f', '同余除法', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#f2186129-fc11-4c2a-94f1-a102cc731605#1c8c5546-bef8-40ff-ae09-90eecb7f756f', '26', '2', '0');
INSERT INTO `knowledge` VALUES ('29', '6bf40c8f-dd4a-4f7a-b797-a5db2e2b7e96', '余数的性质', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#f2186129-fc11-4c2a-94f1-a102cc731605#6bf40c8f-dd4a-4f7a-b797-a5db2e2b7e96', '26', '3', '0');
INSERT INTO `knowledge` VALUES ('30', '2915337f-4a61-4422-bfd1-267ccb844cd8', '物不知其数', 'dd276665-fe98-4aea-ab55-6d85b5d384a7#4ff0e781-357d-4938-934f-1446db870ff8#f2186129-fc11-4c2a-94f1-a102cc731605#2915337f-4a61-4422-bfd1-267ccb844cd8', '26', '4', '0');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', 'index', '1', '1', '0', '/index', 'fa fa-home');
INSERT INTO `menu` VALUES ('7', '系统管理', 'system', '0', '50', '0', 'system', 'fa fa-gear');
INSERT INTO `menu` VALUES ('8', '知识库管理', 'knowledge', '1', '5', '0', '/knowledge/knowledgeManage', 'fa fa-book');
INSERT INTO `menu` VALUES ('9', '班级管理', 'classes', '1', '10', '0', '/classes/classesManage', 'fa fa-group');
INSERT INTO `menu` VALUES ('10', '用户管理', 'userManage', '1', '51', '7', '/user/userManage', 'fa fa-user');
INSERT INTO `menu` VALUES ('11', '角色管理', 'roleManage', '1', '52', '7', '/role/roleManage', 'fa fa-user');
INSERT INTO `menu` VALUES ('12', '菜单管理', 'menuManage', '1', '53', '7', '/menu/menuManage', 'fa fa-bars');
INSERT INTO `menu` VALUES ('13', '学生管理', 'student', '1', '15', '0', '/student/studentManage', 'fa fa-graduation-cap');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '管理员');
INSERT INTO `role` VALUES ('2', 'ROLE_USER', '普通用户');
INSERT INTO `role` VALUES ('3', 'ROLE_TEST', '测试');
INSERT INTO `role` VALUES ('17', 'ROLE_TEACHER', '老师');

-- ----------------------------
-- Table structure for r_classes_knowlwdge
-- ----------------------------
DROP TABLE IF EXISTS `r_classes_knowlwdge`;
CREATE TABLE `r_classes_knowlwdge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) DEFAULT NULL,
  `knowledge_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_classes_knowlwdge
-- ----------------------------
INSERT INTO `r_classes_knowlwdge` VALUES ('11', '7', '9');

-- ----------------------------
-- Table structure for r_classes_student
-- ----------------------------
DROP TABLE IF EXISTS `r_classes_student`;
CREATE TABLE `r_classes_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_classes_student
-- ----------------------------
INSERT INTO `r_classes_student` VALUES ('1', '7', '1');
INSERT INTO `r_classes_student` VALUES ('2', '7', '2');

-- ----------------------------
-- Table structure for r_classes_user
-- ----------------------------
DROP TABLE IF EXISTS `r_classes_user`;
CREATE TABLE `r_classes_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classes_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_classes_user
-- ----------------------------
INSERT INTO `r_classes_user` VALUES ('4', '7', '19');
INSERT INTO `r_classes_user` VALUES ('5', '18', '19');
INSERT INTO `r_classes_user` VALUES ('6', '8', '19');
INSERT INTO `r_classes_user` VALUES ('7', '19', '19');
INSERT INTO `r_classes_user` VALUES ('8', '9', '19');
INSERT INTO `r_classes_user` VALUES ('9', '10', '19');
INSERT INTO `r_classes_user` VALUES ('10', '20', '19');
INSERT INTO `r_classes_user` VALUES ('11', '21', '19');
INSERT INTO `r_classes_user` VALUES ('12', '28', '19');
INSERT INTO `r_classes_user` VALUES ('77', '1', '1');
INSERT INTO `r_classes_user` VALUES ('78', '7', '1');
INSERT INTO `r_classes_user` VALUES ('79', '18', '1');
INSERT INTO `r_classes_user` VALUES ('80', '2', '1');
INSERT INTO `r_classes_user` VALUES ('81', '8', '1');
INSERT INTO `r_classes_user` VALUES ('82', '19', '1');
INSERT INTO `r_classes_user` VALUES ('83', '3', '1');
INSERT INTO `r_classes_user` VALUES ('84', '9', '1');
INSERT INTO `r_classes_user` VALUES ('85', '10', '1');
INSERT INTO `r_classes_user` VALUES ('86', '20', '1');
INSERT INTO `r_classes_user` VALUES ('87', '21', '1');
INSERT INTO `r_classes_user` VALUES ('88', '28', '1');
INSERT INTO `r_classes_user` VALUES ('89', '4', '1');
INSERT INTO `r_classes_user` VALUES ('90', '11', '1');
INSERT INTO `r_classes_user` VALUES ('91', '12', '1');
INSERT INTO `r_classes_user` VALUES ('92', '22', '1');
INSERT INTO `r_classes_user` VALUES ('93', '23', '1');
INSERT INTO `r_classes_user` VALUES ('94', '29', '1');
INSERT INTO `r_classes_user` VALUES ('95', '5', '1');
INSERT INTO `r_classes_user` VALUES ('96', '13', '1');
INSERT INTO `r_classes_user` VALUES ('97', '14', '1');
INSERT INTO `r_classes_user` VALUES ('98', '15', '1');
INSERT INTO `r_classes_user` VALUES ('99', '24', '1');
INSERT INTO `r_classes_user` VALUES ('100', '25', '1');
INSERT INTO `r_classes_user` VALUES ('101', '31', '1');
INSERT INTO `r_classes_user` VALUES ('102', '6', '1');
INSERT INTO `r_classes_user` VALUES ('103', '16', '1');
INSERT INTO `r_classes_user` VALUES ('104', '17', '1');
INSERT INTO `r_classes_user` VALUES ('105', '32', '1');
INSERT INTO `r_classes_user` VALUES ('106', '26', '1');
INSERT INTO `r_classes_user` VALUES ('107', '27', '1');
INSERT INTO `r_classes_user` VALUES ('108', '30', '1');

-- ----------------------------
-- Table structure for r_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `r_role_menu`;
CREATE TABLE `r_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK3sq5xkthr6icwcyohtdoje586` (`menu_id`) USING BTREE,
  KEY `FK65h6sd1kud5klymygbfs9crnn` (`role_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of r_role_menu
-- ----------------------------
INSERT INTO `r_role_menu` VALUES ('34', '3', '13');
INSERT INTO `r_role_menu` VALUES ('33', '3', '9');
INSERT INTO `r_role_menu` VALUES ('32', '3', '8');
INSERT INTO `r_role_menu` VALUES ('31', '3', '1');
INSERT INTO `r_role_menu` VALUES ('62', '17', '13');
INSERT INTO `r_role_menu` VALUES ('61', '17', '9');
INSERT INTO `r_role_menu` VALUES ('60', '17', '8');
INSERT INTO `r_role_menu` VALUES ('59', '17', '1');
INSERT INTO `r_role_menu` VALUES ('58', '2', '13');
INSERT INTO `r_role_menu` VALUES ('29', '1', '11');
INSERT INTO `r_role_menu` VALUES ('28', '1', '10');
INSERT INTO `r_role_menu` VALUES ('27', '1', '7');
INSERT INTO `r_role_menu` VALUES ('26', '1', '13');
INSERT INTO `r_role_menu` VALUES ('25', '1', '9');
INSERT INTO `r_role_menu` VALUES ('24', '1', '8');
INSERT INTO `r_role_menu` VALUES ('23', '1', '1');
INSERT INTO `r_role_menu` VALUES ('57', '2', '9');
INSERT INTO `r_role_menu` VALUES ('30', '1', '12');
INSERT INTO `r_role_menu` VALUES ('35', '3', '7');
INSERT INTO `r_role_menu` VALUES ('36', '3', '10');
INSERT INTO `r_role_menu` VALUES ('37', '3', '11');
INSERT INTO `r_role_menu` VALUES ('38', '3', '12');
INSERT INTO `r_role_menu` VALUES ('56', '2', '8');
INSERT INTO `r_role_menu` VALUES ('55', '2', '1');

-- ----------------------------
-- Table structure for r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `r_user_role`;
CREATE TABLE `r_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`role_id`) USING BTREE,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of r_user_role
-- ----------------------------
INSERT INTO `r_user_role` VALUES ('1', '19', '3');
INSERT INTO `r_user_role` VALUES ('2', '18', '2');
INSERT INTO `r_user_role` VALUES ('6', '1', '1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '9', 'test1', '', '1');
INSERT INTO `student` VALUES ('2', '9', 'hahah', '', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_account_non_expired` bit(1) DEFAULT NULL,
  `is_account_non_locked` bit(1) DEFAULT NULL,
  `is_credentials_non_expired` bit(1) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `real_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '', '', '', '', 'admin', '$2a$10$L.K2WsuT1.Za1LRKTNYwOuDIgkmFSJ6O.IKjhKVRhDDle3jHmvO9S', 'aaa', '18734859657', '1', '超级管理员');
INSERT INTO `user` VALUES ('18', '', '', '', '', 'tyf', '$2a$10$DUocwdaRIMR0j1u09ipC7uiefNQ6mr0gtq7h8qxf1opR2T/YUzk.G', null, '18734859657', '1', '覃亚峰');
INSERT INTO `user` VALUES ('19', '', '', '', '', 'test', '$2a$10$yKll5fgYLzvTYCylqyMpQOeb9KT3Chl4clmeYRVEzJV7/iPNiB.NG', null, '18734859657', '0', '测试2');

-- ----------------------------
-- Table structure for wrong_question
-- ----------------------------
DROP TABLE IF EXISTS `wrong_question`;
CREATE TABLE `wrong_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `knowledge_code` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `knowledge_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wrong_question
-- ----------------------------
INSERT INTO `wrong_question` VALUES ('1', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test1', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('3', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test3', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('4', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test4', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('5', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test5', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('6', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test6', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('7', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test7', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('8', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test8', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('9', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test9', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('10', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test10', '1', '2019-08-23 11:26:36', '分数型');
INSERT INTO `wrong_question` VALUES ('11', '67a74d7e-8990-41a8-9569-5a6e8af3a8cb', 'test11', '1', '2019-08-23 11:26:36', '分数型');
SET FOREIGN_KEY_CHECKS=1;
