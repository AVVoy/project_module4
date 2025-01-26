package service;

import dao.UserDAO;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import servlet.auth.helper.dto.Credential;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    public void saveUser(Credential credential) {
        String hashPassword = getHashPassword(credential.getPassword());
        userDAO.save(User.builder()
                .id(UUID.randomUUID())
                .login(credential.getLogin())
                .password(hashPassword)
                .build()
        );
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> getRegisteredUser(Credential credential) {
        List<User> users = getAllUsers();
        Optional<User> registeredUser = users.stream()
                .filter(user -> user.getLogin().equals(credential.getLogin()))
                .filter(user -> passwordEncoder.matches(credential.getPassword(), user.getPassword()))
                .findFirst();
        return registeredUser;
    }

    public String getHashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
