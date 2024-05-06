package com.pet.pojo;

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
 * @since 2024-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pay")
public class Pay implements Serializable {

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
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 花费金额
     */
    @TableField("money")
    private Integer money;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
