package com.greedy.moaware.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Role;
import com.greedy.moaware.employee.entity.RolePk;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.project.dto.CreateProjectDto;
import com.greedy.moaware.project.dto.ProjEmpDto;
import com.greedy.moaware.project.dto.ProjParticipantDto;
import com.greedy.moaware.project.dto.ProjParticipantPkDto;
import com.greedy.moaware.project.entity.CreateProject;
import com.greedy.moaware.project.entity.ProjParticipant;
import com.greedy.moaware.project.entity.ProjParticipantPk;
import com.greedy.moaware.project.repository.CreateProjectRepository;
import com.greedy.moaware.project.repository.ProjectRepository;
import com.greedy.moaware.project.repository.ProjectparticipantRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {

	private final ModelMapper modelMapper;
	private final ProjectRepository projResitory;
	private final AuthEmpRepository authEmpRepository;
	private final CreateProjectRepository createProjectRepository;
	private final ProjectparticipantRepository projectparticipantRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public ProjectService(ProjectRepository projResitory, ModelMapper modelMapper, AuthEmpRepository authEmpRepository,
			CreateProjectRepository createProjectRepository, EntityManager entityManager,
			ProjectparticipantRepository projectparticipantRepository) {
		this.modelMapper = modelMapper;
		this.projResitory = projResitory;
		this.authEmpRepository = authEmpRepository;
		this.createProjectRepository = createProjectRepository;
		this.projectparticipantRepository = projectparticipantRepository;
		this.entityManager = entityManager;
	}

	public Page<CreateProjectDto> selectMyProgressProj(Integer empCode, int page) {

		log.info("[ProjectService] selectMyproject start ===========================");
		log.info("[ProjectService] empCode : {}", empCode);

		AuthEmp emp = authEmpRepository.findById(empCode).orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		AuthEmpDto projEmp = new AuthEmpDto();
		projEmp.setEmpCode(empCode);
		
		Pageable pageable = PageRequest.of(page - 1, 10);
		List<Role> roleList = emp.getRoleList();
		for (Role role : roleList) {
		    RolePk rolePk = role.getRole();
		    Integer authCode = rolePk.getAuthCode();

		    if (authCode == 1) {
		    	Page<CreateProject> projList = createProjectRepository.findByProjStatus(pageable, "진행중");		    	
		    	Page<CreateProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, CreateProjectDto.class));
		    	
				projDtoList.forEach(dto -> {
					List<ProjParticipant> participants = projectparticipantRepository.findByProjCodeProjCode(dto.getProjCode());
					List<ProjParticipantDto> participantDtos = participants.stream().map(participant -> {
						ProjParticipantDto participantDto = new ProjParticipantDto();
						ProjParticipantPkDto projParticipantPkDto = new ProjParticipantPkDto();
						projParticipantPkDto.setProjCode(participant.getProjCode().getProjCode());
						projParticipantPkDto.setProjMember(participant.getProjCode().getProjMember());
						participantDto.setProjMember(projParticipantPkDto);
						participantDto.setEmp(modelMapper.map(participant.getEmp(), ProjEmpDto.class));
						log.info("[ProjectService] participantDto : {}", participantDto);
						return participantDto;
					}).collect(Collectors.toList());
					dto.setProjMember(participantDtos);
				});
				return projDtoList;
		    	
		    } else {
		    	Page<CreateProject> projList = createProjectRepository.findByEmployeeEmpCodeAndProjStatus(pageable,
		    			projEmp.getEmpCode(), "진행중");
		    	Page<CreateProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, CreateProjectDto.class));
		    	
				projDtoList.forEach(dto -> {
					List<ProjParticipant> participants = projectparticipantRepository.findByProjCodeProjCode(dto.getProjCode());
					List<ProjParticipantDto> participantDtos = participants.stream().map(participant -> {
						ProjParticipantDto participantDto = new ProjParticipantDto();
						ProjParticipantPkDto projParticipantPkDto = new ProjParticipantPkDto();
						projParticipantPkDto.setProjCode(participant.getProjCode().getProjCode());
						projParticipantPkDto.setProjMember(participant.getProjCode().getProjMember());
						participantDto.setProjMember(projParticipantPkDto);
						participantDto.setEmp(modelMapper.map(participant.getEmp(), ProjEmpDto.class));
						log.info("[ProjectService] participantDto : {}", participantDto);
						return participantDto;
					}).collect(Collectors.toList());
					dto.setProjMember(participantDtos);
				});
				return projDtoList;
		    }
		    
		}
		return null;
	}

	public Page<CreateProjectDto> selectMyDoneProj(Integer empCode, int page) {

		log.info("[ProjectService] selectMyDoneProj start ===========================");
		log.info("[ProjectService] empCode : {}", empCode);

		AuthEmp emp = authEmpRepository.findById(empCode).orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		AuthEmpDto projEmp = new AuthEmpDto();
		projEmp.setEmpCode(empCode);
		
		Pageable pageable = PageRequest.of(page - 1, 10);
		List<Role> roleList = emp.getRoleList();
		for (Role role : roleList) {
		    RolePk rolePk = role.getRole();
		    Integer authCode = rolePk.getAuthCode();

		    if (authCode == 1) {
		    	Page<CreateProject> projList = createProjectRepository.findByProjStatus(pageable, "완료");		    	
		    	Page<CreateProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, CreateProjectDto.class));
		    	
				projDtoList.forEach(dto -> {
					List<ProjParticipant> participants = projectparticipantRepository.findByProjCodeProjCode(dto.getProjCode());
					List<ProjParticipantDto> participantDtos = participants.stream().map(participant -> {
						ProjParticipantDto participantDto = new ProjParticipantDto();
						ProjParticipantPkDto projParticipantPkDto = new ProjParticipantPkDto();
						projParticipantPkDto.setProjCode(participant.getProjCode().getProjCode());
						projParticipantPkDto.setProjMember(participant.getProjCode().getProjMember());
						participantDto.setProjMember(projParticipantPkDto);
						participantDto.setEmp(modelMapper.map(participant.getEmp(), ProjEmpDto.class));
						log.info("[ProjectService] participantDto : {}", participantDto);
						return participantDto;
					}).collect(Collectors.toList());
					dto.setProjMember(participantDtos);
				});
				return projDtoList;
		    	
		    } else {
		    	Page<CreateProject> projList = createProjectRepository.findByEmployeeEmpCodeAndProjStatus(pageable,
		    			projEmp.getEmpCode(), "완료");
		    	Page<CreateProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, CreateProjectDto.class));
		    	
				projDtoList.forEach(dto -> {
					List<ProjParticipant> participants = projectparticipantRepository.findByProjCodeProjCode(dto.getProjCode());
					List<ProjParticipantDto> participantDtos = participants.stream().map(participant -> {
						ProjParticipantDto participantDto = new ProjParticipantDto();
						ProjParticipantPkDto projParticipantPkDto = new ProjParticipantPkDto();
						projParticipantPkDto.setProjCode(participant.getProjCode().getProjCode());
						projParticipantPkDto.setProjMember(participant.getProjCode().getProjMember());
						participantDto.setProjMember(projParticipantPkDto);
						participantDto.setEmp(modelMapper.map(participant.getEmp(), ProjEmpDto.class));
						log.info("[ProjectService] participantDto : {}", participantDto);
						return participantDto;
					}).collect(Collectors.toList());
					dto.setProjMember(participantDtos);
				});
				return projDtoList;
		    }
		    
		}
		return null;
	}

	@Transactional
	public void createPorj(CreateProjectDto projectDto, AuthEmpDto emp) {

		log.info("프로젝트 생성 서비스 시작------------------------------------------------------------------");

		List<ProjParticipantDto> projMemberDtoList = projectDto.getProjMember();
		List<ProjParticipant> projMemberList = new ArrayList<>();

		for (ProjParticipantDto projParticipantDto : projMemberDtoList) {
			ProjParticipant projParticipant = new ProjParticipant();
			ProjParticipantPk projParticipantPk = new ProjParticipantPk();
			projParticipantPk.setProjCode(0);
			projParticipantPk.setProjMember(projParticipantDto.getProjMember());
			projParticipant.setProjCode(projParticipantPk);

			projMemberList.add(projParticipant);
		}

		// CreateProject 엔티티에 projMemberList 설정
		CreateProject createProject = modelMapper.map(projectDto, CreateProject.class);
		createProject.setProjMember(projMemberList);

		// createProject 저장
		CreateProject savedProject = createProjectRepository.save(createProject);

		// 내 번호만 따로 저장
		ProjParticipant myParticipant = new ProjParticipant();
		ProjParticipantPk myParticipantPk = new ProjParticipantPk();
		myParticipantPk.setProjCode(savedProject.getProjCode());
		myParticipantPk.setProjMember(emp.getEmpCode());
		myParticipant.setProjCode(myParticipantPk);

		projectparticipantRepository.save(myParticipant);

		log.info("[ProjectService] createPorj end ===========================");

	}
	
	
	
	
	@Transactional
	public void deleteProj(CreateProjectDto proj) {
		
		try {
			CreateProject findProj = createProjectRepository.findById(proj.getProjCode())
					.orElseThrow(() -> new IllegalArgumentException("해당 사원의 정보가 없습니다. findProj=" + proj.getProjCode()));			
			
			proj.setProjStatus("삭제");
			
			findProj.update(
							proj.getProjStatus()
							);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		
	}	

}
