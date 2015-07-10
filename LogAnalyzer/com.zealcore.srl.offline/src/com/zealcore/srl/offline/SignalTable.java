package com.zealcore.srl.offline;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class SignalTable {

    private static final int MAX_SIGNAL_TABLE_AREA_SIZE = 10000;

    private static final int MAX_SIGNAL_NAME_AREA_SIZE = 20000;

    private static final long UINT_MASK = 0xFFFFFFFFL;

    private Map<Long, String> signalNameMap;

    private SignalTable(final ByteBuffer signalNameArea,
            final ByteBuffer signalTableArea) {
        Collection<String> signalNames = createSignalNames(signalNameArea);
        signalNameMap = createSignalNameMap(signalTableArea, signalNames);
    }

    private Map<Long, String> createSignalNameMap(
            final ByteBuffer signalTableArea,
            final Collection<String> signalNames) {
        Map<Long, String> result = new HashMap<Long, String>();
        Iterator<String> signalNameIter = signalNames.iterator();
        for (int i = signalNames.size() - 1; i >= 0; --i) {
            int position = i * 12 + 8;
            final long signalId = signalTableArea.getInt(position) & UINT_MASK;
            String signalName = signalNameIter.next();
            result.put(signalId, signalName);
        }
        return result;
    }

    private Collection<String> createSignalNames(final ByteBuffer signalNameArea) {
        ArrayList<String> signalNames = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean isInString = false;
        for (int i = signalNameArea.capacity() - 1; i >= 0; --i) {
            byte b = signalNameArea.get(i);

            int size = signalNames.size();
            if (i % 4 == 0) {
                int n = signalNameArea.getInt(i - 4);
                if (n == size + 2) {
                    break;
                }
                if (n > 0) {
                    if (n == size) {
                        break;
                    }
                }
                if (n == size + 3) {
                    break;
                }
            }
            if (isInString) {
                if (b != 0) {
                    sb.append((char) b);
                } else {
                    isInString = false;
                    sb.reverse();
                    String signalName = sb.toString();
                    sb = new StringBuilder();
                    signalNames.add(signalName);
                }

            } else {
                if (b != 0) {
                    isInString = true;
                    sb.append((char) b);
                }
            }
        }
        return signalNames;
    }

    public static SignalTable valueof(final File exeFile,
            final ByteOrder order, final long posSignalArray)
            throws IOException {
        ByteBuffer signalNameArea;
        ByteBuffer signalTableArea;
        DataInputStreamEx stream = null;
        try {
            stream = openStream(exeFile, order);
            long startPosition = posSignalArray - MAX_SIGNAL_NAME_AREA_SIZE;
            int signalNameAreaSize = MAX_SIGNAL_NAME_AREA_SIZE;
            if (startPosition < 0) {
                signalNameAreaSize += startPosition;
                startPosition = 0;
            }
            stream.skip(startPosition);
            signalNameArea = createByteBuffer(order, stream, signalNameAreaSize);
            signalTableArea = createByteBuffer(order, stream,
                    MAX_SIGNAL_TABLE_AREA_SIZE);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return new SignalTable(signalNameArea, signalTableArea);
    }

    private static ByteBuffer createByteBuffer(final ByteOrder order,
            final DataInputStreamEx stream, final int signalNameAreaSize)
            throws IOException {
        ByteBuffer signalNameArea;
        signalNameArea = ByteBuffer.allocate(signalNameAreaSize);
        signalNameArea.order(order);
        stream.read(signalNameArea.array());
        return signalNameArea;
    }

    private static DataInputStreamEx openStream(final File module,
            final ByteOrder order) throws FileNotFoundException {
        return new DataInputStreamEx(new BufferedInputStream(
                new FileInputStream(module)), order
                .equals(ByteOrder.BIG_ENDIAN));
    }

    public String getSignalName(final long signalId) {
        String signalName = signalNameMap.get(signalId);
        return signalName;
    }
}
