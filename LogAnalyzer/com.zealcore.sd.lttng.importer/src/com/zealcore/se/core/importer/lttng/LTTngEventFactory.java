package com.zealcore.se.core.importer.lttng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.format.FieldDescription;
import com.zealcore.se.core.format.FieldValues;
import com.zealcore.se.core.format.GenericArtifactInfo;
import com.zealcore.se.core.format.GenericEventInfo;
import com.zealcore.se.core.format.ReceiveEventInfo;
import com.zealcore.se.core.format.SendEventInfo;
import com.zealcore.se.core.format.TaskArtifactInfo;
import com.zealcore.se.core.format.TaskSwitchEventInfo;
import com.zealcore.se.core.format.TypeDescription;
import com.zealcore.se.core.format.FieldDescription.FieldType;
import com.zealcore.se.core.format.TaskArtifactInfo.TaskClass;

class LTTngEventFactory {

	private static final List<TypeDescription> TYPE_DESCRIPTIONS = new ArrayList<TypeDescription>();

	private static final String TASK_SWITCH = "sched_schedule";
	private static final String FORK = "fork";
	private static final String PROCESS_FORK = "process_fork";
	private static final String EXIT = "exit";
	private static final String KILL = "kill";
	private static final String SIGNAL = "signal";
	private static final String SEND_SIGNAL = "send_signal";
	private static final String WAKEUP = "wakeup";
	private static final String WAIT = "wait";
	private static final String PROCESS_WAIT = "process_wait";
	private static final String SENDMSG = "sendmsg";
	private static final String SOCKET_SENDMSG = "socket_sendmsg";
	private static final String RECVMSG = "recvmsg";
	private static final String SOCKET_RECVMSG = "socket_recvmsg";
	private static final String MSGCREATE = "msg_create";
	private static final String SEMCREATE = "sem_create";
	private static final String SHMCREATE = "shm_create";
	private static final String SOCKETCREATE = "create";
	private static final String MEMALLOC = "page_alloc";
	private static final String MEMFREE = "page_free";
	private static final String OPEN = "open";
	private static final String CLOSE = "close";
	private static final String READ = "read";
	private static final String PREAD = "pread64";
	private static final String WRITE = "write";
	private static final String PWRITE = "pwrite64";
	private static final String SELECT = "select";
	private static final String EXEC = "exec";
	private static final String IOCTL = "ioctl";
	private static final String POLL = "pollfd";
	private static final String PAGEFAULTENTRY = "page_fault_entry";
	private static final String PAGEFAULTEXIT = "page_fault_exit";
	private static final String SYSCALLENTRY = "sys_call_entry";
	private static final String SYSCALLEXIT = "sys_call_exit";
	private static final String IRQENTRY = "irq_entry";
	private static final String IRQEXIT = "irq_exit";
	private static final String SWAPIN = "swap_in";
	private static final String SWAPOUT = "swap_out";

	private final Map<Integer, GenericArtifactInfo> artifacts = new HashMap<Integer, GenericArtifactInfo>();

	static {

		TYPE_DESCRIPTIONS.add(new SwapEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new CreateProcessFacilityEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new CreateIPCFacilityEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new KillEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SignalEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new WakeUpEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new WaitEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new ReceiveEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SocketSendEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SocketReceiveEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new AllocEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new FreeEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new OpenEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new CloseEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new ReadEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new WriteEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new MessageTypeDescription());
		TYPE_DESCRIPTIONS.add(new ProcessArtifactTypeDescription());
		TYPE_DESCRIPTIONS.add(new SelectEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new ExecEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new PollEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new IoctlEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new PageFaultEntryEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new PageFaultExitEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SyscallEntryEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SyscallExitEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new IrqEntryEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new IrqExitEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SwapInEventTypeDescription());
		TYPE_DESCRIPTIONS.add(new SwapOutEventTypeDescription());
	}

	public static List<TypeDescription> getTypeDescriptions() {
		return TYPE_DESCRIPTIONS;
	}

