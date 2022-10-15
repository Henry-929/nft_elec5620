package com.nft.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class StringUtil {
    /**
     * 生成随机字符串
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * MD5加密
     */
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static Integer changeString(String string){
        if (string != null && !StringUtils.isBlank(string)) {
            return Integer.valueOf(string);
        }else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.md5("123" + "oppo"));
    }

}
