package com.tbt.ecommerce.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult<T> {

    private Integer status;

    private String message;

    private T payload;

    private JsonResult() {

    }

    private static <T> JsonResult<T> result(Integer status, String message, T payload) {
        JsonResult<T> result = new JsonResult<T>();
        result.status = status;
        result.message = message;
        result.payload = payload;
        return result;
    }

    public static <T> JsonResult<T> result(Integer status, String message) {
        return result(status, message, null);
    }

    public static <T> JsonResult<T> success(T payload) {
        return success(HttpStatus.SUCCESS.value, payload);
    }

    public static <T> JsonResult<T> success(String message, T payload) {
        return result(HttpStatus.SUCCESS.status, message, payload);
    }

    public static <T> JsonResult<T> fail(String message, T payload) {
        return result(HttpStatus.FAIL.status, message, payload);
    }

    @Getter
    public enum HttpStatus {

        SUCCESS(200, "SUCCESS"), FAIL(100, "FAIL"), NOT_FOUND(404, "NOT_FOUND"), UNAUTHORIZED(401, "UNAUTHORIZED");

        private Integer status;
        private String value;

        HttpStatus(Integer status, String value) {
            this.status = status;
            this.value = value;
        }
    }
}
