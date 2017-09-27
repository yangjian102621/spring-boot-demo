package com.monda.demo;

import com.monda.demo.util.HttpUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void getJson() throws IOException {

        String apiUrl = "http://image.so.com/j?src=tab_www";
        HashMap<String, Object> params = new HashMap<>();
        params.put("q", "妹子");
        params.put("sn", 1);
        params.put("pn", 15);
        Map json = HttpUtils.getJson(apiUrl, params);
        System.out.println(json.get("list"));
    }

    @Test
    public void download() throws IOException {
        String url = "http://img2.duitang.com/uploads/item/201304/29/20130429065533_tWVfN.jpeg";
        System.out.println(HttpUtils.download(url, "/javaweb/123.jpeg"));
    }
}
