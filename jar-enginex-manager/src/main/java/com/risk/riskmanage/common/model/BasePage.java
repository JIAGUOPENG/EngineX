package com.risk.riskmanage.common.model;

/**
 * 
 * @ClassName: BasePageVo <br/>
 * @Description: 分页公共基础bean. <br/>
 */
public class BasePage {
	
	/**
	 * 当前页数
	 */
	private int page;
	
	/**
	 * 每页显示的行数
	 */
	private int rows;

	/**
	 * 开始行数
	 */
	private Integer curRow;

	/**
	 * 结束行数
	 */
	private Integer endRow;
	
	/**
	 * 总行数
	 */
	private Integer total;
	
	public BasePage() {

	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
    
	/**
	 * setPagination:(设置当前页面和每页显示行数). <br/>
	 * @author wz
	 * @param page 当前页数
	 * @param rows 每页显示的行数
	 */
	public void setPagination(int page,int rows){
		this.page = page;
		this.rows = rows;
		this.curRow = (page-1)*rows;
		this.endRow = (page)*rows;
	}
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCurRow(Integer curRow) {
		this.curRow = curRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	
}
