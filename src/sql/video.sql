CREATE DATABASE mytest;

CREATE TABLE `t_merchant` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `menchant_id` varchar(32) NOT NULL COMMENT '商户id',
  `menchant_name` varchar(255) DEFAULT NULL COMMENT '商户名称',
  `menchant_addr` varchar(255) DEFAULT NULL COMMENT '商户地址',
  `mobile` varchar(11) DEFAULT NULL COMMENT '商户电话',
  PRIMARY KEY (`id`),
  KEY `menchant_id_index` (`menchant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商户表';

CREATE TABLE `t_vip_codes` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `vip_code` varchar(32) NOT NULL COMMENT 'vip编码',
  `vip_type` int(1) NOT NULL COMMENT 'vip类型 0-免费试用，1-月卡，2-季卡，3-年卡',
  `indate` int(10) NOT NULL COMMENT '有效期 /天',
  `vip_state` int(1) NOT NULL COMMENT '状态 0-失效，1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='Vip表';

CREATE TABLE `t_order` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `order_code` varchar(32) NOT NULL COMMENT '订单编码',
  `order_state` int(1) NOT NULL COMMENT '订单状态 1-待支付，2-已支付，3-支付成功，4-支付失败',
  `order_price` decimal(10,2) NOT NULL COMMENT '订单金额',
  `merchant_id` varchar(32) NOT NULL COMMENT '商户Id',
  `open_id` varchar(32) NOT NULL COMMENT '用户Id',
  `vip_code` varchar(32) NOT NULL COMMENT 'vip编码',
  `vip_state` int(1) NOT NULL COMMENT 'vip状态 0-失效，1-有效',
  `vip_start_time` datetime NOT NULL COMMENT 'vip开始时间',
  `vip_end_time` datetime NOT NULL COMMENT 'vip结束时间',
  `third_oeder_code` varchar(32) DEFAULT NULL COMMENT '三方订单',
  `prepay_id` varchar(255) DEFAULT NULL COMMENT '第三方预支付id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `open_id` varchar(32) NOT NULL COMMENT '用户微信唯一身份识别码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '微信昵称',
  `user_type` int(1) NOT NULL COMMENT '用户类型 0-普通用户，1-商户',
  `menchant_id` varchar(32) NOT NULL COMMENT '商户Id',
  `gender` int(1) DEFAULT NULL COMMENT '性别 0-未知、1-男、2-女',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `country` varchar(255) DEFAULT NULL COMMENT '区',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `t_vip_price` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `vip_type` int(1) NOT NULL COMMENT 'vip类型 0-免费试用，1-月卡，2-季卡，3-年卡',
  `vip_price` decimal(10,2) NOT NULL COMMENT 'vip价格表',
  `indate` int(10) NOT NULL COMMENT '有效期 /天',
  `vip_name` varchar(255) NOT NULL COMMENT 'vip名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Vip价格表';