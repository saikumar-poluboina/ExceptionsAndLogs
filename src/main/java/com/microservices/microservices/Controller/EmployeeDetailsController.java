package com.microservices.microservices.Controller;

import com.microservices.microservices.Model.Employee;
import com.microservices.microservices.Service.EmployyedetailsService;
import com.microservices.microservices.dto.UpdateEmployeeDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RequestMapping("/api/employee")
@RestController
@Validated
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class EmployeeDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsController.class);

    @Autowired
    EmployyedetailsService service;

    // Register employee
    @PostMapping("/save")
    public ResponseEntity<?> saveDetails(@RequestBody @Valid Employee jsonData) {
        logger.info("Received request to save {} employees", jsonData);
        try {
            String response = service.saveEmployeeData(jsonData);
            logger.info("Successfully saved {} employees", jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while saving employees: {}", e.getMessage(), e);
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
           // throw e;
        }
    }
    @GetMapping("/getAllData")
    public ResponseEntity<?> getAllEmployeesData(){
        return ResponseEntity.ok(service.getAllData()) ;
    }
    // Get employee by ID
    @GetMapping("/get/id/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee emp = service.getEmployeeByIdOrThrow(id);
        logger.debug("Fetched employee details: {}", emp);
        return ResponseEntity.ok(emp);
    }

    // Update email and mobile
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmailMobile(
            @PathVariable int id,
            @Valid @RequestBody UpdateEmployeeDTO dto) {
        logger.info("Updating employee ID {} with email={} and mobile={}", id, dto.getEmail(), dto.getMobile());
        return service.updateEmailAndMobile(id, dto.getEmail(), dto.getMobile())
                .map(emp -> {
                    logger.info("Successfully updated employee ID {}", id);
                    return ResponseEntity.ok(emp);
                })
                .orElseGet(() -> {
                    logger.warn("Employee with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Get name and gender by ID
    @GetMapping("/get/name-gender/{id}")
    public ResponseEntity<?> getNameGender(@PathVariable int id) {
        logger.info("Fetching name and gender for employee ID {}", id);
        return service.getEmployeeById(id)
            .map(emp -> {
                logger.debug("Employee found: {}", emp);
                return ResponseEntity.ok(Map.of("name", emp.getName(), "gender", emp.getGender()));
            })
            .orElseGet(() -> {
                logger.warn("Employee with ID {} not found for name-gender fetch", id);
                return ResponseEntity.notFound().build();
            });
    }

    // fpr login page
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        var employeeOpt = service.findByEmailAndPassword(email,password);

        if (employeeOpt.isPresent()) {
            Employee emp = employeeOpt.get();
            if (emp.getPassword().equals(password)) {
                return ResponseEntity.ok(Map.of(
                        "message", "Login successful",
                        "employeeId", emp.getId(),
                        "name", emp.getName()
                ));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
            }
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
    }
    // fprgot functionality
    @GetMapping("/verifyEmail/{email}")
    public ResponseEntity<?> verifyEmail(@PathVariable String email) {
        boolean exists = service.existsByEmail(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");
        boolean updated = service.resetPassword(email, newPassword);
        return ResponseEntity.ok(Map.of("updated", updated));
    }


}
