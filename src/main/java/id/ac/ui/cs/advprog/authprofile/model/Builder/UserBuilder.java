package id.ac.ui.cs.advprog.authprofile.model.Builder;


import id.ac.ui.cs.advprog.authprofile.model.UserEntity;
import id.ac.ui.cs.advprog.authprofile.model.enums.UserType;

import org.springframework.stereotype.Component;

@Component
public class UserBuilder {
    private UserEntity currentUser;
    public UserBuilder(){
        this.reset();
    }

    public UserBuilder reset(){
        currentUser = new UserEntity();
        return this;
    }

    public UserBuilder addName(String name){
        currentUser.setName(name);
        return this;
    }

    public UserBuilder addEmail(String email){
        currentUser.setEmail(email);
        return this;
    }

    public UserBuilder addPassword(String password){
        currentUser.setPassword(password);
        return this;
    }

    public UserBuilder addAddress(String address){
        currentUser.setAddress(address);
        return this;
    }

    public UserBuilder addPhoneNumber(String phoneNumber){
        currentUser.setPhonenumber(phoneNumber);
        return this;
    }

    public UserBuilder addRole(String role){
        if (UserType.contains(role)){
            currentUser.setRole(role);
            return this;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public UserBuilder setCurrent(UserEntity user){
        currentUser = user;
        return this;
    }

    public UserEntity build(){
        return currentUser;
    }
}