package com.ifx.dave.monitor.ui.zoom;

import java.text.NumberFormat;

/**
 * DefaultAxisTickFormatter formats labels using a default number instance.
 *
 */
public class DefaultAxisTickFormatter implements AxisTickFormatter {
//	private final NumberFormat normalFormat = NumberFormat.getNumberInstance();
//	private final NumberFormat engFormat = new DecimalFormat( "0.###E0" );

	private NumberFormat currFormat = NumberFormat.getNumberInstance();

	public DefaultAxisTickFormatter() {
	}

	@Override
	public void setRange( double low, double high, double tickSpacing ) {
		//The below is an attempt as using engineering notation for large numbers, but it doesn't work.

		/*currFormat = normalFormat;
		double log10 = Math.log10( low );
		if ( log10 < -4.0 || log10 > 5.0 ) {
			currFormat = engFormat;
		} else {
			log10 = Math.log10( high );
			if ( log10 < -4.0 || log10 > 5.0 ) {
				currFormat = engFormat;
			}
		}

		if (tickSpacing <= 10000.0)
			currFormat = normalFormat;
		else
			currFormat = engFormat;*/
	}

	@Override
	public String format( Number value ) {
		return currFormat.format( value );
	}
}
