package com.yupi.springbootinit.model.dto.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class ISearchRequest implements Serializable {
   private String SearchText;
   private String type;
   private static final long serialVersionUID = 1L;
}
