package com.example.demo.converter;

import org.springframework.stereotype.Service;

import com.example.demo.command.UserCommand;
import com.example.demo.dto.UserLightDto;
import com.example.demo.model.User;

@Service
public class UserConverter {

    public User apply(UserCommand newUserCommand) {
        User user = new User(newUserCommand.pseudo(), newUserCommand.password());
        return user;
    }

    public UserLightDto applyLight(User user) {
        UserLightDto userLightDto = new UserLightDto(user.getUsername());
        return userLightDto;
    }
}
