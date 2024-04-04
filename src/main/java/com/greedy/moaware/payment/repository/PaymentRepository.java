package com.greedy.moaware.payment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.payment.entity.PayEmp;
import com.greedy.moaware.payment.entity.Payment;
import com.greedy.moaware.payment.entity.PaymentMember;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	
	
	@EntityGraph(attributePaths= {"form"})
	List<Payment> findAll();

	@EntityGraph(attributePaths= {"form"})
	Page<Payment> findByEmpAndPayStatus(Pageable pageable, PayEmp emp, String payStatus);

	List<Payment> findByEmp(PayEmp emp);

	Page<Payment> findByPaymentMemberInOrderByPayCode(Pageable pageable, List<PaymentMember> paymentMemberList);

	Payment findByPaymentMember(PaymentMember paymentMember);

	Page<Payment> findByPayStatusAndPaymentMemberInOrderByPayCode(Pageable pageable, String payStatus,
			List<PaymentMember> paymentMemberList);







}
