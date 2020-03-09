package com.bareksa.news.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    private Integer fieldValue;
    private String fieldName;
    private String resource;

    public ResourceNotFoundException(Integer fieldValue, String fieldName, String resource) {
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
        this.resource = resource;
    }

    public Integer getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getResource() {
        return resource;
    }
}
