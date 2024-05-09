package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Goods;
import com.clothes.pojo.Orders;
import com.clothes.pojo.OrdersDetail;
import com.clothes.service.GoodsService;
import com.clothes.service.OrderService;
import com.clothes.service.OrdersDetailService;
import com.clothes.service.UserService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private OrdersDetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 订单明细
     */
    @PostMapping("/detail")
    public R detailList(Long orderId) {
        QueryWrapper<OrdersDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.orderByDesc("create_time");
        List<OrdersDetail> list = detailService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

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
    @PostMapping("/finish/{id}")
    public R finish(@PathVariable Long id) {
        Orders order = orderService.getById(id);
        // 已完成、已取消订单状态不可调整
        if (order.getStatus().equals(2) || order.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单已处理过，不可再进行操作");
        }

        order.setStatus(3);
        orderService.updateById(order);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public R cancelOrder(Long orderId, String accountId) {
        return R.out(ResponseEnum.SUCCESS);
    }


//    /**
//     * 查询销售流水
//     * <p>
//     * 查询所有订单（已完成订单）
//     * 查询总营业额（已完成订单总价格）
//     * 查询所有商品售卖数量（倒排）
//     */
//    @PostMapping("/total")
//    public R total() {
//        // 查询所有订单（已完成订单）
//        List<Orders> orderList = (List<Orders>) this.getList(null).getData();
//        orderList = orderList.stream()
//                .filter(item -> item.getStatus().equals(3))
//                .collect(Collectors.toList());
//
//        // 查询总营业额
//        Integer totalPrice = 0;
//        for (Orders order : orderList) {
//            totalPrice += order.getMoney();
//        }
//
//        // 查询 热门上新 top3 商品
//        List<Goods> list = goodsService.list().stream().limit(3).collect(Collectors.toList());
//
//        // 封装结果集
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("list", orderList);
//        resultMap.put("money", totalPrice);
//        resultMap.put("top3", list);
//        return R.out(ResponseEnum.SUCCESS, resultMap);
//    }
}

