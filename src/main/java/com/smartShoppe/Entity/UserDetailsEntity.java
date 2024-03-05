package com.smartShoppe.Entity;

import com.smartShoppe.Enum.CustomerType;
import com.smartShoppe.Util.ValidationResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    private String mName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Country code must not be null")
    private Integer countryCode;

    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "[0-9]+", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotNull(message = "Customer type must not be null")
    private CustomerType type;

    @Past(message = "Date of birth must be in the past")
    private Date dob;

    public ValidationResult<UserDetailsEntity> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDetailsEntity>> violations = validator.validate(this);
        if (!violations.isEmpty()){
            ValidationResult<UserDetailsEntity> result = new ValidationResult<>();
            for (ConstraintViolation<UserDetailsEntity> violation : violations){
                result.addError(violation.getPropertyPath().toString(), violation.getMessage());
            }
            result.setValid(Boolean.FALSE);
            return result;
        }
        return ValidationResult.success(this);
    }
}
