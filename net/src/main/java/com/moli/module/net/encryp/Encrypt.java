package com.moli.module.net.encryp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zixiaojun
 * @date 2018/4/21
 * @copyright Moli Teams
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {

    boolean required() default true;
}
