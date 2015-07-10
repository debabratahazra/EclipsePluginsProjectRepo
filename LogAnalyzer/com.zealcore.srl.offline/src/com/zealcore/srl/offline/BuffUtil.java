package com.zealcore.srl.offline;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class BuffUtil {

    private BuffUtil() {}

    public static String getNullTermString(final ByteBuffer buffer) {
        byte b = buffer.get();
        final StringBuilder bld = new StringBuilder();
        while (b != 0) {
            bld.append((char) b);
            b = buffer.get();
        }
        return bld.toString();
    }

    public static String getNullTermString(final InputStream stream)
            throws IOException {
        byte b = (byte) stream.read();
        final StringBuilder bld = new StringBuilder();
        while (b > 0) {
            bld.append((char) b);
            b = (byte) stream.read();
        }
        return bld.toString();
    }
}
