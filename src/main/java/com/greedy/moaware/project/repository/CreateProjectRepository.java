package com.greedy.moaware.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.project.entity.CreateProject;
import com.greedy.moaware.project.entity.ProjParticipantPk;

public interface CreateProjectRepository extends JpaRepository<CreateProject, Integer>{
	
//	@Query(value="SELECT * FROM PROJECT JOIN")
//	Page<CreateProject> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, Integer emp, String projStatus);
	
	@Query(value="SELECT p.PROJ_CODE, p.PROJ_NAME, p.PROJ_CONTENT, p.PROJ_START_DATE, p.PROJ_END_DATE, p.PROJ_STATUS, p.PROJ_AUTHOR, COUNT(pj.PROJ_MEMBER) AS PARTICIPANT_COUNT "
			+ "FROM PROJECT p "
			+ "JOIN PROJ_PRATICIPANT pj ON p.PROJ_CODE = pj.PROJ_CODE "
			+ "WHERE pj.PROJ_MEMBER = :empCode" + " AND p.PROJ_STATUS = :projStatus "
			+ "GROUP BY p.PROJ_CODE, p.PROJ_NAME, p.PROJ_CONTENT, p.PROJ_START_DATE, p.PROJ_END_DATE, p.PROJ_STATUS, p.PROJ_AUTHOR "
			+ "ORDER BY p.PROJ_CODE DESC"
			, nativeQuery = true)
	Page<CreateProject> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, @Param("empCode")Integer empCode, @Param("projStatus") String projStatus);
	
	@Query("SELECT p.projCode FROM CreateProject p ORDER BY p.projCode DESC")
	CreateProject findByProjCode(Integer projCode);
	//다시보냄2트
	
	@Query(value="SELECT p.PROJ_CODE, p.PROJ_NAME, p.PROJ_CONTENT, p.PROJ_START_DATE, p.PROJ_END_DATE, p.PROJ_STATUS, p.PROJ_AUTHOR, COUNT(pj.PROJ_MEMBER) AS PARTICIPANT_COUNT "
			+ "FROM PROJECT p "
			+ "JOIN PROJ_PRATICIPANT pj ON p.PROJ_CODE = pj.PROJ_CODE "
			+ "WHERE p.PROJ_STATUS = :projStatus "
			+ "GROUP BY p.PROJ_CODE, p.PROJ_NAME, p.PROJ_CONTENT, p.PROJ_START_DATE, p.PROJ_END_DATE, p.PROJ_STATUS, p.PROJ_AUTHOR "
			+ "ORDER BY p.PROJ_CODE DESC"
			, nativeQuery = true)
	Page<CreateProject> findByProjStatus(Pageable pageable, @Param("projStatus") String string);
}
