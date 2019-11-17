package org.openmore.cms.dto.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by michaeltang on 2018/3/23.
 */
public class Pagination {
    @ApiModelProperty(value = "当前页码")
    public long page;
    @ApiModelProperty(value = "每页多少条")
    public long limit;
    @ApiModelProperty(value = "共计多少条")
    public long totalItem;
    @ApiModelProperty(value = "共计多少页")
    public long totalPage;


    public Pagination(PageInfo pageInfo){
        this.page = pageInfo.getPageNum();
        this.limit = pageInfo.getPageSize();
        this.totalItem = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
    }

    public Pagination(PageInfo pageInfo, int totalItem){
        this.page = pageInfo.getPageNum();
        this.limit = pageInfo.getPageSize();
        this.totalItem = totalItem;
        this.totalPage = pageInfo.getPages();
    }

    public Pagination(long page, long limit, long totalItem, long totalPage){
        this.page = page;
        this.limit = limit;
        this.totalItem = totalItem;
        this.totalPage = totalPage;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}
