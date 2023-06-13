package com.atdxt.Jdbc_Jpa.Repository;


import com.atdxt.Jdbc_Jpa.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> { }
