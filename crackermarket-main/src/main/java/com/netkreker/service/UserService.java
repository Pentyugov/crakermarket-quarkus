package com.netkreker.service;

import com.netkreker.model.user.Role;
import com.netkreker.model.user.Status;
import com.netkreker.model.user.User;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

public class UserService {
    public static User createUserFromToken(JsonWebToken jsonWebToken, SecurityIdentity identity) {
        User user = new User();
        user.setId(UUID.fromString(jsonWebToken.getSubject()));
        user.setUsername(jsonWebToken.getName());
        user.setEmail(jsonWebToken.getClaim("email"));
        user.setFirstName(jsonWebToken.getClaim("given_name"));
        user.setLastName(jsonWebToken.getClaim("family_name"));
        user.setRole(identity.getRoles().stream().filter(a -> a.equals(Role.ROLE_ADMIN) || a.equals(Role.ROLE_SIMPLE_USER)).findFirst().get());
        user.setStatus(Status.ACTIVE);
        return user;
    }
}
