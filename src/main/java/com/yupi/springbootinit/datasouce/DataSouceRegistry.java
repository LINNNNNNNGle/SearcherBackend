package com.yupi.springbootinit.datasouce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.enums.ISearchEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PostService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Component
public class DataSouceRegistry   {

    @Resource
    private PostDataSouce postDataSouce;
    @Resource
    private UserDataSouce userDataSouce;
    @Resource
    private PictureDataSouce pictureDataSouce;

    //很想策略模式
    private Map<String,DataSouce<T>> dataSouceMap = new HashMap<>();

    //最后加载，保证数据不为空
    @PostConstruct
    public void doInit(){
        dataSouceMap=new HashMap(){{
           put(ISearchEnum.POST.getValue(),postDataSouce);
           put(ISearchEnum.USER.getValue(),userDataSouce);
           put(ISearchEnum.PICTURE.getValue(),pictureDataSouce);
        }};
    }

    public DataSouce getDataSouceByTye(String type){
        if(type == null)return null;
        return dataSouceMap.get(type);
    }

}
