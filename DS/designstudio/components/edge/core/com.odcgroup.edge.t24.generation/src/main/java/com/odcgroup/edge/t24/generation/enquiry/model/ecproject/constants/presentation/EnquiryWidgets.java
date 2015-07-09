package com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation;

import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.GenericActionWidget;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.widget.SearchResultsTableWidget;

/**
 * <code>EnquiryWidgets</code> is simply an easily remembered root for finding the singleton-instances of our various {@link Appliable} widget helper classes. 
 *
 * @author Simon Hayes
 */
public interface EnquiryWidgets
{
	GenericActionWidget GENERIC_ACTION = GenericActionWidget.INSTANCE;
	
	EnquiryResultFieldImageWidget RESULTS_FIELD_IMAGE = EnquiryResultFieldImageWidget.INSTANCE;
	
	SearchResultsTableWidget SEARCH_RESULT_TABLE = SearchResultsTableWidget.INSTANCE;
	
}
