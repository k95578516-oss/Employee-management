package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Create new employee")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO saved =
                employeeService.saveEmployee(employeeDTO);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable int id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");
    }
    @GetMapping("/department/{id}")
    public ResponseEntity<List<EmployeeDTO>> getByDepartment(@PathVariable int id){
        return ResponseEntity.ok(employeeService.getEmployeeByDepartment(id));
    }
    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeDTO>> getEmployees(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
        return ResponseEntity.ok(employeeService.getEmployee(page, size, sortBy));
    }
}