package com.pet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pet.pojo.Comment;
import com.pet.pojo.Goods;
import com.pet.pojo.Orders;
import com.pet.service.CommentService;
import com.pet.service.GoodsService;
import com.pet.service.OrderService;
import com.pet.utils.R;
import com.pet.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评价 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;

    /**
     * 查询商品对应评论列表
     */
    @GetMapping("/list")
    public R list(Long goodId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("good_id", goodId);
        wrapper.orderByDesc("create_time");
        List<Comment> list = commentService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 根据 accountId 查询评论列表
     */
    @GetMapping("/getList")
    public R getList(String accountId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        wrapper.orderByDesc("create_time");
        List<Comment> list = commentService.list(wrapper);
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 发起评论
     */
    @PostMapping("/save")
    public R save(String accountId, Long orderId, Long goodId, String content) {
        // 如果订单状态没完成，不可以评价
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId);
        Orders order = orderService.getOne(wrapper);
        if (!order.getStatus().equals(3)) {
            return R.out(ResponseEnum.FAIL, "订单未完成，不可进行评论");
        }

        // 查询商品信息
        Goods good = goodsService.getById(goodId);

        // 发起评论
        Comment comment = new Comment();
        comment.setAccountId(accountId);
        comment.setGoodId(goodId);
        comment.setGoodName(good.getName());
        comment.setContent(content);
        comment.setCreateTime(LocalDateTime.now());
        commentService.save(comment);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        commentService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

