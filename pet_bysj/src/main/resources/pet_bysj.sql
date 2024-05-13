/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : pet_bysj

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 12/05/2024 21:41:21
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
  `good_id` bigint NULL DEFAULT NULL COMMENT '供应商商品id',
  `supplier_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '供应商名',
  `good_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `count` int NULL DEFAULT NULL COMMENT '采购数量',
  `money` int NULL DEFAULT NULL COMMENT '进货总价',
  `type` int NULL DEFAULT NULL COMMENT '商品类型',
  `status` int NULL DEFAULT NULL COMMENT '审核状态（1：进行中，2：已完成，3：已拒绝）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of caigou
-- ----------------------------
INSERT INTO `caigou` VALUES (1784575030169591810, 'qq', 1, '大连服装供应商', '纯棉短袖', 1, 39, 1, 1, '2024-04-28 21:26:58');
INSERT INTO `caigou` VALUES (1785115073149923329, 'qq', 2, '沈阳服装供应商', '时尚拖鞋', 1, 10, 3, 2, '2024-04-30 09:12:54');
INSERT INTO `caigou` VALUES (1785151100379979778, 'qq', 3, '长春服装供应商', '运动短裤', 1, 100, 2, 2, '2024-04-30 11:36:03');
INSERT INTO `caigou` VALUES (1785629707539808257, 'qq', 4, '哈尔滨服装供应商', '鸭舌帽', 1, 99, 4, 3, '2024-05-01 19:17:52');
INSERT INTO `caigou` VALUES (1786376259724095490, 'xueyan', 1, '大连服装供应商', '纯棉短袖', 1, 39, 1, 3, '2024-05-03 20:44:24');

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
INSERT INTO `goods` VALUES (1786366905230798849, '狗狗玩具', 40, 2, 2, 'http://localhost:9000/bysj/微信图片_20240426141013_1714737996947.jpg', '狗狗铃铛玩具', 3, 10, '2024-05-03 20:07:14');
INSERT INTO `goods` VALUES (1786367045819674626, '猫猫玩具', 40, 2, 2, 'http://localhost:9000/bysj/微信图片_20240426141018_1714738065955.jpg', '猫猫多色玩具', 3, 10, '2024-05-03 20:07:47');
INSERT INTO `goods` VALUES (1786367518152830978, '猫窝', 120, 2, 2, 'http://localhost:9000/bysj/微信图片_20240426141007_1714738104523.jpg', '猫猫温暖猫窝、不起静电', 3, 10, '2024-05-03 20:09:40');
INSERT INTO `goods` VALUES (1786367752652173313, '恩倍多猫咪体外驱虫药', 65, 2, 3, 'http://localhost:9000/bysj/微信图片_20240426135648_1714738234102.jpg', '3支恩倍多猫咪体外驱虫药除跳蚤杀去虫幼成猫宠物药虱子驱虫滴剂', 3, 10, '2024-05-03 20:10:36');
INSERT INTO `goods` VALUES (1786368203112034305, '复方酮康唑软膏', 65, 2, 3, 'http://localhost:9000/bysj/微信图片_20240426135644_1714738341779.jpg', '宠物猫癣外用药皮肤病药膏狗狗猫咪真菌猫藓专用药', 3, 10, '2024-05-03 20:12:23');
INSERT INTO `goods` VALUES (1786368558575104001, '软骨素', 138, 2, 3, 'http://localhost:9000/bysj/微信图片_20240426135132_1714738452267.jpg', 'vetwish唯特适折耳猫专用鲨鱼软骨素猫咪专用宠物保护关节生40片', 3, 10, '2024-05-03 20:13:48');
INSERT INTO `goods` VALUES (1786369059479859201, '宠物生日蛋糕', 260, 2, 1, 'http://localhost:9000/bysj/微信图片_20240426140913_1714738562092.jpg', '宠物生日蛋糕样式自选', 3, 10, '2024-05-03 20:15:48');
INSERT INTO `goods` VALUES (1786369297246564353, '骨头小蛋糕', 60, 2, 1, 'http://localhost:9000/bysj/微信图片_20240426140919_1714738601665.jpg', '可爱骨头小蛋糕', 3, 10, '2024-05-03 20:16:44');
INSERT INTO `goods` VALUES (1786369827201069057, '烘焙粮', 300, 2, 1, 'http://localhost:9000/bysj/微信图片_20240426141025_1714738729919.jpg', '新鲜烘焙粮，注意保质期', 3, 10, '2024-05-03 20:18:51');
INSERT INTO `goods` VALUES (1786370168206372866, '饼干', 20, 2, 1, 'http://localhost:9000/bysj/微信图片_20240426141031_1714738800834.jpg', '新鲜烘焙甜甜圈饼干', 3, 10, '2024-05-03 20:20:12');

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
  `note` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '说明',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ruchu
-- ----------------------------

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
INSERT INTO `supplier` VALUES (1, '大连服装供应商', NULL, '大连甘井子', '2024-04-28 21:27:01');
INSERT INTO `supplier` VALUES (2, '沈阳服装供应商', NULL, '沈阳中街', '2024-04-30 09:12:49');
INSERT INTO `supplier` VALUES (3, '长春服装供应商', NULL, '长春小巷口', '2024-04-30 11:36:05');

