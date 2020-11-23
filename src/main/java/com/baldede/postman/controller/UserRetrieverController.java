package com.baldede.postman.controller;

import com.baldede.postman.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class UserRetrieverController {

    @RequestMapping(method = RequestMethod.GET, value = "/user/alluser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getAllUsers() throws FileNotFoundException, ScriptException {

        return UserRegistration.getInstance().getUserRecords();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserRegistrationReply registerUser(@RequestBody User user) {
        System.out.println("In registerUser");
        UserRegistrationReply userRegistrationReply = new UserRegistrationReply();
        UserRegistration.getInstance().add(user);

        //We are setting the below value just to reply a message back to the caller
        userRegistrationReply.setId(user.getId());
        userRegistrationReply.setName(user.getName());
        userRegistrationReply.setLastname(user.getLastname());
        userRegistrationReply.setPassword(user.getPassword());
        userRegistrationReply.setRole(user.getRole());
        userRegistrationReply.setRegistrationStatus("Successful");

        return userRegistrationReply;
    }

    @RequestMapping(method = RequestMethod.PUT, value="/update/user")
    @ResponseBody
    public String updateUserRecord(@RequestBody User user) {
        System.out.println("In updateUserRecord");
        return UserRegistration.getInstance().upDateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/delete/user/{userId}")
    @ResponseBody
    public String deleteStudentRecord(@PathVariable("userId") int id) {
        System.out.println("In deleteUserRecord");
        return UserRegistration.getInstance().deleteUser(id);
    }
}
