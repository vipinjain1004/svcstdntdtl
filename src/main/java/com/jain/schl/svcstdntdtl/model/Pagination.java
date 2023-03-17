package com.jain.schl.svcstdntdtl.model;

import lombok.Data;

@Data
public class Pagination {
	private int offset;
	private int limit;
	private long total;
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
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Pagination [offset=" + offset + ", limit=" + limit + ", total=" + total + "]";
	}
	
}
