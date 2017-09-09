package com.monda.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class SpringBootDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		String filename = "jpg|gif|png";
		List<String> list = Arrays.asList(filename.split("\\|"));
		System.out.println(list);
	}

}
