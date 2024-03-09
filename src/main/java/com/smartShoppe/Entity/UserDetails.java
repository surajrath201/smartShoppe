package com.smartShoppe.Entity;

import com.smartShoppe.Enum.CustomerType;
import com.smartShoppe.Util.ValidationError;
import com.smartShoppe.Util.ValidationResult;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_id_sequence")
    private Long id;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Country code must not be null")
    private Integer countryCode;

    @Pattern(regexp = "^[0-9]*$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotNull(message = "Customer type must not be null")
    private CustomerType type;

    @Past(message = "Date of birth must be in the past")
    private Date dob;

    @AssertTrue(message = "Either email or mobile number must be provided")
    private boolean isEmailOrMobileNumberProvided() {
        return (email != null && !email.isEmpty()) != (mobileNumber != null && !mobileNumber.isEmpty());
    }

    public ValidationResult<UserDetailsEntity> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDetailsEntity>> violations = validator.validate(this);
        if (!violations.isEmpty()){
            List<ValidationError> result = violations.stream()
                                           .map(violation ->
                                                  new ValidationError(violation.getPropertyPath().toString(),
                                                           violation.getMessage())).collect(Collectors.toList());
            return ValidationResult.error(result);
        }
        return ValidationResult.success(this);
    }
}
