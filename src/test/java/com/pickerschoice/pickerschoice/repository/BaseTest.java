package com.pickerschoice.pickerschoice.repository;

import org.testcontainers.containers.MySQLContainer;

public class BaseTest {
	@SuppressWarnings("rawtypes")
	static MySQLContainer mySqlContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
			.withDatabaseName("pickerschoice_db")
			.withUsername("testUser")
			.withPassword("pass")
			.withReuse(true);
	
	static {
		mySqlContainer.start();
	}
}
