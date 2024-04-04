package com.greedy.moaware.work.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.NotLogin;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.dto.WorkEmpDto2;
import com.greedy.moaware.work.dto.WorkPkDto;
import com.greedy.moaware.work.dto.WorkTimeDto;
import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.entity.WorkEmp2;
import com.greedy.moaware.work.entity.WorkPk;
import com.greedy.moaware.work.entity.WorkTime;
import com.greedy.moaware.work.repository.WorkEmpRepositoty;
import com.greedy.moaware.work.repository.WorkRepository;
import com.greedy.moaware.work.repository.WorkTimeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkService {

	private final WorkRepository workRepository;
	private final WorkTimeRepository workTimeRepository;
	private ModelMapper modelMapper;
	private final AuthEmpRepository authEmpRepository;
	private final WorkEmpRepositoty workEmpRepository;

	public WorkService(WorkRepository workRepository, ModelMapper modelMapper, EmpRepository empRepository,
			AuthEmpRepository authEmpRepository, WorkTimeRepository workTimeRepository, WorkEmpRepositoty workEmpRepository) {
		this.modelMapper = modelMapper;
		this.workRepository = workRepository;
		this.authEmpRepository = authEmpRepository;
		this.workTimeRepository = workTimeRepository;
		this.workEmpRepository= workEmpRepository;
	}

	/* 자기 근태 현황 조회 */
	public Page<WorkDto> selectMyWorkList(Integer empCode, Date workDate, int page) {

		log.info("[WorkService] selectMyWorkList start ======================= ");

		log.info("[WorkService] empCode : {}", empCode);

		Optional<AuthEmp> empOptional = authEmpRepository.findById(empCode);
		AuthEmp emp = empOptional.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));

		WorkPk workPk = new WorkPk();
		workPk.setEmpCode(empCode);

		// 해당 월의 첫 번째 날짜를 구합니다.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(workDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = calendar.getTime();

		// 해당 월의 마지막 날짜를 구합니다.
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workPk.workDate").descending());
		Page<Work> workList = workRepository.findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(workPk.getEmpCode(),
				startDate, endDate, pageable);
		// 리스트 엔티티의 지연로딩을 강제 초기화
		workList.forEach(work -> Hibernate.initialize(work.getAuth().getRoleList()));

		Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));

		log.info("[WorkService] workDtoList.getContent() : {}", workDtoList.getContent());

		log.info("[WorkService] selectMyWorkList end ======================= ");

		return workDtoList;
	}

	/* 사원 번호 + 월별 근태 현황 조회 */
	public Page<WorkDto> selectWorkList(Integer empCode, Date workDate, int page) {

		Optional<AuthEmp> empOptional = authEmpRepository.findById(empCode);
		AuthEmp emp = empOptional.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));

		// 해당 월의 첫 번째 날짜를 구합니다.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(workDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = calendar.getTime();

		// 1일에 한달을 더해서 1일을 빼준다.
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();

		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workPk.workDate").descending());
		Page<Work> workList = workRepository.findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(empCode, startDate, endDate,
				pageable);
		Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));

		return workDtoList;
	}

	/* 시간 insert */
	@Transactional
	public void insertStart(WorkTimeDto workTimeDto) {

		log.info("[WorkService] insertStart start ======================= ");
		log.info("[WorkService] workTimeDto : {}", workTimeDto);
		log.info("[WorkService] empCode : {}", workTimeDto.getWorkPk().getEmpCode());
		log.info("[WorkService] authEmpRepository : {}",
				authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode()));
		log.info("[WorkService] workTimeDto.workpK.workDate : {}", workTimeDto.getWorkPk().getWorkDate());
		// findById 는 반환 타입이 optional<T>로 지정 되어있다.

		Optional<AuthEmp> authEmpOptional = authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode());

		// 값이 있는지 확인하고 있으면
		if (authEmpOptional.isPresent()) {
			AuthEmp authEmp = authEmpOptional.get();

			LocalDateTime nowTime = LocalDateTime.now();

			// LocalDateTime을 ZonedDateTime으로 변환 (시간대 정보 추가)
			ZonedDateTime zonedDateTime = nowTime.atZone(ZoneId.systemDefault());

			// ZonedDateTime을 Instant로 변환
			Instant instant = zonedDateTime.toInstant();

			// Instant를 Date로 변환
			Date date = Date.from(instant);

			// 현재 년도와 월과 일은 고정으로 하고 출근 시간만 고정
			LocalDateTime fixedStartTime = nowTime.withHour(9).withMinute(0).withSecond(0).withNano(0);
			LocalDateTime fixedAfterTime = nowTime.withHour(13).withMinute(0).withSecond(0).withNano(0);

			// 이전 시간
			if (nowTime.isBefore(fixedStartTime)) {
				workTimeDto.setWorkStatus("정상출근");

				// 이후 시간
			} else if (nowTime.isAfter(fixedAfterTime)) {
				workTimeDto.setWorkStatus("지각");
			} else {
				// 사이 시간
				workTimeDto.setWorkStatus("지각");
			}

			workTimeDto.setWorkTime(date);
			workTimeDto.setQuitTime(null);
			workTimeRepository.save(modelMapper.map(workTimeDto, WorkTime.class));
		} else {
			throw new NotLogin("로그인이 되어 있지 않습니다.");
		}

	}

	/* 퇴근 시간 입력 */
	@Transactional
	public void quitTime(WorkTimeDto workTimeDto) {

		Optional<AuthEmp> authEmpOptional = authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode());

		log.info("[WorkService] quitTimeauthEmpOptional : {}",
				authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode()));

		if (authEmpOptional.isPresent()) {
			WorkTime lastWork = workTimeRepository.findAllByWorkPkEmpCode(workTimeDto.getWorkPk().getEmpCode());

			if (lastWork != null) {
				lastWork.setQuitTime(workTimeDto.getQuitTime());
			}

		}

	}

	
	@Transactional
	public void statusUpdate(WorkDto workDto) {
		
		try {
		    Work findWork = workRepository.findByWorkPkEmpCodeAndWorkPkWorkDate(workDto.getWorkPk().getEmpCode(), workDto.getWorkPk().getWorkDate())
		            .orElseThrow(() -> new IllegalArgumentException("해당 사원의 정보가 없습니다. findWork=" + workDto.getWorkPk().getEmpCode()));

		    findWork.update(workDto.getWorkStatus());
		    
		    
		    //트라이 캐치로 익셉션 발생시 밑으로 이동
		} catch (IllegalArgumentException e) {
		    log.info("dkdkdkdk");
		    WorkTimeDto workTimeDto = new WorkTimeDto();
		    WorkPkDto workPkDto = new WorkPkDto();

		    workPkDto.setEmpCode(workDto.getWorkPk().getEmpCode());
		    workPkDto.setWorkDate(workDto.getWorkPk().getWorkDate());

		    workTimeDto.setWorkPk(workPkDto);

		    workTimeDto.setWorkStatus(workDto.getWorkStatus());

		    workTimeRepository.save(modelMapper.map(workTimeDto, WorkTime.class));
		}
	}

	public Page<WorkEmpDto2> empWorkList1(Date workDate, int page) {
//		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("WORK_TIME").descending());
		Pageable pageable = PageRequest.of(page - 1, 10);
		Page<WorkEmp2> workList = workEmpRepository.findByWorkWorkPkWorkDate(workDate, pageable);
		Page<WorkEmpDto2> workDtoList = workList.map(work -> modelMapper.map(work, WorkEmpDto2.class));

		return workDtoList;
	}

	public Page<WorkEmpDto2> findByEmpName(String name, int page, Date workDate) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("EMP_CODE").descending());
		Page<WorkEmp2> workList = workEmpRepository.findByEmpNameAndWorkWorkPkWorkDate(name, workDate, pageable);
		Page<WorkEmpDto2> workDtoList = workList.map(work -> modelMapper.map(work, WorkEmpDto2.class));

		
		return workDtoList;
	}

	public Page<WorkDto> empNameWorkList(String name, Date workDate2, Date workDate, int page) {
		
		log.info("[WorkController] : 이름 조회 투  서비스  시작~~~~~~~~~~~~~{}", name);
		log.info("[WorkController] : 이름 조회 투  서비스ㅜ   시작~~~~~~~~~~~~~{}", workDate2);
		log.info("[WorkController] : 이름 조회 투  서비스     시작~~~~~~~~~~~~~{}", workDate);
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workPk.workDate").descending());
		Page<Work> workList = workRepository.findAllByEmpEmpNameAndWorkPkWorkDateBetween(name,
				workDate2, workDate, pageable);
		
		workList.forEach(work -> Hibernate.initialize(work.getAuth().getRoleList()));
		
		Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));
		
		log.info("[WorkService] workDtoList.getContent() : {}", workDtoList.getContent());
		
		log.info("[WorkService] selectMyWorkList end ======================= ");
		
		return workDtoList;
	}
	
	public Optional<WorkDto> selectMywork(Integer empCode, Date workDate) {
	    AuthEmp emp = authEmpRepository.findByEmpCode(empCode)
	            .orElseThrow(() -> new IllegalArgumentException("해당코드의 기록이 없습니다. emp=" + empCode));

	    Optional<Work> workOptional = workRepository.findByWorkPkEmpCodeAndWorkPkWorkDate(emp.getEmpCode(), workDate);
	    if (workOptional.isEmpty()) {
	        return Optional.empty();
	    }
	    
	    Work work = workOptional.get();
	    WorkDto workDto = modelMapper.map(work, WorkDto.class);
	    return Optional.ofNullable(workDto);
	}



	

//	public Page<WorkDto> selectDateAllList(Date parsedDate, int page) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/* 이름 + 날짜로 근무 조회 */

//    public Page<Work> findWorksByNameAndDate(String empName, LocalDate workDate, int page) {
//        
//    	Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
//    	Page<Emp> employee = empRepository.findByEmpName(empName, );
//
//        return workRepository.findByWorkPkEmpCodeAndWorkPkWorkDate(((Emp) employee).getEmpCode(), workDate, page);
//    }
}
