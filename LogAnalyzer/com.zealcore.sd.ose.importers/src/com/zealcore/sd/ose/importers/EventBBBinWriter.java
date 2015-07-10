package com.zealcore.sd.ose.importers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.SymbolManager;
import com.ose.sigdb.Attribute;
import com.ose.sigdb.CompositeAttribute;
import com.ose.sigdb.SignalAttribute;
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
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.importers.BBBinWriter;
import com.zealcore.se.core.importers.BBBinWriter.TaskType;
import com.zealcore.se.core.model.generic.GenericTask;
import com.zealcore.se.core.util.TimestampManager;

public class EventBBBinWriter {

    private static final String NTICK = ", nTick = ";

    private static final String TICK = ". tick = ";

    private static final String ILLEGAL_TIMESTAMP_FOR_EVENT_WITH_REFERENCE_NR = "Illegal timestamp for event with reference nr: ";

    private static final String TIMESTAMPS_CAN_T_BE_NEGATIVE = "Timestamps can't be negative.";

    // 2L ^ 32L) - 1L;
    private static final long UINT_MAX = 4294967295L;

    private final SymbolManager symbolManager;

    private BBBinWriter binWriter;

    private boolean bigEndian;

    //
    // private String target;
    //
    // private String scope;
    //
    // private String eventActions;

    private int tickLength;

    private int microTickFrequency;

    private int nrOfEvents;

    private Map<Long, GenericTask> processList;

    // private long previousTick = Long.MIN_VALUE;

    private long previousTs = Long.MIN_VALUE;

    private int cnt = -1;

    private long previousTick = Long.MIN_VALUE;

    public EventBBBinWriter(final DataOutputStream bin, final boolean trace)
            throws IOException {
        binWriter = new BBBinWriter(bin);
        processList = new HashMap<Long, GenericTask>();

        if (EventPlugin.getDefault() != null) {
            symbolManager = EventPlugin.getSymbolManager();
        } else {
            symbolManager = null;
        }
        nrOfEvents = 0;
    }

    public void write(final TargetEvent event) throws IOException {
        if (event instanceof SendEvent) {
            writeSendEvent((SendEvent) event);
        } else if (event instanceof ReceiveEvent) {
            writeReceiveEvent((ReceiveEvent) event);
        } else if (event instanceof SwapEvent) {
            writeSwapEvent((SwapEvent) event);
        } else if (event instanceof CreateEvent) {
            writeCreateEvent((CreateEvent) event);
        } else if (event instanceof KillEvent) {
            writeKillEvent((KillEvent) event);
        } else if (event instanceof ErrorEvent) {
            writeErrorEvent((ErrorEvent) event);
        } else if (event instanceof BindEvent) {
            writeBindEvent((BindEvent) event);
        } else if (event instanceof AllocEvent) {
            writeAllocEvent((AllocEvent) event);
        } else if (event instanceof FreeEvent) {
            writeFreeEvent((FreeEvent) event);
        } else if (event instanceof UserEvent) {
            writeUserEvent((UserEvent) event);
        } else if (event instanceof ResetEvent) {
            writeResetEvent((ResetEvent) event);
        } else if (event instanceof LossEvent) {
            writeLossEvent((LossEvent) event);
        } else if (event instanceof TimeoutEvent) {
            writeTimeoutEvent((TimeoutEvent) event);
        } else if (event == null) {
            throw new IllegalArgumentException("Event was null!!!");
        } else {
            MessageDialog
                    .openInformation(
                            new Shell(),
                            "Unknown event type was found",
                            "Unknown event type: "
                                    + event.toString()
                                    + " with entry: "
                                    + event.getReference()
                                    + "was found. Will ignore this event and continue with the import.");
        }
    }

