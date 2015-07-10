/* CommPortIdentifier.java --
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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import javax.swing.event.EventListenerList;

public class CommPortIdentifier {
	public static final int PORT_SERIAL = 1;
	public static final int PORT_PARALLEL = 2;
	
	/**
	 * 登録されているCommPortIdentifierのMap。
	 */
	private static Map identifiersMap;

	static {
		// 初期化処理を行う
		try {
			// ポート名順にソートするためにTreeMapを使う
			identifiersMap = new TreeMap();
			
			// ToDo: 設定ファイルを読み込むようにする
			Class clazz = Class.forName("gnu.javax.comm.wce.WCECommDriver");
			CommDriver initialDriver = (CommDriver) clazz.newInstance();
			initialDriver.initialize();
		} catch (Exception e) {
			e.printStackTrace();
			Error error = new Error("Failed to load CommDriver");
			error.initCause(e);
			throw error;
		}
	}
	
	
	private final String name;
	private final int type;
	private final CommDriver driver;
	
	/**
	 * オーナー名
	 */
	private String currentOwner;
	
	/**
	 * イベントリスナリスト
	 */
	private EventListenerList listeners;
	
	public CommPortIdentifier(String name,
                          CommPort port,
                          int type,
                          CommDriver driver) {
		this.name = name;
		this.type = type;
		this.driver = driver;
	}
	
	public static Enumeration getPortIdentifiers() {
		return new IdentifiersEnumeration();
	}
	
	public static CommPortIdentifier getPortIdentifier(String portName) throws NoSuchPortException {
		synchronized (identifiersMap) {
			CommPortIdentifier identifier = (CommPortIdentifier) identifiersMap.get(portName);
			if (identifier == null) {
				throw new NoSuchPortException();
			}
			return identifier;
		}
	}
	
	public static CommPortIdentifier getPortIdentifier(CommPort port)
                                            throws NoSuchPortException {
		return getPortIdentifier(port.getName());
	}
	
	public static void addPortName(String portName, int portType, CommDriver driver) {
		synchronized (identifiersMap) {
			CommPortIdentifier identifier = new CommPortIdentifier(portName,
																   null,		// この時点ではnullを設定しておく
																   portType,
																   driver);
			identifiersMap.put(portName, identifier);
		}
	}
	
	public CommPort open(String appname, int timeout)
									throws PortInUseException {
		if (isCurrentlyOwned()) {
			// イベントを通知する
			// このイベントを通知中に該当ポートがclose()されれば、ポートを開くことが可能になる
			notifyPortOwnershipListeners(CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED);
		
			if (isCurrentlyOwned()) {
				// 誰も手放さなかった。おしまい。
				PortInUseException piue = new PortInUseException();
				piue.currentOwner = this.currentOwner;
				throw piue;
			}
		}
		
		// CommDriverからCommPortを得る
		CommPort port = this.driver.getCommPort(getName(),
												getPortType());
		if (port == null) {
			// タイムアウト時間まで待つ
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			// 再挑戦
			port = this.driver.getCommPort(getName(),
										   getPortType());
			if (port == null) {
				// やっぱりダメだった
				throw new PortInUseException();
			}
		}
		this.currentOwner = appname;
		
		// リスナーに所有されたことを通知する
		notifyPortOwnershipListeners(CommPortOwnershipListener.PORT_OWNED);
		
		return port;
	}
	
	public CommPort open(java.io.FileDescriptor fd)
              throws UnsupportedCommOperationException {
		// ToDo:実装する
		throw new UnsupportedCommOperationException();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPortType() {
		return this.type;
	}
	
	public String getCurrentOwner() {
		return this.currentOwner;
	}
	
	public boolean isCurrentlyOwned() {
		return this.currentOwner != null;
	}
	
	public void addPortOwnershipListener(CommPortOwnershipListener listener) {
		if (this.listeners == null) {
			this.listeners = new EventListenerList();
		}
		
		this.listeners.add(CommPortOwnershipListener.class, listener);
	}
	
	public void removePortOwnershipListener(CommPortOwnershipListener listener) {
		if (this.listeners != null) {
			this.listeners.remove(CommPortOwnershipListener.class, listener);
		}
	}
	
	/**
	 * 指定された名前のポートが閉じた際に呼び出される。
	 * (CommPort.close()がこのメソッドを呼び出す）
	 */
	void portClosed() {
		this.currentOwner = null;
		notifyPortOwnershipListeners(CommPortOwnershipListener.PORT_UNOWNED);
	}
	
	/**
	 * PortOwnerShipListenerに通知する
	 */
	private void notifyPortOwnershipListeners(int type) {
		if (this.listeners != null) {
			CommPortOwnershipListener[] l
				= (CommPortOwnershipListener[]) this.listeners.getListeners(CommPortOwnershipListener.class);
			for (int i = 0; i < l.length; ++i) {
				l[i].ownershipChange(type);
			}
		}
	}

	/**
	 * CommPortIdentifierを列挙する
	 */
	static class IdentifiersEnumeration implements Enumeration {
		Iterator iter = identifiersMap.keySet().iterator();
		
		public boolean hasMoreElements() {
			return iter.hasNext();
		}
		
		public Object nextElement() {
			return identifiersMap.get(iter.next());
		}
	}
}
