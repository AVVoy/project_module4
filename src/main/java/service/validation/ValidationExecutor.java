package service.validation;

import lombok.RequiredArgsConstructor;
import servlet.auth.helper.dto.Credential;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ValidationExecutor {

    private final Map<String, Validator> validators;

    public Map<String, List<String>> executeValidation(Credential credential) {

        String login = credential.getLogin();
        String password = credential.getPassword();

        Map<String, String> mapValue = Map.ofEntries(
                Map.entry("login", login),
                Map.entry("password", password)
        );

        return validators.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                stringValidatorEntry -> stringValidatorEntry.getValue()
                                        .validate(
                                                mapValue.get(stringValidatorEntry.getValue().getInputName())
                                        )
                        )
                );
    }
}
