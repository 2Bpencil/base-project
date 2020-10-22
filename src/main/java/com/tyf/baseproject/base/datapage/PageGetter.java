package com.tyf.baseproject.base.datapage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PageGetter
 * @Description: dataTable 抽象类
 * @Author tyf
 * @Date 2019/8/8 
 * @Version V1.0
 **/
public class PageGetter<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private Class<T> entityClass = ReflectionUtils.getClassGenricType(getClass());
    /**
     *
     * @param parameterMap  参数
     * @return
     */
    public DataPage<T> getPages(Map<String,String[]> parameterMap){
        String tableName = entityClass.getAnnotation(Table.class).name();
        DataPage<T> page = new DataPage<>();
        page.setDraw(Integer.parseInt(parameterMap.get("draw")[0].toString()));
        //起始页
        int start = Integer.parseInt(parameterMap.get("start")[0].toString());
        //分页长度
        int length = Integer.parseInt(parameterMap.get("length")[0].toString());
        //条件搜索
        String search = parameterMap.get("search[value]")[0].toString();
        //排序的列
        Integer orderColumn = Integer.parseInt(parameterMap.get("order[0][column]")[0].toString());
        //正序倒序
        String orderDir = parameterMap.get("order[0][dir]")[0].toString();
        //sql 语句
        String countSql = "SELECT COUNT(*) FROM "+tableName;
        String sql = "select * from "+tableName;
        String sqlWhere = "";
        String sqlOrder = "";

        //将列数据抽象成对象
        List<String> keyList = parameterMap.keySet().stream().filter(key->key.contains("columns") && !key.contains("[search][value]")&& !key.contains("[search][regex]")).collect(Collectors.toList());
        List<DataColumn> columnList = new ArrayList<>();
        for (int i = 0;i<keyList.size();i+=4){
            DataColumn column = new DataColumn();
            column.setData(parameterMap.get(keyList.get(i))[0]);
            column.setName(parameterMap.get(keyList.get(i+1))[0]);
            column.setSearchable(Boolean.parseBoolean(parameterMap.get(keyList.get(i+2))[0]));
            column.setOrderable(Boolean.parseBoolean(parameterMap.get(keyList.get(i+3))[0]));
            columnList.add(column);
        }
        //拼接sql 语句
        //搜索条件不为空  拼接where条件语句
        if(StringUtils.isNotBlank(search)){
            StringBuffer sb = new StringBuffer();
            columnList.forEach(column->{
                if(column.getSearchable()){
                    if(StringUtils.isNotBlank(sb.toString())){//如果不为空则
                        sb.append(" OR "+column.getData()+" LIKE \"%"+search+"%\"");
                    }else{
                        sb.append (" WHERE "+column.getData()+" LIKE \"%"+search+"%\"");
                    }
                }
            });
            sqlWhere = sb.toString();
        }
        //拼接 排序sql
        String order = columnList.get(orderColumn).getData();
        sqlOrder = " ORDER BY "+order+" "+ orderDir;
        sql = sql + sqlWhere + sqlOrder + " LIMIT " + start + "," + length;
        //查询数据库
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        Integer total = jdbcTemplate.queryForObject(countSql,Integer.class);
        Integer filteredTotal = jdbcTemplate.queryForObject(countSql+sqlWhere+sqlOrder,Integer.class);
        page.setData(list);
        page.setRecordsFiltered(filteredTotal);
        page.setRecordsTotal(total);
        return page;
    }

    /**
     * 自定义sql 查询
     * @param parameterMap
     * @param sql
     * @return
     */
    public DataPage<T> getPages(Map<String,String[]> parameterMap,String sql,String countSql){
        String tableName = entityClass.getAnnotation(Table.class).name();
        DataPage<T> page = new DataPage<>();
        page.setDraw(Integer.parseInt(parameterMap.get("draw")[0].toString()));
        //起始页
        int start = Integer.parseInt(parameterMap.get("start")[0].toString());
        //分页长度
        int length = Integer.parseInt(parameterMap.get("length")[0].toString());
        //条件搜索
        String search = parameterMap.get("search[value]")[0].toString();
        //排序的列
        Integer orderColumn = Integer.parseInt(parameterMap.get("order[0][column]")[0].toString());
        //正序倒序
        String orderDir = parameterMap.get("order[0][dir]")[0].toString();
        //sql 语句

        String sqlWhere = "";
        String sqlOrder = "";

        //将列数据抽象成对象
        List<String> keyList = parameterMap.keySet().stream().filter(key->key.contains("columns") && !key.contains("[search][value]")&& !key.contains("[search][regex]")).collect(Collectors.toList());
        List<DataColumn> columnList = new ArrayList<>();
        for (int i = 0;i<keyList.size();i+=4){
            DataColumn column = new DataColumn();
            column.setData(parameterMap.get(keyList.get(i))[0]);
            column.setName(parameterMap.get(keyList.get(i+1))[0]);
            column.setSearchable(Boolean.parseBoolean(parameterMap.get(keyList.get(i+2))[0]));
            column.setOrderable(Boolean.parseBoolean(parameterMap.get(keyList.get(i+3))[0]));
            columnList.add(column);
        }
        //拼接sql 语句
        //搜索条件不为空  拼接where条件语句
        if(StringUtils.isNotBlank(search)){
            StringBuffer sb = new StringBuffer();
            columnList.forEach(column->{
                if(column.getSearchable()){
                    if(StringUtils.isNotBlank(sb.toString())){//如果不为空则
                        sb.append(" OR "+tableName+"."+column.getData()+" LIKE \"%"+search+"%\"");
                    }else{
                        sb.append (" AND "+tableName+"."+column.getData()+" LIKE \"%"+search+"%\"");
                    }
                }
            });
            sqlWhere = sb.toString();
        }
        //拼接 排序sql
        String order = columnList.get(orderColumn).getData();
        sqlOrder = " ORDER BY "+tableName+"."+order+" "+ orderDir;
        sql = sql + sqlWhere + sqlOrder + " LIMIT " + start + "," + length;
        //查询数据库
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        Integer total = jdbcTemplate.queryForObject(countSql,Integer.class);
        Integer filteredTotal = jdbcTemplate.queryForObject(countSql+sqlWhere+sqlOrder,Integer.class);
        page.setData(list);
        page.setRecordsFiltered(filteredTotal);
        page.setRecordsTotal(total);
        return page;
    }



}
    