//package com.example.library.validators;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//import static java.lang.annotation.ElementType.*;
//import static java.lang.annotation.ElementType.TYPE;
//import static java.lang.annotation.RetentionPolicy.RUNTIME;
//
//@Target({TYPE, FIELD, ANNOTATION_TYPE})
//@Retention(RUNTIME)
//
////jak czytac wszystkie te linie?
//
//
//@Constraint(validatedBy = EmailValidator.class)
//@Documented
//
////czemy @ w nazwie
//public @interface ValidEmail {
//    String message() default "Invalid email";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}