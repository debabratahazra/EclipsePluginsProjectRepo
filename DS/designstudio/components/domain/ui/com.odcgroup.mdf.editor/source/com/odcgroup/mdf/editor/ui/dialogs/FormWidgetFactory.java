package com.odcgroup.mdf.editor.ui.dialogs;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.odcgroup.mdf.editor.ui.WidgetFactory;

public class FormWidgetFactory implements WidgetFactory {
	private Color backgroundColor;
	private Color foregroundColor;
	private Color borderColor;
	private Display display;
	public static final int BORDER_STYLE = SWT.BORDER;

	public FormWidgetFactory() {
		this(Display.getCurrent());
	}

	public FormWidgetFactory(Display display) {
		this.display = display;
		initialize();
	}

	public Button createButton(Composite parent, String text, int style) {
		int flatStyle = BORDER_STYLE == SWT.BORDER ? SWT.NULL : SWT.FLAT;
		Button button = new Button(parent, style | flatStyle);
		if (text != null) {
			button.setText(text);
		}
		return button;
	}
	
	public Composite createComposite(Composite parent) {
		return createComposite(parent, SWT.NULL);
	}
	
	public Composite createComposite(Composite parent, int style) {
		Composite composite = new Composite(parent, style);
		return composite;
	}
	
	public Composite createCompositeSeparator(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(borderColor);
		return composite;
	}

	public Label createHeadingLabel(Composite parent, String text) {
		return createHeadingLabel(parent, text, null, SWT.NONE);
	}

	public Label createHeadingLabel(Composite parent, String text, int style) {
		return createHeadingLabel(parent, text, null, style);
	}

	public Label createHeadingLabel(Composite parent, String text, Color bg) {
		return createHeadingLabel(parent, text, bg, SWT.NONE);
	}

	public Label createHeadingLabel(
		Composite parent,
		String text,
		Color bg,
		int style) {
		Label label = new Label(parent, style);
		if (text != null) {
			label.setText(text);
		}
		label.setFont(JFaceResources.getFontRegistry().get(JFaceResources.BANNER_FONT));
		return label;
	}

	public Label createLabel(Composite parent, String text) {
		return createLabel(parent, text, SWT.NONE);
	}

	public Label createLabel(Composite parent, String text, int style) {
		Label label = new Label(parent, style);
		if (text != null) {
			label.setText(text);
		}
		return label;
	}

	public Label createSeparator(Composite parent, int style) {
		Label label = new Label(parent, SWT.SEPARATOR | style);
		return label;
	}
	
	public Table createTable(Composite parent, int style) {
		Table table = new Table(parent, BORDER_STYLE | style);
		return table;
	}
	
	public Text createText(Composite parent, String value) {
		return createText(parent, value, BORDER_STYLE | SWT.SINGLE);
	}
	
	public Text createText(Composite parent, String value, int style) {
		Text text = new Text(parent, BORDER_STYLE | style);
		if (value != null) {
			text.setText(value);
		}
		return text;
	}
	
	public Tree createTree(Composite parent, int style) {
		Tree tree = new Tree(parent, BORDER_STYLE | style);
		return tree;
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.WidgetFactory#createCombo(org.eclipse.swt.widgets.Composite)
	 */
	public Combo createCombo(Composite parent) {
		return createCombo(parent, null, SWT.READ_ONLY);
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.WidgetFactory#createCombo(org.eclipse.swt.widgets.Composite, java.lang.String[])
	 */
	public Combo createCombo(Composite parent, String[] items) {
		return createCombo(parent, items, SWT.READ_ONLY);
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.WidgetFactory#createCombo(org.eclipse.swt.widgets.Composite, java.lang.String[], int)
	 */
	public Combo createCombo(Composite parent, String[] items, int style) {
		Combo combo = new Combo(parent, style);
		if (items != null) {
			combo.setItems(items);
		}
		return combo;
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.WidgetFactory#createGroup(org.eclipse.swt.widgets.Composite, java.lang.String)
	 */
	public Group createGroup(Composite parent, String text) {
		return createGroup(parent, text, SWT.NONE);
	}

	/**
	 * @see com.odcgroup.mdf.editor.ui.WidgetFactory#createGroup(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 */
	public Group createGroup(Composite parent, String text, int style) {
		Group group = new Group(parent, style);
		group.setBackground(backgroundColor);
		group.setForeground(foregroundColor);
		if (text != null) {
			group.setText(text);
		}
		return group;
	}

	public void dispose() {
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public Color getBorderColor() {
		return borderColor;
	}
	
	public Color getForegroundColor() {
		return foregroundColor;
	}
	
	public boolean isWhiteBackground() {
		return backgroundColor.getRed()==255 && backgroundColor.getGreen()==255 &&
			backgroundColor.getBlue()==255;
	}

	private void initialize() {
		foregroundColor = display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
		backgroundColor = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		borderColor = display.getSystemColor(SWT.COLOR_WIDGET_BORDER);
	}
}
