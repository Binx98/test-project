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
    public R list(String supplierName, Integer status) {
        QueryWrapper<Caigou> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(supplierName)) {
            wrapper.like("supplier_name", supplierName);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        List<Caigou> list = caigouService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 创建采购/退购申请
     */
    @PostMapping("/save")
    public R save(@RequestBody Caigou caigou) {
        caigou.setId(null);
        caigou.setStatus(1);
        caigou.setCreateTime(LocalDateTime.now());
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
    public R status(Long id, Integer status) {
        Caigou caigou = caigouService.getById(id);
        if (status == 4 && caigou.getStatus() == 4) {
            return R.out(ResponseEnum.FAIL, "入库操作已完成");
        }
        if (status == 4 && caigou.getStatus() != 2) {
            return R.out(ResponseEnum.FAIL, "采购申请状态未完成，不可以进行入库操作");
        }
        if (caigou.getStatus() == 4) {
            return R.out(ResponseEnum.FAIL, "商品已入库，不可进行继续进行操作");
        }

        // 修改采购单状态
        caigou.setStatus(status);
        caigouService.updateById(caigou);

        // 入库操作
        if (status == 4) {
            // 判断采购商品是否存在goods表：存在改变总库存即可，不存在保存商品
            Goods good = goodsService.getById(caigou.getGoodId());

            // 不存在：保存商品
            if (ObjectUtils.isEmpty(good)) {
                SupplierGood supplierGood = supplierGoodService.getById(caigou.getGoodId());
                Caigou caigouPO = caigouService.getById(id);
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
                goods.setSize(supplierGood.getSize());
                goods.setMaterial(supplierGood.getMaterial());
                goodsService.save(goods);
            }

            // 存在：改变总库存
            else {
                Caigou caigouPO = caigouService.getById(id);
                good.setTotalStock(good.getTotalStock() + caigouPO.getCount());
                goodsService.updateById(good);
            }

            // 保存出入库记录
            Ruchu ruchu = new Ruchu();
            ruchu.setGoodId(caigou.getGoodId());
            ruchu.setType(1);
            ruchu.setCount(caigou.getCount());
            ruchu.setNote("供货商出库——>总仓库入库");
            ruchuService.save(ruchu);
        }
        return R.out(ResponseEnum.SUCCESS);
    }
}

