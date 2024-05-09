package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.clothes.pojo.Goods;
import com.clothes.pojo.ShopCar;
import com.clothes.service.GoodsService;
import com.clothes.service.OrderService;
import com.clothes.service.ShopCarService;
import com.clothes.service.UserService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private UserService userService;

    /**
     * 查询商品列表（根据商品名关键字查询）
     */
    @PostMapping("/list")
    public R getList(String goodName, Integer type, Integer queCount) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(goodName)) {
            wrapper.like("name", goodName);
        }
        if (ObjectUtils.isNotEmpty(type)) {
            wrapper.like("type", type);
        }
        wrapper.orderByDesc("create_time");
        List<Goods> goods = goodsService.list(wrapper);
        if (ObjectUtils.isNotEmpty(queCount)) {
            goods = goods.stream()
                    .filter(item -> item.getStock() + item.getTotalStock() > queCount)
                    .collect(Collectors.toList());
        }
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
     * 删除商品
     */
    @PostMapping("/delete/{goodId}")
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
     * 销售商品（生成销售单order）
     */
    @PostMapping("/buy")
    public R save(Long goodId, String accountId) {
        return R.out(ResponseEnum.SUCCESS, "下单成功");
    }
}

