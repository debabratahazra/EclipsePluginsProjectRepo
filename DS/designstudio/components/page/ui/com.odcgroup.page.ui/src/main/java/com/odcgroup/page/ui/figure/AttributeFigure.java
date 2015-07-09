package com.odcgroup.page.ui.figure;

import org.eclipse.draw2d.Graphics;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.edit.PaintUtils;

/**
 * The figure used to present business attributes.
 * 
 * @author Gary Hayes
 */
public class AttributeFigure extends BoxFigure {

	/** The label of this figure. */
	private Label label;

	/** The Field of this figure. */
	private IWidgetFigure field;
	
	/** The Widget associated with the field. This Widget is NOT part of the real model. */
	private Widget labelWidget;	
	
	/** The Widget associated with the field. This Widget is NOT part of the real model. */
	private Widget fieldWidget;
	
	/** The text to be displayed by the label */
	private String text;
	
	/**
	 * Code who must be executed after settings the properties.
	 * 
	 * @param name
	 *            The name of the property
	 */
	public void afterPropertyChange(String name) {
		if (name.equals(PropertyTypeConstants.FIELD_TYPE)) {
			removeFigure(field);
			createField();
		}
		
		updateWidgetProperty(labelWidget, name);
		updateWidgetProperty(fieldWidget, name);
		
		updateLabel();
		updateField();
		
		updateFromCorporateDesign();
		
		// Since we have manually created these figure they do not have an associated
		// edit part and are thus not notified of changes to properties
		label.afterPropertyChange(name);
		field.afterPropertyChange(name);
	}
	
	/**
	 * Adds the figure to the contents of the AttributeFigure.
	 * The figure is only added if it not already part of the figure.
	 * 
	 * @param figure
	 *            The figure to add
	 */
	private void addFigure(IWidgetFigure figure) {
		if (getChildren().indexOf(figure) < 0) {
			add(figure);
		}
	}	
	
	/**
	 * Tries to remove the figure from the contents of the Attribute Widget.
	 * 
	 * @param figure
	 *            The figure to remove
	 */
	private void removeFigure(IWidgetFigure figure) {
		if (getChildren().indexOf(figure) >= 0) {
			remove(figure);
		}
	}
	
	/**
	 * Updates the property of the widget with that from the AttributeWidget.
	 * 
	 * @param widget
	 *            The widget to update
	 * @param name
	 *            The name of the property to update
	 */
	private void updateWidgetProperty(Widget widget, String name) {
		Property p = widget.findProperty(name);
		if (p != null) {
			p.setValue(getWidget().getPropertyValue(name));
		}
	}

	
	/**
	 * Creates the Label.
	 */
	private void createLabel() {
		labelWidget = createWidget(WidgetTypeConstants.LABEL);
		label = new Label(labelWidget, getFigureContext()) {
			protected boolean isBoundToDomainAttribute() {
				return true;
			}
		};
		WidgetUtils.updateWidgetFromAttribute(getWidget(), labelWidget);
	}
	
	/**
	 * Creates the correct type of Field according to the chosen properties.
	 */
	private void createField() {
		String type = getString(PropertyTypeConstants.FIELD_TYPE);
		fieldWidget = createWidget(type);
		if (type.equals(WidgetTypeConstants.CALDATE_FIELD)) {
			field = new CaldateField(fieldWidget, getFigureContext());
		} else if (type.equals(WidgetTypeConstants.COMBOBOX)) {
			field = new ComboBox(fieldWidget, getFigureContext());
			
			// OCS-26823 - 15/Jul/2008 - Resizing of drop down boxes 
			Property p = fieldWidget.findProperty(PropertyTypeConstants.WIDTH);
			if (p != null) {
				// default value for combo is 0, not 20
				String columns = getString(PropertyTypeConstants.COLUMNS);
				if (columns.equals("20")) {
					getWidget().setPropertyValue(PropertyTypeConstants.COLUMNS, "0");
				} else {
					p.setValue(columns);
				}
			}
			// OCS-26823 - end
			
		} else if (type.equals(WidgetTypeConstants.TEXTAREA)) {
			field = new TextArea(fieldWidget, getFigureContext());
		} else if (type.equals(WidgetTypeConstants.CHECKBOX)) {
			field = new CheckBox(fieldWidget, getFigureContext());
		} else {
			// Default value "Text". We need to set the default value
			// to the same as that used in the Template
			field = new TextField(fieldWidget, getFigureContext());	
		}
		
		WidgetUtils.updateWidgetFromAttribute(getWidget(), fieldWidget);
	}
	
