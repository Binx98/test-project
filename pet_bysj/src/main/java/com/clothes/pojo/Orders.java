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
 * 订单
 * </p>
 *
 * @author Author
 * @since 2024-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * vip编号
     */
    @TableField("vip_id")
    private Long vipId;

    /**
     * 姓名
     */
    @TableField("user_name")
    private String userName;

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
     * 商品图片
     */
    @TableField("url")
    private String url;

    /**
     * 数量
     */
    @TableField("count")
    private Integer count;

    /**
     * 尺码
     */
    @TableField("size")
    private String size;

    /**
     * 总原价
     */
    @TableField("money")
    private Integer money;


    /**
     * 实际成交价格
     */
    @TableField("buy_money")
    private Integer buyMoney;

    /**
     * 订单状态（1：进行中，2：完成，3：退货）
     */
    @TableField("status")
    private Integer status;

    /**
     * 姓名
     */
    @TableField("address")
    private String address;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
