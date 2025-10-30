package com.microservices.microservices.Service;

import com.microservices.microservices.Exceptions.ResourceNotFoundException;
import com.microservices.microservices.Model.Employee;
import com.microservices.microservices.Repo.EmployeeDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployyedetailsService {

    @Autowired
    EmployeeDetailsRepo repo;

    public String saveEmployeeData(List<Employee> employeeData) {
        repo.saveAll(employeeData);
        return "Saved successfully";
    }

    public Optional<Employee> getEmployeeById(int id) {
        return repo.findById(id);
    }
    public Employee getEmployeeByIdOrThrow(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Optional<Employee> getEmployeeByAge(int age) {
        return repo.findByAge(age);
    }

    public Optional<Employee> getEmployeesByName(String name) {
        return repo.findByName(name);
    }

    public Optional<Employee> updateEmailAndMobile(int id, String email, String mobile) {
        return repo.findById(id).map(emp -> {
            emp.setEmail(email);
            emp.setMobile(mobile);
            return repo.save(emp);
        });
    }
}
