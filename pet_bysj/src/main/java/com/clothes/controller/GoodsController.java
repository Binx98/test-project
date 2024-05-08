package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.clothes.pojo.*;
import com.clothes.service.*;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ✔
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/good")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ShopCarService carService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersDetailService detailService;
    @Autowired
    private PayService payService;
    @Autowired
    private UserService userService;


    /**
     * 修改库存
     */
    @PostMapping("/changeCount")
    public R save(Long goodId, Integer changeCount, Integer type) {
        if (changeCount < 0) {
            return R.out(ResponseEnum.FAIL, "不能输入负数");
        }

        Goods good = goodsService.getById(goodId);
        if (type == 1) {
            good.setCount(good.getCount() + changeCount);
            goodsService.updateById(good);
        } else {
            if (good.getCount() < changeCount) {
                return R.out(ResponseEnum.FAIL, "库存数量不足");
            } else {
                good.setCount(good.getCount() - changeCount);
                goodsService.updateById(good);
            }
        }
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 发布商品
     */
    @PostMapping("/save")
    public R save(@RequestBody Goods goods) {
        goodsService.save(goods);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询商品列表（根据商品名关键字查询）
     */
    @GetMapping("/list")
    public R getList(String goodName, Integer type) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(goodName)) {
            wrapper.like("name", goodName);
        }
        if (ObjectUtils.isNotEmpty(type)) {
            wrapper.like("type", type);
        }
        wrapper.orderByDesc("create_time");
        List<Goods> goods = goodsService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goods);
    }

    /**
     * 查询商品详情
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Long id) {
        Goods good = goodsService.getById(id);
        return R.out(ResponseEnum.SUCCESS, good);
    }

    /**
     * 修改用品
     */
    @PostMapping("/update")
    public R update(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除商品（下架商品）
     */
    @DeleteMapping("/delete/{goodId}")
    public R deleteMenu(@PathVariable Long goodId) {
        // 删除商品
        goodsService.removeById(goodId);

        // 删除所有购物车记录
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("good_id", goodId);
        List<ShopCar> list = carService.list(wrapper);
        carService.remove(wrapper);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 下单用品
     */
    @PostMapping("/buy/{goodId}/{accountId}")
    public R save(@PathVariable Long goodId, @PathVariable String accountId) {
//        // 校验商品数量是否充足
//        Goods goodPO = goodsService.getById(goodId);
//        if (goodPO.getCount() <= 0) {
//            return R.out(ResponseEnum.FAIL, "商品库存不足，不可购买");
//        }
//
//        // 查询用户余额
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("account_id", accountId);
//        User user = userService.getOne(wrapper);
//
//        // 查询商品价格
//        Goods good = goodsService.getById(goodId);
//        Integer price = good.getPrice();
//
//        // 判断账户余额是否充足
//        if (money < price) {
//            return R.out(ResponseEnum.FAIL, "余额不足，请充值！");
//        }
//
//        // 余额减去商品价格 差价
//        int moneys = money - price;
//        user.setMoney(moneys);
//        userService.updateById(user);
//
//        // 下单 (保存)
//        Orders order = new Orders();
//        order.setStatus(1);
//        order.setMoney(goodPO.getPrice());
//        order.setAccountId(accountId);
//        order.setCreateTime(LocalDateTime.now());
//        orderService.save(order);
//
//        // 保存订单明细
//        OrdersDetail detail = new OrdersDetail();
//        detail.setAccountId(accountId);
//        detail.setOrderId(order.getId());
//        detail.setGoodName(good.getName());
//        detail.setGoodUrl(good.getUrl());
//        detail.setGoodId(goodId);
//        detail.setCount(1);
//        detail.setMoney(good.getPrice());
//        detail.setCreateTime(LocalDateTime.now());
//        detailService.save(detail);
//
//        // 保存扣费明细
//        Pay pay = new Pay();
//        pay.setAccountId(order.getAccountId());
//        pay.setOrderId(order.getId());
//        pay.setCreateTime(LocalDateTime.now());
//        pay.setMoney(goodPO.getPrice());
//        payService.save(pay);
//
//        // 库存数量 - 1
//        good.setCount(good.getCount() - 1);
//        goodsService.updateById(good);
        return R.out(ResponseEnum.SUCCESS, "下单成功");
    }
}

