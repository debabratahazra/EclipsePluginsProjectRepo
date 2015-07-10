package com.zealcore.se.iw.types.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NumberType implements IFieldType {

	private Method valueOf;

	private final Object[] args = new Object[1];

	private final String label;

	private final String id;

	public NumberType(final Class<? extends Number> delegator) {
		this.label = delegator.getSimpleName();
		this.id = "TYPE_NUMBER_" + getLabel().toUpperCase();
		try {
			this.valueOf = delegator.getMethod("valueOf",
					new Class[] { String.class });
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public Object valueOf(final String text) {
		this.args[0] = text;
		try {
			return this.valueOf.invoke(null, this.args);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException("Parse error, \"" + text
					+ "\" is not a " + this.label + ".");
		}
	}

	public String getLabel() {
		return this.label;
	}

	public String getId() {
		return this.id;
	}

	public boolean canMatch(final String proposal) {
		this.args[0] = proposal;
		try {
			this.valueOf.invoke(null, this.args);
		} catch (final NumberFormatException e) {
			return false;
		} catch (final IllegalArgumentException e) {
			return false;
		} catch (final IllegalAccessException e) {
			return false;
		} catch (final InvocationTargetException e) {
			return false;
		}
		return true;
	}
}
