package com.greedy.moaware.employee.dto;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
@Data
public class AuthEmpDto implements UserDetails{

	private Integer empCode;
	private String empName;
	private String phone;
	private String empId;
	private String empPwd;
	private String email;
	private String retireYn;
	private Date hireDate;
	private String extensionNum;
	private String empSsi;
	private List<RoleDto> roleList;
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return empPwd;
	}
	@Override
	public String getUsername() {
		return empId;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}