package com.zealcore.srl.offline;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public final class ResolveUtil {

    private static final long UINT_MASK = 0xFFFFFFFFL;

    private static final String BB_MAGIC_STRING = "zc123581321zc";

    private static Map<Long, SignalTable> signalTablesByPosition = new HashMap<Long, SignalTable>();

    private ResolveUtil() {}

    static String loadString(final long stringMem, final File module,
            final long magicMem, final long magicPhys) throws IOException {

        if (stringMem == 0) {
            return null;
        }
        final long distance = magicMem - stringMem;
        final long pos = magicPhys - distance;
        return readString(module, pos);
    }

    static long getMagicPhys(final File file) {
        long magicPhys = 0;
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));

            if (stream.available() < 1) {
                throw new IllegalArgumentException();
            }
            long bpos = 0;
            long fpos = 0;
            int compPos = 0;
            final long compLen = BB_MAGIC_STRING.length();
            final byte[] bytes = BB_MAGIC_STRING.getBytes();

            while (compPos < compLen) {
                final int c = stream.read();
                if (c == -1) {
                    break;
                }
                fpos++;
                if (c == bytes[compPos]) {
                    compPos++;
                } else {
                    compPos = 0;
                    if (c == bytes[0]) {
                        compPos = 1;
                    }
                }
                bpos = fpos;
            }
            if (compPos != compLen) {
                return 0;
            }
            magicPhys = bpos - compLen;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return magicPhys;
    }

    private static String readString(final File module, final long pos)
            throws IOException {
        if (module == null || !module.exists() || !module.canRead()) {
            throw new IllegalArgumentException();
        }
        DataInputStream stream = null;
        try {
            stream = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(module)));
            if (pos > stream.available()) {
                throw new IllegalStateException();
            }
            stream.skip(pos);
            return BuffUtil.getNullTermString(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    static String loadSignalName(final long signalNameMem, final long signalId,
            final File module, final Blackbox box, final ByteOrder order)
            throws IOException {

        final long magicMem = box.getMagicString();
        final long magicPhys = box.getMagicPhys();
        final long magicStructMem = box.getMagicArray();

        if (signalNameMem == 0) {
            return null;
        }
        String signalName = null;
        long distance = magicMem - magicStructMem;
        final long pos = magicPhys - distance;
        DataInputStreamEx stream = null;
        try {
            stream = openStream(module, order);
            if (pos > stream.available()) {
                throw new IllegalStateException();
            }
            stream.skip(pos);
            final long addrMem = stream.readUnsignedLong();

            distance = magicMem - signalNameMem;
            final long posSignalArray = magicPhys - distance;
            stream.close();

            if (module.getName().endsWith(".out")) {
                SignalTable signalTable = signalTablesByPosition
                        .get(posSignalArray);
                if (signalTable == null) {
                    signalTable = SignalTable.valueof(module, order,
                            posSignalArray);
                    signalTablesByPosition.put(posSignalArray, signalTable);
                }
                signalName = signalTable.getSignalName(signalId);
            } else {
                stream = openStream(module, order);
                stream.skip(posSignalArray);

                final ByteBuffer byteBuf = ByteBuffer.allocate(1000 * 12);
                byteBuf.order(order);
                stream.read(byteBuf.array());
                long signalNameRelMem = 0;
                boolean found = false;
                long cnt = 0;
                while (cnt < 1000) {
                    signalNameRelMem = byteBuf.getInt() & UINT_MASK;
                    byteBuf.getInt();
                    final long value = byteBuf.getInt() & UINT_MASK;
                    cnt++;
                    // stream.readInt();
                    // long value = stream.readUnsignedLong();
                    if (value == signalId) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return "NULL";
                }
                distance = addrMem - signalNameRelMem;
                final long signalNamePos = magicPhys - distance;
                signalName = readString(module, signalNamePos);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            // this could happen if we reach end of file
            // could be normal when a signal name is not found
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return signalName;
    }

    private static DataInputStreamEx openStream(final File module,
            final ByteOrder order) throws FileNotFoundException {
        return new DataInputStreamEx(new BufferedInputStream(
                new FileInputStream(module)), order
                .equals(ByteOrder.BIG_ENDIAN));
    }
}
