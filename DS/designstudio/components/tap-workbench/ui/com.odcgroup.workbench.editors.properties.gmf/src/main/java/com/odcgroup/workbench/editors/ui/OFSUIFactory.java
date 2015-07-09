package com.odcgroup.workbench.editors.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author pkk
 *
 */
public class OFSUIFactory implements PaintListener {

	/**
	 * 
	 */
	public static final OFSUIFactory INSTANCE = new OFSUIFactory();

	/**
	 * private constructor
	 */
	private OFSUIFactory() {
	}
	
	public Composite createCompositeWithFillLayout(Composite parent) {
		Composite comp = new Composite(parent, SWT.FILL);
	    comp.setBackground(parent.getBackground());
	    comp.setLayout(new FillLayout(SWT.VERTICAL));
	    return comp;
		
	}
	
	/**
	 * @param parent
	 * @return
	 */
	public Composite createRadioComposite(Composite parent){
		Composite comp = new Composite(parent, SWT.NO_RADIO_GROUP );
	    comp.setBackground(parent.getBackground());
	    comp.setLayout(new RowLayout());
	    comp.setFont(parent.getFont());
	    comp.addPaintListener(this);
	    return comp;		
		
	}
	
	/**
	 * @param parent
	 * @return
	 */
	public Composite createCompositeWithRowLayout(Composite parent){
		Composite comp = new Composite(parent, SWT.FILL);
	    comp.setBackground(parent.getBackground());
	    comp.setLayout(new RowLayout());
	    comp.setFont(parent.getFont());
	    comp.addPaintListener(this);
	    return comp;		
	}

	/**
	 * @param parent
	 * @return
	 */
	public Composite createComposite(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
	    comp.setBackground(parent.getBackground());
        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        comp.setLayout(gridLayout);
	    comp.setFont(parent.getFont());
	    comp.addPaintListener(this);
	    return comp;
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @param gridDataStyle
	 * @return
	 */
	public Composite createComposite(Composite parent, int columns, int gridDataStyle){
        Composite composite = createComposite(parent);
        GridLayout gridLayout = new GridLayout(columns, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(gridDataStyle));
        return composite;
    }
	
	/**
	 * @param parent
	 * @return
	 */
	public Group createGroup(Composite parent,
			String groupName) {
		Group group = new Group(parent, SWT.NONE);
		group.setBackground(parent.getBackground());
		if (groupName != null)
		group.setText(groupName);
		GridLayout gridLayout = new GridLayout(1, false);
		group.setLayout(gridLayout);
		return group;
	}
	
	public void createLine(int width, Composite parent){
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = width;
		line.setVisible(false);
		line.setLayoutData(gridData);		
	}
	
	public void createLineWithRowData(Composite parent, int width){
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		RowData gridData = new RowData();
		gridData.width = width;
		line.setVisible(false);
		line.setLayoutData(gridData);		
		
	}
	
	/**
	 * @param parent
	 * @param ncol
	 */
	public void createLine(Composite parent, int ncol) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL
				| SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		line.setLayoutData(gridData);
	}

	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public Label createLabel(Composite parent, String text, String tooltip, boolean required) {
		Label label = new Label(parent, 0);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		String colon = " :";
		if (required){
			colon = "* :";
		}
		label.setText(text == null ? "" : text+colon);
		if (tooltip != null)	
			label.setToolTipText(tooltip);
		GridData data = new GridData(768);
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 10;
		data.widthHint = 100;
		label.setLayoutData(data);
		return label;
	}
	
