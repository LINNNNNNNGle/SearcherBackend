package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索使徒
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class ISearchVO implements Serializable {

    List<PostVO> posts;
    List<UserVO> users;
    List<Picture> pictures ;

    private static final long serialVersionUID = 1L;
}