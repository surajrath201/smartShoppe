package com.smartShoppe.Dto;

import com.smartShoppe.Enum.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    private String firstName;
    private String mName;
    private String lastName;
    private String email;
    private Integer countryCode;
    private String mobileNumber;
    private String password;
    private CustomerType type;
    private Date dob;
}
