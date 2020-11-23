package com.baldede.postman.controller;


import com.baldede.postman.app.UserRepository;
import com.baldede.postman.domain.NewUser;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {

    //private UserRepository repository;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository repository;
//    @Autowired
//    public UserController (@Qualifier("UserRepository") UserRepository repository) {
//        this.repository = repository;
//    }

    @GetMapping(path = "/users")
    public List<NewUser> findAll() {
        System.out.println("HERE: " + repository);
        return repository.findAll();
    }

//    @GetMapping(path = "/{username}")
//    public NewUser find(@PathVariable("username") NewUser username) {
//        return repository.findOne(username);
//    }

    @PostMapping(path = "/setuser", consumes = "application/json")
    public NewUser create(@RequestBody NewUser user) {

        NewUser newuser = new NewUser();
        newuser = user;
        return repository.save(newuser);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable("id") int id) {

        repository.deleteById(id);
    }

    @PutMapping(path = "/update/user", consumes = "application/json")
    public NewUser update(@RequestBody NewUser user){
            return repository.save(user);
        }

}
