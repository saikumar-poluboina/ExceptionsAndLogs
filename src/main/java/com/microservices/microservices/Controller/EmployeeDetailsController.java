package com.microservices.microservices.Controller;

import com.microservices.microservices.Model.Employee;
import com.microservices.microservices.Service.EmployyedetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/employee")
@RestController
public class EmployeeDetailsController {

    @Autowired
    EmployyedetailsService service;

    // Register patient
    @PostMapping("/save")
    public ResponseEntity<String> saveDetails(@RequestBody @Valid List<Employee> jsonData) {
        return ResponseEntity.ok(service.saveEmployeeData(jsonData));
    }

    // Get patient by ID
    @GetMapping("/get/id/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        Employee emp = service.getEmployeeByIdOrThrow(id);
        return ResponseEntity.ok(emp);
    }


    // Update email and mobile by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmailMobile(@PathVariable int id,
                                                      @RequestParam String email,
                                                      @RequestParam String mobile) {
        return service.updateEmailAndMobile(id, email, mobile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get name and gender by ID
    @GetMapping("/get/name-gender/{id}")
    public ResponseEntity<?> getNameGender(@PathVariable int id) {
        return service.getEmployeeById(id)
                .map(emp -> ResponseEntity.ok(Map.of("name", emp.getName(), "gender", emp.getGender())))
                .orElse(ResponseEntity.notFound().build());
    }
}
