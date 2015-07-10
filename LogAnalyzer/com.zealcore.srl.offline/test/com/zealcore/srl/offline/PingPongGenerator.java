package com.zealcore.srl.offline;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PingPongGenerator {

    private PingPongGenerator() {}

    private static final String PROCESS2 = "Timo Worker";

    private static final int PROCESS2_PID = 66;

    private static final String PROCESS1 = "J-O Worker";

    private static final int PROCESS1_PID = 33;

    private static final String SIGNAL2 = "backhand";

    private static final String SIGNAL1 = "forehand";

    private static final String ACTOR2 = "Timo Boll";

    private static final String ACTOR1 = "J-O Waldner";

    private static final int NR_OF_EVENTS_DEFAULT_VALUE = 1002;

    private static final String STATE2 = "Sleeping";

    private static final String STATE1 = "Fighting";

    private static final int FILE_VERSION = 7;

    private static final int PRIORITY1 = 1;

    private static final int PRIORITY2 = 2;

    private static final int PROCESS1_PRIO = 1;

    private static final int PROCESS2_PRIO = 15;

    private static int nrOfEvents;

    private final List<String> stringDb = new ArrayList<String>(100);

    private long ts;

    private HashMap<String, UmlStateMachine> stateMachineMap;

    public static void main(final String[] args) throws Exception {
        System.out
                .println(">>>>>>>>>> PING PONG Generator V. 1.0 <<<<<<<<<<\n");
        if (args.length < 1) {
            nrOfEvents = NR_OF_EVENTS_DEFAULT_VALUE;
            System.out
                    .println("No argument is specified, will use default value: "
                            + NR_OF_EVENTS_DEFAULT_VALUE + " events");
        } else {
            final String arg = args[0];
            nrOfEvents = Integer.valueOf(arg);
            System.out.println("Nr of events specified is " + nrOfEvents);
        }

        final File file = new File("pingpong" + nrOfEvents + ".hex");
        final long writtenEvents = new PingPongGenerator()
                .run(nrOfEvents, file);
        System.out.println("Nr of written events is " + writtenEvents);
        System.out
                .println("------------------------------------------------------------------------");
        System.out.println("Output file: " + file.getAbsolutePath());
    }

    private long run(final int numEvents, final File output) throws Exception {
        final OutputStream wr = new FileOutputStream(output);
        final DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(wr));
        stateMachineMap = new HashMap<String, UmlStateMachine>();
        ts = 0;
        long writtenEvents = 0;
        writeHeader(out, numEvents, FILE_VERSION);
        long receivedAt = 0;
        long sentAt = 0;
        long finish = 0;
        while (writtenEvents < numEvents) {
            // PING, send
            if (sentAt == 0) {
                sentAt = nextTs();
            }
            UmlStateMachine machine;
            receivedAt = writePongSend(out, sentAt);
            /*
             * Switch process
             */
            switchProcess(out, receivedAt, PROCESS1_PID, PROCESS2_PID,
                    PRIORITY1);
            /*
             * PONG, receive
             */
            out.writeLong(receivedAt);
            out.writeInt(BinaryRosePrinter.Message.ROSE_RECEIVE.binTypeId());
            // actor
            machine = getStatemachineByName(ACTOR2);
            out.writeInt(machine.getId());
            // actor instance
            out.writeInt(getIndexOfString(ACTOR2));
            // recv replication index
            out.writeInt(getIndexOfString(ACTOR2));
            // state
            UmlState state = getStateByName(STATE2, machine);
            out.writeInt(state.getId());
            // signal
            out.writeInt(getIndexOfString(SIGNAL1));
            // port str
            out.writeInt(getIndexOfString(SIGNAL1));
            // port index
            out.writeInt(getIndexOfString(SIGNAL1));
            // msg adr
            out.writeInt(getIndexOfString(SIGNAL1));
            sentAt = nextTs();
            finish = nextTs();
            // finish
            out.writeLong(finish);
            out.writeLong(sentAt);
            /*
             * PONG, send
             */
            out.writeLong(sentAt);
            out.writeInt(BinaryRosePrinter.Message.ROSE_SEND.binTypeId());
            // actor
            machine = getStatemachineByName(ACTOR2);
            out.writeInt(machine.getId());
            // send actor instance
            out.writeInt(getIndexOfString(ACTOR2));
            // send replication index
            out.writeInt(getIndexOfString(ACTOR2));
            // recv actor
            out.writeInt(getIndexOfString(ACTOR1));
            // recv actor instance
            machine = getStatemachineByName(ACTOR1);
            out.writeInt(machine.getId());
            // recv replication index
            out.writeInt(getIndexOfString(ACTOR1));
            // signal
            out.writeInt(getIndexOfString(SIGNAL2));
            // port str
            out.writeInt(getIndexOfString(SIGNAL2));
            // port idx
            out.writeInt(getIndexOfString(SIGNAL2));
            // msg adr
            out.writeInt(getIndexOfString(SIGNAL2));
            receivedAt = nextTs();
            // received at
            out.writeLong(receivedAt);
            /*
             * Switch process
             */
            
            switchProcess(out, receivedAt, PROCESS2_PID, PROCESS1_PID,
                    PRIORITY2);

            /*
             * PING, receive
             */
            out.writeLong(receivedAt);
            out.writeInt(BinaryRosePrinter.Message.ROSE_RECEIVE.binTypeId());
            // actor
            machine = getStatemachineByName(ACTOR1);
            out.writeInt(machine.getId());
            // actor instance
            out.writeInt(getIndexOfString(ACTOR1));
            // recv replication index
            out.writeInt(getIndexOfString(ACTOR1));
            // state
            state = getStateByName(STATE1, machine);
            out.writeInt(state.getId());
            // signal
            out.writeInt(getIndexOfString(SIGNAL2));
            // port str
            out.writeInt(getIndexOfString(SIGNAL2));
            // port index
            out.writeInt(getIndexOfString(SIGNAL2));
            // msg adr
            out.writeInt(getIndexOfString(SIGNAL2));
            sentAt = nextTs();
            finish = nextTs();
            // finish
            out.writeLong(finish);
            out.writeLong(sentAt);
            writtenEvents += 6;
        }
        out.flush();
        out.close();
        return writtenEvents;
    }

    private void switchProcess(final DataOutputStream dOut,
            final long receivedAt, final int out, final int in, final int prio)
            throws IOException {
        dOut.writeLong(receivedAt);
        dOut.writeInt(BinaryRosePrinter.Message.TASK_SWITCH.binTypeId());
        // outPid = s.nextInt();
        dOut.writeInt(out);
        // outName = s.next();
        // out.writeInt(getIndexOfString(PROCESS1));
        // inPid = s.nextInt();
        dOut.writeInt(in);
        // inName = s.next();
        // out.writeInt(getIndexOfString(PROCESS2));
        dOut.writeInt(prio);
    }

    private long writePongSend(final DataOutputStream out, final long sentAt)
            throws IOException {
        long receivedAt;
        out.writeLong(sentAt);
        out.writeInt(BinaryRosePrinter.Message.ROSE_SEND.binTypeId());
        // actor
        UmlStateMachine machine = getStatemachineByName(ACTOR1);
        out.writeInt(machine.getId());
        // send actor instance
        out.writeInt(getIndexOfString(ACTOR1));
        // send replication index
        out.writeInt(getIndexOfString(ACTOR1));
        // recv actor
        machine = getStatemachineByName(ACTOR2);
        out.writeInt(machine.getId());
        // recv actor instance
        out.writeInt(getIndexOfString(ACTOR2));
        // recv replication index
        out.writeInt(getIndexOfString(ACTOR2));
        // signal
        out.writeInt(getIndexOfString(SIGNAL1));
        // port str
        out.writeInt(getIndexOfString(SIGNAL1));
        // port idx
        out.writeInt(getIndexOfString(SIGNAL1));
        // msg adr
        out.writeInt(getIndexOfString(SIGNAL1));
        receivedAt = nextTs();
        // received at
        out.writeLong(receivedAt);
        return receivedAt;
    }

    private UmlStateMachine getStatemachineByName(final String statemachineName) {
        for (final UmlStateMachine machine : stateMachineMap.values()) {
            if (statemachineName.equals(machine.getName())) {
                return machine;
            }
        }
        return null;
    }

    private UmlState getStateByName(final String stateName,
            final UmlStateMachine machine) {
        for (final UmlState state : machine.getStates()) {
            if (stateName.equals(state.getName())) {
                return state;
            }
        }
        return null;
    }

    private long nextTs() {
        ts = ts + 10;
        return ts;
    }

    private void writeHeader(final DataOutputStream out, final int numEvents,
            final int version) throws Exception {
        out.writeInt(BinaryRosePrinter.MAGIC_CONSTANT);
        out.writeInt(version);
        out.write(new byte[36]);
        // Two String, PING and Pong
        out.writeInt(8);
        // Actor 1
        final String ping = ACTOR1;
        byte[] bytes = ping.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(ping);
        // Actor 2
        final String pong = ACTOR2;
        bytes = pong.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(pong);
        // Signal 1
        final String signal1 = SIGNAL1;
        bytes = signal1.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(signal1);
        // Signal 2
        final String signal2 = SIGNAL2;
        bytes = signal2.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(signal2);
        // Process 1
        final String proc1 = PROCESS1;
        bytes = proc1.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(proc1);
        // Process 2
        final String proc2 = PROCESS2;
        bytes = proc2.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(proc2);
        // State 1
        final String s1 = STATE1;
        bytes = s1.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(s1);
        // state 2
        final String s2 = STATE2;
        bytes = s2.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        stringDb.add(s2);

        writeUserStructs(out);
        writeTaskTable(out);
        // Write nr of statemachines
        createStatemachines();
        writeStatemachines(out);
        out.writeInt(numEvents);
    }

    private void writeUserStructs(final DataOutputStream out)
            throws IOException {
        // No user structs
        out.writeInt(0);
    }

    private void writeTaskTable(final DataOutputStream out) throws IOException {
        // Write nr of taskstats
        out.writeInt(0);
        // write nr of tasks
        out.writeInt(2);
        // write processtypename
        out.writeInt(1);

        out.writeInt(PROCESS1_PID);
        out.writeInt(getIndexOfString(PROCESS1));
        out.writeInt(PROCESS1_PRIO);
        // Has executed during log
        out.writeBoolean(true);

        out.writeInt(PROCESS2_PID);
        out.writeInt(getIndexOfString(PROCESS2));
        out.writeInt(PROCESS2_PRIO);
        // Has executed during log
        out.writeBoolean(true);

    }

    private void createStatemachines() {
        int idCounter = 0;
        final int depth = 0;
        final UmlStateMachine waldner = new UmlStateMachine(ACTOR1);
        waldner.setId(idCounter++);
        final UmlState stateW = new UmlState(STATE1, waldner, depth);
        stateW.setId(idCounter++);
        waldner.add(stateW);
        waldner.add(new UmlTransition(stateW.getId(), stateW.getId()));
        final UmlStateMachine boll = new UmlStateMachine(ACTOR2);
        boll.setId(idCounter++);
        final UmlState stateB = new UmlState(STATE2, boll, depth);
        stateB.setId(idCounter++);
        boll.add(stateB);
        boll.add(new UmlTransition(stateB.getId(), stateB.getId()));
        stateMachineMap.put(waldner.getName(), waldner);
        stateMachineMap.put(boll.getName(), boll);
    }

    private void writeStatemachines(final DataOutputStream out)
            throws IOException {
        out.writeInt(stateMachineMap.size());
        for (final UmlStateMachine machine : stateMachineMap.values()) {
            out.writeInt(machine.getId());
            out.writeInt(getIndexOfString(machine.getName()));
            out.writeInt(machine.getNrOfStates());
            out.writeInt(machine.getNrOfTransitions());
            for (final UmlState state : machine.getStates()) {
                out.writeInt(state.getId());
                out.writeInt(getIndexOfString(state.getName()));
                if (state.getParent() == null) {
                    out.writeInt(machine.getId());
                } else {
                    out.writeInt(state.getParent().getId());
                }
                out.writeInt(state.getDepth());
            }
            for (final UmlTransition trans : machine.getTransitions()) {
                out.writeInt(trans.getSource());
                out.writeInt(trans.getTarget());
            }
        }
    }

    private int getIndexOfString(final String string) {
        final int value = stringDb.indexOf(string);
        if (value < 0) {
            throw new RuntimeException(
                    "ERROR, failed to get string value for string " + string
                            + ", nr of strings in String DB are: "
                            + stringDb.size());
        }
        return value;
    }
}
