package com.atdxt.Multi_logging.Repository;

import com.atdxt.Multi_logging.Entity.Customer1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Customer1Repository extends JpaRepository<Customer1, Long> {
}
