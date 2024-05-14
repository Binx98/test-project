package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.clothes.pojo.Goods;
import com.clothes.pojo.Orders;
import com.clothes.pojo.Vip;
import com.clothes.service.GoodsService;
import com.clothes.service.OrderService;
import com.clothes.service.VipService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private VipService vipService;
    @Autowired
    private OrderService orderService;

    /**
     * 查询商品列表（根据商品名关键字查询）
     */
    @PostMapping("/list")
    public R getList(String goodName, Integer type) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(goodName)) {
            wrapper.like("name", goodName);
        }
        if (ObjectUtils.isNotEmpty(type)) {
            wrapper.like("type", type);
        }
        wrapper.ne("stock", 0);
        wrapper.orderByDesc("create_time");
        List<Goods> goods = goodsService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goods);
    }

    /**
     * 查询商品列表（根据商品名关键字查询）
     */
    @PostMapping("/kucunList")
    public R kucunList(String goodName, Integer type, Long min, Long max) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(goodName)) {
            wrapper.like("name", goodName);
        }
        if (ObjectUtils.isNotEmpty(type)) {
            wrapper.like("type", type);
        }
        if (ObjectUtils.isNotEmpty(min)) {
            wrapper.gt("total_stock", min);
        }
        if (ObjectUtils.isNotEmpty(max)) {
            wrapper.lt("total_stock", max);
        }
        wrapper.orderByDesc("create_time");
        List<Goods> goods = goodsService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goods);
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
     * 删除商品：数量清空
     */
    @PostMapping("/delete/{goodId}")
    public R deleteMenu(@PathVariable Long goodId) {
        // 库存推到总仓库
        Goods goods = goodsService.getById(goodId);
        goods.setTotalStock(goods.getTotalStock() + goods.getStock());
        goods.setStock(0);
        goodsService.updateById(goods);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除库存：移除记录
     */
    @PostMapping("/deleteKuCun/{goodId}")
    public R deleteKuCun(@PathVariable Long goodId) {
        // 库存推到总仓库
        Goods goods = goodsService.getById(goodId);
        if (goods.getStock() != 0 || goods.getTotalStock() != 0) {
            return R.out(ResponseEnum.FAIL, "门店库存或仓库库存不为 0，不可进行删除处理");
        }
        goodsService.removeById(goodId);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 销售商品（生成销售单order）
     */
    @PostMapping("/buy")
    @Transactional
    public R save(Integer vipFlag, Long goodId, Integer count, String phone, String userName, String address) {
        // 查询门店库存是否充足
        Goods good = goodsService.getById(goodId);
        if (good.getStock() < count) {
            return R.out(ResponseEnum.FAIL, "门店库存不足，购买失败");
        }

        // 如果是会员
        if (vipFlag == 1) {
            // 判断手机号是否正确
            QueryWrapper<Vip> wrapper = new QueryWrapper<>();
            if (ObjectUtils.isNotEmpty(phone)) {
                wrapper.eq("phone", phone);
            }
            Vip vip = vipService.getOne(wrapper);
            if (ObjectUtils.isEmpty(vip)) {
                return R.out(ResponseEnum.FAIL, "该电话号并未注册会员，请检查重试");
            }

            // 保存订单信息
            Orders orders = new Orders();
            orders.setUserName(vip.getUserName());
            orders.setStatus(1);
            orders.setGoodId(goodId);
            orders.setVipId(vip.getId());
            orders.setGoodName(good.getName());
            orders.setUrl(good.getUrl());
            orders.setMoney(good.getMoney() * count);
            orders.setBuyMoney(new BigDecimal(good.getMoney() * count).multiply(new BigDecimal(vip.getDiscount())).intValue());
            orders.setCount(count);
            orders.setAddress(vip.getAddress());
            orderService.save(orders);

            // 判断升级
            int money = (int) (vip.getMoney() + (good.getMoney() * count * vip.getDiscount()));
            if (money >= 10000) {
                vip.setLevel(1);
                vip.setDiscount(0.9D);
            }
            if (money >= 20000) {
                vip.setLevel(2);
                vip.setDiscount(0.8D);
            }
            if (money >= 30000) {
                vip.setLevel(3);
                vip.setDiscount(0.7D);
            }
            if (money >= 40000) {
                vip.setLevel(4);
                vip.setDiscount(0.6D);
            }
            if (money >= 50000) {
                vip.setLevel(5);
                vip.setDiscount(0.5D);
            }
            vipService.updateById(vip);
        }

        // 不是会员
        else {
            // 保存订单信息
            Orders orders = new Orders();
            orders.setUserName(userName);
            orders.setStatus(1);
            orders.setGoodId(goodId);
            orders.setGoodName(good.getName());
            orders.setUrl(good.getUrl());
            orders.setMoney(good.getMoney() * count);
            orders.setBuyMoney(good.getMoney() * count);
            orders.setCount(count);
            orders.setAddress(address);
            orderService.save(orders);
        }

        // 扣减商品库存
        good.setStock(good.getStock() - count);
        goodsService.updateById(good);
        return R.out(ResponseEnum.SUCCESS, "下单成功");
    }

    /**
     * 查询商品销量
     */
    @PostMapping("/bing")
    public R sellCount() {
        // 查询所有库存商品
        List<Goods> goods = goodsService.list();
        List<Bing> bingList = new ArrayList<>();

        // 查询库存商品对应订单
        for (Goods good : goods) {
            QueryWrapper<Orders> wrapper = new QueryWrapper<>();
            wrapper.eq("good_id", good.getId());
            wrapper.eq("status", 2);
            wrapper.orderByDesc("create_time");
            List<Orders> orderList = orderService.list(wrapper);
            int goodCount = 0;
            for (Orders orders : orderList) {
                goodCount += orders.getCount();
            }
            Bing bing = new Bing();
            bing.setName(good.getName());
            bing.setValue(goodCount);
            bingList.add(bing);
        }
        return R.out(ResponseEnum.SUCCESS, bingList);
    }
}

@Data
class Bing {
    private String name;
    private Integer value;
}