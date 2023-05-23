package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Optional;

public interface UserDao {
     UserImpl addUser(Behaviour behavior, String email, String name, String telephone, String password);
    boolean changePassword(String email,String newPassword);
     Optional<UserImpl> getUser(String email);

     boolean changeRole(String email,Behaviour behaviour);

     Optional<UserImpl> getUser(int id);
    boolean setForgotPasswordToken(PasswordResetToken passwordResetToken);
    Optional<PasswordResetToken> getPasswordRestToken(String token);
    int deletePasswordRestToken(String token);
}
