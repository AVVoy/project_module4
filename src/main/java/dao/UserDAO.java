package dao;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;

import java.io.File;
import java.util.List;
import java.util.UUID;


public class UserDAO extends BaseDAO<User, UUID>{
    public UserDAO(ObjectMapper objectMapper, File file) {
        super(objectMapper, file, new TypeReference<List<User>>() {});
    }

    @Override
    protected UUID getId(User user) {
        return user.getId();
    }
}
