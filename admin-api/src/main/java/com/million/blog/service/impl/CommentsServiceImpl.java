package com.million.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.million.blog.dao.mapper.CommentsMapper;
import com.million.blog.dao.pojo.Comment;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.CommentsService;
import com.million.blog.service.SysUserService;
import com.million.blog.utils.UserThreadLocal;
import com.million.blog.vo.CommentVo;
import com.million.blog.vo.Result;
import com.million.blog.vo.UserVo;
import com.million.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  16:51
 */

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId (Long articleId) {

        /**
         * 步骤：
         * 1、根据文章id  查询 评论列表  从comment中查询
         * 2、根据作者id  查询作者信息
         * 3、判断  如果level = 1，要去查询它有没有子评论
         * 4、如果有，根据评论id  进行查询 （parent_id）
         */

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        //判断  如果level = 1，要去查询它有没有子评论
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    /**
     * 这里需要把评论数据写入到数据库中，不用Vo
     *
     * @param commentParams
     * @return
     */
    @Override
    public Result comment (CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        //把parent赋值给comment中得ParentId
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentsMapper.insert(comment);
        return Result.success(null);
    }

    //将Comment对象转换成Vo集合
    private List<CommentVo> copyList (List<Comment> comments) {

        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    //将Comment对象转化成Vo对象
    private CommentVo copy (Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        /**
         * 作者信息
         */
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoByAuthorId(authorId);
        commentVo.setAuthor(userVo);
        /**
         * 子评论
         */
        //获取level
        Integer level = comment.getLevel();
        if (level == 1) {
            //获取comment的id,这个id就是子评论的父id
            Long parentId = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(parentId);
            commentVo.setChildrens(commentVoList);
        }
        /**
         * toUser  给谁评论
         */
        if (level > 1) {
            Long toUid = comment.getToUid();
            //通过给谁写的id 找到给谁写的用户
            UserVo toUserVo = sysUserService.findUserVoByAuthorId(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId (Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId);
        //level等于2  就是子评论
        queryWrapper.eq(Comment::getLevel, 2);
        List<CommentVo> commentVoList = copyList(commentsMapper.selectList(queryWrapper));
        return commentVoList;
    }
}
