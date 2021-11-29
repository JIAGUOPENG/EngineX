package com.risk.riskmanage.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**  
 * @ClassName: StringUtil  
 * @Description: String 工具类
 */
public class StringUtil {

	/**
	 * <p>判断是否是有效的字符串，空字符串为无效字符串</p>
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isValidStr(String str) {
		return str != null && str.trim().length() > 0;
	}

	/**
	 * <p>判断是字符串否是为空，字符串为空，返回  ""，反之返回其字符串本身.</p>
	 * 
	 * <p>if str is null then convret str to "".</p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String convertStrIfNull(String str) {
		return str == null ? SysConstant.EMPTY_STRING : str;
	}
	
	/**
	 * <p>根据字符串转换为布尔值.</p>
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean getStrToBoolean(String str) {
		return isValidStr(str) ? str.toLowerCase().trim().equals(SysConstant.TRUE) : false;
	}
	
	/**
	 * <p>根据字符串转换为 整型（int）并返回;转换失败，则返回0.</p>
	 * 
	 * <p>convert str value to int. if fail,then return 0.</p>
	 * 
	 * @param str
	 * @return int
	 */
	public static int getStrToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * <p>根据字符串转换为 整型（int）并返回;转换失败，则返回 指定的值.</p>
	 * 
	 * <p>convert str value to int. if fail,then return defaultvalue.</p>
	 * 
	 * @param str
	 * @param defaultValue
	 * @return int
	 */
	public static int getStrToInt(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * <p>根据字符串转换为long.</p>
	 * 
	 * @param str
	 * @return long
	 */
	public static long getStrTolong(String str) {
		long result = 0;
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = Long.parseLong(str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>根据字符串转换为double.</p>
	 * 
	 * <p>convert String to double</p>
	 * 
	 * @param str
	 * @return double
	 */
	public static double getStrTodouble(String str) {
		double result = 0;
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = Double.parseDouble(str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>根据字符串转换为BigDecimal.</p>
	 * 
	 * <p>convert String object to BigDecimal</p>
	 * 
	 * @param str
	 * @return BigDecimal
	 */
	public static BigDecimal getStrToBigDecimal(String str) {
		BigDecimal result = new BigDecimal(0);
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = new BigDecimal(str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>根据字符串转换为Integer.</p>
	 * 
	 * <p>convert String to Integer.</p>
	 * 
	 * @param str
	 * @return Integer
	 */
	public static Integer getStrToInteger(String str) {
		Integer result = new Integer(0);
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = Integer.valueOf(str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>根据字符串转换为Long.</p>
	 * 
	 * <p>convert String to Long.</p>
	 * 
	 * @param str
	 * @return Long
	 */
	public static Long getStrToLong(String str) {
		Long result = new Long(0);
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = Long.valueOf(str.trim());
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>根据字符串转换为Double.</p>
	 * 
	 * <p>convert String to Double</p>
	 * 
	 * @param str
	 * @return Double
	 */
	public static Double getStrToDouble(String str) {
		Double result = new Double(0);
		if (!isValidStr(str)) {
			return result;
		}
		try {
			result = Double.valueOf(str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>根据数组转换为字符串，用","拼接.</p>
	 * <p>例：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;String[] strArray = new String[]{"How","do","you","do"};</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;拼接后字符串样例：How,do,you,do</p>
	 * 
	 * <p>convert Object array to String use ",".</p>
	 * 
	 * @param Object[]
	 * @return String
	 */
	public static String getArrToStr(Object[] obj) {
		if (obj == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		if (obj.length > 0) {
			buffer.append(obj[0]);
		}

		for (int m = 1; m < obj.length; m++) {
			buffer.append(SysConstant.COMMA).append(obj[m]);
		}

		return buffer.toString();
	}
	
	/**
	 * <p>去掉重复数据(1,2,3,2,4 => 1,2,3,4)</p>
	 * 
	 * @param metadata
	 * @param tagStr
	 * @return String
	 */
	public static String removeEqualStr(String metadata, String tagStr) {
		if (!StringUtil.isValidStr(metadata)) {
			return SysConstant.EMPTY_STRING;
		}
		Set<String> set = new HashSet<String>();
		String[] arr = metadata.split(tagStr);
		for (String temp : arr) {
			if (StringUtil.isValidStr(temp)) {
				set.add(temp);
			}
		}
		Iterator<String> it = set.iterator();
		StringBuffer returnMetadata = new StringBuffer();
		while (it.hasNext()) {
			returnMetadata.append(it.next() + tagStr);
		}
		return returnMetadata.toString().substring(0,returnMetadata.length() - 1);
	}

	/**
	 * <p>查询是否有重复数据</p>
	 * 
	 * @param strArr
	 * @param str
	 * @param tagStr
	 * @return boolean
	 */
	public static boolean hasEqualStr(String strArr, String str, String tagStr) {
		boolean bool = false;
		if (StringUtil.isValidStr(strArr)) {
			String[] arr = strArr.split(tagStr);
			for (String temp : arr) {
				if (temp.equals(str)) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}
	
	/**
	 * <p>根据字符串将其转换编码为UTF-8的字符串.</p>
	 * 
	 * <p>convert type to utf-8</p>
	 * 
	 * @param str
	 * @return utf-8 string
	 */
	public static String toUtf8String(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
    
    /**
     * <p>根据字符串指定的位置更新字符串内容</p>
     * 
     * @param formString  被更新字符串
     * @param updateIndex 选择更新位数
     * @param updateValue 更新为值
     * 
     * @return String
     */
    public static String formatIntegrity(String formatString, int updateIndex, char updateValue) {
        if (!isValidStr(formatString)) {
            return formatString;
        }
        if (updateIndex < 1) {
            return formatString;
        }
        if (updateIndex > formatString.length()) {
            return formatString;
        }
        char[] formatStringChar = formatString.toCharArray();
        formatStringChar[updateIndex] = updateValue;
            
        return String.valueOf(formatStringChar);
    }

    /**
     * <p>转换特殊字符</p>
     * 
     * @param str 含有特殊字符的字符串
     * 
     * @return String
     */
    public static String converSpecialChar(String str) {
        if (!isValidStr(str)) {
            return str;
        }
        str = str.trim();
        if (str.indexOf("\\") >= 0) {
            str = str.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
        }
        if (str.indexOf("'") >= 0) {
            str = str.replaceAll("'", "\\\\'");
        }
        if (str.indexOf("\"") >= 0) {
            str = str.replaceAll("\"", "\\\\\"");
        }
        if (str.indexOf("%") >= 0) {
            str = str.replaceAll("%", "\\\\%");
        }
        return str;
    }
    
    /**
     * <p>获取字符串字节长度（包含中文和中文符号）</p>
     * 
     * @param str 含有中文和中文符号的字符串
     * 
     * @return int
     */
    public static int  getLength(String str){
    	return str.replaceAll("[\u4E00-\u9FA5\u3000-\u303F\uFF00-\uFFEF]", "rr").length();
    }
    
    /**
     * <p>此排序方法仅应用于数字类型的string数字排序</p>
     * 
     * @param arrays 有long类型的数字组成的String 数组
     * @return 排序后的数组
     */
    public  static String[]  sortArrays(String[] arrays){
    	 for(int i = 0; i < arrays.length - 1; i++) {
    		 String temp ="";
    		 for(int j = 0; j < arrays.length - i - 1; j++) {
    			 if(StringUtil.getStrTolong(arrays[j]) >StringUtil.getStrTolong(arrays[j +1])) {
    				 temp = arrays[j + 1];
    				 arrays[j + 1] = arrays[j];
    				 arrays[j] = temp;
    			 }
    		 }
    	 }
    	 return arrays;
    }
    
    /**
     * <p>此排序方法仅应用于浮点类型的string数字排序</p>
     * 
     * @param arrays 有浮点类型的数字组成的String 数组
     * @return 排序后的数组
     */
	public static String[] sortArraystoBigDecimal(String[] arrays) {
		for (int i = 0; i < arrays.length - 1; i++) {
			String temp = "";
			for (int j = 0; j < arrays.length - i - 1; j++) {
				if (StringUtil.getStrToBigDecimal(arrays[j]).compareTo(
						StringUtil.getStrToBigDecimal(arrays[j + 1])) == 1) {
					temp = arrays[j + 1];
					arrays[j + 1] = arrays[j];
					arrays[j] = temp;
				}
			}
		}
		return arrays;
	}
	
	/**
	 *<p>判断字符串是否为NUll，或者为空字符</p>
     * @param str 字符串
     * @return boolean
	 * */
	public static boolean isBlank(String str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}
	
	
	/**
	 * <p>将字符拆分，并放入list集合</p>
	 * @param str 字符串
	 * @return list
	 * */
	public static List<Long>  toLongList(String str){
		List<Long> idList = new ArrayList<Long>();
		if(!isBlank(str)){
			String[] idsArray = str.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				idList.add(Long.parseLong(idsArray[i]));
			}
		}
		return idList;
	}
	
	
   public static String listToString(List list, char separator) {   
    	return org.apache.commons.lang.StringUtils.join(list.toArray(),separator);
    }   

    public static void main(String[] args) {
    	//String result = "aaaa　，，aaa";
    	//System.out.println(getLength(result));
    	
    	String[] strArray = new String[]{"How","d$o","you","do"};
    	strArray = new String[]{"5.36","5.003","1.36","9.87","3.33333379"};
    	//strArray = StringUtil.sortArrays(strArray);
    	strArray = StringUtil.sortArraystoBigDecimal(strArray);
    	String str = StringUtil.getArrToStr(strArray);
    	System.out.println(str);
    	
    	//System.out.println(StringUtil.formatIntegrity("dkkemnkn", 2, '6'));
    	
	}
   
   
}
