package imie.campus.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import imie.campus.model.Views;
import imie.campus.utils.commons.ValidationPatterns;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTO {
    @JsonView(Views.Public.class)
    @NotNull
    @Pattern(message = "must only contains alphanumeric characters or [#, -, _, !]",
            regexp = ValidationPatterns.LOGIN)
    private String username;

//    @JsonView(Views.Creation.class)
//    @NotNull
//    private String password;

    @JsonView(Views.Public.class)
    @NotNull @Pattern(regexp = ValidationPatterns.NAMES)
    @Length(min = 3, max = 20)
    private String lastName;

    @JsonView(Views.Public.class)
    @NotNull @Pattern(regexp = ValidationPatterns.NAMES)
    @Length(min = 3, max = 20)
    private String firstName;

    @JsonView(Views.Creation.class)
    @NotNull @Pattern(regexp = ValidationPatterns.EMAIL,
        message = "This email address is not valid.")
    private String email;

    @JsonView(Views.Creation.class)
    @NotNull @Pattern(regexp = ValidationPatterns.PHONE,
        message = "This phone number is not valid.")
    private String phone;

    @JsonIgnore
    private Set<String> roles;

    @JsonIgnore
    private String creationDate;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
