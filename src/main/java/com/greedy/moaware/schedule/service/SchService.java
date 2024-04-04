package com.greedy.moaware.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.ScheduleNotFoundException;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.schedule.dto.ScheduleDto;
import com.greedy.moaware.schedule.entity.SchCategory;
import com.greedy.moaware.schedule.entity.SchPrarticipant;
import com.greedy.moaware.schedule.entity.Schedule;
import com.greedy.moaware.schedule.repository.SchCategoryRepository;
import com.greedy.moaware.schedule.repository.SchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchService {

    private final SchRepository schRepository;
    private final SchCategoryRepository schCategoryRepository;
    private final EmpRepository empRepository;
    private ModelMapper modelMapper;
    
    public SchService(SchRepository schRepository, SchCategoryRepository schCategoryRepository, EmpRepository empRepository, ModelMapper modelMapper) {
        this.schRepository = schRepository;
        this.schCategoryRepository = schCategoryRepository;
        this.empRepository = empRepository;
        this.modelMapper = modelMapper;
    }

    /* 전체 캘린더 조회 */
    public List<ScheduleDto> getScheduleListByUser(Integer authEmp) {
    	
        Emp employee = empRepository.findById(authEmp)
                .orElseThrow(() -> new UserNotFoundException(authEmp + "번의 사원을 찾을 둘수 없습니다."));
        
        List<Schedule> schedules = schRepository.findBySchAuthor(employee);
        
        log.info("[SchService] getScheduleListByUser : {}", schedules);
        
        List<ScheduleDto> scheduleDto = schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
        
        return scheduleDto;
    }

    /* 상세 캘린더 조회 */
	public ScheduleDto getScheduleByCodeAndUser(Integer schCode, Integer authEmp) {
		
		Emp employee = empRepository.findById(authEmp)
	            .orElseThrow(() -> new UserNotFoundException(authEmp + "번의 사원을 찾을 수 없습니다."));
		
		Schedule schedule = schRepository.findBySchCodeAndSchAuthor(schCode, employee)
	            .orElseThrow(() -> new ScheduleNotFoundException(schCode + "번의 일정을 찾을 수 없습니다."));

	    log.info("[SchService] getScheduleByCodeAndUser : {}", schedule);

	    return modelMapper.map(schedule, ScheduleDto.class);
	}

	/* 일정 등록 */
	@Transactional
	public void insertSchedule(Integer authEmp, @ModelAttribute ScheduleDto scheduleDto) {
	    Emp employee = empRepository.findById(authEmp)
	            .orElseThrow(() -> new UserNotFoundException(authEmp + "번의 사원을 찾을 수 없습니다."));

	    scheduleDto.setSchAuthor(modelMapper.map(employee, EmpDto.class)); // 사원 정보를 scheduleDto의 schAuthor 필드에 설정

	    log.info("[SchService] insertSchedule : {}", scheduleDto);

	    schRepository.save(modelMapper.map(scheduleDto, Schedule.class));
	}


	/* 일정 참여자 검색 */
	

	/* 일정 삭제 */
	@Transactional
	public void deleteSchedule(Integer schCode, Integer authEmp) {
		
		Emp employee = empRepository.findById(authEmp)
	            .orElseThrow(() -> new UserNotFoundException(authEmp + "번의 사원을 찾을 수 없습니다."));
		
		Schedule schedule = schRepository.findBySchCodeAndSchAuthor(schCode, employee)
	            .orElseThrow(() -> new ScheduleNotFoundException(schCode + "번의 일정을 찾을 수 없습니다."));
		
		schRepository.delete(schedule);
		
		log.info("[SchService] deleteSchedule : {}", schCode);
		
	}
	
	/* 일정 수정 */
	@Transactional
	public void modifySchedule(Integer authEmp, @ModelAttribute ScheduleDto scheduleDto) {

	    log.info("[SchService] modifySchedule : {}", scheduleDto);
		
		Emp employee = empRepository.findById(authEmp)
	            .orElseThrow(() -> new UserNotFoundException(authEmp + "번의 사원을 찾을 수 없습니다."));
	    
	    Schedule sch = schRepository.findById(scheduleDto.getSchCode())
	    		.orElseThrow(() -> new ScheduleNotFoundException(scheduleDto.getSchCode() + "번의 일정을 찾을 수 없습니다."));
	    		
	    sch.setSchName(scheduleDto.getSchName());
	    sch.setSchContent(scheduleDto.getSchContent());
	    sch.setSchDate(scheduleDto.getSchDate());
	    sch.setSchEndDate(scheduleDto.getSchEndDate());
	    sch.setSchType(modelMapper.map(scheduleDto.getSchType(), SchCategory.class));
//	    sch.setSchPrarticipant(modelMapper.map(scheduleDto.getSchPrarticipant(), SchPrarticipant.class));
	    
	    log.info("sch : {}", sch);
	    
//	    schRepository.save(schedule);
//	    schRepository.save(modelMapper.map(scheduleDto, ScheduleDto.class));
	    
	}

}

