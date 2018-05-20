package com.wtgroup.demo.remotedebug;

import com.wtgroup.demo.remotedebug.service.TestAppRemoteDebug;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);


		TestAppRemoteDebug testAppRemoteDebug = new TestAppRemoteDebug();
		testAppRemoteDebug.run();

	}
}
