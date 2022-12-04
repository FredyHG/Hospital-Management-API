package dev.dracarys.com.hospitalquerysystem.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidation.class)
public @interface ValidPhone {

    String message() default "Phone number is invalid check parameters and try again";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
