package com.yupi.springbootinit.model.entity;

import lombok.Data;

@Data
public class Picture {
    private String url;
    private String title;

    public Picture(String url, String title){
        this.url = url;
        this.title = title;
    }

}
