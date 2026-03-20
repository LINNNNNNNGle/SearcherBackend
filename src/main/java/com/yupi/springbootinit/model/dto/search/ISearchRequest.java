package com.yupi.springbootinit.model.dto.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class ISearchRequest extends PageRequest implements Serializable {
   private String SearchText;
   private String type;
   private static final long serialVersionUID = 1L;
}
