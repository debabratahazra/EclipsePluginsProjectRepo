package com.odcgroup.aaa.ui.internal.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.aaa.connector.internal.util.ImportReportVO;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAFormatType;
import com.odcgroup.aaa.ui.internal.model.AAAFunction;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.AAAPreferences;
import com.odcgroup.aaa.ui.internal.model.ConnectionInfo;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 * @since DS 1.40.0
 */
public abstract class AAAAbstractImportFacade implements AAAImportFacade {

	/** Connection information used */
	private static ConnectionInfo connectionInfoUsed;
	
	/** Delegate used to implement property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	/** the current format code pattern */
	private String codePattern = DEFAULT_CODE_PATTERN;
	
	private static final String DEFAULT_CODE_PATTERN = "*";
	
	/** the selected format type */
	private AAAFormatType selectedFormatType = null;
	
	/** the selected function */
	private AAAFunction selectedFunction;
	
	/** the selected codes */
	private Set<AAAFormatCode> selectedCodes = new HashSet<AAAFormatCode>();
	
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(AAAAbstractImportFacade.class);

	/**
	 * Check if we need to reconnect to Triple'A 
	 * @param connectionInfo
	 */
	private boolean connect() {
		return doConnect();
	}
	
	/**
	 * Check if we need to reconnect & test connection to Triple'A for meta dict import 
	 * @param connectionInfo
	 */
	private boolean connectForMetaDict() {
		return doConnectForMetaDict();
	}
	
	private final void disconnect() {
		doDisconnect();
	}
	
	protected abstract void doConfigureConnectionFactory(ConnectionInfo connectionInfo) throws InvocationTargetException;
	
	protected abstract boolean doConnect();
	
	protected abstract boolean doConnectForMetaDict();
	
	protected abstract void doDisconnect();
	
