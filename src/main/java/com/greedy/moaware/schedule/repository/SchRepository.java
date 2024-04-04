package com.greedy.moaware.schedule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.schedule.entity.SchPrarticipant;
import com.greedy.moaware.schedule.entity.Schedule;

public interface SchRepository extends JpaRepository<Schedule, Integer> {

//	@EntityGraph(attributePaths= {"schType"})
//	List<Schedule> findBySchAuthor(Integer emp);

//	@EntityGraph(attributePaths = {"schType"})
//    Optional<Schedule> findBySchCodeAndSchAuthor(Integer schCode, Integer empCode);

//	List<Schedule> findBySchAuthor(Emp employee);
//	List<Schedule> findBySchAuthorAndSchPrarticipant(Emp author, Emp participant);
	
	List<Schedule> findBySchAuthor(Emp employee);
	
	Optional<Schedule> findBySchCodeAndSchAuthor(Integer schCode, Emp employee);
	
}
