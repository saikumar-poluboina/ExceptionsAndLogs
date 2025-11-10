package com.microservices.microservices.Repo;

import ch.qos.logback.core.model.conditional.ElseModel;
import com.microservices.microservices.Model.Employee;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDetailsRepo extends JpaRepository<Employee,Integer> {
    Optional<Employee> findById(int id);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByAge(int age);
    Optional<Employee> findByName(String Name);

    Optional<Employee> findByEmailAndPassword(String email, String password);
}
