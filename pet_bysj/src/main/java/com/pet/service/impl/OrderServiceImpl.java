package com.pet.service.impl;

import com.pet.pojo.Orders;
import com.pet.mapper.OrderGoodsMapper;
import com.pet.service.OrderService;
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
