package com.risk.riskmanage.datamanage.vo;

import java.util.List;

import com.risk.riskmanage.datamanage.model.Field;

/**
 * 字段枚举包装类
 * 主要用于决策选项中,选择字段要加载字段对应的枚举值(如果字段是枚举的话)
 */
public class FieldEnumVo {

	private Field field;
	
	private List<String> enums;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public List<String> getEnums() {
		return enums;
	}

	public void setEnums(List<String> enums) {
		this.enums = enums;
	}
}
