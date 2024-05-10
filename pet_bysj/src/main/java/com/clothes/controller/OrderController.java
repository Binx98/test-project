package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Orders;
import com.clothes.pojo.Ruchu;
import com.clothes.service.OrderService;
import com.clothes.service.RuchuService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RuchuService ruchuService;

    /**
     * 查询销售单列表
     */
    @PostMapping("/list")
    public R getList(Long id, String userName) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        if (ObjectUtils.isEmpty(userName)) {
            wrapper.eq("user_name", userName);
        }
        wrapper.orderByDesc("create_time");
        List<Orders> goodOrderList = orderService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goodOrderList);
    }

    /**
     * 完成订单
     */
    @PostMapping("/finish")
    @Transactional
    public R finish(Long orderId) {
        // 已完成、已取消订单状态不可调整
        Orders order = orderService.getById(orderId);
        if (order.getStatus().equals(2) || order.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单已处理过，不可再进行操作");
        }

        // 修改订单状态
        order.setStatus(2);
        orderService.updateById(order);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public R cancelOrder(Long orderId) {
        // 已完成、已取消订单状态不可调整
        Orders order = orderService.getById(orderId);
        if (order.getStatus().equals(2) || order.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单已处理过，不可再进行操作");
        }

        // 修改订单状态
        order.setStatus(3);
        orderService.updateById(order);

        // 商品退回库存
        Ruchu ruchu = new Ruchu();
        ruchu.setCount(order.getCount());
        ruchu.setType(1);
        ruchu.setGoodId(order.getGoodId());
        ruchu.setNote("消费者退货——>门店仓库入库");
        ruchuService.save(ruchu);
        return R.out(ResponseEnum.SUCCESS);
    }
}

