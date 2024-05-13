package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.SupplierGood;
import com.clothes.service.SupplierGoodService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/supplierGood")
public class SupplierGoodController {
    @Autowired
    private SupplierGoodService supplierGoodService;

    /**
     * 查询列表
     */
    @PostMapping("/list")
    public R getList(Long supplierId) {
        QueryWrapper<SupplierGood> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(supplierId)) {
            wrapper.eq("supplier_id", supplierId);
        }
        wrapper.orderByDesc("create_time");
        List<SupplierGood> list = supplierGoodService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }
}

