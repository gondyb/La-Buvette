package fr.sasvb.labuvette.resolver;

import fr.sasvb.labuvette.model.User;
import fr.sasvb.labuvette.service.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class UserResolver implements GraphQLResolver<User> {
    private final UserService service;

    @Autowired
    public UserResolver(UserService service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    public String getToken(User user) {
        return service.getToken(user);
    }
}
