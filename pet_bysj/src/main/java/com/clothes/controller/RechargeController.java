package com.clothes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clothes.pojo.AliRecharge;
import com.clothes.pojo.Recharge;
import com.clothes.pojo.User;
import com.clothes.pojo.WxRecharge;
import com.clothes.service.AliRechargeService;
import com.clothes.service.RechargeService;
import com.clothes.service.UserService;
import com.clothes.service.WxRechargeService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    private UserService userService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private WxRechargeService wxRechargeService;
    @Autowired
    private AliRechargeService aliRechargeService;

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public R list(String accountId) {
        QueryWrapper<Recharge> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.orderByDesc("create_time");
        List<Recharge> list = rechargeService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 充值账户
     */
    @PostMapping("/add")
    public R add(String accountId, Integer type, Integer money) {
//        // 1：微信
//        if (type == 1) {
//            WxRecharge recharge = new WxRecharge();
//            recharge.setAccountId(accountId);
//            recharge.setMoney(money);
//            recharge.setCreateTime(LocalDateTime.now());
//            wxRechargeService.save(recharge);
//        } else {
//            AliRecharge recharge = new AliRecharge();
//            recharge.setAccountId(accountId);
//            recharge.setMoney(money);
//            recharge.setCreateTime(LocalDateTime.now());
//            aliRechargeService.save(recharge);
//        }
//
//        // 2.支付宝
//
//        // 保存充值记录
//        Recharge recharge = new Recharge();
//        recharge.setAccountId(accountId);
//        recharge.setType(type);
//        recharge.setStatus(1);
//        recharge.setMoney(money);
//        rechargeService.save(recharge);
//
//        // 修改账户余额
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("account_id", accountId);
//        User userPO = userService.getOne(wrapper);
//        userPO.setMoney(userPO.getMoney() + money);
//        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }
}
