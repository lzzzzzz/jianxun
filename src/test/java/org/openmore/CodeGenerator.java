package org.openmore;


import org.openmore.codegen.CodeGenMain;
import org.openmore.codegen.DatabaseUtil;

/**
 * Created by michaeltang on 2018/7/23.
 */
public class CodeGenerator {

    public static void main(String[] args){
        //createDao();
        //Md5Test();
        //shiroMd5Test();
//        createPwd();
        test2();
    }
    /**代码生成器*/
    public static void createDao(){
        String url = "jdbc:mysql://qupingfang-db.mysql.zhangbei.rds.aliyuncs.com/qupingfang?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&allowPublicKeyRetrieval=true";
        String username = "qpf";
        String password = "Qpf2019^&*";
        CodeGenMain.build(DatabaseUtil.DB_VERSION_8, url, username, password).
                setBasePackage("org.openmore.cms").start();//根据命令行提示操作
    }

    public static void test1(int a){
        a=5;
        System.out.println("test1中a的值为=="+a);
    }
    public static void test2(){
        int a=3;
        test1(a);
        System.out.println("主函数中a的值为=="+a);
    }
}
