package com.risk.riskmanage.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHelp {
	public static int  day=0;
	public static String getNowDate(){
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simple.format(date);
		return s;
	}
	public static String getEndDate(){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, + DataHelp.day);  
        Date monday = c.getTime();
        String s = simple.format(monday);
		return s;
	}
	public static String getNowDateString(){
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = simple.format(date);
		return s;
	}
	public static String getDay(){
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		String s = simple.format(date);
		return s;
	}
	public static void main(String[] args) {
		System.out.println(getNowDate());
		System.out.println(getNowDateString());
	
	}
}
