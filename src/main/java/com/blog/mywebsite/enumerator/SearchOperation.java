package com.blog.mywebsite.enumerator;

public enum SearchOperation {
    EQUAL,
    NOT_EQUAL,
    NOT_NULL(true),
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    LIKE;

    private boolean nullable;

    private SearchOperation(){}

    private SearchOperation(boolean nullable){
        this.nullable = nullable;
    }

    public boolean isNullable(){
        return this.nullable;
    }
}