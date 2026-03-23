package com.yupi.springbootinit.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.datasouce.DataSouce;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索使徒
 *
 *
 *
 */
@Data
public class ISearchVO implements Serializable {

    List<PostVO> posts;
    List<UserVO> users;
    List<Picture> pictures ;
    List<?> datas;
    private static final long serialVersionUID = 1L;
}