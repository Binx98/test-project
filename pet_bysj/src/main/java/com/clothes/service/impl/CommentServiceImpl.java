package com.clothes.service.impl;

import com.clothes.pojo.Comment;
import com.clothes.mapper.CommentMapper;
import com.clothes.service.CommentService;
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
