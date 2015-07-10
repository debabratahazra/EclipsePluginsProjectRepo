package com.zealcore.srl.offline;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class FcpTransformer implements ITransformer {

    private static final int SIZEOF_POINTER = 4;

    private static final int SIZEOF_MESSAGECOUNT = 4;

    private static final int NUM_STATIC_MESSAGES = 3;

    static final class FcpHeader {
        static final int SIZE_IN_BYTES = 36;

        private static final int STRUCT_ID_BEGIN = 1000;

        static FcpHeader read(final LinearMessage message) {
            return new FcpHeader(message);
        }

        private final ByteBuffer data;

        private final int circTypeId;

        private final long msgSize;

        private final long msgCount;

        private final long offset;

        private final long voidPattern;

        private FcpHeader(final LinearMessage message) {
            data = message.getData();
            circTypeId = message.getTypeId()
                    - (FCP_ID_THRESHOLD - STRUCT_ID_BEGIN);

            // int voidPattern =
            voidPattern = data.getInt() & UINT_MASK;
            msgSize = data.getInt() & UINT_MASK;
            msgCount = data.getInt() & UINT_MASK;
            // int runBit =
            data.getInt();
            final long pEnd = data.getInt() & UINT_MASK;
            final long pNext = data.getInt() & UINT_MASK;
            // skip pBegin
            data.getInt();
            offset = pNext - pEnd;
            // int pRunBitMask =
            data.getInt();
        }
    }

    private static final long UINT32_NUM_BITS = 32;

    private static final int HIGH_LOW_SIZE = 8;

    public static final long UINT_MASK = 0xFFFFFFFFL;

    private static final int FCP_ID_THRESHOLD = 8000;

    /*
     * CPU frequency in MHz
     */
//    private static final int CPU_FREQ = 33;

    private boolean isFcp(final LinearMessage message) {
        return message.getTypeId() >= FCP_ID_THRESHOLD;
    }

    private CircularMessage readMessage(final FcpHeader fcp) {
        final long high = fcp.data.getInt() & UINT_MASK;
        final long low = fcp.data.getInt() & UINT_MASK;

        final ByteBuffer circData = ByteBuffer
                .allocate((int) (fcp.msgSize - HIGH_LOW_SIZE));
        circData.order(fcp.data.order());
        fcp.data.get(circData.array());
        final CircularMessage circularMessage = new CircularMessage();
        circularMessage.setData(circData);
        final long highConverted = high << UINT32_NUM_BITS;
        long timestamp = highConverted + low;

        // timestamp = (timestamp * 1000) / CPU_FREQ;
        circularMessage.setTs(timestamp);
        final CircularMessage transformed = circularMessage;
        transformed.setTypeId(fcp.circTypeId);
        final CircularMessage msg = transformed;

        if (high == fcp.voidPattern) {
            return null;
        }

        return msg;
    }

    public void transform(final Blackbox box) {
        for (final Iterator<LinearMessage> iter = box.getLinearMessages()
                .iterator(); iter.hasNext();) {
            final LinearMessage message = iter.next();
            if (isFcp(message)) {
                box.addCircularMessages(transformFcp(message));
                iter.remove();
            }
        }
    }

    private List<CircularMessage> transformFcp(final LinearMessage message) {
        final FcpHeader fcp = FcpHeader.read(message);
        if (fcp.offset > Integer.MAX_VALUE) {
            throw new IllegalStateException("Unable to handle this large FCP");
        }
        final List<CircularMessage> messages = new LinkedList<CircularMessage>();
        final long messageArrayPosition = FcpHeader.SIZE_IN_BYTES
                + SIZEOF_MESSAGECOUNT + NUM_STATIC_MESSAGES * SIZEOF_POINTER;
        fcp.data.position((int) messageArrayPosition);

        for (int i = 0; i < fcp.msgCount; i++) {
            final CircularMessage msg = readMessage(fcp);
            if (msg != null) {
                messages.add(msg);
            }
        }
        return messages;
    }
}
