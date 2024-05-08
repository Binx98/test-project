package com.pet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pet.pojo.User;
import com.pet.service.UserService;
import com.pet.utils.EmailUtil;
import com.pet.utils.R;
import com.pet.utils.RandomUtil;
import com.pet.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 账号 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-03-15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailUtil emailUtil;
    private static final Cache cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();

    /**
     * 注册、保存
     */
    @PostMapping("/save")
    public R saveUser(User user) {
        if (ObjectUtils.isEmpty(user.getAccountId()) || user.getAccountId().length() < 6) {
            return R.out(ResponseEnum.FAIL, "账号长度不能 < 6 位");
        }
        if (ObjectUtils.isEmpty(user.getPassword()) || user.getPassword().length() < 6) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 6 位");
        }
        if (ObjectUtils.isEmpty(user.getEmail())) {
            return R.out(ResponseEnum.FAIL, "邮件信息不能为空");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", user.getAccountId());
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(userPO)) {
            return R.out(ResponseEnum.FAIL, "账户已存在，不可重复注册");
        }

        user.setStatus("N");
        user.setMoney(0);
        user.setRole(1);
        user.setCreateTime(LocalDateTime.now());
        userService.saveOrUpdate(user);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(String accountId, String password) {
        if (ObjectUtils.isEmpty(accountId) || accountId.length() < 2) {
            return R.out(ResponseEnum.FAIL, "账号长度不能 < 2 位");
        }
        if (ObjectUtils.isEmpty(password) || password.length() < 6) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 6 位");
        }

        // 查询账户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isEmpty(userPO) || !password.equals(userPO.getPassword())) {
            return R.out(ResponseEnum.FAIL, "密码输入错误");
        }

        // 其他人登录状态下掉
        List<User> userList = userService.list();
        for (User user : userList) {
            user.setStatus("N");
        }
        userService.updateBatchById(userList);

        // 当前用户登陆状态成功
        userPO.setStatus("Y");
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public R logout() {
        List<User> userList = userService.list();
        for (User user : userList) {
            user.setStatus("N");
        }
        userService.updateBatchById(userList);
        return R.out(ResponseEnum.SUCCESS, "退出登录成功");
    }

    /**
     * 找回密码
     */
    @PostMapping("/findBack")
    public R findBack(String accountId, String password, String code) {
        if (StringUtils.isBlank(code) || code.length() < 4) {
            return R.out(ResponseEnum.FAIL, "验证码长度不能 < 4 位");
        }

        if (StringUtils.isBlank(accountId) || accountId.length() < 2) {
            return R.out(ResponseEnum.FAIL, "账号长度不能小于 2 位");
        }

        if (StringUtils.isBlank(password) || password.length() < 6) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 6 位");
        }

        // 查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isEmpty(userPO)) {
            return R.out(ResponseEnum.FAIL, "账号还未注册，请重新输入账号");
        }

        // 判断验证码是否正确
        String cacheCode = (String) cache.getIfPresent(userPO.getEmail());
        if (StringUtils.isBlank(cacheCode) || !code.equals(cacheCode)) {
            return R.out(ResponseEnum.FAIL, "验证码输入错误，请重试");
        }

        // 修改密码
        userPO.setPassword(password);
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询用户信息
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Long id) {
        User userPO = userService.getById(id);
        return R.out(ResponseEnum.SUCCESS, userPO);
    }

    /**
     * 修改用户信息（用户、管理员通用）
     */
    @PostMapping("/update")
    public R updateUser(@RequestBody User user) {
        userService.updateById(user);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 修改角色
     */
    @PostMapping("/updateRole")
    public R updateRole(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", user.getAccountId());
        User userPO = userService.getOne(wrapper);
        if (userPO.getRole().equals(1)) {
            userPO.setRole(2);
        } else if (userPO.getRole().equals(2)) {
            userPO.setRole(1);
        }
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 账户充值
     */
    @PostMapping("/recharge")
    public R recharge(Long id, Integer money) {
        User userPO = userService.getById(id);
        userPO.setMoney(userPO.getMoney() + money);
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询登陆中的用户
     */
    @GetMapping("/getLoginUser")
    public R getLoginUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "Y");
        List<User> userList = userService.list(wrapper);
        if (CollectionUtils.isEmpty(userList)) {
            return R.out(ResponseEnum.SUCCESS, userList);
        }
        User user = userList.get(0);
        return R.out(ResponseEnum.SUCCESS, user);
    }

    /**
     * 发送邮件功能
     */
    @GetMapping("/sendEmail")
    public R sendEmail(String accountId) {
        // 查询用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isEmpty(userPO)) {
            return R.out(ResponseEnum.FAIL, "账号信息不存在");
        }

        // 发送邮件
        String code = RandomUtil.generate(4, 1);
        String email = userPO.getEmail();
        emailUtil.sendSimpleMail(email, "账号安全验证:", "您的验证码为：" + code);

        // 验证码缓存到 Caffeine
        cache.put(email, code);
        return R.out(ResponseEnum.SUCCESS);
    }

    /*-----------------------------------管理员权限------------------------------------*/

    /**
     * 删除用户信息（管理员权限）
     */
    @DeleteMapping("/delete/{id}")
    public R deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询用户列表（管理员权限）
     */
    @GetMapping("/list")
    public R getList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("role", 3);
        List<User> userList = userService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, userList);
    }
}

