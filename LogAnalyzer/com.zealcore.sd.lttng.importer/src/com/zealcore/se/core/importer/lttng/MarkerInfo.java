package com.zealcore.se.core.importer.lttng;

import java.util.List;

public class MarkerInfo {

	private String name;

	private String format;

	private int intSize, longSize, pointerSize, size_tSize;

	private int alignment;

	private int id;

	private int largestAlignment;

	private int size;

	private List<MarkerField> fields;

	public static final int MARKER_CORE_IDS = 8;

	public static final int MARKER_ID_SET_MARKER_ID = 0;

	public static final int MARKER_ID_SET_MARKER_FORMAT = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<MarkerField> getFields() {
		return fields;
	}

	public void setFields(List<MarkerField> fields) {
		this.fields = fields;
	}

	public int getIntSize() {
		return intSize;
	}

	public void setIntSize(int intSize) {
		this.intSize = intSize;
	}

	public int getLongSize() {
		return longSize;
	}

	public void setLongSize(int longSize) {
		this.longSize = longSize;
	}

	public int getPointerSize() {
		return pointerSize;
	}

	public void setPointerSize(int pointerSize) {
		this.pointerSize = pointerSize;
	}

	public int getSize_tSize() {
		return size_tSize;
	}

	public void setSize_tSize(int size_tSize) {
		this.size_tSize = size_tSize;
	}

	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLargestAlignment() {
		return largestAlignment;
	}

	public void setLargestAlignment(int largestAlignment) {
		this.largestAlignment = largestAlignment;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("name " + name);
		buffer.append("id " + id);
		buffer.append("format " + format);
		return buffer.toString();
	}
}
