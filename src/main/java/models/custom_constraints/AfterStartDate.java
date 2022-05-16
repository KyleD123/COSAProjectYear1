//package models.custom_constraints;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Repeatable;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//import java.util.List;
//
//import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
//import static java.lang.annotation.ElementType.FIELD;
//import static java.lang.annotation.ElementType.METHOD;
//import static java.lang.annotation.ElementType.PARAMETER;
//import static java.lang.annotation.ElementType.TYPE_USE;
//import static java.lang.annotation.RetentionPolicy.RUNTIME;
//
//@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
//@Retention(RUNTIME)
//@Constraint(validatedBy = AfterStartDateValidator.class)
//@Documented
//public @interface AfterStartDate
//{
//    String message() default "{AfterTomorrow.message}";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//
//}
