package com.baldede.postman.controller;

import com.baldede.postman.domain.User;
import com.baldede.postman.domain.UserRegistration;
import com.baldede.postman.domain.UserRegistrationReply;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class UserRetrieverController {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @RequestMapping(method = RequestMethod.GET, value = "/user/alluser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getAllUsers() {
        return UserRegistration.getInstance().getUserRecords();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserRegistrationReply registerUser(@RequestBody User user) {
        logger.info("In registerUser");
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
        logger.info("In updateUserRecord");
        return UserRegistration.getInstance().upDateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/delete/user/{userId}")
    @ResponseBody
    public String deleteStudentRecord(@PathVariable("userId") int id) {
        logger.info("In deleteUserRecord");
        return UserRegistration.getInstance().deleteUser(id);
    }
}
