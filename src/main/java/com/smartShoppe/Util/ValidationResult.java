package com.smartShoppe.Util;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult<T> {

    private Boolean Valid;
    private T data;
    private List<ValidationError> errors;

    public void addError(String field, String message){
        if (this.errors == null)
            this.errors = new ArrayList<>();
        this.errors.add(new ValidationError(field, message));
    }

    public static <T> ValidationResult<T> success(T data){
        return new ValidationResult<>(Boolean.TRUE, data, new ArrayList<>());
    }

    public static ValidationResult<?> error(List<ValidationError> errors) {
        return new ValidationResult<>(Boolean.FALSE, null, errors);
    }



}
