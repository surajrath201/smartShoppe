package com.smartShoppe.Service.Impl;


import com.smartShoppe.Entity.UserDetails;
import com.smartShoppe.Repositories.UserDetailsRepository;
import com.smartShoppe.Util.ValidationResult;
import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Service.ILoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetailsDto getUserDetails(Long userId){

        if (Objects.isNull(userId))
            throw new RuntimeException("user Id not found");
        Optional<UserDetails> optionalUserDetailsDto = userDetailsRepository.findById(userId);

        if (optionalUserDetailsDto.isEmpty())
            throw new RuntimeException("User Id does not exist");
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        BeanUtils.copyProperties(optionalUserDetailsDto.get(), userDetailsDto);
        return userDetailsDto;
    }

    @Override
    public Long insertUserDetails(UserDetailsDto userDetailsDto) {

        if (Objects.isNull(userDetailsDto))
            throw new RuntimeException("Nothing to insert");
        UserDetails userDetails = new UserDetails();
        BeanUtils.copyProperties(userDetailsDto, userDetails);
        ValidationResult<UserDetails> validationResult = userDetails.validate();

        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());
        
        try {
            UserDetails savedUserDetails = userDetailsRepository.save(validationResult.getData());
            if (Objects.isNull(savedUserDetails.getId())) {
                throw new RuntimeException("Failed to insert");
            }
            return savedUserDetails.getId();

        } catch (DataIntegrityViolationException e) {
            // Catch the exception for duplicate key violation
            throw new RuntimeException("User with the same Email or Mobile Number already exists", e);
        }


    }
}
