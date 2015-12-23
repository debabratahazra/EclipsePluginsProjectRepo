package com.ifx.dave.monitor.ui.zoom;

public interface AxisTickFormatter {

	public void setRange( double low, double high, double tickSpacing );

	public String format( Number value );
}
