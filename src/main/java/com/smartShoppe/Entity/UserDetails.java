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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_details", uniqueConstraints = {
        @UniqueConstraint(name = "id_UNIQUE", columnNames = "id"),
        @UniqueConstraint(name = "email_mobile_unq", columnNames = {"email", "mobile_number"})
})
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_id_sequence")
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "First name must not be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotBlank(message = "Last name must not be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Invalid email address")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Country code must not be null")
    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    @Pattern(regexp = "^[0-9]*$", message = "Invalid mobile number format")
    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Customer type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CustomerType type;

    @Past(message = "Date of birth must be in the past")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "created_timestamp", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdTimestamp;

    @Column(name = "modified_timestamp", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifiedTimestamp;

    @AssertTrue(message = "Either email or mobile number must be provided")
    private boolean isEmailOrMobileNumberProvided() {
        return (email != null && !email.isEmpty()) != (mobileNumber != null && !mobileNumber.isEmpty());
    }

    public ValidationResult<UserDetails> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDetails>> violations = validator.validate(this);
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
