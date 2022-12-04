package dev.dracarys.com.hospitalquerysystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidation implements ConstraintValidator<ValidPhone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^(\\([0-9]{2}\\))([0-9]{4,5})([0-9]{4})");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
