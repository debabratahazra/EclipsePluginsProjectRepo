package com.odcgroup.page.ui.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.ui.action.event.CopyEventToClipboardAction;
import com.odcgroup.page.ui.action.event.PasteEventFromClipboardAction;
import com.odcgroup.page.ui.command.AddWidgetEventCommand;
import com.odcgroup.page.ui.command.DeleteWidgetEventCommand;
import com.odcgroup.page.ui.command.ReplaceWidgetEventCommand;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.dialog.EventDialogFactory;
import com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.util.AdaptableUtils;
/**
 * An instance of EventSection help to manage widget's events
 * 
 * @author atr
 */
public class EventSection extends AbstractPagePropertySection implements KeyListener {

	/** The selected widget. */
	protected Widget widget;

	/** The event table of the selected widget. */
	protected Table eventsTbl;

	/** The modify event section action. */
	protected Button modifyBtn;

	/** The delete event section action. */
	protected Button deleteBtn;

	/** The create event section action. */
	protected Button createBtn;
	
	/** The Label for the Action event. */
	private CLabel actionLabel;

	/** The text for the Action event. */
	private Text actionText;
	
	
	
	/**
	 * Gets the PropertyCategory of the NewEventSection.
	 * 
	 * @return PropertyCategory The property category used
	 */
	protected PropertyCategory getPropertyCategory() {
		return PropertyCategory.EVENT_LITERAL;
	}

	/**
	 * Notifies the section that the workbench selection has changed.
	 * 
	 * @param part
	 *            The active workbench part.
	 * @param selection
	 *            The active selection in the workbench part.
	 * 
	 * Implementation note: transmit the category to the selection only if it's
	 * an instance of a IWidgetPropertySource.
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object input = ((IStructuredSelection) selection).getFirstElement();
			IWidgetPropertySource wps = (IWidgetPropertySource) AdaptableUtils
					.getAdapter(input, IWidgetPropertySource.class);

			// notify the property source the current category of widget properties
			wps.setCurrentPropertyCategory(getPropertyCategory());
			widget = wps.getWidget();
			refillWidgetEventsList();
			
		}
		
		enableButtons();
		
		if (!hasAction()) {
			actionText.setVisible(false);
			actionLabel.setVisible(false);
			actionText.setText("");
		} else {
			actionText.setVisible(true);
			actionLabel.setVisible(true);
			actionText.setText(widget.getPropertyValue(PropertyTypeConstants.ACTION));
		}

		super.setInput(part, selection);
	}
	
	/**
	 * @param widget
	 * @return {@code true} if the widget can have events
	 */
	private final boolean supportsEvents(Widget widget) {
		return MetaModelRegistry.supportsEvents(widget.getType());		
	}
	
	/**
	 * Enables / disables the buttons depending upon the currently selected Widget / Events.
	 */
	private void enableButtons() {
	    createBtn.setEnabled(supportsEvents(widget));
	    
	    int nbSelected = eventsTbl.getSelectionCount();
	    
        deleteBtn.setEnabled(nbSelected > 0);
        modifyBtn.setEnabled(nbSelected == 1);
	}

	/**
	 * Executes the delete widget event command.
	 * 
	 * @param events
	 *            The events to delete
	 */
	public void executeDeleteEventsCommand(List<Event> events) {
	    CompoundCommand cc = new CompoundCommand();
	    for (Event e : events) {
	        Command c = new DeleteWidgetEventCommand(widget, e);
		    cc.add(c);
	    }
		CommandStack cs = getCommandStack();
		cs.execute(cc);
	}
	
    /**
     * Executes the create widget event command.
     * 
     * @param event The event to create
     */
    private void executeCreateEventCommand(Event event) {
        Command command = new AddWidgetEventCommand(widget, event);
        getCommandStack().execute(command);
    }
    
    /**
     * Executes the modify event command. Note that the EventDialog creates a copy of the event that
     * it receives therefore when we modify an event we are really deleting the original event and
     * replacing it by a new one.
     * 
     * @param oldEvent The original event
     * @param newEvent The new event
     */
    private void executeModifyEventCommand(Event oldEvent, Event newEvent) {
        ReplaceWidgetEventCommand c = new ReplaceWidgetEventCommand(oldEvent, newEvent);               
        getCommandStack().execute(c);
    }
    
    /**
     * Handler to create a new event
     */
    private void handleCreateEvent() {
		IPageEventDefinitionDialog dialog = getEventDialog(getShell(), widget, null);
		int code = dialog.open();	
		if (code == Window.OK) {
		    executeCreateEventCommand(dialog.getDefinedEvent());
		}
		refillWidgetEventsList();
		enableButtons();
    }
    
