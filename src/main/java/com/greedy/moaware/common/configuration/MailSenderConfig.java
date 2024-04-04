package com.greedy.moaware.common.configuration;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailSenderConfig {
	
	private final JavaMailSender javaMailSender;
	
	public void sendMail(String email, String pwd) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("mukmukbo@gmail.com");
		mailMessage.setSubject("Moa 계정 - 비밀번호 변경안내");
		mailMessage.setText("안녕하세요. 귀하의 임시 비밀번호를 안내드립니다. \n 확인 후 반드시 비밀번호를 재설정하여 주시기 바랍니다.\n" 
				+ pwd);
		
		javaMailSender.send(mailMessage);
	}   
	
}
