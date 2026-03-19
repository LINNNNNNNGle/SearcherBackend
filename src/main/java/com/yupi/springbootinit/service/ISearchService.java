package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.search.ISearchRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.vo.ISearchVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片
 */
public interface ISearchService {
    ISearchVO isearch(ISearchRequest searchRequest, HttpServletRequest request);
}