    /**
     * Handler to delete the selected events
     */
    private void handleDeleteEvents() {
		boolean okpressed = MessageDialog.openConfirm(getShell(), 
				"Confirm",
				"Are you sure you want to delete the selection?");
		if (okpressed && eventsTbl.getSelectionCount() > 0) {
			List<Event> toDelete = new ArrayList<Event>();
			int[] indices = eventsTbl.getSelectionIndices();
			for (int kx = indices.length - 1; kx >= 0; kx--) {
				Event event = widget.getEvents().get(indices[kx]);
				if (event != null) {
					toDelete.add(event);
				}
			}
			if (toDelete.size() > 0) {
				executeDeleteEventsCommand(toDelete);
				refillWidgetEventsList();
			}
		}
		enableButtons();
    }
    
    /**
     * Handler to copy the selected event to the clipboard
     */
    private void handleCopyEvent() {
    	if (eventsTbl.getSelectionCount() == 1) {
			int index = eventsTbl.getSelectionIndex();
			Event event = widget.getEvents().get(index);
			Clipboard.getDefault().setContents(EcoreUtil.copy(event));
    	}
    }
    
    /**
     * @param event the event to add
     */
    private void handlePasteEvent(Event event) {
	    String eventTypeName = event.getEventType().getName();
		if (MetaModelRegistry.hasEventType(widget.getType(), eventTypeName)) {
	        executeCreateEventCommand(event);
			refillWidgetEventsList();
		} else {
			MessageDialog.openError(getShell(), 
					"Event Paste Error", "The selected widget does not support ["+eventTypeName+"] Event");
		}
		enableButtons();
    }
    
    
    /**
     * Handler to paste an event from the clipboard
     */
    protected void handlePasteEvent() {
		if (supportsEvents(widget)) {
			Object obj = (Clipboard.getDefault().getContents());
			if (obj instanceof Event && (widget.getEvents().indexOf((Event) obj) == -1)) {
				// no paste if the event is already in the collection.
				handlePasteEvent(EcoreUtil.copy((Event)obj));
			}
		}
    }

    /**
     * Handler to modify the selected event
     */
    private void handleModifyEvent() {
	    Event event = getSelectedEvent();
	    if (event != null) {
	    	IPageEventDefinitionDialog dialog = getEventDialog(getShell(), widget, event);		    
		    int code = dialog.open();   
		    if (code == Window.OK) {
		        // New event is a copy of the original event
		        Event newEvent = dialog.getDefinedEvent();
		        executeModifyEventCommand(event, newEvent);
		    }
		    refillWidgetEventsList();
		}
        enableButtons();
    }

    /**
	 * @param selection
	 * @return a list of actions given a selection
	 */
	protected List<Action> getActions(TableItem [] selection) {
		List<Action> actions = new ArrayList<Action>();
		if (selection.length == 1) {
			int index = eventsTbl.getSelectionIndex();
			Event event = widget.getEvents().get(index);
			actions.add(new CopyEventToClipboardAction(event){
				protected void doCopyEvent(Event event) {
					Clipboard.getDefault().setContents(WidgetCopier.copy(event));
				}
			});
		}
		if (supportsEvents(widget)) {
			Object obj = Clipboard.getDefault().getContents();
			if (obj instanceof Event && (widget.getEvents().indexOf((Event) obj) == -1)) {
				// no paste if the event is already in the collection.
				actions.add(new PasteEventFromClipboardAction((Event) obj){
					protected void doPasteEvent(Event event) {
						handlePasteEvent();
					}
				});
			}
		}
		return actions;
	}
    
