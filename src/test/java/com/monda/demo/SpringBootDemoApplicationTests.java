package com.monda.demo;

import com.monda.demo.util.qiniu.UploadUtils;
import com.qiniu.common.QiniuException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class SpringBootDemoApplicationTests {


	@Autowired
	private UploadUtils uploadUtils;


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
