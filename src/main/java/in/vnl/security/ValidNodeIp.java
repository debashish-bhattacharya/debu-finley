package in.vnl.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.stereotype.Component;



@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NodeIpValidator.class)
public @interface ValidNodeIp 
{
    String message() default "{\"RESULT\":\"Unauthorized access\"}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
