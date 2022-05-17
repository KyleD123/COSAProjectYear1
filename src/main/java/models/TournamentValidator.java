package models;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class TournamentValidator {

        private ValidatorFactory validFac;
        private Validator validator;

        public TournamentValidator()
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
        public boolean isTournValid(Tournament obj)
        {
            return validator.validate(obj).isEmpty();
        }

}