	public Label createLabel(Composite parent, String text){
		Label label = new Label(parent, 0);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		label.setText(text == null ? "" : text);
		GridData data = new GridData(768);
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		data.horizontalIndent = 10;
		data.widthHint = 100;
		label.setLayoutData(data);
		return label;	
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @return
	 */
	public Label createFiller(Composite parent, int columns){
		Label label = new Label(parent, 0);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		GridData data = new GridData(768);
		data.horizontalIndent = 10;
		data.horizontalSpan = columns;
		label.setLayoutData(data);
		return label;		
	}

	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public Label createReadOnly(Composite parent, String text) {
		Label label = new Label(parent, 0);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		label.setText(text == null ? "" : text);
		GridData data = new GridData(1808);
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public Label createDescriptionLabel(Composite parent, String text) {
		Label label = new Label(parent, 0);
		label.setBackground(parent.getBackground());
		label.setFont(parent.getFont());
		label.setText(text == null ? "" : text+" :");
		GridData data = new GridData(768);
		data.horizontalAlignment = GridData.FILL;
		data.horizontalIndent = 10;
		data.verticalAlignment = GridData.FILL;
		data.verticalSpan = 6;
		data.widthHint = 80;
		label.setLayoutData(data);
		return label;
	}
	
	public Combo createCombo(Composite parent){
		Combo combo =  new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 300;
		combo.setLayoutData(gridData);
		return combo;
	}

	/**
	 * @param parent
	 * @return
	 */
	public Text createTextField(Composite parent) {
		return createTextField(parent, 300);
	}
	
	/**
	 * @param parent
	 * @param textWidth
	 * @return
	 */
	public Text createTextField(Composite parent, int textWidth){
		Text text = new Text(parent, 4);
		text.setBackground(parent.getBackground());
		GridData data = new GridData();
		data.verticalAlignment = GridData.CENTER;
		data.widthHint = textWidth;
		text.setLayoutData(data);
		return text;		
	}
	
	/**
	 * @param parent
	 * @param width
	 * @return
	 */
	public Text createTextArea(Composite parent, int width){
		return createTextArea(parent, width, 1, 0);	
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param horIndent
	 * @return
	 */
	public Text createTextArea(Composite parent, int width, int verIndent){
		return createTextArea(parent, width, 1, verIndent);		
	}
	
	/**
	 * @param parent
	 * @param width
	 * @return
	 */
	public Text createTextArea(Composite parent, int width, int colspan, int verIndent){
		Text description = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		description.setBackground(parent.getBackground());
		GridData gridData = new GridData();
		gridData.widthHint = width;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.heightHint = 150;
		gridData.verticalIndent=verIndent;
		description.setLayoutData(gridData);
		return description;		
	}

	/**
	 * @param parent
	 * @return
	 */
	public Text createTextArea(Composite parent) {
		return createTextArea(parent, 300);
	}

	/**
	 * @param group
	 * @param label
	 * @return
	 */
	public Button createCheckBox(Composite parent, String text) {
		Button button = new Button(parent, SWT.CHECK | SWT.COLOR_WIDGET_DARK_SHADOW);
		button.setBackground(parent.getBackground());
		button.setText(text == null ? "" : "  "+text);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		button.setLayoutData(data);
		return button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
	 */
	public void paintControl(PaintEvent e) {
		Control control = (Control) e.widget;
		if (control instanceof Composite) {
			Control controls[] = ((Composite) control).getChildren();
			for (int i = 0; i < controls.length; i++) {
				control = controls[i];
				if (control.isDisposed() || !control.isVisible()
						|| !(control instanceof Text)
						&& !(control instanceof CCombo)
						&& !(control instanceof Combo))
					continue;
				Rectangle b = control.getBounds();
				GC gc = e.gc;
				gc.setForeground(control.getBackground());
				gc.drawRectangle(b.x - 1, b.y - 1, b.width + 1, b.height + 1);
				gc.setForeground(control.getDisplay().getSystemColor(2));
				if (control instanceof CCombo)
					gc.drawRectangle(b.x - 1, b.y - 1, b.width + 1,
							b.height + 1);
				else
					gc.drawRectangle(b.x - 1, b.y - 2, b.width + 1,
							b.height + 3);
			}

		}

	}

}
