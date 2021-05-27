package com.example.practical.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MyException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public static Supplier<MyException> getSupplier(HttpStatus httpStatus, String message) {
        return () -> new MyException(httpStatus, message);
    }

}
