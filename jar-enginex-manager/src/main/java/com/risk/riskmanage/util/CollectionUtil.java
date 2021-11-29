package com.risk.riskmanage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {

	/**
	 * 集合判非空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		if (null == collection || collection.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * map判非空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotNullOrEmpty(Map<?, ?> map) {
		if (null == map || map.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取多个集合并集
	 * @param list
	 * @return
	 */
	public static Set<Object> getUnion(List<List<Object>> list) {
		Set<Object> set = new HashSet<Object>();
		if (list == null) {
			list = new ArrayList<List<Object>>();
		}
		int size = list.size();
		if (size > 1) {
			for (int i = 0; i < size; i++) {
				int j = i + 1;
				if (j < size) {
					list.get(0).removeAll(list.get(j));
					list.get(0).addAll(list.get(j));
					if (i == size - 2) {
						List<Object> resultList = list.get(0);
						for (Object result : resultList) {
							 set.add(result);
							}
						}
					}
				}
			} else {
				// 只有一个集合则直接插入结果
				for (List<Object> subList : list) {
					for (Object result : subList) {
						set.add(result);
						}
					}
				}
		return set;

	}

	/**
	 * 获取多个集合交集
	 * @param list
	 * @return
	 */
	public static Set<Object> getIntersection(List<List<Object>> list) {
		Set<Object> set = new HashSet<Object>();
		int size = list.size();
		if (size > 1) {
			// 集合个数大于1，取交集
			for (int i = 0; i < size; i++) {
				int j = i + 1;
				if (j < size) {
					list.get(0).retainAll(list.get(j));
					if (i == size - 2) {
						List<Object> resultList = list.get(0);
						for (Object result : resultList) {
							set.add(result);
						}
					}
				}
			}
		} else {
			// 只有一个集合则不取交集
			for (List<Object> subList : list) {
				for (Object result : subList) {
					set.add(result);
				}
			}
		}

		return set;

	}
}
