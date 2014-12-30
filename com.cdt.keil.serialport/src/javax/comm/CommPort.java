/* CommPort.java --
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

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class CommPort {
	protected String name;
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return null;
	}
	
	public abstract InputStream getInputStream()
                       throws IOException;

	public abstract OutputStream getOutputStream()
                       throws IOException;


	public void close() {
		try {
			CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(this);
			if (identifier != null) {
				// �|�[�g���N���[�Y���ꂽ���Ƃ�ʒm����
				identifier.portClosed();
			}
		} catch (NoSuchPortException nspe) {
			nspe.printStackTrace();
		}
	}
	
	public abstract void enableReceiveThreshold(int thresh)
                                     throws UnsupportedCommOperationException;
                                     
	public abstract void disableReceiveThreshold();
	
	public abstract boolean isReceiveThresholdEnabled();
	
	public abstract int getReceiveThreshold();
	
	public abstract void enableReceiveTimeout(int rcvTimeout)
                                   throws UnsupportedCommOperationException;

	public abstract void disableReceiveTimeout();
	
	public abstract boolean isReceiveTimeoutEnabled();
	
	public abstract int getReceiveTimeout();
	
	public abstract void enableReceiveFraming(int framingByte)
                                   throws UnsupportedCommOperationException;
    public abstract void disableReceiveFraming();
    public abstract boolean isReceiveFramingEnabled();
    public abstract int getReceiveFramingByte();
    public abstract void setInputBufferSize(int size);
    public abstract int getInputBufferSize();
    public abstract void setOutputBufferSize(int size);
    public abstract int getOutputBufferSize();
}
