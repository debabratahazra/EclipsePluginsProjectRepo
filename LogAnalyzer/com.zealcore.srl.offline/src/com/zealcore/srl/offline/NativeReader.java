package com.zealcore.srl.offline;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;

public class NativeReader implements ITransformer {

    private static final long UINT_MASK = 0xFFFFFFFFL;

    private static final int SIZEOF_TIMESTAMP = 8;

    private static final int ELEMENT_SIZE = 4;

    private static final long UINT32_NUM_BITS = 32;

    private final String filename;

    public NativeReader(final String filename) {
        this.filename = filename;
    }

    public void transform(final Blackbox blackbox) {
        if (blackbox == null) {
            throw new IllegalArgumentException();
        }
        DataInputStreamEx stream = null;
        try {
            stream = openFile(filename);
            final ByteBuffer buffer = loadFile(stream, blackbox);
            stream.close();
            blackbox.addLinearMessages(readLinearMessages(buffer, blackbox));
            blackbox
                    .addCircularMessages(readCircularMessages(buffer, blackbox));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static DataInputStreamEx openFile(final String filename)
            throws IOException {
        DataInputStreamEx stream = new DataInputStreamEx(
                new BufferedInputStream(new FileInputStream(filename)));
        final boolean isBigEndian = isBigEndian(stream);
        stream.close();
        stream = new DataInputStreamEx(new BufferedInputStream(
                new FileInputStream(filename)), isBigEndian);
        return stream;
    }

    private static boolean isBigEndian(final DataInputStreamEx stream)
            throws IOException {
        final byte endianByte = stream.readByte();
        return endianByte == (byte) 0xA5;
    }

    private static ByteBuffer loadFile(final DataInputStreamEx stream,
            final Blackbox blackbox) throws IOException {
        stream.skipBytes(1);

        Version version = Version.valueOf(stream.readByte(), stream.readByte(),
                stream.readByte());

        blackbox.setVersion(version);
        // Skip srlId

        if (blackbox.getVersion().compareTo(Version.valueOf(1, 0, 3)) > 0) {
            stream.skipBytes(4);
        }
        blackbox.setBufferSize(stream.readUnsignedLong());
        blackbox.setChecksum(stream.readUnsignedLong());

        blackbox.setMagicFunction(stream.readUnsignedLong());
        blackbox.setMagicString(stream.readUnsignedLong());
        blackbox.setMagicArray(stream.readUnsignedLong());

        stream.skipBytes(4);
        blackbox.setMinCircularBufElemCount(stream.readUnsignedLong());
        blackbox.setMarginElemCount(stream.readUnsignedLong());
        blackbox.setIBorder(stream.readUnsignedLong());
        blackbox.setElemCount(stream.readUnsignedLong());
        blackbox.setIEnd(stream.readUnsignedLong());
        long iNext = stream.readUnsignedLong();
        blackbox.setINext(iNext);
        blackbox.setINextOriginal(iNext);

        if (blackbox.getVersion().compareTo(Version.valueOf(1, 0, 0)) > 0) {
            // Skip LogDescriptor
            final int skip = stream.skipBytes(12);

            if (skip != 12) {
                throw new IllegalStateException();
            }
        }
        if (blackbox.getVersion().compareTo(Version.valueOf(1, 0, 3)) > 0) {
            // skip status
            int skip = stream.skipBytes(4);
            if (skip != 4) {
                throw new IllegalStateException();
            }
            // skip messageSize
            skip = stream.skipBytes(4);
            if (skip != 4) {
                throw new IllegalStateException();
            }
            // read messageBufferSize
            final int messageBufferSize = (int) stream.readUnsignedLong();
            skip = stream.skipBytes(messageBufferSize);
            if (skip != messageBufferSize) {
                throw new IllegalStateException();
            }
        }
        if (blackbox.getVersion().compareTo(
                Version.valueOf(1, 1, Integer.MAX_VALUE)) > 0) {
            // skip dyn typeId
            if (stream.skipBytes(4) != 4) {
                throw new IllegalStateException();
            }
        }
        final int bufferSize = (int) blackbox.getElemCount() * ELEMENT_SIZE;
        final ByteBuffer buffert = ByteBuffer.allocate(bufferSize);

        final byte[] buff = new byte[bufferSize];
        final long readBytes = stream.read(buff);
        if (readBytes != bufferSize) {
            throw new IllegalStateException(
                    "Error reading SrlBuffer. Expected " + bufferSize
                            + " but read " + readBytes);
        }
        buffert.put(buff);
        final ByteOrder byteOrder = stream.isBigEndian() ? ByteOrder.BIG_ENDIAN
                : ByteOrder.LITTLE_ENDIAN;
        blackbox.setByteOrder(byteOrder);
        buffert.order(byteOrder);
        return buffert;
    }

    private static Collection<LinearMessage> readLinearMessages(
            final ByteBuffer buffer, final Blackbox blackbox) {
        final ArrayList<LinearMessage> messages = new ArrayList<LinearMessage>();
        messages.add(getFirstLinearMessage(buffer, blackbox));
        LinearMessage message = getNextLinearMessage(buffer, blackbox);
        while (message != null) {
            messages.add(message);
            message = getNextLinearMessage(buffer, blackbox);
        }
        return messages;
    }

    private static Collection<CircularMessage> readCircularMessages(
            final ByteBuffer buffer, final Blackbox blackbox) {
        final ArrayList<CircularMessage> messages = new ArrayList<CircularMessage>();

        CircularMessage message = getCircularMessage(buffer, blackbox);
        while (message != null) {
            messages.add(message);
            message = getCircularMessage(buffer, blackbox);
        }
        return messages;
    }

    private static LinearMessage getFirstLinearMessage(final ByteBuffer buf,
            final Blackbox blackbox) {

        final int idx = 4 * (int) blackbox.getIBorder();
        buf.position(idx);
        return getNextLinearMessage(buf, blackbox);
    }

    private static LinearMessage getNextLinearMessage(final ByteBuffer buf,
            final Blackbox blackbox) {

        final int capacity = buf.capacity();
        final int position = buf.position();
        if (capacity <= position) {
            return null;
        }
        final LinearMessage message = new LinearMessage();
        message.setTypeId(buf.getInt());
        final int tmp = buf.getInt();
        Logger.log(tmp);
        final int size = 4 * tmp;
        final byte[] data = new byte[size];
        buf.get(data);

        final ByteBuffer blob = ByteBuffer.wrap(data);
        blob.order(buf.order());
        message.setData(blob);
        return message;
    }

    private static CircularMessage getCircularMessage(final ByteBuffer buf,
            final Blackbox blackbox) {
        long iNext = blackbox.getINext();
        long iNextOriginal = blackbox.getINextOriginal();
        long iEnd = blackbox.getIEnd();
        long iLastMessage;
        if (iEnd < iNextOriginal) {
            iEnd = iNextOriginal;
        }
                
        if(!blackbox.isCircularReadStarted()) {
            iLastMessage = iNextOriginal;
        } else {
            iLastMessage = iNext;
        }
        if(iLastMessage == 0) {
            iLastMessage = iEnd;
            
            if(iLastMessage == iNextOriginal) {
                if(!blackbox.isCircularReadStarted()) {
                    blackbox.stopCircularRead();
                    return null;
                }     
            }            
        }
        
        if(iLastMessage == iNextOriginal) {
            if(blackbox.isCircularReadStarted()) {
                blackbox.stopCircularRead();
                return null;
            }
        }
        
        boolean bMessageAboveNext = false;
        if(iLastMessage > iNextOriginal) {
            if((iLastMessage - iNextOriginal) < 2) {
                blackbox.stopCircularRead();
                return null;
            }
            bMessageAboveNext = true;
        }

        --iLastMessage;
        buf.position(4 * (int)iLastMessage);
        int typeId = buf.getInt();
        
        --iLastMessage;
        buf.position(4 * (int)iLastMessage);
        int elemCount = buf.getInt();

        iLastMessage -= elemCount;
        if(bMessageAboveNext) {
            if(iLastMessage < iNextOriginal) {
                blackbox.stopCircularRead();
                return null;
            }
        }
        
        buf.position(4 * (int)iLastMessage);

        int size = elemCount*4;
        if(size < SIZEOF_TIMESTAMP) {
            throw new IllegalStateException("illegal message without timeStamp");
        }

        CircularMessage message = new CircularMessage();
        
        long hi = buf.getInt() & UINT_MASK;
        long lo = buf.getInt() & UINT_MASK;
        long ts = (hi << UINT32_NUM_BITS) + lo;
        message.setTs(ts);
        
        final byte[] data = new byte[size-SIZEOF_TIMESTAMP];
        buf.get(data);
        final ByteBuffer blob = ByteBuffer.wrap(data);
        blob.order(buf.order());
        message.setData(blob);
        message.setTypeId(typeId);
        
        blackbox.setINext(iLastMessage);
        blackbox.startCircularRead();
        return message;
    }

}
