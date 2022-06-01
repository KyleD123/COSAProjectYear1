package models;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Set;


public class PlayerValidator {
    private ValidatorFactory vf;
    private Validator validator;

    /***
     * initailize the external validation objects
     */
    public PlayerValidator() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

     /***
     *
     * @param obj - object to validate
     * @return true if nr violations exist and false if any violations exist
     */
    public boolean isValid(Object obj){
        return validator.validate( obj ).isEmpty();
    }

    /***
     * Validate object and return a hash map of key (property name) value (error message) pairs
     * @param obj - object to validate
     * @return HashMap of property name (key), and error message (value)
     */
    public HashMap<String,String> getErrors(Object obj){

        HashMap<String,String> errors = new HashMap<String,String>();
        Set<ConstraintViolation<Object>> violations =  validator.validate( obj );

        if(!violations.isEmpty()) {
            for (ConstraintViolation<Object> cv : violations) {
                errors.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }
        return errors;
    }



}
