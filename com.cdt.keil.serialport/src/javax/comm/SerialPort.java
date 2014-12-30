/* SerialPort.java --
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

public abstract class SerialPort extends CommPort {
	public static final int DATABITS_5=5;
	public static final int DATABITS_6=6 ;
	public static final int DATABITS_7=7 ;
	public static final int DATABITS_8=8 ;
	public static final int FLOWCONTROL_NONE=0 ;
	public static final int FLOWCONTROL_RTSCTS_IN=1 ;
	public static final int FLOWCONTROL_RTSCTS_OUT=2 ;
	public static final int FLOWCONTROL_XONXOFF_IN=4 ;
	public static final int FLOWCONTROL_XONXOFF_OUT=8 ;
	public static final int PARITY_EVEN=2 ;
	public static final int PARITY_MARK=3 ;
	public static final int PARITY_NONE=0 ;
	public static final int PARITY_ODD=1 ;
	public static final int PARITY_SPACE=4 ;
	public static final int STOPBITS_1=1 ;
	public static final int STOPBITS_1_5=3 ;
	public static final int STOPBITS_2=2 ;

	public SerialPort() {
	}
	
	public abstract int getBaudRate();
	public abstract int getDataBits();
	public abstract int getStopBits();
	public abstract int getParity();
	public abstract void sendBreak(int millis);
	public abstract void setFlowControlMode(int flowcontrol)
                                 throws UnsupportedCommOperationException;
	
	public abstract int getFlowControlMode();
	
	public void setRcvFifoTrigger(int trigger) {
		// ‰½‚à‚µ‚È‚¢
	}
	
	public abstract void setSerialPortParams(int baudrate,
                                         int dataBits,
                                         int stopBits,
                                         int parity)
                                  throws UnsupportedCommOperationException;
    
    public abstract void setDTR(boolean dtr);
    public abstract boolean isDTR();
    public abstract void setRTS(boolean rts);
    public abstract boolean isRTS();
    public abstract boolean isCTS();
    public abstract boolean isDSR();
    public abstract boolean isRI();
    public abstract boolean isCD();
    public abstract void addEventListener(SerialPortEventListener lsnr)
                               throws java.util.TooManyListenersException;
    
    public abstract void removeEventListener();
    public abstract void notifyOnDataAvailable(boolean enable);
    public abstract void notifyOnOutputEmpty(boolean enable);
    public abstract void notifyOnCTS(boolean enable);
    public abstract void notifyOnDSR(boolean enable);
    public abstract void notifyOnRingIndicator(boolean enable);
    public abstract void notifyOnCarrierDetect(boolean enable);
    public abstract void notifyOnOverrunError(boolean enable);
    public abstract void notifyOnParityError(boolean enable);
    public abstract void notifyOnFramingError(boolean enable);
    public abstract void notifyOnBreakInterrupt(boolean enable);
}

