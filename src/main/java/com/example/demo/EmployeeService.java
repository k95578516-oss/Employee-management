package com.example.demo;

import com.example.demo.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(this::convertToDTO).toList();
    }

    public EmployeeDTO getEmployeeById(int id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));

        return convertToDTO(employee);
    }

    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        Employee employee = convertToEntity(dto);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    public EmployeeDTO updateEmployee(int id, EmployeeDTO dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());

        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(dept);

        Employee updatedEmployee = employeeRepository.save(employee);

        return convertToDTO(updatedEmployee);
    }

    public void deleteEmployee(int id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(employee);
    }

    private EmployeeDTO convertToDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentId(employee.getDepartment().getId());

        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {

        Employee employee = new Employee();

        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        Department dept = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(dept);

        return employee;
    }
    public List<EmployeeDTO> getEmployeeByDepartment(int deptId){
        List<Employee> employees = employeeRepository.findByDepartmentId(deptId);
        return employees.stream().map(this::convertToDTO).toList();
    }

    public Page<EmployeeDTO> getEmployee(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Employee> employeepage = employeeRepository.findAll(pageable);
        return employeepage.map(this::convertToDTO);
    }

}