package service.validation.impl;

import entity.User;
import service.UserService;
import service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class LoginValidator implements Validator {


    private final UserService userService;

    public LoginValidator(UserService userService) {
        this.userService = userService;
    }

    public List<String> validate(String login) {
        List<String> errorMessages = new ArrayList<>();
        boolean isExist = userService.getAllUsers().stream().map(User::getLogin).anyMatch(s -> s.equals(login));
        if (isExist) {
            errorMessages.add("This login already is occupied");
        }

        if (login.length() < 5) {
            errorMessages.add("This login is too short");
        }

        if (login.contains(" ")) {
            errorMessages.add("Login must not contains whitespace");
        }
        return errorMessages;
    }

    @Override
    public String getInputName() {
        return "login";
    }
}
