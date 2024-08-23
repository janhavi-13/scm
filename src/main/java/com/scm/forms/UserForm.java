package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 characters are allowed")
    private String name;
    @Email(message = "Invalid email address")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 10, message = "Min 6 & Max 10 characters are allowed")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min=10, max=10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d+", message = "Phone number can only contain digits")
    private String phoneNumber;
}
