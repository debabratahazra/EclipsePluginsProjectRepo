package com.zealcore.se.core.importer.lttng;

public class LttProcessState {

	// PROCESS STATE EVENT
	public void processStateEvent(LttEvent event) {

		TraceState traceState = TraceState.getTraceState();
		int processPId = Integer.parseInt(event.getFields().get("pid"));
		int parentPId = Integer.parseInt(event.getFields().get("parent_pid"));
		String processName = event.getFields().get("name").toString();
		int targetPId = Integer.parseInt(event.getFields().get("tgid"));
		int type = Integer.parseInt(event.getFields().get("type"));

		LttProcess parentProcess = findProcess(traceState, event.getCpu(),
				parentPId);
		if (parentProcess != null) {
			parentProcess.setTargetPId(processPId);
		}

		LttProcess process = findProcess(traceState, event.getCpu(), processPId);
		if (processPId == 0) {

			LttProcess runningProcess = traceState.getRunningProcess(event
					.getCpu());
			if (runningProcess != null && runningProcess.getProcessId() == 0) {

				runningProcess.setParentPId(parentPId);
				runningProcess.setTargetPId(targetPId);
				runningProcess.setProcessName(processName);
				runningProcess.setType(type);
			}
			if (process != null) {

				process.setParentPId(parentPId);
				process.setTargetPId(targetPId);
				process.setProcessName(processName);
				process.setType(type);
			}
		} else {

			if (process == null) {

				parentProcess = findProcess(traceState, event.getCpu(),
						parentPId);

				process = createProcess(traceState, parentProcess, event
						.getCpu(), processPId, targetPId, processName,
						(parentProcess != null ? parentProcess
								.getProcessTimeStamp() : null));

				if (type == LttProcessType.LTT_STATE_KERNEL_THREAD) {

					process.setType(LttProcessType.LTT_STATE_KERNEL_THREAD);
					process
							.setExecutionMode(LttExecutionMode.LTT_STATE_MODE_UNKNOWN);
					process
							.setProcessStatus(LttProcessStatus.LTT_STATE_UNNAMED);
				} else {

					process.setType(LttProcessType.LTT_STATE_USER_THREAD);
					process
							.setExecutionMode(LttExecutionMode.LTT_STATE_MODE_UNKNOWN);
					process
							.setProcessStatus(LttProcessStatus.LTT_STATE_UNNAMED);
				}
			} else {

				process.setParentPId(parentPId);
				process.setTargetPId(targetPId);
				process.setProcessName(processName);
				process.setType(type);
			}
		}
	}

	// TASK SWITCH(SCHEDULE CHANGE) EVENT
	public void schedChangeEvent(LttEvent event) {

		int pCPU = event.getCpu();
		int pIdOut = Integer.parseInt(event.getFields().get("prev_pid"));
		int pIdIn = Integer.parseInt(event.getFields().get("next_pid"));
		int stateOut = Integer.parseInt(event.getFields().get("prev_state"));

		TraceState traceState = TraceState.getTraceState();

		LttProcess process = traceState.getRunningProcess(pCPU);
		LttProcess parentProcess = getParentProcess(traceState);

		if (process != null) {

			if (process.getProcessId() == 0
					&& process.getExecutionMode() == LttExecutionMode.LTT_STATE_MODE_UNKNOWN) {

				if (pIdOut == 0) {

					process
							.setExecutionMode(LttExecutionMode.LTT_STATE_SYSCALL);
					process.setProcessStatus(LttProcessStatus.LTT_STATE_WAIT);
					process.setProcessChangeTime(parentProcess
							.getProcessTimeStamp());
					process.setProcessEntryTime(parentProcess
							.getProcessTimeStamp());
				}
			} else {
				if (process.getProcessStatus() == LttProcessStatus.LTT_STATE_EXIT) {
					process.setProcessStatus(LttProcessStatus.LTT_STATE_ZOMBIE);
					process.setProcessChangeTime(parentProcess
							.getProcessTimeStamp());
				} else {
					if (stateOut == 0) {

						process
								.setProcessStatus(LttProcessStatus.LTT_STATE_ZOMBIE);
					} else {

						process
								.setProcessStatus(LttProcessStatus.LTT_STATE_WAIT);
					}
					process.setProcessChangeTime(parentProcess
							.getProcessTimeStamp());
				}

				if (stateOut == 32 || stateOut == 64) {
					// TODO Need to change process state
				}
			}
		}
		process = findCreateProcess(traceState, pCPU, pIdOut, parentProcess
				.getProcessTimeStamp());
		process = findCreateProcess(traceState, pCPU, pIdIn, parentProcess
				.getProcessTimeStamp());
		traceState.setRunningProcess(process, pCPU);
		process.setProcessStatus(LttProcessStatus.LTT_STATE_RUN);
		process.setCpu(pCPU);

		// TODO if User space is associated with process then
		// set the cpu of user space to pCpu.

		process.setProcessChangeTime(parentProcess.getProcessTimeStamp());

	}

