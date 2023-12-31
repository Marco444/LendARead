package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.RepeatPassword;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.ValidToken;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RepeatPassword
@Getter
@Setter
public class ChangePasswordForm {

    @Size(min = 3, max = 500)
    @ValidToken
    private String token;

    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    @NotEmpty
    @Size(max = 100)
    private String repeatPassword;

}
