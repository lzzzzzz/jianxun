package org.openmore.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.openmore.cms.entity.enums.BaseEnum;
import org.openmore.common.annotation.ReferencedField;
import org.openmore.common.annotation.ReferencedFieldEnabled;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.cms.dao.SupperMapper;
import org.openmore.cms.entity.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by michaeltang on 2018/8/22.
 * 关联字段处理器
 */

@Component
public class ReferencedFieldProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ThreadLocal<Boolean> enableProcessor = new ThreadLocal<>();
    private static ThreadLocal<Map<String, LinkedHashMap<String, Object>>> cachedProcessEntity = new ThreadLocal<>();
    private static ThreadLocal<Map<String, List<LinkedHashMap<String, Object>>>> cachedProcessEntityList = new ThreadLocal<>();

    @Autowired
    private SupperMapper supperMapper;

    //  全局词典缓存，用于缓存Dictionary, District，Staff
    private Map<String, LinkedHashMap<String, Object>> globalCachedEntity = new HashMap<>();

    /**
     * 是否使用本次处理
     * @return
     */
    public static Boolean isProcessorEnable() {
        if(enableProcessor.get() == null) {
            return true;
        }
        return enableProcessor.get();
    }

    /**
     * 一定要在每次线程请求开始时调用清除
     * 或在明确不再使用缓存时调用，以防止线程池被复用时导致数据污染
     */
    public static void clearThreadLocalCache(){
        getCachedProcessEntity().clear();
        getCachedProcessEntityList().clear();
        enableProcessor();
    }

    /**
     * 设置使能处理
     * @return
     */
    public static void disableProcessor() {
        enableProcessor.set(false);
    }

    /**
     * 设置使能处理
     * @return
     */
    public static void enableProcessor() {
        enableProcessor.set(true);
    }

    /**
     * 获得本次请求的缓存对象处理
     * @return
     */
    private static Map<String, LinkedHashMap<String, Object>> getCachedProcessEntity() {
        if(cachedProcessEntity.get() == null) {
            Map<String, LinkedHashMap<String, Object>> cachedEntity = new HashMap<>();
            cachedProcessEntity.set(cachedEntity);
        }
        return cachedProcessEntity.get();
    }

    /**
     * 设置本次请求的缓存对象处理
     * @return
     */
    private static void setCachedProcessEntity(Map<String, LinkedHashMap<String, Object>> entity) {
        cachedProcessEntity.set(entity);
    }

    /**
     * 获得本次请求的缓存对象处理
     * @return
     */
    private static Map<String, List<LinkedHashMap<String, Object>>> getCachedProcessEntityList() {
        if(cachedProcessEntityList.get() == null) {
            Map<String, List<LinkedHashMap<String, Object>>> cachedEntityList = new HashMap<>();
            cachedProcessEntityList.set(cachedEntityList);
        }
        return cachedProcessEntityList.get();
    }

    /**
     * 设置本次请求的缓存对象处理
     * @return
     */
    private static void setCachedProcessEntityList(Map<String, List<LinkedHashMap<String, Object>>> entityList) {
        cachedProcessEntityList.set(entityList);
    }


    /**
     * 清除缓存
     * @param type
     */
    public void flushCache(Class<?> type) {
        Iterator it = globalCachedEntity.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey().toString().startsWith(type.getSimpleName())) {
                it.remove();
            }
        }
    }

    /**
     * 是否需要进行全局缓存
     * @param type
     * @return
     */
    public boolean needGlobalCache(Class<?> type) {
//        TODO:: 需要缓存的class
        if (type == Dictionary.class) {
//                || type == User.class) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 处理List类型中所有的ReferencedField字段
     * @param data
     */
    public void process(List data) {
        if (data.isEmpty())
            return;
        if(!isProcessorEnable()) {
            return;
        }
        if(CollectionUtils.isEmpty(data)){
            return;
        }
//      取得List中的处理对象类型
        Object obj = data.get(0);
        if(null == obj){
            return;
        }
        Class<?> type = obj.getClass();
//      获得对象中所有的字段
        List<Field> fields = collect(type);
        BeanWrapperImpl br = new BeanWrapperImpl();
        this.processField(type, fields, br, data);
    }

    /**
     * 返回将指定类型的对象中所有的字段
     * @param type
     * @return
     */
    private List<Field> collect(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> t = type;
        while (true) {
            fields.addAll(Arrays.asList(t.getDeclaredFields()));
            t = t.getSuperclass();
//          如果已经到达继承体系最顶端，返回，否则找到所有的子类里的字段
            if (t == Object.class)
                return fields;
        }
    }

    /**
     * 处理指定的对象里的ReferencedField字段
     * @param entity
     */
    public void processOne(Object entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof List) {
            process((List) entity);
        } else {
            List list = new ArrayList();
            list.add(entity);
            process(list);
        }
    }

    /**
     * 将record 里的字段Map数据按照type里的字段读取到新对象里返回
     * 由于表结构和实体对象字段的默认转换规则，读取时会自动将属性名切换成AbcDef->abc_def
     * @param type
     * @param record
     * @return
     */
    private Object writeMapToObject(Class<?> type, Map<String, Object> record) {
        try{
            List<Field> fields = collect(type);
            if(record != null) {
                // 创建目标类对象
                Object targetObj = type.newInstance();
                // 获得对象中所有的字段
                for (Field field : fields) {
                    PropertyDescriptor refField = BeanUtils.getPropertyDescriptor(
                            type, field.getName());
                    String tableField = CommonUtils.humpToUnderline(field.getName());
                    // 处理枚举类型
                    if(field.isEnumConstant() || BaseEnum.class.isAssignableFrom(field.getType())){
                        String className = field.getGenericType().toString().replace("class ", "");
                        Class fileClass = Class.forName(className);
                        Object value = record.get(tableField);
                        refField.getWriteMethod().invoke(targetObj, Enum.valueOf(fileClass, value.toString()));
                        // 处理布尔类型
                    } else if(field.getGenericType().toString().equals(
                            "class java.lang.Boolean") || field.getGenericType().toString().equals(
                            "boolean")) {
                        Object value = record.get(tableField);
                        if(value instanceof Boolean) {
                            refField.getWriteMethod().invoke(targetObj, value);
                        } else if(value == null || value.equals("0") || "false".equals(value)) {
                            // 将读取的结果写入到目标对象里
                            refField.getWriteMethod().invoke(targetObj, false);
                        } else {
                            refField.getWriteMethod().invoke(targetObj, true);
                        }
                    } else if(Number.class.isAssignableFrom(field.getType())) {
                        Object value = record.get(tableField);
                        if(value == null) {
                            refField.getWriteMethod().invoke(targetObj, 0);
                        } else {
                            Number num = (Number) value;
                            if(Utils.isType(field, "int")) {
                                refField.getWriteMethod().invoke(targetObj, num.intValue());
                            } else if(Utils.isType(field, "long")) {
                                refField.getWriteMethod().invoke(targetObj, num.longValue());
                            } else if(Utils.isType(field, "short")) {
                                refField.getWriteMethod().invoke(targetObj, num.shortValue());
                            } else if(Utils.isType(field, "byte")) {
                                refField.getWriteMethod().invoke(targetObj, num.byteValue());
                            } else if(Utils.isType(field, "double")) {
                                refField.getWriteMethod().invoke(targetObj, num.doubleValue());
                            } else if(Utils.isType(field, "float")) {
                                refField.getWriteMethod().invoke(targetObj, num.floatValue());
                            } else if(Utils.isType(field, "bigDecimal")) {
                                if(field.getType().isAssignableFrom(BigDecimal.class)) {
                                    refField.getWriteMethod().invoke(targetObj, value);
                                } else {
                                    throw new OpenmoreException(400, "BigDecimal 不支持！");
                                }
                            } else if(Utils.isType(field, "bigInt")) {
                                if(field.getType().isAssignableFrom(BigInteger.class)) {
                                    refField.getWriteMethod().invoke(targetObj, value);
                                } else {
                                    throw new OpenmoreException(400, "BigInteger 不支持！");
                                }
                            }
                        }
                    } else {
                        // 将读取的结果写入到目标对象里
                        Object obj = record.get(tableField);
                        refField.getWriteMethod().invoke(targetObj, obj);
                    }
                }
                return targetObj;
            }
        }catch (Exception e){
            logger.error(">> writeMapToObject:" + e.getMessage());
        }
        return null;
    }

    /**
     * 处理字段
     * @param type 数据类型
     * @param typeField 标注的关联字段名
     * @param data 数据列表
     */
    private void processField(Class<?> type, Field typeField, List<?> data) {
//      获得注解类
        ReferencedField annotation = typeField.getAnnotation(ReferencedField.class);
        String localField = annotation.localField();
        Class<?> targetType = annotation.targetClass();
        if (StringUtils.isEmpty(localField)){
//          当关联字段为空，表示和被关联字段名相同
            localField = annotation.conditionalField();
        }
        if(annotation.isOne2Many() && !typeField.getType().getSimpleName().equals("List")) {
            throw new OpenmoreException(400, "1对多关系配置不正确：" + typeField.getName() + "应该为List类型");
        }

        // 如果未指定targetClass则说明和refClass相同
        if(targetType.getSimpleName().equals("String")) {
            targetType = annotation.refClass();
        }

        // 当选择字段为空，表示选择整个对象赋值
        boolean isSelectObject = false;
        if (StringUtils.isEmpty(annotation.selectField())) {
            isSelectObject = true;
        }
//      获得缓存里没有的关联字段，需要通过查询语句查出来
        List<String> localValuesNotInCache = getLocalValuesNotCached(type, localField, annotation, data);
        if (!localValuesNotInCache.isEmpty()) {
            //      将所有被关联的字段数据缓存起来
            queryAndCacheReferenceField(annotation, localValuesNotInCache);
        }
        for (Object d : data) {
            try {
                // 获得关联字段名
                PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(type, annotation.localField());
                // 获得本地关联字段的值
                String localValue = (String) pd.getReadMethod().invoke(d);
                if(StringUtils.isEmpty(localValue)){
                    continue;
                }
                // 获得标注字段属性描述
                PropertyDescriptor referencedField = BeanUtils.getPropertyDescriptor(
                        type, typeField.getName());
                if (referencedField == null) {
                    throw new OpenmoreException(type.getName() + "类中" + typeField.getName() + "字段未添加getter和setter方法");
                }
                // key = 目标类名_关联字段名_关联字段值
                String key = null;
                if(annotation.isOne2Many()) {
                    key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_one2many_" + localValue;
                    // 没有值，返回
                    if (!getCachedProcessEntityList().containsKey(key))
                        continue;
                    List<Object> resultList = new ArrayList<>();
                    for (LinkedHashMap<String, Object> record : getCachedProcessEntityList().get(key)) {
                        resultList.add(writeMapToObject(targetType, record));
                    }
                    // 再次处理Dto对象
                    if(targetType.getSimpleName().contains("Dto")) {
                        process(resultList);
                    }
                    referencedField.getWriteMethod().invoke(d, resultList);
                } else {
                    key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_" + localValue;
                    Map<String, Object> map = null;
                    if (needGlobalCache(annotation.refClass())) {
                        map = globalCachedEntity.get(key);
                    } else {
                        map = getCachedProcessEntity().get(key);
                    }
                    // 没有值，返回
                    if (map == null)
                        continue;
                    // 取出缓存里的数据，并赋值
                    if(isSelectObject) {
                        // 读取获得的数据的值到目标类对象
                        Object targetObj = writeMapToObject(targetType, map);
                        // 再次处理Dto对象
                        if(targetType.getSimpleName().contains("Dto")) {
                            processOne(targetObj);
                        }
                        referencedField.getWriteMethod().invoke(d, targetObj);
                    } else {
                        referencedField.getWriteMethod().invoke(d, map.get(annotation.selectField()));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(">> processField:" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 返回缓存中没有的关联字段
     * @param type
     * @param localField
     * @param annotation
     * @param dataList
     * @return
     */
    private List<String> getLocalValuesNotCached(Class<?> type, String localField, ReferencedField annotation, List<?> dataList) {
        List<String> localValues = new ArrayList<>();
        try {
//          获得关联表里的条件字段描述
            PropertyDescriptor pds = BeanUtils.getPropertyDescriptor(type, localField);
            if (pds == null)
                throw new OpenmoreException("关联字段配置错误");
            for (Object data : dataList) {
//              获得条件字段返回的值
                Object localValue = pds.getReadMethod().invoke(data);
//              为空直接返回
                if (localValue == null)
                    continue;
                if (!(localValue instanceof String))
                    throw new RuntimeException("请检查" + type.getName() + "上的@ReferencedField注解");
                String id = (String) localValue;
                if (StringUtils.isEmpty(id))
                    continue;
                // key = 目标类名_关联字段名_关联字段值
                String key = null;
                if(annotation.isOne2Many()) {
                    key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_one2many_" + id;
                } else {
                    key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_" + id;
                }
//              如果是词典类，需要全局缓存
                if (needGlobalCache(annotation.refClass())) {
                    // 缓存里有关联字段对应的数据
                    if (globalCachedEntity.containsKey(key)) {
                        logger.debug(key + "->全局缓存命中，线程：" + Thread.currentThread().getName());
                        continue;
                    }
                } else {
//                  如果是非词典类，进行请求缓存
                    // 缓存里有
                    if (getCachedProcessEntity().containsKey(key)) {
                        logger.debug(key + "->线程缓存命中，线程：" + Thread.currentThread().getName());
                        continue;
                    }
                }
                logger.debug(" >> 缓存未命中，需要读取db：" + key + "，线程：" + Thread.currentThread().getName());
//              缓存里没有
                localValues.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">> getLocalValuesNotCached:" + e.getMessage());
            throw new RuntimeException(e);
        }
        return localValues;
    }

    /**
     * 通过Sql语句查出关系数据，并缓存起来
     * 原理：根据将localValues对应的selectField字段值找出来
     * @param annotation
     * @param localValues
     * @return
     */
    public void queryAndCacheReferenceField(ReferencedField annotation, List<String> localValues) {
        // 类名转成表名，驼峰转小写加下划线
        String underLineName = CommonUtils.humpToUnderline(annotation.refClass().getSimpleName());
        // 1对多处理
        if(annotation.isOne2Many()) {
            String orderByClause = annotation.orderByClause();
            if(!StringUtils.isEmpty(orderByClause)) {
                orderByClause = " order by " + orderByClause;
            }
            for (String localVal: localValues) {
                String hql = "select * from " + underLineName
                        + " where " + annotation.conditionalField() + " = '" + localVal + "'" + orderByClause;
                try {
                    List<LinkedHashMap<String, Object>> entityList = supperMapper.superSelect(hql);
                    if(entityList.size() > 0) {
                        // key = 目标类名_关联字段名_one2many_关联字段值
                        String key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_one2many_" + localVal;
                        getCachedProcessEntityList().put(key, entityList);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error(">> queryAndCacheReferenceField:" + ex.getMessage());
                }
            }
        } else {
            String inLocalField = joinWithToken(localValues, "'");
            String hql = "select * from " + underLineName
                    + " where " + annotation.conditionalField() + " in(" + inLocalField + ")";
            try {
                List<LinkedHashMap<String, Object>> entityList = supperMapper.superSelect(hql);
                for (LinkedHashMap<String, Object> record : entityList) {
                    // key = 目标类名_关联字段名_关联字段值
                    String key = annotation.refClass().getSimpleName() + "_" + annotation.conditionalField() + "_" + record.get(annotation.conditionalField()).toString();
                    if (needGlobalCache(annotation.refClass())) {
                        // 词典放到全局缓存里
                        globalCachedEntity.put(key, record);
                    } else {
                        // 其它关联类，放到请求缓存里
                        getCachedProcessEntity().put(key, record);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(">> queryAndCacheReferenceField:" + ex.getMessage());
            }
        }
    }

    private void processField(Class<?> type, List<Field> typeFieldList, BeanWrapperImpl br, List data){
        for (Field f : typeFieldList) {
//          如果有此注解
            if (f.isAnnotationPresent(ReferencedFieldEnabled.class)) {
                List cdata = new ArrayList();
                for (Object o : data) {
                    br.setWrappedInstance(o);
                    Object c = br.getPropertyValue(f.getName());
                    if (c != null)
                        cdata.add(c);
                }
                process(cdata);
            }
//          没有注解，跳过不处理
            if (!f.isAnnotationPresent(ReferencedField.class))
                continue;

//          需要处理
            processField(type, f, data);
        }
    }

    public String joinWithToken(Collection<String> ss, String token){
        List<String> idss = new ArrayList<String>();
        for (String s : ss){
            idss.add(token + s + token);
        }
        return StringUtils.join(idss, ',');
    }

    public String joinWithToken(String[] ss, String token){
        List<String> idss = new ArrayList<String>();
        for (String s : ss){
            idss.add(token + s + token);
        }
        return StringUtils.join(idss, ',');
    }
}