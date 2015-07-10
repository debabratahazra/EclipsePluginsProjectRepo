package com.zealcore.se.core.importer.lttng;

public class MarkerField {

	public static enum LttType {
		LTT_TYPE_SIGNED_INT, LTT_TYPE_UNSIGNED_INT, LTT_TYPE_POINTER, LTT_TYPE_STRING, LTT_TYPE_COMPACT, LTT_TYPE_NONE,
	};

	public static int LTT_ATTRIBUTE_NETWORK_BYTE_ORDER = 2;

	private String name;
	private LttType type = LttType.LTT_TYPE_NONE;
	private long offset; /* offset in the event data */
	private long size = 0;
	private long alignment;
	private long attributes;
	private int staticOffset; /*
							 * boolean - private - is the field offset
							 * statically known with the preceding types ?
							 */
	private String fmt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LttType getType() {
		return type;
	}

	public void setType(LttType type) {
		this.type = type;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getAlignment() {
		return alignment;
	}

	public void setAlignment(long alignment) {
		this.alignment = alignment;
	}

	public long getAttributes() {
		return attributes;
	}

	public void setAttributes(long attributes) {
		this.attributes = attributes;
	}

	public int getStaticOffset() {
		return staticOffset;
	}

	public void setStaticOffset(int staticOffset) {
		this.staticOffset = staticOffset;
	}

	public String getFmt() {
		return fmt;
	}

	public void setFmt(String fmt) {
		this.fmt = fmt;
	}

}
