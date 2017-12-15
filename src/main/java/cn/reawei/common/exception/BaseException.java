package cn.reawei.common.exception;

public class BaseException extends RuntimeException {

    private Boolean success = false;
    private String message;

    public BaseException() {
    }

    BaseException(String message) {
        this.message = message;
    }


}
