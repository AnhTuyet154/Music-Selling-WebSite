package com.example.WebBanNhac.validator;

import com.example.WebBanNhac.entity.User;
import com.example.WebBanNhac.validator.annotation.ValidUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {

    @Override
    public void initialize(ValidUserId constraintAnnotation) {
        // Initialization logic, if needed
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) {
            return true;
        }
        return user.getId() != null;
    }
}
