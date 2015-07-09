package com.odcgroup.translation.ui.views.table;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.command.ITranslationCommandProvider;
import com.odcgroup.translation.ui.internal.TranslationTableManager;
import com.odcgroup.translation.ui.internal.views.BaseTranslationDialog;
import com.odcgroup.translation.ui.internal.views.RichTextTranslationDialog;
import com.odcgroup.translation.ui.internal.views.TranslationDialog;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationTable;
import com.odcgroup.translation.ui.views.ITranslationTableCellModifier;
import com.odcgroup.translation.ui.views.ITranslationTableContentProvider;
import com.odcgroup.translation.ui.views.ITranslationTableLabelProvider;

/**
 * The class <code>TranslationTable</code> implements the basic mechanisms to
 * manager the translation inside a Table widget.
 * <p>
 * 
 * Concrete behaviors must be implemented by sub classes.
 * 
 * @author atr
 */
public abstract class TranslationTable implements ITranslationTable, IPropertyChangeListener, PropertyChangeListener {

	/** The symbolic translation font name */
	private static final String TRANSLATION_FONT_ID = "com.odcgroup.workbench.fontdefinition.translations";

	/** the project */
	private IProject project;

	/** The table viewer */
	private TableViewer tableViewer;
	
	/** The translations */
	private ITranslationModel model;

	/** The control which contains the table and the button panel */
	private Composite tablePanel;

	/** this control which contains all the buttons */
	private Composite buttonsPanel;

	/** Collection of buttons created by the <code>createButton</code> method. */
	private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
	
	/** translation command provider */
	private ITranslationCommandProvider commandProvider;

	/** Content provider */
	private ITranslationTableContentProvider tableContentProvider;

	/** Label provider */
	private ITranslationTableLabelProvider tableLabelProvider;

	/** Cell modifier */
	private ITranslationTableCellModifier translationCellModifier;
	
	/** The edit command */
	private ITranslationCommand editCommand;

	/** The remove command */
	private ITranslationCommand removeCommand;

	private boolean richTextStatus = false;

	/**
	 * @return FontRegistry
	 */
	private FontRegistry getFontRegistry() {
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		ITheme theme = themeManager.getTheme(IThemeManager.DEFAULT_THEME);
		return theme.getFontRegistry();
	}

	/**
	 * @return the table viewer
	 */
	protected final TableViewer getViewer() {
		return this.tableViewer;
	}

	/**
	 * @return The underlying translation model
	 */
	protected final ITranslationModel getModel() {
		return this.model;
	}
	
	/**
	 * @return Font
	 */
	protected Font getPreferredFont() {
		return getFontRegistry().get(TRANSLATION_FONT_ID);
	}
	
