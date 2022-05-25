package models.custom_constraints;

import models.Game;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompareAwayHomeTeamValidation implements
        ConstraintValidator<CompareAwayHomeTeam, Game> {

    @Override
    public void initialize(CompareAwayHomeTeam constraintAnnotation)
    {

    }

    @Override
    public boolean isValid(Game value, ConstraintValidatorContext context) {
        if (value.gettAwayTeam().getTeamID() == value.gettHomeTeam().getTeamID())
        {
            return false;
        }

        return true;
    }
}