package com.odcgroup.mdf.editor.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface WidgetFactory {
	public Button createButton(Composite parent, String text, int style);
	public Composite createComposite(Composite parent);
	public Composite createComposite(Composite parent, int style);
	public Composite createCompositeSeparator(Composite parent);
	public Label createHeadingLabel(Composite parent, String text);
	public Label createHeadingLabel(Composite parent, String text, int style);
	public Label createHeadingLabel(Composite parent, String text, Color bg);
	public Label createHeadingLabel(
		Composite parent,
		String text,
		Color bg,
		int style);
	public Label createLabel(Composite parent, String text);
	public Label createLabel(Composite parent, String text, int style);
	public Label createSeparator(Composite parent, int style);
	public Table createTable(Composite parent, int style);
	public Text createText(Composite parent, String value);
	public Text createText(Composite parent, String value, int style);
	public Tree createTree(Composite parent, int style);
	public Combo createCombo(Composite parent);
	public Combo createCombo(Composite parent, String[] items);
	public Combo createCombo(Composite parent, String[] items, int style);
	public Group createGroup(Composite parent, String text);
	public Group createGroup(Composite parent, String text, int style);
	public void dispose();
	public Color getBackgroundColor();
	public Color getBorderColor();
	public Color getForegroundColor();
	public boolean isWhiteBackground();
}