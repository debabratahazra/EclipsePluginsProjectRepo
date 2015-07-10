package com.zealcore.se.core.importer.lttng;

public class LttBuffer {

	private long headerPos;

	private long offset; /* Offset of the current subbuffer */

	private int size; /* The size of the current subbuffer */

	private int index;

	private int dataSize; /* Size of data in the subbuffer */

	private LttTime beginTimestamp;

	private long beginCycleCount;

	private long beginFreq; /* Frequency in khz */

	private LttTime endTimestamp;

	private long endCycleCount;

	private long endFreq; /* Frequency in khz */

	/* Timekeeping */
	private long tsc; /* Current timestamp counter */

	private long freq; /* Frequency in khz */

	private long cyc2nsScale;

	public long getHeaderPos() {
		return headerPos;
	}

	public void setHeaderPos(long headerPos) {
		this.headerPos = headerPos;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public LttTime getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(LttTime beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public long getBeginCycleCount() {
		return beginCycleCount;
	}

	public void setBeginCycleCount(long beginCycleCount) {
		this.beginCycleCount = beginCycleCount;
	}

	public long getBeginFreq() {
		return beginFreq;
	}

	public void setBeginFreq(long beginFreq) {
		this.beginFreq = beginFreq;
	}

	public LttTime getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(LttTime endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public long getEndCycleCount() {
		return endCycleCount;
	}

	public void setEndCycleCount(long endCycleCount) {
		this.endCycleCount = endCycleCount;
	}

	public long getEndFreq() {
		return endFreq;
	}

	public void setEndFreq(long endFreq) {
		this.endFreq = endFreq;
	}

	public long getTsc() {
		return tsc;
	}

	public void setTsc(long tsc) {
		this.tsc = tsc;
	}

	public long getFreq() {
		return freq;
	}

	public void setFreq(long freq) {
		this.freq = freq;
	}

	public long getCyc2nsScale() {
		return cyc2nsScale;
	}

	public void setCyc2nsScale(long cyc2nsScale) {
		this.cyc2nsScale = cyc2nsScale;
	}

}
