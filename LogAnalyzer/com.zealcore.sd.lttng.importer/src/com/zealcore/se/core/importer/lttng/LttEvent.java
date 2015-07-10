package com.zealcore.se.core.importer.lttng;

import java.util.Iterator;
import java.util.Map;

public class LttEvent {

	private int block;

	private int offset;

	/* Timekeeping */
	private long tsc; /* Current timestamp counter */

	private int eventId;

	private int facilityId;

	private int cpu;

	private LttTime eventTime;

	private long eventPos;

	private Map<String, String> fields; /* event data */

	private int dataSize;

	private int eventSize; /*
							 * event_size field of the header : used to verify
							 * data_size from marker.
							 */

	private long timeStamp;

	private String name;

	/*
	 * Format event data based on the marker
	 */
	public void parseEventDataToFields(MarkerInfo marker, String eventData) {

	}

	public int getBlock() {
		return block;

	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getTsc() {
		return tsc;
	}

	public void setTsc(long tsc) {
		this.tsc = tsc;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public LttTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LttTime eventTime) {
		this.eventTime = eventTime;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public long getEventPos() {
		return eventPos;
	}

	public void setEventPos(long eventPos) {
		this.eventPos = eventPos;
	}

	public int getDataSize() {

		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getEventSize() {
		return eventSize;
	}

	public void setEventSize(int eventSize) {
		this.eventSize = eventSize;
	}

	public String getEventName() {
		return null;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getCpu() {
		return cpu;
	}

	public long getOriginalTimestamp() {

		return (eventTime.getTvSec() * 1000000000) + eventTime.getTvNsec();
	}

	public long getTimestamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timestamp) {
		this.timeStamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("cpu " + getCpu());
		buf.append(" Name " + getName());
		buf.append(" Fields " + getFields().toString() + "\n");
		return buf.toString();
	}

	public String getEventContent() {

		Iterator<String> fieldIter = fields.values().iterator();
		StringBuffer strBuffer = new StringBuffer();

		while (fieldIter.hasNext()) {

			strBuffer.append((String) fieldIter.next());
			strBuffer.append(",");
		}
		return strBuffer.toString();
	}
}
