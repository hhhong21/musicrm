package com._1.musicrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com._1.musicrm")
public class MusicrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicrmApplication.class, args);
	}

}
