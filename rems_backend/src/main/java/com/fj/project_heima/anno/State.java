package com.fj.project_heima.anno;

import com.fj.project_heima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
public @interface State {
    String message() default "{只能是已发布或草稿}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
