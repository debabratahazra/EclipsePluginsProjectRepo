/* SerialPortEvent.java --
   Copyright (C) 2005 Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package javax.comm;

import java.util.EventObject;

public class SerialPortEvent extends EventObject {

	static final long serialVersionUID = -8036409165500102378L;

	public static final int BI = 10;
	public static final int CD = 6;
	public static final int CTS = 3;
	public static final int DATA_AVAILABLE = 1;
	public static final int DSR = 4;
	public static final int FE = 9;
	public static final int OE = 7;
	public static final int OUTPUT_BUFFER_EMPTY = 2;
	public static final int PE = 8;
	public static final int RI = 5;
	
	private final int eventtype;
	private boolean oldvalue;
	private boolean newvalue;
	
	public SerialPortEvent(SerialPort srcport,
                       int eventtype,
                       boolean oldvalue,
                       boolean newvalue) {
		super(srcport);
		this.eventtype = eventtype;
		this.oldvalue = oldvalue;
		this.newvalue = newvalue;
	}
	
	public int getEventType() {
		return this.eventtype;
	}
	
	public boolean getOldValue() {
		return this.oldvalue;
	}
	
	public boolean getNewValue() {
		return this.newvalue;
	}

}
