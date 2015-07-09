package com.odcgroup.aaa.ui.internal.model.impl;

import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.aaa.ui.AAAUIPlugin;
import com.odcgroup.aaa.ui.internal.model.AAAPreferences;
import com.odcgroup.aaa.ui.internal.model.ConnectionInfo;
import com.odcgroup.aaa.ui.internal.model.FormatSelectionInfo;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAPreferenceImpl implements AAAPreferences {
	
	private static final String PREFS_PREFIX = "com.odcgroup.mdf.aaa.prefs.";
	private static final String SERVER_KEY = PREFS_PREFIX + "server";
	private static final String DATABASE_KEY = PREFS_PREFIX + "database";
	private static final String PORT_KEY = PREFS_PREFIX + "port";
	private static final String CHARSET_KEY = PREFS_PREFIX + "charset";
	private static final String USERNAME_KEY = PREFS_PREFIX + "username";
	
	private static final String FORMAT_CODE_KEY = PREFS_PREFIX+"formatCode";
	private static final String FORMAT_FUNCTION_KEY = PREFS_PREFIX+"formatFunction";
	private static final String FORMAT_TYPE_KEY = PREFS_PREFIX+"formatType";
	private static final String FORMAT_SORT_COLUMN = PREFS_PREFIX+"formatSortColumn";
	private static final String FORMAT_SORT_DIR = PREFS_PREFIX+"formatSortDirection";
	
	private IPreferenceStore store;
	
	protected IPreferenceStore doGetPreferenceStore() {
		return AAAUIPlugin.getDefault().getPreferenceStore();
	}
	
	protected final IPreferenceStore getPreferenceStore() {
		if (store == null) {
			store = doGetPreferenceStore();
		}
		return store;
	}

	@Override
	public final String getCharset() {
		return getPreferenceStore().getString(CHARSET_KEY);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getPortNumber()
	 */
	public final String getPortNumber() {
		return getPreferenceStore().getString(PORT_KEY);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getSQLServer()
	 */
	public final String getSQLServer() {
		return getPreferenceStore().getString(SERVER_KEY);
	}


	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getDatabase()
	 */
	public String getDatabase() {
		return getPreferenceStore().getString(DATABASE_KEY);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getUsername()
	 */
	public final String getUsername() {
		return getPreferenceStore().getString(USERNAME_KEY);
	}

	@Override
	public final void setCharset(String value) {
		getPreferenceStore().setValue(CHARSET_KEY, value);
	}
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setPortNumber(java.lang.String)
	 */
	public final void setPortNumber(String value) {
		getPreferenceStore().setValue(PORT_KEY, value);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setSQLServer(java.lang.String)
	 */
	public final void setSQLServer(String value) {
		getPreferenceStore().setValue(SERVER_KEY, value);
	}

	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setDatabase(java.lang.String)
	 */
	public void setDatabase(String value) {
		getPreferenceStore().setValue(DATABASE_KEY, value);
	}	
	
	/*
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setUsername(java.lang.String)
	 */
	public final void setUsername(String value) {
		getPreferenceStore().setValue(USERNAME_KEY, value);
	}

	public String getPassword() {
		return AAAUIPlugin.getPassword();
	}

	/* 
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#save(com.odcgroup.mdf.aaa.integration.ui.model.ConnectionInfo)
	 */
	public void save(ConnectionInfo connectionInfo) {
		setSQLServer(connectionInfo.getServer());
		setDatabase(connectionInfo.getDatabase());
		setPortNumber(connectionInfo.getPort());
		setCharset(connectionInfo.getCharset());
		setUsername(connectionInfo.getUsername());
		AAAUIPlugin.setPassword(connectionInfo.getPassword());
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#save(com.odcgroup.mdf.aaa.integration.ui.model.FormatSelectionInfo)
	 */
	public void save(FormatSelectionInfo formatSelectionInfo) {
		setFormatCode(formatSelectionInfo.getCode());
		setFormatType(formatSelectionInfo.getType());
		setFormatFunction(formatSelectionInfo.getFunction());
		setFormatSortColumn(formatSelectionInfo.getSortColumn());
		setFormatSortDirection(formatSelectionInfo.getSortDirection());
	}
	
	/**
	 * Empty constructor
	 */
	public AAAPreferenceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getFormatCode()
	 */
	public String getFormatCode() {
		return getPreferenceStore().getString(FORMAT_CODE_KEY);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getFormatDenom()
	 */	
	public String getFormatType() {
		return getPreferenceStore().getString(FORMAT_TYPE_KEY);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getFormatFunction()
	 */
	public String getFormatFunction() {
		return getPreferenceStore().getString(FORMAT_FUNCTION_KEY);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setFormatCode(java.lang.String)
	 */
	public void setFormatCode(String value) {
		getPreferenceStore().setValue(FORMAT_CODE_KEY, value);
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setFormatDenom(java.lang.String)
	 */
	public void setFormatType(String value) {
		getPreferenceStore().setValue(FORMAT_TYPE_KEY, value);
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setFormatFunction(java.lang.String)
	 */
	public void setFormatFunction(String value) {
		getPreferenceStore().setValue(FORMAT_FUNCTION_KEY, value);		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getFormatSortColumn()
	 */
	public int getFormatSortColumn() {
		return getPreferenceStore().getInt(FORMAT_SORT_COLUMN);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#getFormatSortDirection()
	 */
	public int getFormatSortDirection() {
		return getPreferenceStore().getInt(FORMAT_SORT_DIR);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setFormatSortColumn(int)
	 */
	public void setFormatSortColumn(int value) {
		getPreferenceStore().setValue(FORMAT_SORT_COLUMN, value);
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.model.AAAPreferences#setFormatSortDirection(int)
	 */
	public void setFormatSortDirection(int value) {
		getPreferenceStore().setValue(FORMAT_SORT_DIR, value);		
	}




}
