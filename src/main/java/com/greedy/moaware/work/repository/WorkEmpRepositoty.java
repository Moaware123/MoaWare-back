package com.greedy.moaware.work.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.work.entity.WorkEmp2;
import com.greedy.moaware.work.entity.WorkPk;

public interface WorkEmpRepositoty extends JpaRepository<WorkEmp2, WorkPk>{

    @Query(value = "SELECT * FROM EMPLOYEE e LEFT JOIN WORK w ON e.EMP_CODE = w.EMP_CODE AND w.WORK_DATE = :workDate",
            nativeQuery = true)
	Page<WorkEmp2> findByWorkWorkPkWorkDate(@Param("workDate") Date workDate, Pageable pageable);
    
    //네이티브 쿼리해서 LIKE는 concat을 주고 와일드사용
//    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.EMP_CODE LIKE :empName%"
//			+ " AND e.RETIRE_YN = 'N'", nativeQuery = true)
//    @Query(value = "SELECT e.*, w.WORK_DATE FROM EMPLOYEE e LEFT JOIN WORK w ON e.EMP_CODE = w.EMP_CODE WHERE e.EMP_NAME LIKE :empName% AND e.RETIRE_YN = 'N'", nativeQuery = true)
//	Page<WorkEmp2> findByEmpName(@Param("empName") String empName, Pageable pageable);
    
//    @Query(value = "SELECT * FROM EMPLOYEE e LEFT JOIN WORK w ON e.EMP_CODE = w.EMP_CODE AND w.WORK_DATE = :workDate WHERE e.EMP_NAME LIKE :empName% AND e.RETIRE_YN = 'N'",
//            nativeQuery = true)
	
    @Query(value = "SELECT * FROM EMPLOYEE e LEFT JOIN WORK w ON e.EMP_CODE = w.EMP_CODE AND w.WORK_DATE = :workDate WHERE e.EMP_NAME LIKE %:empName% AND e.RETIRE_YN = 'N' ORDER BY w.WORK_TIME DESC",
            nativeQuery = true)
    Page<WorkEmp2> findByEmpNameAndWorkWorkPkWorkDate(@Param("empName") String name, Date workDate, Pageable pageable);


}
