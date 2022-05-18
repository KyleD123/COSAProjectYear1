package models;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Set;

public class TeamValidator
{
    private ValidatorFactory validFac;
    private Validator validator;

    public TeamValidator()
    {
        validFac = Validation.buildDefaultValidatorFactory();
        validator = validFac.getValidator();
    }

    public boolean isTeamValid(Team obj)
    {
        return validator.validate(obj).isEmpty();
    }

    public HashMap<String, String> getErrors(Object obj)
    {
        HashMap<String, String> errors = new HashMap<String, String>();
        Set<ConstraintViolation<Object>> violations =  validator.validate(obj);

        if(!violations.isEmpty()) {
            for (ConstraintViolation<Object> cv : violations) {
                errors.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }
        return errors;
    }

}
