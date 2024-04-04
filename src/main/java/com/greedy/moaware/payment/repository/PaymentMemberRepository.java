package com.greedy.moaware.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.payment.entity.PaymentMember;
import com.greedy.moaware.payment.entity.PaymentMemberPk;

public interface PaymentMemberRepository extends JpaRepository<PaymentMember, PaymentMemberPk>{

	List<PaymentMember> findByPaymentMemberPkEmpCode(Integer empCode);

	List<PaymentMember> findByPaymentMemberPkEmpCodeAndPayType(Integer empCode, String payType);

	PaymentMember findByPaymentMemberPkEmpCodeAndPaymentMemberPkPayCode(Integer empCode, Integer payCode);




	
	
}
