package org.openmore.codegen;

import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by LZ on 2017/6/29.
 */
public class ProjectPathHelper {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取项目包路径
     */
    public String getBasePackagePath(boolean mainSource, String basePathStr){
        try{
            String projectPath = System.getProperty("user.dir");
            if (null == basePathStr || "".equals(basePathStr)){
                return null;
            } else{
                String subString = splidFileName(basePathStr);
                if (null == subString || "".equals(subString)){
                    logger.debug(">>加载自定义输出目录basepackage：(" + basePathStr + ")出错，使用默认路径输出");
                    return null;
                }
                return mainSource ? projectPath + FreeMakerConfig.PROJECT_MAIN_JAVA_PATH + File.separator + subString :
                        projectPath + FreeMakerConfig.PROJECT_MAIN_TEST_PATH + File.separator + subString;
            }
        } catch (Exception e){
            logger.debug(">>加载自定义输出目录basepackage：(" + basePathStr + ")出错，使用默认路径输出");
            return null;
        }
    }

    /**
     * 字符串替换
     */
    public static String splidFileName(String filenameStr){
        if (null != filenameStr && !"".equals(filenameStr)){
            String pathStr = filenameStr.replace(".", File.separator);
            return pathStr;
        } else{
            return null;
        }
    }
}
