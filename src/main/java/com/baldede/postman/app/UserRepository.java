package com.baldede.postman.app;

import com.baldede.postman.domain.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<NewUser, Integer> {

    void deleteByUsername(String username);
}
