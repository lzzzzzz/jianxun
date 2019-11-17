package org.openmore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by michaeltang on 2018/8/22.
 * 当返回的结果中需要出现关联字段和被关联表字段时，使用该注解
 * 关联字段上添加@ReferencedField，表示这个字段是来自被关联表中的字段
 * 通过conditionalField指定注解字段对应的被关联表条件字段名
 * 通过selectField指定取值的被关联表中字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ReferencedField {

    /**
     * 关联表名
     * @return
     */
    Class<?> refClass();

    /**
     * 1对象多和关联返回对象时使用
     * 如果未指定该值，则使用和refClass一样的值
     * 用途：查询User对应的Order列表，返回OrderDto，可指定refClass=Order, targetClass=OrderDto
     * 返回的Dto对象会被再次处理
     * @return
     */
    Class<?> targetClass() default String.class;

    /**
     * 是否为1对多的关系
     * @return
     */
    boolean isOne2Many() default false;

    /**
     * 排序语句
     * @return
     */
    String orderByClause() default "";
    /**
     * 当前注释类的本地字段，如果为空，表示和条件字段名相同
     * @return
     */
    String localField() default "";

    /**
     * 关联表里参与查询的条件字段名
     * @return
     */
    String conditionalField();

    /**
     * 赋值给注解字段的关联表字段
     * @return
     */
    String selectField() default "";
}
