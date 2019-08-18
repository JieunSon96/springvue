package com.example.springvue.service;

import com.example.springvue.entity.UserAccount;
import com.example.springvue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserAccount> findAllUser() {
        return userRepository.findAll();
    }

    //유저 회원가입
    public UserAccount createUser(UserAccount userAccount) {
        UserAccount user = userAccount;
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return user;
        }
    }

    //회원가입시 유저이름 확인
    public boolean existsByUserName(String userName){
       boolean result=false;
       try{
           result=userRepository.existsByUserName(userName);
       }catch (Exception e){
           System.out.println(e);
       }finally {
           return result;
       }

    }

    //회원가입시 유저이메일 확인
    public boolean existsByEmail(String email){
        boolean result=false;
        try{
            result=userRepository.existsByUserName(email);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return result;
        }

    }

    //유저 이름에 따른 유저 정보 확인
    public Optional<UserAccount> retrieveByUserName(String userName) {
        Optional<UserAccount> userAccount = userRepository.findByUsername(userName);
        return userAccount;
    }
}
