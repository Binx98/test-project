package com.clothes.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2024-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("vip")
public class Vip implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 会员名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 手机号
     */
    @TableField("phone")
    private Integer phone;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 消费金额
     */
    @TableField("money")
    private Integer money;

    /**
     * 会员等级（5个等级，1w元升1级，1级折扣0.1）
     */
    @TableField("level")
    private Integer level;

    /**
     * 折扣
     */
    @TableField("discount")
    private Double discount;

    /**
     * 充值时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
