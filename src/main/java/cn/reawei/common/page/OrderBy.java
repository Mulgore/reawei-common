package cn.reawei.common.page;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by xingwu on 2017/5/25.
 */
public class OrderBy implements Serializable {
    private static final long serialVersionUID = -521144609074292900L;
    private String fieldName;
    private boolean desc = true;
    private String toOrderBy;

    public OrderBy() {
    }

    public OrderBy(String fieldName, boolean desc) {
        this.fieldName = fieldName;
        this.desc = desc;
        this.toOrderBy = this.toOrderBy();
    }

    public String getToOrderBy() {
        return StringUtils.isNotBlank(this.toOrderBy)?this.toOrderBy:this.toOrderBy();
    }

    public void setToOrderBy(String toOrderBy) {
        this.toOrderBy = toOrderBy;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isDesc() {
        return this.desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    private String toOrderBy() {
        if(StringUtils.isBlank(this.fieldName)) {
            return null;
        } else {
            String orderby = this.fieldName;
            if(this.desc) {
                orderby = orderby + " desc";
            } else {
                orderby = orderby + " asc";
            }

            return orderby;
        }
    }

    public String toString() {
        return "OrderBy{fieldName='" + this.fieldName + '\'' + ", desc=" + this.desc + ", toOrderBy='" + this.toOrderBy + '\'' + '}';
    }
}