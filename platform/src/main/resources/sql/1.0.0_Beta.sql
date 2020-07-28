-- --------------------------------------------------------
-- 主机:                           192.168.184.128
-- 服务器版本:                        10.3.11-MariaDB - MariaDB Server
-- 服务器OS:                        Linux
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for aloha_admin
CREATE DATABASE IF NOT EXISTS `aloha_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `aloha_admin`;

-- Dumping structure for table aloha_admin.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `parent_id` char(19) NOT NULL DEFAULT '' COMMENT '父部门主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `order_number` smallint(3) unsigned NOT NULL DEFAULT 999 COMMENT '排序',
  `status` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '状态 0停用 1启用',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- Dumping data for table aloha_admin.department: ~4 rows (大约)
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`, `parent_id`, `name`, `description`, `order_number`, `status`, `create_time`, `update_time`) VALUES
	('1274938253606125570', '0', '开发部门', '开发程序', 999, 1, '2020-06-22 13:32:21', '1970-01-01 08:00:00'),
	('1274939074146205697', '0', '测试部门', '测试部门', 100, 1, '2020-06-22 13:35:37', '1970-01-01 08:00:00'),
	('1274939143452884993', '0', '财务部', '财务部', 200, 1, '2020-06-22 13:35:53', '1970-01-01 08:00:00'),
	('1274939689542893570', '1274939074146205697', '测试部门1', '测试部门2', 999, 1, '2020-06-22 13:38:03', '2020-06-22 13:38:45');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

-- Dumping structure for table aloha_admin.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `parent_id` char(19) NOT NULL DEFAULT '' COMMENT '上级菜单主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单/按钮名称',
  `path` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单路径',
  `permission` varchar(50) NOT NULL DEFAULT '' COMMENT '权限',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '图标',
  `order_number` smallint(3) unsigned NOT NULL DEFAULT 999 COMMENT '排序 1-999 顺序排序',
  `type` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '类型 1目录 2菜单 3按钮',
  `display` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '是否可见 0不可见 1可见',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- Dumping data for table aloha_admin.menu: ~17 rows (大约)
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`id`, `parent_id`, `name`, `path`, `permission`, `icon`, `order_number`, `type`, `display`, `create_time`, `update_time`) VALUES
	('1', '0', '系统管理', '', '', 'layui-icon-setting', 100, 1, 1, '2020-06-07 06:14:15', '2020-06-19 14:52:12'),
	('1269832716489084929', '0', '测试', '', '', 'layui-icon-sync', 999, 1, 1, '2020-06-08 11:24:46', '2020-06-18 13:34:46'),
	('1269955198068011009', '1', '角色管理', '/role/manage', '', '', 200, 2, 1, '2020-06-08 19:31:28', '2020-06-19 14:52:30'),
	('1273489936812691458', '1269832716489084929', '测试菜单', '/test', '', '', 999, 2, 1, '2020-06-18 13:37:15', '1970-01-01 08:00:00'),
	('1273869468526755842', '1', '部门管理', '/department/manage', '', '', 300, 2, 1, '2020-06-19 14:45:23', '2020-06-22 13:31:46'),
	('1274976224694792193', '1', '用户管理', '/user/manage', '', '', 400, 2, 1, '2020-06-22 16:03:14', '1970-01-01 08:00:00'),
	('1285826129269096449', '1269955198068011009', '角色删除', '', 'role:delete', '', 999, 3, 0, '2020-07-22 14:36:53', '2020-07-22 15:56:30'),
	('1285833234709487617', '1269955198068011009', '角色新增', '', 'role:add', '', 999, 3, 0, '2020-07-22 15:05:07', '2020-07-22 15:56:40'),
	('1285845580328730626', '2', '菜单新增', '', 'menu:add', '', 999, 3, 0, '2020-07-22 15:54:10', '1970-01-01 08:00:00'),
	('1285845643809521665', '2', '菜单删除', '', 'menu:delete', '', 999, 3, 0, '2020-07-22 15:54:25', '1970-01-01 08:00:00'),
	('1285845688726323201', '2', '菜单修改', '', 'menu:update', '', 999, 3, 0, '2020-07-22 15:54:36', '1970-01-01 08:00:00'),
	('1285845768682340354', '1269955198068011009', '角色修改', '', 'role:update', '', 999, 3, 0, '2020-07-22 15:54:55', '2020-07-22 15:56:48'),
	('1285845989395005441', '1273869468526755842', '部门新增', '', 'department:add', '', 999, 3, 0, '2020-07-22 15:55:48', '1970-01-01 08:00:00'),
	('1285846044759818241', '1273869468526755842', '部门删除', '', 'department:delete', '', 999, 3, 0, '2020-07-22 15:56:01', '1970-01-01 08:00:00'),
	('1285846085847220226', '1273869468526755842', '部门修改', '', 'department:update', '', 999, 3, 0, '2020-07-22 15:56:11', '1970-01-01 08:00:00'),
	('1285846326940008450', '1274976224694792193', '用户新增', '', 'user:add', '', 999, 3, 0, '2020-07-22 15:57:08', '1970-01-01 08:00:00'),
	('1285846375929479170', '1274976224694792193', '用户删除', '', 'user:delete', '', 999, 3, 0, '2020-07-22 15:57:20', '1970-01-01 08:00:00'),
	('1285846449027809281', '1274976224694792193', '用户修改', '', 'user:update', '', 999, 3, 0, '2020-07-22 15:57:37', '1970-01-01 08:00:00'),
	('2', '1', '菜单管理', '/menu/manage', '', '', 100, 2, 1, '2020-06-07 06:14:26', '2020-06-19 14:52:23');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;

