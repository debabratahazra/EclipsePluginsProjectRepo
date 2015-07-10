package com.zealcore.se.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class is searchable. A searchable class must
 * contain at least one searchable property, which is annotated by the
 * ZSearchableProperty annotation.
 * 
 * @author pasa
 * @version 1.0
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ZSearchable {
    String name() default "";
}
