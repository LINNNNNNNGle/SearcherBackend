package com.yupi.springbootinit.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全量同步帖子到 es
 *
 * 
 *
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FutchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        //获得数据
        String json = "{\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"type\":\"all\",\"reviewStatus\":1,\"current\":1,\"pageSize\":10,\"tags\":[\"实习\"]}";
        String url = "https://api.codefather.cn/api/search";
        String result2 = HttpRequest.get(url)
                .body(json)
                .execute().body();
        System.out.println(result2);
        // json
        Map<String, Object> map = JSONUtil.toBean(result2, Map.class);
        JSONObject data =(JSONObject) map.get("data");
        JSONObject searchPage =(JSONObject) data.get("searchPage");
        JSONArray records =(JSONArray) searchPage.get("records");
        System.out.println(records);
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject jsonObject = (JSONObject) record;
            Post post = new Post();
            post.setContent(jsonObject.getStr("content"));
            post.setTitle(jsonObject.getStr("title"));

            JSONArray tags = (JSONArray) jsonObject.get("tags");
            List<String>tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));

            post.setUserId(1L);
            postList.add(post);

        }
        System.out.println(postList);
        //存库
        boolean b = postService.saveBatch(postList);
        if(b)
            log.info("【success】:"+postList.size());
        else
            log.error("[error]");
    }
}
