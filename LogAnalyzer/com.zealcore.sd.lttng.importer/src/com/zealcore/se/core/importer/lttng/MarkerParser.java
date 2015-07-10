package com.zealcore.se.core.importer.lttng;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkerParser {

	private List<MarkerField> fields = new ArrayList<MarkerField>();
	private MarkerField field = new MarkerField();

	/*
	 * Check from the tracefiles group if the event name already exists and
	 * compare with the format. If it doesnt exist add it mdata of LttTrace
	 */
	public void parseMarkerFormatEvent(LttTrace trace, String channel,
			String name, String format) {
		MarkerInfo info = getMarkerInfoByName(trace, channel, name);
		if (info == null) {
			System.out.println("Marker format event without id");
			return;
		}
		String fmt = info.getFormat();
		if (fmt != null && !(fmt.equals(format))) {
			System.out.println("Duplicate non matching format for marker "
					+ name);
			return;
		}
		info.setFormat(format);
		parseFormat(info);
		List<LttTraceFile> files = trace.getTraceFiles().get(channel);
		files.get(0).getMIddata().put(info.getId(), info);
		files.get(0).getMNamedata().remove(name);
		files.get(0).getMNamedata().put(name, info);

	}

	public void parseMarkerIdEvent(LttTrace trace, String channel, String name,
			int id, int intSize, int longSize, int pointerSize, int size_tSize,
			int alignment) {

		List<LttTraceFile> files = trace.getTraceFiles().get(channel);
		if (files == null) {
			List<LttTraceFile> list = new ArrayList<LttTraceFile>();
			LttTraceFile file = new LttTraceFile();
			file.setName(channel);
			file.setEof(true);
			list.add(file);
			trace.getTraceFiles().put(channel, list);
			files = trace.getTraceFiles().get(channel);
		}

		// if(files.get(0).getMdata().get(name)!= null) {
		if (files.get(0).getMIddata().get(id) != null) {
			System.out.println("Marker already exists");
			return;
		}
		MarkerInfo info = new MarkerInfo();
		info.setName(name);
		info.setIntSize(intSize);
		info.setLongSize(longSize);
		info.setPointerSize(pointerSize);
		info.setSize_tSize(size_tSize);
		info.setAlignment(alignment);
		info.setFormat(null);
		info.setId(id);
		files.get(0).getMNamedata().put(name, info);
		files.get(0).getMIddata().put(id, info);

	}

	public void parseFormat(MarkerInfo info) {

		long offset = 0;
		ByteArrayInputStream in = new ByteArrayInputStream(info.getFormat()
				.getBytes());
		BufferedInputStream f = new BufferedInputStream(in);
		int fmt;
		StringBuffer fieldFmt = new StringBuffer();
		StringBuffer name = new StringBuffer();
		if (fields == null) {
			fields = new ArrayList<MarkerField>();
		}
		if (field == null) {
			field = new MarkerField();
		}

		try {
			while ((fmt = f.read()) != -1) {
				switch (fmt) {
				case '#':
					// # denoted trace type
					fmt = f.read(); /* skip first '#' */
					if (fmt == '#') { /* Escaped ## */
						fieldFmt.append(fmt);
						fieldFmt.append(fmt);
						break;
					}
					parseTraceType(info, field, f, fmt);
					break;
				case '%':
					// % denotes C type
					fieldFmt.append(fmt);
					fmt = f.read(); /* skip first '#' */
					if (fmt == '%') { /* Escaped ## */
						fieldFmt.append(fmt);
						break;
					}
					parserCDataType(info, field, f, fmt);
					field.setName(name.toString());
					fields.add(field);
					offset = (int) setAlignment(info, field, offset);
					name = null;
					name = new StringBuffer();
					field = null;
					field = new MarkerField();
					break;
				case ' ':
					break;
				default:
					fieldFmt.append((char) fmt);
					name.append((char) fmt);
					break;

				}
			}
			info.setFields(fields);
			info.setSize((int) offset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				fields = null;
				field = null;
				f.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void parseTraceType(MarkerInfo info, MarkerField field,
			BufferedInputStream stream, int format) {

		long attributes = 0;
		boolean flag = true;
		int qualifier = 0;
		while (flag) {

			switch (format) {
			case 'n':
				attributes |= MarkerField.LTT_ATTRIBUTE_NETWORK_BYTE_ORDER;
				try {
					format = stream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				flag = false;
				break;
			}
		}
		field.setAttributes(attributes);
		if (format == 'h' || format == 'l' || format == 'L' || format == 'Z'
				|| format == 'z' || format == 't' || format == 'S'
				|| format == '1' || format == '2' || format == '4'
				|| format == '8') {
			qualifier = format;
			try {
				format = stream.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (qualifier == 'l' && format == 'l') {
				qualifier = 'L';
				try {
					format = stream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		int traceSize = 0;
		switch (format) {
		case 'c':
			field.setType(MarkerField.LttType.LTT_TYPE_UNSIGNED_INT);
			traceSize = 1; // sizeof(char)
			break;
		case 's':
			field.setType(MarkerField.LttType.LTT_TYPE_STRING);
			break;
		case 'p':
			field.setType(MarkerField.LttType.LTT_TYPE_POINTER);
			traceSize = info.getPointerSize();
			break;
		case 'd':
		case 'i':
			field.setType(MarkerField.LttType.LTT_TYPE_SIGNED_INT);
			break;
		case 'o':
		case 'u':
		case 'x':
		case 'X':
			field.setType(MarkerField.LttType.LTT_TYPE_UNSIGNED_INT);
			break;
		default:
			break;
		}

		switch (qualifier) {
		case 'L':
			traceSize = 8; // sizeof(long long)
			break;
		case 'l':
			traceSize = info.getLongSize();
			break;
		case 'Z':
		case 'z':
			traceSize = info.getSize_tSize();
			break;
		case 't':
			traceSize = info.getPointerSize();
			break;
		case 'h':
			traceSize = 2; // sizeof(short)
			break;
		case '1':
			traceSize = 1; // sizeof(uint8_t)
			break;
		case '2':
			traceSize = 2; // sizeof(guint16)
			break;
		case '4':
			traceSize = 4; // sizeof(uint32_t)
			break;
		case '8':
			traceSize = 8; // sizeof(uint64_t)
			break;
		default:
			traceSize = info.getIntSize();
		}
		field.setSize(traceSize);
	}

	public void parserCDataType(MarkerInfo info, MarkerField field,
			BufferedInputStream stream, int format) {
		int qualifier = 0; /* 'h', 'l', or 'L' for integer fields */
		/* 'z' support added 23/7/1999 S.H. */
		/* 'z' changed to 'Z' --davidm 1/25/99 */
		/* 't' added for ptrdiff_t */
		MarkerField.LttType cType = null;
		boolean flag = true;
		while (flag) {

			switch (format) {
			case '-':
			case '+':
			case ' ':
			case '#':
			case '0':
				try {
					format = stream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				flag = false;
				break;
			}
		}
		if (format == 'h' || format == 'l' || format == 'L' || format == 'Z'
				|| format == 'z' || format == 't' || format == 'S') {
			qualifier = format;
			try {
				format = stream.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (qualifier == 'l' && format == 'l') {
				qualifier = 'L';
				try {
					format = stream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		int cSize = 0;

		switch (format) {
		case 'c':
			cType = MarkerField.LttType.LTT_TYPE_UNSIGNED_INT;
			cSize = 1; // sizeof(char)
			break;
		case 's':
			cType = MarkerField.LttType.LTT_TYPE_STRING;
			break;
		case 'p':
			cType = MarkerField.LttType.LTT_TYPE_POINTER;
			cSize = info.getPointerSize();
			break;
		case 'd':
		case 'i':
			cType = MarkerField.LttType.LTT_TYPE_SIGNED_INT;
			break;
		case 'o':
		case 'u':
		case 'x':
		case 'X':
			cType = MarkerField.LttType.LTT_TYPE_UNSIGNED_INT;
			break;
		default:
			break;
		}
		/*
		 * if(set_size) { field.setSize(c_size); return; }
		 */
		switch (qualifier) {
		case 'L':
			cSize = 8; // sizeof(long long)
			break;
		case 'l':
			cSize = info.getLongSize();
			break;
		case 'Z':
		case 'z':
			cSize = info.getSize_tSize();
			break;
		case 't':
			cSize = info.getPointerSize();
			break;
		case 'h':
			cSize = 2; // sizeof(short)
			break;
		default:
			cSize = info.getIntSize();
		}
		if (field.getSize() == 0) {
			field.setSize(cSize);
		}
		if (field.getType() == MarkerField.LttType.LTT_TYPE_NONE) {
			field.setType(cType);
		}
		if (cType == MarkerField.LttType.LTT_TYPE_STRING) {
			field.setType(cType);
		}

	}

	private long setAlignment(MarkerInfo info, MarkerField field, long offset) {
		switch (field.getType()) {
		case LTT_TYPE_SIGNED_INT:
		case LTT_TYPE_UNSIGNED_INT:
		case LTT_TYPE_POINTER:
			info.setLargestAlignment(LttUtil.max((int) field.getAlignment(),
					info.getLargestAlignment()));
			if (offset == -1) {
				field.setOffset(-1);
				field.setStaticOffset(0);
				return -1;
			} else {
				field.setOffset(offset
						+ LttUtil.lttAlign(offset, (int) field.getAlignment(),
								info.getAlignment()));
				field.setStaticOffset(1);
				return field.getOffset() + field.getSize();
			}
		case LTT_TYPE_STRING:
			field.setOffset(offset);
			field.setSize(0); /* Variable length, size is 0 */
			field.setAlignment(1);
			if (offset == -1)
				field.setStaticOffset(0);
			else
				field.setStaticOffset(1);
			return -1;
		default:
			System.out.println("Unexpected type");
			return 0;
		}
	}

	public static long markerUpdateFields(LttTraceFile ltf, MarkerInfo info,
			long pos) {
		int i = 0;
		long offset = 0;
		MarkerField field = null;
		/* Find the last field with a static offset, then update from there. */
		for (i = info.getFields().size() - 1; i >= 0; i--) {
			field = info.getFields().get(i);
			if (field.getStaticOffset() == 1) {
				offset = field.getOffset();
				break;
			}
		}

		for (; i < info.getFields().size(); i++) {
			field = info.getFields().get(i);

			switch (field.getType()) {
			case LTT_TYPE_SIGNED_INT:
			case LTT_TYPE_UNSIGNED_INT:
			case LTT_TYPE_POINTER:
				field.setOffset(offset
						+ LttUtil.lttAlign(offset, (int) field.getAlignment(),
								info.getAlignment()));
				offset = field.getOffset() + field.getSize();
				break;
			case LTT_TYPE_STRING:
				field.setOffset(offset);
				try {
					offset = offset
							+ (ltf.readStringFromFile(pos + offset).length() + 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// not aligning on pointer size, breaking genevent backward
				// compatibility.
				break;
			default:
				System.out.println("Unexpected type");
				return -1;
			}
		}
		return offset;

	}

	public static MarkerInfo getMarkerInfoByName(LttTrace lt, String channel,
			String name) {
		List<LttTraceFile> files = lt.getTraceFiles().get(channel);
		MarkerInfo info = files.get(0).getMNamedata().get(name);
		return info;
	}

	public static MarkerInfo getMarkerInfoById(LttTrace lt, String channel,
			int id) {
		List<LttTraceFile> files = lt.getTraceFiles().get(channel);
		MarkerInfo info = files.get(0).getMIddata().get(id);
		return info;
	}

}