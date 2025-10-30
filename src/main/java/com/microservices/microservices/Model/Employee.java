package com.microservices.microservices.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EmployeeReg")//tbl_tablename
public class Employee {
  //  @NotNull(message = "Name ID cannot be null")
     @NotBlank(message = "Name cannot be empty")
    private String name;
//     @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
     @Min(value = 1000000000L, message = "Mobile number must be 10 digits")
     @Max(value = 9999999999L, message = "Mobile number must be 10 digits")
     private String mobile;
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must be less than or equal to 65")
    private Integer age;
    @NotNull(message = "Gender must be specified")
    private Character gender;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
