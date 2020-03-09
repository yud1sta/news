package com.bareksa.news.common.annotations;

import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
public @interface EnumPattern {
   String regexp();
   String message() default "must match \"{regexp}\"";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
