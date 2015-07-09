package com.odcgroup.workbench.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author atr
 */
public class OfsElementTransfer extends ByteArrayTransfer {

	/** */
	private static final int OFS_FOLDER = 0x4;
	/** */
	private static final int OFS_PACKAGE = 0x2;
	/** */
	private static final int OFS_MODEL = 0x1;

	/** singleton instance */
	private static final OfsElementTransfer instance = new OfsElementTransfer();

	/**
	 * Create a unique ID to make sure that different Eclipse applications use
	 * different "types" of <code>ResourceTransfer</code>
	 */
	private static final String TYPE_NAME = OfsElementTransfer.class.getName() + ":" + System.currentTimeMillis() + ":"+instance.hashCode();//$NON-NLS-1$

	/** */
	private static final int TYPE_ID = registerType(TYPE_NAME);

	/** The current workspace, needed to retrieve project given its name */
	private IWorkspace workspace = ResourcesPlugin.getWorkspace();

	/**
	 * Creates a new transfer object
	 */
	private OfsElementTransfer() {
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

	/**
	 * Returns the singleton instance.
	 * 
	 * @return the singleton instance
	 */
	public static OfsElementTransfer getInstance() {
		return instance;
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * multiple running copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#javaToNative(Object, TransferData)
	 */
	public void javaToNative(Object data, TransferData transferData) {

		if (!(data instanceof IOfsElement[])) {
			return;
		}

		IOfsElement[] ofsElements = (IOfsElement[]) data;
		/**
		 * The resource serialization format is:
		 * 
		 * <pre>
		 * (int) number of OFS elements
		 * 
		 * Then, the following for each OFS element: 
		 * 
		 * (int) type (0x1=model, 0x2=package, 0x4=folder) 
		 * (int) scope
		 * (String) Project name 
		 * (String) URI
		 * </pre>
		 */

		int elementCount = ofsElements.length;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);

			// write the number of resources
			dataOut.writeInt(elementCount);

			// write each resource
			for (int ix = 0; ix < ofsElements.length; ix++) {
				writeOfsElement(dataOut, ofsElements[ix]);
			}

			// cleanup
			dataOut.close();
			out.close();
			byte[] bytes = out.toByteArray();
			super.javaToNative(bytes, transferData);
		} catch (IOException e) {
			// it's best to send nothing if there were problems
		}
	}

	/**
	 * The data object is not converted to bytes. It is held onto in a field.
	 * Instead, a checksum is written out to prevent unwanted drags across
	 * multiple running. copies of Eclipse.
	 * 
	 * @see org.eclipse.swt.dnd.Transfer#nativeToJava(TransferData)
	 */
	public Object nativeToJava(TransferData transferData) {
		/**
		 * The resource serialization format is:
		 * 
		 * <pre>
		 * (int) number of OFS elements
		 * 
		 * Then, the following for each OFS element: 
		 * 
		 * (int) type (0x1=model, 0x2=package, 0x4=folder) 
		 * (int) scope
		 * (String) Project name 
		 * (String) URI
		 * </pre>
		 */

		byte[] bytes = (byte[]) super.nativeToJava(transferData);
		if (bytes == null) {
			return null;
		}
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			int count = in.readInt();
			IOfsElement[] results = new IOfsElement[count];
			for (int i = 0; i < count; i++) {
				results[i] = readOfsElement(in);
			}
			return results;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Reads an OFS element from the given stream.
	 * 
	 * @param dataIn
	 *            the input stream
	 * @return the OFS element
	 * @exception IOException
	 *                if there is a problem reading from the stream
	 */
	private IOfsElement readOfsElement(DataInputStream dataIn) throws IOException {
		int type = dataIn.readInt();
		int scope = dataIn.readInt();
		String projectName = dataIn.readUTF();
		String strURI = dataIn.readUTF();

		URI uri = URI.createURI(strURI);

		IProject project = workspace.getRoot().getProject(projectName);
		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);

		IOfsElement element = null;

		if (ofsProject!=null) {
			if(type == OFS_MODEL) {
				try {
					element = ofsProject.getOfsModelResource(uri);
				} catch (ModelNotFoundException e) {
					// do nothing
				}
			} else if (type == OFS_FOLDER) {
				element = null;
			} else if (type == OFS_PACKAGE) {
				element = null;
			} else {
				throw new IllegalArgumentException("Unknown type OFS element in OfsElementTransfer.readResource"); //$NON-NLS-1$
			}
		}
		return element;
	}

	/**
	 * Writes the given OFS element to the given stream.
	 * 
	 * @param dataOut
	 *            the output stream
	 * @param ofsElement
	 *            the OFS element
	 * @exception IOException
	 *                if there is a problem writing to the stream
	 */
	private void writeOfsElement(DataOutputStream dataOut, IOfsElement ofsElement) throws IOException {
		dataOut.writeInt(getType(ofsElement));
		dataOut.writeInt(ofsElement.getScope());
		dataOut.writeUTF(ofsElement.getOfsProject().getProject().getName());
		dataOut.writeUTF(ofsElement.getURI().toString());
	}

	/**
	 * @param ofsElement
	 * @return
	 */
	private int getType(IOfsElement ofsElement) {
		if (ofsElement instanceof IOfsModelFolder) {
			return OFS_FOLDER;
		} else if (ofsElement instanceof IOfsModelPackage) {
			return OFS_PACKAGE;
		} else if (ofsElement instanceof IOfsModelResource) {
			return OFS_MODEL;
		}
		// unknown type
		return 0;
	}

}
