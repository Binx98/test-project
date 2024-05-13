/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 101102 (10.11.2-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : pet_bysj

 Target Server Type    : MySQL
 Target Server Version : 101102 (10.11.2-MariaDB)
 File Encoding         : 65001

 Date: 13/05/2024 17:47:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for caigou
-- ----------------------------
DROP TABLE IF EXISTS `caigou`;
CREATE TABLE `caigou`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `account_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商id',
  `good_id` bigint NULL DEFAULT NULL COMMENT '供应商商品id',
  `supplier_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '供应商名',
  `good_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `count` int NULL DEFAULT NULL COMMENT '采购数量',
  `money` int NULL DEFAULT NULL COMMENT '进货总价',
  `type` int NULL DEFAULT NULL COMMENT '商品类型',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `material` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '材质',
  `status` int NULL DEFAULT NULL COMMENT '审核状态（1：进行中，2：已完成，3：已拒绝）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of caigou
-- ----------------------------
INSERT INTO `caigou` VALUES (1789939418418470913, '销售员007', 1, 4, '大连服装供应商', '鸭舌帽', 33, 99, 4, NULL, '锦纶、涤纶、晴纶', 2, '2024-05-09 21:42:20');
INSERT INTO `caigou` VALUES (1789944301490118657, '销售员007', 1, 4, '大连服装供应商', '鸭舌帽', 30, 99, 4, NULL, '锦纶、涤纶、晴纶', 3, '2024-05-09 21:42:20');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `money` int NULL DEFAULT NULL COMMENT '出售价',
  `origin_money` int NULL DEFAULT NULL COMMENT '进货价',
  `type` int NULL DEFAULT NULL COMMENT '类型（1：衣服、2：裤子、3：鞋子，4：其他）',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `material` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `stock` int NULL DEFAULT NULL COMMENT '门店库存',
  `total_stock` int NULL DEFAULT NULL COMMENT '仓库库存',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (4, '鸭舌帽', 198, 99, 4, NULL, '锦纶、涤纶、晴纶', 0, 33, '2024-05-13 17:06:46');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `vip_id` bigint NULL DEFAULT NULL COMMENT 'vip账号',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户名',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `money` int NULL DEFAULT NULL COMMENT '总价格',
  `buy_money` int NULL DEFAULT NULL COMMENT '实际成交价格',
  `status` tinyint NULL DEFAULT NULL COMMENT '订单状态（1：进行中，2：完成，3：退货）',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1785150923795587074, NULL, 'qq', NULL, 1476, NULL, 3, NULL, NULL, '2024-04-30 11:35:21');
INSERT INTO `orders` VALUES (1785151964360450050, NULL, 'qq', NULL, 10928, NULL, 3, NULL, NULL, '2024-04-30 11:39:29');
INSERT INTO `orders` VALUES (1785152724284448770, NULL, 'qq', NULL, 20, NULL, 3, NULL, NULL, '2024-04-30 11:42:31');
INSERT INTO `orders` VALUES (1785184501468209154, NULL, 'qq', NULL, 123, NULL, 2, NULL, NULL, '2024-04-30 13:48:47');
INSERT INTO `orders` VALUES (1785184647077666817, NULL, 'qq', NULL, 1107, NULL, 2, NULL, NULL, '2024-04-30 13:49:22');
INSERT INTO `orders` VALUES (1785184733308362754, NULL, 'qq', NULL, 286, NULL, 2, NULL, NULL, '2024-04-30 13:49:42');
INSERT INTO `orders` VALUES (1785188773501050882, NULL, 'qq', NULL, 3198, NULL, 3, NULL, NULL, '2024-04-30 14:05:45');
INSERT INTO `orders` VALUES (1785189789416001538, NULL, 'qq', NULL, 7380, NULL, 3, NULL, NULL, '2024-04-30 14:09:48');
INSERT INTO `orders` VALUES (1785629474105819137, NULL, 'qq', NULL, 123, NULL, 3, NULL, NULL, '2024-05-01 19:16:57');
INSERT INTO `orders` VALUES (1785629582520188930, NULL, 'qq', NULL, 1107, NULL, 3, NULL, NULL, '2024-05-01 19:17:23');
INSERT INTO `orders` VALUES (1786371920498503682, NULL, 'qq', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:27:10');
INSERT INTO `orders` VALUES (1786371995341664258, NULL, 'qq', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:27:28');
INSERT INTO `orders` VALUES (1786372000379023361, NULL, 'qq', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:27:29');
INSERT INTO `orders` VALUES (1786372006330740738, NULL, 'qq', NULL, 300, NULL, 3, NULL, NULL, '2024-05-03 20:27:30');
INSERT INTO `orders` VALUES (1786372048521244674, NULL, 'qq', NULL, 740, NULL, 3, NULL, NULL, '2024-05-03 20:27:40');
INSERT INTO `orders` VALUES (1786375675235250178, NULL, 'wxy', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:42:05');
INSERT INTO `orders` VALUES (1786375680243249153, NULL, 'wxy', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:42:06');
INSERT INTO `orders` VALUES (1786375686807334914, NULL, 'wxy', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:42:08');
INSERT INTO `orders` VALUES (1786375843548475393, NULL, '用户', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:42:45');
INSERT INTO `orders` VALUES (1786376275813445633, NULL, 'xueyan', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:44:28');
INSERT INTO `orders` VALUES (1786376280280379393, NULL, 'xueyan', NULL, 20, NULL, 3, NULL, NULL, '2024-05-03 20:44:29');

-- ----------------------------
-- Table structure for ruchu
-- ----------------------------
DROP TABLE IF EXISTS `ruchu`;
CREATE TABLE `ruchu`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `type` int NULL DEFAULT NULL COMMENT '类型（0：出，1：入）',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ruchu
-- ----------------------------
INSERT INTO `ruchu` VALUES (1789945372425854977, 4, 33, 1, '供货商出库——>总仓库入库', '2024-05-13 17:06:47');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (1, '大连服装供货商', '14521513521', '三丰大厦B', '2024-05-13 14:44:43');
INSERT INTO `supplier` VALUES (2, '沈阳服装供应商', '13214214', '沈阳中街', '2024-04-30 09:12:49');
INSERT INTO `supplier` VALUES (3, '长春服装供应商', '2142153154123', '长春小巷口123', '2024-04-30 11:36:05');
INSERT INTO `supplier` VALUES (4, '哈尔冰服装供货商', '188477423', '黑龙江哈尔滨中央大道', '2024-05-13 15:42:29');

-- ----------------------------
-- Table structure for supplier_good
-- ----------------------------
DROP TABLE IF EXISTS `supplier_good`;
CREATE TABLE `supplier_good`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商id',
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商名',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `type` int NULL DEFAULT NULL COMMENT '商品类型',
  `money` int NULL DEFAULT NULL COMMENT '进货价',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材质',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of supplier_good
-- ----------------------------
INSERT INTO `supplier_good` VALUES (1, 1, '大连服装供应商', 1, '纯棉短袖', 1, 39, NULL, '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (2, 1, '大连服装供应商', 2, '时尚拖鞋', 3, 10, NULL, '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (3, 1, '大连服装供应商', 3, '运动短裤', 2, 100, NULL, '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (4, 1, '大连服装供应商', 4, '鸭舌帽', 4, 99, NULL, '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `account_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号id',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `role` tinyint(1) NULL DEFAULT NULL COMMENT '角色（1：用户，2：员工，3：管理员）',
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登陆状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1782001831691141121, 'yuangong', '123123', '123123', '辽宁省大连', 2, 'N', '2024-04-21 19:01:59');
INSERT INTO `user` VALUES (1782002022385172481, 'jingli', '123123', 'jingli', '辽宁鞍山', 1, 'N', '2024-04-21 19:02:45');
INSERT INTO `user` VALUES (1783750162981281794, '用户1', '1321421421', '123123', '辽宁鞍山', 4, 'N', '2024-04-26 14:49:14');
INSERT INTO `user` VALUES (1786376185870790658, 'xueyan', '1321421421', '123123', '辽宁鞍山', 4, 'N', '2024-05-03 20:44:07');
INSERT INTO `user` VALUES (1789612715072118785, 'xuzhi', '328173717', 'xuzhibin123', '大有文员', 4, 'N', '2024-05-12 19:04:55');
INSERT INTO `user` VALUES (1789614950485082114, '销售员007', '124214214', 'xiaoshouyuan', '大有文员214214', 2, 'Y', '2024-05-12 19:13:48');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `money` int NULL DEFAULT NULL COMMENT '消费金额',
  `level` tinyint(1) NULL DEFAULT NULL COMMENT '会员等级（5个等级，1w元升1级，1级折扣0.1）',
  `discount` double(10, 2) NULL DEFAULT NULL COMMENT '折扣（例如：0.1）',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES (1784099985622183937, '132142121412', '张三', 1000044, 5, 0.50, '大有文员', '2024-04-27 13:59:18');
INSERT INTO `vip` VALUES (1784575043603947522, '132142121412', '张三', 2124214, 5, 0.50, '大有文员', '2024-04-28 21:27:01');
INSERT INTO `vip` VALUES (1785115054216835075, '132142121412', '张三', 123, 2, 0.10, '大有文员', '2024-04-30 09:12:49');
INSERT INTO `vip` VALUES (1785151108298825731, '132142121412', '张三214214', 123123, 5, 0.50, '大有文员', '2024-04-30 11:36:05');
INSERT INTO `vip` VALUES (1789643768096739330, '1832813812', '徐志斌', 10000, 1, 0.90, '第七人民医院', '2024-05-12 21:08:19');
INSERT INTO `vip` VALUES (1789644066655686658, '19238217381', '马中慧', 20000, 2, 0.80, '第七人民医院', '2024-05-12 21:09:30');
INSERT INTO `vip` VALUES (1789650783783186433, '2141214', '徐志斌', 1232131, 5, 0.50, '2151242', '2024-05-12 21:36:12');

SET FOREIGN_KEY_CHECKS = 1;