-- Dumping structure for table aloha_admin.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- Dumping data for table aloha_admin.role: ~2 rows (大约)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`, `description`, `create_time`, `update_time`) VALUES
	('1273857755014275073', '管理员', '超级管理员', '2020-06-19 13:58:50', '2020-07-22 18:35:14'),
	('1273862449468071937', '测试', '测试', '2020-06-19 14:17:29', '2020-06-19 14:19:17');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table aloha_admin.role_menu
CREATE TABLE IF NOT EXISTS `role_menu` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `role_id` char(19) NOT NULL DEFAULT '' COMMENT '角色主键',
  `menu_id` char(19) NOT NULL DEFAULT '' COMMENT '菜单主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关系表';

-- Dumping data for table aloha_admin.role_menu: ~19 rows (大约)
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` (`id`, `role_id`, `menu_id`) VALUES
	('1273862903128186881', '1273862449468071937', '1269832716489084929'),
	('1273862903161741314', '1273862449468071937', '1273489936812691458'),
	('1285886114023882753', '1273857755014275073', '1'),
	('1285886114036465666', '1273857755014275073', '2'),
	('1285886114036465667', '1273857755014275073', '1285845688726323201'),
	('1285886114036465668', '1273857755014275073', '1285845643809521665'),
	('1285886114044854273', '1273857755014275073', '1285845580328730626'),
	('1285886114049048577', '1273857755014275073', '1269955198068011009'),
	('1285886114053242882', '1273857755014275073', '1285845768682340354'),
	('1285886114057437185', '1273857755014275073', '1285833234709487617'),
	('1285886114065825794', '1273857755014275073', '1285826129269096449'),
	('1285886114070020097', '1273857755014275073', '1273869468526755842'),
	('1285886114070020098', '1273857755014275073', '1285846085847220226'),
	('1285886114070020099', '1273857755014275073', '1285846044759818241'),
	('1285886114070020100', '1273857755014275073', '1285845989395005441'),
	('1285886114078408705', '1273857755014275073', '1274976224694792193'),
	('1285886114078408706', '1273857755014275073', '1285846449027809281'),
	('1285886114078408707', '1273857755014275073', '1285846375929479170'),
	('1285886114082603009', '1273857755014275073', '1285846326940008450');
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;

-- Dumping structure for table aloha_admin.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(200) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `department_id` char(19) NOT NULL DEFAULT '' COMMENT '部门主键',
  `sex` tinyint(1) unsigned NOT NULL DEFAULT 0 COMMENT '性别 0男 1女',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  `status` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '状态 0锁定 1有效',
  `is_tab` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '是否开启tab 0关闭 1开启',
  `theme` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '主题 0黑 1白',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- Dumping data for table aloha_admin.user: ~3 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `nick_name`, `department_id`, `sex`, `email`, `phone`, `status`, `is_tab`, `theme`, `description`, `create_time`, `update_time`) VALUES
	('1275290449589878785', 'test', '$2a$10$zMlr4jFimbHW2DjIFtyCKOAfk1zQlTzdIgP4jrwUs7aWp.ndtDJ0i', '测试人员', '1274939074146205697', 1, 'test@qq.com', '12312345678', 1, 1, 1, '测试人员', '2020-06-23 12:51:51', '2020-07-28 11:17:21'),
	('1277461732029124609', 'admin', '$2a$10$DQmtBLlJBIy6udD3N24odeQnlX1QpBzqMEP2YrfeUdwco3fK2wVXq', '管理员', '1274938253606125570', 0, '', '', 1, 1, 1, '', '2020-06-29 12:39:45', '2020-07-22 18:19:05');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table aloha_admin.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键',
  `role_id` char(19) NOT NULL DEFAULT '' COMMENT '角色主键',
  `user_id` char(19) NOT NULL DEFAULT '' COMMENT '用户主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色关联表';

-- Dumping data for table aloha_admin.user_role: ~2 rows (大约)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`id`, `role_id`, `user_id`) VALUES
	('1285882049365819394', '1273857755014275073', '1277461732029124609'),
	('1287950242624311297', '1273862449468071937', '1275290449589878785');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
