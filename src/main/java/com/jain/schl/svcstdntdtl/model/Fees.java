package com.jain.schl.svcstdntdtl.model;


public class Fees{
    public String feesFor;
    public int amount;
    public int count;
    
	public String getFeesFor() {
		return feesFor.toUpperCase();
	}

	public void setFeesFor(String feesFor) {
		this.feesFor = feesFor.toUpperCase();
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Fees [feesFor=" + feesFor + ", amount=" + amount + ", count=" + count + "]";
	}
    
}
