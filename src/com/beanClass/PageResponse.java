package com.beanClass;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private long totalElements;

    public List<T> getContent() {
        return content;
    }
    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
