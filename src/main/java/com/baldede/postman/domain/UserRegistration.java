package com.baldede.postman.domain;

import java.util.ArrayList;
import java.util.List;

public class UserRegistration {

    private List<User> userRecords;
    private static UserRegistration stdregd = null;

    private UserRegistration(){
        userRecords = new ArrayList<User>();
    }

    public static UserRegistration getInstance() {
        if(stdregd == null) {
            stdregd = new UserRegistration();
            return stdregd;
        } else {
            return stdregd;
        }
    }

    public void add(User user) {
        userRecords.add(user);
    }

    public String upDateUser(User user) {
        for(int i=0; i<userRecords.size(); i++) {
            User usr = userRecords.get(i);
            if(usr.getId() == (user.getId())) {
                userRecords.set(i, user);//update the new record
                return "Update successful";
            }
        }
        return "Update un-successful";
    }

    public String deleteUser(int id) {
        for(int i=0; i<userRecords.size(); i++) {
            User user = userRecords.get(i);
            if(user.getId() == (id)){
                userRecords.remove(i);//update the new record
                return "Delete successful";
            }
        }

        return "Delete un-successful";
    }

    public List<User> getUserRecords() {
        return userRecords;
    }
}
