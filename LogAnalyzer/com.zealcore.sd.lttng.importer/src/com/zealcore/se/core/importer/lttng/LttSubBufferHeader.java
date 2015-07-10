package com.zealcore.se.core.importer.lttng;

public class LttSubBufferHeader {

	private long cycleCountBegin; /* Cycle count at subbuffer start */
	private long cycleCountEnd; /* Cycle count at subbuffer end */
	private int magicNumber; /*
							 * Trace magic number. contains endianness
							 * information.
							 */
	private byte majorVersion;
	private byte minorVersion;
	private byte archSize; /* Architecture pointer size */
	private byte alignment; /* LTT data alignment */
	private long startTimeSec; /* NTP-corrected start time */
	private long startTimeUsec;
	private long startFreq; /*
							 * Frequency at trace start, used all along the
							 * trace.
							 */
	private int freqScale; /* Frequency scaling (divide freq) */
	private int dataSize; /* Size of data in subbuffer */
	private int sbSize; /* Subbuffer size (page aligned) */
	private int eventsLost; /*
							 * Events lost in this subbuffer since the beginning
							 * of the trace. (may overflow)
							 */
	private int subBufCorrupt; /*
								 * Corrupted (lost) subbuffers since the
								 * begginig of the trace. (may overflow)
								 */

	private static final byte CYCLE_COUNT_BEGIN = 8;
	private static final byte CYCLE_COUNT_INT = 8;
	private static final byte MAGIC_NUMBER = 4;
	private static final byte MAJOR_VERSION = 1;;
	private static final byte MINOR_VERSION = 1;
	private static final byte ARCH_SIZE = 1;
	private static final byte ALIGNMENT = 1;
	private static final byte START_TIME_SEC = 8;
	private static final byte START_TIME_USEC = 8;
	private static final byte START_FREQ = 8;
	private static final byte FREQ_SCALE = 4;
	private static final byte DATA_SIZE = 4;
	private static final byte SB_SIZE = 4;
	private static final byte EVENTS_LOST = 4;
	private static final byte SUB_BUF_CORRUPT = 4;

	private static final short BUFFER_HEADER_SIZE = CYCLE_COUNT_BEGIN
			+ CYCLE_COUNT_INT + MAGIC_NUMBER + MAJOR_VERSION + MINOR_VERSION
			+ ARCH_SIZE + ALIGNMENT + START_TIME_SEC + START_TIME_USEC
			+ START_FREQ + FREQ_SCALE + DATA_SIZE + SB_SIZE + EVENTS_LOST
			+ SUB_BUF_CORRUPT;

	public static short getBufferHeaderSizeConstant() {
		return BUFFER_HEADER_SIZE;
	}

	public long getCycleCountBegin() {
		return cycleCountBegin;
	}

	public void setCycleCountBegin(long cycleCountBegin) {
		this.cycleCountBegin = cycleCountBegin;
	}

	public long getCycleCountEnd() {
		return cycleCountEnd;
	}

	public void setCycleCountEnd(long cycleCountEnd) {
		this.cycleCountEnd = cycleCountEnd;
	}

	public int getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(int magicNumber) {
		this.magicNumber = magicNumber;
	}

	public byte getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(byte majorVersion) {

		this.majorVersion = majorVersion;
	}

	public byte getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(byte minorVersion) {

		this.minorVersion = minorVersion;
	}

	public byte getArchSize() {
		return archSize;
	}

	public void setArchSize(byte archSize) {
		this.archSize = archSize;
	}

	public byte getAlignment() {
		return alignment;
	}

	public void setAlignment(byte alignment) {
		this.alignment = alignment;
	}

	public long getStartTimeSec() {
		return startTimeSec;
	}

	public void setStartTimeSec(long startTimeSec) {
		this.startTimeSec = startTimeSec;
	}

	public long getStartTimeUsec() {
		return startTimeUsec;
	}

	public void setStartTimeUsec(long startTimeUsec) {
		this.startTimeUsec = startTimeUsec;
	}

	public long getStartFreq() {
		return startFreq;
	}

	public void setStartFreq(long startFreq) {
		this.startFreq = startFreq;
	}

	public int getFreqScale() {
		return freqScale;
	}

	public void setFreqScale(int freqScale) {
		this.freqScale = freqScale;
	}

	public int getDataSize() {

		return dataSize;
	}

	public void setDataSize(int dataSize) {

		this.dataSize = dataSize;
	}

	public int getSbSize() {
		return sbSize;
	}

	public void setSbSize(int sbSize) {
		this.sbSize = sbSize;
	}

	public int getEventsLost() {
		return eventsLost;
	}

	public void setEventsLost(int eventsLost) {
		this.eventsLost = eventsLost;
	}

	public int getSubBufCorrupt() {
		return subBufCorrupt;
	}

	public void setSubBufCorrupt(int subBufCorrupt) {
		this.subBufCorrupt = subBufCorrupt;
	}

}
