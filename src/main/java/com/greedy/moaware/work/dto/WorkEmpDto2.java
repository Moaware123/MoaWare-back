package com.greedy.moaware.work.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greedy.moaware.employee.dto.JobDto;
import com.greedy.moaware.employee.dto.PdeptDto;
import com.greedy.moaware.employee.entity.Job;
import com.greedy.moaware.employee.entity.Pdept;

import lombok.Data;

@Data
@JsonIgnoreProperties("hibernateLazyInitializer")
public class WorkEmpDto2 {
	
    private Integer empCode;
    private String empName;
    private String phone;
    private String empId;
    private String empPwd;
    private String email;
    private String retireYn;
    private Date hireDate;
    private Date retireDate;
    private String extensionNum;
    private String empSsi;
    private JobDto job;
    private PdeptDto dept;
    private WorkTimeDto work;
//    private Date workDate;
    
}
