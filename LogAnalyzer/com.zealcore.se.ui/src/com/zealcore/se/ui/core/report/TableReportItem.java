package com.zealcore.se.ui.core.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete class for AbstractReportItems representing tabular data
 * <p>
 * ReportItems are part of the report framework and used by report contributors
 * to fill a report
 * <p>
 * 
 * @see AbstractReportItem
 * @see IReportContributor
 * 
 * @author daze
 * 
 */
public class TableReportItem extends AbstractReportItem {

    private static final String E_S_COLSIZE_MISMATCH = "Unable to add row. The table contains headers with different column size!";

    /**
     * Represents a row in the table
     * <p>
     * Each row has a list of {@link String} values, one for each column
     * 
     */
    public static final class TableRow {
        private final List<String> columnValues = new ArrayList<String>();

        /**
         * Adds a column value to this row, values are added from left to right
         * 
         * @param value
         *                The {@link String} value to add
         */
        public void addValue(final String value) {
            this.columnValues.add(value);
        }

        /**
         * Returns an unmodifiable list collection of column values.
         * <p>
         * The order of columns are from left to right
         * 
         * @return The unmodifiable list collection of column values
         */
        public List<String> getValues() {
            return Collections.unmodifiableList(this.columnValues);
        }
    }

    private final TableRow header;

    private final List<TableRow> rows = new ArrayList<TableRow>();

    /**
     * Creates a new {@link TableReportItem} and sets the header to be used for
     * this table
     * 
     * @param header
     *                The header for this table
     */
    public TableReportItem(final TableRow header) {
        this.header = header;
    }

    /**
     * Adds a row to this table. The row must have as many columns as the header
     * for this table.
     * 
     * @param row
     *                The row to be added to this table
     */
    public void addRow(final TableRow row) {
        if (this.header.columnValues.size() != row.getValues().size()) {
            throw new IllegalArgumentException(
                    TableReportItem.E_S_COLSIZE_MISMATCH);
        }
        this.rows.add(row);
    }

    /**
     * Gets the rows for this table
     * 
     * @return The rows for this table
     * 
     */
    public List<TableRow> getRows() {
        return Collections.unmodifiableList(this.rows);
    }

    /**
     * Gets the header for this table
     * 
     * @return The header for this table
     */
    public TableRow getHeader() {
        return this.header;
    }
}
