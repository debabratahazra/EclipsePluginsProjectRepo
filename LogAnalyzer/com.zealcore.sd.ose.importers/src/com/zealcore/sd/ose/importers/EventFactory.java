package com.zealcore.sd.ose.importers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.SymbolManager;
import com.ose.sigdb.Attribute;
import com.ose.sigdb.CompositeAttribute;
import com.ose.sigdb.Signal;
import com.ose.sigdb.SignalAttribute;
import com.ose.sigdb.TruncatedAttribute;
import com.ose.sigdb.util.EndianConstants;
import com.ose.system.AllocEvent;
import com.ose.system.BindEvent;
import com.ose.system.CreateEvent;
import com.ose.system.ErrorEvent;
import com.ose.system.FreeEvent;
import com.ose.system.KillEvent;
import com.ose.system.LossEvent;
import com.ose.system.ReceiveEvent;
import com.ose.system.ResetEvent;
import com.ose.system.SendEvent;
import com.ose.system.SwapEvent;
import com.ose.system.TargetEvent;
import com.ose.system.TimeoutEvent;
import com.ose.system.UserEvent;
import com.ose.system.TargetEvent.ProcessInfo;
import com.zealcore.se.core.ImportException;
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
import com.zealcore.se.core.util.TimestampManager;

class EventFactory {
    private static final List<TypeDescription> TYPE_DESCRIPTIONS =
        new ArrayList<TypeDescription>();

    private static final long UINT_MAX = 0x100000000L; // 2^32

    private final SymbolManager symbolManager = EventPlugin.getSymbolManager();

    private final Map<Integer, GenericArtifactInfo> artifacts =
        new HashMap<Integer, GenericArtifactInfo>();

    private boolean bigEndian;

    private long tickLength;

    private long nTickFrequency;

    private long previousTs;

    private long previousTick;

    static {
        TYPE_DESCRIPTIONS.add(new SendEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new ReceiveEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new SwapEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new CreateEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new KillEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new ErrorEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new BindEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new AllocEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new FreeEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new TimeoutEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new UserEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new ResetEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new LossEventTypeDescription());
        TYPE_DESCRIPTIONS.add(new ProcessArtifactTypeDescription());
    }

    public void init(final boolean bigEndian, final int tickLength,
            final int nTickFrequency) {
        this.bigEndian = bigEndian;
        this.tickLength = tickLength & 0xFFFFFFFFL;
        this.nTickFrequency = nTickFrequency & 0xFFFFFFFFL;
    }

    public static List<TypeDescription> getTypeDescriptions() {
        return TYPE_DESCRIPTIONS;
    }

    public GenericEventInfo getEvent(final TargetEvent event) {
        if (event instanceof SendEvent) {
            return getSendEvent((SendEvent) event);
        } else if (event instanceof ReceiveEvent) {
            return getReceiveEvent((ReceiveEvent) event);
        } else if (event instanceof SwapEvent) {
            return getSwapEvent((SwapEvent) event);
        } else if (event instanceof CreateEvent) {
            return getCreateEvent((CreateEvent) event);
        } else if (event instanceof KillEvent) {
            return getKillEvent((KillEvent) event);
        } else if (event instanceof ErrorEvent) {
            return getErrorEvent((ErrorEvent) event);
        } else if (event instanceof BindEvent) {
            return getBindEvent((BindEvent) event);
        } else if (event instanceof AllocEvent) {
            return getAllocEvent((AllocEvent) event);
        } else if (event instanceof FreeEvent) {
            return getFreeEvent((FreeEvent) event);
        } else if (event instanceof TimeoutEvent) {
            return getTimeoutEvent((TimeoutEvent) event);
        } else if (event instanceof UserEvent) {
            return getUserEvent((UserEvent) event);
        } else if (event instanceof ResetEvent) {
            return getResetEvent((ResetEvent) event);
        } else if (event instanceof LossEvent) {
            return getLossEvent((LossEvent) event);
        } else if (event == null) {
            throw new IllegalArgumentException("Event was null!");
        } else {
            throw new IllegalArgumentException("Unknown event type was found: "
                  + event.toString());
        }
    }

