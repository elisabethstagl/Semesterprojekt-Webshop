package com.webshop.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/applications-de.properties")
class FhWebshopApplicationTests {

	@Test
	void contextLoads() {
	}

}
