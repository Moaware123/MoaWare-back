package com.greedy.moaware.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AuthEmp;

public interface AuthEmpRepository extends JpaRepository<AuthEmp, Integer>{
	
	Optional<AuthEmp> findByEmpId(String empId);

	Optional<AuthEmp> findByEmpCode(Integer empCode);

}