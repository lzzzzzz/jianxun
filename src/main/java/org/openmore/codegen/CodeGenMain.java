package org.openmore.codegen;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenMain {

    private static CodeGenMain instance;

    private static DatabaseUtil databaseUtil;

    /**
     * 设置一级目录包名
     */
    private String basePackage = "org.openmore";

    /**
     * 读取数据库表生成实体类和模板源码
     */
    private CodeGenMain() {
    }

    public static CodeGenMain build(int dbVersion, String url, String username, String password) {
        if (null == instance) {
            instance = new CodeGenMain();
        }
        databaseUtil = new DatabaseUtil(dbVersion, url, username, password);
        return instance;
    }

    /**
     * 设置一级目录包名
     */
    public CodeGenMain setBasePackage(String basePackage) {
        if (null != basePackage && !basePackage.isEmpty())
            this.basePackage = basePackage;
        return this;
    }

    /**
     * 命令行提示操作
     */
    public void start() {
        List<String> tableNames = databaseUtil.getTableNames();
        int x = 0;
        while (true) {
            System.out.println("-1: exit");
            System.out.println("0: create by all tables");
            for (int i = 0; i < tableNames.size(); i++) {
                System.out.println(i + 1 + ": create " + tableNames.get(i));
            }
            System.out.println("input number will do as resume：");
            Scanner s = new Scanner(System.in);
            try {
                x = s.nextInt();
            } catch (Exception e) {
                System.out.println(" goodbye!");
                return;
            }
            if (x < 0) {
                System.out.println(" goodbye!");
                return;
            } else if (x == 0) {
                System.out.println("create code ...");
                for (String tableName : tableNames) {
                    createByTableName(tableName);
                }
                System.out.println("...create done");
            } else if (x > 0 && x <= tableNames.size()) {
                System.out.println("start create...");
                createByTableName(tableNames.get(x - 1));
                System.out.println("...create done");
            } else {
                System.out.println("cancel for illegal number!");
            }
//            System.out.println("press any key to continue?(-1 exit)");
//            s.next();
        }
    }

    /**
     * 直接创建全部
     */
    public void createAll() {
        List<String> tableNames = databaseUtil.getTableNames();
        System.out.println("generating...");
        for (String tableName : tableNames) {
            createByTableName(tableName);
        }
        System.out.println("...create done");
    }

    private void createByTableName(String tableName) {
        List<String> columns = databaseUtil.getColumnNames(tableName);
        List<String> columnTypes = databaseUtil.getColumnTypes(tableName);
        List<String> columnComments = databaseUtil.getColumnComments(tableName);
        List<DtoParam> dps = new ArrayList<DtoParam>();
        for (int i = 0; i < columns.size(); i++) {
            DtoParam dp = new DtoParam(columns.get(i), columnTypes.get(i), columnComments.get(i));
            dps.add(dp);
        }
        String t = "allApi";
        Gson gson = new Gson();
        String attrs = gson.toJson(dps);
        DtoService service = new DtoService();
        service.setBasePackage(basePackage);
        DtoResponse re = service.pageCreateDto(t, databaseUtil.initcap(tableName),
                null, attrs, null, true);
        if (re.getResponseCode() == DtoResponse.RESPONSE_CODE_SUCCESS) {
            System.out.println("create ：" + tableName);
            //System.out.println(re.getResponse_data());
        } else {
            System.out.println(re.getE().getMessage());
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://47.93.36.225:3306/qupingfang?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
        String username = "qpf";
        String password = "Qpf@)!*";
        CodeGenMain.build(DatabaseUtil.DB_VERSION_8, url, username, password).setBasePackage("org.openmore.cms").start();
    }
}
