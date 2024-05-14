package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Goods;
import com.clothes.pojo.Orders;
import com.clothes.pojo.Ruchu;
import com.clothes.pojo.SupplierGood;
import com.clothes.service.GoodsService;
import com.clothes.service.OrderService;
import com.clothes.service.RuchuService;
import com.clothes.service.SupplierGoodService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    private GoodsService goodsService;
    @Autowired
    private RuchuService ruchuService;
    @Autowired
    private SupplierGoodService supplierGoodService;

    /**
     * 查询退货订单
     */
    @PostMapping("/backList")
    public R backList() {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 3);
        wrapper.orderByDesc("create_time");
        List<Orders> goodOrderList = orderService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goodOrderList);
    }

    /**
     * 查询销售单列表
     */
    @PostMapping("/list")
    public R getList(Long orderId, String userName) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(orderId)) {
            wrapper.like("id", orderId);
        }
        if (ObjectUtils.isNotEmpty(userName)) {
            wrapper.like("user_name", userName);
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
    public R finish(Long id) {
        // 已完成、已取消订单状态不可调整
        Orders order = orderService.getById(id);
        if (order.getStatus().equals(2) || order.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单已处理完毕，不可再进行操作");
        }

        // 修改订单状态
        order.setStatus(2);
        orderService.updateById(order);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 退货
     */
    @PostMapping("/back")
    public R cancelOrder(Long id) {
        // 已完成、已取消订单状态不可调整
        Orders order = orderService.getById(id);
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
        ruchu.setNote("消费者退货——>总仓库入库");
        ruchuService.save(ruchu);

        // 总仓库数量调整
        Goods goods = goodsService.getById(order.getGoodId());
        if (ObjectUtils.isEmpty(goods)) {
            SupplierGood supplierGood = supplierGoodService.getById(order.getGoodId());
            Goods good = new Goods();
            good.setId(supplierGood.getId());
            good.setName(supplierGood.getGoodName());
            good.setStock(0);
            good.setTotalStock(order.getCount());
            good.setUrl(supplierGood.getUrl());
            good.setOriginMoney(supplierGood.getMoney());
            good.setMoney(supplierGood.getMoney() * 2);
            good.setCreateTime(LocalDateTime.now());
            good.setType(supplierGood.getType());
            good.setMaterial(supplierGood.getMaterial());
            goodsService.save(goods);
        } else {
            goods.setTotalStock(goods.getTotalStock() + order.getCount());
            goodsService.updateById(goods);
        }
        return R.out(ResponseEnum.SUCCESS);
    }


    /**
     * 删除订单
     */
    @PostMapping("/delete")
    @Transactional
    public R delete(Long id) {
        // 已完成、已取消订单状态不可调整
        orderService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

