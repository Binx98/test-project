package com.pet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pet.pojo.*;
import com.pet.service.*;
import com.pet.utils.R;
import com.pet.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/car")
public class ShopCarController {
    @Autowired
    private ShopCarService carService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersDetailService detailService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;

    /**
     * 查询购物车商品列表
     */
    @GetMapping("/list")
    public R list(String accountId) {
        // 查询购物车列表
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        List<ShopCar> list = carService.list(wrapper);

        // 计算总数
        Integer total = 0;
        Integer money = 0;
        for (ShopCar shopCar : list) {
            total += shopCar.getCount();
            money += shopCar.getMoney() * shopCar.getCount();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("total", total);
        resultMap.put("money", money);
        return R.out(ResponseEnum.SUCCESS, resultMap);
    }

    /**
     * 购物车添加商品
     */
    @PostMapping("/add")
    public R add(String accountId, Long goodId, Integer count) {
        // 不能输入 <= 0
        if (count <= 0) {
            return R.out(ResponseEnum.FAIL, "数量不能 <= 0");
        }

        // 判断库存是否足够
        Goods goodPO = goodsService.getById(goodId);
        if (goodPO.getCount() <= count) {
            return R.out(ResponseEnum.FAIL, "商品数量不足，不可加入到购物车");
        }

        // 添加成功，库存数量 - 1
        goodPO.setCount(goodPO.getCount() - count);
        goodsService.updateById(goodPO);

        // 购物车新增记录（重复该数量，不重复新增）
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.eq("good_id", goodId);
        ShopCar carGood = carService.getOne(wrapper);
        if (ObjectUtils.isEmpty(carGood)) {
            ShopCar car = new ShopCar();
            car.setAccountId(accountId);
            car.setGoodId(goodId);
            car.setCount(count);
            car.setGoodUrl(goodPO.getUrl());
            car.setGoodName(goodPO.getName());
            car.setMoney(goodPO.getPrice() * count);
            car.setCreateTime(LocalDateTime.now());
            carService.save(car);
        } else {
            carGood.setCount(carGood.getCount() + count);
            carService.updateById(carGood);
        }
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除购物车商品
     */
    @DeleteMapping("/delete")
    public R remove(String accountId, String goodId) {
        // 查询购物车商品信息
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.eq("good_id", goodId);
        ShopCar car = carService.getOne(wrapper);

        // 删除购物车商品
        carService.remove(wrapper);

        // 商品库存数恢复
        Goods goodPO = goodsService.getById(goodId);
        goodPO.setCount(goodPO.getCount() + car.getCount());
        goodsService.updateById(goodPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 结算购物车
     */
    @Transactional
    @PostMapping("/calc")
    public R calc(String accountId) {
        // 查询购物车所有商品信息
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        List<ShopCar> list = carService.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return R.out(ResponseEnum.FAIL, "购物车中还未添加商品");
        }

        // 计算购物车总金额
        Integer price = 0;
        for (ShopCar shopCar : list) {
            price += shopCar.getMoney() * shopCar.getCount();
        }

        // 判断账户余额是否充足
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("account_id", accountId);
        User userPO = userService.getOne(wrapper1);
        if (userPO.getMoney() < price) {
            return R.out(ResponseEnum.FAIL, "余额不足，请充值");
        }

        // 账户扣费
        userPO.setMoney(userPO.getMoney() - price);
        userService.updateById(userPO);

        // 清空购物车
        QueryWrapper<ShopCar> wrapper2 = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        carService.remove(wrapper2);

        // 保存订单
        Orders order = new Orders();
        order.setStatus(1);
        order.setMoney(price);
        order.setAccountId(accountId);
        order.setCreateTime(LocalDateTime.now());
        orderService.save(order);

        // 批量保存订单详情
        List<OrdersDetail> details = new ArrayList<>();
        for (ShopCar shopCar : list) {
            OrdersDetail detail = new OrdersDetail();
            detail.setOrderId(order.getId());
            detail.setAccountId(accountId);
            detail.setMoney(shopCar.getMoney());
            detail.setCount(shopCar.getCount());
            detail.setGoodId(shopCar.getGoodId());
            detail.setGoodUrl(shopCar.getGoodUrl());
            detail.setGoodName(shopCar.getGoodName());
            detail.setCreateTime(LocalDateTime.now());
            details.add(detail);
        }
        detailService.saveBatch(details);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 购物车：商品数变更
     */
    @PostMapping("/changeCount")
    public R changeCount(String accountId, Long goodId, Integer type) {
        // 查询购物车信息
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.eq("good_id", goodId);
        ShopCar car = carService.getOne(wrapper);

        // 查询商品信息
        Goods good = goodsService.getById(goodId);

        // 加号
        if (type == 1) {
            // 判断库存数量是否充足
            if (good.getCount() <= 0) {
                return R.out(ResponseEnum.FAIL, "库存数量不足");
            }

            car.setCount(car.getCount() + 1);
            carService.updateById(car);
            good.setCount(good.getCount() - 1);
            goodsService.updateById(good);
        }

        // 减号
        else {
            if (car.getCount() == 1) {
                QueryWrapper<ShopCar> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("good_id", good.getId());
                carService.remove(wrapper1);
                good.setCount(good.getCount() + 1);
                goodsService.updateById(good);
            } else {
                car.setCount(car.getCount() - 1);
                carService.updateById(car);
                good.setCount(good.getCount() + 1);
                goodsService.updateById(good);
            }
        }

        return R.out(ResponseEnum.SUCCESS);
    }
}

