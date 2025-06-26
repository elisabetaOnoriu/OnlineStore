package com.beci.product_service.mapper;

import com.beci.product_service.dto.UserRequest;
import com.beci.product_service.dto.UserResponse;
import com.beci.product_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRoles(user.getRoles());

        return response;
    }

    public User toEntity(UserRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRoles(request.getRoles());

        return user;
    }

    public UserRequest toRequest(User user) {
        if (user == null) return null;

        UserRequest request = new UserRequest();
        request.setUsername(user.getUsername());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        request.setPhoneNumber(user.getPhoneNumber());
        request.setRoles(user.getRoles());

        return request;
    }

    public void updateUserFromRequest(User user, UserRequest request) {
        if (user == null || request == null) return;

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRoles(request.getRoles());
    }
}
