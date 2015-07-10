package com.zealcore.srl.offline;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class SingleEventGenerator {

    private SingleEventGenerator() {}

    private static final int NR_OF_EVENTS_DEFAULT_VALUE = 2270000;

    private static final int EVENT_DATA_SIZE_DEFAULT_VALUE = 30;

    private static final int FILE_VERSION = 3;

    private static int nrOfEvents;

    private static int eventDataSize;

    private long ts;

    private long writtenEvents;

    public static void main(final String[] args) throws Exception {
        System.out
                .println(">>>>>>>>>> Simple Event Log Generator V. 1.0 <<<<<<<<<<\n");
        if (args.length < 2) {
            nrOfEvents = NR_OF_EVENTS_DEFAULT_VALUE;
            eventDataSize = EVENT_DATA_SIZE_DEFAULT_VALUE;
            System.out
                    .println("No argument is specified, will use default value: "
                            + NR_OF_EVENTS_DEFAULT_VALUE
                            + " events"
                            + ", event data size is " + eventDataSize);
        } else {
            nrOfEvents = Integer.valueOf(args[0]);
            eventDataSize = Integer.valueOf(args[1]);
            System.out.println("Nr of events specified is " + nrOfEvents);
            System.out.println("Event data size specified is " + eventDataSize
                    + " bytes");
        }

        final File file = new File("simpleEventLog_" + nrOfEvents + "events_" + eventDataSize + "bytes" + ".bin");
        final long writtenEvents = new SingleEventGenerator().run(nrOfEvents,
                file);
        System.out.println("Nr of written events is " + writtenEvents);
        System.out
                .println("------------------------------------------------------------------------");
        System.out.println("Output file: " + file.getAbsolutePath());
    }

    private long run(final int numEvents, final File output) throws Exception {
        final OutputStream wr = new FileOutputStream(output);
        final DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(wr));

        ts = 0;
        writtenEvents = 0;
        writeHeader(out, numEvents, FILE_VERSION);

        while (writtenEvents < numEvents) {
            writeSimpleEvent(out, nextTs());
            writtenEvents++;
        }
        out.flush();
        out.close();
        return writtenEvents;
    }

    private void writeSimpleEvent(final DataOutputStream out, final long ts)
            throws IOException {
        out.writeLong(ts);
        out.writeInt(BinaryRosePrinter.Message.BENCHMARK.binTypeId());
        final String pong = getData();
        byte[] bytes = pong.getBytes();
        // stringlength
        out.writeInt(bytes.length);
        // string
        out.write(bytes);
    }

    private String getData() {
        // writtenEvents
        String nr = Long.toString(writtenEvents);

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < eventDataSize- nr.length(); i++) {
            buf.append('0');
        }
        buf.append(nr);
        return buf.toString();
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

        // Write nr of strings
        out.writeInt(0);

        writeTaskTable(out);

        // Write nr of statemachines
        out.writeInt(0);
        out.writeInt(numEvents);
    }

    private void writeTaskTable(final DataOutputStream out) throws IOException {
        // Write nr of taskstats
        out.writeInt(0);
        // write nr of tasks
        out.writeInt(0);
    }
}
