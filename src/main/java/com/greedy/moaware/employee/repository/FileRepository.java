package com.greedy.moaware.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AttachedFile;

public interface FileRepository extends JpaRepository<AttachedFile, Integer>{

}
