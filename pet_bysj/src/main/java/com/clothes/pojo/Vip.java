package com.clothes.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @TableField("name")
    private String name;

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
    private Boolean level;

    /**
     * 充值时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
