package dev.fredyhg.com.hospitalquerysystem.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfValidation.class)
public @interface ValidCPF {

    String message() default "CPF is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };




}
