package com.risk.riskmanage.datamanage.common;

public enum Status {

	enable(1), disable(0), delete(-1), yes(1), no(0);
	public final int value;

	private Status(int value) {
		this.value = value;
	}
}
