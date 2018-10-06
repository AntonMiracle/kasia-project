package com.kasia.repository.converter;

import com.kasia.model.User;

import javax.persistence.AttributeConverter;

public class UserRoleAttributeConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role role) {
        return role.toString();
    }

    @Override
    public User.Role convertToEntityAttribute(String s) {
        return User.Role.valueOf(s);
    }
}
