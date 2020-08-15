package com.login.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.login.password.service.CommonService;

@SpringBootApplication
public class PasswordApplication extends SpringBootServletInitializer implements CommandLineRunner{
	
	@Autowired
	CommonService commonservice;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PasswordApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PasswordApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		commonservice.createFileIfNotExist();
	}
}
