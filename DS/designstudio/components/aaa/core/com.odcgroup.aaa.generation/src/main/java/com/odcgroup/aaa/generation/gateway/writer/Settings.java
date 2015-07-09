package com.odcgroup.aaa.generation.gateway.writer;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Settings {
	
	private String newlineMarker = "\n";
	
	private String nullValue = "NULL";
	private char datSeparator = ';';
	
	private char quote = 0;
	
	private char decimal = '.';
	private char thousand = 0;
	private DecimalFormat decimalFormat = new DecimalFormat("0.0##############################");
	
	// Prefix with DAT
	private  boolean prefixData = true;
	
	private Formatter formatter;
	
	private String aaaDateFormat = "DD-MM-YYYY";
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private String dataFormat = "DELIMITED";
	
	public Settings() {
		formatter = new Formatter(this);
	}
	
	public Settings(Formatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * @return the newlineMarker
	 */
	public String getNewlineMarker() {
		return newlineMarker;
	}

	/**
	 * @param newlineMarker the newlineMarker to set
	 */
	public void setNewlineMarker(String newlineMarker) {
		this.newlineMarker = newlineMarker;
	}

	/**
	 * @return the nullValue
	 */
	public String getNullValue() {
		return nullValue;
	}

	/**
	 * @param nullValue the nullValue to set
	 */
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	/**
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * @return the dateFormat
	 */
	public String getAAADateFormat() {
		return aaaDateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setAAADateFormat(String aaaDateFormat) {
		// Needs logic to create SimpleDateFormat according to aaa date format
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	/**
	 * @return the formatter
	 */
	public Formatter getFormatter() {
		return formatter;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * @return the dATSeparator
	 */
	public char getDATSeparator() {
		return datSeparator;
	}

	/**
	 * @param dATSeparator the dATSeparator to set
	 */
	public void setDATSeparator(char datSeparator) {
		this.datSeparator = datSeparator;
	}

	/**
	 * @return the quote
	 */
	public char getQuote() {
		return quote;
	}

	/**
	 * @param quote the quote to set
	 */
	public void setQuote(char quote) {
		this.quote = quote;
	}

	/**
	 * @return the prefixData
	 */
	public boolean isPrefixData() {
		return prefixData;
	}

	/**
	 * @param prefixData the prefixData to set
	 */
	public void setPrefixDataNone(boolean prefixData) {
		this.prefixData = prefixData;
	}

	/**
	 * @return the decimal
	 */
	public char getDecimal() {
		return decimal;
	}

	/**
	 * @param decimal the decimal to set
	 */
	public void setDecimal(char decimal) {
		// Needs logic to create DecimalFormat accordingly
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	/**
	 * @return the thousand
	 */
	public char getThousand() {
		return thousand;
	}

	/**
	 * @param thousand the thousand to set
	 */
	public void setThousand(char thousand) {
		// Needs logic to create DecimalFormat accordingly
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public NumberFormat getDecimalFormat() {
		return decimalFormat;
	}

	public String getDataFormat() {
		return dataFormat;
	}

}
