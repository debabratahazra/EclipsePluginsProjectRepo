package com.odcgroup.translation.ui.editor.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.translation.ui.editor.model.ITranslationTableItem;

/**
 *
 * @author pkk
 *
 */
public class TranslationGeneralPropertySection extends AbstractPropertySection {

	// copy/pasted from org.eclipse.draw2d.ColorConstants, which is an interface so it's also static, so only created once - and never disposed. 
	private static final Color lightGray = new Color(null, 192, 192, 192);
	
	private CLabel tcLabel;
	private CLabel projLabel;
	private CLabel uriLabel;
	
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage); 
		
		Composite pageComposite = getWidgetFactory().createComposite(parent);
        GridLayout layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        pageComposite.setLayout(layout);
        
        Composite body = getWidgetFactory().createComposite(pageComposite);
		layout = new GridLayout(2, false);
		body.setLayout(layout);
        GridData data = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(data);
		body.setBackground(pageComposite.getBackground());

		CLabel cLabel = new CLabel(body, SWT.LEFT);
		cLabel.setBackground(body.getBackground());
		cLabel.setText("Type :");
		
		tcLabel =  new CLabel(body, SWT.LEFT);
		tcLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		tcLabel.setBackground(lightGray);
		CLabel pLabel = new CLabel(body, SWT.LEFT);
		pLabel.setBackground(body.getBackground());
		pLabel.setText("Project Name :");
		
		projLabel=  new CLabel(body, SWT.LEFT);
		projLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		projLabel.setBackground(lightGray);
		
		CLabel rLabel = new CLabel(body, SWT.LEFT);
		rLabel.setBackground(body.getBackground());
		rLabel.setText("Model Location :    ");
		
		uriLabel =  new CLabel(body, SWT.LEFT);
		uriLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		uriLabel.setBackground(lightGray);

	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		Object input = ((IStructuredSelection) selection).getFirstElement();
		if (input instanceof ITranslationTableItem) {
			ITranslationTableItem item = (ITranslationTableItem) input;
			tcLabel.setText(item.getTranslation().getDisplayName(item.getTranslationKind()));
			projLabel.setText(item.getProject());
			uriLabel.setText(item.getResourceURI());
		}
	}

}
