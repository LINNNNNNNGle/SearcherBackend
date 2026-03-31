package com.yupi.springbootinit.job.once;

import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.yupi.springbootinit.common.ErrorCode.SYSTEM_ERROR;

/**
 * 全量同步帖子到 es
 *
 * 
 *
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        List<Post> postList = postService.list();
        if (CollUtil.isEmpty(postList)) {
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("FullSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            int finalI = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    postEsDao.saveAll(postEsDTOList.subList(finalI, end));
                } catch (Exception e) {
                    new BusinessException(SYSTEM_ERROR);
                }
            });
            CompletableFuture.allOf(future).join();
        }
        log.info("FullSyncPostToEs end, total {}", total);
    }
}
