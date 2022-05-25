package models.custom_constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TwoHourValidation.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TwoHourGap {
    String message() default "The end time is over two hours based on the requested start time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}