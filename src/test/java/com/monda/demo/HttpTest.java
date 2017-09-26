package com.monda.demo;

import com.monda.demo.util.HttpUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by yangjian on 2017/9/26.
 */
public class HttpTest {

    @Test
    public void post() throws IOException {

        System.out.println(HttpUtils.post("https://jr.dayi35.com", null));

    }

    @Test
    public void httpBuildQuery() {
        String url = "http://www.baidu.com?id=1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "xiaoming");
        map.put("sex", 'F');
        map.put("address", "dongguan");
        System.out.println(HttpUtils.httpBuildQuery(url, map));
    }

    @Test
    public void get() throws IOException {

        System.out.println(HttpUtils.get("http://www.r9it.com", null));
    }
}
