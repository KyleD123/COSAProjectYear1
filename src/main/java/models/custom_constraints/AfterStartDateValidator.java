package models.custom_constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;


//public class AfterStartDateValidator implements ConstraintValidator<AfterStartDate, Date>
//{
//    public final void initialize(final AfterStartDate annotation) {}
//
////    @Override
////    public boolean isValid(Date value, ConstraintValidatorContext constraintValidatorContext)
////    {
////        Calendar calendar = Calendar.getInstance();
////        calendar.setTime(value);
////        calendar.add(Calendar.DATE, 1);
////
////        return null;
////    }
//}
