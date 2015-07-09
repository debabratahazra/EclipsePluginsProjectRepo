package com.odcgroup.ds.t24.packager.data;

import java.util.ArrayList;
import java.util.List;

public class Cd {

	private String type = "";
	private Long number;
	private List<Record> records = new ArrayList<Record>();
	private String priority = "";
	private String restoredFrom = "";
	private String reference = "";
	private String problem = "";
	private String symptom = "";
	private String fixDescription = "";

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getRestoredFrom() {
		return restoredFrom;
	}
	public void setRestoredFrom(String restoredFrom) {
		this.restoredFrom = restoredFrom;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getFixDescription() {
		return fixDescription;
	}
	public void setFixDescription(String fixDescription) {
		this.fixDescription = fixDescription;
	}
	
}
