package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {
   List<Employee> findByNameContaining(String keyword);
   List<Employee> findByDepartmentId(int departmentId);
}