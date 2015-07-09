package com.odcgroup.aaa.ui.internal.model;

/**
 * @author atr
 * @since DS 1.40.0
 */
public interface AAAPreferences {
	
	/**
	 * @return
	 */
	String getSQLServer();
	
	/**
	 * @param value
	 */
	void setSQLServer(String value);
	
	/**
	 * @return
	 */
	String getDatabase();

	/**
	 * @param value
	 */
	void setDatabase(String value);

	/**
	 * @return
	 */
	String getPortNumber();

	/**
	 * @param value
	 */
	void setPortNumber(String value);
	
	/**
	 * @return
	 */
	String getCharset();
	
	/**
	 * @param value
	 */
	void setCharset(String value);

	/**
	 * @return
	 */
	String getUsername();

	/**
	 * @param value
	 */
	void setUsername(String value);

	/**
	 * @return
	 */
	String getPassword();
	
	/**
	 * @return
	 */
	String getFormatCode();
	
	/**
	 * @param value
	 */
	void setFormatCode(String value);	
	
	/**
	 * @return
	 */
	String getFormatFunction();
	
	/**
	 * @param value
	 */
	void setFormatFunction(String value);
	
	/**
	 * @return
	 */
	String getFormatType();
	
	/**
	 * @param value
	 */
	void setFormatType(String value);
	
	/**
	 * @return
	 */
	int getFormatSortColumn();
	
	/**
	 * @param value
	 */
	void setFormatSortColumn(int value);
	
	/**
	 * @return
	 */
	int getFormatSortDirection();
	
	/**
	 * @param value
	 */
	void setFormatSortDirection(int value);
	
	/**
	 * @param connectionInfo
	 */
	void save(ConnectionInfo connectionInfo);
	
	/**
	 * @param formatSelectionInfo
	 */
	void save(FormatSelectionInfo formatSelectionInfo);


}
