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
 * @since 2024-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("caigou")
public class Caigou implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 供应商名
     */
    @TableField("supplier_name")
    private String supplierName;

    /**
     * 商品id
     */
    @TableField("good_id")
    private Long goodId;

    /**
     * 商品名
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 采购数量
     */
    @TableField("count")
    private Integer count;

    /**
     * 进货总价
     */
    @TableField("money")
    private Integer money;

    /**
     * 商品类型
     */
    @TableField("type")
    private Integer type;


    /**
     * 审核状态（1：进行中，2：已完成，3：已拒绝）
     */
    @TableField("status")
    private Integer status;

    /**
     * 时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
