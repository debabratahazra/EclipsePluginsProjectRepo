package com.zealcore.se.core.importers;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.Map.Entry;

import com.zealcore.se.core.model.generic.GenericTask;

public class BBBinWriter {

    //private static final String NULL = "null";

    private DataOutputStream bin;

    private final byte[] reserved = new byte[36];

    //private List<String> stringList;

    public static final int MAGIC_CONSTANT = 0xBADBABE;

    private static final int BIN_FORMAT_VERSION = 9;

    public enum TaskType {
        TASK(0), PROCESS(1), THREAD(2);
        private int taskTypeId;

        private TaskType(final int taskTypeId) {
            this.taskTypeId = taskTypeId;
        }

        public int taskTypeId() {
            return taskTypeId;
        }
    }

    public enum Message {
        ROSE_SEND(1008, 2), ROSE_RECEIVE(1009, 1), TASK(1010), TASK_STATS(1018), TASK_SWITCH(
                1011, 3), INTERRUPT(1016), INT_BEGIN(1014, 4), INT_END(1015, 5), TASK_INSTANCE(
                -1, 6), TASK_RELEASE(1012, 7), TASK_COMPLETE(1013, 8), TIME_REF(
                1017, 9), UML_SEND(1020), UML_RECEIVE(1021), UML_EXAMPLE_DATA(
                1004), FUNCTION(1022), FUNCTION_ENTER(1023, 10), FUNCTION_EXIT(
                1024, 11), OSE_SEND(666, 12), OSE_RECIEVE(666, 13), OSE_CREATE(
                666, 14), OSE_KILL(666, 15), OSE_ERROR(666, 16), OSE_ALLOC(666,
                17), OSE_FREE(666, 18), OSE_RESET(666, 19), OSE_LOSS(666, 20), OSE_USER(
                666, 21), OSE_BIND(666, 22), OSE_SWAP(666, 23), OSE_TIMEOUT(
                666, 24);

        private int srlTypeId;

        private int binTypeId;

        private Message(final int srlTypeId) {
            this.srlTypeId = srlTypeId;
            binTypeId = -1;
        }

        private Message(final int srlTypeId, final int binTypeId) {
            this.srlTypeId = srlTypeId;
            this.binTypeId = binTypeId;
        }

        public int srlTypeId() {
            return srlTypeId;
        }

        public int binTypeId() {
            return binTypeId;
        }
    }

    public BBBinWriter(final DataOutputStream bin) throws FileNotFoundException {
        this.bin = bin;
        //stringList = new ArrayList<String>();
    }

    public void close() throws IOException {
        if (bin != null) {
            bin.close();
        }
    }

    public void writeHeader() throws IOException {
        bin.writeInt(BBBinWriter.MAGIC_CONSTANT);
        bin.writeInt(BBBinWriter.BIN_FORMAT_VERSION);
        bin.write(reserved);
    }

    // public void writeStrings() throws IOException {
    // if (stringList != null) {
    // bin.writeInt(stringList.size());
    // for (final String string : stringList) {
    // final byte[] bytes = string.getBytes();
    // bin.writeInt(bytes.length);
    // bin.write(bytes);
    // }
    // }
    // }

    public void writeUserStructs() throws IOException {
        bin.writeInt(0);
    }

    public void writeTaskStatsTable() throws IOException {
        bin.writeInt(0);
    }

    public void writeTaskTable(final Set<Entry<Long, GenericTask>> taskSet,
            final TaskType taskType) throws IOException {
        bin.writeInt(taskSet.size());
        bin.writeInt(taskType.taskTypeId);
        for (final Entry<Long, GenericTask> task : taskSet) {
            bin.writeInt((int) task.getValue().getTaskId());
            // bin.writeInt(getStringIx(task.getValue().getName()));
            bin.writeInt(task.getValue().getName().length());
            bin.write(task.getValue().getName().getBytes());
            bin.writeInt(task.getValue().getPriority());
            // bin.writeBoolean(task.getValue().hasExecutedDuringLog);
            bin.writeBoolean(false);
        }
    }

    public void writeStateMachineMap() throws IOException {
        bin.writeInt(0);
    }

    public void writeNrOfEvents(final int nrOfEvents) throws IOException {
        bin.writeInt(nrOfEvents);
    }

    public void setOutputStream(final DataOutputStream bin) {
        this.bin = bin;
    }

    // public int getStringIx(final String string) {
    // int indexOf;
    // if (string == null) {
    // stringList.add(NULL);
    // indexOf = stringList.indexOf(NULL);
    // } else {
    // indexOf = stringList.indexOf(string);
    // if (indexOf < 0) {
    // stringList.add(string);
    // indexOf = stringList.indexOf(string);
    // }
    // }
    // return indexOf;
    // }

    public void writeOSESend(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_SEND.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEReceive(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_RECIEVE.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());

    }

    public void writeOSESwap(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_SWAP.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSECreateProc(final long ts, final int tick,
            final int nTick, final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_CREATE.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEKillProc(final long ts, final int tick,
            final int nTick, final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_KILL.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEError(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_ERROR.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEFree(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_FREE.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEAlloc(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_ALLOC.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEReset(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_RESET.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSELoss(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_LOSS.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEUserEvent(final long ts, final int tick,
            final int nTick, final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_USER.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSEBind(final long ts, final int tick, final int nTick,
            final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_BIND.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }

    public void writeOSETimeoutEvent(final long ts, final int tick,
            final int nTick, final ByteBuffer bb) throws IOException {
        bin.writeLong(ts);
        bin.writeInt(Message.OSE_TIMEOUT.binTypeId());
        bin.writeInt(tick);
        bin.writeInt(nTick);
        bin.write(bb.array());
    }
}
