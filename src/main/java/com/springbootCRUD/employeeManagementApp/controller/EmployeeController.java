package com.springbootCRUD.employeeManagementApp.controller;

import com.springbootCRUD.employeeManagementApp.entities.Employee;
import com.springbootCRUD.employeeManagementApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
       Employee employee = employeeRepository.findById(Math.toIntExact(id))
               .orElseThrow(()-> new ResourceNotFoundException("Employee not available with is : " +id));
        return  ResponseEntity.ok(employee);
    }
 // update the employee
    @PutMapping("/employees/{id}")
     public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails)throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(Math.toIntExact(id))
                .orElseThrow(()-> new ResourceNotFoundException("Employee not updated"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee =  employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);

    }
    //Delete Employee Api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>>deleteEmployee(@PathVariable Long id)throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(Math.toIntExact(id))
                .orElseThrow(()-> new ResourceNotFoundException("Employee not available with is : " +id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
}
