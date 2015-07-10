package com.zealcore.se.core.importer.lttng;

import java.io.IOException;

public class EventParser {

	public long lttEventUnsignedInt(LttTraceFile ltf, LttEvent event,
			MarkerField field, long pos) throws IOException {
		boolean reverseByteOrder = false;

		if ((field.getAttributes() != MarkerField.LTT_ATTRIBUTE_NETWORK_BYTE_ORDER)) {
			reverseByteOrder = true;
		} else {
			reverseByteOrder = ltf.isReverseBo();
		}
		long x = 0;
		switch ((int) field.getSize()) {
		case 1: {
			x = ltf.readByteFromFile(pos + field.getOffset());

			break;
		}

		case 2:
			x = LttUtil.lttShortByteOrderValue(reverseByteOrder, ltf
					.readShortFromFile(pos + field.getOffset()));

			break;
		case 4:
			x = LttUtil.lttIntByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			x = x & 0x00000000ffffffff;
			break;
		case 8:
		default:
			System.out.println("lttEventUnsigned: field size unknown"
					+ field.getSize());
			break;

		}
		return x;
	}

	public int lttEventInt(LttTraceFile ltf, LttEvent event, MarkerField field,
			long pos) throws IOException {
		boolean reverseByteOrder = false;

		if ((field.getAttributes() != MarkerField.LTT_ATTRIBUTE_NETWORK_BYTE_ORDER)) {
			reverseByteOrder = true;
		} else {
			reverseByteOrder = ltf.isReverseBo();
		}
		int x = 0;
		switch ((int) field.getSize()) {
		case 1: {
			x = ltf.readByteFromFile(pos + field.getOffset());

			break;
		}

		case 2:
			x = LttUtil.lttShortByteOrderValue(reverseByteOrder, ltf
					.readShortFromFile(pos + field.getOffset()));

			break;
		case 4:
			x = LttUtil.lttIntByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			break;
		case 8:
		default:
			System.out.println("lttEventInt: field size unknown"
					+ field.getSize());
			break;
		}
		return x;

	}

	public long lttEventLongUnsigned(LttTraceFile ltf, LttEvent event,
			MarkerField field, long pos) throws IOException {
		boolean reverseByteOrder = false;

		if ((field.getAttributes() != MarkerField.LTT_ATTRIBUTE_NETWORK_BYTE_ORDER)) {
			reverseByteOrder = true;
		} else {
			reverseByteOrder = ltf.isReverseBo();
		}
		long x = 0;
		switch ((int) field.getSize()) {
		case 1: {
			x = ltf.readByteFromFile(pos + field.getOffset());

			break;
		}

		case 2:
			x = LttUtil.lttShortByteOrderValue(reverseByteOrder, ltf
					.readShortFromFile(pos + field.getOffset()));

			break;
		case 4:
			x = LttUtil.lttIntByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			x = x & 0x00000000ffffffffL;
			break;
		case 8:
			x = LttUtil.lttLongByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			x = x & 0x7fffffffffffffffL;
			break;
		default:
			System.out.println("lttEventLongUnsigned: field size unknown"
					+ field.getSize());
			break;

		}
		return x;
	}

	public long lttEventLongInt(LttTraceFile ltf, LttEvent event,
			MarkerField field, long pos) throws IOException {
		boolean reverseByteOrder = false;

		if ((field.getAttributes() != MarkerField.LTT_ATTRIBUTE_NETWORK_BYTE_ORDER)) {
			reverseByteOrder = true;
		} else {
			reverseByteOrder = ltf.isReverseBo();
		}
		long x = 0;
		switch ((int) field.getSize()) {
		case 1: {
			x = ltf.readByteFromFile(pos + field.getOffset());

			break;
		}

		case 2:
			x = LttUtil.lttShortByteOrderValue(reverseByteOrder, ltf
					.readShortFromFile(pos + field.getOffset()));

			break;
		case 4:
			x = LttUtil.lttIntByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			break;
		case 8:
			x = LttUtil.lttLongByteOrderValue(reverseByteOrder, ltf
					.readIntFromFile(pos + field.getOffset()));
			break;
		default:
			System.out.println("lttEventLongUnsigned: field size unknown"
					+ field.getSize());
			break;

		}
		return x;
	}

	public String lttEventString(LttTraceFile ltf, LttEvent event,
			MarkerField field, long pos) throws IOException {
		if (field.getType() != MarkerField.LttType.LTT_TYPE_STRING) {
			System.out.println("Event field is not string type");
			return null;
		}
		String val = ltf.readStringFromFile(pos + field.getOffset());
		return val;
	}
}
