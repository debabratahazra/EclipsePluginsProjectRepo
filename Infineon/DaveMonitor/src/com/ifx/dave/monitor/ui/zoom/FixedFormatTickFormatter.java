package com.ifx.dave.monitor.ui.zoom;

import java.text.Format;

public class FixedFormatTickFormatter implements AxisTickFormatter {
	private final Format format;

	public FixedFormatTickFormatter( Format format ) {
		this.format = format;
	}

	@Override
	public void setRange( double low, double high, double tickSpacing ) {
	}

	@Override
	public String format( Number value ) {
		return format.format( value );
	}
}
