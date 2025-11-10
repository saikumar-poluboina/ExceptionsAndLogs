package com.microservices.microservices.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {
    @Email(message = "Email must be valid")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain (e.g. example@domain.com)")
//    @NotBlank(message = "Email cannot be empty")  // commented becase either mail or mobile going to pdate not same at a time
    private String email;

    @JsonIgnore //Will not reflect into the output ..
    private Integer id;


    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private String mobile;
}



