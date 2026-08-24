package org.example.projectbackend.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean passwordValid(String password){
        if(password.isBlank()||password.length()<8||password.length()>20){
            System.out.println("1");
            return false;
        }
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if(Character.isDigit(c)) hasDigit = true;
            if (hasUpper && hasLower&&hasDigit) break;
        }
       if(!(hasUpper && hasLower&&hasDigit)){
           System.out.println("2");
           return false;
       }
        return true;
    }
}
