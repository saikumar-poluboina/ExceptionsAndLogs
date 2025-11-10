package com.microservices.microservices.Service;

import com.microservices.microservices.Exceptions.ResourceNotFoundException;
import com.microservices.microservices.Model.Employee;
import com.microservices.microservices.Repo.EmployeeDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployyedetailsService {

    private static final Logger logger = LoggerFactory.getLogger(EmployyedetailsService.class);

    @Autowired
    EmployeeDetailsRepo repo;

    public String saveEmployeeData(Employee employeeData) {
        logger.debug("Saving {} employee records", employeeData);
        repo.save(employeeData);
        logger.info("Successfully saved {} employee records", employeeData);
        return "Saved successfully";
    }

    public Optional<Employee> getEmployeeById(int id) {
        logger.debug("Looking up employee by ID: {}", id);
        return repo.findById(id);
    }

    public Employee getEmployeeByIdOrThrow(int id) {
        logger.debug("Looking up employee by ID (or throw): {}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee with ID {} not found", id);
                    return new ResourceNotFoundException("Employee with ID " + id + " not found");
                });
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        logger.debug("Fetching employee by email: {}", email);
        return repo.findByEmail(email);
    }

    public Optional<Employee> getEmployeeByAge(int age) {
        logger.debug("Fetching employee by age: {}", age);
        return repo.findByAge(age);
    }

    public Optional<Employee> getEmployeesByName(String name) {
        logger.debug("Fetching employee by name: {}", name);
        return repo.findByName(name);
    }

    public Optional<Employee> updateEmailAndMobile(int id, String email, String mobile) {
        logger.info("Updating employee {} with email={} and mobile={}", id, email, mobile);
        return repo.findById(id).map(emp -> {
            if (email != null && !email.isBlank()) {
                emp.setEmail(email);
                logger.debug("Email updated for ID {}: {}", id, email);
            }
            if (mobile != null && !mobile.isBlank()) {
                emp.setMobile(mobile);
                logger.debug("Mobile updated for ID {}: {}", id, mobile);
            }
            Employee saved = repo.save(emp);
            logger.info("Employee {} updated successfully", id);
            return saved;
        });
    }

    public List<Employee> getAllData() {
        return repo.findAll();
    }

    public Optional<Employee> findByEmailAndPassword(String email, String password) {
        return repo.findByEmailAndPassword(email,password);
    }

    public boolean existsByEmail(String email) {
        return repo.findByEmail(email).isPresent();
    }

    public boolean resetPassword(String email, String newPassword) {
        Optional<Employee> emp = repo.findByEmail(email);
        if (emp.isPresent()) {
            emp.get().setPassword(newPassword);
            repo.save(emp.get());
            return true;
        }
        return false;
    }

}
