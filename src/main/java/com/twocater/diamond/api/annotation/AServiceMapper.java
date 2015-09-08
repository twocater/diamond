package com.twocater.diamond.api.annotation;

import java.lang.annotation.*;

/**
 * Created by chenzhiwei on 15-9-7.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AServiceMapper {

     String[] paths();
     int order() default 0;
     String[] params() default "";

}
