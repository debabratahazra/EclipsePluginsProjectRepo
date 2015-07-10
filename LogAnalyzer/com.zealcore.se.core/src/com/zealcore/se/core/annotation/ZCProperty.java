package com.zealcore.se.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method is a property method. A property can be
 * searchable and plottable, which is specified by setting searchAble/plottAble
 * to true Example:
 * 
 * \@ZCproperty(name="Timestamp", description="The timestamp...", searchAble =
 *                               true, plottAble=true) private long
 *                               getTimestamp() { return ts; }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface ZCProperty {
    String name() default "";

    String description() default "";

    boolean searchable() default false;

    boolean plottable() default false;

    boolean direct() default false;

    boolean ts() default false;
    String format() default "ns";
}
