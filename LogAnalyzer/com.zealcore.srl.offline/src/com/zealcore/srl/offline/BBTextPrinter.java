package com.zealcore.srl.offline;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BBTextPrinter implements IPrinter {

    private static final String REPLICATION_INDEX = "receiverReplicationIndex";

    private static final String MESSAGE_ADRESS = "msgAddr";

    private static final String PORT_INDEX_STR = "portIndex";

    private static final String PORT_STR = "port";

    private static final String SIGNAL_DESCRIPTORS = "signalDescriptors";

    private static final String STATE = "state";

    private static final String ACTOR_INSTANCE = "receiverActorInstance";

    private static final String ACTOR_REPLICATION_INDEX = REPLICATION_INDEX;

    private static final String ACTOR = "receiverActor";

    private static final String SENDER_ACTOR_INSTANCE = "senderActorInstance";

    private static final String SENDER_REPLICATION_INDEX = "senderReplicationIndex";

    private static final String RECEIVER_REPLICATION_INDEX = REPLICATION_INDEX;

    private static final String SENDER_ACTOR = "senderActor";

    private static final String SEPARATOR = ",";

    private PrintStream out;

    private Map<Long, String> tasks;

    private final Map<Object, Long> missing = new LinkedHashMap<Object, Long>();

    public void transform(final Blackbox blackbox) {

        tasks = new LinkedHashMap<Long, String>();
        for (final TypedLinearMessage message : blackbox
                .getTypedLinearMessages()) {
            if (message.getStruct().getId() == 1010) {
                final Long id = (Long) message.getValue("id");
                final String name = (String) message.getValue("name");
                tasks.put(id, name);
            }
            Logger.log(message);
            Logger.log(message.getStruct().getId());
        }
        if (blackbox.isVerboseMode()) {
            Logger.log("Number Of Tasks : " + tasks.size());
        }

        for (final TypedLinearMessage message : blackbox
                .getTypedLinearMessages()) {
            Logger.log(message);
        }

        write("# BBTextFormat-v3");

        write("# ROSEREC (A Rose RT dispatch event): HIGH,LOW,EVENT-ID,ReceiverName,ReceiverInstance,ReceiverReplicationIndex,CurrentState,ReceivedSignal,PortName,PortIndex,MessagePointer,FinishHigh,FinishLow");
        write("# ROSESND (A Rose RT send event) HIGH,LOW,EVENT,SenderActor,SenderInstance,SenderReplicationIndex,ReceiverActor,ReceiverInstance,ReceiverReplicationIndex,Signal,SenderPortName,SenderPortIndex,MessageAdress");
        write("# CTRLFLW (A OS Specific Task Switch) HIGH,LOW,EVENT,EVENT-DATA");

        // write("170,274,CTRLFLW,65685,RHi5,65682,SHi4,13,OSE_RECEIVE,0,RECEIVE,3435973836,0,2,");

        final Collection<TypedCircularMessage> msgs = blackbox
                .getTypedCircularMessages();

        final List<TypedCircularMessage> cmsgs = new ArrayList<TypedCircularMessage>(
                msgs);
        sort(cmsgs);

        for (final TypedCircularMessage message : cmsgs) {
            final StringBuilder bld = new StringBuilder();

            final long ts = ((CircularMessage) message.getMessage()).getTs();
            final long high = ts >> 32;

            bld.append(high);
            bld.append(SEPARATOR);

            bld.append(ts - (high << 32));
            bld.append(SEPARATOR);

            final int id = message.getStruct().getId();
            if (id == 1009) {
                bld.append(recvToString(message));
            } else if (id == 1008) {
                bld.append(sndToString(message));
            } else if (id == 1011) {
                bld.append(taskSwitch(message));
            } else {
                bld.append(message.getStruct().getName());
                bld.append(id);
                bld.append(SEPARATOR);

                final int fieldsLength = message.getStruct().getFields().size();
                for (int i = 0; i < fieldsLength - 1; i++) {
                    final Object value = getValue(message, i);
                    bld.append(value);
                    bld.append(SEPARATOR);
                }

                bld.append(getValue(message, fieldsLength - 1));
            }
            write(bld.toString());
        }
    }

    private String lastTask = "UNKNOWN-TASK";

    private Long lastPid = 0L;

    private CharSequence taskSwitch(final TypedCircularMessage message) {
        /*
         * outPid = s.nextInt(); outName = s.next(); inPid = s.nextInt(); inName =
         * s.next(); prio = s.nextInt(); ev = s.next(); eventData =
         * s.nextLong(); state = s.next(); pc = s.nextLong(); sp = s.nextLong(); //
         * ic s.nextLong();
         */
        final StringBuilder builder = new StringBuilder();
        builder.append("CTRLFLW").append(SEPARATOR);

        builder.append(lastPid).append(SEPARATOR);
        builder.append(lastTask).append(SEPARATOR);

        final Object taskid = getValue(message, 0);
        builder.append(taskid).append(SEPARATOR);
        lastPid = (Long) taskid;

        lastTask = tasks.get(taskid);
        if (lastTask == null) {
            if (!missing.containsKey(taskid)) {
                missing.put(taskid, (Long) taskid);
                Logger.err("Error: Missing Task  (PID)=" + taskid);
            }
            lastTask = "UnknownTask-" + taskid;
        }
        builder.append(lastTask).append(SEPARATOR);

        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);
        builder.append(0).append(SEPARATOR);

        // builder.append(b)

        return builder.toString();
    }

    private void write(final String string) {
        out.println(string);
    }

    private CharSequence sndToString(final TypedCircularMessage message) {
        final StringBuilder str = new StringBuilder();
        /*
         * send = s.next(); sendI = s.nextLong(); sendState = s.next(); sendPort =
         * s.next(); sendPortI = s.nextLong(); recv = s.next(); recvI =
         * s.nextLong(); // recvState s.next(); // recvPort s.next(); //
         * recvPortI s.nextLong(); // sig s.next(); // xHEXDATA s.next(); //
         * sASCIIDATA s.next();
         */

        /*
         * write("# ROSESND (A Rose RT send event) HIGH,LOW,EVENT, SenderActor,
         * SenderInstance, ReceiverActor, ReceiverInstance, Signal,
         * SenderPortName, SenderPortIndex, MessageAdress");
         * 
         */
        str.append("ROSESND").append(SEPARATOR);

        str.append(getValue(message, SENDER_ACTOR)).append(SEPARATOR);
        str.append(getValue(message, SENDER_ACTOR_INSTANCE)).append(SEPARATOR);
        str.append(getValue(message, SENDER_REPLICATION_INDEX)).append(
                SEPARATOR);
        str.append(getValue(message, ACTOR)).append(SEPARATOR);
        str.append(getValue(message, ACTOR_INSTANCE)).append(SEPARATOR);
        str.append(getValue(message, RECEIVER_REPLICATION_INDEX)).append(
                SEPARATOR);
        str.append(getValue(message, SIGNAL_DESCRIPTORS)).append(SEPARATOR);

        str.append(getValue(message, PORT_STR)).append(SEPARATOR);
        str.append(getValue(message, PORT_INDEX_STR)).append(SEPARATOR);
        str.append(getValue(message, MESSAGE_ADRESS)).append(SEPARATOR);

        return str.toString();

    }

    private CharSequence recvToString(final TypedCircularMessage message) {
        final StringBuilder str = new StringBuilder();
        str.append("ROSEREC").append(SEPARATOR);
        str.append(getValue(message, ACTOR)).append(SEPARATOR);
        str.append(getValue(message, ACTOR_INSTANCE)).append(SEPARATOR);
        str.append(getValue(message, ACTOR_REPLICATION_INDEX))
                .append(SEPARATOR);
        str.append(getValue(message, STATE)).append(SEPARATOR);
        str.append(getValue(message, SIGNAL_DESCRIPTORS)).append(SEPARATOR);
        str.append(getValue(message, PORT_STR)).append(SEPARATOR);
        str.append(getValue(message, PORT_INDEX_STR)).append(SEPARATOR);
        str.append(getValue(message, MESSAGE_ADRESS)).append(SEPARATOR);
        str.append(getValue(message, "finishHi")).append(SEPARATOR);
        str.append(getValue(message, "finishLo")).append(SEPARATOR);
        return str.toString();
    }

    private Object getValue(final TypedCircularMessage message, final int i) {
        final Field f = message.getStruct().getFields().get(i);
        final String name = f.getName();
        return getValue(message, name);
    }

    private Object getValue(final TypedCircularMessage message,
            final String name) {
        final Object value = message.getValue(name);
        if (value == null) {
            Logger.err("WARNING: No such value: " + name);
            return null;
        }
        if (value instanceof IPointer) {
            final IPointer p = (IPointer) value;

            if (p.getValue() == null) {
                // Logger.err("Pointer % 4 = " + (p.getPointer() % 2));
                final String hex = Long.toString(p.getPointer()).toUpperCase();
                Logger
                        .err("Note: '"
                                + name
                                + "' is an unresolved pointer value (NULL). Will use pointer value ("
                                + hex + ")");
                return hex;
            }
            return p.getValue();
        }

        if (value.getClass().isArray()) {
            final Object[] arr = (Object[]) value;
            return Arrays.asList(arr);
        }
        return value;
    }

    private void sort(final List<TypedCircularMessage> cmsgs) {
        Collections.sort(cmsgs, new Comparator<TypedCircularMessage>() {
            public int compare(final TypedCircularMessage o1,
                    final TypedCircularMessage o2) {
                final long ts1 = ((CircularMessage) o1.getMessage()).getTs();
                final long ts2 = ((CircularMessage) o2.getMessage()).getTs();
                return Long.valueOf(ts1).compareTo(ts2);
            }
        });
    }

    public void setOut(final PrintStream out) {
        this.out = out;
    }
}
