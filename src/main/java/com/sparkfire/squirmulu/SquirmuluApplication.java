package com.sparkfire.squirmulu;

import com.sparkfire.squirmulu.config.StaticResourceConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.sparkfire.squirmulu.mapper")
@MapperScan("com.sparkfire.squirmulu.dao")
@Import(StaticResourceConfiguration.class)
public class SquirmuluApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquirmuluApplication.class, args);
	}

}
