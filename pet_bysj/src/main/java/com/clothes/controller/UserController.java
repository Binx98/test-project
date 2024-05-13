package com.clothes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.clothes.pojo.User;
import com.clothes.service.UserService;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查询用户列表
     */
    @PostMapping("/list")
    public R getList(String accountId, Integer role) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(accountId)) {
            wrapper.like("account_id", accountId);
        }
        if (ObjectUtils.isNotEmpty(role)) {
            wrapper.eq("role", role);
        }
        wrapper.orderByDesc("create_time");
        List<User> userList = userService.list(wrapper);
        userList = userList.stream().filter(item -> !item.getRole().equals(1)).collect(Collectors.toList());
        return R.out(ResponseEnum.SUCCESS, userList);
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(String accountId, String password, Integer role) {
        if (ObjectUtils.isEmpty(accountId) || accountId.length() < 3) {
            return R.out(ResponseEnum.FAIL, "账号长度不能 < 3 位");
        }
        if (ObjectUtils.isEmpty(password) || password.length() < 3) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 3 位");
        }

        // 查询账户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.eq("role", role);
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
     * 查询用户信息
     */
    @PostMapping("/detail/{id}")
    public R detail(@PathVariable Long id) {
        User userPO = userService.getById(id);
        return R.out(ResponseEnum.SUCCESS, userPO);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    public R updateUser(@RequestBody User user) {
        if (ObjectUtils.isEmpty(user.getAccountId()) || user.getAccountId().length() < 3) {
            return R.out(ResponseEnum.FAIL, "账号长度不能小于3");
        }
        if (ObjectUtils.isEmpty(user.getPassword()) || user.getPassword().length() < 3) {
            return R.out(ResponseEnum.FAIL, "密码长度不能小于3");
        }
        if (ObjectUtils.isEmpty(user.getPhone())) {
            return R.out(ResponseEnum.FAIL, "电话号不能为空");
        }
        if (ObjectUtils.isEmpty(user.getAddress())) {
            return R.out(ResponseEnum.FAIL, "地址不能为空");
        }
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
        if (userPO.getRole().equals(2)) {
            userPO.setRole(3);
        } else if (userPO.getRole().equals(3)) {
            userPO.setRole(4);
        } else if (userPO.getRole().equals(4)) {
            userPO.setRole(2);
        }
        userService.updateById(userPO);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询登陆中的用户
     */
    @PostMapping("/getLoginUser")
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
     * 删除账号
     */
    @PostMapping("/delete/{id}")
    public R deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 保存账号
     */
    @PostMapping("/save")
    public R saveUser(User user) {
        if (ObjectUtils.isEmpty(user.getAccountId()) || user.getAccountId().length() < 3) {
            return R.out(ResponseEnum.FAIL, "账号长度不能 < 3 位");
        }
        if (ObjectUtils.isEmpty(user.getPassword()) || user.getPassword().length() < 3) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 3 位");
        }
        if (ObjectUtils.isEmpty(user.getPhone())) {
            return R.out(ResponseEnum.FAIL, "联系方式不能为空");
        }
        if (ObjectUtils.isEmpty(user.getAddress())) {
            return R.out(ResponseEnum.FAIL, "地址不能为空");
        }
        if (ObjectUtils.isEmpty(user.getRole())) {
            return R.out(ResponseEnum.FAIL, "角色不能为空");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", user.getAccountId());
        wrapper.eq("role", user.getRole());
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(userPO)) {
            return R.out(ResponseEnum.FAIL, "账户已存在，不可重复注册");
        }

        user.setStatus("N");
        user.setCreateTime(LocalDateTime.now());
        userService.saveOrUpdate(user);
        return R.out(ResponseEnum.SUCCESS);
    }
}

