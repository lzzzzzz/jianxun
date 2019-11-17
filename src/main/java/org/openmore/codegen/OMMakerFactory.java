package org.openmore.codegen;

import java.io.File;
import java.util.Map;

/**
 * Created by LZ on 2017/6/30.
 * openmore定制化代码生成器
 */
public class OMMakerFactory{


    /**指定模板名和目标文件名生成源码文件
     * @param modelFileName: 模板文件名称
     * @param basePackage: 生成文件一级目录
     * @param subPackage: 生成文件二级目录
     * @param fileName: 生成文件名称
     * @param root: 所需元素
     * */
    public static DtoResponse freeMaker(String modelFileName, String basePackage, String subPackage, String fileName , Map<String, Object> root){

        try {
            if (null == fileName) {
                throw new RuntimeException("指定输出文件不合法");
            }
            //获取输出目录
            String outFileDir = "";
            if (null == basePackage || "".equals(basePackage)){
                String sourcePath = DtoFileUtils.getGeneratorConfigFileName(FreeMakerConfig.GENERATOR_CONFIG_FILE_NAME);
                outFileDir = PropertiesUtils.getPropertyValueByKey(sourcePath, "outRoot");
                if (null == sourcePath || "".equals(sourcePath)) {
                    //自定义目录不存在则使用默认目录
                    outFileDir = FreeMakerConfig.ROOT_PATH + File.separator + FreeMakerConfig.DEFAULT_SOURCES_DIRECTORY;
                } else {
                    outFileDir = FreeMakerConfig.ROOT_PATH + File.separator + outFileDir;
                    File sourceDir = new File(outFileDir);
                    if (!sourceDir.exists()) {
                        sourceDir.mkdirs();
                    }
                }
                System.out.println("freemaker未指定生成文件根目录使用默认输出目录：" + outFileDir);
            } else {
                ProjectPathHelper helper = new ProjectPathHelper();
                boolean mainSource = root.containsKey("mainSource")? (Boolean) root.get("mainSource") : true;
                outFileDir = helper.getBasePackagePath(mainSource, basePackage);
                String subStr = ProjectPathHelper.splidFileName(subPackage);
                if (null != subStr && !"".equals(subStr)){
                    outFileDir = outFileDir + File.separator + subStr;
                }
            }
            if (null != modelFileName && !"".equals(modelFileName) && modelFileName.contains("${className}")){
                fileName = modelFileName.replace("${className}", fileName);
            }
            //获取输出文件
            File outFile = new File(outFileDir + File.separator + fileName);
            return FreeMakerFactory.getInstance().freeMaker(modelFileName, outFile, root);
        } catch (Exception e){
            return new DtoResponse(e);
        }
    }
    /**指定模板名和生成文件名
     * @param modelFileName: 模板文件名称
     * @param filePath: 生成文件路径
     * @param root: 所需元素
     * */
    public static DtoResponse freeMaker(String modelFileName, String filePath, Map<String, Object> root ){

        try {
            if (null == filePath) {
                throw new RuntimeException("指定输出文件不合法");
            }
            return FreeMakerFactory.getInstance().freeMaker(modelFileName, filePath, root);
        } catch (Exception e){
            return new DtoResponse(e);
        }
    }

    /**指定模板名生成所需源码
     * @param modelFileName: 模板文件
     * @param root: 所需元素
     * */
    public static DtoResponse freeMaker(String modelFileName, Map<String, Object> root ) {
        try {
            return FreeMakerFactory.getInstance().freeMaker(modelFileName, root);
        } catch (Exception e){
            return new DtoResponse(e);
        }
    }

}
