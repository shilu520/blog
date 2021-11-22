package com.million.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.million.blog.dao.dos.Articles;
import com.million.blog.dao.mapper.ArticleBodyMapper;
import com.million.blog.dao.mapper.ArticleMapper;
import com.million.blog.dao.mapper.ArticleTagMapper;
import com.million.blog.dao.pojo.Article;
import com.million.blog.dao.pojo.ArticleBody;
import com.million.blog.dao.pojo.ArticleTag;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.*;
import com.million.blog.utils.UserThreadLocal;
import com.million.blog.vo.*;
import com.million.blog.vo.params.ArticleParam;
import com.million.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private ArticleBodyService articleBodyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;

    /**
     * 自定义按照年份和月份来文章分页查询
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticlesPage (PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticlesPage(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));
    }

//    @Override
//    public Result listArticlesPage (PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId() != null) {
//            //相当于加上了 and category_id=#{categoryId}
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            //加入标签  条件查询
//            //article表中并没有tag字段  一篇文章，有多个标签
//            //article_tag表中  有article_id  tag_id
//            //可以通过tag_id查询到articleTag的集合，然后把这个集合里面的article_id添加到集合里
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0) {
//                //and in (1,2,3...)
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//        //是否置顶进行排序 Article::getWeight
//        //order by create_date desc  Article::getCreateDate
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        /**articlePage.getRecords()得到的是一个Records的集合，我们不能返回。
//         * 我们需要把数据库中的数据转成vo对象，再把vo对象添加成集合，我们返回集合给前端
//         * 这样做的目的是因为页面的数据千遍万化，转成vo也可以降低于数据库的耦合度
//         */
//        List<ArticleVo> articleVoList = copyList(articlePage.getRecords(), true, true);
//        return Result.success(articleVoList);
//    }

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

    //查看文章详情
    @Override
    public Result findArticleById (Long articleId) {

        /**
         * 思路：
         * 1、根据id 查询文章信息
         * 2、根据body_id 和category_id 去做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        //查看的是单个的文章信息就不用返回集合Vo了
        ArticleVo articleVo = copy(article, true, true, true, true);
        //查看完文章了，新增阅读数，有没有问题？
        /**
         * 查看完文章之后，本应该直接返回数据，这时候做了一个更新操作，更新时加写锁，阻塞
         * 其他的读操作，性能就会降低。
         * 更新  增加了此次接口的耗时，如果一旦更新出现问题，不能影响 查看文章操作
         * 如果优化？ 采用线程池
         * 线程池可以把更新操作 扔到线程池中去执行，和主线程就不相关了
         */
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    //文章发布
    @Override
    @Transactional  //添加事务
    public Result publish (ArticleParam articleParam) {

        //此接口，需要加入到登录拦截器中
        SysUser sysUser = UserThreadLocal.get();

        /**
         * 1.发布文章  目的就是创建Article对象
         * 2.获取作者id  就是当前的登录用户 可用UserThreadLocal来获取
         * 3.标签 要将标签加入到关联表中 需要article_id和tag_id
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //插入之后，就会产生一个文章id
        /**
         * insert 后主键会自动set到实体的id字段，所以你只需要getId()就好
         */
        articleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                //插入之后就会有articleTag的id了
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        //因为Article中也有bodyID
        article.setBodyId(articleBody.getId());
        //注意 这里需要更新Article 因为前面已经插入Article，要不然bodyID会丢失
        articleMapper.updateById(article);
        //返回数据给前端用ArticleVo来，注意这里需要考虑JSON精度丢失问题
        //需要在ArticleVo中的id上加上@JsonSerialize(using = ToStringSerializer.class)注解
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
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
        articleVo.setId(String.valueOf(article.getId()));
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

    public ArticleVo copy (Article article, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        //这个可以将article赋值给articleVo
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
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
        //获取文章信息，这里定义了一个新的接口articleBodyService
        //一个接口实现一个功能好一些
        if (isBody) {
            ArticleBodyVo articleBodyVo = articleBodyService.findArticleBodyById(article.getBodyId());
            articleVo.setBody(articleBodyVo);
        }
        //文章分类 理应只能有一个分类 多个标签
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            CategoryVo categoryVo = categoryService.findCategoryById(categoryId);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }
}
