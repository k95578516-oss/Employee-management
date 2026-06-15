package com.example.demo;

import com.example.demo.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    public class EmployeeServiceTest {

        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private DepartmentRepository departmentRepository;

        @InjectMocks
        private EmployeeService employeeService;

        @Test
        void shouldReturnEmployeeWhenEmployeeExists() {

            Department department = new Department();
            department.setId(1);
            department.setName("IT");

            Employee employee = new Employee();

            employee.setId(1);
            employee.setName("Khushi");
            employee.setEmail("khushi@gmail.com");
            employee.setDepartment(department);

            when(employeeRepository.findById(1))
                    .thenReturn(Optional.of(employee));

            EmployeeDTO result =
                    employeeService.getEmployeeById(1);

            assertNotNull(result);

            assertEquals(1, result.getId());
            assertEquals("Khushi", result.getName());
            assertEquals("khushi@gmail.com", result.getEmail());
            assertEquals(1, result.getDepartmentId());
        }

        @Test
        void shouldThrowExceptionWhenEmployeeNotFound() {

            when(employeeRepository.findById(1))
                    .thenReturn(Optional.empty());

            assertThrows(
                    ResourceNotFoundException.class,
                    () -> employeeService.getEmployeeById(1)
            );
        }
        @Test
        void shouldSaveEmployee() {

            Department department = new Department();
            department.setId(1);

            Employee employee = new Employee();
            employee.setId(1);
            employee.setName("Khushi");
            employee.setEmail("khushi@gmail.com");
            employee.setDepartment(department);

            EmployeeDTO dto = new EmployeeDTO();
            dto.setName("Khushi");
            dto.setEmail("khushi@gmail.com");
            dto.setDepartmentId(1);

            when(departmentRepository.findById(1))
                    .thenReturn(Optional.of(department));

            when(employeeRepository.save(org.mockito.ArgumentMatchers.any(Employee.class)))
                    .thenReturn(employee);

            EmployeeDTO result =
                    employeeService.saveEmployee(dto);

            assertEquals("Khushi", result.getName());
        }
    }

