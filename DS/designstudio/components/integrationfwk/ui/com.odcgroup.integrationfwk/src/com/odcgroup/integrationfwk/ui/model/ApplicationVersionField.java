package com.odcgroup.integrationfwk.ui.model;

/**
 * Model class which contains the field details as instance of {@link Field}
 * 
 * @author sbharathraja
 * 
 */
public class ApplicationVersionField extends Field {
	/** name to be displayed in ui */
	private String displayName;
	/** name of the field */
	private String fieldName;
	/** type of the field */
	private String fieldType;
	/** hash code */
	private volatile int hashCode;

	/**
	 * constructs {@link ApplicationVersionField}
	 */
	public ApplicationVersionField() {
		initialize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Field)) {
			return false;
		}
		Field field = (Field) obj;
		// if the user wants to add a same field with different display name,
		// that's a valid use case and the tooling shouldn't block the user as
		// its duplicate field...
		return field.getFieldName().equalsIgnoreCase(fieldName)
				&& field.getDisplayName().equalsIgnoreCase(displayName)
				&& field.getFieldType().equalsIgnoreCase(fieldType);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String getFieldType() {
		return fieldType;
	}

	@Override
	public int hashCode() {
		final int multiplier = 23;
		if (hashCode == 0) {
			int code = 5;
			code = multiplier * code + fieldType.hashCode();
			code = multiplier * code + displayName.hashCode();
			code = multiplier * code + fieldName.hashCode();
			hashCode = code;
		}
		return hashCode;
	}

	/**
	 * Helps to initialize the instance variables.
	 */
	private void initialize() {
		displayName = "";
		fieldName = "";
		fieldType = "";
		hashCode = 0;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
