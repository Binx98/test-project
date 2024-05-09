package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Caigou;
import com.clothes.pojo.Goods;
import com.clothes.pojo.Ruchu;
import com.clothes.pojo.SupplierGood;
import com.clothes.service.CaigouService;
import com.clothes.service.GoodsService;
import com.clothes.service.RuchuService;
import com.clothes.service.SupplierGoodService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024-05-09
 */
@RestController
@RequestMapping("/caigou")
public class CaigouController {
    @Autowired
    private CaigouService caigouService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SupplierGoodService supplierGoodService;
    @Autowired
    private RuchuService ruchuService;

    /**
     * 查询列表
     */
    @PostMapping("/list")
    public R list() {
        QueryWrapper<Caigou> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Caigou> list = caigouService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 创建采购申请
     */
    @PostMapping("/save")
    public R save(@RequestBody Caigou caigou) {
        caigouService.save(caigou);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除采购申请
     */
    @PostMapping("/delete")
    public R delete(Long id) {
        caigouService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 调整采购状态
     * 2：同意，3：拒绝
     */
    @PostMapping("changeStatus")
    @Transactional
    public R status(Long caigouId, Integer status) {
        Caigou caigou = caigouService.getById(caigouId);
        if (caigou.getStatus() == 2 || caigou.getStatus() == 3) {
            return R.out(ResponseEnum.FAIL, "采购信息已被处理，补课再次操作");
        }

        // 修改采购单状态
        caigou.setStatus(status);
        caigouService.updateById(caigou);


        // 拒绝
        if (status == 3) {
            return R.out(ResponseEnum.SUCCESS);
        }

        // 同意
        else {
            // 判断采购商品是否存在goods表：存在改变总库存即可，不存在保存商品
            Goods good = goodsService.getById(caigou.getSupplierGoodId());

            // 不存在：保存商品
            if (ObjectUtils.isEmpty(good)) {
                SupplierGood supplierGood = supplierGoodService.getById(caigou.getSupplierGoodId());
                Caigou caigouPO = caigouService.getById(caigouId);
                Goods goods = new Goods();
                goods.setId(supplierGood.getId());
                goods.setName(supplierGood.getGoodName());
                goods.setStock(0);
                goods.setTotalStock(caigouPO.getCount());
                goods.setUrl(supplierGood.getUrl());
                goods.setOriginMoney(supplierGood.getMoney());
                goods.setMoney(supplierGood.getMoney() * 2);
                goods.setCreateTime(LocalDateTime.now());
                goods.setType(supplierGood.getType());
                goods.setMaterial(supplierGood.getMaterial());
                goodsService.save(goods);
            }

            // 存在：改变总库存
            else {
                Caigou caigouPO = caigouService.getById(caigouId);
                good.setTotalStock(good.getTotalStock() + caigouPO.getCount());
                goodsService.updateById(good);
            }

            // 保存出入库记录
            Ruchu ruchu = new Ruchu();
            ruchu.setGoodId(caigou.getSupplierGoodId());
            ruchu.setType(1);
            ruchu.setCount(caigou.getCount());
            ruchuService.save(ruchu);
            return R.out(ResponseEnum.SUCCESS);
        }
    }
}

