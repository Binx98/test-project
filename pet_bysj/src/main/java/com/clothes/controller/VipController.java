package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.Vip;
import com.clothes.service.VipService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
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
@RequestMapping("/vip")
public class VipController {
    @Autowired
    private VipService vipService;

    /**
     * 查询列表
     */
    @PostMapping("/list")
    public R getList(Integer level, String name) {
        QueryWrapper<Vip> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (ObjectUtils.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        List<Vip> list = vipService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 保存/修改
     */
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Vip vip) {
        vipService.saveOrUpdate(vip);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 注销（删除）
     */
    @PostMapping("/delete")
    public R delete(Long id) {
        vipService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

