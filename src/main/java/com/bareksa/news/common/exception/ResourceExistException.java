package com.bareksa.news.common.exception;

public class ResourceExistException extends RuntimeException {
    private String fieldValue;
    private String fieldName;
    private String resource;

    public ResourceExistException(String fieldValue, String fieldName, String resource) {
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
        this.resource = resource;

    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getResource() {
        return resource;
    }
}
