package servlet.auth.helper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Credential {
    private String login;
    private String password;
}
