package com.smartShoppe.Service.Impl;


import com.smartShoppe.Dao.UserDetailsDao;
import com.smartShoppe.Entity.UserDetailsEntity;
import com.smartShoppe.Util.ValidationResult;
import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Override
    public UserDetailsDto getUserDetails(Integer userId){

        if (Objects.isNull(userId))
            throw new RuntimeException("user Id not found");
        Optional<UserDetailsDto> optionalUserDetailsDto = userDetailsDao.getUserDetails(userId);

        if (optionalUserDetailsDto.isEmpty())
            throw new RuntimeException("User Id does not exist");
        return optionalUserDetailsDto.get();
    }

    @Override
    public Integer insertUserDetails(UserDetailsDto userDetailsDto) {

        if (Objects.isNull(userDetailsDto))
            throw new RuntimeException("Nothing to insert");
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        BeanUtils.copyProperties(userDetailsDto, userDetailsEntity);
        ValidationResult<UserDetailsEntity> validationResult = userDetailsEntity.validate();

        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());
        Integer usedId = userDetailsDao.insertUserDetails(validationResult.getData());

        if (Objects.isNull(usedId))
            throw new RuntimeException("Failed to insert");
        return usedId;
    }
}
