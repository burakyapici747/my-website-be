package com.blog.mywebsite.specification;

import com.blog.mywebsite.enumerator.SearchOperation;

public class SearchCriteria {
    private final String key;
    private final Object value;
    private final SearchOperation searchOperation;

    public SearchCriteria(String key, Object value, SearchOperation searchOperation){
        this.key = key;
        this.value = value;
        this.searchOperation = searchOperation;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public SearchOperation getSearchOperation() {
        return searchOperation;
    }
}