package com.greedy.moaware.admin.emp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.admin.emp.entity.AdminEmp;

public interface AdminEmpRepository extends JpaRepository<AdminEmp, Integer> {

	
	
	


	
	//전체 조회
	/* 연관 관계가 지연 로딩으로 되어 있을 경우 엔티티를 하나 조회하고 다시 다른 엔티티에 대해서 여러번 조회를 별도로 하게 되는 
	 * N + 1 문제가 발생하게 된다. (성능 이슈) fetch 조인을 사용하게 되면 한 번에 조인해서 결과를 가져오게 된다.
	 * @EntityGraph는 Data JPA에서 fetch 조인은 어노테이션으로 사용할 수 있도록 만들어준 기능이다.*/
	@EntityGraph(attributePaths = {"job", "dept"})
	Page<AdminEmp> findAll(Pageable pageable);
	
	@EntityGraph(attributePaths = {"job", "dept"})
	Optional<AdminEmp> findById(Integer EmpCode);
	
//	@EntityGraph(attributePaths = {"job", "dept"})
//	List<Emp> findByRefDeptCode( Long refDetpCode);
	
	@Query("SELECT e FROM Emp e WHERE e.empName LIKE :empName% "
			+ " AND e.retireYn = 'N'")
Page<AdminEmp> findByEmpName(@Param("empName") String empName, Pageable pageable);

	
	

}

	
	
	
	
	
	
	
	
	
	
	
	
	










