package com.pet.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("orders_detail")
public class OrdersDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("order_id")
    private Long orderId;

    /**
     * 用户id
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 商品名称
     */
    @TableField("good_id")
    private Long goodId;

    /**
     * 商品名称
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 商品图片
     */
    @TableField("good_url")
    private String goodUrl;

    /**
     * 数量
     */
    @TableField("count")
    private Integer count;

    /**
     * 价格
     */
    @TableField("money")
    private Integer money;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
