package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;

import java.util.List;

/**
 * 图片
 */
public interface PictureService {
    Page<Picture> listPicture(String searchText , Long pageNum , Long pageSize);
}
