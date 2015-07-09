package com.odcgroup.page.ui.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.impl.WidgetTransformerContextImpl;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacility;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.transformmodel.util.TransformModelRegistry;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.page.util.XmlUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * The Code Property Editor contains the code from the Property Code of the CodeWidget. Each time that the user changes
 * the editor to another one, Design / Preview etc. the code is parsed in order to determine if the EMF model is able to
 * recognize the XML.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class CodePropertyEditor extends StructuredTextEditor {

	/**
	 * The text to place before the Code Widget. This enables content-assist and auto-completion to work.
	 * At the moment we use only the xgui.dtd.
	 */
	private static final String BEFORE_CODE_TEXT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\""
				+ "<!DOCTYPE xgui:module PUBLIC \"-//ODYSSSEY/DTD XGUI\" \"xgui.dtd\">";

	/** The current selected CodeWidget. */
	private Widget selectedWidget;

	/** The Property containing the code that we display. This is null if the Property is not a Code Property. */
	private Property property;

	/** The selection listener. This enables us to know which CodeWidget has been selected. */
	private CodePageSelectionListener selectionListener;

	/** The command stack ensures that undo / redo works. ALL changes to the model are performed via the CommandStack. */
	private CommandStack commandStack;

	/** Listens to changes in the code Property from other views of the model. */
	private CodeChangeAdapter codeChangeAdapter;

	/**
	 * Creates a new CodePropertyEditor.
	 * 
	 * @param commandStack
	 *            The command stack
	 */
	public CodePropertyEditor(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	/**
	 * Gets the CodeChangeAdapter.
	 * 
	 * @return CodeChangeAdapter
	 */
	private CodeChangeAdapter getCodeChangeAdapter() {
		if (codeChangeAdapter == null) {
			codeChangeAdapter = new CodeChangeAdapter();
		}
		return codeChangeAdapter;
	}

	/**
	 * Gets the CommandStack.
	 * 
	 * @return CommandStack
	 */
	private CommandStack getCommandStack() {
		return commandStack;
	}

	/**
	 * Initialize the editor.
	 * 
	 * @param site
	 *            The editor site
	 * @param input
	 *            The editor input
	 */
	public void init(IEditorSite site, IEditorInput input) {
		this.setSite(site);
		this.setInput(input);
		selectionListener = new CodePageSelectionListener();

		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
	}

	/**
	 * Disposes the editor.
	 */
	public void dispose() {
		super.dispose();

		removeProperty();

		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
	}

	/**
	 * Saves the diagram.
	 */
	public void doSaveAs() {
		// Nothing to do. The MultiPageEditor is responsable for saving the model.
	}

	/**
	 * Saves the diagram.
	 * 
	 * @param progressMonitor
	 *            The monitor to display the progress of the save operation
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		// Nothing to do. The MultiPageEditor is responsible for saving the model.
	}

	/**
	 * Returns true if the command stack is dirty.
	 * 
	 * @return boolean
	 */
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	/**
	 * Returns false since the CodePropertyEditor is not responsible for saving the model.
	 * 
	 * @return boolean
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Removes the Property and detaches the CodeChangeAdapter.
	 */
	private void removeProperty() {
		if (property != null) {
			property.eAdapters().remove(getCodeChangeAdapter());
		}
		property = null;
	}

	/**
	 * Activates the editor.
	 */
	public void activateEditor() {
		String str = "";
		boolean readOnly = true;
		if (property != null) {
			String v = property.getValue();
			str = v == null ? "" : v;
			readOnly = false;
		}

		// We add text before and after the text of the code Widget. Within it we include
		// the xgui.dtd. This enables content-assist and auto-completion to work.
		// Afterwards we set the visible region of the editor to hide this text.
		String nodeName = findParentElementName();
		String before = "";
		String after = "";
		if (! StringUtils.isEmpty(nodeName)) {
			before = BEFORE_CODE_TEXT + "<" + nodeName + ">\n";
			after = "</" + nodeName + ">\n";
			str = before + str + after;
		}

		// create a new InputStreamEditorInput
		XSPInputStreamEditorInput oldInput = (XSPInputStreamEditorInput) getEditorInput();
		XSPInputStreamEditorInput newInput = new XSPInputStreamEditorInput(new ByteArrayInputStream(str.getBytes()), readOnly, oldInput.getOfsResource(), "CodePage");

		setInput(newInput);

		int bl = before.length();
		int al = after.length();
		// Set the visible region to contain only the text from the code widget
		getSourceViewer().setVisibleRegion(bl, str.length() - bl - al);
	}
	
	/**
	 * Finds the node name of the Element that the parent Widget will create.
	 * This can be treated as the "root" element for the contents of the code Widget.
	 * It is used by the content-assist and auto-complete facilities.
	 * 
	 * @return String of type xgui:hbox etc.
	 */
	private String findParentElementName() {
		if (property == null) {
			return "";
		}
		Widget parent = property.getWidget().getParent();

		IOfsProject ofsProject = EclipseUtils.findCurrentProject();
		NamespaceFacility facility = NamespaceFacilityUtils.getNamespaceFacility(ofsProject);
		TransformModel tm = TransformModelRegistry.getTransformModel();
		facility.addUserDefinedNamespaces(tm);
		
		final boolean namespaceAware = true;
		Document document = XmlUtils.createDocument(namespaceAware);
		
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetTransformerContext context = new WidgetTransformerContextImpl(mm, tm, document, ofsProject.getProject());
		WidgetTransformer wt = tm.findWidgetTransformer(parent);
		Element e = wt.getParentElement(context, parent);
		if (e == null) {
			return "";
		}
		return e.getNodeName();
	}

	/**
	 * Save the actual code.
	 */
	public void disactivateEditor() {
		if (property != null) {

			// Note that we do not use the Document since it includes the hidden text needed for auto-completion to work.
			String text = getTextViewer().getTextWidget().getText();
			if (!ObjectUtils.equals(property.getValue(), text)) {
				UpdatePropertyCommand command = new UpdatePropertyCommand(property, text);
				getCommandStack().execute(command);
			}
		}

	}

	/**
	 * Detects when a different Widget is selected and updates the code editor.
	 * 
	 * @author Gary Hayes
	 */
	private class CodePageSelectionListener implements ISelectionListener {

		/**
		 * The event that occurs when a new item is selected.
		 * 
		 * @param part
		 *            The IWorkbenchPart
		 * @param selection
		 *            The ISelection
		 */
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (selection instanceof TextSelection) {
				// This is fired when the page is activated. We need to ignore this
				// and remember the old selection.
				return;
			}

			if (!(selection instanceof IStructuredSelection)) {
				// Does not interest us
				removeProperty();
				return;
			}

			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() != 1) {
				// We can only edit one Code Property at a time
				removeProperty();
				return;
			}

			Object obj = structuredSelection.getFirstElement();
			Widget newSelectedWidget = (Widget) AdaptableUtils.getAdapter(obj, Widget.class);
			if (newSelectedWidget == null) {
				// This is not a Widget
				removeProperty();
				return;
			}

			if (selectedWidget == newSelectedWidget) {
				// They have selected the same Widget continue displaying the same code
				return;
			}

			selectedWidget = newSelectedWidget;

			Property p = selectedWidget.findProperty(PropertyTypeConstants.CODE);
			if (p == null) {
				// This is not a Code Widget since it does not have a Code Property
				removeProperty();
				return;
			}

			removeProperty();
			p.eAdapters().add(getCodeChangeAdapter());
			property = p;
		}
	}

	/**
	 * Listens to changes to the value of the code Property.
	 * 
	 * @author Gary Hayes
	 */
	private class CodeChangeAdapter implements Adapter {

		/** The target. */
		private Notifier target;

		/**
		 * Gets the target.
		 * 
		 * @return Notifier
		 */
		public Notifier getTarget() {
			return target;
		}

		/**
		 * Returns true if this adapter is adapted to the type.
		 * 
		 * @param type
		 *            The type to test
		 * @return true If this is the adapter for the type
		 */
		public boolean isAdapterForType(Object type) {
			return (type instanceof Property);
		}

		/**
		 * Called when the code Property is changed.
		 * 
		 * @param notification
		 *            The Notification
		 */
		public void notifyChanged(Notification notification) {
			if (notification.getNewStringValue() != null) {
				String str = notification.getNewStringValue();
				String value = str == null ? "" : str;

				// Update the contents of the InputStreamEditorInput
				XSPInputStreamEditorInput input = (XSPInputStreamEditorInput) getEditorInput();
				InputStream is = new ByteArrayInputStream(value.getBytes());
				input.setContents(is);
				input.setReadOnly(input.isReadOnly());

				// Finally update the user-interface
				if (getEditorInput() != null) {
					setInput(getEditorInput());
				}
			}

		}

		/**
		 * Sets the target.
		 * 
		 * @param newTarget
		 *            The target
		 */
		public void setTarget(Notifier newTarget) {
			this.target = newTarget;
		}
	}
}
