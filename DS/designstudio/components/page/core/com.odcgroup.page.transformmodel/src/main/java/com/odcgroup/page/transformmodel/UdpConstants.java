package com.odcgroup.page.transformmodel;

/**
 * Constants used for UDP transformations.
 * 
 * @author Gary Hayes
 */
public interface UdpConstants {

	/** The namespace uri for UDP. */
	public String UDP_NAMESPACE_URI = "http://www.odcgroup.com/uif/udp/0.1";
	
	/** The root element for UDP. */
	public String UDP_ROOT_ELEMENT = "udp";
	
	/** Table model construction. */
	public String UDP_BUILD_ELEMENT = "build";
	
	/** The udp render list tag. */
	public String UDP_RENDER_LIST_ELEMENT = "render-list";
	
	/** The udp recurse data tag. */
	public String UDP_RECURSE_DATA_ELEMENT = "recurse-data";	
	
	/** The udp munge columns tag. */
	public String UDP_MUNGE_COLUMNS_ELEMENT = "munge-columns";
	
	/** The upd page tag.*/
	public String UDP_PAGE = "page";
	
	/** The udp size tag.*/
	public String UDP_SIZE = "size";
	
	/** The udp keep tag.*/
	public String UDP_KEEP_ELEMENT = "keep";
	
	/** The udp list tag.*/
	public String UDP_LIST_ELEMENT = "list";
	
	/** The udp group tag.*/
	public String UDP_GROUP_ELEMENT = "group";
	
	/** The udp group-column tag.*/
	public String UDP_GROUP_COLUMN_ELEMENT = "group-column";	
	
	/** Defines the column headers. */
	public String UDP_FOR_EACH_COLUMN_ELEMENT = "for-each-column";
	
	/** Defines the row body. */
	public String UDP_FOR_EACH_ROW_ELEMENT = "for-each-row";
	
	/** Defines the filter tag.*/
	public String UDP_FILTER = "filter";
	
	/** Defines the sort tag.*/
	public String UDP_SORT = "sort";
	
	/** Defines the sort-column tag.*/
	public String UDP_SORT_COLUMN = "sort-column";
	
	/** Defines the as-type attribute.*/
	public String UDP_AS_TYPE = "as-type";
	
	/** Defines the as-type attribute.*/
	public String UDP_TYPE = "type";	
	
	/** Defines the format attribute.*/
	public String UDP_FORMAT = "format";
	
	/** Defines the computation attribute.*/
	public String UDP_COMPUTATION = "computation";	
	
	/** Defines the handle tag.*/
	public String UDP_HANDLE = "handle";
	
	/** Defines the on row body. */
	public String UDP_ON_ROW_ELEMENT = "on-row";	
	
	/** Defines each item in a row. */
	public String UDP_FOR_EACH_ITEM_ELEMENT = "for-each-item";	
		
	/** Defines the udp colum name.*/
	public String UDP_COLUMN_NAME_ELEMENT = "column-name";
	
	/** Defines the udp item tag. */
	public String UDP_ITEM_ELEMENT = "item";
	
	/** Defines the udp keep-filter tag. */
	public String UDP_KEEP_FILTER = "keep-filter";
	
	/** Defines the udp compute tag. */
	public String UDP_COMPUTE_ELEMENT = "compute";
	
	/** Defines the udp name tag. */
	public String UDP_NAME = "name";	
	
	/** The attribute model-ref of the udp tag.*/
	public String UDP_MODEL_REF = "model-ref";
	
	/** The attribute reverse of the udp-sort-columnt tag.*/
	public String UDP_REVERSE = "reverse";
	
	/** The attribute sticky of the udp tag.*/
	public String UDP_STICKY = "sticky";
	
	/** The attribute view reference of the udp tag.*/
	public  String UDP_VIEW_REF = "view-ref";	

	/** The attribute from index of the for-each-column tag.*/
	public String UDP_FROM_INDEX = "from-index";

