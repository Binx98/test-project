package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Supplier;
import com.clothes.service.SupplierService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024-05-08
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 查询列表
     */
    @PostMapping("/list")
    public R list() {
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Supplier> list = supplierService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 创建、修改
     */
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Supplier supplier) {
        supplierService.saveOrUpdate(supplier);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(Long id) {
        supplierService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

