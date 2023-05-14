package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface UserService {
    User getUser(final String email) throws UserNotFoundException;
     void createUser(final String email,final String name,final String telephone,final String password);

    void changeRole(final String email,final Behaviour behaviour) throws UserNotFoundException;

     String getCurrentUser();


    Collection
            <? extends GrantedAuthority> getCurrentRoles();
    boolean createChangePasswordToken(final String email);
    boolean changePassword(final String token,final String password);
    boolean isTokenValid(final String token);
    void logInUser(final String email, final String password);
}
