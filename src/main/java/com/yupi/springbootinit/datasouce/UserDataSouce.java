package com.yupi.springbootinit.datasouce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserDataSouce implements DataSouce {

    @Resource
    private UserService userService;

    public Page<UserVO> isearch(String searchText, int pageNum, int pageSize) {
        Page<UserVO> userVOPage = new Page<>();
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes)
                        RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        userVOPage = userService.listUsers(userQueryRequest, request);
        return userVOPage;
    }
}