    private void writeTimeoutEvent(final TimeoutEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(3 * 4);

        bb.putInt(event.getTimeout());
        bb.putInt(event.getSystemCall());
        int ref = event.getReference();
        bb.putInt(ref);
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSETimeoutEvent(ts, event.getTick(), event
                    .getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeUserEvent(final UserEvent event) throws IOException {
        byte[] signalData = event.getEventData();
        ByteBuffer eventDataBytes = extractEventDataToByteBuffer(signalData,
                event);
        ByteBuffer bb = ByteBuffer.allocate(3 * 4 + eventDataBytes.capacity());
        bb.putInt(event.getEventNumber());
        bb.putInt(event.getEventDataSize());
        // int stringIx = binWriter.getStringIx(event.getFormattedEventData());
        // bb.putInt(stringIx);

        bb.put(eventDataBytes.array());
        int ref = event.getReference();
        bb.putInt(ref);
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEUserEvent(ts, event.getTick(), event.getNTick(),
                    bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeLossEvent(final LossEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(2 * 4);
        bb.putInt(event.getLostSize());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSELoss(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;

    }

    private void writeResetEvent(final ResetEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(1 * 4);
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEReset(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeFreeEvent(final FreeEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(6 * 4);
        ProcessInfo proc = event.getProcess();
        storeProcess(proc);
        bb.putInt(proc.getId());
        // bb.putInt(binWriter.getStringIx(proc.getName()));
        bb.putInt(event.getSignalAddress());
        bb.putInt(event.getSignalId());
        bb.putInt(event.getSignalNumber());
        bb.putInt(event.getSignalSize());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEFree(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeAllocEvent(final AllocEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(6 * 4);
        ProcessInfo proc = event.getProcess();
        storeProcess(proc);
        bb.putInt(proc.getId());
        // bb.putInt(binWriter.getStringIx(proc.getName()));
        bb.putInt(event.getSignalAddress());
        bb.putInt(event.getSignalId());
        bb.putInt(event.getSignalNumber());
        bb.putInt(event.getSignalSize());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEAlloc(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeBindEvent(final BindEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(3 * 4);
        ProcessInfo proc = event.getProcess();
        storeProcess(proc);
        bb.putInt(proc.getId());
        // bb.putInt(binWriter.getStringIx(proc.getName()));
        bb.putInt(event.getToExecutionUnit());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEBind(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeErrorEvent(final ErrorEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(5 * 4 + Boolean.toString(
                event.isExecutive()).length());
        ProcessInfo proc = event.getProcess();
        storeProcess(proc);

        bb.putInt(proc.getId());
        // bb.putInt(binWriter.getStringIx(proc.getName()));
        bb.putInt(event.getError());
        // bb.putInt(binWriter.getStringIx(Boolean.toString(event.isExecutive())));
        bb.putInt(Boolean.toString(event.isExecutive()).length());
        byte[] bytes = Boolean.toString(event.isExecutive()).getBytes();
        bb.put(bytes);
        bb.putInt(event.getExtra());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEError(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeKillEvent(final KillEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(3 * 4);
        ProcessInfo killed = event.getKilledProcess();
        storeProcess(killed);
        ProcessInfo killer = event.getKillerProcess();
        storeProcess(killer);
        bb.putInt(killed.getId());
        // bb.putInt(binWriter.getStringIx(killed.getName()));
        if (killer == null) {
            bb.putInt(-1);
            // bb.putInt(binWriter.getStringIx(null));
        } else {
            bb.putInt(killer.getId());
            // bb.putInt(binWriter.getStringIx(killer.getName()));
        }
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSEKillProc(ts, event.getTick(), event.getNTick(),
                    bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeCreateEvent(final CreateEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(3 * 4);
        ProcessInfo created = event.getCreatedProcess();
        storeProcess(created);
        ProcessInfo creator = event.getCreatorProcess();
        storeProcess(creator);

        bb.putInt(created.getId());
        // bb.putInt(binWriter.getStringIx(created.getName()));

        if (creator == null) {
            bb.putInt(-1);
            // bb.putInt(binWriter.getStringIx(null));
        } else {
            bb.putInt(creator.getId());
            // bb.putInt(binWriter.getStringIx(creator.getName()));
        }
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSECreateProc(ts, event.getTick(), event.getNTick(),
                    bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeSwapEvent(final SwapEvent event) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(4 * 4);

        ProcessInfo out = event.getSwappedOutProcess();
        bb.putInt(out.getId());
        storeProcess(out);
        // bb.putInt(binWriter.getStringIx(out.getName()));
        ProcessInfo in = event.getSwappedInProcess();
        storeProcess(in);
        bb.putInt(in.getId());
        // bb.putInt(binWriter.getStringIx(in.getName()));
        // prio
        bb.putInt(-0);
        // bb.putInt(event.getExecutionUnit());
        bb.putInt(event.getReference());

        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSESwap(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeReceiveEvent(final ReceiveEvent event) throws IOException {
        byte[] signalData = event.getSignalData();
        ByteBuffer signalBb = extractSignalDataToByteBuffer(signalData, event);
        ByteBuffer bb = ByteBuffer.allocate(7 * 4 + signalBb.capacity());

        bb.putInt(event.getSenderProcess().getId());
        // bb.putInt(binWriter.getStringIx(event.getSenderProcess().getName()));
        bb.putInt(event.getAddresseeProcess().getId());
        // bb.putInt(binWriter.getStringIx(event.getAddresseeProcess().getName()));
        storeProcess(event.getAddresseeProcess());
        storeProcess(event.getSenderProcess());
        bb.putInt(event.getExecutionUnit());
        bb.putInt(event.getSignalNumber());
        bb.putInt(event.getSignalAddress());
        bb.putInt(event.getSignalSize());

        // for (int i = 0; i < signalBb.capacity(); i++) {
        // bb.put(signalBb.get(i));
        // }
        bb.put(signalBb.array());
        // varför funkar inte detta? bb.put(signalBb);

        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter
                    .writeOSEReceive(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private void writeSendEvent(final SendEvent event) throws IOException {
        byte[] signalData = event.getSignalData();
        ByteBuffer signalBb = extractSignalDataToByteBuffer(signalData, event);
        ByteBuffer bb = ByteBuffer.allocate(7 * 4 + signalBb.capacity());
        bb.putInt(event.getSenderProcess().getId());

        // bb.putInt(binWriter.getStringIx(event.getSenderProcess().getName()));
        storeProcess(event.getAddresseeProcess());
        storeProcess(event.getSenderProcess());
        bb.putInt(event.getAddresseeProcess().getId());
        // bb.putInt(binWriter.getStringIx(event.getAddresseeProcess().getName()));
        bb.putInt(event.getExecutionUnit());
        bb.putInt(event.getSignalNumber());
        bb.putInt(event.getSignalAddress());
        bb.putInt(event.getSignalSize());
        // for (int i = 0; i < signalBb.capacity(); i++) {
        // bb.put(signalBb.get(i));
        // }
        bb.put(signalBb.array());
        bb.putInt(event.getReference());
        try {
            long ts = getTsFromEvent(event);
            binWriter.writeOSESend(ts, event.getTick(), event.getNTick(), bb);
        } catch (IllegalStateException e) {
            return;
        }
        nrOfEvents++;
    }

    private ByteBuffer extractSignalDataToByteBuffer(final byte[] signalData,
            final TargetEvent event) {
        ByteBuffer nbb = ByteBuffer.allocate(0);
        int signalNumber = 0;
        String formattedSignalData = "";
        if (event instanceof ReceiveEvent) {
            ReceiveEvent event2 = (ReceiveEvent) event;
            signalNumber = event2.getSignalNumber();
            formattedSignalData = event2.getFormattedSignalData();

        } else if (event instanceof SendEvent) {
            SendEvent event2 = (SendEvent) event;
            signalNumber = event2.getSignalNumber();
            formattedSignalData = event2.getFormattedSignalData();
        }
        /**
         * Use symbolManager if available
         */
        if (symbolManager != null) {
            int byteOrder = (bigEndian ? EndianConstants.BIG_ENDIAN
                    : EndianConstants.LITTLE_ENDIAN);
            com.ose.sigdb.Signal signal = symbolManager.getSignal(signalNumber,
                    signalData, byteOrder);
            if ((signalData.length > 0) || (signal != null)) {
                if (signal != null) {
                    String interpretedData = writeInterpretedData(signal);
                    nbb = ByteBuffer.allocate(2 * 4 + signal.getName().length()
                            + interpretedData.length());
                    nbb.putInt(signal.getName().length());
                    nbb.put(signal.getName().getBytes());
                    nbb.putInt(interpretedData.length());
                    nbb.put(interpretedData.getBytes());
                } else {
                    /*
                     * We don't have a signal name, use signal nr as "name"
                     */
                    String dataInHex = writeBinaryData(signalData);

                    // Funkar det här med hex
                    nbb = ByteBuffer.allocate(2 * 4
                            + String.valueOf(signalNumber).length()
                            + dataInHex.length());
                    nbb.putInt(String.valueOf(signalNumber).length());
                    nbb.put(String.valueOf(signalNumber).getBytes());
                    nbb.putInt(dataInHex.length());
                    nbb.put(dataInHex.getBytes());
                }
            } else {
                if (formattedSignalData != null) {
                    /*
                     * We don't have a signal name, use signal nr as "name"
                     */
                    nbb = ByteBuffer.allocate(2 * 4
                            + String.valueOf(signalNumber).length()
                            + formattedSignalData.length());
                    nbb.putInt(String.valueOf(signalNumber).length());
                    nbb.put(String.valueOf(signalNumber).getBytes());
                    nbb.putInt(formattedSignalData.length());
                    nbb.put(formattedSignalData.getBytes());
                } else {
                    /*
                     * We don't have a signal name, use signal nr as "name"
                     */
                    String dataInHex = writeBinaryData(signalData);
                    nbb = ByteBuffer.allocate(2 * 4
                            + String.valueOf(signalNumber).length()
                            + dataInHex.length());
                    nbb.putInt(String.valueOf(signalNumber).length());
                    nbb.put(String.valueOf(signalNumber).getBytes());
                    nbb.putInt(dataInHex.length());
                    // Hallå, fungerar detta?
                    nbb.put(dataInHex.getBytes());
                }
            }
        } else {
            /**
             * No SymbolManager is available
             */
            if (formattedSignalData != null) {
                /*
                 * We don't have a signal name, use signal nr as "name"
                 */
                nbb = ByteBuffer.allocate(2 * 4
                        + String.valueOf(signalNumber).length()
                        + formattedSignalData.length());
                nbb.putInt(String.valueOf(signalNumber).length());
                nbb.put(String.valueOf(signalNumber).getBytes());
                nbb.putInt(formattedSignalData.length());
                nbb.put(formattedSignalData.getBytes());
            } else {
                /*
                 * We don't have a signal name, use signal nr as "name"
                 */
                String dataInHex = writeBinaryData(signalData);
                nbb = ByteBuffer.allocate(2 * 4
                        + String.valueOf(signalNumber).length()
                        + dataInHex.length());
                nbb.putInt(String.valueOf(signalNumber).length());
                nbb.put(String.valueOf(signalNumber).getBytes());
                nbb.putInt(dataInHex.length());
                nbb.put(dataInHex.getBytes());
            }
        }
        return nbb;
    }

    private ByteBuffer extractEventDataToByteBuffer(final byte[] eventData,
            final UserEvent event) {

        ByteBuffer bb = null;
        String formattedSignalData = "";

        formattedSignalData = event.getFormattedEventData();

        /**
         * Use symbolManager if available
         */
        if (symbolManager != null) {
            int byteOrder = (bigEndian ? EndianConstants.BIG_ENDIAN
                    : EndianConstants.LITTLE_ENDIAN);
            CompositeAttribute struct;
            struct = symbolManager.getEvent(event.getEventNumber(), eventData,
                    byteOrder);
            if ((eventData.length > 0) || (struct != null)) {
                if (struct != null) {
                    // bb.putInt(binWriter.getStringIx(struct.getName()));
                    // det här fungerar inte
                    String interpretedData = writeInterpretedData(struct);
                    bb = ByteBuffer.allocate((4 + struct.getName().length())
                            + (4 + interpretedData.length()));
                    bb.putInt(struct.getName().length());
                    bb.put(struct.getName().getBytes());
                    // bb.putInt(binWriter.getStringIx(interpretedData));
                    bb.putInt(interpretedData.length());
                    bb.put(interpretedData.getBytes());
                } else {
                    /*
                     * We don't have a event data name
                     */
                    String dataInHex = writeBinaryData(eventData);
                    bb = ByteBuffer.allocate((4) + (4 + dataInHex.length()));

                    bb.putInt(0);
                    // bb.putInt(binWriter.getStringIx(dataInHex));
                    bb.putInt(dataInHex.length());
                    byte[] bytes = dataInHex.getBytes();
                    bb.put(bytes);
                }
            } else {
                if (formattedSignalData != null) {
                    /*
                     * We don't have a event data name
                     */
                    bb = ByteBuffer.allocate((4) + (4 + formattedSignalData
                            .length()));
                    bb.putInt(0);
                    // bb.putInt(binWriter.getStringIx(formattedSignalData));
                    bb.putInt(formattedSignalData.length());
                    bb.put(formattedSignalData.getBytes());
                } else {
                    /*
                     * We don't have a event data name
                     */
                    String dataInHex = writeBinaryData(eventData);
                    bb = ByteBuffer.allocate((4) + (4 + dataInHex.length()));
                    bb.putInt(0);
                    // bb.putInt(binWriter.getStringIx(dataInHex));
                    bb.putInt(dataInHex.length());
                    bb.put(dataInHex.getBytes());
                }
            }
        } else {
            /**
             * No SymbolManager is available
             */
            if (formattedSignalData != null) {
                /*
                 * We don't have a event data name
                 */
                bb = ByteBuffer.allocate((4) + (4 + formattedSignalData
                        .length()));
                bb.putInt(-1);
                // bb.putInt(binWriter.getStringIx(formattedSignalData));
                bb.putInt(formattedSignalData.length());
                bb.put(formattedSignalData.getBytes());
            } else {
                /*
                 * We don't have a event data name
                 */
                String dataInHex = writeBinaryData(eventData);
                bb = ByteBuffer.allocate((4) + (4 + dataInHex.length()));
                bb.putInt(-1);
                // bb.putInt(binWriter.getStringIx(dataInHex));
                bb.putInt(dataInHex.length());
                bb.put(dataInHex.getBytes());
            }
        }
        return bb;
    }

    private void storeProcess(final ProcessInfo proc) {
        if (proc != null) {
            if (!processList.containsKey(Long.valueOf(proc.getId()))) {
                String name = proc.getName() + " (0x"
                        + Integer.toHexString(proc.getId()) + ")";
                // store process name
                // binWriter.getStringIx(name);
                GenericTask process = new GenericTask(name);
                process.setTypeName("OSEProcess");
                process.setTaskId(Long.valueOf(proc.getId()));
                process.addProperty("Bid", proc.getBid());
                process.addProperty("Entrypoint", proc.getEntrypoint());
                process.addProperty("Properties", proc.getProperties());
                process.addProperty("Sid", proc.getSid());
                process.addProperty("Target", proc.getTarget());
                process.addProperty("Type", proc.getType());
                processList.put(process.getTaskId(), process);
            }
        }
    }

    private String writeInterpretedData(final CompositeAttribute attribute) {
        final StringBuilder builder = new StringBuilder();
        builder.append(attribute.getName() + "{");

        for (Iterator i = attribute.getAttributes().iterator(); i.hasNext();) {
            Attribute attrib = (Attribute) i.next();
            if (attrib instanceof CompositeAttribute) {
                writeInterpretedData((CompositeAttribute) attrib);
            } else if (attrib instanceof SignalAttribute) {
                builder.append(attrib.toString() + ";");
            }
        }
        builder.append("}");

        return builder.toString();
    }

    private long getTsFromEvent(final TargetEvent event) {
        boolean isLossEvent = false;
        if (event instanceof LossEvent) {
            isLossEvent = true;
        }
        return getTsFromParams(microTickFrequency, tickLength, event.getTick(),
                event.getNTick(), event.getSeconds(), event.getSecondsTick(),
                isLossEvent, event.getReference());
    }

    protected long getTsFromParams(final long microTickFrequency,
            final long tickLength, final long tick, final long nTick,
            final long seconds, final long secondsTick,
            final boolean isLossEvent, final long reference) {
        long ts = Long.MIN_VALUE;

        if (tick < 0 || nTick < 0) {
            throw new ImportException(
                    ILLEGAL_TIMESTAMP_FOR_EVENT_WITH_REFERENCE_NR + reference
                            + TICK + tick + NTICK + nTick);
        }

        /*
         * If tickLength is 0 => use counter timestamps (This is not a normal
         * case, should not occur)
         */
        if (tickLength <= 0) {
            cnt++;
            this.previousTs = cnt;
            return cnt;
        }

        // TODO: Investigate if we can use the time stamp in loss event.
        if (isLossEvent) {
            ts = previousTs;
            return ts;
        } else if (seconds != 0) {
            /**
             * Calculate absolute time stamp.
             */
            long absoluteTsInMs = seconds * TimestampManager.MILLIS_PER_SECOND;
            long secTick = secondsTick;
            long curTick = tick;
            long tickLen = tickLength;
            // tick Wrapped?
            if (curTick < secTick) {
                curTick += 0x100000000L;
            }
            absoluteTsInMs += ((curTick - secTick) * tickLen)
                    / TimestampManager.MILLIS_PER_SECOND;
            long nTickInNs = nTick * TimestampManager.NANOS_PER_SECOND
                    / microTickFrequency;
            // We add microtick to get better precision, also convert to ns
            ts = (absoluteTsInMs * TimestampManager.NANOS_PER_MILLI)
                    + nTickInNs;
            // Save previous ts for loss event
            this.previousTs = ts;
        } else {
            /**
             * Calculate relative time stamp based on tick and nTick
             */
            long tickInNs = 0;
            /*
             * If we have tick overflow...
             */
            if (tick < previousTick) {
                /*
                 * There is a bug in the RMM that occurs high values on the tick
                 * counter. If the difference is larger than 1000, we will
                 * assume that it is the bug and not a normal overflow. WHAT BUG
                 * IS THIS REFERING TO?
                 */
                if (tick + UINT_MAX - previousTick > 1000) {
                    return previousTs;
                }
                tickInNs = (tick + UINT_MAX) * tickLength
                        * TimestampManager.NANOS_PER_MICRO;
            } else {
                tickInNs = tick * tickLength * TimestampManager.NANOS_PER_MICRO;
            }

            long nTickInNs = 0;
            if (microTickFrequency != 0) {
                nTickInNs = nTick * TimestampManager.NANOS_PER_SECOND
                        / microTickFrequency;
            }
            ts = tickInNs + nTickInNs;
            // Save previous ts for loss event
            this.previousTs = ts;
            // Save previous tick to verify wrap
            this.previousTick = tick;
        }

        if (ts < 0) {
            throw new IllegalStateException(TIMESTAMPS_CAN_T_BE_NEGATIVE);
        }

        return ts;
    }

    public void close() throws IOException {
        if (binWriter != null) {
            binWriter.close();
        }
        this.previousTs = Long.MIN_VALUE;
        this.previousTick = Long.MIN_VALUE;
        this.cnt = -1;
    }
    
    public void stopImporting(final ImportContext importContext) throws IOException {
        //importContext.getLogset().setLock(false);
        if (binWriter != null) {
            binWriter.close();
            binWriter = null;
        }
        throw new ImportException("Cancelled");            
    }


    public void setOutputStream(final DataOutputStream bin) {
        if (binWriter != null) {
            try {
                close();
            } catch (IOException e) {}
        }
        binWriter.setOutputStream(bin);
    }

    public void writeCommonAttributes(final String target,
            final boolean bigEndian, final String scope,
            final String eventActions, final int tickLength,
            final int microTickFrequency, final int osType) throws IOException {
        this.bigEndian = bigEndian;
        // this.target = target;
        // this.scope = scope;
        // this.eventActions = eventActions;
        // this.osType = osType;
        this.tickLength = tickLength;
        this.microTickFrequency = microTickFrequency;
        // TODO Take care of string arguments, e.g. target etc.
        binWriter.writeHeader();
        // binWriter.writeStrings();
        binWriter.writeUserStructs();
        binWriter.writeTaskStatsTable();
        binWriter.writeTaskTable(processList.entrySet(), TaskType.PROCESS);
        binWriter.writeStateMachineMap();
        binWriter.writeNrOfEvents(nrOfEvents);
    }

    private String writeBinaryData(final byte[] data) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            builder.append(toU8HexString(data[i]));
            if (i < (data.length - 1)) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    private static String toU8HexString(final byte b) {
        String s = Integer.toHexString(b & 0xFF).toUpperCase();
        if (s.length() < 2) {
            s = "0" + s;
        }
        return s;
    }
}
