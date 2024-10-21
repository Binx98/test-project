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

 Date: 21/10/2024 14:33:07
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
  `money` int NULL DEFAULT NULL COMMENT '进货单价',
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
INSERT INTO `caigou` VALUES (1789939418418470913, '销售员007', 1, 4, '大连服装供应商', '鸭舌帽', 33, 99, 4, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', 4, '2024-05-09 21:42:20');
INSERT INTO `caigou` VALUES (1789944301490118657, '销售员007', 1, 4, '大连服装供应商', '鸭舌帽', 30, 99, 4, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', 4, '2024-05-09 21:42:20');
INSERT INTO `caigou` VALUES (1789958890755837954, '销售员007', 1, 3, '大连服装供应商', '运动短裤', 22, 100, 2, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', 4, '2024-05-09 21:41:37');
INSERT INTO `caigou` VALUES (1789959566927974401, '销售员007', 1, 2, '大连服装供应商', '时尚拖鞋', 33, 10, 3, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', 4, '2024-05-09 21:40:38');
INSERT INTO `caigou` VALUES (1789959942079012865, '销售员007', 1, 3, '大连服装供应商', '运动短裤', 22, 100, 2, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', 4, '2024-05-13 18:04:40');
INSERT INTO `caigou` VALUES (1790286597603577858, 'caigou', 1, 4, '大连服装供应商', '鸭舌帽', 10, 99, 4, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', 4, '2024-05-14 15:42:41');

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
  `size` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (2, '时尚拖鞋', 20, 10, 3, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', 6, 16, NULL, '2024-05-14 09:20:47');
INSERT INTO `goods` VALUES (3, '运动短裤', 180, 100, 2, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', 3, 2, NULL, '2024-05-14 09:20:48');
INSERT INTO `goods` VALUES (4, '鸭舌帽', 198, 99, 4, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', 27, 76, NULL, '2024-05-13 17:06:46');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `vip_id` bigint NULL DEFAULT NULL COMMENT 'vip标识',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '客户名',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品id',
  `good_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片',
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
INSERT INTO `orders` VALUES (1790270184818069505, NULL, '张三', 4, '鸭舌帽', 'http://localhost:9000/bysj/1701237756321.jpg', 594, 594, 3, 3, '沈阳小胡同', '2024-05-14 14:37:28');
INSERT INTO `orders` VALUES (1790270253852119041, NULL, '没头脑', 2, '时尚拖鞋', 'http://localhost:9000/bysj/1701237756321.jpg', 220, 220, 2, 11, '大连', '2024-05-14 14:37:44');
INSERT INTO `orders` VALUES (1790270700910399490, 1789644066655686658, '马中慧', 3, '运动短裤', 'http://localhost:9000/bysj/1701237756321.jpg', 540, 432, 2, 3, '第七人民医院', '2024-05-14 14:39:31');

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
INSERT INTO `ruchu` VALUES (1789958351150239746, 4, 33, 1, '供货商出库——>总仓库入库', '2024-05-13 17:58:21');
INSERT INTO `ruchu` VALUES (1789958717493334018, 4, 30, 1, '供货商出库——>总仓库入库', '2024-05-13 17:59:48');
INSERT INTO `ruchu` VALUES (1790190490626142209, 2, 33, 1, '供货商出库——>总仓库入库', '2024-05-14 09:20:47');
INSERT INTO `ruchu` VALUES (1790190491930570753, 3, 22, 1, '供货商出库——>总仓库入库', '2024-05-14 09:20:48');
INSERT INTO `ruchu` VALUES (1790190495860633601, 3, 22, 1, '供货商出库——>总仓库入库', '2024-05-14 09:20:49');
INSERT INTO `ruchu` VALUES (1790190524134436865, 3, 20, 1, '总仓库出库——>门店仓库入库', '2024-05-14 09:20:55');
INSERT INTO `ruchu` VALUES (1790191428971638786, 3, 14, 1, '总仓库出库——>门店仓库入库', '2024-05-14 09:24:31');
INSERT INTO `ruchu` VALUES (1790206277994790914, 3, 19, 1, '总仓库出库——>门店仓库入库', '2024-05-14 10:23:31');
INSERT INTO `ruchu` VALUES (1790214154398588930, 2, 20, 1, '总仓库出库——>门店仓库入库', '2024-05-14 10:54:49');
INSERT INTO `ruchu` VALUES (1790214168084602882, 3, 11, 1, '总仓库出库——>门店仓库入库', '2024-05-14 10:54:52');
INSERT INTO `ruchu` VALUES (1790214192147324930, 4, 38, 1, '总仓库出库——>门店仓库入库', '2024-05-14 10:54:58');
INSERT INTO `ruchu` VALUES (1790248034694451202, 2, 3, 1, '消费者退货——>总仓库入库', '2024-05-14 13:09:27');
INSERT INTO `ruchu` VALUES (1790248304178483201, 3, 2, 1, '消费者退货——>总仓库入库', '2024-05-14 13:10:31');
INSERT INTO `ruchu` VALUES (1790255722312503298, 4, 5, 1, '消费者退货——>总仓库入库', '2024-05-14 13:40:00');
INSERT INTO `ruchu` VALUES (1790270274907525122, 4, 3, 1, '消费者退货——>总仓库入库', '2024-05-14 14:37:49');
INSERT INTO `ruchu` VALUES (1790287469322555393, 4, 10, 1, '供货商出库——>总仓库入库', '2024-05-14 15:46:09');

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
INSERT INTO `supplier_good` VALUES (1, 1, '大连服装供应商', 1, '纯棉短袖', 1, 39, 'http://localhost:9000/bysj/1701237756321.jpg', '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (2, 1, '大连服装供应商', 2, '时尚拖鞋', 3, 10, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (3, 1, '大连服装供应商', 3, '运动短裤', 2, 100, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (4, 1, '大连服装供应商', 4, '鸭舌帽', 4, 99, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');
INSERT INTO `supplier_good` VALUES (5, 2, '沈阳服装供应商', 1, '短袖', 1, 39, 'http://localhost:9000/bysj/1701237756321.jpg', '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (6, 2, '沈阳服装供应商', 2, '拖鞋', 3, 10, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (7, 2, '沈阳服装供应商', 3, '短裤', 2, 100, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (8, 2, '沈阳服装供应商', 4, '帽子', 4, 99, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');
INSERT INTO `supplier_good` VALUES (9, 3, '长春服装供应商', 1, '可爱短袖', 1, 39, 'http://localhost:9000/bysj/1701237756321.jpg', '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (10, 3, '长春服装供应商', 2, '人字拖鞋', 3, 10, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (11, 3, '长春服装供应商', 3, '运动短裤', 2, 100, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (12, 3, '长春服装供应商', 4, '运动帽子', 4, 99, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');
INSERT INTO `supplier_good` VALUES (13, 4, '哈尔冰服装供货商', 1, '哈尔冰短袖', 1, 39, 'http://localhost:9000/bysj/1701237756321.jpg', '100%棉', '2024-05-09 21:39:30');
INSERT INTO `supplier_good` VALUES (14, 4, '哈尔冰服装供货商', 2, '哈尔冰拖鞋', 3, 10, 'http://localhost:9000/bysj/1701237756321.jpg', '塑料', '2024-05-09 21:40:38');
INSERT INTO `supplier_good` VALUES (15, 4, '哈尔冰服装供货商', 3, '哈尔冰短裤', 2, 100, 'http://localhost:9000/bysj/1701237756321.jpg', '棉、涤纶', '2024-05-09 21:41:37');
INSERT INTO `supplier_good` VALUES (16, 4, '哈尔冰服装供货商', 4, '哈尔冰帽子', 4, 99, 'http://localhost:9000/bysj/1701237756321.jpg', '锦纶、涤纶、晴纶', '2024-05-09 21:42:20');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `account_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号id',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地址',
  `role` tinyint(1) NULL DEFAULT NULL COMMENT '角色（1：经理，2：销售，3：库存、4：采购员）',
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登陆状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1782002022385172481, 'jingli', '123123', 'jingli', '辽宁鞍山', 1, 'N', '2024-04-21 19:02:45');
INSERT INTO `user` VALUES (1786376185870790658, 'caigou', '18875647563', 'caigou', '辽宁鞍山', 4, 'Y', '2024-05-03 20:44:07');
INSERT INTO `user` VALUES (1789612715072118785, 'kucun', '100954837', 'kucun', '吉林菜市场', 3, 'N', '2024-05-12 19:04:55');
INSERT INTO `user` VALUES (1789614950485082114, 'xiaoshou', '18895874837', 'xiaoshou', '辽宁大连', 2, 'N', '2024-05-12 19:13:48');

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
INSERT INTO `vip` VALUES (1789643768096739330, '1832813812', '徐志斌', 10000, 1, 0.90, '第七人民医院', '2024-05-12 21:08:19');
INSERT INTO `vip` VALUES (1789650783783186433, '2141214', '徐志斌', 1232131, 5, 0.50, '2151242', '2024-05-12 21:36:12');
INSERT INTO `vip` VALUES (1789959420697759746, '12421421', '12321321', 21321421, 5, 0.50, '213214', '2024-05-13 18:02:36');

SET FOREIGN_KEY_CHECKS = 1;
