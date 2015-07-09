package com.odcgroup.page.ui.request;

/**
 * Request Constants specific to the Page Designer.
 * 
 * @author Gary Hayes
 */
public interface PageUIRequestConstants {

	/** The Request to include a Widget. */
	public static final String REQ_INCLUDE ="include";
	
	/** The Request for a multiple create. */
	public static final String REQ_MULTIPLE_CREATE ="multipleCreate";
	
	/** The Request to modify a Widget. */
	public static final String REQ_MODIFY ="modify";	

	/** The Request domain widgets in Grid by dropping domain element in a GridCell. */
	public static final String REQ_MULTIPLE_CREATE_IN_GRID ="multipleCreateInGrid";

	/** The Request domain widgets in Table by dropping domain element in a Table. */
	public static final String REQ_MULTIPLE_CREATE_IN_TABLE ="multipleCreateInTable";

	/** The Request domain widgets in TableColumn by dropping domain element in a TableColumn. */
	public static final String REQ_MULTIPLE_CREATE_IN_TABLE_COLUMN ="multipleCreateInTableColumn";

	/** The Request domain widgets in MatrixContentCell by dropping domain element in a MatrixContentCell. */
	public static final String REQ_MULTIPLE_CREATE_IN_MATRIXCELL ="multipleCreateInMatrixCell";

	/** The Request domain widgets in MatrixContentCell by dropping domain element in a Box widget of MatrixContentCell. */
	public static final String REQ_MULTIPLE_CREATE_IN_MATRIXCELLBOX ="multipleCreateInMatrixCellBox";

	/** The Request domain widgets in MatrixAxis by dropping domain element in a MatrixAxis */
	public static final String REQ_MULTIPLE_CREATE_IN_MATRIXAXIS = "multipleCreateInMatrixAxis";
	
	/** The Request domain widgets in MatrixExtraColumn by dropping domain element in a MatrixExtraColumn */
	public static final String REQ_MULTIPLE_CREATE_IN_MATRIXEXTRA = "multipleCreateInMatrixExtraColumn";

}
