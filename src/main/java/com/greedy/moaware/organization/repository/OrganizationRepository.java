package com.greedy.moaware.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
				
	/* 조직도 상위 부서 검색 */
	@EntityGraph(attributePaths= {"orgEmp", "orgEmp.job"})
	List<Organization> findAll();
	
	/* 조직도 하위 부서 및 직원 검색 */
	@EntityGraph(attributePaths= {"orgEmp", "orgEmp.job"})
	List<Organization> findByRefDeptCode(Integer refDeptCode);

	
	/* 조직도 이름,직급,직책으로 검색 */
	@Query(value="SELECT DISTINCT o FROM Organization o JOIN fetch o.highDept h JOIN fetch o.orgEmp e JOIN fetch e.job j "
			+ "WHERE o.deptName like %:search% or e.empName like %:search% or j.jobName like %:search%"
			)
	List<Organization> findBySearch(@Param("search") String search);



}		
