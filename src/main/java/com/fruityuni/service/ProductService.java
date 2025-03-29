package com.fruityuni.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fruityuni.entity.Product;

/**
 * 商品服务接口
 *
 * @author fruityuni
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询商品列表
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param categoryId 分类ID
     * @param keyword    关键词
     * @return 分页商品列表
     */
    IPage<Product> getProductPage(Integer pageNum, Integer pageSize, Long categoryId, String keyword);

    /**
     * 增加商品销量
     *
     * @param productId 商品ID
     * @param quantity  数量
     * @return 是否成功
     */
    boolean increaseSales(Long productId, Integer quantity);

    /**
     * 减少商品库存
     *
     * @param productId 商品ID
     * @param quantity  数量
     * @return 是否成功
     */
    boolean decreaseStock(Long productId, Integer quantity);
}