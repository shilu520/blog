package com.million.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.million.blog.dao.mapper.CategoryMapper;
import com.million.blog.dao.pojo.Category;
import com.million.blog.service.CategoryService;
import com.million.blog.vo.CategoryVo;
import com.million.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  22:25
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById (Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }

    @Override
    public Result categorys () {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //只需要id和名字 ，只查询这两个可以减少查询的时间
        queryWrapper.select(Category::getId, Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //页面交互的数据用Vo对象
        List<CategoryVo> categoryVoList = copyList(categories);
        return Result.success(categoryVoList);
    }

    /**
     * 查看 文章分类详情 就需要全部的数据了
     *
     * @return
     */
    @Override
    public Result categorysDetail () {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //页面交互的数据用Vo对象
        List<CategoryVo> categoryVoList = copyList(categories);
        return Result.success(categoryVoList);
    }

    @Override
    public Result categoryDetailById (Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    private List<CategoryVo> copyList (List<Category> categories) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    private CategoryVo copy (Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
