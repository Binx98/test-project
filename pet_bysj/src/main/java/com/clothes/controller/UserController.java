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
        if (ObjectUtils.isEmpty(user.getPhone())) {
            return R.out(ResponseEnum.FAIL, "联系方式不能为空");
        }
        if (ObjectUtils.isEmpty(user.getAddress())) {
            return R.out(ResponseEnum.FAIL, "地址不能为空");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", user.getAccountId());
        wrapper.eq("role", user.getRole());
        User userPO = userService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(userPO)) {
            return R.out(ResponseEnum.FAIL, "账户已存在，不可重复注册");
        }

        user.setStatus("N");
        user.setRole(1);
        user.setCreateTime(LocalDateTime.now());
        userService.saveOrUpdate(user);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(String accountId, String password, Integer role) {
        if (ObjectUtils.isEmpty(accountId) || accountId.length() < 6) {
            return R.out(ResponseEnum.FAIL, "账号长度不能 < 6 位");
        }
        if (ObjectUtils.isEmpty(password) || password.length() < 6) {
            return R.out(ResponseEnum.FAIL, "密码长度不能 < 6 位");
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
     * 删除用户信息（管理员权限）
     */
    @DeleteMapping("/delete/{id}")
    public R deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public R getList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("role", 1);
        List<User> userList = userService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, userList);
    }
}

