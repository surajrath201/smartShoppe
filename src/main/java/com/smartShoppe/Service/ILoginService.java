package com.smartShoppe.Service;

import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Dto.VendorDto;

public interface ILoginService {

    /**
     * @param userId : id of the user
     * @return : Details of user whose id is userId
     */
    public UserDetailsDto getUserDetails(Long userId);

    /**
     * @param userDetailsDto : user details
     * @return : created user details with user Id
     */
    public UserDetailsDto insertUserDetails(UserDetailsDto userDetailsDto);

    /***
     *
     * @param vendorDto : vendor details to create the vendor
     * @return : created vendor details with his Id
     */
    public VendorDto createVendor(VendorDto vendorDto);
}
