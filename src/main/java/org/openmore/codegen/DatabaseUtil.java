package org.openmore.codegen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

    public static final int DB_VERSION_5 = 5;
    public static final int DB_VERSION_8 = 8;
    private static final String DB_DRIVER_5 = "com.mysql.jdbc.Driver";
    private static final String DB_DRIVER_8 = "com.mysql.cj.jdbc.Driver";
    private final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    /**数据库驱动*/
    private  String driver = DB_DRIVER_5;
    private  String url = "jdbc:mysql://39.108.123.150:3306/tb_han?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    /**数据库用户名*/
    private  String username = "user_lz";
    /**数据库密码*/
    private  String password = "lz_627458";

    /**数据库名称*/
    private String dbName = "";

    /**数据库版本*/
    private int dbVersion = DB_VERSION_5;

    private static final String SQL = "SELECT * FROM ";
    // 数据库操作
    public DatabaseUtil(int dbVersion, String url, String username, String password){
        this.dbVersion = dbVersion;
        if (dbVersion == DB_VERSION_8){
            this.driver = DB_DRIVER_8;
        }
        if (null == url || url.isEmpty()){
            throw new RuntimeException("url 不合法");
        }
        this.url = url;
        this.dbName = getDBName(url);
        this.username = username;
        this.password = password;
        validate();
    }
/*
    public static void start(){
        List<String> tableNames = getTableNames();
        System.out.println("0: create by all tables");
        for (int i=0;i < tableNames.size();i++) {
            System.out.println(i+1+": create by "+tableNames.get(i));
        }
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        if(x ==0){
            System.out.println("start create...");
            for (String tableName : tableNames) {
                System.out.println("ColumnNames:" + getColumnNames(tableName));
                System.out.println("ColumnTypes:" + getColumnTypes(tableName));
                System.out.println("ColumnComments:" + getColumnComments(tableName));
            }
        }else{
            System.out.println("start create...");
            System.out.println("ColumnNames:" + getColumnNames(tableNames.get(x-1)));
            System.out.println("ColumnTypes:" + getColumnTypes(tableNames.get(x-1)));
            System.out.println("ColumnComments:" + getColumnComments(tableNames.get(x-1)));
        }
        System.out.println("...create done");
    }*/

    /*public static void main(String[] args) {
       // start();
        List<String> tableNames = getTableNames();
        System.out.println("tableNames:" + tableNames);
        for (String tableName : tableNames) {
            System.out.println("ColumnNames:" + getColumnNames(tableName));
            System.out.println("ColumnTypes:" + getColumnTypes(tableName));
            System.out.println("ColumnComments:" + getColumnComments(tableName));
        }
    }*/
    /**数据库驱动加载校验*/
    private void validate(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            if (this.dbVersion == DB_VERSION_5){
                rs = db.getTables(null, null, null, new String[] {"TABLE"});
            } else if (dbVersion == DB_VERSION_8){
            rs = db.getTables(dbName, dbName, null, new String[] {"TABLE"});
            }
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            logger.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                logger.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            logger.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    logger.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(sqlType2JavaType(rsmd.getColumnTypeName(i + 1)));
            }
        } catch (SQLException e) {
            logger.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    logger.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     * @param tableName
     * @return
     */
    public List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>(); //列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                String comment = rs.getString("Comment");
                columnComments.add(comment == null ? "" : comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    logger.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }

    public String initcap(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**数据库类型转换*/
    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("time") ||
                sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        }
        return null;
    }

    /**根据url获取数据库名*/
    private String getDBName(String url){
        if (null == url){
            return null;
        }
        String[] sArray = url.split("/");
        int end = sArray[3].indexOf("?");
        String name = sArray[3].substring(0, end);
        return name;
    }
}