package com.lostfound.common.utils;

import com.lostfound.common.config.Constant;
import com.lostfound.common.dao.GeneratorMapper;
import com.lostfound.common.domain.ColumnDO;
import com.lostfound.common.domain.TableDO;
import com.lostfound.common.service.GeneratorService;
import com.lostfound.common.service.impl.GeneratorServiceImpl;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName CodeUtil
 * @Description
 * @Author yangtao@biyouxinli.com
 * @Date 2019/10/23 0023 11:44
 **/
public class CodeUtil {
    private static String mysql_url = "localhost";
    private static String mysql_account = "root";
    private static String mysql_password = "root";
    private static String database = "lostfound";
    private static String project_name = "lostfound";


    public void  generatorCode(String[] tableNames) throws SQLException {
        // 数据连Connection获取,自己想办法就行.
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + database, mysql_account, mysql_password);
        Statement st = conn.createStatement();

        for(String tableName : tableNames){

            //查询表信息
            Map<String, String> table = getTableInfo(tableName,st);
            //查询列信息
             List<Map<String, String>> columns = getColumsInfo(tableName,st);
            //生成代码
           generatorCode(table, columns);
        }
    }

    private List<Map<String, String>> getColumsInfo(String tableName,Statement st) throws SQLException {
        String strsql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"
                + tableName + "' AND table_schema = '" + database + "';";// +" WHERE ROWNUM=1"
        ResultSet rs = st.executeQuery(strsql);
        List<Map<String, String>> columnsInfo= new ArrayList<>();
        while (rs.next()){
            Map<String,String> map = new HashMap<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsCount = rsmd.getColumnCount();
            for (int i = 1;i<=columnsCount;i++){
                String columnName = rsmd.getColumnName(i);
                map.put(columnName,rs.getString(columnName));
            }
            columnsInfo.add(map);
        }
        return  columnsInfo;
    }

    private Map<String,String> getTableInfo(String tableName,Statement st) throws SQLException {
        Map<String,String> tableInfo = new HashMap<>();
        String strsql = "select table_schema tableSchema, table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables \r\n"
                + "	where table_schema = '"+database+"' and table_name = '"+tableName+"';";

        ResultSet rs = st.executeQuery(strsql);
        rs.first();
        tableInfo.put("tableName",rs.getString("tableName"));
        tableInfo.put("engine",rs.getString("engine"));
        tableInfo.put("tableComment",rs.getString("tableComment"));
        tableInfo.put("createTime",rs.getString("createTime"));
        tableInfo.put("tableSchema",rs.getString("tableSchema"));
        return tableInfo;

    }

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
//        templates.add("templates/common/generator/domain.java.vm");
//        templates.add("templates/common/generator/Dao.java.vm");
//        //templates.add("templates/common/generator/Mapper.java.vm");
//        templates.add("templates/common/generator/Mapper.xml.vm");
//        templates.add("templates/common/generator/Service.java.vm");
//        templates.add("templates/common/generator/ServiceImpl.java.vm");
//        templates.add("templates/common/generator/Controller.java.vm");
//        templates.add("templates/common/generator/list.html.vm");
//        templates.add("templates/common/generator/add.html.vm");
//        templates.add("templates/common/generator/edit.html.vm");
//        templates.add("templates/common/generator/list.js.vm");
//        templates.add("templates/common/generator/add.js.vm");
//        templates.add("templates/common/generator/edit.js.vm");
        templates.add("templates/common/generator/LianxinController.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */


    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns) {
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        tableDO.setDbName(table.get("tableSchema"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"), config.getString("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(org.apache.commons.lang.StringUtils.uncapitalize(className));

        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("COLUMN_NAME"));
            columnDO.setDataType(column.get("DATA_TYPE"));
            columnDO.setComments(column.get("COLUMN_COMMENT"));
            columnDO.setExtra(column.get("EXTRA"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(org.apache.commons.lang.StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        if(tableDO.getDbName().indexOf(config.getString("dataBasePrefix"))==-1){
            map.put("pathName", tableDO.getDbName());
        }else {
            map.put("pathName",tableDO.getDbName().substring(tableDO.getDbName().indexOf(config.getString("dataBasePrefix"))));
        }

        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                String fileName = getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package"));
                File dir = new File(fileName.substring(0,fileName.lastIndexOf(File.separator)));
                if (dir != null && !dir.exists()) {
                    dir.mkdirs();
                }
                FileWriter fw = new FileWriter(fileName);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(sw.toString());
                pw.flush();
                pw.close();
                System.out.println("生成controller...");
            } catch (IOException e) {
                throw new BDException("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (Constant.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName) {
        String packagePath = System.getProperty("user.dir") + File.separator + project_name+ File.separator+ "src"+File.separator+"main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + "domain" + File.separator + className + "DO.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

//		if(template.contains("Mapper.java.vm")){
//			return packagePath + "dao" + File.separator + className + "Mapper.java";
//		}

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName + File.separator + className + "Mapper.xml";
        }

        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + classname + ".html";
            //				+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }
        if (template.contains("add.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + "add.html";
        }
        if (template.contains("edit.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + packageName + File.separator + classname + File.separator + "edit.html";
        }

        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + classname + ".js";
            //		+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }
        if (template.contains("add.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + "add.js";
        }
        if (template.contains("edit.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + packageName + File.separator + classname + File.separator + "edit.js";
        }

//		if(template.contains("menu.sql.vm")){
//			return className.toLowerCase() + "_menu.sql";
//		}

        return null;
    }
    public static void  main(String[] args) throws SQLException {
        CodeUtil util = new CodeUtil();
        String[] tables = {"tb_daily_mood"};
        util.generatorCode(tables);
    }
}
