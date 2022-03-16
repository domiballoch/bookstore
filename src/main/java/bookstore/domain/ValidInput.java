package bookstore.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = InputValidator.class)
@Documented
public @interface ValidInput {

    String message() default "{Input invalid. Special chars not allowed}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
