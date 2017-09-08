package com.monda.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class SpringBootDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		String filename = "aaaaa.png";
		int pos = filename.lastIndexOf(".");
		String substring = filename.substring(pos + 1);
		System.out.printf(substring);
	}

}
