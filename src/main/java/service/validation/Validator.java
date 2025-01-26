package service.validation;

import java.util.List;

public interface Validator {
    List<String> validate(String value) ;
    String getInputName();
}
