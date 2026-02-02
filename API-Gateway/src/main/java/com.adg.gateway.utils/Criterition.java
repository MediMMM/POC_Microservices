package com.adg.gateway.utils;

public class Criterition {
    private Integer offset; // starting index
    private Integer limit;  // page size

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
