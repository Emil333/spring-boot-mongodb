package com.emil.springproject.controller;

import com.emil.springproject.beans.Employee;
import com.emil.springproject.exception.ResourceNotFoundException;
import com.emil.springproject.repository.EmployeeRepository;
import com.emil.springproject.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO add service based implementations

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this id: " + employeeId)
        );
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {

        employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this id:" + employeeId));
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this id: " + employeeId));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

}
