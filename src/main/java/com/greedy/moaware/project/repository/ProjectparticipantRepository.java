package com.greedy.moaware.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.ProjParticipant;
import com.greedy.moaware.project.entity.ProjParticipantPk;

public interface ProjectparticipantRepository extends JpaRepository<ProjParticipant, ProjParticipantPk>{

	List<ProjParticipant> findByProjCodeProjCode(Integer projCode);

	ProjParticipant findByProjCodeProjCodeAndProjCodeProjMember(int i, Integer projMember);

}
