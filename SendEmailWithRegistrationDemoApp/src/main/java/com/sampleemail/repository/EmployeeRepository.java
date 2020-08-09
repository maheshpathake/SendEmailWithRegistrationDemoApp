package com.sampleemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampleemail.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
