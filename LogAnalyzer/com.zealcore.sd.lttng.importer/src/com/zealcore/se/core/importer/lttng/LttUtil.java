package com.zealcore.se.core.importer.lttng;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LttUtil {

	public static long lttAlign(long alignDrift, int sizeOfType, int alignment) {
		if (alignment == 0)
			return 0;

		int alignOffset = (alignment < sizeOfType) ? alignment : sizeOfType;

		if (sizeOfType == 0) {
			System.out.println("size_of_type cannot be 0");
		}
		return ((alignOffset - alignDrift) & (alignOffset - 1));
	}

	public static int max(int a, int b) {
		return (a > b ? a : b);
	}

	public static short lttShortByteOrderValue(boolean reverseByteOrder,
			short value) {
		return (reverseByteOrder ? shortLittleEndianToBigEndian(value) : value);
	}

	public static int lttIntByteOrderValue(boolean reverseByteOrder, int value) {
		return (reverseByteOrder ? intLittleEndianToBigEndian(value) : value);
	}

	public static long lttLongByteOrderValue(boolean reverseByteOrder,
			long value) {
		return (reverseByteOrder ? longLittleEndianToBigEndian(value) : value);
	}

	// 2-byte number
	public static short shortLittleEndianToBigEndian(short i) {
		return (short) (((i >> 8) & 0xff) + ((i << 8) & 0xff00));
	}

	// 4-byte number
	public static int intLittleEndianToBigEndian(int i) {
		return ((i & 0xff) << 24) + ((i & 0xff00) << 8) + ((i & 0xff0000) >> 8)
				+ ((i >> 24) & 0xff);
	}

	// 8-byte number
	public static long longLittleEndianToBigEndian(long i) {
		ByteBuffer bbuf = ByteBuffer.allocate(8);
		bbuf.order(ByteOrder.LITTLE_ENDIAN);
		bbuf.putLong(i);
		bbuf.order(ByteOrder.BIG_ENDIAN);
		return bbuf.getLong(0);

	}
}
