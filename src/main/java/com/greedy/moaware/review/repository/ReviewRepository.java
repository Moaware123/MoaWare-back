package com.greedy.moaware.review.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.review.entity.TaskReview;

public interface ReviewRepository extends JpaRepository<TaskReview, Integer>{

//	@EntityGraph(attributePaths= {"task","emp", "emp.fileCategory.file"})
//	List<TaskReview> findByTaskAndStatus(Task task, String status);

	List<TaskReview> findByTaskAndStatus(Task task, String status);


//	Slice<TaskReview> findByTaskAndStatus(Pageable pageable, Task task, String status);
//	@Query("SELECT * "
//		 + "FROM TaskReview r "
//		 + "JOIN Task t "
//		 + "JOIN ReviewEmp e "
//		 + ""
//		 + "WHERE r.task = :task "
//		 + "AND r.status LIKE %:status%")
//	Slice<TaskReview> findByTaskAndStatusWithContainingStatus(Pageable pageable, @Param("task") Task task, @Param("status") String status);
//

}
