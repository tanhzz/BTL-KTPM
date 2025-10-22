package com.mycompany.qldv.controller;

import com.mycompany.qldv.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users;

    public UserController() {
        this.users = new ArrayList<>();
        // Thêm tài khoản mặc định
        users.add(new User("admin", "password123"));
    }

    public boolean login(String username, String password) {
        return users.stream()
            .anyMatch(user -> user.getUsername().equals(username) && user.checkPassword(password));
}

}