    public Collection<GenericArtifactInfo> getArtifacts() {
        return artifacts.values();
    }

    private GenericEventInfo getSendEvent(final SendEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        addProcess(event.getSenderProcess());
        addProcess(event.getAddresseeProcess());
        values.addIntValue(event.getSignalNumber());
        values.addIntValue(event.getSignalId());
        values.addIntValue(event.getSignalAddress());
        values.addIntValue(event.getSignalSize());
        SignalData sd = getSignalData(event);
        values.addStringValue(sd.getData());
        values.addShortValue(event.getExecutionUnit());

        return new SendEventInfo(getTimestamp(event),
                event.getSenderProcess().getId(),
                event.getAddresseeProcess().getId(), -1, sd.getMessage(),
                SendEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getReceiveEvent(final ReceiveEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        addProcess(event.getSenderProcess());
        addProcess(event.getAddresseeProcess());
        values.addArtifactIdValue(event.getSenderProcess().getId());
        values.addIntValue(event.getSignalNumber());
        values.addIntValue(event.getSignalId());
        values.addIntValue(event.getSignalAddress());
        values.addIntValue(event.getSignalSize());
        SignalData sd = getSignalData(event);
        values.addStringValue(sd.getData());
        values.addShortValue(event.getExecutionUnit());

        return new ReceiveEventInfo(getTimestamp(event),
                event.getSenderProcess().getId(),
                event.getAddresseeProcess().getId(), -1, sd.getMessage(),
                ReceiveEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getSwapEvent(final SwapEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        addProcess(event.getSwappedOutProcess());
        addProcess(event.getSwappedInProcess());
        values.addShortValue(event.getExecutionUnit());

        return new TaskSwitchEventInfo(getTimestamp(event),
                event.getSwappedInProcess().getId(),
                event.getSwappedOutProcess().getId(), 0,
                SwapEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getCreateEvent(final CreateEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo creator = event.getCreatorProcess();
        if (creator != null) {
            addProcess(creator);
            values.addArtifactIdValue(creator.getId());
        } else {
            values.addIntValue(-1);
        }
        ProcessInfo created = event.getCreatedProcess();
        addProcess(created);
        values.addArtifactIdValue(created.getId());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                CreateEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getKillEvent(final KillEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo killer = event.getKillerProcess();
        if (killer != null) {
            addProcess(killer);
            values.addArtifactIdValue(killer.getId());
        } else {
            values.addIntValue(-1);
        }
        ProcessInfo killed = event.getKilledProcess();
        addProcess(killed);
        values.addArtifactIdValue(killed.getId());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                KillEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getErrorEvent(final ErrorEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addBooleanValue(event.isExecutive());
        values.addIntValue(event.getError());
        values.addIntValue(event.getExtra());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                ErrorEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getBindEvent(final BindEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addShortValue(event.getFromExecutionUnit());
        values.addShortValue(event.getToExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                BindEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getAllocEvent(final AllocEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addIntValue(event.getSignalNumber());
        values.addIntValue(event.getSignalId());
        values.addIntValue(event.getSignalAddress());
        values.addIntValue(event.getSignalSize());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                AllocEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getFreeEvent(final FreeEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addIntValue(event.getSignalNumber());
        values.addIntValue(event.getSignalId());
        values.addIntValue(event.getSignalAddress());
        values.addIntValue(event.getSignalSize());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                FreeEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getTimeoutEvent(final TimeoutEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        values.addIntValue(event.getTimeout());
        values.addStringValue(toSystemCallString(event.getSystemCall()));
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                TimeoutEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getUserEvent(final UserEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        ProcessInfo process = event.getProcess();
        addProcess(process);
        values.addArtifactIdValue(process.getId());
        values.addIntValue(event.getEventNumber());
        values.addIntValue(event.getEventDataSize());
        SignalData sd = getSignalData(event);
        values.addStringValue(sd.getData());
        values.addShortValue(event.getExecutionUnit());

        return new GenericEventInfo(getTimestamp(event),
                UserEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getResetEvent(final ResetEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        values.addBooleanValue(event.isWarmReset());

        return new GenericEventInfo(getTimestamp(event),
                ResetEventTypeDescription.TYPE_ID, values);
    }

    private GenericEventInfo getLossEvent(final LossEvent event) {
        FieldValues values = new FieldValues();
        addCommonFields(event, values);
        values.addIntValue(event.getLostSize());

        return new GenericEventInfo(getTimestamp(event),
                LossEventTypeDescription.TYPE_ID, values);
    }

    private void addProcess(final ProcessInfo process) {
        if (!artifacts.containsKey(process.getId())) {
            FieldValues values = new FieldValues();
            values.addIntValue(process.getBid());
            values.addIntValue(process.getSid());
            values.addStringValue(toProcessTypeString(process.getType()));
            values.addIntValue(process.getEntrypoint());
            boolean systemProcess = (process.getProperties() & 0x1) != 0;
            values.addBooleanValue(systemProcess);
            TaskArtifactInfo artifact = new TaskArtifactInfo(process.getId(),
                    process.toString(), TaskClass.PROCESS, 0,
                    ProcessArtifactTypeDescription.TYPE_ID, values);
            artifacts.put(process.getId(), artifact);
        }
    }

    private static void addCommonFields(final TargetEvent event,
            final FieldValues values) {
        values.addIntValue(event.getReference());
        values.addIntValue(event.getTick());
        values.addIntValue(event.getNTick());
        values.addStringValue(event.getFileName());
        values.addIntValue(event.getLineNumber());
    }

    private long getTimestamp(final TargetEvent event) {
        final long reference = event.getReference() & 0xFFFFFFFFL;
        final long tick = event.getTick() & 0xFFFFFFFFL;
        final long nTick = event.getNTick() & 0xFFFFFFFFL;
        long ts;

        if ((tick < 0) || (nTick < 0)) {
            throw new ImportException(
                "Illegal timestamp for event with entry number = " + reference
                    + ", tick = " + tick + ", nTick = " + nTick);
        }

        if (tickLength <= 0) {
            /** Use counter timestamps if tick length is erroneously missing. */
            return previousTs++;
        } else if ((tick == 0) && (nTick == 0)) {
            /** Use previous timestamp if some event lacks tick and nTick. */
            return previousTs;
        } else if ((event instanceof LossEvent) && (tick == 0) && (nTick != 0)) {
            // Use previous timestamp for OSEck loss events that,
            // erroneously, lacks a tick but have an nTick.
            return previousTs;
        } else if (event.getSeconds() != 0) {
            /** Calculate absolute time stamp. */
            long tsInMs = event.getMilliSeconds();
            // Add nTick to get (fake?) better precision.
            long nTickInNs = 0;
            if (nTickFrequency != 0) {
                nTickInNs = (nTick * TimestampManager.NANOS_PER_SECOND)
                        / nTickFrequency;
            }
            ts = tsInMs * TimestampManager.NANOS_PER_MILLI + nTickInNs;
            previousTs = ts;
            previousTick = tick;
        } else {
            /** Calculate relative time stamp based on tick and nTick. */
            long tickInNs;
            // Has the tick counter wrapped?
            if (tick < previousTick) {
                tickInNs = (tick + UINT_MAX) * tickLength
                        * TimestampManager.NANOS_PER_MICRO;
            } else {
                tickInNs = tick * tickLength * TimestampManager.NANOS_PER_MICRO;
            }
            long nTickInNs = 0;
            if (nTickFrequency != 0) {
                nTickInNs = (nTick * TimestampManager.NANOS_PER_SECOND)
                        / nTickFrequency;
            }
            ts = tickInNs + nTickInNs;
            previousTs = ts;
            previousTick = tick;
        }

        if (ts < 0) {
            throw new IllegalStateException("Timestamps can't be negative.");
        }

        return ts;
    }

    private SignalData getSignalData(final TargetEvent event) {
        int number;
        byte[] rawData;
        String preFormattedData;
        boolean isUserEvent = false;
        int byteOrder = (bigEndian ? EndianConstants.BIG_ENDIAN
            : EndianConstants.LITTLE_ENDIAN);
        String message;
        String data;

        if (event instanceof SendEvent) {
            SendEvent e = (SendEvent) event;
            number = e.getSignalNumber();
            rawData = e.getSignalData();
            preFormattedData = e.getFormattedSignalData();
        } else if (event instanceof ReceiveEvent) {
            ReceiveEvent e = (ReceiveEvent) event;
            number = e.getSignalNumber();
            rawData = e.getSignalData();
            preFormattedData = e.getFormattedSignalData();
        } else if (event instanceof UserEvent) {
            UserEvent e = (UserEvent) event;
            number = e.getEventNumber();
            rawData = e.getEventData();
            preFormattedData = e.getFormattedEventData();
            isUserEvent = true;
        } else {
            throw new IllegalArgumentException();
        }

        Signal signal = isUserEvent ?
                symbolManager.getEvent(number, rawData, byteOrder)
                : symbolManager.getSignal(number, rawData, byteOrder);
        if (signal != null) {
            message = signal.getName();
            data = (rawData.length > 0) ? getFormattedData(signal) : "";
        } else {
            message = String.valueOf(number);
            data = (preFormattedData != null) ? preFormattedData
                : getBinaryData(rawData);
        }

        return new SignalData(message, data);
    }

    private static String getFormattedData(final CompositeAttribute attribute) {
        StringBuilder builder = new StringBuilder();
        builder.append(attribute.getName() + "{");

        for (Iterator i = attribute.getAttributes().iterator(); i.hasNext();) {
            Attribute attr = (Attribute) i.next();
            if (attr instanceof CompositeAttribute) {
               builder.append(getFormattedData((CompositeAttribute) attr));
            } else if (attr instanceof SignalAttribute) {
                builder.append(attr.toString() + ";");
            } else if (attr instanceof TruncatedAttribute) {
                builder.append("<truncated-field>");
            }
        }

        builder.append("}");
        return builder.toString();
    }

    private static String getBinaryData(final byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            builder.append(toByteHexString(data[i]));
            if (i < (data.length - 1)) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    private static String toProcessTypeString(final int type) {
        switch (type) {
            case ProcessInfo.TYPE_PRIORITIZED:
                return "Prioritized";
            case ProcessInfo.TYPE_BACKGROUND:
                return "Background";
            case ProcessInfo.TYPE_INTERRUPT:
                return "Interrupt";
            case ProcessInfo.TYPE_TIMER_INTERRUPT:
                return "Timer Interrupt";
            case ProcessInfo.TYPE_PHANTOM:
                return "Phantom";
            case ProcessInfo.TYPE_SIGNAL_PORT:
                return "Signal Port";
            case ProcessInfo.TYPE_IDLE:
                return "Idle";
            case ProcessInfo.TYPE_KILLED:
                return "Killed";
            case ProcessInfo.TYPE_UNKNOWN:
                // Fall through.
            default:
                return "Unknown";
        }
    }

    private static String toSystemCallString(final int systemCall) {
        switch (systemCall) {
            case TimeoutEvent.SYSTEM_CALL_RECEIVE:
                return "Receive";
            default:
                return "Unknown";
        }
    }

    private static String toByteHexString(final byte b) {
        String s = Integer.toHexString(b & 0xFF).toUpperCase();
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }

    private static class SignalData {
        private final String message;

        private final String data;

        SignalData(final String message, final String data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public String getData() {
            return data;
        }
    }

    private static abstract class OseEventTypeDescription extends
            TypeDescription {
        static final List<FieldDescription> COMMON_FIELDS =
            new ArrayList<FieldDescription>();

        static {
            COMMON_FIELDS.add(new FieldDescription("Entry", FieldType.UINT32));
            COMMON_FIELDS.add(new FieldDescription("Ticks", FieldType.UINT32));
            COMMON_FIELDS.add(new FieldDescription("Micro Ticks", FieldType.UINT32));
            COMMON_FIELDS.add(new FieldDescription("File", FieldType.STRING));
            COMMON_FIELDS.add(new FieldDescription("Line", FieldType.UINT32));
        }

        OseEventTypeDescription(final int typeId, final String typeName,
                final List<FieldDescription> fields) {
            super(typeId, typeName, fields);
        }
    }

    private static class SendEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 1;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Signal Number", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Address", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Size", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Data", FieldType.STRING));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        SendEventTypeDescription() {
            super(TYPE_ID, "Send", FIELDS);
        }
    }

    private static class ReceiveEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 2;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("From", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Signal Number", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Address", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Size", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Data", FieldType.STRING));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        ReceiveEventTypeDescription() {
            super(TYPE_ID, "Receive", FIELDS);
        }
    }

    private static class SwapEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 3;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        SwapEventTypeDescription() {
            super(TYPE_ID, "Swap", FIELDS);
        }
    }

    private static class CreateEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 4;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Creator Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Created Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        CreateEventTypeDescription() {
            super(TYPE_ID, "Create", FIELDS);
        }
    }

    private static class KillEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 5;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Killer Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Killed Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        KillEventTypeDescription() {
            super(TYPE_ID, "Kill", FIELDS);
        }
    }

    private static class ErrorEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 6;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Faulting Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Kernel Invoked", FieldType.BOOLEAN));
            FIELDS.add(new FieldDescription("Error Code", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Extra Parameter", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        ErrorEventTypeDescription() {
            super(TYPE_ID, "Error", FIELDS);
        }
    }

    private static class BindEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 7;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Bound Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("From Execution Unit", FieldType.UINT16));
            FIELDS.add(new FieldDescription("To Execution Unit", FieldType.UINT16));
        }

        BindEventTypeDescription() {
            super(TYPE_ID, "Bind", FIELDS);
        }
    }

    private static class AllocEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 8;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Allocating Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Signal Number", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Address", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Size", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        AllocEventTypeDescription() {
            super(TYPE_ID, "Alloc", FIELDS);
        }
    }

    private static class FreeEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 9;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Freeing Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Signal Number", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Address", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Signal Size", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        FreeEventTypeDescription() {
            super(TYPE_ID, "Free", FIELDS);
        }
    }

    private static class TimeoutEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 10;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Timeout Length", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Timed Out System Call", FieldType.STRING));
            FIELDS.add(new FieldDescription("Timed Out Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        TimeoutEventTypeDescription() {
            super(TYPE_ID, "Timeout", FIELDS);
        }
    }

    private static class UserEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 11;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Reporting Process", FieldType.ARTIFACT_ID));
            FIELDS.add(new FieldDescription("User Event Number", FieldType.UINT32));
            FIELDS.add(new FieldDescription("User Event Data Size", FieldType.UINT32));
            FIELDS.add(new FieldDescription("User Event Data", FieldType.STRING));
            FIELDS.add(new FieldDescription("Execution Unit", FieldType.UINT16));
        }

