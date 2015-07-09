package com.odcgroup.server.preferences;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class RGBColor {
	
	private int red;
	private int green;
	private int blue;
	
	public final int red() { return red; }
	public final int green() { return green; }
	public final int blue() { return blue; }
	
	public static RGBColor asRGBColor(String value) {
        if (value == null) {
			throw new IllegalArgumentException("Null doesn't represent a valid RGB Color"); //$NON-NLS-1$
		}
        StringTokenizer stok = new StringTokenizer(value, ","); //$NON-NLS-1$

        try {
            String red = stok.nextToken().trim();
            String green = stok.nextToken().trim();
            String blue = stok.nextToken().trim();
            int rval = 0, gval = 0, bval = 0;
            try {
                rval = Integer.parseInt(red);
                gval = Integer.parseInt(green);
                bval = Integer.parseInt(blue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
            return new RGBColor(rval, gval, bval);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
	}
	
	public String asString() {
		StringBuffer buffer = new StringBuffer();
        buffer.append(red);
        buffer.append(','); //$NON-NLS-1$
        buffer.append(green);
        buffer.append(','); //$NON-NLS-1$
        buffer.append(blue);
        return buffer.toString();
	}
	
	public RGBColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

}
