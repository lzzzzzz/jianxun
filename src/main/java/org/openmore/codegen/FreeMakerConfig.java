package org.openmore.codegen;

import java.io.File;

/**
 * Created by LZ on 2017/6/19.
 */
public class FreeMakerConfig {

    /**
     * 默认模块根目录
     */
    public static final String ROOT_PATH = System.getProperty("user.dir")+ File.separator + "template";

    /**
     * 默认配置文件名称
     */
    public static final String GENERATOR_CONFIG_FILE_NAME = "generator.xml";
    /**
     * 默认模板所在根目录
     */
    public static final String MODEL_ROOT_DIRECTORY = "template-model";
    /**
     * 默认默认编码
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * 默认源码默认生成目录
     * generator.xml配置文件中的 outRoot 将覆盖此值
     */
    public static final String DEFAULT_SOURCES_DIRECTORY = "template-sources";

    /**
     * 项目包名
     */
    public static final String DEFAULT_BASEPACKAGE_KEY = "basepackage";
    /**
     * 项目二级包名
     */
    public static final String DEFAULT_SUBPACKAGE_PATH = "subpackage";
    /**
     * 项目主目录相对(project)路径
     */
    public static final String PROJECT_MAIN_JAVA_PATH = File.separator + "src" + File.separator + "main" + File.separator + "java";
    /**
     * 项目Test目录路径
     */
    public static final String PROJECT_MAIN_TEST_PATH = File.separator + "src" + File.separator + "test" + File.separator + "java";


}
