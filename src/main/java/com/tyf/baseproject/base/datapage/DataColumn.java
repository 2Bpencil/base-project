package com.tyf.baseproject.base.datapage;
/**
* @Description: DataTable  行数据
* @Author: Mr.Tan
* @Date: 2019/10/8 9:24
*/
public class DataColumn {

    private String data;

    private String name;

    private Boolean searchable;

    private Boolean orderable;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }
}