	/**
	 * @param parent
	 */
	private void createTable(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


		// Create the table viewer to display the translation
		int style = SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.LINE_SOLID;
		TableViewer tv = new TableViewer(composite, style);
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChange(event);
			}
		});
		tv.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick(event);
			}
		});

		// Set the content and label providers
		tv.setContentProvider(getTranslationTableContentProvider());
		tv.setLabelProvider(getTranslationTableLabelProvider());

		// Set up the table
		Table table = tv.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING));

		tableViewer = tv;
		hookTable(tv);
	}
	
	/**
	 * This method create all standard buttons
	 * @param parent
	 */
	private void createButtons(Composite parent) {
		
		// create container for all the buttons
		buttonsPanel = new Composite(parent, SWT.NONE);
		buttonsPanel.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		buttonsPanel.setLayout(layout);
		buttonsPanel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Create Edit Button
		createButton(buttonsPanel, TranslationTableConstants.EDIT_ID);

		// Create Remove Button
		createButton(buttonsPanel, TranslationTableConstants.REMOVE_ID);

	}

	/**
	 * @param parent
	 */
	private void createControls(Composite parent) {

		// create container for the table
		tablePanel = new Composite(parent, SWT.NONE);
		tablePanel.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		tablePanel.setLayout(layout);
		tablePanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		createButtons(tablePanel);
		createTable(tablePanel);

		getFontRegistry().addListener(this);

	}
	
	/**
	 * Updates the state of the button 
	 * @param button
	 * @param command
	 */
	private void updateButton(Button button, ITranslationCommand command) {
			button.setVisible(command.isVisible());
			button.setEnabled(command.isEnabled());
			button.setText(command.getName());
			button.setToolTipText(command.getToolTip());
			// force a redraw of the button to adapt the to new new text
			button.getParent().pack();
		}

	/**
	 * Update the state of all buttons
	 */
	private void updateButtons() {
		if (buttonsPanel != null) {
			updateButton(getButton(TranslationTableConstants.EDIT_ID), editCommand);
			updateButton(getButton(TranslationTableConstants.REMOVE_ID), removeCommand);
		}
	}

	/**
	 * Returns the button created by the method <code>createButton</code> for
	 * the specified ID as defined on <code>TranslationTableConstants</code>. If
	 * <code>createButton</code> was never called with this ID this method will
	 * return <code>null</code>.
	 * 
	 * @param id
	 *            the id of the button to look for
	 * 
	 * @return the button for the ID or <code>null</code>
	 * 
	 * @see #createButton(Composite, int, String)
	 */
	protected final Button getButton(int id) {
		return buttons.get(id);
	}

	
	public boolean isRichText() {
		return richTextStatus;
	}
	/**
	 * Notifies that the edit button has been pressed.
	 * <p>
	 * Subclasses may override if desired.
	 * </p>
	 */
	protected void editPressed() {
		if (!getModel().isReadOnly()) {
			Shell shell = new Shell();
			try {
				BaseTranslationDialog dialog;
				if (getModel().acceptRichText()) {
					dialog = new RichTextTranslationDialog(shell, getModel(), getPreferredFont());
				} else {
					dialog = new TranslationDialog(shell, getModel(), getPreferredFont());
				}
				
				if (Window.OK == dialog.open()) {
					try {
						editCommand.execute(dialog.getText());
					} catch (CoreException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			} finally{
				shell.dispose();
			}
		}

	}
	
	/**
	 * Notifies that the remove button has been pressed.
	 * <p>
	 * Subclasses may override if desired.
	 * </p>
	 */
	protected void removePressed() {
		try {
			removeCommand.execute("");
		} catch (CoreException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	/**
	 * Notifies that this button with the given id has been pressed.
	 * <p>
	 * The method calls
	 * <code>editPressed</code> if the edit button is the pressed, and
	 * <code>removePressed</code> if the remove button is the pressed. All
	 * other button presses are ignored. Subclasses may override to handle other
	 * buttons, but should call <code>super.buttonPressed</code> if the
	 * default handling of the edit and remove buttons is desired.
	 * </p>
	 * 
	 * @param buttonId
	 *            the id of the button that was pressed (see
	 *            <code>TranslationTableConstants.*_ID</code> constants)
	 */
	protected void buttonPressed(int buttonId) {
		if (TranslationTableConstants.EDIT_ID == buttonId) {
			editPressed();
		} else if (TranslationTableConstants.REMOVE_ID == buttonId) {
			removePressed();
		} 
	}

	/**
	 * Creates a new button with the given id.
	 * <p>
	 * The method creates a standard push button, registers it for selection
	 * events including button presses. The button id is stored as the button's
	 * client data. The new button will be accessible from
	 * <code>getButton(int)</code>. Note that the parent's layout is assumed to
	 * be a <code>GridLayout</code>. Subclasses may override.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite
	 * @param id
	 *            the id of the button (see
	 *            <code>TranslationTableConstants.*_ID</code> constants for
	 *            standard dialog button ids)
	 * 
	 * @return the new button
	 * 
	 * @see #getButton(int)
	 */
	protected void createButton(Composite parent, int id) {
		Button button = new Button(parent, SWT.PUSH);
		//button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});
		buttons.put(id, button);
	}
	
	protected final ITranslationCommandProvider getTranslationCommandProvider(ITranslation translation) {
		if (null == commandProvider) {
			commandProvider = new TranslationTableManager(project).getTranslationCommandProvider(translation);
		}
		return commandProvider;
	}
	
	protected final ITranslationTableContentProvider getTranslationTableContentProvider() {
		if (null == tableContentProvider) {
			tableContentProvider = createTranslationTableContentProvider();
		}
		return tableContentProvider;
	}

	protected final ITranslationTableLabelProvider getTranslationTableLabelProvider() {
		if (null == tableLabelProvider) {
			tableLabelProvider = createTranslationTableLabelProvider();
		}
		return tableLabelProvider;
	}

	protected final ITranslationTableCellModifier getTranslationTableCellModifier() {
		if (null == translationCellModifier) {
			translationCellModifier = createTranslationTableCellModifier();
		}
		return translationCellModifier;
	}	

	protected abstract void hookTable(TableViewer viewer);

	protected abstract ITranslationTableCellModifier createTranslationTableCellModifier();

	protected abstract ITranslationTableContentProvider createTranslationTableContentProvider();

	protected abstract ITranslationTableLabelProvider createTranslationTableLabelProvider();

	/**
	 * @param event
	 */
	protected void handleSelectionChange(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (selection != null) {
			Locale locale = (Locale) selection.getFirstElement();
			getModel().selectLocale(locale);
		}
		updateButtons();
	}

	/**
	 * @param event
	 */
	protected void handleDoubleClick(DoubleClickEvent event) {
		editPressed();
	}	
	
	/**
	 * Updates the table selection. If nothing is selected then attempt to
	 * select the first row.
	 */
	protected void updateSelection() {
		TableViewer tv = getViewer();
		if (tv != null) {
			tv.getTable().deselectAll();
			ISelection selection = tv.getSelection();
			if (selection != null && selection instanceof IStructuredSelection) {
				// if (((IStructuredSelection) selection).isEmpty()) {
				Locale selectedLocale = getModel().getSelectedLocale();
				for (TableItem item : tv.getTable().getItems()) {
					Locale locale = (Locale) item.getData();
					if (locale.equals(selectedLocale)) {
						int index = tv.getTable().indexOf(item);
						tv.getTable().select(index);
						break;
					}
				}
				// }
			} else {
				if (tv.getTable().getItemCount() > 0) {
					tv.getTable().select(0);
				}
			}
		}
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(TRANSLATION_FONT_ID)) {
			TableViewer tv = getViewer();
			if (tv != null && !tv.getControl().isDisposed()) {
				tv.getTable().setFont(getPreferredFont());
			}
		}
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(java.beans.PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		if (ITranslationModel.TRANSLATION_KIND_PROPERTY.equals(propertyName)) {
			getViewer().refresh(true);
			updateSelection();
		}
		updateButtons();
	}

	@Override
	public void hideButtons() {
		if (tablePanel != null) {
			GridData tableLayoutData = (GridData)tablePanel.getLayoutData();
			tableLayoutData.exclude = true;
			tablePanel.setVisible(false);
		}
	}

	@Override
	public void showButtons() {
		if (null != tablePanel) {
			GridData tableLayoutData = (GridData)tablePanel.getLayoutData();
			tableLayoutData.exclude = false;
			tablePanel.setVisible(true);
		}
	}

	@Override
	public void setTranslationModel(ITranslationModel newModel) {
		
		if (null == newModel) {
			throw new IllegalArgumentException("Argument [model] cannot be bull");
		}
		
		if (this.model != null) {
			this.model.removePropertyChangeListener(this);
		}
		this.model = newModel;
		
		ITranslation translation = this.model.getTranslation();
		
		if (this.editCommand != null) {
			this.editCommand.release();
		}
		this.editCommand = getTranslationCommandProvider(translation).getEditCommand(this.model);

		if (this.removeCommand != null) {
			this.removeCommand.release();
		}
		this.removeCommand = getTranslationCommandProvider(translation).getRemoveCommand(this.model);

		ITranslationTableCellModifier tcm = getTranslationTableCellModifier();
		if (tcm != null) {
			tcm.setTranslationModel(getModel());
			tcm.setCommand(this.editCommand);
		}

		ITranslationTableLabelProvider tlp = getTranslationTableLabelProvider();
		if (tlp != null) {
			tlp.setTranslationModel(getModel());
		}

		this.model.addPropertyChangeListener(this);

		getViewer().setInput(this.model);
		updateSelection();
		updateButtons();
	}

	@Override
	public void dispose() {
		
		getFontRegistry().removeListener(this);
		
		if (this.editCommand != null) {
			this.editCommand.release();
		}

		if (this.removeCommand != null) {
			this.removeCommand.release();
		}

		if (this.model != null) {
			this.model.removePropertyChangeListener(this);
		}
	}

	/**
	 * @param parent
	 */
	public TranslationTable(IProject project, Composite parent) {
		this.project = project;
		createControls(parent);
	}

}
