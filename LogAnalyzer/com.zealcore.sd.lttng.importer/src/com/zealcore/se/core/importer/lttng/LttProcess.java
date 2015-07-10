package com.zealcore.se.core.importer.lttng;

public class LttProcess {

	private int processId;

	private int targetPId;

	private int parentPId;

	private int cpu;

	private String processName;

	private String processStatus;

	private int executionMode;

	private int type;

	private LttTime processChangeTime;

	private LttTime processEntryTime;

	private LttTime processTimeStamp;

	private LttTime processPushInTime;

	private LttTime processStartTime;

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public void setExecutionMode(int lttStateModeUnknown) {
		this.executionMode = lttStateModeUnknown;
	}

	public int getExecutionMode() {
		return executionMode;
	}

	public void setProcessChangeTime(LttTime processChangeTime) {
		this.processChangeTime = processChangeTime;
	}

	public LttTime getProcessChangeTime() {
		return processChangeTime;
	}

	public void setProcessEntryTime(LttTime processEntryTime) {
		this.processEntryTime = processEntryTime;
	}

	public LttTime getProcessEntryTime() {
		return processEntryTime;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getCpu() {
		return cpu;
	}

	public void setProcessTimeStamp(LttTime processTimeStamp) {
		this.processTimeStamp = processTimeStamp;
	}

	public LttTime getProcessTimeStamp() {
		return processTimeStamp;
	}

	public void setProcessPushInTime(LttTime processPushInTime) {
		this.processPushInTime = processPushInTime;
	}

	public LttTime getProcessPushInTime() {
		return processPushInTime;
	}

	public void setProcessStartTime(LttTime processStartTime) {
		this.processStartTime = processStartTime;
	}

	public LttTime getProcessStartTime() {
		return processStartTime;
	}

	public void setTargetPId(int targetPId) {
		this.targetPId = targetPId;
	}

	public int getTargetPId() {
		return targetPId;
	}

	public void setParentPId(int parentPId) {
		this.parentPId = parentPId;
	}

	public int getParentPId() {
		return parentPId;
	}

	public void setType(int type) {
		this.type = type;

	}

	public int getType() {
		return type;
	}

}
