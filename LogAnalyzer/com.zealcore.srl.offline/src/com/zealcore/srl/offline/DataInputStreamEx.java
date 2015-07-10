package com.zealcore.srl.offline;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class DataInputStreamEx extends DataInputStream {

    private static final int BYTE_1 = 8;

    private static final int BYTE_2 = 16;

    private static final int BYTE_3 = 24;

    private boolean isBigEndian;

    public DataInputStreamEx(final InputStream in) {
        super(in);
    }

    public DataInputStreamEx(final InputStream in, final boolean isBigEndian) {
        super(in);
        this.isBigEndian = isBigEndian;
    }

    public long readUnsignedLong() throws IOException {
        long result = 0;
        final long byte1 = readUnsignedByte();
        final long byte2 = readUnsignedByte();
        final long byte3 = readUnsignedByte();
        final long byte4 = readUnsignedByte();
        if (isBigEndian) {
            // reverse order
            result = byte1 << BYTE_3;
            result |= byte2 << BYTE_2;
            result |= byte3 << BYTE_1;
            result |= byte4;
        } else {
            result = byte1;
            result |= byte2 << BYTE_1;
            result |= byte3 << BYTE_2;
            result |= byte4 << BYTE_3;
        }
        // if(_signed)
        // if( (result&0x80000000L) == 0x80000000L )
        // result = -(0x100000000L - result);
        return result;
    }

    public boolean isBigEndian() {
        return isBigEndian;
    }
}
