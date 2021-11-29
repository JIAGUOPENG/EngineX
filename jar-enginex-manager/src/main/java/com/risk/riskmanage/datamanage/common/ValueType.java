package com.risk.riskmanage.datamanage.common;

public enum ValueType {

	// 待选:0, 数值型:1, 字符型:2, 枚举型:3, 小数型:4, 数组型:5
	Unknown(0), Num(1), Char(2), Enum(3), Dec(4), Array(5);

	public final int value;

	private ValueType(int value) {
		this.value = value;
	}

	public int getValue(){
		return value;
	}
}
