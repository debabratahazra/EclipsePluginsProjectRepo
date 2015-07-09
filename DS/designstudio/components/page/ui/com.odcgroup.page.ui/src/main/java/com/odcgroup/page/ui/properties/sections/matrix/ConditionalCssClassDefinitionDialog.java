package com.odcgroup.page.ui.properties.sections.matrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.page.model.widgets.matrix.IConditionalCssClass;
import com.odcgroup.page.model.widgets.matrix.ICssClass;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.matrix.RemoveCssConditionCommand;

/**
 * @author pkk
 *
 */
public class ConditionalCssClassDefinitionDialog extends TitleAreaDialog {
	
	private ICssClass cssClass;
	private CommandStack commandStack;
	
	private static Image ADD_FS = createImage("/icons/add.png"); //$NON-NLS-1$
	private static final String CONDITION_TAB = "condition";
	private Map<String, Composite> tabMap = new HashMap<String, Composite>();
    /** tab folder for the event tabs */
    private CTabFolder tabFolder;

	/**
	 * @param cssClass
	 * @param parentShell
	 * @param commandStack
	 */
	public ConditionalCssClassDefinitionDialog(ICssClass cssClass, Shell parentShell, CommandStack commandStack) {
		super(parentShell);
		this.commandStack = commandStack;
		this.cssClass = cssClass;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Css class");
		setMessage("Specify the css class to apply under a condition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Css class");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {		
		Composite top = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);		
		top.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 450;
		top.setLayoutData(gridData);		
		// top bar
		createTopBar(top);
		// create tabbed-pane
		createTabbedPaneSection(top);
		
		return top;
	}
	

	
	/**
	 * @param body
	 * @param name
	 * @param required
	 * @param index
	 * @return
	 */
	private Composite createConditionComp(String name, Composite body, IConditionalCssClass condition, boolean required) {
		Composite paramGroup = new Composite(body, SWT.FILL | SWT.BORDER);
        GridLayout gridLayout = new GridLayout(1, false);
		paramGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
        paramGroup.setLayoutData(gridData);        
        CssConditionControl conditionControl= new CssConditionControl(cssClass, paramGroup, condition, required) {
        	public void executeCommand(BaseCommand command) {
        		commandStack.execute(command);
        	}
        };
        tabMap.put(name, conditionControl);
        return paramGroup;
	}
	
	/**
	 * @param comp
	 */
	private void createTabbedPaneSection(Composite comp) {
		
		tabFolder = new CTabFolder(comp, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
		
		// add condition tabs
		List<IConditionalCssClass> conditions =  cssClass.getConditionalCssClasses();
		Composite conditionControl;
		for (IConditionalCssClass condition : conditions) {
			int index = conditions.indexOf(condition);
			CTabItem fsItem = new CTabItem(tabFolder, SWT.CLOSE);
			String tabID = CONDITION_TAB+"("+index+")";
			fsItem.setText(condition.getName().getValue());
			fsItem.setData(tabID);
			conditionControl =  createConditionComp(tabID, tabFolder, condition, true);
			fsItem.setControl(conditionControl);	
		}
		
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				CTabItem tabItem = (CTabItem)event.item;
				CTabFolder folder = (CTabFolder)event.getSource();
				int fsIndex = -1;
				for (CTabItem tab : folder.getItems()) {
					fsIndex++;
					if (tab == tabItem) {
						handleCloseConditionTab(fsIndex);
					}
				}
			}
		});
		if (!conditions.isEmpty()) {
			tabFolder.setSelection(tabFolder.getItem(0));
		}
	}
	
	/**
	 * The user has just close the condition tab. Removes the corresponding
	 * Condition from the cssClass.
	 *  
	 * @param index of the filterSet to be removed.
	 */
	private void handleCloseConditionTab(int index) {
		List<IConditionalCssClass> conditions =  cssClass.getConditionalCssClasses();
		if (!conditions.isEmpty()) {
			IConditionalCssClass condition = conditions.get(index);
			BaseCommand cmd = new RemoveCssConditionCommand(cssClass, condition);
			commandStack.execute(cmd);
		}
	}
	
	/**
	 * creates a an add condition button
	 * 
	 * @param body
	 */
	protected void createTopBar(Composite body) {	
		
		Composite composite = new Composite(body, SWT.NONE);
    	GridLayout layout = new GridLayout(2, false);
    	layout.horizontalSpacing = 0;
    	layout.verticalSpacing = 0;
    	layout.marginHeight = 0;
    	layout.marginLeft = 0;
    	composite.setLayout(layout);
    	composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    	composite.setFont(body.getFont());
    	
    	new Label(composite, SWT.NONE);    	

		Composite comp = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		comp.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		comp.setLayoutData(data);
		comp.setFont(composite.getFont());
		
		createAddConditionalClassButton(comp);
		
        data.grabExcessHorizontalSpace = true;
       
	}
	
	/*
     * Creates a button with a help image. This is only used if there
     * is an image available.
     */
	private ToolBar createAddConditionalClassButton(Composite parent) {
        ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS| SWT.RIGHT);
        ((GridLayout) parent.getLayout()).numColumns++;
		toolBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		final Cursor cursor = new Cursor(parent.getDisplay(), SWT.CURSOR_HAND);
		toolBar.setCursor(cursor);
		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				cursor.dispose();
			}
		});		
        ToolItem item = new ToolItem(toolBar, SWT.NONE );
		item.setImage(ADD_FS);
		item.setText("Add Condition"); //$NON-NLS-1$
		item.setToolTipText("Add new conditional class"); //$NON-NLS-1$
		item.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
            	handleAddFilterSetTab();
            }
        });
		return toolBar;
	}
	
	/**
	 * 
	 */
	private synchronized void handleAddFilterSetTab() {
    	int index = tabMap.size();
		CTabItem fsItem = new CTabItem(tabFolder, SWT.CLOSE);
		String tabID = CONDITION_TAB+"("+index+")";
		fsItem.setText(CONDITION_TAB);
		fsItem.setData(tabID);
		IConditionalCssClass condition = SnippetUtil.getSnippetFactory().createConditionalCssClass();
		fsItem.setControl(createConditionComp(tabID, tabFolder, condition, false));
		tabFolder.setSelection(fsItem);
	}
	
	/**
	 * @param name
	 * @return
	 */
	private static Image createImage(String name) {
		String id = PageUIPlugin.PLUGIN_ID;
		return PageUIPlugin.imageDescriptorFromPlugin(id, name).createImage();
	}

}
