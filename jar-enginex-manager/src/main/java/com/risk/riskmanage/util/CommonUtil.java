package com.risk.riskmanage.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	// MD5简单加密
	public static String MD5Purity(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			plainText = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return plainText;
	}

	public static String Md5Sixteen(String plainText) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString().substring(8, 24);
			System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
			System.out.println("md5 32bit: " + buf.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 生成4位随机数验证码
	public static int getCode() {
		return (int) (Math.random() * 9000 + 1000);
	}

	/**
	 * Boolean 类型转换string
	 * 
	 * @param param
	 * @return
	 */
	public static String booleanToString(Boolean param) {
		if (null != param) {
			return param ? "1" : "0";
		} else {
			return "0";
		}
	}
	
	/**
	 * 获取当前日期几年前的日期
	 * @param date 日期
	 * @param year 年
	 * @return
	 */
	public static Date getBeforeDate(Date date,int year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR,year);
		return cal.getTime();
	}

	public static boolean validateNewAdress(String address) {
		if(StringUtils.isBlank(address)) {
			return false;
		}
        String specChar = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-～ - ]";
        String addressChar = "^(.*[\u4e00-\u9fa5]+.*([0-9]|[零一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾])+.*)|(.*([0-9]|[零一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾])+.*[\u4e00-\u9fa5]+.*)$";    
        Pattern pattern = Pattern.compile(specChar);
        Pattern reg = Pattern.compile(addressChar);
        Matcher matcher1 = pattern.matcher(address);
        Matcher matcher2= reg.matcher(address);
        if (matcher1.matches() || !matcher2.matches() || address.length() < 12 || address.length() > 50) {
            return false;
        } else {
            return true;
        }
    }

	/**
	 * 获取一个唯一的序列号，助于排错
	 *
	 * @return 唯一序列号
     */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
 
	/**
	 * 版本号转换成数字
	 *
	 * @author: liuyafei
	 * @date 创建时间：2016年10月10日
	 * @version 1.0
	 * @parameter
	 * @return
	 */
	public static int transVersionToNum(String version) {
		int num = 0;
		String[] msg = version.split("\\.");
		int len = msg.length;
		if (len >= 3) {
			num += Integer.parseInt(msg[2]);
		}
		if (len >= 2) {
			num += Integer.parseInt(msg[1]) * 10;
		}
		if (len >= 1) {
			num += Integer.parseInt(msg[0]) * 100;
		}
		return num;
	}
	
	/**
	 * 生成指定范围内随机数
	 * @param min 最小随机数
	 * @param max 最大随机数
	 * @return 随机数
	 */
	public static int getRandom(int min, int max) {
		Random random = new Random();
		int maxrand = random.nextInt(max);
		int s = maxrand % (max - min + 1) + min;
		return s;
	} 
	
	/**
	 * 概率
	 * @param i 概率值
	 * @return
	 */
	public static boolean rendomChance(int i) {
		Random r = new Random();
		int next = r.nextInt(101);
		return next > 0 && next <= i;
	}
	
	/**
	 * 获取当前倒推X秒的时间
	 *
	 * @param second
	 * @return
	 */
	public static Date getSubSecondDate(int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - second);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		boolean r  = rendomChance(50);
		System.out.println(r);
	}
}
