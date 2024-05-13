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
    public R getList(Integer level, String userName) {
        QueryWrapper<Vip> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (ObjectUtils.isNotEmpty(userName)) {
            wrapper.like("user_name", userName);
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
        if (ObjectUtils.isEmpty(vip.getUserName())) {
            return R.out(ResponseEnum.FAIL, "姓名不能为空");
        }
        if (ObjectUtils.isEmpty(vip.getPhone())) {
            return R.out(ResponseEnum.FAIL, "电话不能为空");
        }
        if (ObjectUtils.isEmpty(vip.getAddress())) {
            return R.out(ResponseEnum.FAIL, "地址不能为空");
        }
        if (ObjectUtils.isEmpty(vip.getMoney())) {
            return R.out(ResponseEnum.FAIL, "消费金额不能为空");
        }

        // 注册操作，判断手机号是否重复
        if (ObjectUtils.isEmpty(vip.getId())) {
            QueryWrapper<Vip> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", vip.getPhone());
            Vip vipInfo = vipService.getOne(wrapper);
            if (ObjectUtils.isNotEmpty(vipInfo)) {
                return R.out(ResponseEnum.FAIL, "手机号已经注册为VIP会员，请更换手机号");
            }
        }

        if (vip.getMoney() < 10000) {
            return R.out(ResponseEnum.FAIL, "设置vip起始消费金额必须 > 10000元");
        }
        Integer money = vip.getMoney();
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