        UserEventTypeDescription() {
            super(TYPE_ID, "User", FIELDS);
        }
    }

    private static class ResetEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 12;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Warm Reset", FieldType.BOOLEAN));
        }

        ResetEventTypeDescription() {
            super(TYPE_ID, "Reset", FIELDS);
        }
    }

    private static class LossEventTypeDescription extends
            OseEventTypeDescription {
        static final int TYPE_ID = 13;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.addAll(COMMON_FIELDS);
            FIELDS.add(new FieldDescription("Lost Bytes", FieldType.UINT32));
        }

        LossEventTypeDescription() {
            super(TYPE_ID, "Loss", FIELDS);
        }
    }

    private static class ProcessArtifactTypeDescription extends
            TypeDescription {
        static final int TYPE_ID = 14;

        private static final List<FieldDescription> FIELDS =
            new ArrayList<FieldDescription>();

        static {
            FIELDS.add(new FieldDescription("Block ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Segment ID", FieldType.UINT32));
            FIELDS.add(new FieldDescription("Type", FieldType.STRING));
            FIELDS.add(new FieldDescription("Entrypoint", FieldType.UINT32));
            FIELDS.add(new FieldDescription("System Process", FieldType.BOOLEAN));
        }

        ProcessArtifactTypeDescription() {
            super(TYPE_ID, "Process", FIELDS);
        }
    }
}
