package com.smartShoppe.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDto {

    private Long id;
    private String  name;
    private Long regId;
}
