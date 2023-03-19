package ru.yandex.practicum.filmorate.constraint.impl;

import ru.yandex.practicum.filmorate.constraint.DateRelease;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DataReleaseValidator implements ConstraintValidator<DateRelease, LocalDate> {

    private int day;

    private int month;

    private int year;

    @Override
    public void initialize(DateRelease constraintAnnotation) {
        this.day = constraintAnnotation.day();
        this.month = constraintAnnotation.month();
        this.year = constraintAnnotation.year();
    }

    @Override
    public boolean isValid(LocalDate valueDate, ConstraintValidatorContext constraintValidatorContext) {
        return valueDate.isAfter(LocalDate.of(year, month, day));
    }
}
