package com.tyf.baseproject.base.datapage;

import java.util.List;
import java.util.Map;

/**

/**
 * @ClassName DataPage
 * @Description: DataTable page对象
 * @Author tyf
 * @Date 2019/8/8 
 * @Version V1.0
 **/
public class DataPage<T> {
    //请求计数器,即绘制表索引，页面有多个表时需严格定义，一般情况下为1
    private Integer draw;
    //列表数据
    private List<Map<String,Object>> data;
    //记录总数
    private Integer recordsTotal;
    //过滤后的总数
    private Integer recordsFiltered;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
    