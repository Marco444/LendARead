package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LocationForm {

    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String zipcode;

    @Size(min = 1, max = 100)
    private String locality;

    @Size(min = 4, max = 100)
    private String province;

    @Size(min = 4, max = 100)
    private String country;

    @Size(min = 1, max = 100)
    private String name;

    private int id;
}
