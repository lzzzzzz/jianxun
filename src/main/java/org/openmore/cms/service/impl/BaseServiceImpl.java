package org.openmore.cms.service.impl;

import org.openmore.cms.entity.User;
import org.openmore.common.exception.ExceptionPrint;
import org.openmore.common.utils.CommonUtils;
import org.openmore.common.utils.Utils;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.dto.api.OperateLogDto;
import org.openmore.cms.service.OperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by michaeltang on 2018/8/10.
 */
public class BaseServiceImpl {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExceptionPrint exceptionPrint;

    /**
     * 将字段首字母大写
     *
     * @param filedName
     * @return
     */
    public String getMethodName(String filedName) {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 获得所有的父类和子类的属性
     * @param tempClass
     * @return
     */
    private List<Field> getAllField(Class tempClass){
        List<Field> fieldList = new ArrayList<>() ;
        while (tempClass != null) {
            //当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }
    /**
     * 在执行插入前，执行本方法
     * > 用来生成 uuid
     * > 初始化null字段
     * > 生成创建时间和删除状态及版本号
     *
     * @param entity
     */
    public void beforeInsert(Object entity) {
    }

    /**
     * 设置基本类型的默认值信息
     * @param entity
     * @param field
     */
    private void setBasicTypeDefaultValue(Object entity, Field field){
        try {
            // String为空的，设置成""，因为Mysql里varchar默认值NOT NULL,不能插入空值
            if (Utils.isType(field, "string")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), String.class);
                    m.invoke(entity, "");
                }
            }

            // Boolean为空的，设置成false
            if (Utils.isType(field, "boolean")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), Boolean.class);
                    m.invoke(entity, false);
                }
            }

            // Integer为空的，设置成0
            if (Utils.isType(field, "int")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), Integer.class);
                    m.invoke(entity, 0);
                }
            }

            // Long为空的，设置成0
            if (Utils.isType(field, "long")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), Long.class);
                    m.invoke(entity, new Long("0"));
                }
            }
            // Float为空的，设置成0
            if (Utils.isType(field, "float")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), Float.class);
                    m.invoke(entity, 0.0f);
                }
            }
            // Double为空的，设置成0
            if (Utils.isType(field, "double")) {
                field.setAccessible(true);
                Method getMethod = entity.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                if (getMethod.invoke(entity) == null) {
                    Method m = entity.getClass().getMethod(
                            "set" + getMethodName(field.getName()), Double.class);
                    m.invoke(entity, 0.0);
                }
            }
        } catch (Exception e) {
            exceptionPrint.print(this.getClass(), e);
        }
    }

    /**
     * 在执行插入前，执行本方法
     * > 用来生成 uuid
     * > 初始化null字段
     * > 生成创建时间和删除状态及版本号
     * @param needNull :字符属性是否需要插入null值，否则插入empty
     *
     * @param entity
     */
    public void beforeInsert(Object entity, boolean needNull) {

    }

    @Autowired
    private OperateLogService operateLogService;


    /**
     * 插入log
     *
     * @param remark
     */
    public void insertLog(String remark) {
        try {
            OperateLogDto log = new OperateLogDto();
            log.setContent(remark);
            log.setIpAddress(ThreadLocalConfig.getIPAddr());
            User user = ThreadLocalConfig.getUser();
            if (user != null) {
                //log.setOperator(user.getNickname());
                log.setStaffId(user.getId());
            } else {
                log.setStaffId("");
            }
            beforeInsert(log);
            operateLogService.insert(log);
        } catch (Exception e) {
            logger.debug("日志记录出错[" + remark + "]");
            exceptionPrint.print(this.getClass(), e);
        }
    }

  /*  public Staff getStaff(){
        Object o = SecurityUtils.getSubject().getPrincipal();
        if(o instanceof Staff){
            return (Staff) o;
        }else{
            throw new OpenmoreException(4001,"no permission");
        }
    }
    public User getUser(){
        Object o = SecurityUtils.getSubject().getPrincipal();
        if(o instanceof User){
            return (User) o;
        }else{
            throw new OpenmoreException(4001,"no permission");
        }
    }*/

}
