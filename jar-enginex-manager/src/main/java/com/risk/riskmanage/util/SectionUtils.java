package com.risk.riskmanage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionUtils {
	public final static float max_float = Integer.MAX_VALUE;
	public final static float min_float = Integer.MIN_VALUE;

	private static void SecSort(Section[] secs, int low, int high) {
		int i = low;
		int j = high;
		Section temp = secs[low];
		while (i < j) {
			while (i < j && temp.x < secs[j].x)
				j--;
			if (i < j) {
				secs[i] = secs[j];
				i++;
			}
			while (i < j && temp.x > secs[i].x)
				i++;
			if (i < j) {
				secs[j] = secs[i];
				j--;
			}
		}
		secs[i] = temp;

		if (low < i)
			SecSort(secs, low, i - 1);
		if (high > j)
			SecSort(secs, j + 1, high);
	}

	private static boolean inSection(Section[] secs, Section s, int offset) {
		boolean ret = false;
		for (int k = 0; k < secs.length; k++) {
			if (secs[k].x == secs[k].y || s.x == s.y)
				continue;

			if (secs[k].x == s.x || secs[k].y == s.y)
				continue;

			if (secs[k].x <= s.x && secs[k].y >= s.y && offset != k) {
				ret = true;
				break;
			} else if (secs[k].x <= s.x && s.x < secs[k].y && offset != k) {
				ret = true;
				break;
			}
		}
		return ret;

	}

	public static boolean checkSectionCoincide(List<String> section_list) {
		boolean ret = false;
		String temp = "";
		float min = 0;
		float max = 0;

		Section[] sections = new Section[section_list.size()];
		for (int i = 0; i < section_list.size(); i++) {
			temp = section_list.get(i);
			String chars[] = temp.split(",");
			if (chars.length == 2) {
				if ("(".equals(chars[0]) || "[".equals(chars[0])) {
					min = min_float;
				} else {
					if (chars[0].indexOf("(") == 0) {
						temp = chars[0].replace("(", "");
						min = parseFloat(temp);
					} else if (chars[0].indexOf("[") == 0) {
						temp = chars[0].replace("[", "");
						min = parseFloat(temp);
					}
				}
				if (")".equals(chars[1]) || "]".equals(chars[1])) {
					max = max_float;
				} else {
					if (chars[1].indexOf(")") > 0) {
						temp = chars[1].replace(")", "");
						max = parseFloat(temp);
					} else if (chars[1].indexOf("]") > 0) {
						temp = chars[1].replace("]", "");
						max = parseFloat(temp);
					}
				}
				sections[i] = new Section(min, max);
			} else {
				sections[i] = new Section(parseFloat(temp), parseFloat(temp));
			}
		}

		int n = sections.length;
		SecSort(sections, 0, n - 1);
		for (int i = 0; i < n; i++) {
			ret = inSection(sections, sections[i], i);
			if (ret)
				break;
		}
		return ret;
	}

	public static boolean checkSectionValid(List<String> section_list) {
		boolean ret_min = false;
		boolean ret_max = false;
		boolean ret_lr = false;
		String temp = "";

		HashMap<String, Integer> left_hp = new HashMap<String, Integer>();
		HashMap<String, Integer> right_hp = new HashMap<String, Integer>();
		HashMap<String, String> collection = new HashMap<String, String>();

		for (int i = 0; i < section_list.size(); i++) {
			temp = section_list.get(i);
			String chars[] = temp.split(",");

			if (chars.length == 2) {
				if ("(".equals(chars[0]) || "[".equals(chars[0])) {
					if ("(".equals(chars[0]))
						ret_min = true;
				} else {
					if (chars[0].indexOf("(") == 0) {
						temp = chars[0].replace("(", "");
						collection.put(temp, temp);
						if (left_hp.get(chars[0]) != null) {
							left_hp.put(chars[0], left_hp.get(chars[0]) + 1);
						} else {
							left_hp.put(chars[0], 1);
						}
					} else if (chars[0].indexOf("[") == 0) {
						temp = chars[0].replace("[", "");
						collection.put(temp, temp);
						if (left_hp.get(chars[0]) != null) {
							left_hp.put(chars[0], left_hp.get(chars[0]) + 1);
						} else {
							left_hp.put(chars[0], 1);
						}
					}
				}

				if (")".equals(chars[1]) || "]".equals(chars[1])) {
					if (")".equals(chars[1]))
						ret_max = true;
				} else {
					if (chars[1].indexOf(")") > 0) {
						temp = chars[1].replace(")", "");
						collection.put(temp, temp);

						if (right_hp.get(chars[1]) != null) {
							right_hp.put(chars[1], right_hp.get(chars[1]) + 1);
						} else {
							right_hp.put(chars[1], 1);
						}
					} else if (chars[1].indexOf("]") > 0) {
						temp = chars[1].replace("]", "");
						collection.put(temp, temp);
						if (right_hp.get(chars[1]) != null) {
							right_hp.put(chars[1], right_hp.get(chars[1]) + 1);
						} else {
							right_hp.put(chars[1], 1);
						}
					}
				}
			} else {
				if (right_hp.get(String.format("%s]", temp)) != null) {
					right_hp.put(String.format("%s]", temp),
							right_hp.get(String.format("%s]", temp)) + 1);
				} else {
					right_hp.put(String.format("%s]", temp), 1);
				}

				if (left_hp.get(String.format("[%s", temp)) != null) {
					left_hp.put(String.format("[%s", temp),
							left_hp.get(String.format("[%s", temp)) + 1);
				} else {
					left_hp.put(String.format("[%s", temp), 1);
				}
			}
		}
		
		int ct1 = 0;
		int ct2 = 0;
		for (Map.Entry<String, Integer> entry : left_hp.entrySet()) {
			String key = entry.getKey();
			ct1++;
			if (key.indexOf("(") == 0) {
				key = key.replace("(", "");
				key = String.format("%s]", key);
				if (right_hp.get(key) != null && right_hp.get(key) == 1) {

					ct2++;
				}

			} else if (key.indexOf("[") == 0) {
				key = key.replace("[", "");
				key = String.format("%s)", key);
				if (right_hp.get(key) != null && right_hp.get(key) == 1) {
					ct2++;
				}
			}
		}

		if (ct1 == ct2) {
			ret_lr = true;
		}
		return (ret_min && ret_max && ret_lr);
	}

	public static float parseFloat(String s) {
		float i = 0f;
		try {
			i = Float.parseFloat(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static void main(String[] args) {
		  ArrayList<String> section_list = new ArrayList<String>();  
		  section_list.add("[0,3)");
		  section_list.add("(4,)");
		  section_list.add("(,0.5)");
		  section_list.add("[3,4]");
		  System.out.println("区间是否完整:"+checkSectionValid(section_list));  //检测表达式区间完整度
		  System.out.println("区间是否重叠:"+checkSectionCoincide(section_list));  //检测表达式区间完整度
	}
}