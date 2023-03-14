package com.jain.schl.svcstdntdtl.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document("StudentDetails")
public class StudentDetails {
	@Id
	private String id;
	private String fName;
	private String mName;
	private String lName;
	private String fatherName;
	private Date dateOfBirth;
	private String stdClass;
	public String getStdClass() {
		return stdClass;
	}
	public void setStdClass(String stdClass) {
		this.stdClass = stdClass;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "StudentDetails [id=" + id + ", fName=" + fName + ", mName=" + mName + ", lName=" + lName
				+ ", fatherName=" + fatherName + ", dateOfBirth=" + dateOfBirth + ", stdClass=" + stdClass + "]";
	}
	

}
