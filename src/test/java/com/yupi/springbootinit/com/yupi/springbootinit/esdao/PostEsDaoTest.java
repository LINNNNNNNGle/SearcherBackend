package com.yupi.springbootinit.com.yupi.springbootinit.esdao;


import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.*;

@SpringBootTest
public class PostEsDaoTest {
    @Resource
    private PostEsDao postEsDao;

    @Test
    public void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(11L);
        postEsDTO.setTitle("我是鸡");
        postEsDTO.setContent("我吃米");
        postEsDTO.setTags(Arrays.asList("基","米"));
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    public void testFindById() {
        Optional<PostEsDTO> byId = postEsDao.findById(11L);
        System.out.println(byId);
    }

    @Test
    public void testFindByTitle() {
        List<PostEsDTO> post = postEsDao.findByTitle("鸡");
        System.out.println(post);
    }
}