	public GenericEventInfo getEvent(final LttEvent event) {

		if (event == null) {
			return null;
		}
		
		TraceState traceState = TraceState.getTraceState();

		GenericEventInfo genEvent = null;
		if (TASK_SWITCH.equals(event.getName())) {
			genEvent = getSwapEvent(event, traceState);
		} else if (FORK.equals(event.getName())
				|| PROCESS_FORK.equals(event.getName())) {
			genEvent = getCreateEvent(event, traceState);
		} else if (EXIT.equals(event.getName())) {
			genEvent = getExitEvent(event, traceState);
		} else if (KILL.equals(event.getName())) {
			genEvent = getKillEvent(event, traceState);
		} else if (SIGNAL.equals(event.getName())
				|| SEND_SIGNAL.equals(event.getName())) {
			genEvent = getSignalEvent(event, traceState);
		} else if (WAKEUP.equals(event.getName())) {
			genEvent = getWakeupEvent(event, traceState);
		} else if (WAIT.equals(event.getName())
				|| PROCESS_WAIT.equals(event.getName())) {
			genEvent = getWaitEvent(event, traceState);
		} else if (SENDMSG.equals(event.getName())
				|| SOCKET_SENDMSG.equals(event.getName())) {
			genEvent = getSocketSendEvent(event, traceState);
		} else if (RECVMSG.equals(event.getName())
				|| SOCKET_RECVMSG.equals(event.getName())) {
			genEvent = getSocketRecvEvent(event, traceState);
		} else if (MSGCREATE.equals(event.getName())
				|| SEMCREATE.equals(event.getName())
				|| SHMCREATE.equals(event.getName())) {
			genEvent = getIPCCreateEvent(event, traceState);
		} else if (SOCKETCREATE.equals(event.getName())) {
			genEvent = getSocketCreateEvent(event, traceState);
		} else if (MEMALLOC.equals(event.getName())) {
			genEvent = getAllocEvent(event, traceState);
		} else if (MEMFREE.equals(event.getName())) {
			genEvent = getFreeEvent(event, traceState);
		} else if (OPEN.equals(event.getName())) {
			genEvent = getOpenEvent(event, traceState);
		} else if (CLOSE.equals(event.getName())) {
			genEvent = getCloseEvent(event, traceState);
		} else if (READ.equals(event.getName())
				|| PREAD.equals(event.getName())) {
			genEvent = getReadEvent(event, traceState);
		} else if (WRITE.equals(event.getName())
				|| PWRITE.equals(event.getName())) {
			genEvent = getWriteEvent(event, traceState);
		} else if (SELECT.equals(event.getName())) {
			genEvent = getSelectEvent(event, traceState);
		} else if (EXEC.equals(event.getName())) {
			genEvent = getExecEvent(event, traceState);
		} else if (POLL.equals(event.getName())) {
			genEvent = getPollEvent(event, traceState);
		} else if (IOCTL.equals(event.getName())) {
			genEvent = getIoctlEvent(event, traceState);
		} else if (PAGEFAULTENTRY.equals(event.getName())) {
			genEvent = getPageFaultEntryEvent(event, traceState);
		} else if (PAGEFAULTEXIT.equals(event.getName())) {
			genEvent = getPageFaultExitEvent(event, traceState);
		} else if (SYSCALLENTRY.equals(event.getName())) {
			genEvent = getSyscallEntryEvent(event, traceState);
		} else if (SYSCALLEXIT.equals(event.getName())) {
			genEvent = getSyscallExitEvent(event, traceState);
		} else if (IRQENTRY.equals(event.getName())) {
			genEvent = getIrqEntryEvent(event, traceState);
		} else if (IRQEXIT.equals(event.getName())) {
			genEvent = getIrqExitEvent(event, traceState);
		} else if (SWAPIN.equals(event.getName())) {
			genEvent = getSwapInEvent(event, traceState);
		} else if (SWAPOUT.equals(event.getName())) {
			genEvent = getSwapOutEvent(event, traceState);
		} else {

			// return new GenericEventInfo(0, 0, null);
			// genEvent = getMessageEvent(event);
		}

		return genEvent;
	}

	public Collection<GenericArtifactInfo> getArtifacts() {
		return artifacts.values();
	}

	private GenericEventInfo getSwapEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		int inProcessId = Integer.parseInt(event.getFields().get("next_pid"));
		int outProcessId = Integer.parseInt(event.getFields().get("prev_pid"));

		if (traceState.getProcess(inProcessId) != null) {
			addProcess(inProcessId, traceState.getProcess(inProcessId)
				.getProcessName());
		}