	protected abstract boolean doImportMetaDictionary(IOfsProject ofsProject, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException;

	protected abstract ImportReportVO doImportFormats(IOfsModelPackage ofsPackage, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException;
	
	protected abstract boolean doSynchronizeFormats(IOfsModelResource resource, MdfModelElement element, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException;
	
	protected abstract List<AAAFormatType> doGetFormatTypes();
	
	protected abstract List<AAAFormatCode> doGetFormatCodes();

	protected abstract List<AAAFunction> doGetFunctions();


	/**
	 * Report a property change to registered listeners.
	 * 
	 * @param property
	 *            the name of the property that changed
	 * @param oldValue
	 *            the old value of this property
	 * @param newValue
	 *            the new value of this property
	 */
	protected void firePropertyChange(String property, Object oldValue, Object newValue) {
		if (pcsDelegate.hasListeners(property)) {
			pcsDelegate.firePropertyChange(property, oldValue, newValue);
		}
	}
	
	// TODO move to simple format composite
	public String getRegExpForCurrentCodePattern() {
		String pattern = getFormatCodePattern();
		return StringUtils.replace(pattern, "*", ".*");
	}

	protected final AAAFormatType getSelectedFormatType() {
		return this.selectedFormatType;
	}
	
	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		pcsDelegate.addPropertyChangeListener(listener);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			pcsDelegate.removePropertyChangeListener(listener);
		}
	}

	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportModel#getPreferences()
	 */
	public AAAPreferences getPreferences() {
		return new AAAPreferenceImpl();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#validateConnection()
	 */
	public void validateConnection(ConnectionInfo connectionInfo) throws InvocationTargetException {
		if (connectionInfoUsed == null || !connectionInfoUsed.equals(connectionInfo)) {
			try {
				doConfigureConnectionFactory(connectionInfo);
			} catch (InvocationTargetException e) {
				connectionInfoUsed = null;
				throw e;
			}
			connectionInfoUsed = connectionInfo;
			// Remember for the next time the wizard is started
			getPreferences().save(connectionInfoUsed);
		}
		if (!connect()) {
			throw new InvocationTargetException(new RuntimeException(Messages.getString("aaa.wizard.invalid.connection")));
		}
		disconnect();
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#getFormatTypes()
	 */
	public List<AAAFormatType> getFormatTypes() {
		List<AAAFormatType> result = null;
		try {
			if (connect()) {
				result = doGetFormatTypes();
			}
		} catch (Exception e) {
			LOGGER.error("Unable to get the format types", e);
		} finally {
			disconnect();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#getFunctions()
	 */
	public List<AAAFunction> getFunctions() {
		List<AAAFunction> result = null;
		try {
			if (connect()) {
				result = doGetFunctions();
			}
		} catch (Exception e) {
			LOGGER.error("Unable to get the format types", e);
		} finally {
			disconnect();
		}
		return result;
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#setFormatType(com.odcgroup.mdf.aaa.integration.ui.model.AAAFormatType)
	 */
	public final void setSelectedFormatType(AAAFormatType formatType) {
		this.selectedFormatType = formatType;
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#setCodePattern(java.lang.String)
	 */
	public void setFormatCodePattern(String text) {
		String oldPattern = codePattern;
		codePattern = text;
		firePropertyChange("formatCode", oldPattern, codePattern);
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#resetCodePattern()
	 */
	public void resetFormatCodePattern() {
		setFormatCodePattern(DEFAULT_CODE_PATTERN);
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#getCodePattern()
	 */
	public String getFormatCodePattern() {
		return codePattern;
	}	
	
	public AAAFunction getSelectedFunction() {
		return selectedFunction;
	}
	
	public void setSelectedFunction(AAAFunction function) {
		AAAFunction oldFunction = selectedFunction;
		selectedFunction = function;
		firePropertyChange("function", oldFunction, selectedFunction);
	}
	

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#getFormatCodes()
	 */
	public List<AAAFormatCode> getFormatCodes() {
		List<AAAFormatCode> result = null;
		try {
			if (connect()) {
				result = doGetFormatCodes();
			}
		} catch (Exception e) {
			LOGGER.error("Unable to get the format types", e);
		} finally {
			disconnect();
		}
		return result;
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#getSelectedFormatCodes()
	 */
	public Set<AAAFormatCode> getSelectedFormatCodes() {
		return selectedCodes;
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#matchFormatCodePattern(com.odcgroup.mdf.aaa.integration.ui.model.AAAFormatCode)
	 */
	public boolean matchFormatCodePattern(AAAFormatCode formatCode) {
		String code = formatCode.getCode();
		return code.matches(getRegExpForCurrentCodePattern());
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#importFormats()
	 */
	public ImportReportVO importFormats(IOfsModelPackage ofsPackage, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
		
		try {
			if (connect()) {
				return doImportFormats(ofsPackage, monitor);
			} else {
				throw new InvocationTargetException(new RuntimeException(Messages.getString("aaa.wizard.invalid.connection")));
			}
		} catch (InterruptedException e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#importMetaDictionary()
	 */
	public boolean importMetaDictionary(IOfsProject ofsProject, IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
		
		try {
			if (connectForMetaDict()) {
				return doImportMetaDictionary(ofsProject, monitor);
			}
			return false;
		} catch (InterruptedException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Unable to import the meta dictionary", e);
			return false;
		} finally {
			disconnect();		
		}
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAImportFacade#synchronizeFormats(com.odcgroup.workbench.core.IOfsModelResource, org.eclipse.jface.viewers.ISelection, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean synchronizeFormats(IOfsModelResource resource, ISelection selection, IProgressMonitor monitor) throws InterruptedException {
		try {
			if (connect()) {
				MdfModelElement element = null;
				if (selection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection)selection).getFirstElement();
					if (obj instanceof MdfModelElement) {
						element = (MdfModelElement)obj;
					}
				}
				return doSynchronizeFormats(resource, element, monitor);
			}
			return false;
		} catch (InterruptedException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Unable to synchronize the formats", e);
			return false;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 
	 */
	protected AAAAbstractImportFacade() {
		
	}


}
