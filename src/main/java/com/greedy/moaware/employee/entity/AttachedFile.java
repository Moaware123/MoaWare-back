package com.greedy.moaware.employee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="ATTACHED_FILE")
@SequenceGenerator(name="FILE_SEQ_GENERATOR", sequenceName="SEQ_ATTACHMENT_FILE_CODE", initialValue=1, allocationSize=1)
public class AttachedFile {
	
	@Id
	@Column(name="FILE_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILE_SEQ_GENERATOR")
	private Integer fileCode;
	
	@Column(name="ORIGINAL_FILE_NAME")
	private String originalFileName;
	
	@Column(name="FILE_PATH")
	private String filePath;
	
	@Column(name="SAVED_FILE_NAME")
	private String savedFileName;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="F_CATEGORY_CODE")
	private FileCategory fileCategory;
	

}
