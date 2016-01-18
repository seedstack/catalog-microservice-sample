package org.seedstack.samples.catalog.rest;

import org.seedstack.business.view.Page;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PageInfo {

    public static final String PAGE_INDEX = "pageIndex";
    public static final String PAGE_SIZE = "pageSize";

    @Min(0)
    @DefaultValue("0")
    @QueryParam(PAGE_INDEX)
    public int pageIndex;

    @Min(1)
    @DefaultValue("10")
    @QueryParam(PAGE_SIZE)
    public int pageSize;

    public Page page() {
        return new Page(pageIndex, pageSize);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
