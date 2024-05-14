package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Goods;
import com.clothes.pojo.Ruchu;
import com.clothes.service.GoodsService;
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
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024-05-09
 */
@RestController
@RequestMapping("/ruchu")
public class RuchuController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RuchuService ruchuService;

    /**
     * 根据 good_id 查询出入库列表
     */
    @PostMapping("/getById")
    public R getById(Long goodId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("good_id", goodId);
        wrapper.orderByDesc("create_time");
        List<Ruchu> goods = ruchuService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, goods);
    }

    /**
     * 1：总仓库 to 门店仓库
     * 2：门店仓库 to 总仓库
     */
    @PostMapping("/change")
    @Transactional
    public R ru(Long goodId, Integer count, Integer type) {
        if (ObjectUtils.isEmpty(count) || ObjectUtils.isEmpty(type)) {
            return R.out(ResponseEnum.FAIL, "请填写完整后，再点击确定");
        }

        if (count <= 0) {
            return R.out(ResponseEnum.FAIL, "输入数量必须 > 0");
        }

        Goods good = goodsService.getById(goodId);
        Ruchu ruchu = new Ruchu();

        if (type == 1) {
            if (good.getTotalStock() < count) {
                return R.out(ResponseEnum.FAIL, "总库存数量不足");
            }
            good.setStock(good.getStock() + count);
            good.setTotalStock(good.getTotalStock() - count);
            ruchu.setNote("总仓库出库——>门店仓库入库");
        } else if (type == 3) {
            if (good.getTotalStock() < count) {
                return R.out(ResponseEnum.FAIL, "总库存数量不足");
            }
            good.setTotalStock(good.getTotalStock() - count);
            ruchu.setNote("总仓库出库——>供货商");
        } else {
            if (good.getStock() < count) {
                return R.out(ResponseEnum.FAIL, "门店库存数量不足");
            }
            good.setStock(good.getStock() - count);
            good.setTotalStock(good.getTotalStock() + count);
            ruchu.setNote("门店仓库出库——>总仓库入库");
        }
        goodsService.updateById(good);

        // 保存出入库记录
        ruchu.setCount(count);
        ruchu.setType(type);
        ruchu.setGoodId(goodId);
        ruchuService.save(ruchu);
        return R.out(ResponseEnum.SUCCESS);
    }
}

