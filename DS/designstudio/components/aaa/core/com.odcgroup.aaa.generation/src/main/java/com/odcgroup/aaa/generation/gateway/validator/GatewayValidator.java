package com.odcgroup.aaa.generation.gateway.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.aaa.generation.gateway.line.ATT;
import com.odcgroup.aaa.generation.gateway.line.CMD;
import com.odcgroup.aaa.generation.gateway.line.DAT;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;
import com.odcgroup.aaa.generation.gateway.writer.Settings;


public class GatewayValidator {
	
	public static final String NO_ATT_IN_CMD = "No ATT in CMD";
	public static final String DATA_CONTAINS_SEPARATOR = "Data contains the separator.";
	public static final String DATA_CONTAINS_QUOTE = "Data contains the quote.";
	public static final String WRONG_NUMBER_OF_COLUMNS = "Wrong number of columns.";
	public static final String NO_DATA_DEFINED_IN_DAT = "No data defined in DAT.";
	public static final String NO_COLUMN_DEFINED_IN_ATT = "No column defined in ATT.";
	public static final String CMD_TABLE_NAME_UNDEFINED = "CMD table name undefined";
	public static final String CMD_TYPE_UNDEFINED = "CMD type undefined";
	
	private Settings settings;
	
	public GatewayValidator(Settings settings) {
		this.settings = settings;
	}

	public void validate(List<CMD> cmds) throws GatewayMultiValidationException {
		LinkedList<GatewayValidationException> exceptions = new LinkedList<GatewayValidationException>();
		validateInternal(cmds, exceptions);
		if (exceptions.size() > 0) {
			throw new GatewayMultiValidationException(exceptions.size() + " validation error(s) found.", exceptions);
		}
	}
	
	public void validate(CMD cmd) throws GatewayMultiValidationException {
		LinkedList<GatewayValidationException> exceptions = new LinkedList<GatewayValidationException>();
		validateInternal(cmd, exceptions);
		if (exceptions.size() > 0) {
			throw new GatewayMultiValidationException(exceptions.size() + " validation error(s) found.", exceptions);
		}
	}
	

	public void validate(ATT att) throws GatewayMultiValidationException {
		LinkedList<GatewayValidationException> exceptions = new LinkedList<GatewayValidationException>();
		validateInternal(att, exceptions);
		if (exceptions.size() > 0) {
			throw new GatewayMultiValidationException(exceptions.size() + " validation error(s) found.", exceptions);
		}
	}
	
	public void validate(DAT dat) throws GatewayMultiValidationException {
		LinkedList<GatewayValidationException> exceptions = new LinkedList<GatewayValidationException>();
		validateInternal(dat, exceptions);
		if (exceptions.size() > 0) {
			throw new GatewayMultiValidationException(exceptions.size() + " validation error(s) found.", exceptions);
		}
	}
	
	protected void validateInternal(List<CMD> cmds, List<GatewayValidationException> exceptions) {
		for (CMD cmd:cmds) {
			validateInternal(cmd, exceptions);
		}
	}

	protected void validateInternal(CMD cmd, List<GatewayValidationException> exceptions) {
		if (cmd.getType() == null) {
			exceptions.add(new GatewayValidationException(CMD_TYPE_UNDEFINED));
		}
		if (cmd.getEntityName() == null) {
			exceptions.add(new GatewayValidationException(CMD_TABLE_NAME_UNDEFINED));
		}
		if (cmd.getATT() == null) {
			exceptions.add(new GatewayValidationException(NO_ATT_IN_CMD));
		} else {
			validateInternal(cmd.getATT(), exceptions);
		}
	}

	protected void validateInternal(ATT att, List<GatewayValidationException> exceptions) {
		int nbColumns = att.getColumnNames().size();
		if (nbColumns == 0) {
			exceptions.add(new GatewayValidationException(NO_COLUMN_DEFINED_IN_ATT));
		}
		for (DAT dat:att.getDATs()) {
			validateInternal(dat, exceptions);
			if (dat.getData().size() != nbColumns) {
				exceptions.add(new GatewayValidationException(WRONG_NUMBER_OF_COLUMNS + "\n" +
						"Expected: " + nbColumns + ", actual: " + dat.getData().size() + " in \n" + 
						dat + "\n" +
						"for att\n" + 
						att + "\n"));
			}
		}
	}

	protected void validateInternal(DAT dat, List<GatewayValidationException> exceptions) {
		if (dat.getData().isEmpty()) {
			exceptions.add(new GatewayValidationException(NO_DATA_DEFINED_IN_DAT));
		}
		for (DATValue data:dat.getData()) {
			if (data!=null) {
				String formattedData = data.format(settings.getFormatter());
				if (settings.getQuote() == 0) {
					if (formattedData.contains("" + settings.getDATSeparator())) {
						exceptions.add(new GatewayValidationException(DATA_CONTAINS_SEPARATOR + "\n" +
								"separator:" + settings.getDATSeparator() +
								"data:" + formattedData));
					}
				} else {
					String formattedDataWithoutQuotes = StringUtils.removeEnd(StringUtils.removeStart(formattedData, "" + settings.getQuote()), "" + settings.getQuote());
					if (formattedDataWithoutQuotes.contains("" + settings.getQuote())) {
						exceptions.add(new GatewayValidationException(DATA_CONTAINS_QUOTE + "\n" +
								"quote:" + settings.getQuote() +
								"data:" + formattedData));
					}
				}
			}
		}
	}
}
