package com.greedy.moaware.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.moaware.employee.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer>{
	
}
