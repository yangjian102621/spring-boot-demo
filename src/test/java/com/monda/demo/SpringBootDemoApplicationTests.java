package com.monda.demo;

import com.monda.demo.util.QiNiuUploadUtils;
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
	private QiNiuUploadUtils qiNiuUploadUtils;


	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		String filename = "jpg|gif|png";
		List<String> list = Arrays.asList(filename.split("\\|"));
		System.out.println(list);
	}

	/**
	 * 七牛获取文件列表
     */
	@Test
	public void fileList() throws QiniuException {

		qiNiuUploadUtils.getFileList("", "", 15);
	}


}
