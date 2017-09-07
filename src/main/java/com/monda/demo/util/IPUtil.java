package com.monda.demo.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP地址获取工具
 * @author yangjian
 * @since 2017/08/01
 */
public class IPUtil {

    public static final String PATTERN_IP = "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$";

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        ip = getTrueIp(ip);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            ip = getTrueIp(ip);
        }
        if(ip==null && StringUtils.equalsIgnoreCase("localhost",request.getServerName())){
            ip="127.0.0.1"; //本地运行时IP为NULL,报空指针
        }
        return ip;
    }

    /**
     * 取真实客户端IP，过滤代理IP
     *
     * @param ip IP
     * @return 真实客户端IP
     */
    private static String getTrueIp(String ip) {
        if(StringUtils.isNotBlank(ip)) {
            for (String anIp : StringUtils.split(ip, ",")) {
                anIp = StringUtils.trim(anIp);
                if (isIp(anIp) && !StringUtils.startsWith(anIp, "10.") && !StringUtils.startsWith(anIp, "172.16")) {
                    return anIp;
                }
            }
        }
        return null;
    }

    /**
     * 判断IP是否合法
     *
     * @param ip IP
     * @return 合法返回true, 否则返回false
     */
    private static boolean isIp(String ip) {
        if(StringUtils.isNotBlank(ip)) {
            Pattern p = Pattern.compile(PATTERN_IP, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(ip);
            return m.matches();
        }
        return false;
    }
    
    /**
     * 从ip的字符串形式得到字节数组形式
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        //if(ip==null){ip="127.0.0.1";}
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    
    /**
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串
     * @param s 原始字符串
     * @param srcEncoding 源编码方式
     * @param destEncoding 目标编码方式
     * @return 转换编码后的字符串，失败返回原始字符串
     */
    public static String getString(String s, String srcEncoding, String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
    
    /**
     * 根据某种编码方式将字节数组转换成字符串
     * @param b 字节数组
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, String encoding) {
        try {
            return new String(b, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b);
        }
    }
    
    /**
     * 根据某种编码方式将字节数组转换成字符串
     * @param b 字节数组
     * @param offset 要转换的起始位置
     * @param len 要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }
    
    /**
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');   
        sb.append(ip[1] & 0xFF);
        sb.append('.');   
        sb.append(ip[2] & 0xFF);
        sb.append('.');   
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

//    /**
//     * 通过ip获取城市信息
//     * @param ip
//     * @return
//     */
//    public static String getAddressByIp(String ip) {
//        if(StringUtils.isBlank(ip)) return "";
//
//        String ip138IdCardApiUrl = "http://ip138.com/ips138.asp";
//        String checkResult = HttpUtils.get(ip138IdCardApiUrl, ImmutableMap.of("ip", ip, "action", "2"));
//
//        if(StringUtils.isNotBlank(checkResult)) {
//            Pattern pattern = Pattern.compile("<ul class=\"ul1\"><li>本站数据：(.*?)</li>");
//            Matcher matcher = pattern.matcher(checkResult);
//            if (matcher.find()) {
//                return matcher.group(1).split(" ")[0];
//            }
//        }
//
//        return "";
//    }
}
