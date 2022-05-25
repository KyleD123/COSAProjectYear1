package models;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Set;

public class GameValidator
{
    private ValidatorFactory validFac;
    private Validator validator;

    public GameValidator()
    {
        validFac = Validation.buildDefaultValidatorFactory();
        validator = validFac.getValidator();
    }

    /**
     * This method determine if the tournament object is valid based on the given rules as stated on the annotations.
     *
     * If the validator returns a list of nothing, it is valid.
     * @param obj
     * @return
     */
    public boolean isGameValid(Game obj)
    {
        return validator.validate(obj).isEmpty();
    }

    /**
     * This method creates a hash map of errors and their values from the validator
     * @param obj
     * @return
     */
    public HashMap<String, String> getErrors (Game obj)
    {
        HashMap<String,String> errors = new HashMap<String,String>();
        Set<ConstraintViolation<Object>> violations =  validator.validate( obj);

        if (!violations.isEmpty())
        {
            for (ConstraintViolation<Object> cv : violations)
            {
                errors.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }

        return errors;
    }
}
