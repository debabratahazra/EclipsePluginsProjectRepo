package com.odcgroup.aaa.ui.internal.model;

import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;

import com.odcgroup.aaa.connector.internal.util.ImportReportVO;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 * @since DS 1.40.0
 */
public interface AAAImportFacade {
	
	/**
	 * @throws InvocationTargetException 
	 * Validate the connection
	 * @throws
	 */
	void validateConnection(ConnectionInfo connectionInfo) throws InvocationTargetException;
	
	
	/**
	 * @param listener
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);
	
	/**
	 * @param listener
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @return
	 */
	AAAPreferences getPreferences();
	
	/**
	 * @return
	 */
	List<AAAFormatType> getFormatTypes();
	
	/**
	 * @param formatType
	 */
	void setSelectedFormatType(AAAFormatType formatType);

	/**
	 * @param text
	 */
	void setFormatCodePattern(String text);
	
	/**
	 * @param text
	 */
	void resetFormatCodePattern();
	
	/**
	 * @return
	 */
	String getFormatCodePattern();

	/**
	 * @return
	 */
	List<AAAFormatCode> getFormatCodes();

	/**
	 * @param formatCode
	 * @return
	 */
	boolean matchFormatCodePattern(AAAFormatCode formatCode);
	
	/**
	 * @param monitor TODO
	 * @param connectionInfo
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	boolean importMetaDictionary(IOfsProject ofsProject, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException;

	/**
	 * @param monitor TODO
	 * @param connectionInfo
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	ImportReportVO importFormats(IOfsModelPackage ofsPackage, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException;

	/**
	 * @param resource
	 * @param monitor 
	 * @throws InterruptedException 
	 */
	boolean synchronizeFormats(IOfsModelResource resource, ISelection selection, IProgressMonitor monitor) throws InterruptedException;
	
	// TODO: remove
	public String getRegExpForCurrentCodePattern();


	/**
	 * @return the list of available functions
	 */
	public List<AAAFunction> getFunctions();

	/**
	 * @return the selected function
	 */
	public AAAFunction getSelectedFunction();

	/**
	 * @param function
	 */
	void setSelectedFunction(AAAFunction function);
	
	/**
	 * @return a set of selected (checked) codes
	 */
	Set<AAAFormatCode> getSelectedFormatCodes();

}
