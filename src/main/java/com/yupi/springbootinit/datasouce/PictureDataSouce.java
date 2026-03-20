package com.yupi.springbootinit.datasouce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class PictureDataSouce implements DataSouce {

    @Resource
    private PictureService pictureService;

    public Page<Picture> isearch(String searchText, int pageNum, int pageSize) {
        Page<Picture> picturePage = new Page<>();
        picturePage = pictureService.listPicture(searchText, Long.valueOf(pageNum), Long.valueOf(pageSize));
        return picturePage;
    }
}
