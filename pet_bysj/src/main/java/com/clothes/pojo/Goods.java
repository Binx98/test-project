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
 * 商品
 * </p>
 *
 * @author Author
 * @since 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品名
     */
    @TableField("name")
    private String name;

    /**
     * 销售价格
     */
    @TableField("money")
    private Integer money;

    /**
     * 进货价格
     */
    @TableField("origin_money")
    private Integer originMoney;

    /**
     * 分类（1：衣服、2：裤子、3：鞋子，4：其他）
     */
    @TableField("type")
    private Integer type;

    /**
     * 商品图片
     */
    @TableField("url")
    private String url;

    /**
     * 材质
     */
    @TableField("material")
    private String material;

    /**
     * 店铺库存
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 仓库库存
     */
    @TableField("total_stock")
    private Integer totalStock;

    /**
     * 尺码
     */
    @TableField("size")
    private String size;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
