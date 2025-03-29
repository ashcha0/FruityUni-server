package com.fruityuni.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruityuni.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品Mapper接口
 *
 * @author fruityuni
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}