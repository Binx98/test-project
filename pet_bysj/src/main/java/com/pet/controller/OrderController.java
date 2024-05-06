package com.pet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pet.pojo.Goods;
import com.pet.pojo.Orders;
import com.pet.pojo.OrdersDetail;
import com.pet.pojo.User;
import com.pet.service.GoodsService;
import com.pet.service.OrderService;
import com.pet.service.OrdersDetailService;
import com.pet.service.UserService;
import com.pet.utils.R;
import com.pet.utils.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/detail")
    public R detailList(Long orderId) {
        QueryWrapper<OrdersDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.orderByDesc("create_time");
        List<OrdersDetail> list = detailService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 用户查询订单
     */
    @GetMapping("/list")
    public R getList(String accountId) {
        // 查询订单列表
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(accountId)) {
            wrapper.eq("account_id", accountId);
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
        // 已完成、已取消订单状态不可调整
        Orders orderPO = orderService.getById(orderId);
        if (orderPO.getStatus().equals(2) || orderPO.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单已处理过，不可再进行操作");
        }

        // 修改订单状态：订单状态（1：进行中，2：已取消，3：完成）
        orderPO.setStatus(2);
        orderService.updateById(orderPO);

        // 查询用户余额
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        User userPO = userService.getOne(wrapper);

        // 退款
        userPO.setMoney(userPO.getMoney() + orderPO.getMoney());
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }


    /**
     * 查询销售流水
     * <p>
     * 查询所有订单（已完成订单）
     * 查询总营业额（已完成订单总价格）
     * 查询所有商品售卖数量（倒排）
     */
    @GetMapping("/total")
    public R total() {
        // 查询所有订单（已完成订单）
        List<Orders> orderList = (List<Orders>) this.getList(null).getData();
        orderList = orderList.stream()
                .filter(item -> item.getStatus().equals(3))
                .collect(Collectors.toList());

        // 查询总营业额
        Integer totalPrice = 0;
        for (Orders order : orderList) {
            totalPrice += order.getMoney();
        }

        // 查询 热门上新 top3 商品
        List<Goods> list = goodsService.list().stream().limit(3).collect(Collectors.toList());

        // 封装结果集
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", orderList);
        resultMap.put("money", totalPrice);
        resultMap.put("top3", list);
        return R.out(ResponseEnum.SUCCESS, resultMap);
    }
}

