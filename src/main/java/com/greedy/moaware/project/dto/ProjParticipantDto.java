package com.greedy.moaware.project.dto;

import lombok.Data;

@Data
public class ProjParticipantDto {
	
	private ProjParticipantPkDto projMember;
	private ProjEmpDto emp;
	
	//참여자 등록 때문에 추가 
    public Integer getProjMember() {
        return projMember.getProjMember();
    }

}
