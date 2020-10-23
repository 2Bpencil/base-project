package com.tyf.baseproject.service;

import com.alibaba.fastjson.JSONArray;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.base.datapage.PageGetter;
import com.tyf.baseproject.dao.MouldRepository;
import com.tyf.baseproject.entity.Mould;
import com.tyf.baseproject.ustil.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description: 模板
 * @author: tyf
 * @create: 2020-07-27 11:47
 **/
@Service
public class MouldService extends PageGetter<Mould> {

    @Autowired
    private MouldRepository mouldRepository;
    @Value("config.operateFolder")
    private String operateFolder;



    /**
     * 分页查询
     * @param parameterMap
     * @return
     */
    public DataPage<Mould> getDataPage(Map<String,String[]> parameterMap){
        return super.getPages(parameterMap);
    }

    /**
     * @Description: 保存实体对象
     * @Param: [mould]
     * @return: com.tyf.baseproject.entity.Mould
     * @Author: tyf
     * @Date: 2020/7/28 9:38
     */ 
    public Mould saveEntity(Mould mould){
        return mouldRepository.save(mould);
    }
    /**
     * @Description: 根据id删除实体
     * @Param: [id]
     * @return: void
     * @Author: tyf
     * @Date: 2020/7/28 9:40
     */
    public void deleteEntity(Integer id){
        mouldRepository.deleteById(id);
    }

    public Mould getEntityById(Integer id){
        return mouldRepository.getOne(id);
    }

    /**
     * @Description: 获取数据库中的表
     * @Param: [databaseIp, databaseName, userName, password]
     * @return: java.lang.String
     * @Author: tyf
     * @Date: 2020/10/21 9:51
     */ 
    public String getTables(String databaseIp,String databaseName,String username,String password){
        String url = "jdbc:mysql://"+databaseIp+":3306/?serverTimezone=GMT";
        String driver = "com.mysql.jdbc.Driver";
        Connection conn = null;
        List<String> tableNameList = new ArrayList<String>();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            String sql = "SELECT table_name name FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = '"+databaseName+"'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if(!"case".equals(resultSet.getString("name"))){
                    tableNameList.add(resultSet.getString("name"));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return JSONArray.toJSONString(tableNameList);
    }

    public void createFiles(String databaseIp,String tableNames,String username,String password,String mouldIds){
        //创建临时文件夹
        String tempPath = operateFolder+"/"+ UUID.randomUUID();
        FileUtil.creatFoler(tempPath);





    }






}
