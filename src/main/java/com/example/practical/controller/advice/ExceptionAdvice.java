package com.example.practical.controller.advice;

import com.example.practical.exception.MyException;
import com.example.practical.json.BaseResponse;
import com.example.practical.json.ErrorJson;
import com.example.practical.util.Constant;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorJson> errors = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> errors.add(ErrorJson.builder()
                .field(fieldError.getField())
                .code(fieldError.getCode())
                .message(fieldError.getDefaultMessage())
                .build()));
        return ResponseEntity.badRequest().body(BaseResponse.builder().errors(errors).build());
    }

    @ExceptionHandler({MyException.class, PropertyReferenceException.class})
    public ResponseEntity<BaseResponse<Object>> handleMyException(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (e instanceof MyException) {
            httpStatus = ((MyException) e).getHttpStatus();
        }
        return ResponseEntity.status(httpStatus).body(BaseResponse.builder()
                .errors(Collections.singletonList(ErrorJson.builder()
                        .message(e.getMessage())
                        .build()))
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder()
                .errors(Collections.singletonList(ErrorJson.builder()
                        .message(Constant.ValidationMessage.INTERNAL_SERVER_ERROR)
                        .build()))
                .build());
    }

}
