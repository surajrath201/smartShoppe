package com.smartShoppe.Service;

import com.smartShoppe.Dto.UserDetailsDto;

public interface LoginService {

    /**
     * @param userId : id of the user
     * @return : user details of
     */
    public UserDetailsDto getUserDetails(Integer userId);

    /**
     * @param userDetailsDto : user details
     * @return : True if user details is updated
     */
    public Integer insertUserDetails(UserDetailsDto userDetailsDto);
}
