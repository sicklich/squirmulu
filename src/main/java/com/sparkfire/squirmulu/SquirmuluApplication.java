package com.sparkfire.squirmulu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sparkfire.squirmulu.mapper")
@MapperScan("com.sparkfire.squirmulu.dao")
public class SquirmuluApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquirmuluApplication.class, args);
	}

}
