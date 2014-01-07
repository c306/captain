package com.c306.springsecurity.test;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordTest {
	
	public static void main(String[] args) {
		
		StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
		System.out.println(standardPasswordEncoder.encode("admin"));//每次加密后的摘要值都不一样
		System.out.println(standardPasswordEncoder.encode("admin"));//每次加密后的摘要值都不一样
		System.out.println(standardPasswordEncoder.encode("admin"));//每次加密后的摘要值都不一样
		System.out.println(standardPasswordEncoder.encode("admin"));//每次加密后的摘要值都不一样
		System.out.println(standardPasswordEncoder.matches("admin", standardPasswordEncoder.encode("admin")));
		
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(); // default sha-1
		System.out.println(shaPasswordEncoder.encodePassword("admin", "admin"));
		
		System.out.println(shaPasswordEncoder.isPasswordValid("a40546cc4fd6a12572828bb803380888ad1bfdab", "admin", "admin"));

		
		ShaPasswordEncoder shaPasswordEncoder256 = new ShaPasswordEncoder(256); // sha-256
		System.out.println(shaPasswordEncoder256.encodePassword("admin", "admin"));
		
		
	}

}
