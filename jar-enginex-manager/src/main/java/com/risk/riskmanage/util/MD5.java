
package com.risk.riskmanage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5 <br/>
 * Description: md5加密工具类. <br/>
 */
public class MD5 {
	/**
	 * 全局数组
	 */
    private static final  String[] DIGITS = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "H", "i", 
            "j", "k", "l", "m", "n", "~", "$", "@", "%", "*", "#", "&", "!" };

    public MD5() {
    }

    /**
     * byteToArrayString:(返回形式为数字跟字符串). <br/>
     * @author wz
     * @param bByte byte
     * @return 返回形式为数字跟字符串
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 32;
        int iD2 = iRet % 32;
        return DIGITS[iD1] + DIGITS[iD2];
    }

    /**
     * byteToNum:(返回形式只为数字). <br/>
     * @param bByte byte
     * @return 返回形式只为数字
     */
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    /**
     * byteToString:(转换字节数组为16进制字串). <br/>
     * @param bByte byte数组
     * @return 返回转换字节数组为16进制字串
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    /**
     * GetMD5Code:(md5加密). <br/>
     * @author wz
     * @param param 需要加密的字段
     * @return 加密后的字段
     */
    public static String GetMD5Code(String param) {
        String resultString = null;
        try {
            resultString = new String(param);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(param.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
	
	
//	public static void main(String[] args) {
//		MD5 getMD5 = new MD5();
//        System.out.println(getMD5.GetMD5Code("123456"));
//	}

}

