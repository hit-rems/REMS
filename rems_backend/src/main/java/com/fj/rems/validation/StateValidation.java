package com.fj.rems.validation;

import com.fj.rems.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s==null){
            return false;
        }
        return s.equals("已发布") || s.equals("草稿");
    }
}
