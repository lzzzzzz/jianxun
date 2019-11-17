package org.openmore.codegen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openmore.common.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by LZ on 2017/7/10.
 */
public class DtoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 一级目录名
     */
    private String basePackage = "org.openmore";

    /**
     * 传入所需元素参数生成（可选）目标文件
     *
     * @param om: 封装参数模型
     */
    public DtoResponse pageCreateByOM(OpenMoreEntity om) {
        if (null == om) {
            return new DtoResponse(new Exception("参数错误"));
        }
        return pageCreateDto(om.getModelName(), om.getClassName(), om.getClassNameZn(),
                om.getAttrs(), om.getController_desc(), om.getFlag_create_file());
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * 传入所需元素参数生成（可选）目标文件
     *
     * @param t:              生成文件类型
     * @param className:      文件名称
     * @param classNameZn:    文件中文描述
     * @param attrs:          所需元素
     * @param controllerDesc: controller描述信息
     * @param flagCreatFile:  是否生成目标文件
     */
    public DtoResponse pageCreateDto(String t, String className, String classNameZn,
                                     String attrs, String controllerDesc, boolean flagCreatFile) {
        //创建模板文件和源码文件目录
        Map<String, String> modelNames = new HashMap<String, String>();
        if (null == t || "".equals(t)) {
            logger.debug("参数错误->create type(t) is invilid");
            return new DtoResponse(new Exception("参数错误->create type(t) is invilid"));
        } else if ("dtoApi".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Dto.java", "dto.api");
        } else if ("dtoCommon".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Dto.java", "dto.common");
        } else if ("controllerApi".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Controller.java", "controller.api");
        } else if ("controllerCommon".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Controller.java", "controller.common");
        } else if ("service".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Service.java", "service");
            modelNames.put("${className}ServiceImpl.java", "service.impl");
        } else if ("test".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}ServiceTest.java", "service.test");
        } else if ("allApi".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Dto.java", "dto.api");
            modelNames.put("${className}Service.java", "service");
            modelNames.put("${className}ServiceImpl.java", "service.impl");
            modelNames.put("${className}ServiceTest.java", "test:service");
            modelNames.put("${className}Controller.java", "controller.api");
        } else if ("allCommon".equals(t)) {
            modelNames.clear();
            modelNames.put("${className}Dto.java", "dto.common");
            modelNames.put("${className}Service.java", "service");
            modelNames.put("${className}ServiceImpl.java", "service.impl");
            modelNames.put("${className}ServiceTest.java", "service.test");
            modelNames.put("${className}Controller.java", "controller.common");
        }

        List<DtoParam> att = null;
        try {
            Gson gson = new Gson();
            att = gson.fromJson(attrs,
                    new TypeToken<List<DtoParam>>() {
                    }.getType());
            if (null != att) {
                for (DtoParam ap : att) {
                    if (null == ap || null == ap.getName() || "".equals(ap.getName())) {
                        att.remove(ap);
                    }
                }
            }
            logger.debug(att == null ? "att is null" : att.size() + "");
        } catch (Exception e) {
            logger.debug("参数错误->无法解析");
            return new DtoResponse(new Exception("参数错误->无法解析"));
        }

        className = CommonUtils.lineToHump(className);

        try {
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("className", className);
            root.put("className_zn", classNameZn);
            //root.put("attrs", attrs);
            root.put("controller_desc", controllerDesc);
            root.put("attrs", att);
            if (flagCreatFile) { //创建文件
                for (Iterator<String> i = modelNames.keySet().iterator(); i.hasNext(); ) {
                    DtoResponse re = null;
                    String modelName = i.next();
                    root.put("basepackage", basePackage);
                    root.put("subpackage", modelNames.get(modelName));
                    String pkgName = modelNames.get(modelName);
                    int pos = pkgName.indexOf(":");
                    if (modelNames.get(modelName).startsWith("test:")) {
                        root.put("mainSource", false);
                        pkgName = pkgName.substring(pos + 1);
                        root.put("subpackage", pkgName);
                    } else {
                        root.put("mainSource", true);
                    }
                    re = OMMakerFactory.freeMaker(modelName, basePackage, pkgName, className, root);
                    if (re.getResponseCode() == DtoResponse.RESPONSE_CODE_ERROR) {
                        return re;
                    }
                }
                return new DtoResponse("文件创建成功");
            } else { //预览源码
                List<DtoResponse> responses = new ArrayList<DtoResponse>();
                for (Iterator<String> i = modelNames.keySet().iterator(); i.hasNext(); ) {
                    DtoResponse re = null;
                    String modelName = i.next();
                    if (modelName.startsWith("test:")) {
                        root.put("mainSource", true);
                    } else {
                        root.put("mainSource", false);
                    }
                    root.put("basepackage", basePackage);
                    root.put("subpackage", modelNames.get(modelName));
                    re = OMMakerFactory.freeMaker(modelName, basePackage, modelNames.get(modelName), className, root);
                    if (re.getResponseCode() == DtoResponse.RESPONSE_CODE_ERROR) {
                        return re;
                    } else {
                        responses.add(re);
                    }
                }
                DtoResponse re3 = new DtoResponse("");
                for (DtoResponse re2 : responses) {
                    re3.setResponseData(re3.getResponseData() + "\n\n" + re2.getResponseData());
                }
                return re3;
            }
        } catch (Exception e) {
            return new DtoResponse(e);
        }
    }
}
