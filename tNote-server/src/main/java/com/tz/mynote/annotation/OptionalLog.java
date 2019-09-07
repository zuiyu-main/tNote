package com.tz.mynote.annotation;

import java.lang.annotation.*;

/**
 * @author cxt
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalLog {
    String module()  default "";
    String methods()  default "";
}
