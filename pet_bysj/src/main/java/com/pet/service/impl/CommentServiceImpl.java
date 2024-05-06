package com.pet.service.impl;

import com.pet.pojo.Comment;
import com.pet.mapper.CommentMapper;
import com.pet.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评价 服务实现类
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