	// PROCESS FORK
	public void processFork(LttEvent event) {

		int pCPU = event.getCpu();
		int parentPId = Integer.parseInt(event.getFields().get("parent_pid"));
		int childPId = Integer.parseInt(event.getFields().get("child_pid"));
		int childTargetId = Integer.parseInt(event.getFields()
				.get("child_tgid"));

		TraceState traceState = TraceState.getTraceState();
		LttProcess runningProcess = traceState.getRunningProcess(pCPU);

		LttProcess parentProcess = getParentProcess(traceState, pCPU, parentPId);
		LttTime timestamp = null;
		if (parentProcess == null) {
			timestamp = new LttTime(0, 0);
			parentProcess = createProcess(traceState, null, pCPU, parentPId,
					childTargetId, Integer.toString(parentPId), timestamp);
		} else {

			timestamp = parentProcess.getProcessTimeStamp();
			parentProcess.setTargetPId(childPId);
		}
		LttProcess childProcess = findProcess(traceState, pCPU, childPId);
		if (childProcess == null) {

			childProcess = createProcess(traceState, runningProcess, pCPU,
					childPId, childTargetId, parentProcess.getProcessName(),
					timestamp/* parentProcess.getProcessTimeStamp() */);
		} else {

			childProcess.setProcessId(runningProcess.getProcessId());

		}

		if (childProcess.getProcessName() == LttProcessStatus.LTT_STATE_UNNAMED) {

			childProcess.setProcessName(runningProcess.getProcessName());
		}
	}

	// EXEC EVENT
	public void processExec(LttEvent event) {

		int pCPU = event.getCpu();
		Object execFileName = event.getFields().get("filename");

		TraceState traceState = TraceState.getTraceState();
		LttProcess runningProcess = traceState.getRunningProcess(pCPU);
		runningProcess.setProcessName((String) execFileName);

	}

	// --------------------
	private LttProcess findCreateProcess(TraceState traceState, int pCPU,
			int processId, LttTime processTimeStamp) {

		LttProcess process = findProcess(traceState, pCPU, processId);
		if (process == null) {

			process = createProcess(traceState, null, pCPU, processId, 0,
					Integer.toString(processId), processTimeStamp);
		}

		return process;
	}

	private LttProcess createProcess(TraceState traceState,
			LttProcess parentProcess, int pCPU, int processId, int targetId,
			String processName, LttTime processTimeStamp) {

		LttProcess process = new LttProcess();

		process.setProcessId(processId);
		process.setCpu(pCPU);
		process.setProcessName(processName);
		process.setProcessTimeStamp(processTimeStamp);
		process.setTargetPId(targetId);

		if (parentProcess != null) {

			process.setParentPId(parentProcess.getProcessId());
			process.setProcessStartTime(processTimeStamp);
		} else {

			process.setParentPId(0);
			process.setProcessStartTime(new LttTime(0, 0));
		}
		process.setProcessPushInTime(processTimeStamp);

		process.setProcessEntryTime(processTimeStamp);
		process.setProcessChangeTime(processTimeStamp);

		process.setExecutionMode(LttExecutionMode.LTT_STATE_USER_MODE);
		process.setProcessStatus(LttProcessStatus.LTT_STATE_RUN);

		process.setExecutionMode(LttExecutionMode.LTT_STATE_USER_MODE);
		process.setProcessStatus(LttProcessStatus.LTT_STATE_WAIT_FORK);

		traceState.addProcess(process);
		return process;
	}

	private LttProcess findProcess(TraceState traceState, int pCPU,
			int processId) {

		return traceState.getProcess(processId);
	}

	private LttProcess getParentProcess(TraceState traceState) {
		return new LttProcess();
	}

	private LttProcess getParentProcess(TraceState traceState, int pCpu,
			int processId) {

		return findProcess(traceState, pCpu, processId);
	}
}