	/**
	 * Creates a new empty widget of the given type
	 * 
	 * @param type
	 *            The widget type
	 * @return Widget The new widget
	 */
	private Widget createWidget(String type) {
		WidgetType wt = getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI, type);
		return new WidgetFactory().create(wt);
	}
	
	@Override
	protected void paintSpecificFigure(Graphics graphics) {
	    Widget widget = getWidget();
	    if(PaintUtils.isWidgethAlignLead(getWidget())){
		PaintUtils.paintIcons(getWidget(), graphics,getMinWidth() - PaintUtils.getWidth(getWidget()));
	    } else {
		PaintUtils.paintIcons(getWidget(), graphics,0);
	    }
	    super.paintSpecificFigure(graphics);
	}

	/**
	 * Updates the label.
	 */
	private void updateLabel() {

		String sl = getString(PropertyTypeConstants.SHOW_LABEL);
		
		if (PropertyTypeConstants.SHOW_LABEL_SHOW.equals(sl)) {
			label.setVisible(true);
			label.setText(this.text);
		} else if (PropertyTypeConstants.SHOW_LABEL_LABEL_ONLY.equals(sl)) {
			// The label does not take up any space
			label.setVisible(true);
			label.setText(this.text);
		} else if (PropertyTypeConstants.SHOW_LABEL_HIDE.equals(sl)) {
			// The label is still included when calculating the positions
			// of the different widgets. We set the text to an empty String
			// so that it does not take up any space
			label.setVisible(true);
			label.setText("");
		} else if (PropertyTypeConstants.SHOW_LABEL_NO_LABEL.equals(sl)) {
			// The label does not take up any space
			label.setVisible(false);
			label.setText("");
		}
	}
	
	/**
	 * Updates the field.
	 */
	private void updateField() {
		String sl = getString(PropertyTypeConstants.SHOW_LABEL);
		if (PropertyTypeConstants.SHOW_LABEL_LABEL_ONLY.equals(sl)) {
			// The field does not take up any space
			field.setVisible(false);
		} else {
			field.setVisible(true);
		}
	}	
		
	/**
	 * Sets the box type of the figure. This is determined by the Corporate Design.
	 */
	private void setBoxType() {
		setBoxType(PropertyTypeConstants.BOX_TYPE_HORIZONTAL);
	}	

	/**
	 * Updates the position of the label according to the Corporate Design.
	 */
	private void updateLabelPosition() {
		removeFigure(label);
		removeFigure(field);
		addFigure(label);
		addFigure(field);
	}
	
	/**
	 * Updates the alignment of the label using the Corporate Design.
	 */
	private void updateLabelAlignment() {
		CorporateDesign cd = getFigureContext().getCorporateDesign();
		String ha = cd.getLabelHorizontalAlignment();
		labelWidget.setPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT, ha);
		
		String va = cd.getLabelVerticalAlignment();
		labelWidget.setPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT, va);		
	}	
	
	/**
	 * Updates the alignment of the field using the Corporate Design.
	 */
	private void updateFieldAlignment() {
		CorporateDesign cd = getFigureContext().getCorporateDesign();
		String ha = cd.getFieldHorizontalAlignment();
		fieldWidget.setPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT, ha);
		
		String va = cd.getFieldVerticalAlignment();
		fieldWidget.setPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT, va);		
	}
	
	/**
	 * Method called when a Preference is changed in Eclipse.
	 */
	public void preferenceChange() {
		updateFromCorporateDesign();
	}	
	
	/**
	 * Updates values which depend upon the corporate design.
	 */
	private void updateFromCorporateDesign() {
		setBoxType();
		updateLabelAlignment();
		updateFieldAlignment();
		updateLabelPosition();
	}
	
	/** 
	 * Whenever the inner field widget is a ComboBox, this method will propagates any change made 
	 * on the "columns" property to the "width" property of the ComboBox. 
	 * 
	 * @see com.odcgroup.page.ui.figure.IWidgetFigure#notifyPropertyChange(java.lang.String)
	 */
	public void notifyPropertyChange(String name) {
		
		// OCS-26823 - 14/Jul/2008 - Resizing of drop down boxes 
		if (field instanceof ComboBox) {
			if (PropertyTypeConstants.COLUMNS.equals(name)) {
				String width = getString(PropertyTypeConstants.COLUMNS);
				if (width != null) {
					fieldWidget.setPropertyValue(PropertyTypeConstants.WIDTH, width);
				}
			}
		} 
		super.notifyPropertyChange(name);

	}	
	
	@Override
	public void setText(String newText) {
		super.setText("");
		this.text = newText;
		if (label != null) {
			updateLabel();
		}
	}
	
	/**
	 * Creates a new AttributeFigure.
	 * 
	 * @param widget
	 *            The Widget being displayed by this Figure
	 * @param figureContext
	 *            The context providing information required for displaying the page correctly
	 */
	public AttributeFigure(Widget widget, FigureContext figureContext) {
		super(widget, figureContext);

		createLabel();
		updateLabel();
		
		createField();
		updateField();
		
		updateFromCorporateDesign();
	}

	
	
}