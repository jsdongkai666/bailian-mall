package com.cuning.util;

import lombok.Data;

import java.util.Collection;

/**
 * @author dengteng
 * @title: PageSupport
 * @projectName unitReturn
 * @description: 分页返回实体类
 * @date 2022/5/13
 */
@Data
public class PageSupport<T> {

    /**
     * 分页页码
     */
    private Integer pageNo;
    /**
     * 分页条数
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前分页数据
     */
    private Collection<T> pageData;

    /**
     * 页码处理
     * @param pageNo
     */
    public void setPageNo(Integer pageNo) {
        if(pageNo < 1){
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    /**
     * 分页条数处理
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        if(pageSize < 3){
            pageSize = 3;
        }
        this.pageSize = pageSize;
    }

    /**
     * 当总条数确定时，总页数也是可以确定的
     */
    public void setTotalCount(Integer totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        }
    }
}
