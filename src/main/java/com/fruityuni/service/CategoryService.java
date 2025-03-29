package com.fruityuni.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fruityuni.entity.Category;

import java.util.List;

/**
 * 商品分类服务接口
 *
 * @author fruityuni
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取所有一级分类
     *
     * @return 一级分类列表
     */
    List<Category> getLevel1Categories();

    /**
     * 获取指定父分类下的所有子分类
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> getChildCategories(Long parentId);

    /**
     * 获取分类树
     *
     * @return 分类树列表
     */
    List<Category> getCategoryTree();
}