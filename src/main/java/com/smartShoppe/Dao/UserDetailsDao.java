package com.smartShoppe.Dao;

import com.smartShoppe.Entity.UserDetailsEntity;
import com.smartShoppe.Dto.UserDetailsDto;

import java.util.Optional;

public interface UserDetailsDao {

    public Optional<UserDetailsDto> getUserDetails(Integer userId);

    public Integer insertUserDetails(UserDetailsEntity userDetailsEntity);
}
