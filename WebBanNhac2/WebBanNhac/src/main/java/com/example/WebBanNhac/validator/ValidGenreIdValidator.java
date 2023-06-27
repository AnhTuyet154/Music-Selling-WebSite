package com.example.WebBanNhac.validator;

import com.example.WebBanNhac.entity.Genre;
import com.example.WebBanNhac.validator.annotation.ValidGenreId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidGenreIdValidator implements ConstraintValidator<ValidGenreId, Genre> {

    @Override
    public boolean isValid(Genre genre, ConstraintValidatorContext context) {
        return genre != null && genre.getId() != null;
    }
}