package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeViewController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    @GetMapping("/employee")
    public String listEmployees(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "employees";
    }

    @GetMapping("/employees/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee-form";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDTO dto) {
        service.saveEmployee(dto);
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "employee-form";
    }

    @GetMapping("/employees/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteEmployee(id);
        return "redirect:/employees";
    }
}