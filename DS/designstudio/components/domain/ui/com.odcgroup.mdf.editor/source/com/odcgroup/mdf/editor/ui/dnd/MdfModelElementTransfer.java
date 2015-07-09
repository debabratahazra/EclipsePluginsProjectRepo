package com.odcgroup.mdf.editor.ui.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.odcgroup.mdf.metamodel.MdfModelElement;


public class MdfModelElementTransfer extends ByteArrayTransfer {
    public static final MdfModelElementTransfer INSTANCE = new MdfModelElementTransfer();
    private final String TYPE_NAME = MdfModelElementTransfer.class.getName();
    private final int TYPE_ID = registerType(TYPE_NAME);
    private MdfModelElement element;
    private long startTime;

    MdfModelElementTransfer() {
        super();
    }

    public void setModelElement(MdfModelElement element) {
        this.element = element;
    }

    public MdfModelElement getModelElement() {
        return element;
    }

    /**
     * The data object is not converted to bytes. It is held onto in a field. Instead, a
     * checksum is written out to prevent unwanted drags across mulitple running copies of
     * Eclipse.
     * @see org.eclipse.swt.dnd.Transfer#javaToNative(Object, TransferData)
     */
    public void javaToNative(Object object, TransferData transferData) {
        setModelElement((MdfModelElement) object);
        startTime = System.currentTimeMillis();

        if (transferData != null)
            super.javaToNative(String.valueOf(startTime).getBytes(),
                transferData);
    }

    /**
     * The data object is not converted to bytes. It is held onto in a field. Instead, a
     * checksum is written out to prevent unwanted drags across mulitple running. copies of
     * Eclipse.
     * @see org.eclipse.swt.dnd.Transfer#nativeToJava(TransferData)
     */
    public Object nativeToJava(TransferData transferData) {
        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        long startTime = Long.parseLong(new String(bytes));
        return (this.startTime == startTime) ? getModelElement() : null;
    }

    protected int[] getTypeIds() {
        return new int[] { TYPE_ID };
    }

    protected String[] getTypeNames() {
        return new String[] { TYPE_NAME };
    }

}
