package com.smartShoppe.Entity;

import com.smartShoppe.Util.ValidationError;
import com.smartShoppe.Util.ValidationResult;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vendor")
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_id_sequence")
    @SequenceGenerator(name = "vendor_id_sequence", sequenceName = "vendor_id_sequence", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Vendor name must not be blank")
    @Column(name = "vendor_name", nullable = false, unique = true)
    private String vendorName;

    @Column(name = "created_timestamp", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdTimestamp;

    @Column(name = "modified_timestamp", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifiedTimestamp;

    @PrePersist
    protected void onCreate() {
        createdTimestamp = new java.sql.Date(System.currentTimeMillis());
        modifiedTimestamp = new java.sql.Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedTimestamp = new java.sql.Date(System.currentTimeMillis());
    }

    public ValidationResult<VendorEntity> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<VendorEntity>> violations = validator.validate(this);
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
