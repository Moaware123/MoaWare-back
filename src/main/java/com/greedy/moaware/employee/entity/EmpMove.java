//package com.greedy.moaware.employee.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//@Entity
//@Table(name="EMP_MOVE")
//public class EmpMove {
//
//
//	
//@ManyToOne
//@EmbeddedId
//@JoinColumn(name="JOB_CODE", referencedColumnName ="JOB_CODE", insertable = true, updatable = true)
//private Job job;
//
//@ManyToOne
//@EmbeddedId
//@JoinColumn(name="DEPT_CODE", referencedColumnName ="DEPT_CODE", insertable = true, updatable = true)
//private Dept dept;
//
//@ManyToOne
//@JoinColumn(name="EMP_CODE", referencedColumnName ="EMP_CODE", insertable = true, updatable = true)
//@JoinColumn(name="EMP_CODE")
//private Emp emp; 
//
//
//@Column(name="HISTORY_CODE")
//private Integer historyCode;
//
//@Column(name="HISTORY_TYPE")
//private String historyType;
//
//@Column(name="HISTORY_START_DATE")
//private Date historyStartDate;
//
//@Column(name="HISTORY_END_DATE")
//private Date historyEndDate;
//
//
//
//}
