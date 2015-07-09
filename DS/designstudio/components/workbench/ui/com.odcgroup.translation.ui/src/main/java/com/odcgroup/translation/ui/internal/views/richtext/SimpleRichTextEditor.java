package com.odcgroup.translation.ui.internal.views.richtext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.ui.internal.views.converter.RichTextToStyledTextConverter;
import com.odcgroup.translation.ui.internal.views.converter.StyledTextToRichTextConverter;

/**
 * This class provides the basic features for a kind of Rich Text Editor. The
 * implementation is based on the class StyledText (SWT)
 * 
 * @author atr
 * @version 1.0
 */
public class SimpleRichTextEditor {

	private List<StyleRange> cachedStyles = new ArrayList<StyleRange>();

	// the current active font
	private Font defaultFont;
	
	private Set<Color> colors = new HashSet<Color>();
	private Set<Font> fonts = new HashSet<Font>();
	
	private RichTextToolbar toolBar; 

	private StyledTextToRichTextConverter styledToRichTextConverter;

	private StyledText text;
	
	private BoldHandler boldHandler;
	private ItalicHandler italicHandler;
	private UnderlineHandler underlineHandler;
	private FontSizeHandler fontSizeHandler;
	
	private Menu createEditMenu(Shell shell) {
		Menu menu = new Menu(shell);

		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("Cut \tCTRL+X");
		item.setAccelerator(SWT.MOD1 + 'X');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleCutCopy();
				text.cut();
			}
		});
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("Copy \tCTRL+C");
		item.setAccelerator(SWT.MOD1 + 'C');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleCutCopy();
				text.copy();
			}
		});
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("Paste \tCTRL+V");
		item.setAccelerator(SWT.MOD1 + 'V');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				text.paste();
			}
		});
		
		return menu;
	}

	/*
	 * Cache the style information for text that has been cut or copied.
	 */
	private void handleCutCopy() {
		// Save the cut/copied style info so that during paste we will maintain
		// the style information. Cut/copied text is put in the clipboard in
		// RTF format, but is not pasted in RTF format. The other way to
		// handle the pasting of styles would be to access the Clipboard
		// directly and
		// parse the RTF text.
		cachedStyles = new Vector<StyleRange>();
		Point sel = text.getSelectionRange();
		int startX = sel.x;
		for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
			StyleRange style = text.getStyleRangeAtOffset(i);
			if (style != null) {
				style.start = style.start - startX;
				if (!cachedStyles.isEmpty()) {
					StyleRange lastStyle = cachedStyles
							.get(cachedStyles.size() - 1);
					if (lastStyle.similarTo(style)
							&& lastStyle.start + lastStyle.length == style.start) {
						lastStyle.length++;
					} else {
						cachedStyles.add(style);
					}
				} else {
					cachedStyles.add(style);
				}
			}
		}
	}

	private void handleExtendedModify(ExtendedModifyEvent event) {
		if (event.length == 0)
			return;
		StyleRange style;
		if (event.length == 1
				|| text.getTextRange(event.start, event.length).equals(
						text.getLineDelimiter())) {
//			if (" ".equals(text.getTextRange(event.start, event.length))) {
//				// do not apply style to space character.
//				return;
//			}
			style = null;
			style = new StyleRange(event.start, event.length, null, null, SWT.NORMAL);
			int fontSize = fontSizeHandler.getSize();
			//System.out.println("font-size:"+fontSize);
			style.font = createFont(fontSizeHandler.getSize());
			if (boldHandler.isSelected()) {
				// set bold
				style.fontStyle |= SWT.BOLD;
			} else {
				// clear bold
				style.fontStyle &= ~SWT.BOLD;
			}
			if (italicHandler.isSelected()) {
				// set italic
				style.fontStyle |= SWT.ITALIC;
			} else {
				// clear italic
				style.fontStyle &= ~SWT.ITALIC;
			}
			style.underline = underlineHandler.isSelected();
			if (!style.isUnstyled()) {
				text.setStyleRange(style);
			}
		} else {
			// paste occurring, have text take on the styles it had when it was cut/copied
			for (int i = 0; i < cachedStyles.size(); i++) {
				style = (StyleRange) cachedStyles.get(i);
				StyleRange newStyle = (StyleRange) style.clone();
				newStyle.start = style.start + event.start;
				text.setStyleRange(newStyle);
			}
		}
	}

	/**
	 * @return null if the height is equals to the height of the default font.
	 */
	protected synchronized Font createFont(int height) {
		// check if it map the default font
		FontData data = defaultFont.getFontData()[0];
		if (data.getHeight() == height) {
			return null;
		}
		
		// check if this color has already been created
		for (Font f : fonts) {
			data = f.getFontData()[0]; 
			if (data.getHeight() == height) {
				return f;
			}
		}
		
		// create a new font
		FontData[] fontData = defaultFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(height);
		}
		Font f = new Font(text.getDisplay(), fontData);
		fonts.add(f);
		
		return f;
	}
	
	
	private void disposeFonts() {
		for (Font f : this.fonts) {
			f.dispose();
		}
		fonts.clear();
	}

	private void disposeColors() {
		for (Color c : this.colors) {
			c.dispose();
		}
		colors.clear();
	}

	protected void disposeAll() {
		disposeFonts();
		disposeColors();
		if (defaultFont != null) {
			defaultFont.dispose();
		}
		defaultFont = null;
	}

	protected void handleKeyPressed(KeyEvent e) {
		if (e.stateMask == 0) {
			
			// Enter key
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {

				/*
				 * if the previous line has a bullet define, then apply
				 * this bullet to the new line.
				 */
				int offset = text.getCaretOffset();
				int line = text.getLineAtOffset(offset);
				if (line > 0) {
					Bullet bullet = text.getLineBullet(line-1/*previous line*/);
					if (bullet != null) {
						text.setLineBullet(line, 1, bullet);
					}
				}
				
			// back space
			} else if (e.keyCode == SWT.BS) {
				
				int offset = text.getCaretOffset();
				int line = text.getLineAtOffset(offset);
				Bullet bullet = text.getLineBullet(line);
				if (bullet != null) {
					int offsetAtLine = text.getOffsetAtLine(line);
					if (offset == offsetAtLine) {
						// remove the bullet.
						text.setLineBullet(line, 1, null);
					}
				}
			}
			
		}
//			// THIS DOES NOT WORK, THE QUESTION IS :
//			// HOW CAN I OVERRIDE DEFAULT ECLIPSE Copy/Cut/Paste command in this dialog ???
//			// if (ch == 'A') {
//			// // handleSelectAll();
//			// } else if (ch == 'C') {
//			// handleCutCopy();
//			// text.copy();
//			// } else if (ch == 'X') {
//			// handleCutCopy();
//			// text.cut();
//			// } else if (ch == 'V') {
//			// text.paste();
//			// }
//			
//			// CTRL-U
//			if (e.keyCode == 117) {
//				underlineHandler.updateStyle(!underlineHandler.isSelected());
//				toolBar.updateToolbar();
//
//			// CTRL-I
//			} else if (e.keyCode == 105) {
//				italicHandler.updateStyle(!italicHandler.isSelected());
//				toolBar.updateToolbar();
//				e.doit = false;
//
//			// CTRL-B
//			} else if (e.keyCode == 98) {
//				boldHandler.updateStyle(!boldHandler.isSelected());
//				toolBar.updateToolbar();
//			}
//
//		}
	}

	public void addModifyListener(ModifyListener modifyListener) {
		text.addModifyListener(modifyListener);
	}

	/**
	 * @return the rich text string representation
	 */
	public String getText(boolean convert) {

		if (!convert) {
			return text.getText();
		}

		// --------------------------------------------------------
		// CALL THE CONVERTER StyledText-to-RichText
		// --------------------------------------------------------
		if (styledToRichTextConverter == null) {
			styledToRichTextConverter = new StyledTextToRichTextConverter(text);
		}
		String convertedText = styledToRichTextConverter.convert();
		
		return convertedText;
	}

	public void setEditable(boolean editable) {
		text.setEditable(editable);
		if (!editable) toolBar.setEnabled(false);
	}

	public void setText(String msg, boolean enableToolbar) throws Exception {
		
		disposeFonts();
		disposeColors();
		
		toolBar.setEnabled(enableToolbar);
		
		if (toolBar.isEnabled()) {
			if (RichTextUtils.isRichRext(msg)) {
				Display display = text.getShell().getDisplay();
				RichTextToStyledTextConverter converter = 
						new RichTextToStyledTextConverter(text, display, defaultFont, this.fonts, this.colors);
				RichTextUtils.parseRichText(msg, converter);
				// if something goes wrong an exception is raised.
				
			} else {
				text.setText(RichTextUtils.unescapeRichTextTags(msg));
			}
		} else {
			text.setText(msg);
		}
		
		// set the focus at the beginning of the text
		text.setFocus();
		text.setCaretOffset(0);
		
		// synchronize the toolbar
		toolBar.updateToolbar();
	}

	public SimpleRichTextEditor(Composite parent, final Font font) {

		// prepare general layout
		Composite body = new Composite(parent, SWT.NULL);
		final GridLayout bodyLayout = new GridLayout();
		body.setLayout(bodyLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH));

		toolBar = new RichTextToolbar(body);

		// style text editor
		int style = SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL;
		text = new StyledText(body, style);
		text.setMargins(5, 5, 5, 5);
		text.setAlignment(SWT.LEFT);
		
		// set the default font for editing text
		// final String DEFAULT_FONT_NAME = "Arial";
		final int DEFAULT_FONT_SIZE = 10;
		final String[] fontSizes = new String[] { "8", "9", DEFAULT_FONT_SIZE + "", "12", "13", "14", "15", "16", "17" };

		FontData fontData = text.getFont().getFontData()[0];
		fontData.setHeight(DEFAULT_FONT_SIZE);
		defaultFont = new Font(text.getDisplay(), fontData);
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				text.setFont(defaultFont);
			}
		});
		
		fontSizeHandler = new FontSizeHandler(text) {
			protected Font getFont(int height) {
				return createFont(Integer.valueOf(height));
			}
        };

		// Bold/Italic/Underline handler
		boldHandler = new BoldHandler(text);
		italicHandler = new ItalicHandler(text);
		underlineHandler = new UnderlineHandler(text);

		// Create all the actions for the toolbar
		RichTextAction[] actions = {

//				// font name & size
//				new DropDownAction("Font Name", fontNames, "Arial", fontNameHandler),
				new DropDownAction("Font Size", fontSizes, DEFAULT_FONT_SIZE+"", fontSizeHandler),
//				new SeparatorAction(),

				// bold, italic underline
				new CheckButtonAction("text_bold", "Make the text bold", boldHandler),
				new CheckButtonAction("text_italic", "Italicize the text", italicHandler),
				new CheckButtonAction("text_underline", "Underline the text", underlineHandler),

				// NICE TO HAVE
				// subscript & superscript
				/*
				new SeparatorAction(),
				new CheckButtonAction("text_subscript", "Create small letter below the text baseline", new SubscriptHandler(text)),
				new CheckButtonAction("text_superscript", "Create small letter above the line of text", new SuperscriptHandler(text)),
				*/

				// paragraph alignment
				new SeparatorAction(),
				new CheckButtonAction("text_align_left", "Align text to the left", true, new AlignLeftHandler(text)),
				new CheckButtonAction("text_align_center", "Center text", new CenterHandler(text)),
				new CheckButtonAction("text_align_right", "Align text to the right", new AlignRightHandler(text)),
				new CheckButtonAction("text_align_justify", "Align text to both the left and right margins", new JustifyHandler(text)),

				// ordered and unordered list
				new SeparatorAction(),
				new CheckButtonAction("text_list_numbers", "Numbered List", new OrderedListHandler(text)),
				new CheckButtonAction("text_list_bullets", "Bulleted List", new UnorderedListHandler(text)),

				// indentation
				new SeparatorAction(),
				new PushButtonAction("text_indent_remove", "Decrease indent", new DecreaseLineIndent(text)),
				new PushButtonAction("text_indent", "Increase indent", new IncreaseLineIndent(text)),
				new PushButtonAction("text_indent_wrapped_lines", "Increase indent of wrapped lines", new IncreaseWrappedLinesIndent(text)),
				new PushButtonAction("text_indent_wrapped_lines_remove", "Decrease indent of wrapped lines", new DecreaseWrappedLinesIndent(text)),

				// foreground and background color
				new SeparatorAction(),
				new PushButtonAction("text_foreground_color", "Change the text color", new ForegroundColorHandler(text, colors)),
				new PushButtonAction("text_background_color", "Change the color behind the selected text", new BackgroundColorHandler(text, colors)),

				// clear styles
				new SeparatorAction(),
				new PushButtonAction("text_clear", "Clear all the formatting of the selected text",	new ClearHandler(text)) 
		};

		toolBar.setActions(actions);

		GridData spec = new GridData();
		spec.horizontalAlignment = GridData.FILL;
		spec.grabExcessHorizontalSpace = true;
		spec.verticalAlignment = GridData.FILL;
		spec.grabExcessVerticalSpace = true;
		text.setLayoutData(spec);

		// install listeners
		text.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeAll();
			}
		});
		text.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent e) {
				handleExtendedModify(e);
			}
		});
//		text.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				// TODO ---
//			}
//		});
		text.addCaretListener(new CaretListener() {
			public void caretMoved(CaretEvent event) {				
				toolBar.updateToolbar();
			}
		});
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				handleKeyPressed(e);
			}
		});
		
		text.setMenu(createEditMenu(parent.getShell()));

	}
	
}
