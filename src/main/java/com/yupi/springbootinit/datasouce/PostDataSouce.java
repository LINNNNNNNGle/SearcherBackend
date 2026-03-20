package com.yupi.springbootinit.datasouce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PostService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class PostDataSouce implements DataSouce {

    @Resource
    private PostService postService;

    public Page<PostVO> isearch(String searchText, int pageNum, int pageSize) {
        Page<UserVO> userVOPage = new Page<>();
        Page<PostVO> postVOPage = new Page<>();
        PostQueryRequest postQueryRequest = new PostQueryRequest();

        postQueryRequest.setSearchText(searchText);
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        postVOPage = postService.listPostVOByPage(postQueryRequest, request);
        return postVOPage;
    }
}