	/**
	 * This method install a popup menu manager for the events table
	 */
	private void installMenus() {
		final MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				for (Action action : getActions(eventsTbl.getSelection())) {
					mgr.add(action);
				}
			}
		});
		eventsTbl.setMenu(mgr.createContextMenu(eventsTbl));

	}    
    
    /**
     * Executes the modify widget event command.
     * 
     * @param widget
     *            The widget to add
     * @param action
     *            The new action
     */
    private void executeModifyActionCommand(Widget widget, String action) {
        if (!hasAction()) {
            return;
        }

        Property p = widget.findProperty(PropertyTypeConstants.ACTION);

        if (StringUtils.equals(p.getValue(), action)) {
            return;
        }

        Command command = new UpdatePropertyCommand(p, action);
        CommandStack cs = getCommandStack();
        cs.execute(command);
    } 
	
    
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite comp = factory.createFlatFormComposite(parent);
		GridLayout gd = new GridLayout();
		gd.verticalSpacing = 1;
		gd.marginHeight = 3;
		comp.setLayout(gd);
		
		Composite actionBody = factory.createFlatFormComposite(comp);
		gd = new GridLayout();
		gd.verticalSpacing = 1;
		actionBody.setLayout(gd);
		gd.numColumns = 3;
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		actionBody.setLayoutData(gridData);

		actionLabel = factory.createCLabel(actionBody, "Action:");
		gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		actionLabel.setLayoutData(gridData);

		actionText = factory.createText(actionBody, "");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		actionText.setLayoutData(gridData);
		actionText.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String s = actionText.getText();
				s = (s == null) ? "" : s;
				if (widget != null) {
				    executeModifyActionCommand(widget, s);
				}
			}
		});
		
		CLabel filler = factory.createCLabel(actionBody, ""); 
		gridData = new GridData();
		gridData.widthHint = 60;
		filler.setLayoutData(gridData);

		Composite body = factory.createFlatFormComposite(comp);
		gd = new GridLayout();
		gd.verticalSpacing = 1;
		gd.marginHeight = 1;
		body.setLayout(gd);
		gd.numColumns = 3;
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessHorizontalSpace = true;
		body.setLayoutData(gridData);
		
		// Events label
		CLabel eventsLabel = factory.createCLabel(body, "Events:");
		gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gridData.minimumWidth = 200;
		eventsLabel.setLayoutData(gridData);
		// Events table
		eventsTbl = factory.createTable(body, SWT.MULTI | SWT.FULL_SELECTION);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessHorizontalSpace = true;
		eventsTbl.setLayoutData(gridData);
		eventsTbl.setHeaderVisible(true);	
		addColumn(eventsTbl, "Event", 90);
		addColumn(eventsTbl, "Function", 90);
		addColumn(eventsTbl, "Description", 500);

		eventsTbl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				enableButtons();									
			}
		});
		
		eventsTbl.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent event) {
					handleModifyEvent();
			}
		});
		
		// add key listener for specific key pressed events
		eventsTbl.addKeyListener(this);

		installMenus();		

		// The buttons container
		Composite buttonContainer = factory.createFlatFormComposite(body);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		buttonContainer.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.widthHint = 60;
		buttonContainer.setLayoutData(gridData);
		
		createBtn = factory.createButton(buttonContainer, "Create", SWT.PUSH);
		createBtn.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		createBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleCreateEvent();
			}
		});

		deleteBtn = factory.createButton(buttonContainer, "Delete", SWT.PUSH);
		deleteBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDeleteEvents();
			}
		});

		modifyBtn = factory.createButton(buttonContainer, "Modify", SWT.PUSH);		
		modifyBtn.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		modifyBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleModifyEvent();
			}
		});
		
	}
	
	/**
	 * @param shell
	 * @param widget
	 * @param event
	 * @return IPageEventDefinitionDialog
	 */
	private IPageEventDefinitionDialog getEventDialog(Shell shell, Widget widget, Event event) {
		return EventDialogFactory.getInstance().createEventDialog(shell, widget, event);
	}
	
	/**
	 * Gets the selected event.
	 * 
	 * @return Event
	 */
	private Event getSelectedEvent() {
		Event event = null;
		int index = eventsTbl.getSelectionIndex();
		if (index != -1) {
			event = widget.getEvents().get(index);
		}
		return event;
	}
	
	/**
	 * Gets the CommandStack.
	 * 
	 * @return CommandStack
	 */
	private CommandStack getCommandStack() {
	    return (CommandStack) getPart().getAdapter(CommandStack.class);
	}
	
	/**
	 * Gets the Shell.
	 * 
	 * @return Shell
	 */
	private Shell getShell() {
	    return Display.getCurrent().getActiveShell();
	}

	/**
	 * @return true
	 */
	public boolean shouldUseExtraSpace() {
		return true;
	}	

	/**
	 * Fill the table with the widget events properties.
	 */
	private void refillWidgetEventsList() {
		eventsTbl.removeAll();
		for (Event ev : widget.getEvents()) {
			new TableItem(eventsTbl, SWT.NONE).setText(new String[] {
					ev.getEventName(), ev.getFunctionName(),
					ev.getDescription(),
					});
		}
	}

    /**
     * Add a column to the specified table.
     * 
     * @param table
     *            The selected table
     * @param name
     *            The name of the column
     * @param width
     *            The width of the column
     */
    private void addColumn(Table table, String name, int width) {
        final TableColumn tc = new TableColumn(table, SWT.LEFT);
        tc.setText(name);
        tc.setWidth(width);
    }

	/**
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.character == SWT.DEL) {
			handleDeleteEvents();
		} else if (e.keyCode == SWT.INSERT) { 
			handleCreateEvent();
		} else if (e.keyCode == SWT.CTRL) {
			if (e.character == 'c' || e.character == 'C') {
				handleCopyEvent();
			}
			if (e.character == 'v' || e.character == 'V') {
				handlePasteEvent();
			}
		}
	}

	/**
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		// do nothing
	}		

	/**
	 * Returns true if the Widget has the Action property.
	 * 
	 * @return boolean
	 */
	private boolean hasAction() {
		if (!widget.getTypeName().equals(WidgetTypeConstants.ATTRIBUTE)) {
			return false;
		}
		return widget.findProperty(PropertyTypeConstants.ACTION) != null;
	}

}
