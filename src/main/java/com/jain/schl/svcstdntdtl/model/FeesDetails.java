package com.jain.schl.svcstdntdtl.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document("FeesDetails")
public class FeesDetails{
	@Id
	private String id;	
    public String studentId;
    public String name;
    public String stdClass;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm a z")
	public Date createdDate;
    public String userId;
    public String token;
    public int totalAmout;
    public String financialYear;
    public String hardCopyNo;
    public ArrayList<Fees> feesDetails;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStdClass() {
		return stdClass;
	}

	public void setStdClass(String stdClass) {
		this.stdClass = stdClass;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(int totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getHardCopyNo() {
		return hardCopyNo;
	}

	public void setHardCopyNo(String hardCopyNo) {
		this.hardCopyNo = hardCopyNo;
	}

	public ArrayList<Fees> getFeesDetails() {
		return feesDetails;
	}

	public void setFeesDetails(ArrayList<Fees> feesDetails) {
		this.feesDetails = feesDetails;
	}

	@Override
	public String toString() {
		return "FeesDetails [id=" + id + ", studentId=" + studentId + ", name=" + name + ", stdClass=" + stdClass
				+ ", createdDate=" + createdDate + ", userId=" + userId + ", token=" + token + ", totalAmout="
				+ totalAmout + ", financialYear=" + financialYear + ", hardCopyNo=" + hardCopyNo + ", feesDetails="
				+ feesDetails + "]";
	}
    
    
}
