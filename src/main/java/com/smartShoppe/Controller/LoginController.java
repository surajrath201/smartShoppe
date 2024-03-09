package com.smartShoppe.Controller;

import com.smartShoppe.Dto.UserDetailsDto;
import com.smartShoppe.Service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @GetMapping(path = "/login")
    public ResponseEntity userLogin(@RequestParam Long userId){

        if (Objects.isNull(userId))
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Empty User Id");
        try {
            UserDetailsDto userDetailsDto = loginService.getUserDetails(userId);
            return ResponseEntity.status(HttpStatus.OK).body(userDetailsDto);
        } catch (Exception E) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E.getMessage());
        }
    }

    @PostMapping(path = "/createUser")
    public ResponseEntity insertUserDetails(@RequestBody UserDetailsDto userDetailsDto){

        if (Objects.isNull(userDetailsDto))
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User details is empty");
        try {
            Long userId = loginService.insertUserDetails(userDetailsDto);
            return ResponseEntity.status(HttpStatus.OK).body(userId);
        } catch (Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(E.getMessage());
        }
    }
}
