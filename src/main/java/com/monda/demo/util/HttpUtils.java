package com.monda.demo.util;

import org.apache.commons.lang.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
	public static String post(String url, Map params) throws IOException {

		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> args = new ArrayList<>();
		if (null != params) {
			params.forEach((k,v) -> {
				args.add(new BasicNameValuePair(k.toString(), v.toString()));
			});
		}

		UrlEncodedFormEntity uefEntity;
		uefEntity = new UrlEncodedFormEntity(args, "UTF-8");
		httpPost.setEntity(uefEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			result =  EntityUtils.toString(entity, "UTF-8");
		}

		response.close();
		httpClient.close();

		return result;
	}

	/**
	 * 发送 get 请求
	 * @param url 请求路径
	 * @param params 请求参数
	 * @return
	 */
	public static String get(String url, Map params) throws IOException {

		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(httpBuildQuery(url, params));
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			result =  EntityUtils.toString(entity, "UTF-8");
		}

		response.close();
		httpClient.close();

		return result;
	}

	/**
	 * 组装 Url 和参数
	 * @param url
	 * @param params
     * @return
     */
	public static String httpBuildQuery(String url, Map params) {
		if (null == params) {
			return url;
		}
		String newUrl;
		if (url.indexOf("?") == -1) {
			newUrl = url+"?";
		} else {
			newUrl = url+"&";
		}

		ArrayList<String> list = new ArrayList<>();
		params.forEach((k, v) -> {
			list.add(k+"="+v);
		});
		return newUrl + StringUtils.join(list, "&");
	}

}
