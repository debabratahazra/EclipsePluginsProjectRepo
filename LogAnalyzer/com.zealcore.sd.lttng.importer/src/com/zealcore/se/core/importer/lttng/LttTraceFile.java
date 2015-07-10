package com.zealcore.se.core.importer.lttng;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LttTraceFile {

	LttTrace trace;

	LttSubBufferHeader header;

	private boolean cpuOnline; // is the cpu online ?

	private String longName; // tracefile complete filename

	private String name; // tracefile name

	private int cpuNum; // cpu number of the tracefile

	private int tid; // Usertrace tid, else 0

	private int pgid; // Usertrace pgid, else 0

	private int creation; // Usertrace creation, else 0

	private RandomAccessFile fd; // file descriptor

	private long fileSize; // file size

	private int numBlocks; // number of blocks in the file

	private boolean reverseBo; // must we reverse byte order ?

	private boolean floatWordOrder; // what is the byte order of floats ?

	private int alignment; // alignment of events in the tracefile.
	// 0 or the architecture size in bytes.
	private long bufferHeaderSize;

	private int tscBits;

	private int eventBits;

	private long tscMask;

	private long tscMaskNextBit; // next MSB after the mask<

	private int eventsLost;

	private int subBufCorrupt;

	private boolean eof;

	private boolean move;

	private List<Long> bufIndex = new ArrayList<Long>(); /*
														 * index mapping buffer
														 * index to offset
														 */

	/* Current event */
	private LttEvent event; // Event currently accessible in the trace

	/* Current block */
	private LttBuffer buffer; // current buffer

	private Map<String, MarkerInfo> mNamedata = new HashMap<String, MarkerInfo>(); // marker
	// id/name/fields
	// mapping
	private Map<Integer, MarkerInfo> mIddata = new HashMap<Integer, MarkerInfo>(); // marker
	// id/name/fields
	// mapping

	private static final int MAXINT = 4;

	private static final long LTT_TIME_UINT_SHIFT_CONST = 1953125;
	private static final int LTT_TIME_UINT_SHIFT = 9;
	private static final long NANOSECONDS_PER_SECOND = 1000000000;

	private LttTime res = new LttTime();
	private LttSubBufferHeader headerTemp = new LttSubBufferHeader();
	private MarkerParser parser = new MarkerParser();
	private EventParser eventParser = new EventParser();
	private Map<String, String> eventFields = new HashMap<String, String>();

	/*
	 * Parse current block and add it to buffer
	 */

	public LttSubBufferHeader parseLttSubBufferHeader(long offset) {

		RandomAccessFile in = getInputStream();

		try {
			in.seek(offset);
			headerTemp.setCycleCountBegin(in.readLong());
			headerTemp.setCycleCountEnd(in.readLong());
			headerTemp.setMagicNumber(in.readInt());
			headerTemp.setMajorVersion(in.readByte());
			headerTemp.setMinorVersion(in.readByte());
			headerTemp.setArchSize(in.readByte());
			headerTemp.setAlignment(in.readByte());
			headerTemp.setStartTimeSec(in.readLong());
			headerTemp.setStartTimeUsec(in.readLong());
			headerTemp.setStartFreq(in.readLong());
			headerTemp.setFreqScale(in.readInt());
			headerTemp.setDataSize(in.readInt());
			headerTemp.setSbSize(in.readInt());
			headerTemp.setEventsLost(in.readInt());
			headerTemp.setSubBufCorrupt(in.readInt());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return headerTemp;

	}

	/*
	 * Process the trace header
	 */
	public void parseTraceHeader(LttTrace lt, long offset) throws Exception {
		LttSubBufferHeader header = null;
		try {
			header = parseLttSubBufferHeader(offset);
		} catch (Exception e) {
			throw new Exception();
		}
		setHeader(header);
		int magicNumber = header.getMagicNumber();
		if (magicNumber == LttTrace.LTT_MAGIC_NUMBER) {
			setReverseBo(false);
		} else if (magicNumber == LttTrace.LTT_REV_MAGIC_NUMBER) {
			setReverseBo(true);
		} else {
			System.out.println("Invalid magic number. Bad trace file");
			throw new Exception();
		}
		if (lt != null) {
			lt.setLttMajorVersion(header.getMajorVersion());
			lt.setLttMinorVersion(header.getMinorVersion());
			lt.setArchSize(header.getArchSize());
		}
		setAlignment(header.getAlignment());

		/*
		 * Get float byte order : might be different from int byte order (or is
		 * set to 0 if the trace has no float (kernel trace))
		 */
		setFloatWordOrder(false);
		switch (header.getMajorVersion()) {

		case 2:
			switch (header.getMinorVersion()) {
			case 6: {
				setBufferHeaderSize(LttSubBufferHeader
						.getBufferHeaderSizeConstant());
				setTscBits(27);
				setEventBits(5);
				setTscMask((1L << getTscBits()) - 1); // should be 1LL
				setTscMaskNextBit(1L << getTscBits());
				if (lt != null) {
					lt.setStartTime(LttUtil.lttLongByteOrderValue(
							isReverseBo(), header.getStartTimeSec()));
					lt.setStartTimeUsec(LttUtil.lttLongByteOrderValue(
							isReverseBo(), header.getStartTimeUsec()));
					lt.setStartFreq(LttUtil.lttLongByteOrderValue(
							isReverseBo(), header.getStartFreq()));
					lt.setFreqScale(LttUtil.lttIntByteOrderValue(isReverseBo(),
							header.getFreqScale()));
					lt.setStartTsc(LttUtil.lttLongByteOrderValue(isReverseBo(),
							header.getCycleCountBegin()));
					lt.setStartMonotonic(0);
					lt.setStartTimeFromTsc(lttTimeFromlong(tscTolong(lt
							.getFreqScale(), lt.getStartFreq(), lt
							.getStartTsc())));

				}
			}

				break;
			default:

				throw new Exception("PARSE ERROR : Unsupported trace version "
						+ header.getMajorVersion() + "."
						+ header.getMinorVersion() + ".Only 2.6 supported");
			}
			break;
		default:

			throw new Exception("PARSE ERROR : Unsupported trace version "
					+ header.getMajorVersion() + "." + header.getMinorVersion()
					+ ".Only 2.6 supported");

		}

	}

	/*
	 * Since we have several blocks in the same trace file have an index of the
	 * blocks to the respective offset. When we seek an event based on time we
	 * can jump to the block first and then sequentially go throught the events
	 */
	public void lttTraceCreateBlockIndex(LttTrace lt) {
		long offset = 0;
		long blockOffset = 0;
		int blockSize = 0;
		while (offset < getFileSize()) {
			LttSubBufferHeader header = parseLttSubBufferHeader(offset);
			blockOffset = offset;
			if (header.getMagicNumber() == LttTrace.LTT_REV_MAGIC_NUMBER) {
				blockSize = LttUtil.lttIntByteOrderValue(true, header
						.getSbSize());
			}
			getBufIndex().add(blockOffset);
			offset += blockSize;

		}
		setNumBlocks(getBufIndex().size());

	}

	public void mapBlock(LttTrace lt, int blockNum) throws Exception {

		long offset = (Long) getBufIndex().get(blockNum);
		parseTraceHeader(null, offset);
		LttSubBufferHeader header = parseLttSubBufferHeader(offset);
		setHeader(header);
		LttBuffer buffer = getBuffer();
		if (buffer != null) {
			buffer = null;
		}
		buffer = new LttBuffer();
		setBuffer(buffer);
		LttEvent event = getEvent();
		if (event != null) {
			event = null;
		}
		event = new LttEvent();
		setEvent(event);
		getBuffer().setIndex(blockNum);
		getBuffer().setBeginCycleCount(
				LttUtil.lttLongByteOrderValue(isReverseBo(), header
						.getCycleCountBegin()));
		getBuffer().setEndCycleCount(
				LttUtil.lttLongByteOrderValue(isReverseBo(), header
						.getCycleCountEnd()));
		getBuffer().setOffset(offset);
		getBuffer().setHeaderPos(offset);
		getBuffer()
				.setSize(
						LttUtil.lttIntByteOrderValue(isReverseBo(), header
								.getSbSize()));
		getBuffer().setDataSize(
				LttUtil.lttIntByteOrderValue(isReverseBo(), header
						.getDataSize()));
		getBuffer().setTsc(
				LttUtil.lttLongByteOrderValue(isReverseBo(), header
						.getCycleCountBegin()));
		getEvent().setTsc(getBuffer().getTsc());
		getBuffer().setFreq(getBuffer().getBeginFreq());

		if (getTrace() != null && getTrace().getStartFreq() != 0) {
			getBuffer().setBeginFreq(getTrace().getStartFreq());
			getBuffer().setBeginTimestamp(
					lttInterpolateTimeFromTsc(lt, getBuffer()
							.getBeginCycleCount()));
			getBuffer().setEndFreq(getTrace().getStartFreq());
			getBuffer().setEndTimestamp(
					lttInterpolateTimeFromTsc(lt, getBuffer()
							.getEndCycleCount()));

		}
		/*
		 * Make the current event point to the beginning of the buffer : it
		 * means that the event read must get the first event.
		 */
		getEvent().setBlock(blockNum);
		getEvent().setOffset(0);

		if (header.getEventsLost() != 0) {
			setEventsLost(header.getEventsLost());
		}

		if (header.getSubBufCorrupt() != 0) {
			setSubbufCorrupt(header.getSubBufCorrupt());
		}
	}

	public void moveToNextEvent(LttTrace t) throws EndOfRangeException,
			Exception {
		try {
			lttTraceFileReadSeek(t);
			lttTraceFileReadUpdateEvent(t);
			parseEvent(t, getEvent());
		} catch (EndOfRangeException eof) {
			throw new EndOfRangeException();
		} catch (Exception e) {
			throw new Exception("PARSE ERROR : Error parsing file " + getName()
					+ "_" + getCpuNum());
		}
	}

	public LttEvent parseEvent(LttTrace t, LttEvent event) throws Exception {
		MarkerInfo info = MarkerParser.getMarkerInfoById(t, getName(),
				getEvent().getEventId());
		if (info == null) {
			throw new Exception();

		}
		List<MarkerField> fields = info.getFields();
		MarkerField field = null;

		eventFields.clear();
		long value = 0;
		event.setName(info.getName());
		long pos = event.getEventPos();
		for (int i = 0; i < fields.size(); i++) {
			field = fields.get(i);
			switch (field.getType()) {
			case LTT_TYPE_SIGNED_INT:
				value = eventParser.lttEventLongInt(this, event, field, pos);
				eventFields.put(field.getName(), String.valueOf(value));

				break;
			case LTT_TYPE_UNSIGNED_INT:
				value = eventParser.lttEventLongUnsigned(this, event, field,
						pos);
				eventFields.put(field.getName(), String.valueOf(value));

				break;
			case LTT_TYPE_POINTER:
				value = eventParser.lttEventLongUnsigned(this, event, field,
						pos);
				eventFields.put(field.getName(), String.valueOf(value));

				break;
			case LTT_TYPE_STRING:
				String val = eventParser
						.lttEventString(this, event, field, pos);
				eventFields.put(field.getName(), val);

				break;
			case LTT_TYPE_NONE:
				break;
			default:
				break;
			}
		}
		eventFields.put("Execution Unit", String.valueOf(getCpuNum()));
		eventFields.put("Channel", getName());
		event.setFields(eventFields);
		event.setCpu(getCpuNum());
		return event;
	}

	/*
	 * Process meta data trace file and have the metadata info in LttTrace
	 * metadata.
	 */
	public void processMetaDataTraceFile(LttTrace t) {
		String channelName, markerName, format;
		long pos = 0;
		byte intSize;
		byte longSize;
		byte pointerSize;
		byte sizeTSize;
		byte alignment;
		short id;

		while (true) {
			try {
				lttTraceFileReadSeek(t);
				lttTraceFileReadUpdateEvent(t);

				if (getEvent().getEventId() >= MarkerInfo.MARKER_CORE_IDS) {
					System.out
							.println("Error in processing metadata file. Should only contain core events");
					break;
				} else {
					switch (getEvent().getEventId()) {
					case MarkerInfo.MARKER_ID_SET_MARKER_ID:
						pos = getEvent().getEventPos();
						channelName = readStringFromFile(pos);
						pos += channelName.length() + 1;
						markerName = readStringFromFile(pos);
						pos += markerName.length() + 1;
						pos += LttUtil.lttAlign(pos, 2, getAlignment());
						id = LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos));
						pos += 2; // sizeof(guint16)
						intSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1;
						longSize = readByteFromFile(pos);
						pos += 1; // sizeof(guint8);
						pointerSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						sizeTSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						alignment = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						parser.parseMarkerIdEvent(t, channelName, markerName,
								id, intSize, longSize, pointerSize, sizeTSize,
								alignment);
						break;
					case MarkerInfo.MARKER_ID_SET_MARKER_FORMAT:
						pos = getEvent().getEventPos();
						channelName = readStringFromFile(pos);
						pos += channelName.length() + 1;
						markerName = readStringFromFile(pos);
						pos += markerName.length() + 1;
						format = readStringFromFile(pos);
						pos += format.length() + 1;
						parser.parseMarkerFormatEvent(t, channelName,
								markerName, format);
						break;
					default:
						System.out.println("Invalid marker event");

					}
				}

			} catch (EndOfRangeException e) {
				// TODO Auto-generated catch block

				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}

		}

	}

	/*
	 * Process meta data trace file and have the metadata info in LttTrace
	 * metadata.
	 */
	public void processEventTraceFile(LttTrace t) {

		String channelName, markerName, format;
		long pos = 0;
		byte intSize;
		byte longSize;
		byte pointerSize;
		byte sizeTSize;
		byte alignment;
		short id;

		while (true) {
			try {
				lttTraceFileReadSeek(t);
				lttTraceFileReadUpdateEvent(t);

				if (getEvent().getEventId() >= MarkerInfo.MARKER_CORE_IDS) {
					System.out
							.println("Error in processing metadata file. Should only contain core events");
					break;
				} else {
					switch (getEvent().getEventId()) {
					case MarkerInfo.MARKER_ID_SET_MARKER_ID:
						pos = getEvent().getEventPos();
						channelName = readStringFromFile(pos);
						pos += channelName.length() + 1;
						markerName = readStringFromFile(pos);
						pos += markerName.length() + 1;
						pos += LttUtil.lttAlign(pos, 2, getAlignment());
						id = LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos));
						pos += 2; // sizeof(guint16)
						intSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1;
						longSize = readByteFromFile(pos);
						pos += 1; // sizeof(guint8);
						pointerSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						sizeTSize = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						alignment = readByteFromFile(pos); // *(guint8*)pos;
						pos += 1; // sizeof(guint8);
						parser.parseMarkerIdEvent(t, channelName, markerName,
								id, intSize, longSize, pointerSize, sizeTSize,
								alignment);
						break;
					case MarkerInfo.MARKER_ID_SET_MARKER_FORMAT:
						pos = getEvent().getEventPos();
						channelName = readStringFromFile(pos);
						pos += channelName.length() + 1;
						markerName = readStringFromFile(pos);
						pos += markerName.length() + 1;
						format = readStringFromFile(pos);
						pos += format.length() + 1;
						parser.parseMarkerFormatEvent(t, channelName,
								markerName, format);
					default:
						System.out.println("Invalid marker event");

					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}

		}

	}

	public String readStringFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		if (raf == null) {
			return null;
		}
		raf.seek(pos);
		StringBuffer name = new StringBuffer();
		char c = (char) raf.readByte();
		while (c != '\0') {
			name.append(c);
			c = (char) raf.readByte();
		}
		return name.toString();
	}

	public int readIntFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		raf.seek(pos);
		return raf.readInt();
	}

	public short readShortFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		raf.seek(pos);
		return raf.readShort();
	}

	public byte readByteFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		raf.seek(pos);
		return raf.readByte();
	}

	public long readLongFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		raf.seek(pos);
		return raf.readLong();
	}

	public char readCharFromFile(long pos) throws IOException {
		RandomAccessFile raf = getInputStream();
		raf.seek(pos);
		return raf.readChar();
	}

	public void lttTraceFileReadUpdateEvent(LttTrace lt) throws Exception {
		long pos;
		short packedEvid; /* event id reader from the 5 bits in header */

		event = getEvent();
		pos = getBuffer().getHeaderPos() + getEvent().getOffset();

		/* Read event header */

		/* Align the head */
		try {
			pos += LttUtil.lttAlign(pos, 4, getAlignment());

			getEvent().setTimeStamp(
					LttUtil.lttIntByteOrderValue(isReverseBo(),
							readIntFromFile(pos)));
			int eventId = (int) getEvent().getTimestamp() >>> getTscBits();
			getEvent().setEventId(eventId);
			packedEvid = (short) getEvent().getEventId();

			getEvent().setTimeStamp(getEvent().getTimestamp() & getTscMask());
			pos += 4; // sizeof(guint32);

			switch (packedEvid) {
			case 29: /* LTT_RFLAG_ID_SIZE_TSC */
				getEvent().setEventId(
						LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos)));
				pos += 2; // sizeof(guint16);
				getEvent().setEventSize(
						LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos)));
				pos += 2; // sizeof(guint16);
				if (getEvent().getEventSize() == 0xFFFF) {
					getEvent().setEventSize(
							LttUtil.lttIntByteOrderValue(isReverseBo(),
									readIntFromFile(pos)));
					pos += 4; // sizeof(guint32);
				}
				pos += LttUtil.lttAlign(pos, 8, getAlignment());
				getBuffer().setTsc(
						LttUtil.lttLongByteOrderValue(isReverseBo(),
								readLongFromFile(pos)));
				pos += 8; // sizeof(guint64);
				break;
			case 30: /* LTT_RFLAG_ID_SIZE */
				getEvent().setEventId(
						LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos)));
				pos += 2; // sizeof(guint16);
				getEvent().setEventSize(
						LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos)));
				pos += 2; // sizeof(guint16);
				if (getEvent().getEventSize() == 0xFFFF) {
					getEvent().setEventSize(
							LttUtil.lttIntByteOrderValue(isReverseBo(),
									readIntFromFile(pos)));
					pos += 4; // sizeof(guint32);
				}

				break;
			case 31: /* LTT_RFLAG_ID */
				getEvent().setEventId(
						LttUtil.lttShortByteOrderValue(isReverseBo(),
								readShortFromFile(pos)));
				pos += 2; // sizeof(guint16);
				getEvent().setEventSize(MAXINT);// TODO G_MAXUINT;
				break;
			default:
				getEvent().setEventSize(MAXINT);// TODO G_MAXUINT;
				break;
			}

			if (packedEvid != 29) {
				/* No extended timestamp */
				if (getEvent().getTimestamp() < (getBuffer().getTsc() & getTscMask()))
					getBuffer().setTsc(((getBuffer().getTsc() & ~getTscMask()) /* overflow */
					+ getTscMaskNextBit()) | getEvent().getTimestamp());
				else {
					getBuffer().setTsc((getBuffer().getTsc() & ~getTscMask()) /*
																			 * no
																			 * overflow
																			 */
							| getEvent().getTimestamp());
				}
			}
			getEvent().setTsc(getBuffer().getTsc());

			getEvent().setEventTime(lttInterpolateTime(lt));

			getEvent().setEventPos(pos);

			/*
			 * Let ltt_update_event_size update event->data according to the
			 * largest alignment within the payload. Get the data size and
			 * update the event fields with the current information.
			 */
			lttUpdateEventSize(lt);
		} catch (IOException e) {
			throw e;
		}

	}

	void lttUpdateEventSize(LttTrace lt) {
		long size = 0;
		long eventPos = getEvent().getEventPos();
		String channelName;
		String markerName;
		String format = null;
		MarkerInfo info = null;

		try {
			if ("metadata".equals(getName())) {
				switch (getEvent().getEventId()) {
				case MarkerInfo.MARKER_ID_SET_MARKER_ID:
					channelName = readStringFromFile(eventPos + size);
					size += channelName.length() + 1;
					markerName = readStringFromFile(eventPos + size);
					size += markerName.length() + 1;
					size += LttUtil.lttAlign(readIntFromFile(eventPos + size),
							2, getAlignment());
					size += 2; // sizeof(guint16);
					size += 1; // sizeof(guint8);
					size += 1; // sizeof(guint8);
					size += 1; // sizeof(guint8);
					size += 1; // sizeof(guint8);
					size += 1; // sizeof(guint8);
					break;
				case MarkerInfo.MARKER_ID_SET_MARKER_FORMAT:
					channelName = readStringFromFile(eventPos + size);
					size += channelName.length() + 1;
					markerName = readStringFromFile(eventPos + size);
					size += markerName.length() + 1;
					format = readStringFromFile(eventPos + size);
					size += format.length() + 1;

					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		info = MarkerParser.getMarkerInfoById(lt, getName(), getEvent()
				.getEventId());
		if (getEvent().getEventId() >= MarkerInfo.MARKER_CORE_IDS) {
			if (info == null) {
				System.out.println("Info cannot be null");
			}
		}

		/*
		 * Do not update field offsets of core markers when initially reading
		 * the metadata tracefile when the infos about these markers do not
		 * exist yet.
		 */

		if (!("metadata".equals(getName())) && info != null
				&& info.getFields() != null) {
			long pos = getEvent().getEventPos();
			pos += LttUtil.lttAlign(pos, info.getLargestAlignment(), info
					.getAlignment());
			getEvent().setEventPos(pos);
			if (info.getSize() != -1) {
				size = info.getSize();
			} else {
				size = MarkerParser.markerUpdateFields(this, info, getEvent()
						.getEventPos());
				// TODO something to be done here
			}
		}

		getEvent().setDataSize((int) size);

		/* Check consistency between kernel and LTTV structure sizes */
		if (getEvent().getEventSize() == MAXINT) {
			/* Event size too big to fit in the event size field */
			getEvent().setEventSize(getEvent().getDataSize());
		}

		if (getEvent().getDataSize() != getEvent().getEventSize()) {

			if (info == null)
				System.out.println("Undescribed event in channel "
						+ getEvent().getEventId() + getName());
		}
	}

	public void lttTraceFileReadSeek(LttTrace tf) throws Exception {
		/* Get next buffer until we finally have an event, or end of trace */
		while (true) {
			try {
				lttSeekNextEvent(this);
				break;

			} catch (EndOfRangeException eof) {
				if (getBuffer().getIndex() == getNumBlocks() - 1) {
					throw eof;
				}
				mapBlock(tf, getBuffer().getIndex() + 1);
			} catch (Exception e) {
				throw e;

			}
		}
	}

	public void lttSeekNextEvent(LttTraceFile ltf) throws EndOfRangeException,
			Exception {
		int offset = ltf.getEvent().getOffset();
		if (offset == 0) {
			offset += LttSubBufferHeader.getBufferHeaderSizeConstant();
			if (offset == ltf.getBuffer().getDataSize()) {
				throw new EndOfRangeException();
			}
			ltf.getEvent().setOffset(offset);
			return;
		}
		long pos = 0;
		pos = ltf.getEvent().getEventPos();
		if (ltf.getEvent().getDataSize() < 0) {
			System.out.println("Error in lttSeekNextEvent in file"
					+ ltf.getName());
			throw new Exception();
		}
		pos += ltf.getEvent().getDataSize();
		ltf.getEvent().setEventPos(pos);
		pos -= ltf.getBuffer().getHeaderPos();
		ltf.getEvent().setOffset((int) pos);

		if (ltf.getEvent().getOffset() == ltf.getBuffer().getDataSize()) {
			throw new EndOfRangeException();
		}

	}

	private LttTime lttInterpolateTime(LttTrace lt) {
		return lttInterpolateTimeFromTsc(lt, getBuffer().getTsc());

	}

	long tscTolong(long freq_scale, long start_freq, long tsc) {
		return tsc * NANOSECONDS_PER_SECOND * freq_scale / start_freq;
	}

	private LttTime lttTimeFromlong(long t1) {

		if ((t1 >> LTT_TIME_UINT_SHIFT >= LTT_TIME_UINT_SHIFT_CONST)) {
			res.setTvSec((t1 >> LTT_TIME_UINT_SHIFT)
					/ LTT_TIME_UINT_SHIFT_CONST); // acceleration
			res.setTvNsec((t1 - res.getTvSec() * NANOSECONDS_PER_SECOND));
		} else {
			res.setTvSec(0);
			res.setTvNsec(t1);
		}
		return res;
	}

	private LttTime lttInterpolateTimeFromTsc(LttTrace lt, long tsc) {
		int freqScale = getTrace().getFreqScale();
		long startFreq = getTrace().getStartFreq();

		double tscD = getTrace().getDrift() * tsc + getTrace().getOffset();
		tscD = tscD * 1000000000 * freqScale / startFreq;
		return lttTimeFromlong((long) tscD);
	}

	/*
	 * Get the version number of the current trace and see if it is supported
	 */
	public String getVersionNumber() {
		return null;
	}

	public LttTrace getTrace() {
		return trace;
	}

	public void setTrace(LttTrace trace) {
		this.trace = trace;
	}

	public LttSubBufferHeader getHeader() {
		return header;
	}

	public void setHeader(LttSubBufferHeader header) {
		this.header = header;
	}

	public Map<String, MarkerInfo> getMNamedata() {
		return mNamedata;
	}

	public void setMNamedata(Map<String, MarkerInfo> namedata) {
		mNamedata = namedata;
	}

	public Map<Integer, MarkerInfo> getMIddata() {
		return mIddata;
	}

	public void setMIddata(Map<Integer, MarkerInfo> iddata) {
		mIddata = iddata;
	}

	public boolean isCpuOnline() {
		return cpuOnline;
	}

	public void setCpuOnline(boolean cpuOnline) {
		this.cpuOnline = cpuOnline;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getPgid() {
		return pgid;
	}

	public void setPgid(int pgid) {
		this.pgid = pgid;
	}

	public int getCreation() {
		return creation;
	}

	public void setCreation(int creation) {
		this.creation = creation;
	}

	public RandomAccessFile getInputStream() {
		return fd;
	}

	public void setInputStream(RandomAccessFile fd) {
		this.fd = fd;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public int getNumBlocks() {
		return numBlocks;
	}

	public void setNumBlocks(int numBlocks) {
		this.numBlocks = numBlocks;
	}

	public boolean isReverseBo() {
		return reverseBo;
	}

	public void setReverseBo(boolean reverseBo) {
		this.reverseBo = reverseBo;
	}

	public boolean isFloatWordOrder() {
		return floatWordOrder;
	}

	public void setFloatWordOrder(boolean floatWordOrder) {
		this.floatWordOrder = floatWordOrder;
	}

	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public long getBufferHeaderSize() {
		return bufferHeaderSize;
	}

	public void setBufferHeaderSize(long bufferHeaderSize) {
		this.bufferHeaderSize = bufferHeaderSize;
	}

	public int getTscBits() {
		return tscBits;
	}

	public void setTscBits(int tscbits) {
		this.tscBits = tscbits;
	}

	public int getEventBits() {
		return eventBits;
	}

	public void setEventBits(int eventbits) {
		this.eventBits = eventbits;
	}

	public long getTscMask() {
		return tscMask;
	}

	public void setTscMask(long tscMask) {
		this.tscMask = tscMask;
	}

	public long getTscMaskNextBit() {
		return tscMaskNextBit;
	}

	public void setTscMaskNextBit(long tscMaskNextBit) {
		this.tscMaskNextBit = tscMaskNextBit;
	}

	public int getEventsLost() {
		return eventsLost;
	}

	public void setEventsLost(int eventsLost) {
		this.eventsLost = eventsLost;
	}

	public int getSubbufCorrupt() {
		return subBufCorrupt;
	}

	public void setSubbufCorrupt(int subbufCorrupt) {
		this.subBufCorrupt = subbufCorrupt;
	}

	public List<Long> getBufIndex() {
		return bufIndex;
	}

	public void setBufIndex(List<Long> bufIndex) {
		this.bufIndex = bufIndex;
	}

	public LttEvent getEvent() {
		return event;
	}

	public void setEvent(LttEvent event) {
		this.event = event;
	}

	public LttBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(LttBuffer buffer) {
		this.buffer = buffer;
	}

	public boolean isEof() {
		return eof;
	}

	public void setEof(boolean eof) {
		this.eof = eof;
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

}
