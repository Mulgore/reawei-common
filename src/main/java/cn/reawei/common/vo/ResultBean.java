package cn.reawei.common.vo;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message = "ok";
    private Boolean success = true;
    private T data;
    public ResultBean() {
        super();
    }
    public ResultBean(T data) {
        super();
        this.data = data;
    }
    public ResultBean(Throwable e) {
        super();
        this.message = e.toString();
        this.success = false ;
    }
    public ResultBean(String message) {
        super();
        this.message = message;
        this.success = false ;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
