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
@TableName("supplier_good")
public class SupplierGood implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 供应商id
     */
    @TableField("supplier_id")
    private Long supplierId;

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
     * 商品名称
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 商品类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 商品图片
     */
    @TableField("url")
    private String url;

    /**
     * 尺码
     */
    @TableField("size")
    private String size;

    /**
     * 进货价
     */
    @TableField("money")
    private Integer money;

    /**
     * 材质
     */
    @TableField("material")
    private String material;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;


}
