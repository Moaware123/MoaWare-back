package com.greedy.moaware.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.work.entity.WorkPk;
import com.greedy.moaware.work.entity.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, WorkPk>{
	
	//코드번호로 work 테이블의 마지막 행 조회 native쿼리 방법
	@Query(value = "SELECT * FROM WORK w WHERE w.EMP_CODE = :empCode" + " ORDER BY w.WORK_DATE DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	WorkTime findAllByWorkPkEmpCode(@Param("empCode") Integer empCode);
}
