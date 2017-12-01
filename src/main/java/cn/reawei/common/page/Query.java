package cn.reawei.common.page;


import java.io.Serializable;

/**
 * Created by xingwu on 2017/5/25.
 */
public class Query<T> implements Serializable {

    private static final long serialVersionUID = 1930382256159908170L;
    private T queryObject;
    private int start = 0;
    private int limit = 20;
    private OrderBy orderBy;

    public Query() {
    }

    public Query(T queryObject, int start, int limit, OrderBy orderBy) {
        this.queryObject = queryObject;
        this.start = start;
        this.limit = limit;
        this.orderBy = orderBy;
    }

    public T getQueryObject() {
        return this.queryObject;
    }

    public void setQueryObject(T queryObject) {
        this.queryObject = queryObject;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return this.limit == 0?20:this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByClause() {
        if(this.orderBy != null) {
            String sort = this.orderBy.isDesc()?" desc":"asc";
            return this.orderBy.getFieldName() + sort;
        } else {
            return null;
        }
    }

    public String toString() {
        return "Query{queryObject=" + this.queryObject + ", start=" + this.start + ", limit=" + this.limit + ", orderBy=" + this.orderBy + '}';
    }
}

