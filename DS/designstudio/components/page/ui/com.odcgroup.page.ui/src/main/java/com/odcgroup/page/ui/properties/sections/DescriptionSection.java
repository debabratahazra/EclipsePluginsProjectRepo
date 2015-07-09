package com.odcgroup.page.ui.properties.sections;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * An instance of this class will contains the widget properties related to
 * documentation
 * 
 * @author atr
 */
public class DescriptionSection extends AbstractPagePropertySection {

	/** The selected widget. */
	private Widget widget;

	/** The textarea. */
	private Text documentationText;

	/** The documentation label text. */
	public static final String DOCUMENTATION = "Description :";

	/** The container for the fields. */
	private Composite container;
	
	/**
	 * Gets the property category.
	 * 
	 * @return PropertyCategory The property category used
	 */
	protected PropertyCategory getPropertyCategory() {
		return PropertyCategory.DESCRIPTION_LITERAL;
	}

	/**
	 * Create the controls of the DocumentationSection.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param aTabbedPropertySheetPage
	 *            The property sheet page
	 */
	public void createControls(final Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		GridLayout parentLayout = new GridLayout(1, false);
		parent.setLayout(parentLayout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH)); 
		super.createControls(parent, aTabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory widgetFactory = getWidgetFactory();
		container = widgetFactory.createComposite(parent); 
		GridLayout gridLayout = new GridLayout(1, false); 
		gridLayout.marginHeight = 0; 
		container.setLayout(gridLayout); 
		container.setLayoutData(new GridData(GridData.FILL_BOTH)); 
		
		getWidgetFactory().createCLabel(container, DOCUMENTATION);
		documentationText = widgetFactory.createText(container, "", SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		documentationText.setLayoutData(new GridData(GridData.FILL_BOTH)); 
		documentationText.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String s = documentationText.getText();
				s = (s == null) ? "" : s;
				if (widget != null) {
					executeModifyCommand(widget, s);
				}
			}
		});

	}

	/**
	 * Notifies the section that the workbench selection has changed.
	 * 
	 * @param part
	 *            The active workbench part.
	 * @param selection
	 *            The active selection in the workbench part.
	 * 
	 *            Implementation note: transmit the category to the selection
	 *            only if it's an instance of a WidgetPropertySource.
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object input = ((IStructuredSelection) selection).getFirstElement();
		IWidgetPropertySource wps = (IWidgetPropertySource) AdaptableUtils.getAdapter(input,
				IWidgetPropertySource.class);
		widget = wps.getWidget();
		String s = widget.getPropertyValue(PropertyTypeConstants.DOCUMENTATION);
		if (s != null) {
			this.documentationText.setText(s);
		}

		wps.setCurrentPropertyCategory(getPropertyCategory());

		// Hide or show the fields depending upon whether the Widget has the
		// documentation Property
		if (hasDocumentation()) {
			container.setVisible(true);
		} else {
			container.setVisible(false);
		}

		super.setInput(part, selection);
	}

	/**
	 * Executes the modify widget event command.
	 * 
	 * @param widget
	 *            The widget to add
	 * @param documentation
	 *            The new documentation
	 */
	private void executeModifyCommand(Widget widget, String documentation) {
		if (!hasDocumentation()) {
			return;
		}

		Property p = widget.findProperty(PropertyTypeConstants.DOCUMENTATION);

		if (StringUtils.equals(p.getValue(), documentation)) {
			return;
		}

		Command command = new UpdatePropertyCommand(p, documentation);
		CommandStack cs = (CommandStack) getPart().getAdapter(CommandStack.class);
		cs.execute(command);
	}

	/**
	 * 
	 * Refresh the DocumentationSection.
	 * 
	 */
	public void refresh() {
		// do nothing
	}

	/**
	 * Returns true if the Widget has the Documentation property.
	 * 
	 * @return boolean
	 */
	private boolean hasDocumentation() {
		return widget.findProperty(PropertyTypeConstants.DOCUMENTATION) != null;
	}
}
