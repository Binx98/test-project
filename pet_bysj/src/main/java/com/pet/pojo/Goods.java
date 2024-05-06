package com.pet.pojo;

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
     * 商品价格
     */
    @TableField("price")
    private Integer price;

    /**
     * 分类：1：食品、2：用品、3：药品
     */
    @TableField("type")
    private Integer type;

    /**
     * 商品数量
     */
    @TableField("count")
    private Integer count;

    /**
     * 商品图片
     */
    @TableField("url")
    private String url;

    /**
     * 商品描述
     */
    @TableField("description")
    private String description;

    /**
     * 商品配料表
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
