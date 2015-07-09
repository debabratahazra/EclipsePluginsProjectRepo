package com.odcgroup.page.domain.ui.dnd;

import java.util.List;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * @author atr
 */
public class DomainElementTransfer extends ByteArrayTransfer {
	
	/** */
	private static final DomainElementTransfer INSTANCE = new DomainElementTransfer();
	/** */
	private static final String TYPE_NAME = DomainElementTransfer.class.getName();
	/** */
	private static final int TYPE_ID = registerType(TYPE_NAME);
	/** */
	private List<MdfModelElement> elements;
	/** */
	private long startTime;

	/**
	 * 
	 */
	private DomainElementTransfer() {
	}
	
	/**
	 * Returns the singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static DomainElementTransfer getInstance() {
		return INSTANCE;
	}	

	/**
	 * @param elements
	 */
	public void setModelElements(List<MdfModelElement> elements) {
		this.elements = elements;
	}

	/**
	 * @return List<MdfModelElement>
	 */
	public List<MdfModelElement> getModelElements() {
		return elements;
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * multiple running copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#javaToNative(Object, TransferData)
	 */
	@SuppressWarnings("unchecked")
	public void javaToNative(Object object, TransferData transferData) {
		setModelElements((List<MdfModelElement>) object);
		startTime = System.currentTimeMillis();

		if (transferData != null)
			super.javaToNative(String.valueOf(startTime).getBytes(), transferData);
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * multiple running. copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#nativeToJava(TransferData)
	 */
	public Object nativeToJava(TransferData transferData) {
		byte[] bytes = (byte[]) super.nativeToJava(transferData);
		long startTime = Long.parseLong(new String(bytes));
		return (this.startTime == startTime) ? getModelElements() : null;
	}

	/**
	 * @see org.eclipse.swt.dnd.Transfer#getTypeIds()
	 */
	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPE_ID };
	}

	/**
	 * @see org.eclipse.swt.dnd.Transfer#getTypeNames()
	 */
	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}
}
