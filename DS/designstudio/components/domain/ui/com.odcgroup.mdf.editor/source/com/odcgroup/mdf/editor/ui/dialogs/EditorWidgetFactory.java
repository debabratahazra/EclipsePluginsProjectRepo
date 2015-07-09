package com.odcgroup.mdf.editor.ui.dialogs;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.odcgroup.mdf.editor.ui.WidgetFactory;

public class EditorWidgetFactory implements WidgetFactory {
	public static final String KEY_DRAW_BORDER = "EditorWidgetFactory.drawBorder";
	public static final String DEFAULT_HEADER_COLOR = "__default__header__";
	public static final String COLOR_BORDER = "__border";
	public static final String COLOR_COMPOSITE_SEPARATOR = "__compSep";

	private Hashtable colorRegistry = new Hashtable();
	private Color backgroundColor;
	private Color foregroundColor;
	private Display display;
	public static final int BORDER_STYLE = SWT.NONE; //SWT.BORDER;
	private BorderPainter borderPainter;
	private Color borderColor;

	class BorderPainter implements PaintListener {
		public void paintControl(PaintEvent event) {
			Composite composite = (Composite) event.widget;
			Control[] children = composite.getChildren();
			
			for (int i = 0; i < children.length; i++) {
				Control c = children[i];
				if (c.isVisible()) {
					Object flag = c.getData(KEY_DRAW_BORDER);
					if ((flag != null) && (flag.equals(Boolean.TRUE))) {
						Rectangle b = c.getBounds();
						GC gc = event.gc;
						gc.setForeground(borderColor);
						gc.drawRectangle(b.x - 1, b.y - 1, b.width + 2, b.height + 2);
					}
				}
			}
		}
	}

	public EditorWidgetFactory() {
		this(Display.getCurrent());
	}

	public EditorWidgetFactory(Display display) {
		this.display = display;
		initialize();
	}

	public Button createButton(Composite parent, String text, int style) {
		int flatStyle = BORDER_STYLE == SWT.BORDER ? SWT.NULL : SWT.FLAT;
		Button button = new Button(parent, style | flatStyle);
		button.setBackground(backgroundColor);
		button.setForeground(foregroundColor);
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
		composite.setBackground(backgroundColor);
		return composite;
	}
	
	public Composite createCompositeSeparator(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(getColor(COLOR_COMPOSITE_SEPARATOR));
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
		label.setBackground(backgroundColor);
		label.setForeground(foregroundColor);
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
		label.setBackground(backgroundColor);
		label.setForeground(foregroundColor);
		return label;
	}

	public Label createSeparator(Composite parent, int style) {
		Label label = new Label(parent, SWT.SEPARATOR | style);
		label.setBackground(backgroundColor);
		label.setForeground(borderColor);
		return label;
	}
	
	public Table createTable(Composite parent, int style) {
		Table table = new Table(parent, BORDER_STYLE | style);
		table.setBackground(backgroundColor);
		table.setForeground(foregroundColor);
		paintBordersFor(table);
		return table;
	}
	
	public Text createText(Composite parent, String value) {
		return createText(parent, value, BORDER_STYLE | SWT.SINGLE);
	}
	
	public Text createText(Composite parent, String value, int style) {
		Text text = new Text(parent, style);
		if (value != null) {
			text.setText(value);
		}
		text.setBackground(backgroundColor);
		text.setForeground(foregroundColor);
		paintBordersFor(text);
		return text;
	}
	
	public Tree createTree(Composite parent, int style) {
		Tree tree = new Tree(parent, BORDER_STYLE | style);
		tree.setBackground(backgroundColor);
		tree.setForeground(foregroundColor);
		paintBordersFor(tree);
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
		combo.setBackground(backgroundColor);
		combo.setForeground(foregroundColor);
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
		Enumeration colors = colorRegistry.elements();
		while (colors.hasMoreElements()) {
			Color c = (Color) colors.nextElement();
			if (c != null) c.dispose();
		}		
		colorRegistry.clear();
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
		backgroundColor = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		registerColor(COLOR_BORDER, 195, 191, 179);
		registerColor(COLOR_COMPOSITE_SEPARATOR, 152, 170, 203);
		registerColor(DEFAULT_HEADER_COLOR, 0x48, 0x70, 0x98);
		if (isWhiteBackground()) {
			borderColor = getColor(COLOR_BORDER);
		} else {
			borderColor = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		}
		foregroundColor = display.getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	}

	private Color getColor(String key) {
		return (Color) colorRegistry.get(key);
	}

	private void paintBordersFor(Control c) {
		if (BORDER_STYLE != SWT.BORDER) {
			if (borderPainter == null) {
				borderPainter = new BorderPainter();
			}
			
			c.setData(KEY_DRAW_BORDER, Boolean.TRUE);
			c.getParent().addPaintListener(borderPainter);
		}
	}
	
	private Color registerColor(String key, int r, int g, int b) {
		Color c = new Color(display, r, g, b);
		colorRegistry.put(key, c);
		return c;
	}
}
