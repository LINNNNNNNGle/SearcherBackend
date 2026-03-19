package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.ISearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.vo.ISearchVO;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.ISearchService;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import io.netty.util.concurrent.CompleteFuture;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 图片
 */
    @Service
    public class ISearchServiceImpl implements ISearchService {
    @Resource
    private PostService postService;
    @Resource
    private UserService userService;
    @Resource
    private PictureService pictureService;
    public ISearchVO isearch(ISearchRequest searchRequest, HttpServletRequest request) {

        CompletableFuture< Page<Picture>> PictureCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Page<Picture> picturePage = pictureService.listPicture(searchRequest.getSearchText(), 1L, 10L);
            return picturePage;
        });
        CompletableFuture<Page<UserVO>> UserVOCompletableFuture = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchRequest.getSearchText());
            Page<UserVO> userVOPage = userService.listUsers(userQueryRequest, request);
            return userVOPage;
        });
        CompletableFuture< Page<PostVO>> PostVOCompletableFuture = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchRequest.getSearchText());
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
            return postVOPage;
        });

        //全部执行后继续
        CompletableFuture.allOf(PostVOCompletableFuture,UserVOCompletableFuture,PictureCompletableFuture).join();

        try {
            Page<Picture> picturePage = PictureCompletableFuture.get();
            Page<PostVO> postVOPage = PostVOCompletableFuture.get();
            Page<UserVO> userVOPage = UserVOCompletableFuture.get();
            ISearchVO searchVO = new ISearchVO();
            searchVO.setPictures(picturePage.getRecords());
            searchVO.setPosts(postVOPage.getRecords());
            searchVO.setUsers(userVOPage.getRecords());
            return searchVO;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"error");
        }




    }
}
