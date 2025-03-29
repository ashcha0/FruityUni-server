package com.fruityuni.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fruityuni.entity.Product;
import com.fruityuni.service.ProductService;
import com.fruityuni.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 *
 * @author fruityuni
 */
@Api(tags = "商品接口")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("分页查询商品列表")
    @GetMapping("/page")
    public Result<IPage<Product>> getProductPage(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页数量", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "分类ID") @RequestParam(required = false) Long categoryId,
            @ApiParam(value = "关键词") @RequestParam(required = false) String keyword) {
        IPage<Product> page = productService.getProductPage(pageNum, pageSize, categoryId, keyword);
        return Result.success(page);
    }

    @ApiOperation("获取商品详情")
    @GetMapping("/{id}")
    public Result<Product> getProductDetail(@ApiParam(value = "商品ID") @PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    @ApiOperation("添加商品")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<Void> addProduct(@RequestBody Product product) {
        productService.save(product);
        return Result.success();
    }

    @ApiOperation("更新商品")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<Void> updateProduct(
            @ApiParam(value = "商品ID") @PathVariable Long id,
            @RequestBody Product product) {
        product.setId(id);
        productService.updateById(product);
        return Result.success();
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<Void> deleteProduct(@ApiParam(value = "商品ID") @PathVariable Long id) {
        productService.removeById(id);
        return Result.success();
    }
}