package com.smartShoppe.Entity;

import com.smartShoppe.Util.ValidationError;
import com.smartShoppe.Util.ValidationResult;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "vendor_admin")
public class VendorAdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_admin_id_sequence")
    @SequenceGenerator(name = "vendor_admin_id_sequence", sequenceName = "vendor_admin_id_sequence", allocationSize = 1)
    private Long id;

    @NotNull(message = " Vendor Id should not be null")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private VendorEntity vendorEntity;

    @NotNull(message = "User Id should not be null")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetailsEntity userDetailsEntity;

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

    public ValidationResult<VendorAdminEntity> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<VendorAdminEntity>> violations = validator.validate(this);
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
