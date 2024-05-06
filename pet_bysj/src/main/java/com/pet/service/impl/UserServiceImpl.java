package com.pet.service.impl;

import com.pet.pojo.User;
import com.pet.mapper.UserMapper;
import com.pet.service.UserService;
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
