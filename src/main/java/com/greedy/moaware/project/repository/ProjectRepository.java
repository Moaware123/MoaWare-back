package com.greedy.moaware.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

	Project findByProjCode(int projCode);

	
	
	
	
	
	
	
	
	
	











	










}
