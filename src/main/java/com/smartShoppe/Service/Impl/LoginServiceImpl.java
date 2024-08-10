package com.smartShoppe.Service.Impl;


import com.smartShoppe.Dto.VendorDto;
import com.smartShoppe.Entity.UserDetailsEntity;
import com.smartShoppe.Entity.VendorAdminEntity;
import com.smartShoppe.Entity.VendorEntity;
import com.smartShoppe.Enum.CustomerType;
import com.smartShoppe.Repositories.UserDetailsRepository;
import com.smartShoppe.Repositories.VendorAdminRepository;
import com.smartShoppe.Repositories.VendorRepository;
import com.smartShoppe.Util.ValidationResult;
import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Service.ILoginService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LoginServiceImpl implements ILoginService {

    private final UserDetailsRepository userDetailsRepository;

    private final VendorRepository vendorRepository;

    private final VendorAdminRepository vendorAdminRepository;

    private final ModelMapper modelMapper;

    public LoginServiceImpl(UserDetailsRepository userDetailsRepository, VendorRepository vendorRepository,
                            VendorAdminRepository vendorAdminRepository, ModelMapper modelMapper) {
        this.userDetailsRepository = userDetailsRepository;
        this.vendorRepository = vendorRepository;
        this.vendorAdminRepository = vendorAdminRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetailsDto getUserDetails(Long userId){

        if (Objects.isNull(userId))
            throw new RuntimeException("user Id not found");
        Optional<UserDetailsEntity> optionalUserDetailsDto = userDetailsRepository.findById(userId);

        if (optionalUserDetailsDto.isEmpty())
            throw new RuntimeException("User Id does not exist");
        return modelMapper.map(optionalUserDetailsDto.get(), UserDetailsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailsDto insertUserDetails(UserDetailsDto userDetailsDto) {

        if (Objects.isNull(userDetailsDto))
            throw new RuntimeException("Nothing to insert");
        UserDetailsEntity userDetailsEntity = modelMapper.map(userDetailsDto, UserDetailsEntity.class);
        ValidationResult<UserDetailsEntity> validationResult = userDetailsEntity.validate();

        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());

        try {
            userDetailsEntity = validationResult.getData();
            UserDetailsEntity savedUserDetailsEntity = userDetailsRepository.save(userDetailsEntity);
            if (Objects.equals(userDetailsEntity.getType(), CustomerType.ADMIN)) {
                VendorEntity vendorEntity = getVendorByName(userDetailsDto.getVendorName());
                Long vendorAdminId = createVendorAdmin(vendorEntity, savedUserDetailsEntity);
                if (Objects.isNull(vendorAdminId))
                    throw new RuntimeException("Error creating vendor admin");
            }
            return modelMapper.map(savedUserDetailsEntity, UserDetailsDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User with the same Email or Mobile Number already exists", e);
        }
    }

    @Override
    public VendorDto createVendor(VendorDto vendorDto) {



        if (Objects.isNull(vendorDto))
            throw new RuntimeException("Nothing to insert");

        VendorEntity vendorEntity = modelMapper.map(vendorDto, VendorEntity.class);
        ValidationResult<VendorEntity> validationResult = vendorEntity.validate();
        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());
        try{
            VendorEntity vendorEntityCreated = vendorRepository.save(validationResult.getData());
            return modelMapper.map(vendorEntityCreated, VendorDto.class);
        } catch (Exception e){
            throw new RuntimeException("vendor with given name already Exist", e);
        }
    }

    private VendorEntity getVendorByName(String vendorName){

        if (Objects.isNull(vendorName))
            throw new RuntimeException("vendor name cannot be Empty");
        Optional<VendorEntity> optionalVendorEntity = vendorRepository.getByVendorName(vendorName);
        if (!optionalVendorEntity.isPresent())
            throw new RuntimeException("Vendor name does not exist");
        return optionalVendorEntity.get();
    }
    private Long createVendorAdmin(VendorEntity vendorEntity, UserDetailsEntity userDetailsEntity){
        if (Objects.isNull(vendorEntity))
            throw new RuntimeException("Vendor cannot be empty");
        if (Objects.isNull(userDetailsEntity))
            throw new RuntimeException("User cannot be Empty");
        VendorAdminEntity vendorAdminEntity = new VendorAdminEntity();
        vendorAdminEntity.setVendorEntity(vendorEntity);
        vendorAdminEntity.setUserDetailsEntity(userDetailsEntity);
        ValidationResult<VendorAdminEntity> validationResult = vendorAdminEntity.validate();
        if (!validationResult.getValid())
            throw new RuntimeException(validationResult.getErrors().toString());
        VendorAdminEntity vendorAdminEntityCreated = vendorAdminRepository.save(validationResult.getData());
        return vendorAdminEntityCreated.getId();
    }
}
