package com.odcgroup.translation.ui.internal.views.richtext;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.odcgroup.translation.ui.TranslationUICore;

class RichTextToolbar {

	private ToolBar toolBar;
	
	private Map<ActionHandler, ComboViewer> comboViewers = new HashMap<ActionHandler, ComboViewer>();

	private void createButton(final ButtonAction action) {
		final ToolItem item = new ToolItem(toolBar, action.getStyle());
		item.setImage(createImage(action.getImageName()));
		item.setToolTipText(action.getTooltip());
		item.setData(action.getHandler());
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (action.getHandler() != null) {
					boolean selected = ((ToolItem)e.widget).getSelection();
					action.getHandler().updateStyle(selected);
				}
				updateToolbar();
			}
		});
		item.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				item.getImage().dispose();
			}
		});
	}

	private void createDropDownCombo(final DropDownAction action) {
		ToolItem sep = new ToolItem(toolBar, SWT.SEPARATOR);
		sep.setData(action.getHandler());
		Composite composite = new Composite(toolBar, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = layout.marginHeight = 1;
		composite.setLayout(layout);
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.BORDER);
		combo.setToolTipText(action.getToolTip());
		ComboViewer mComboViewer = new ComboViewer(combo);
		mComboViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						String selection = (String) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						action.getHandler().updateStyle(selection);
					}
				});
		mComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		mComboViewer.setInput(action.getValues());
		mComboViewer.setSelection(new StructuredSelection(action.getSelection()));
		comboViewers.put(action.getHandler(), mComboViewer);
		combo.setEnabled(true);
		sep.setWidth(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x); // control.getSize().x);
		sep.setControl(composite);
	}

	private Image createImage(String name) {
		return TranslationUICore.getImageDescriptor("icons/" + name + ".png")
				.createImage();
	}

	private void createSeparator() {
		new ToolItem(toolBar, SWT.SEPARATOR);
	}

	private void createToolItems(RichTextAction... actions) {
		for (RichTextAction action : actions) {
			int style = action.getStyle();
			if ((style & SWT.PUSH) != 0) {
				createButton((ButtonAction)action);
			} else if ((style & SWT.CHECK) != 0) {
				createButton((ButtonAction)action);
			} else if ((style & SWT.DROP_DOWN) != 0) {
				createDropDownCombo((DropDownAction)action);
			} else if ((style & SWT.SEPARATOR) != 0) {
				createSeparator();
			}
		}
	}

	private void disposeAll() {
		// TODO do we need to dispose something ?????
	}

	public void setActions(RichTextAction[] actions) {
		createToolItems(actions);
	}

	public void setEnabled(boolean enabled) {
		toolBar.setEnabled(enabled);		
	}
	
	public boolean isEnabled() {
		return toolBar.isEnabled();
	}

	public void updateToolbar() {
		for (ToolItem item : toolBar.getItems()) {
			Object data = item.getData();
			if (data != null) {
				if (data instanceof FontHandler) {
					// combo box
					FontHandler handler = ((FontHandler)data);
					ComboViewer cv = comboViewers.get(handler);
					if (cv != null) {
						String selection = handler.getSelection();
						IStructuredSelection ss = ((IStructuredSelection)cv.getSelection());
						String currentSelection = (String)ss.getFirstElement();
						
						//System.out.println("size current:"+currentSelection+" new:"+selection);
						
						if (!currentSelection.equals(selection)) {
							cv.setSelection(new StructuredSelection(selection));
						}
					}
				} else if (data instanceof ActionHandler) {
					// button
					item.setSelection(((ActionHandler) data).isStyleSelected(item
							.getSelection()));
				}
			}
		}
	}

	public RichTextToolbar(Composite parent, RichTextAction... actions) {
		toolBar = new ToolBar(parent, SWT.FLAT | SWT.WRAP );
		
		GridData spec = new GridData();
		spec.horizontalAlignment = GridData.FILL;
		spec.grabExcessHorizontalSpace = true;
		spec.grabExcessVerticalSpace = false;
		toolBar.setLayoutData(spec);

		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				disposeAll();
			}
		});
	}

}
