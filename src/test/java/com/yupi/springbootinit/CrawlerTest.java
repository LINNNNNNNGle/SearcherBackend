package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import static org.junit.jupiter.api.Assertions.*;

import com.yupi.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {
    @Resource
    PostService postService;
    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36";

    @Test
    public void picture() throws IOException {
        List<Picture> pictureList = new ArrayList<>();
        String url = "https://www.bing.com/images/search?q=时尚杂志封面&first=1";
        Document doc = null;
        try {
            doc =Jsoup.connect(url)
                .userAgent(userAgent)
                .timeout(10000)
                .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements allDiv = doc.select(".iuscp.varh.isv");
        for (Element element : allDiv) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String,Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String) map.get("murl");
            pictureList.add(new Picture(murl,title));
        }
        System.out.println(pictureList);
    }


    @Test
    public void test() {
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
        assertTrue(b);

    }
}
