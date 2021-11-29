package com.risk.riskmanage.system.model;

public class Pager {

	private int rows = 10;
	private int totalResult;
	private int page;

	private String sortField;
	private String order;

	public Pager() {
	}
	
	public Pager(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return (totalResult + rows - 1) / rows;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentResult() {
		if (page == 0) return 0;
		else return (page - 1) * rows;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPageCount() {
		int pagecount = 0;
		if (totalResult % rows == 0) {
			pagecount = totalResult / rows;
		} else {
			pagecount = totalResult / rows + 1;
		}
		return pagecount;
	}

}
