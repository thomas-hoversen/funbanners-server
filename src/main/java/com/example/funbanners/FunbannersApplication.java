package com.example.funbanners;

//import com.example.funbanners.service.FilesDBStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import com.example.funbanners.service.FilesDiskStorage;

import javax.annotation.Resource;

@SpringBootApplication
public class FunbannersApplication {

	// helpful disk save: https://bezkoder.com/spring-boot-upload-multiple-files/
	// helpful db save: https://bezkoder.com/spring-boot-upload-file-database/
	// use curl to save files: curl --form "files=@me.png" http://localhost:8080/upload
	// use curl to save multiple files: curl -F "files=@me.png" -F "files=@me.png" http://localhost:8080/upload
	//https://gentle-refuge-49956.herokuapp.com

	public static void main(String[] args) {
		SpringApplication.run(FunbannersApplication.class, args);
	}

}
