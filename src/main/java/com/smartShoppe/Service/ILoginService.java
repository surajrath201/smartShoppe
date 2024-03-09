package com.smartShoppe.Service;

import com.smartShoppe.Dto.UserDetailsDto;

public interface ILoginService {

    /**
     * @param userId : id of the user
     * @return : user details of
     */
    public UserDetailsDto getUserDetails(Long userId);

    /**
     * @param userDetailsDto : user details
     * @return : True if user details is updated
     */
    public Long insertUserDetails(UserDetailsDto userDetailsDto);
}
