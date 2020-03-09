package com.bareksa.news.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private boolean status;
    private String code;
    private String message;
    private T data;

    public static BaseResponse error(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }

    public static BaseResponse ok() {
        return new BaseResponse<>(true, "200", "success", null);
    }

    public static <I> BaseResponse<I> ok(I body) {
        return new BaseResponse<I>(true, "200", "success", body);
    }

    public static <I> BaseResponse<I> created(I body) {
        return new BaseResponse<I>(true, "201", "created", body);
    }

//    public static BaseResponse created() {
//        return new BaseResponse<>(true, "201", "created", null);
//    }

//    public static BaseResponse created(Object dt) {
//        BaseResponse<> baseResponse = new BaseResponse<>();
//        baseResponse.setStatus(true);
//        baseResponse.setCode("201");
//        baseResponse.setMessage("created");
//        baseResponse.setData(dt);
//        return baseResponse;
//    }
}
