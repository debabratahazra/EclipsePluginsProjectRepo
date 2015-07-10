package com.zealcore.se.core.importer.lttng;

import java.util.HashMap;
import java.util.Map;

public class TraceState {

	private Map<Integer, LttProcess> runningProcessSet;

	private Map<Integer, LttProcess> processList;

	private static TraceState traceState;

	private TraceState() {

		runningProcessSet = new HashMap<Integer, LttProcess>();
		processList = new HashMap<Integer, LttProcess>();
		LttProcess process = new LttProcess();
		process.setProcessId(0);
		process.setExecutionMode(LttExecutionMode.LTT_STATE_MODE_UNKNOWN);
		process.setProcessStatus(LttProcessStatus.LTT_STATE_RUN);
		process.setProcessName("default");
		processList.put(0, process);
	}

	public static synchronized TraceState getTraceState() {

		if (traceState == null) {

			traceState = new TraceState();
		}
		return traceState;
	}

	public Map<Integer, LttProcess> getProcessList() {
		return processList;
	}

	public void setProcessList(Map<Integer, LttProcess> processList) {
		this.processList = processList;
	}

	public void setRunningProcess(LttProcess process, int cpu) {

		this.runningProcessSet.put(cpu, process);
	}

	public LttProcess getRunningProcess(int cpu) {

		LttProcess runningProcess = runningProcessSet.get(cpu);

		if (runningProcess == null) {

			runningProcess = new LttProcess();
			runningProcess.setProcessId(0);
			runningProcess
					.setExecutionMode(LttExecutionMode.LTT_STATE_MODE_UNKNOWN);
			runningProcess.setProcessStatus(LttProcessStatus.LTT_STATE_RUN);
			runningProcess.setCpu(cpu);
			runningProcess.setProcessName("default");
			runningProcessSet.put(cpu, runningProcess);
		}
		return runningProcess;
	}

	public void addProcess(LttProcess process) {

		int pId = process.getProcessId();

		processList.put(pId, process);

	}

	public LttProcess getProcess(int pId) {

		return processList.get(pId);
	}

	public void removeProcess(int pCPU, int processId, LttProcess process) {

		processList.remove(processId);
	}

	public void clean() {
		runningProcessSet.clear();
		processList.clear();
	}
}
