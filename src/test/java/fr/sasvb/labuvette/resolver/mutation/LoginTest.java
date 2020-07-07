package fr.sasvb.labuvette.resolver.mutation;

import fr.sasvb.labuvette.model.User;
import fr.sasvb.labuvette.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    UserService userService;

    @Mock
    AuthenticationProvider authenticationProvider;

    @InjectMocks
    Login loginMutation;

    @Test
    public void it_should_check_for_credentials() {
        // Arrange
        String username = "benjamin";
        String password = "securedpassword";

        User user = new User();
        user.setUsername(username);

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return credentials;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return username;
            }
        };

        when(authenticationProvider.authenticate(credentials)).thenReturn(authentication);
        when(userService.getCurrentUser()).thenReturn(user);

        // Act
        User returnedUser = loginMutation.login(username, password);

        // Assert
        Assert.assertEquals(returnedUser.getUsername(), username);
        verify(authenticationProvider, times(1)).authenticate(credentials);
        verify(userService, times(1)).getCurrentUser();
    }

    @Test(expected = BadCredentialsException.class)
    public void it_should_throw_an_exception_if_authentication_does_not_succeed() {
        // Arrange
        String username = "benjamin";
        String password = "wrondpassword";

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return credentials;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return username;
            }
        };

        when(authenticationProvider.authenticate(credentials)).thenReturn(authentication);
        when(userService.getCurrentUser()).thenThrow(new BadCredentialsException("Credentials bad"));

        // Act
        loginMutation.login(username, password);

        // Assert

    }
}