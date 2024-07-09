package com.nageoffer.shortlink.admin.tookit;

import java.util.Random;

/**
 * 分组ID随机生成器
 */
public final class RandomStringGenerator {
  
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";  
    private static final int POOL_SIZE = CHAR_POOL.length();  
    private static final Random random = new Random();  
  
    /**  
     * 生成一个指定长度的随机字符串，包含大写字母、小写字母和数字。  
     *  
     * @param length 指定生成的随机字符串的长度。  
     * @return 生成的随机字符串。  
     */  
    public static String generateRandomString(int length) {  
        if (length <= 0) {  
            throw new IllegalArgumentException("Length must be greater than 0");  
        }  
        StringBuilder sb = new StringBuilder(length);  
        for (int i = 0; i < length; i++) {  
            int index = random.nextInt(POOL_SIZE);  
            sb.append(CHAR_POOL.charAt(index));  
        }  
        return sb.toString();  
    }  
  
    // 提供一个特定长度（6位）的随机字符串生成方法  
    public static String generateRandomString() {
        return generateRandomString(6);  
    }
}