package com.yupi.springbootinit.datasouce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口
 * @param <T>
 */
public interface DataSouce<T> {
    /**
     * 搜索接口
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> isearch(String searchText, int pageNum, int pageSize);
}
