package com.smartShoppe.Controller;

import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Dto.VendorDto;
import com.smartShoppe.Service.ILoginService;
import com.smartShoppe.Util.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/user")
public class LoginController {

    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(path = "/details")
    public ResponseEntity<ResponseWrapper> userLogin(@RequestParam Long userId){

        if (Objects.isNull(userId))
            return new ResponseEntity<>(new ResponseWrapper("User Id is Null"), HttpStatus.PRECONDITION_FAILED);
        try {
            UserDetailsDto userDetailsDto = loginService.getUserDetails(userId);
            return new ResponseEntity<>(new ResponseWrapper(userDetailsDto), HttpStatus.OK);
        } catch (Exception E) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/details")
    public ResponseEntity<ResponseWrapper> insertUserDetails(@RequestBody UserDetailsDto userDetailsDto){

        if (Objects.isNull(userDetailsDto))
            return new ResponseEntity<>(new ResponseWrapper("User Details should not be null"), HttpStatus.PRECONDITION_FAILED);
        try {
            UserDetailsDto userDetailsDtoCreated = loginService.insertUserDetails(userDetailsDto);

            return new ResponseEntity<>(new ResponseWrapper(userDetailsDtoCreated), HttpStatus.CREATED);
        } catch (Exception E){
            return new ResponseEntity<>(new ResponseWrapper(E.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/vendor")
    public ResponseEntity<ResponseWrapper> createVendor(@RequestBody VendorDto vendorDto){

        if (Objects.isNull(vendorDto))
            return new ResponseEntity<>(new ResponseWrapper("Vendor details cannot be null"), HttpStatus.PRECONDITION_FAILED);
        try {
            VendorDto vendorDtoCreated = loginService.createVendor(vendorDto);
            return new ResponseEntity<>(new ResponseWrapper(vendorDtoCreated), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseWrapper(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
