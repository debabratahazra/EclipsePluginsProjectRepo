package com.odcgroup.page.model.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.snippets.impl.ParameterSnippet;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

public class SearchField extends WidgetAdapter implements ISearchField {
	
	private static final String SEARCHTYPE_PROPERTY = "searchType";
	private static final String NUMCHARS_PROPERTY = "nb-chars";
	private static final String AUTODELAY_PROPERTY = "auto-delay";
	private static final String OUTPUTDESIGN_PROPERTY = "outputDesign";
	
	private static final String SEARCHTYPE_SEARCHONLY = "searchOnly";
	private static final String SEARCHTYPE_AUTOONLY = "autoCompleteOnly";
	private static final String SEARCHTYPE_AUTOANDSEARCH = "autoCompleteAndSearch";
	private static final String WIDGETGROUPREF_PROPERTY = "widgetGroup";

	/**
	 * @param widget
	 */
	public SearchField(Widget widget) {
		super(widget);
	}

	@Override
	public Model getOutputDesign() {
		Widget widget = getWidget();
		EcoreUtil.resolveAll(widget);
    	Property output = widget.findProperty(OUTPUTDESIGN_PROPERTY);
    	if (output != null) {
          	Model model = output.getModel();
          	return model;
    	}
    	return null;
	}

	@Override
	public String getWidgetGroup() {
		return getWidget().getPropertyValue(WIDGETGROUPREF_PROPERTY);
	}

	@Override
	public int getDelay() {
		String val = getWidget().getPropertyValue(AUTODELAY_PROPERTY);
		int ii  = 0;
		if (!StringUtils.isEmpty(val)) {
			try {
				ii = Integer.valueOf(val).intValue();
			} catch (Exception e) {
				return ii;
			}
		}
		return ii;
	}

	@Override
	public int getNumberOfCharacters() {
		String val = getWidget().getPropertyValue(NUMCHARS_PROPERTY);
		int ii  = 0;
		if (!StringUtils.isEmpty(val)) {
			try {
				ii = Integer.valueOf(val).intValue();
			} catch (Exception e) {
				return ii;
			}
		}
		return ii;
	}

	@Override
	public List<IParameterSnippet> getParameters() {
		List<Snippet> snippets = getWidget().getSnippets();
		List<IParameterSnippet> parameters = new ArrayList<IParameterSnippet>();
		for (Snippet snippet : snippets) {
			if (snippet.getTypeName().equals("Parameter")) {
				parameters.add(new ParameterSnippet(snippet));
			}
		}
		return parameters;
	}


	@Override
	public String getDomainAttribute() {
		return getWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
	}


	@Override
	public String getSearchType() {
		return getWidget().getPropertyValue(SEARCHTYPE_PROPERTY);
	}

	@Override
	public URI getOutputDesignURI() {
		Widget widget = getWidget();
		EcoreUtil.resolveAll(widget);
    	Property output = widget.findProperty(OUTPUTDESIGN_PROPERTY);
    	if (output != null) {
    		return output.getModelURI();
    	}
		return null;
	}

	@Override
	public void addParameter(IParameterSnippet parameter) {
		if (parameter != null) {
			getWidget().getSnippets().add(parameter.getSnippet());
		}		
	}

	@Override
	public void removeParameter(IParameterSnippet parameter) {
		if (parameter != null) {
			Snippet snip = parameter.getSnippet();
			if(getWidget().getSnippets().contains(snip)) {
				getWidget().getSnippets().remove(snip);
			}
		}		
	}

	@Override
	public boolean isAutoCompleteOnly() {
		return SEARCHTYPE_AUTOONLY.equals(getSearchType());
	}

	@Override
	public boolean isAutoCompleteAndSearch() {
		return SEARCHTYPE_AUTOANDSEARCH.equals(getSearchType());
	}

	@Override
	public boolean isSearchOnly() {
		return SEARCHTYPE_SEARCHONLY.equals(getSearchType());
	}
	
}
