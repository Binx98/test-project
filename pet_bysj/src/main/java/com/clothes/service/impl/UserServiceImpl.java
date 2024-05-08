package com.clothes.service.impl;

import com.clothes.pojo.User;
import com.clothes.mapper.UserMapper;
import com.clothes.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号 服务实现类
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
