package com.shoestore.Server.service.impl;

import com.shoestore.Server.client.UserClient;
import com.shoestore.Server.dto.response.UserResponseDTO;
import com.shoestore.Server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserClient userClient;

    public UserServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public String getUserNameById(int userId) {
        try {
            UserResponseDTO user = userClient.getUser(userId);
            return user != null ? user.getName() : "Unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }




}
