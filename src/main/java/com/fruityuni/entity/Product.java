package com.fruityuni.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 *
 * @author fruityuni
 */
@Data
@TableName("product")
@ApiModel(value = "商品信息")
public class Product {

    @ApiModelProperty(value = "商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String subtitle;

    @ApiModelProperty(value = "主图URL")
    private String mainImage;

    @ApiModelProperty(value = "子图URL，JSON格式")
    private String subImages;

    @ApiModelProperty(value = "商品详情")
    private String detail;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "状态：0下架，1上架")
    private Integer status;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "是否删除：0否，1是")
    @TableLogic
    private Integer deleted;
}