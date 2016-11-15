package com.rent.api.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Duck on 10/28/2016.
 */
public class UserDTO implements java.io.Serializable {

    @NotNull(message = "username required")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "user name must be a valid email address.")
    private String username;

    @NotNull(message = "password required")
    @Pattern(regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9!@#$%&*]{7,}$",
             message = "Password must contain at least seven characters with one number and one letter.")
    @JsonIgnore
    private String password;

    @NotNull(message = "first name required")
    @Size(min = 1, message = "First name is required.")
    private String firstName;

    @NotNull(message = "last name required")
    @Size(min = 1, message = "Last name is required.")
    private String lastName;

    public UserDTO() {

    }

    public UserDTO(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getPassword() {

        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
