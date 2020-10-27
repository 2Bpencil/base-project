package com.tyf.baseproject.service;

import com.alibaba.fastjson.JSONArray;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.base.datapage.PageGetter;
import com.tyf.baseproject.dao.MouldRepository;
import com.tyf.baseproject.entity.Mould;
import com.tyf.baseproject.ustil.BeanConvertUtil;
import com.tyf.baseproject.ustil.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @description: 模板
 * @author: tyf
 * @create: 2020-07-27 11:47
 **/
@Service
public class MouldService extends PageGetter<Mould> {

    @Autowired
    private MouldRepository mouldRepository;
    @Value("${folder.operateFolder}")
    private String operateFolder;
    @Value("${folder.saveFolder}")
    private String saveFolder;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    private  Configuration cfg = new Configuration();



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

    public void createFiles(String databaseIp,String tableNames,String username,String password,String mouldIds,String databaseName){
        //创建临时文件夹
        String tempPath = operateFolder+"/"+ UUID.randomUUID();
        String savePath = saveFolder+"/"+UUID.randomUUID();
        FileUtil.creatFoler(tempPath);
        FileUtil.creatFoler(savePath);
        List<Mould> mouldList = new ArrayList<>();
        //生成模板
        Stream.of(mouldIds.split(",")).forEach(id->{
            Mould mould = mouldRepository.getOne(Integer.parseInt(id));
            //将mould里的模板数据写到文件里
            try {
                FileUtil.writeFileContent(tempPath+"/"+ mould.getName()+".ftl",mould.getContent());
                mouldList.add(mould);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //根据模板生成对应的java文件
        Stream.of(tableNames.split(",")).forEach(tableName->{
            String entityName = null;
            entityName = BeanConvertUtil.fieldToProperty(tableName);
            entityName = BeanConvertUtil.initcap(entityName);//首字母大写
            Map<String, Object> dataMap = getEntityData(tableName,username,password,databaseIp,entityName,databaseName);
            String ClassName = dataMap.get("entityName").toString();
            try {
                cfg.setDirectoryForTemplateLoading(new File(tempPath));
            }catch (Exception e){
                e.printStackTrace();
            }
            mouldList.forEach(mould->{
                try{
                    String saveFileName = "";
                    if(mould.getName().contains("Entity")){
                        saveFileName =ClassName+"."+mould.getSuffix();
                    }else{
                        saveFileName = ClassName+mould.getName()+"."+mould.getSuffix();
                    }
                    Writer out = new OutputStreamWriter(new FileOutputStream(savePath+"/"+saveFileName), "utf-8");
                    Template temp = cfg.getTemplate(mould.getName()+".ftl");
                    temp.process(dataMap, out, ObjectWrapper.BEANS_WRAPPER);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
        FileUtil.delFolder(tempPath);

    }

    private Map<String, Object> getEntityData(String tableName,String username,String password,String databaseIp,String entityName,String databaseName) {

        Map<String, Object> dataMap = new HashMap<String, Object>();

        List<String> importList = new ArrayList<String>();//存放import

        List<Map<String, String>> propertyMapList =  new ArrayList<>();
        List<Map<String, String>> methodMapList =  new ArrayList<>();

        Connection conn = null;
        try {
            String url = "jdbc:mysql://"+databaseIp+":3306/?serverTimezone=GMT";
            conn = DriverManager.getConnection(url, username, password);
            String strsql = "select * from " + databaseName+"."+tableName;

            PreparedStatement pstmt = conn.prepareStatement(strsql);
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            String[] cols = new String[size];
            String[] colnames = new String[size];
            String[] colTypes = new String[size];
            int[] colSizes = new int[size];
            boolean f_util = false;
            boolean f_sql = false;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                cols[i] = rsmd.getColumnName(i + 1);
                colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
                colnames[i] = BeanConvertUtil.fieldToProperty(colnames[i]);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //*********************************************************
            if(f_util){
                importList.add("import java.util.*;");
            }
            if(f_sql){
                importList.add("import java.sql.*;");
            }
            //*********************************************************
            String[] propertyType = new String[colTypes.length];
            for (int i = 0; i < colTypes.length; i++) {
                String type = colTypes[i];
                propertyType[i] = sqlType2JavaType(type);
            }
            List<String> commentlist = new ArrayList<String>();//ReadFileUtil.readFileByLinesReturnComment(tableName);
            String commentSql = "show full FIELDS FROM "+databaseName+"."+tableName;
            pstmt = conn.prepareStatement(commentSql);
            ResultSet rs = pstmt.executeQuery(commentSql);
            while(rs.next()){
                commentlist.add(rs.getString("Comment"));
            }
            for (int i = 0; i < cols.length; i++) {
                Map<String, String> propertyMap = new HashMap<String, String>();
                Map<String, String> methodMap = new HashMap<String, String>();
                propertyMap.put("col", cols[i]);
                propertyMap.put("colName", BeanConvertUtil.toLowerCaseFirstOne(colnames[i]));
                propertyMap.put("colType", propertyType[i]);
                propertyMap.put("comment", commentlist.get(i));
                propertyMapList.add(propertyMap);

                methodMap.put("colType", propertyType[i]);
                methodMap.put("colName", BeanConvertUtil.toLowerCaseFirstOne(colnames[i]));
                methodMap.put("colNameUp", BeanConvertUtil.initcap(colnames[i]));//首字母大写
                methodMapList.add(methodMap);
            }
            dataMap.put("tableName", tableName);
            dataMap.put("entityName", entityName);
            dataMap.put("entityNameLower", BeanConvertUtil.toLowerCaseFirstOne(entityName));//首字母小写的实体名
            dataMap.put("propertyMapList", propertyMapList);
            dataMap.put("methodMapList", methodMapList);
            dataMap.put("importList", importList);
            dataMap.put("ctx", "${ctx}");
            dataMap.put("contextPath", "${pageContext.request.contextPath}");
            dataMap.put("project","project");
            dataMap.put("datetime", sdf.format(new Date()));
            return dataMap;
        }  catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private  String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("double")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("date")) {
            return "String";
        } else if(sqlType.equalsIgnoreCase("varbinary") || sqlType.equalsIgnoreCase("longblob")){
            return "Byte[]";
        }

        else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        }
        return null;
    }



}
