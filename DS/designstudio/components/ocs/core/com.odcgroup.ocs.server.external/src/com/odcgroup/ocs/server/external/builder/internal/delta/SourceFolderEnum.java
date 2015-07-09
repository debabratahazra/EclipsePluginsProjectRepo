package com.odcgroup.ocs.server.external.builder.internal.delta;


/**
 * Hold the different source folder
 * @author yan
 */
public enum SourceFolderEnum {
	API_DOMAIN("src/api/domain", ServerLocationConstants.UTILITY_JAR_LOCATION, "-lib"),
	IMPL_DOMAIN("src/impl/domain", ServerLocationConstants.UTILITY_JAR_LOCATION, "-lib"),
	IMPL_DOMAIN_SPECIFIC("src/impl/domain-specific", ServerLocationConstants.UTILITY_JAR_LOCATION, "-lib"),
	EJB_DOMAIN("src/ejb/domain", ServerLocationConstants.EJB_JAR_LOCATION, "-ejb"),
	API_RULES("src/api/rules", ServerLocationConstants.UTILITY_JAR_LOCATION, "-lib"),
	IMPL_RULES("src/impl/rules", ServerLocationConstants.UTILITY_JAR_LOCATION, "-lib"),
	WUI_BLOCK("src/wui_block", ServerLocationConstants.WUI_BLOCK_LOCATION, "-wuiblock"),
	WUI_BLOCK_IMPORT("src/import", ServerLocationConstants.IMPORT_LOCATION),
	WUI_BLOCK_SPECIFIC("src/wui_block-specific", ServerLocationConstants.WUI_BLOCK_LOCATION, "-wuiblock"),
	CONFIG_SPECIFIC("src/config-specific", ServerLocationConstants.CONFIG_LOCATION);

	private String workspaceFolder;
	private String defaultServerFolder;
	private String genSuffix = "";

	/**
	 * Private constructor
	 */
	private SourceFolderEnum(String workspaceFolder, String defaultServerFolder, String genSuffix) {
		this.workspaceFolder = workspaceFolder;
		this.defaultServerFolder = defaultServerFolder;
		this.genSuffix = genSuffix;
	}

	/**
	 * Private constructor
	 */
	private SourceFolderEnum(String workspaceFolder, String defaultServerFolder) {
		this(workspaceFolder, defaultServerFolder, "");
	}

	/**
	 * @return the workspace folder associated with the source folder
	 */
	public String getWorkspaceFolder() {
		return workspaceFolder;
	}

	/**
	 * @return the default server folder
	 */
	public String getDefaultServerFolder() {
		return defaultServerFolder;
	}

	public String getGenSuffix() {
		return genSuffix;
	}

	/**
	 * @return return the key to be used to configure the server path
	 */
	public String getServerFolderConfigKey() {
		return this.name().toLowerCase().replace('_', '.') + ".server.folder";
	}

}
