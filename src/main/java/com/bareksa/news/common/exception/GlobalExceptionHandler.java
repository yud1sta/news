package com.bareksa.news.common.exception;

import com.bareksa.news.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse> validationErrorHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String error = "";
        if (!fieldErrors.isEmpty()) {
            error = fieldErrors.get(0).getField() + " " + fieldErrors.get(0).getDefaultMessage();
        }

        BaseResponse errorResponse = BaseResponse.error("B001", error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidParamException.class})
    public ResponseEntity<BaseResponse> invalidException(InvalidParamException e, HttpServletRequest request) {
        BaseResponse errorResponse = BaseResponse.error("B002", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<BaseResponse> notFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        if(e.getResource() == null && e.getFieldName() == null && e.getFieldValue() == null){
            BaseResponse errorResponse = BaseResponse.error("B003", "Data not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        BaseResponse errorResponse = BaseResponse.error("B003", e.getResource() + " with " + e.getFieldName() + " " + e.getFieldValue() + " not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceExistException.class})
    public ResponseEntity<BaseResponse> existException(ResourceExistException e, HttpServletRequest request) {
        BaseResponse errorResponse = BaseResponse.error("B004", e.getResource() + " with " + e.getFieldName() + " " + e.getFieldValue() + " already exists");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
