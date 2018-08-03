package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mastermind.service.MastermindService;

@SpringBootApplication
public class MastermindApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(MastermindApplication.class, args);
		MastermindService.execute();
	}
}
