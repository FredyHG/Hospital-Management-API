package dev.fredyhg.com.hospitalquerysystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CpfValidation implements ConstraintValidator<ValidCPF, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        Pattern pattern = Pattern.compile("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();

    }
}
