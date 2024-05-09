package com.clothes.controller;


import com.clothes.pojo.Goods;
import com.clothes.pojo.Ruchu;
import com.clothes.service.GoodsService;
import com.clothes.service.RuchuService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 1：总仓库 to 门店仓库
     * 2：门店仓库 to 总仓库
     */
    @PostMapping("/ru")
    @Transactional
    public R ru(Long goodId, Integer count, Integer type) {
        Goods good = goodsService.getById(goodId);
        Ruchu ruchu = new Ruchu();
        if (type == 1) {
            if (good.getTotalStock() < count) {
                return R.out(ResponseEnum.FAIL, "总库存数量不足");
            }
            good.setStock(good.getStock() + count);
            good.setTotalStock(good.getTotalStock() - count);
            ruchu.setNote("总仓库出库——>门店仓库入库");
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

