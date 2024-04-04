package com.greedy.moaware.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SCHEDULE_CATEGORY")
@SequenceGenerator(name="SCH_CATEGORY_SEQ_GENERATOR", sequenceName="SEQ_SCH_CATEGORY_CODE", initialValue=1, allocationSize=1)
public class SchCategory {

	@Id
	@Column(name="SCH_CATEGORY_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SCH_CATEGORY_SEQ_GENERATOR")
	private Integer schCategoryCode;
	
	@Column(name="SCH_CATEGORY_NAME")
	private String schCategoryName;
	
}