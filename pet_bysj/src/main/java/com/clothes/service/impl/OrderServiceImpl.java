package com.clothes.service.impl;

import com.clothes.pojo.Orders;
import com.clothes.mapper.OrderGoodsMapper;
import com.clothes.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderGoodsMapper, Orders> implements OrderService {

}
