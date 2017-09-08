package com.monda.demo.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * 字符串工具
 * @author yangjian
 * @since 2017/8/1.
 */
public class StringUtils {

    public static String getMd5Code(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("utf-8"));
            return new BigInteger(1, md5.digest()).toString(16);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取密码 MD5 hash 值
     * @param source
     * @param salt
     * @param hashIterations
     * @return
     */
    public static String getPasswordHash(String source, String salt, Integer hashIterations) {
        return new SimpleHash("MD5", source, salt, hashIterations).toHex();
    }

    /**
     * 生成uuid
     * @return UUID
     */
    public static String getUUId() {
        return getMd5Code(UUID.randomUUID().toString());
    }
}
