package bookstore.domain;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class InputValidator implements ConstraintValidator<ValidInput, String> {

    /**
     * Validates for special chars
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("/^[a-z0-9]+$/gi");
        Matcher matcher = pattern.matcher(value);
        try {
            if (!matcher.matches()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.info("Matcher exception {} {}", e.getCause(), e.getMessage());
            return false;
        }
    }
}
