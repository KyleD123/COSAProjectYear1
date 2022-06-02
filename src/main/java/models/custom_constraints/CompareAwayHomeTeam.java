package models.custom_constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompareAwayHomeTeamValidation.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareAwayHomeTeam
{
    String message() default "Home and Away Teams selected is similar!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}