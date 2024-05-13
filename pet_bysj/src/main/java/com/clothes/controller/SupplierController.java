package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Supplier;
import com.clothes.service.SupplierService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R list(String name) {
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        List<Supplier> list = supplierService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 创建、修改
     */
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Supplier supplier) {
        // 添加操作，不能少数据
        if (ObjectUtils.isEmpty(supplier.getName())) {
            return R.out(ResponseEnum.FAIL, "供货商名不能为空");
        }
        if (ObjectUtils.isEmpty(supplier.getPhone())) {
            return R.out(ResponseEnum.FAIL, "电话号不能为空");
        }
        if (ObjectUtils.isEmpty(supplier.getAddress())) {
            return R.out(ResponseEnum.FAIL, "供货商地址不能为空");
        }
        supplierService.saveOrUpdate(supplier);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        supplierService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

