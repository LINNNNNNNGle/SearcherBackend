package com.yupi.springbootinit.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片
 */
@Service
public class PictureServiceImpl implements PictureService {
    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36";

    /**
     * 搜索图片
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<Picture> listPicture(String searchText, Long pageNum, Long pageSize) {
        List<Picture> pictureList = new ArrayList<>();
        Long currentPageNum = (pageNum+1)*pageSize;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s",searchText,currentPageNum);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(5000)
                    .get();
        }
        catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,("数据获取异常"));
        }
        Elements allDiv = doc.select(".iuscp");
        for (Element element : allDiv) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String,Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String) map.get("murl");
            pictureList.add(new Picture(murl,title));
            if(pictureList.size()>pageSize){break;}
        }
        Page<Picture> pageable = new Page<>(pageNum, pageSize);
        pageable.setRecords(pictureList);
        return pageable;
    }
}
