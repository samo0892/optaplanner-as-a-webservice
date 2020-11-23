package com.baldede.postman.app;

import com.baldede.postman.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stopRepository")
public interface StopRepository extends JpaRepository<Stop, Long> {

}