		if (traceState.getProcess(outProcessId) != null) {
			addProcess(outProcessId, traceState.getProcess(outProcessId)
				.getProcessName());
		}

		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);
		GenericEventInfo eventInfo = new TaskSwitchEventInfo(
				event.getOriginalTimestamp(), inProcessId, outProcessId, 0,
				SwapEventTypeDescription.TYPE_ID, values);

		return eventInfo;
	}

	private GenericEventInfo getCreateEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		int parentPId = Integer.parseInt(event.getFields().get("parent_pid"));
		int childPId = Integer.parseInt(event.getFields().get("child_pid"));

		values.addArtifactIdValue(parentPId);
		values.addArtifactIdValue(childPId);

		if (traceState.getProcess(parentPId) != null) {
			addProcess(parentPId, traceState.getProcess(parentPId).getProcessName());
		}
		if (traceState.getProcess(childPId) != null) {
			addProcess(childPId, traceState.getProcess(childPId).getProcessName());
		}

		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		GenericEventInfo eventInfo = new GenericEventInfo(
				event.getOriginalTimestamp(),
				CreateProcessFacilityEventTypeDescription.TYPE_ID, values);

		return eventInfo;
	}

	private GenericEventInfo getIPCCreateEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object created = event.getFields().get("id");
		Object flags = event.getFields().get("flags");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				(traceState.getRunningProcess(event.getCpu()).getProcessName()));
		addProcess(Integer.parseInt(created.toString()),
				"IPC" + created.toString());
		values.addArtifactIdValue(traceState.getRunningProcess(event.getCpu())
				.getProcessId());
		values.addArtifactIdValue(Integer.parseInt(created.toString()));

		values.addStringValue(flags.toString());

		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		GenericEventInfo eventInfo = new GenericEventInfo(
				event.getOriginalTimestamp(),
				CreateIPCFacilityEventTypeDescription.TYPE_ID, values);

		return eventInfo;
	}

	private GenericEventInfo getSocketCreateEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object socketAddress = event.getFields().get("sock");
		Object family = event.getFields().get("family");
		Object type = event.getFields().get("type");
		Object protocol = event.getFields().get("protocol");
		Object fd = event.getFields().get("fd");

		values.addArtifactIdValue(traceState.getRunningProcess(event.getCpu())
				.getProcessId());
		values.addArtifactIdValue(Integer.parseInt(socketAddress.toString()));

		values.addStringValue(family.toString());
		values.addStringValue(type.toString());
		values.addStringValue(protocol.toString());
		values.addIntValue(Integer.parseInt(fd.toString()));
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				(traceState.getRunningProcess(event.getCpu()).getProcessName()));

		addProcess(Integer.parseInt(socketAddress.toString()), "SOCK"
				+ socketAddress.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		GenericEventInfo eventInfo = new GenericEventInfo(
				event.getOriginalTimestamp(),
				CreateSocketFacilityEventTypeDescription.TYPE_ID, values);

		return eventInfo;
	}

	private GenericEventInfo getExitEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object killed = event.getFields().get("pid");

		values.addArtifactIdValue(traceState.getRunningProcess(event.getCpu())
				.getProcessId());
		values.addArtifactIdValue(Integer.parseInt(killed.toString()));

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				(traceState.getRunningProcess(event.getCpu()).getProcessName()));
		if (traceState.getProcess(Integer.parseInt((String) killed)) != null) {
			addProcess(Integer.parseInt(killed.toString()),
				traceState.getProcess(Integer.parseInt((String) killed))
						.getProcessName());
		}
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		GenericEventInfo eventInfo = new GenericEventInfo(
				event.getOriginalTimestamp(), KillEventTypeDescription.TYPE_ID,
				values);

		return eventInfo;
	}

	private GenericEventInfo getKillEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object killer = event.getFields().get("pid");
		Object killed = event.getFields().get("target_pid");

		if (traceState.getProcess(Integer.parseInt((String) killer)) != null){
			addProcess(Integer.parseInt(killer.toString()),
				traceState.getProcess(Integer.parseInt((String) killer))
						.getProcessName());
		}
		if (traceState.getProcess(Integer.parseInt((String) killed)) != null) {
			addProcess(Integer.parseInt(killed.toString()),
				traceState.getProcess(Integer.parseInt((String) killed))
						.getProcessName());
		}
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		GenericEventInfo eventInfo = new GenericEventInfo(
				event.getOriginalTimestamp(), KillEventTypeDescription.TYPE_ID,
				values);

		return eventInfo;
	}

	private GenericEventInfo getSignalEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object addressee = event.getFields().get("pid");
		Object signal = event.getFields().get("signal");

		values.addIntValue(Integer.parseInt(signal.toString()));
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				(traceState.getRunningProcess(event.getCpu()).getProcessName()));
		if (traceState
				.getProcess(Integer.parseInt((String) addressee)) != null) {
			addProcess(Integer.parseInt(addressee.toString()), traceState
				.getProcess(Integer.parseInt((String) addressee))
				.getProcessName());
		}
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new SendEventInfo(event.getOriginalTimestamp(), traceState
				.getRunningProcess(event.getCpu()).getProcessId(),
				Integer.parseInt(addressee.toString()),
				event.getOriginalTimestamp(), signal.toString(),
				SignalEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getWakeupEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object addressee = event.getFields().get("pid");
		Object state = event.getFields().get("state");

		values.addIntValue(Integer.parseInt(state.toString()));
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		if (traceState
				.getProcess(Integer.parseInt((String) addressee)) != null) {
			addProcess(Integer.parseInt(addressee.toString()), traceState
				.getProcess(Integer.parseInt((String) addressee))
				.getProcessName());
		}
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new SendEventInfo(event.getOriginalTimestamp(), traceState
				.getRunningProcess(event.getCpu()).getProcessId(),
				Integer.parseInt(addressee.toString()),
				event.getOriginalTimestamp(), state.toString(),
				WakeUpEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getWaitEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object waitforprocess = event.getFields().get("pid");

		values.addArtifactIdValue(traceState.getRunningProcess(event.getCpu())
				.getProcessId());
		values.addArtifactIdValue(Integer.parseInt(waitforprocess.toString()));

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		if (traceState
				.getProcess(Integer.parseInt((String) waitforprocess)) != null) {
			addProcess(Integer.parseInt(waitforprocess.toString()), traceState
					.getProcess(Integer.parseInt((String) waitforprocess))
					.getProcessName());
			
		}
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				WaitEventTypeDescription.TYPE_ID, values);

	}

	private GenericEventInfo getSocketSendEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		/*
		 * Object addressee = content.getField("sock").getValue(); Object family
		 * = allFields[1].getValue(); Object type = allFields[2].getValue();
		 * Object protocol = allFields[3].getValue(); Object size =
		 * content.getField("size").getValue(); Object message =
		 * content.getField("msg").getValue();
		 */
		Object addressee = event.getFields().get("sock");
		Object size = event.getFields().get("size");
		Object message = event.getFields().get("msg");
		values.addIntValue(Integer.parseInt(size.toString()));
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		addProcess(Integer.parseInt(addressee.toString()), "Socket "
				+ addressee.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new SendEventInfo(event.getOriginalTimestamp(), traceState
				.getRunningProcess(event.getCpu()).getProcessId(),
				Integer.parseInt(addressee.toString()), -1, message.toString(),
				SocketSendEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSocketRecvEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/* LttngEventContent content = event.getContent(); */
		/*
		 * Object socket = allFields[0].getValue(); Object family =
		 * allFields[1].getValue(); Object type = allFields[2].getValue();
		 * Object protocol = allFields[3].getValue(); Object size =
		 * allFields[4].getValue();
		 */
		Object socket = event.getFields().get("sock");
		Object size = event.getFields().get("size");
		Object message = event.getFields().get("msg");

		values.addIntValue(Integer.parseInt(size.toString()));
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		addProcess(Integer.parseInt(socket.toString()),
				"Socket " + socket.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new ReceiveEventInfo(event.getOriginalTimestamp(), traceState
				.getRunningProcess(event.getCpu()).getProcessId(),
				Integer.parseInt(socket.toString()), -1, message.toString(),
				SocketReceiveEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getAllocEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();
		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object order = event.getFields().get("order");
		Object address = event.getFields().get("address");

		values.addStringValue(order.toString());
		values.addStringValue(address.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		return new GenericEventInfo(event.getOriginalTimestamp(),
				AllocEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getFreeEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();
		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object order = event.getFields().get("order");
		Object address = event.getFields().get("pfn");
		values.addStringValue(order.toString());
		values.addStringValue(address.toString());

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				FreeEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getOpenEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object filename = event.getFields().get("filename");
		Object fd = event.getFields().get("fd");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue(filename.toString());
		values.addIntValue(Integer.parseInt(fd.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				OpenEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getCloseEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		Object fd = event.getFields().get("fd");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(fd.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				CloseEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getReadEvent(final LttEvent event,
			TraceState traceState) {
		FieldValues values = new FieldValues();

		Object fd = event.getFields().get("fd");
		Object count = event.getFields().get("count");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(fd.toString()));
		values.addIntValue(Integer.parseInt(count.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				ReadEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getWriteEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object fd = event.getFields().get("fd");
		Object count = event.getFields().get("count");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(fd.toString()));
		values.addIntValue(Integer.parseInt(count.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				WriteEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSelectEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		/*
		 * LttngEventContent content = event.getContent(); LttngEventField[]
		 * allFields = content.getFields();
		 */
		Object fd = event.getFields().get("fd");
		Object end_time_sec = event.getFields().get("end_time_sec");
		Object end_time_nsec = event.getFields().get("end_time_nsec");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());
		// addProcess(Integer.parseInt(fd.toString()),fd.toString());
		values.addIntValue(Integer.parseInt(fd.toString()));
		values.addStringValue((end_time_sec.toString()));
		values.addStringValue((end_time_nsec.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				SelectEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getExecEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object filename = event.getFields().get("filename");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue(filename.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				ExecEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getIoctlEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object fd = event.getFields().get("fd");
		Object cmd = event.getFields().get("cmd");
		Object arg = event.getFields().get("arg");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(fd.toString()));
		values.addStringValue(cmd.toString());
		values.addStringValue(arg.toString());
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				IoctlEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getPageFaultEntryEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object ip = event.getFields().get("ip");
		Object address = event.getFields().get("address");
		Object trap_id = event.getFields().get("trap_id");
		Object write_access = event.getFields().get("write_access");
		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue((ip.toString()));
		values.addStringValue((address.toString()));
		values.addStringValue((trap_id.toString()));
		values.addStringValue((write_access.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				PageFaultEntryEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getPageFaultExitEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object ret = event.getFields().get("res");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(ret.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				PageFaultExitEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSyscallEntryEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object ip = event.getFields().get("ip");
		Object syscall_id = event.getFields().get("syscall_id");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue((ip.toString()));
		values.addStringValue((syscall_id.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				SyscallEntryEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSyscallExitEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object ret = event.getFields().get("ret");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(ret.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				SyscallExitEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getPollEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object fd = event.getFields().get("fd");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(fd.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				PollEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getIrqEntryEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object ip = event.getFields().get("ip");
		Object handler = event.getFields().get("handler");
		Object irq_id = event.getFields().get("irq_id");
		Object kernel_mode = event.getFields().get("kernel_mode");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue((ip.toString()));
		values.addStringValue((handler.toString()));
		values.addStringValue((irq_id.toString()));
		values.addStringValue((kernel_mode.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);
		return new GenericEventInfo(event.getOriginalTimestamp(),
				IrqEntryEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getIrqExitEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object handled = event.getFields().get("handled");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addIntValue(Integer.parseInt(handled.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);
		return new GenericEventInfo(event.getOriginalTimestamp(),
				IrqExitEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSwapInEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object handled = event.getFields().get("address");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue((handled.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				SwapInEventTypeDescription.TYPE_ID, values);
	}

	private GenericEventInfo getSwapOutEvent(final LttEvent event,
			TraceState traceState) {

		FieldValues values = new FieldValues();

		Object handled = event.getFields().get("address");

		addProcess(traceState.getRunningProcess(event.getCpu()).getProcessId(),
				traceState.getRunningProcess(event.getCpu()).getProcessName());

		values.addStringValue((handled.toString()));
		short executionUnit = Short.parseShort(event.getFields().get(
				"Execution Unit"));
		String channel = String.valueOf(event.getFields().get("Channel"));
		values.addShortValue(executionUnit);
		values.addStringValue(channel);

		return new GenericEventInfo(event.getOriginalTimestamp(),
				SwapOutEventTypeDescription.TYPE_ID, values);
	}

	private void addProcess(Integer pid, String name) {
		if (!artifacts.containsKey(pid)) {
			FieldValues values = new FieldValues();
			values.addIntValue(pid);
			values.addStringValue(name);
			TaskArtifactInfo artifact = new TaskArtifactInfo(pid, name,
					TaskClass.PROCESS, 0,
					ProcessArtifactTypeDescription.TYPE_ID, values);
			artifacts.put(pid, artifact);

		} else {
			GenericArtifactInfo artifact = artifacts.get(pid);
			if (!(artifact.getName().equals(name))) {
				artifacts.remove(pid);
				FieldValues values = new FieldValues();
				values.addIntValue(pid);
				values.addStringValue(name);
				TaskArtifactInfo artifactNew = new TaskArtifactInfo(pid, name,
						TaskClass.PROCESS, 0,
						ProcessArtifactTypeDescription.TYPE_ID, values);
				artifacts.put(pid, artifactNew);
			}
		}
	}


	private static abstract class LttEventTypeDescription extends
			TypeDescription {
		static final List<FieldDescription> COMMON_FIELDS = new ArrayList<FieldDescription>();

		static {
			COMMON_FIELDS.add(new FieldDescription("Execution Unit",
					FieldType.UINT16));
			COMMON_FIELDS
					.add(new FieldDescription("Channel", FieldType.STRING));

		}

		LttEventTypeDescription(final int typeId, final String typeName,
				final List<FieldDescription> fields) {
			super(typeId, typeName, fields);
		}
	}

	private static class SwapEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 3;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.addAll(COMMON_FIELDS);
		}

		SwapEventTypeDescription() {
			super(TYPE_ID, "Swap", FIELDS);
		}
	}

	private static class MessageTypeDescription extends LttEventTypeDescription {
		static final int TYPE_ID = 12;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Message", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		MessageTypeDescription() {
			super(TYPE_ID, "Generic", FIELDS);
		}

	}

	private static class ProcessArtifactTypeDescription extends TypeDescription {
		static final int TYPE_ID = 14;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Process ID", FieldType.UINT32));
			FIELDS.add(new FieldDescription("Process Name", FieldType.STRING));

		}

		ProcessArtifactTypeDescription() {
			super(TYPE_ID, "Process", FIELDS);
		}
	}

	private static class CreateProcessFacilityEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 4;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Creator Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Created Process",
					FieldType.ARTIFACT_ID));
			FIELDS.addAll(COMMON_FIELDS);

		}

		CreateProcessFacilityEventTypeDescription() {
			super(TYPE_ID, "Create", FIELDS);
		}
	}

	private static class CreateIPCFacilityEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 15;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Creator Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Created Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Flags", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		CreateIPCFacilityEventTypeDescription() {
			super(TYPE_ID, "IPCFacility_Create", FIELDS);
		}
	}

	private static class CreateSocketFacilityEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 16;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Creator Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Socket Address",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Family", FieldType.STRING));
			FIELDS.add(new FieldDescription("Type", FieldType.STRING));
			FIELDS.add(new FieldDescription("Protocol", FieldType.STRING));
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		CreateSocketFacilityEventTypeDescription() {
			super(TYPE_ID, "SocketFacility_Create", FIELDS);
		}
	}

	private static class KillEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 5;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Killer Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Killed Process",
					FieldType.ARTIFACT_ID));
			FIELDS.addAll(COMMON_FIELDS);

		}

		KillEventTypeDescription() {
			super(TYPE_ID, "Kill", FIELDS);
		}
	}

	private static class SignalEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 25;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Signal Number", FieldType.INT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SignalEventTypeDescription() {
			super(TYPE_ID, "Signal", FIELDS);
		}
	}

	private static class WakeUpEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 23;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("State", FieldType.INT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		WakeUpEventTypeDescription() {
			super(TYPE_ID, "Wakeup", FIELDS);
		}
	}

	private static class WaitEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 24;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Waiting Process",
					FieldType.ARTIFACT_ID));
			FIELDS.add(new FieldDescription("Process waited for",
					FieldType.ARTIFACT_ID));
			FIELDS.addAll(COMMON_FIELDS);

		}

		WaitEventTypeDescription() {
			super(TYPE_ID, "Wait", FIELDS);
		}
	}

	private static class SocketSendEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 17;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("size", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SocketSendEventTypeDescription() {
			super(TYPE_ID, "SockedSend", FIELDS);
		}
	}

	private static class SocketReceiveEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 22;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("size", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SocketReceiveEventTypeDescription() {
			super(TYPE_ID, "SocketReceive", FIELDS);
		}
	}

	private static class ReceiveEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 2;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.addAll(COMMON_FIELDS);
		}

		ReceiveEventTypeDescription() {
			super(TYPE_ID, "Receive", FIELDS);
		}
	}

	private static class AllocEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 8;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Order", FieldType.STRING));
			FIELDS.add(new FieldDescription("Address", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		AllocEventTypeDescription() {
			super(TYPE_ID, "page_alloc", FIELDS);
		}
	}

	private static class OpenEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 18;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("filename", FieldType.STRING));
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		OpenEventTypeDescription() {
			super(TYPE_ID, "Open", FIELDS);
		}
	}

	private static class CloseEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 19;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		CloseEventTypeDescription() {
			super(TYPE_ID, "Close", FIELDS);
		}
	}

	private static class ReadEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 20;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.add(new FieldDescription("Number of bytes read",
					FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		ReadEventTypeDescription() {
			super(TYPE_ID, "Read", FIELDS);
		}
	}

	private static class WriteEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 21;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.add(new FieldDescription("Number of bytes written",
					FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		WriteEventTypeDescription() {
			super(TYPE_ID, "Write", FIELDS);
		}
	}

	private static class FreeEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 9;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("Order", FieldType.STRING));
			FIELDS.add(new FieldDescription("Address", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		FreeEventTypeDescription() {
			super(TYPE_ID, "page_free", FIELDS);
		}
	}

	private static class SelectEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 26;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.add(new FieldDescription("end_time_sec", FieldType.STRING));
			FIELDS.add(new FieldDescription("end_time_nsec", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SelectEventTypeDescription() {
			super(TYPE_ID, "select", FIELDS);
		}
	}

	private static class ExecEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 27;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("filename", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		ExecEventTypeDescription() {
			super(TYPE_ID, "exec", FIELDS);
		}
	}

	private static class IoctlEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 28;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.add(new FieldDescription("cmd", FieldType.STRING));
			FIELDS.add(new FieldDescription("arg", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		IoctlEventTypeDescription() {
			super(TYPE_ID, "ioctl", FIELDS);
		}
	}

	private static class PollEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 29;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {
			FIELDS.add(new FieldDescription("fd", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		PollEventTypeDescription() {
			super(TYPE_ID, "poll", FIELDS);
		}
	}

	private static class PageFaultEntryEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 30;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("ip", FieldType.STRING));
			FIELDS.add(new FieldDescription("address", FieldType.STRING));
			FIELDS.add(new FieldDescription("trap_id", FieldType.STRING));
			FIELDS.add(new FieldDescription("write_access", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		PageFaultEntryEventTypeDescription() {
			super(TYPE_ID, "page_fault_entry", FIELDS);
		}
	}

	private static class PageFaultExitEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 31;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("res", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		PageFaultExitEventTypeDescription() {
			super(TYPE_ID, "page_fault_exit", FIELDS);
		}
	}

	private static class SyscallEntryEventTypeDescription extends
			LttEventTypeDescription {

		static final int TYPE_ID = 32;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("ip", FieldType.STRING));
			FIELDS.add(new FieldDescription("syscall_id", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SyscallEntryEventTypeDescription() {
			super(TYPE_ID, "syscall_entry", FIELDS);
		}
	}

	private static class SyscallExitEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 33;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("ret", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SyscallExitEventTypeDescription() {
			super(TYPE_ID, "syscall_exit", FIELDS);
		}
	}

	private static class IrqEntryEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 34;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("ip", FieldType.STRING));
			FIELDS.add(new FieldDescription("handler", FieldType.STRING));
			FIELDS.add(new FieldDescription("irq_id", FieldType.STRING));
			FIELDS.add(new FieldDescription("kernel_mode", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		IrqEntryEventTypeDescription() {
			super(TYPE_ID, "irq_entry", FIELDS);
		}
	}

	private static class IrqExitEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 35;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("handled", FieldType.UINT32));
			FIELDS.addAll(COMMON_FIELDS);

		}

		IrqExitEventTypeDescription() {
			super(TYPE_ID, "irq_exit", FIELDS);
		}
	}

	private static class SwapInEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 36;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("address", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SwapInEventTypeDescription() {
			super(TYPE_ID, "swap_in", FIELDS);
		}
	}

	private static class SwapOutEventTypeDescription extends
			LttEventTypeDescription {
		static final int TYPE_ID = 37;

		private static final List<FieldDescription> FIELDS = new ArrayList<FieldDescription>();

		static {

			FIELDS.add(new FieldDescription("address", FieldType.STRING));
			FIELDS.addAll(COMMON_FIELDS);

		}

		SwapOutEventTypeDescription() {
			super(TYPE_ID, "swap_out", FIELDS);
		}
	}

}
