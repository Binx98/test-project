package com.clothes.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
     * 主键id
     */
    @TableField(value = "supplier_id")
    private Long supplierId;

    /**
     * 供应商名
     */
    @TableField("supplier_name")
    private String supplierName;

    /**
     * 供应商名
     */
    @TableField("url")
    private String url;

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
     * 进货单价
     */
    @TableField("money")
    private Integer money;

    /**
     * 尺码
     */
    @TableField("size")
    private String size;

    /**
     * 商品类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 材质
     */
    @TableField("material")
    private String material;


    /**
     * 审核状态（1：进行中，2：已完成，3：已拒绝，4：已入库）
     */
    @TableField("status")
    private Integer status;

    /**
     * 时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
