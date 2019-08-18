package com.example.springvue.controller;

import com.example.springvue.entity.UserAccount;
import com.example.springvue.exception.ResourceNotFoundException;
import com.example.springvue.service.UserService;
import com.example.springvue.vo.ApiResponse;
import com.example.springvue.vo.SignUpRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestVO signUpRequestVO) {
        if (userService.existsByUserName(signUpRequestVO.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "EC01"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequestVO.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "EC02"), HttpStatus.BAD_REQUEST);
        }

        UserAccount userAccount = new UserAccount(signUpRequestVO.getNickName(), signUpRequestVO.getUserName(), signUpRequestVO.getEmail(), signUpRequestVO.getPassword());

        //이어서 만들기
        return ResponseEntity.ok(userAccount);
    }

    @GetMapping(value = "/retrieveUser")
    public List<UserAccount> findUsers() {
        return userService.findAllUser();
    }

    @GetMapping(value = "/findUser")
    public ResponseEntity<UserAccount> retrieveByUserName(HttpServletRequest request) {
        UserAccount userAccount = userService.retrieveByUserName(request.getUserPrincipal().getName())
                .orElseThrow(() -> new ResourceNotFoundException("UserAccount", "userId", request.getUserPrincipal().getName()));

        return new ResponseEntity<UserAccount>(userAccount, HttpStatus.OK);
    }

}
