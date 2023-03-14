package com.jain.schl.svcstdntdtl.model;

import lombok.Data;

@Data
public class Pagination {
	private int offset;
	private int limit;
	private int total;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Pagination [offset=" + offset + ", limit=" + limit + ", total=" + total + "]";
	}
	
}
