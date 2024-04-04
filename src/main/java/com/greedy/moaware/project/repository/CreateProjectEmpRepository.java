package com.greedy.moaware.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.project.entity.CreateProjectEmp;

public interface CreateProjectEmpRepository extends JpaRepository<CreateProjectEmp, Integer>{

	@Query("SELECT e " + 
	   "  FROM CreateProjectEmp e " +
	   "  JOIN Pdept p ON e.dept.deptCode = p.deptCode " +
	   " WHERE p.deptCode = :deptCode ")
	List<CreateProjectEmp> findByDeptDeptCode(@Param("deptCode") Integer deptCode);			

}
