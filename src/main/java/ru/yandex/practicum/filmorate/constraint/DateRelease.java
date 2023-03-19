package ru.yandex.practicum.filmorate.constraint;

import ru.yandex.practicum.filmorate.constraint.impl.DataReleaseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataReleaseValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRelease {

    int day() default 1;

    int month() default 1;

    int year() default 1500;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}