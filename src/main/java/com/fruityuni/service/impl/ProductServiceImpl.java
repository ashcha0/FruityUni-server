package com.fruityuni.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fruityuni.entity.Product;
import com.fruityuni.exception.BusinessException;
import com.fruityuni.mapper.ProductMapper;
import com.fruityuni.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 商品服务实现类
 *
 * @author fruityuni
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public IPage<Product> getProductPage(Integer pageNum, Integer pageSize, Long categoryId, String keyword) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加分类条件
        if (categoryId != null) {
            queryWrapper.eq(Product::getCategoryId, categoryId);
        }
        
        // 添加关键词条件
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Product::getName, keyword)
                    .or()
                    .like(Product::getSubtitle, keyword);
        }
        
        // 只查询上架商品
        queryWrapper.eq(Product::getStatus, 1);
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Product::getCreatedTime);
        
        return page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseSales(Long productId, Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误");
        }
        
        Product product = getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 增加销量
        product.setSales(product.getSales() + quantity);
        return updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Long productId, Integer quantity) {
        if (productId == null || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误");
        }
        
        Product product = getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查库存是否充足
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足");
        }
        
        // 减少库存
        product.setStock(product.getStock() - quantity);
        return updateById(product);
    }
}