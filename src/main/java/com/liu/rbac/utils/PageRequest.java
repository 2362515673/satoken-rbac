package com.liu.rbac.utils;

import lombok.Data;

/**
 * 分页请求
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 12;

    /**
     * 排序字段
     */
    private String sortField;
}
