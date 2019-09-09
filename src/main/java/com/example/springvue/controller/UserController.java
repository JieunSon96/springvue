package com.example.springvue.controller;

import com.example.springvue.entity.RoleName;
import com.example.springvue.entity.UserAccount;
import com.example.springvue.entity.UserRole;
import com.example.springvue.exception.AppException;
import com.example.springvue.exception.ResourceNotFoundException;
import com.example.springvue.security.JwtTokenProvider;
import com.example.springvue.service.UserService;
import com.example.springvue.vo.ApiResponse;
import com.example.springvue.vo.JwtAuthenticationResponse;
import com.example.springvue.vo.LoginRequestVO;
import com.example.springvue.vo.SignUpRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/auth/createUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestVO signUpRequestVO) {
        System.out.println("회원가입컨트롤러");
        if (userService.existsByUserName(signUpRequestVO.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "EC01"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequestVO.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "EC02"), HttpStatus.BAD_REQUEST);
        }

        UserAccount userAccount = new UserAccount(signUpRequestVO.getNickName(), signUpRequestVO.getUserName(), signUpRequestVO.getEmail(), signUpRequestVO.getPassword());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        UserRole userRole=userService.retrieveByName(RoleName.ROLE_USER)
                .orElseThrow(()-> new AppException(("User Role not Set.")));
        userAccount.setRoles(Collections.singleton(userRole));

        userAccount=userService.createUser(userAccount);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(userAccount.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestVO loginRequestVO){
        System.out.println("로그인컨트롤러");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestVO.getUserNameOrEmail(),
                        loginRequestVO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
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
