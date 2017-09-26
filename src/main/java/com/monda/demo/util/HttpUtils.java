package com.monda.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http 请求工具类
 * Created by yangjian on 17-9-26.
 */
public class HttpUtils {

	/**
	 * 发送 post 请求
	 * @param url 请求路径
	 * @param params 请求参数
	 * @return
	 */
	public String post(String url, Map params) throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> args = new ArrayList<>();
		params.forEach((k,v) -> {
			args.add(new BasicNameValuePair(k.toString(), v.toString()));
		});

		UrlEncodedFormEntity uefEntity;
		uefEntity = new UrlEncodedFormEntity(args, "UTF-8");
		httpPost.setEntity(uefEntity);
		System.out.println("executing request " + httpPost.getURI());
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			System.out.println("--------------------------------------");
			System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
			System.out.println("--------------------------------------");
		}
		response.close();
		httpClient.close();

		return null;
	}

}