-- ----------------------------
-- Table structure for supplier_good
-- ----------------------------
DROP TABLE IF EXISTS `supplier_good`;
CREATE TABLE `supplier_good`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商id',
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '供应商名',
  `good_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品名称',
  `type` int NULL DEFAULT NULL COMMENT '商品类型',
  `money` int NULL DEFAULT NULL COMMENT '进货价',
  `url` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品图片',
  `material` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '材质',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier_good
-- ----------------------------
INSERT INTO `supplier_good` VALUES (1, 1, '大连服装供应商', '纯棉短袖', 1, 39, NULL, '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (2, 2, '沈阳服装供应商', '时尚拖鞋', 3, 10, NULL, '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (3, 3, '长春服装供应商', '运动短裤', 2, 100, NULL, '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (4, 4, '哈尔滨服装供应商', '鸭舌帽', 4, 99, NULL, '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `account_id` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '账号id',
  `phone` varchar(255) CHARACTER SET utf8mb3  DEFAULT NULL COMMENT '邮件',
  `password` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '地址',
  `role` tinyint(1) NULL DEFAULT NULL COMMENT '角色（1：用户，2：员工，3：管理员）',
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登陆状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1782001831691141121, 'yuangong', '1321421421', '123123', '辽宁省大连', 2, 'N', '2024-04-21 19:01:59');
INSERT INTO `user` VALUES (1782002022385172481, 'guanliyuan', '1321421421', '123123', '辽宁鞍山', 1, 'N', '2024-04-21 19:02:45');
INSERT INTO `user` VALUES (1783750162981281794, '用户', '1321421421', '123123', '辽宁鞍山', 4, 'N', '2024-04-26 14:49:14');
INSERT INTO `user` VALUES (1786376185870790658, 'xueyan', '1321421421', '123123', '辽宁鞍山', 4, 'N', '2024-05-03 20:44:07');
INSERT INTO `user` VALUES (1789612715072118785, 'xuzhibin123', '328173717', 'xuzhibin123', '大有文员', 4, 'N', '2024-05-12 19:04:55');
INSERT INTO `user` VALUES (1789614950485082114, '球球', '6666666', '6666666', '大有', 2, 'N', '2024-05-12 19:13:48');
INSERT INTO `user` VALUES (1789650375694184450, '12312321', '3123213', '123213', '1231', 2, 'N', '2024-05-12 21:34:34');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `phone` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '手机号',
  `user_name` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '姓名',
  `money` int NULL DEFAULT NULL COMMENT '消费金额',
  `level` tinyint(1) NULL DEFAULT NULL COMMENT '会员等级（5个等级，1w元升1级，1级折扣0.1）',
  `discount` double(10, 2) NULL DEFAULT NULL COMMENT '折扣（例如：0.1）',
  `address` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES (1783751571294347265, '132142121412', '张三', 1000, 2, 0.10, '大有文员', '2024-04-26 14:54:50');
INSERT INTO `vip` VALUES (1783752435975282689, '132142121412', '张三', 10000, 1, 0.10, '大有文员', '2024-04-26 14:58:16');
INSERT INTO `vip` VALUES (1784028736715882498, '132142121412', '张三', 400, 3, 0.10, '大有文员', '2024-04-27 09:16:11');
INSERT INTO `vip` VALUES (1784099978215043073, '132142121412', '张三', 100, 3, 0.10, '大有文员', '2024-04-27 13:59:16');
INSERT INTO `vip` VALUES (1784099985622183937, '132142121412', '张三', 100, 2, 0.10, '大有文员', '2024-04-27 13:59:18');
INSERT INTO `vip` VALUES (1784575030236700674, '132142121412', '张三', 1, 5, 0.10, '大有文员', '2024-04-28 21:26:58');
INSERT INTO `vip` VALUES (1784575043603947522, '132142121412', '张三', 2, 2, 0.10, '大有文员', '2024-04-28 21:27:01');
INSERT INTO `vip` VALUES (1785115054216835075, '132142121412', '张三', 123, 2, 0.10, '大有文员', '2024-04-30 09:12:49');
INSERT INTO `vip` VALUES (1785151108298825731, '132142121412', '张三', 123123, 2, 0.10, '大有文员', '2024-04-30 11:36:05');
INSERT INTO `vip` VALUES (1789643768096739330, '1832813812', '徐志斌', 10000, 1, 0.90, '第七人民医院', '2024-05-12 21:08:19');
INSERT INTO `vip` VALUES (1789644066655686658, '19238217381', '马中慧', 20000, 2, 0.80, '第七人民医院', '2024-05-12 21:09:30');
INSERT INTO `vip` VALUES (1789650783783186433, '214121421', '徐志斌33', 1232131321, 5, 0.50, '215124214', '2024-05-12 21:36:12');
INSERT INTO `vip` VALUES (1789651649072226305, '12421213123', '12421421421', 112412, 5, 0.50, '141221321', '2024-05-12 21:39:38');

SET FOREIGN_KEY_CHECKS = 1;
