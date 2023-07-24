package com.atdxt.Multi_logging.Repository;

import com.atdxt.Multi_logging.Entity.Customer2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Customer2Repository extends JpaRepository<Customer2, Long> {

    Optional<Customer2> findByUsername(String username);
}