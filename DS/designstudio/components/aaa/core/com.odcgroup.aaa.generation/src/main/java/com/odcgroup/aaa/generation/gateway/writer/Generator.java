package com.odcgroup.aaa.generation.gateway.writer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.aaa.generation.gateway.line.ATT;
import com.odcgroup.aaa.generation.gateway.line.CMD;
import com.odcgroup.aaa.generation.gateway.line.DAT;
import com.odcgroup.aaa.generation.gateway.line.value.DATValue;
import com.odcgroup.aaa.generation.gateway.validator.GatewayValidationException;
import com.odcgroup.aaa.generation.gateway.validator.GatewayValidator;


public class Generator {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
	private Settings settings;
	private String[] onlyIncludeHeadersSettings;

	public Generator() {
		settings = new Settings();
	}

	public Generator(Settings settings) {
		this.settings = settings;
	}
	
	public void setOnlyIncludeHeadersSettings(String... includeHeadersSettings) {
		this.onlyIncludeHeadersSettings = includeHeadersSettings;
	}
	
	public String generateCommands(List<CMD> cmds, String projectName, String dsVersion) throws GatewayValidationException {
		return generateCommands(cmds, projectName, dsVersion, new Date());
	}
	
	public String generateCommands(List<CMD> cmds, String projectName, String dsVersion, Date generationTime) throws GatewayValidationException {
		new GatewayValidator(settings).validate(cmds);
				
		StringBuilder result = new StringBuilder();
		generateRemHeader(result, projectName, dsVersion, generationTime);
		generateSettingsHeader(result);
		for (CMD cmd:cmds) {
			generateCMD(cmd, result);
		}
		return result.toString();
	}
	
	private void generateRemHeader(StringBuilder result, String projectName, String dsVersion, Date generationTime) {
		result.append("REM Importation of user-defined tables.");
		result.append(settings.getNewlineMarker());
		result.append("REM This file has been extracted by Design Studio from project: " + projectName);
		result.append(settings.getNewlineMarker());
		result.append("REM Design Studio Build: " + dsVersion);
		result.append(settings.getNewlineMarker());
		result.append("REM Extraction date: ");
		result.append(DATE_FORMAT.format(generationTime));
		result.append(settings.getNewlineMarker());
		result.append(settings.getNewlineMarker());
	}

	protected void generateSettingsHeader(StringBuilder result) {
		generateSettingHeader("SEPARATOR", settings.getDATSeparator(), result);
		generateSettingHeader("QUOTE", settings.getQuote(), result);
		generateSettingHeader("DECIMAL", settings.getDecimal(), result);
		generateSettingHeader("THOUSAND", settings.getThousand(), result);
		generateSettingHeader("DATAFORMAT", settings.getDataFormat(), result);
		generateSettingHeader("DATEFORMAT", settings.getAAADateFormat(), result);
		if (!settings.isPrefixData()) {
			generateSettingHeader("PREFIX", "NONE", result);
		}
		result.append(settings.getNewlineMarker());
	}
	
	protected void generateSettingHeader(String property, String value, StringBuilder result) {
		result.append("SET ");
		result.append(property);
		result.append(" ");
		result.append(value);
		result.append(settings.getNewlineMarker());
	}

	protected void generateSettingHeader(String property, char value, StringBuilder result) {
		if (onlyIncludeHeadersSettings!=null && 
				!ArrayUtils.contains(onlyIncludeHeadersSettings, property)) {
			return; // If include headers settings defined, only include properties listed in here
		}

		if (value == 0) {
			generateSettingHeader(property, "NONE", result);
		} else if (value == ' ') {
			generateSettingHeader(property, "SPACE", result);
		} else {
			generateSettingHeader(property, ""+value, result);
		}
	}
	
	protected void generateCMD(CMD cmd, StringBuilder result) {
		generateREM(cmd.getComment(), result);		
		result.append("CMD ");
		result.append(cmd.getType());
		result.append(" ");
		result.append(cmd.getEntityName());
		if (cmd.getObjectEntityName() != null) {
			result.append(" ");
			result.append(cmd.getObjectEntityName());
		}
		result.append(settings.getNewlineMarker());
		
		generateATT(cmd.getATT(), result);

		result.append(settings.getNewlineMarker());
	}
	
	private void generateREM(String comment, StringBuilder result) {
		if (StringUtils.isNotEmpty(comment)) {
			result.append("REM "+comment);
			result.append(settings.getNewlineMarker());
		}
	}

	protected void generateATT(ATT att, StringBuilder result) {
		result.append("ATT ");
		for (String columnName : att.getColumnNames()) {
			result.append(columnName);
			result.append(" ");
		}
		removeEnd(result, ' ');
		result.append(settings.getNewlineMarker());
		for (DAT dat : att.getDATs()) {
			generateDAT(dat, result);
		}
	}

	protected void generateDAT(DAT dat, StringBuilder result) {
		if (settings.isPrefixData()) {
			result.append("DAT ");
		}
		for (DATValue value : dat.getData()) {
			if (value != null) {
				result.append(value.format(settings.getFormatter()));
			} else {
				result.append(settings.getNullValue());
			}
			result.append(settings.getDATSeparator());
		}
		removeEnd(result, settings.getDATSeparator());
		result.append(settings.getNewlineMarker());
	}

	/**
	 * @param result
	 */
	private void removeEnd(StringBuilder result, char c) {
		if (result.charAt(result.length()-1) == c) {
			result.deleteCharAt(result.length()-1);
		}
	}

}
