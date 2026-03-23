package com.yupi.springbootinit.model.dto.picture;

import com.yupi.springbootinit.common.PageRequest;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * 
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {


    /**
     * 搜索词
     */
    private String searchText;

}