package fr.sasvb.labuvette.security;

import fr.sasvb.labuvette.model.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class JWTPreAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    public JWTPreAuthenticationToken(User principal, WebAuthenticationDetails details) {
        super(principal, details);
        super.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
