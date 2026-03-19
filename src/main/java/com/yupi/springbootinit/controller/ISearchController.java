package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.dto.search.ISearchRequest;
import com.yupi.springbootinit.model.vo.ISearchVO;
import com.yupi.springbootinit.service.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 图片接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/isearch")
@Slf4j
public class ISearchController {


    @Resource
    private ISearchService iSearchService;

    /**
     * 分页获取列表（封装类）
     *
     * @param iSearchRequest
     * @param request
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<ISearchVO> iSearchAll(@RequestBody ISearchRequest iSearchRequest,
                                              HttpServletRequest request) {
        ISearchVO isearch = iSearchService.isearch(iSearchRequest, request);

        return  ResultUtils.success(isearch);
    }

}
