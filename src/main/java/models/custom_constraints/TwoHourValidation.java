package models.custom_constraints;

import models.Game;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TwoHourValidation implements
        ConstraintValidator<TwoHourGap, Game> {

    @Override
    public void initialize(TwoHourGap constraintAnnotation)
    {

    }

    @Override
    public boolean isValid(Game value, ConstraintValidatorContext context) {
       context.disableDefaultConstraintViolation();

        if (value.getnEndTime() <= value.getnStartTime())
        {
            customMessageForValidation(context, "Past End Time is selected");
            return false;
        }

        else
        {

            if (value.getnEndTime() <= value.getnStartTime() + 200 && value.getnEndTime() >= value.getnStartTime())
            {
                return true;
            }

        }

       customMessageForValidation(context, "The end date is over two hours based on the requested time");
       return false;
    }

    private void customMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        // build new violation message and add it
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}