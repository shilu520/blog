package com.million.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.million.blog.dao.dos.Articles;
import com.million.blog.dao.mapper.ArticleMapper;
import com.million.blog.dao.pojo.Article;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.ArticleService;
import com.million.blog.service.SysUserService;
import com.million.blog.service.TagsService;
import com.million.blog.vo.ArticleVo;
import com.million.blog.vo.Result;
import com.million.blog.vo.TagVo;
import com.million.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagsService tagsService;

    @Override
    public Result listArticlesPage (PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //是否置顶进行排序 Article::getWeight
        //order by create_date desc  Article::getCreateDate
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        /**articlePage.getRecords()得到的是一个Records的集合，我们不能返回。
         * 我们需要把数据库中的数据转成vo对象，再把vo对象添加成集合，我们返回集合给前端
         * 这样做的目的是因为页面的数据千遍万化，转成vo也可以降低于数据库的耦合度
         */
        List<ArticleVo> articleVoList = copyList(articlePage.getRecords(), true, true);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticles (int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据浏览量降序排序
        queryWrapper.orderByDesc(Article::getViewCounts);
        //我们只需要id和文章标题 所以使用select
        queryWrapper.select(Article::getId, Article::getTitle);
        //需要前几个，使用last方法，注意这里的limit后面需要加空格,不然会把数据拼到一起
        queryWrapper.last("limit " + limit);
        //select id,title from article order by view_Counts limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //需要返回vo对象的集合
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticles (int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据创建时间降序排序
        queryWrapper.orderByDesc(Article::getCreateDate);
        //我们只需要id和文章标题 所以使用select
        queryWrapper.select(Article::getId, Article::getTitle);
        //需要前几个，使用last方法，注意这里的limit后面需要加空格,不然会把数据拼到一起
        queryWrapper.last("limit " + limit);
        //select id,title from article order by create_date limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //需要返回vo对象的集合
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives () {
       List<Articles> articlesList = articleMapper.listArchives();
        return Result.success(articlesList);
    }

    private List<ArticleVo> copyList (List<Article> records, boolean isAuthor, boolean isTags) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            ArticleVo articleVo = copy(article, isAuthor, isTags);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }


    public ArticleVo copy (Article article, boolean isAuthor, boolean isTags) {
        ArticleVo articleVo = new ArticleVo();
        //这个可以将article赋值给articleVo
        BeanUtils.copyProperties(article, articleVo);
        //因为article中的createDate时Long类型的，而articleVo是String类型的，需要自己设置
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //并不是所有的接口都需要标签作者
        if (isAuthor) {
            SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
            articleVo.setAuthor(sysUser.getNickname());
        }
        if (isTags) {
            List<TagVo> tags = tagsService.findTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        return articleVo;
    }
}
