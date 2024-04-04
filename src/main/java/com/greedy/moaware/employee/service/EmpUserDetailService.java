package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;

import io.jsonwebtoken.lang.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpUserDetailService implements UserDetailsService{

	private final AuthEmpRepository authEmpRepository;
	private final ModelMapper modelMapper;
	
	public EmpUserDetailService(AuthEmpRepository authEmpRepository, ModelMapper modelMapper) {
		this.authEmpRepository = authEmpRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		log.info("[EmpUserDetailService] loadUserByUsername start ======================================");
		log.info("[CustomUserDetailService] userId : {}", userId);
		
		log.info("[EmpUserDetailService] loadUserByUsername end ======================================");
		return authEmpRepository.findByEmpId(userId)
				.map(user -> addAuthorities(user))
				.orElseThrow(()-> new UserNotFoundException(userId + "를 찾을 수 없습니다. "));
	}
	
	
	private AuthEmpDto addAuthorities(AuthEmp emp) {
	    AuthEmpDto empDto = modelMapper.map(emp, AuthEmpDto.class);
	    
	    /* 권한명 추출 */
	    List<String> roles = emp.getRoleList().stream()
	    		.map(role -> role.getAuth().getAuthTitle()).collect(Collectors.toList());
	    log.info("[EmpUserDetailService] roles : {}", roles);
	    
	    /* SimpleGrantedAuthority 객체로 변환 */
	    List<SimpleGrantedAuthority> authorities = roles.stream()
	            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	    
	    empDto.setAuthorities(authorities);
	    
	    return empDto;
	}

	
	
}
