package cn.reawei.page;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by xingwu on 2017/5/25.
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -8722768053248374040L;
    private boolean success = true;
    private long total = 0L;
    private Collection<T> dataList;
    private String info;
    private String key;

    public Result(boolean success, long total, Collection<T> dataList, String info, String key) {
        this.success = success;
        this.total = total;
        this.dataList = dataList;
        this.info = info;
        this.key = key;
    }

    public Result() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Collection<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(Collection<T> dataList) {
        this.dataList = dataList;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return "Result{success=" + this.success + ", total=" + this.total + ", dataList=" + this.dataList + ", info='" + this.info + '\'' + ", key='" + this.key + '\'' + '}';
    }
}