	/** The attribute column of the udp:item. */
	public String UDP_ITEM_COLUMN = "column";
	
	/** The element dump-model. */
	public String UDP_DUMP_MODEL = "dump-model";	
	
	/** The element aggregation. */
	public String UDP_AGGREGATION = "aggregation";
	
	/** The element aggregate. */
	public String UDP_AGGREGATE = "aggregate";
	
	/** The attribute aggregated-column of the aggregate tag. */
	public String UDP_AGGREGATED_COLUMN = "aggregated-column";
	
	/** The attribute aggregated-compution of the aggregate tag. */
	public String UDP_AGGREGATED_COMPUTATION = "aggregation-computation";
	
	/** The attribute aggregated-extra-column of the aggregate tag. */
	public String UDP_AGGREGATED_EXTRA_COLUMN = "aggregated-extra-column";
	
	/** The element group-x. */
	public String UDP_GROUP_X = "group-x";
	
	/** The element group-y. */
	public String UDP_GROUP_Y = "group-y";
	
	/** The element group-column .*/
	public String UDP_GROUP_COLUMN = "group-column";
	
	/** The element show-in-cell. */
	public String UDP_SHOW_IN_CELL = "show-in-cell";
	
	/** The element column. */
	public String UDP_COLUMN = "column";
	
	/** The element matrix. */
	//public String UDP_MATRIX = "matrix";
	
	/** The element render-matrix. */
	//public String UDP_RENDER_MATRIX = "render-matrix";
	
	/** The element for-each-column-header. */
	public String UDP_FOR_EACH_COLUMN_HEADER = "for-each-column-header";
	
	/** The element row. */
	public String UDP_ROW = "row";	
	
	/** The element row-is-selected. */
	public String UDP_ROW_IS_SELECTED = "row-is-selected";

	/** The element row-is-enabled. */
	public String UDP_ROW_IS_ENABLED = "row-is-enabled";
	
	/** The element for-each-cell. */
	public String UDP_FOR_EACH_CELL = "for-each-cell";
	
	/** The element for-each-aggregate. */
	public String UDP_FOR_EACH_AGGREGATE = "for-each-aggregate";
	
	/** The element for-each-column-footer.*/
	public String UDP_FOR_EACH_COLUMN_FOOTER = "for-each-column-footer";
	
	/** The element aggregated-cell. */
	public String UDP_AGGREGATED_CELL = "aggregated-cell";
	
	/** The element standard-chart. */
	public String UDP_STANDARD_CHART = "standard-chart";
	
	/** The element base-url. */
	public String UDP_BASE_URL = "base-url";
	
	/** The element link-column. */
	public String UDP_LINK_COLUMN = "link-column";
	
	/** The element label-column. */
	public String UDP_LABEL_COLUMN = "label-column";
	
	/** The element data-column. */
	public String UDP_DATA_COLUMN = "data-column";
	
	/** The element date-column. */
	public String UDP_DATE_COLUMN = "date-column";
	
	/** The element render-chart. */
	public String UDP_RENDER_CHART = "render-chart";
	
	/** The attribute appearance of the render-chart element. */
	public String UDP_RENDER_CHART_APPEARANCE = "appearance";
	
	/** The element stacked-bar-chart. */
	public String UDP_STACKED_BAR_CHART = "stacked-bar-chart";
	
	/** The element series-column. */
	public String UDP_SERIES_COLUMN = "series-column";
	
	/** The element sort-series-column. */
	public String UDP_SORT_SERIES_COLUMN = "sort-series-column";
	
	/** The element time-chart. */
	public String UDP_TIME_CHART = "time-chart";
	
	/** The element first-date. */
	public String UDP_FIRST_DATE = "first-date";
	
	/** The element pattern. */
	public String UDP_PATTERN = "pattern";
	
	public String UDP_UNFORMATTED_ITEM = "unformatted-item";
	
}
