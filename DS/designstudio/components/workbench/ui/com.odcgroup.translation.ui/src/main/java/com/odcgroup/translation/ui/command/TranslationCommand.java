package com.odcgroup.translation.ui.command;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * This is the base class for all translation commands.
 * <p>
 * It listen to the ITranslationModel changes and update its state accordingly.
 * 
 * @author atr
 */
public abstract class TranslationCommand implements ITranslationCommand, PropertyChangeListener {

	/**
	 * @return the active editpart
	 */
	protected static IEditorPart getActiveEditorPart() {
		IEditorPart editorPart = null;
		IWorkbench iworkbench = PlatformUI.getWorkbench();
		if (iworkbench != null) {
			IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
			if (iworkbenchwindow != null) {
				IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
				editorPart = iworkbenchpage.getActiveEditor();
			}
		}
		return editorPart;
	}

	private boolean busy = false;
	private boolean enabled = false;
	private String inheritedInformation = "";
	private String inheritedName = "";
	private String inheritedToolTip = "";

	private ITranslationModel model;
	private String name = "";
	private String notInheritedInformation = "";
	private String notInheritedName = "";
	private String notInheritedToolTip = "";
	private String standardName = "";
	private String standardToolTip = "";
	private String toolTip = "";

	private boolean visible = false;

	protected final String getInheritedInformation() {
		return inheritedInformation;
	}

	protected final String getInheritedName() {
		return inheritedName;
	}

	protected final String getInheritedToolTip() {
		return inheritedToolTip;
	}
	
	protected final ITranslationModel getModel() {
		return this.model;
	}

	protected final String getNotInheritedInformation() {
		return notInheritedInformation;
	}

	protected final String getNotInheritedName() {
		return notInheritedName;
	}

	protected final String getNotInheritedToolTip() {
		return notInheritedToolTip;
	}

	protected final String getStandardName() {
		return standardName;
	}

	protected final String getStandardToolTip() {
		return standardToolTip;
	}

	/**
	 * Intializes all texts
	 */
	protected abstract void initializeTexts();

	protected final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	protected final void setInheritedInformation(String text) {
		inheritedInformation = text;
	}

	protected final void setInheritedName(String text) {
		inheritedName = text;
	}

	protected final void setInheritedToolTip(String text) {
		inheritedToolTip = text;
	}

	protected final void setName(String name) {
		this.name = name;
	}

	protected final void setNotInheritedInformation(String text) {
		notInheritedInformation = text;
	}

	protected final void setNotInheritedName(String text) {
		notInheritedName = text;
	}

	protected final void setNotInheritedToolTip(String text) {
		notInheritedToolTip = text;
	}

	protected final void setStandardName(String text) {
		standardName = text;
	}

	protected final void setStandardToolTip(String text) {
		standardToolTip = text;
	}

	protected final void setToolTipText(String toolTip) {
		this.toolTip = toolTip;
	}

	protected final void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Updates the states of this command given the model
	 * 
	 * @param model
	 *            the translation model
	 */
	protected abstract void updateCommandState(ITranslationModel model);

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return toolTip;
	}

	@Override
	public final boolean isEnabled() {
		return enabled;
	}

	@Override
	public final boolean isVisible() {
		return visible;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (ITranslationModel.TRANSLATION_INFO_PROPERTY.equals(event.getPropertyName())) {
			// to avoid a stackoverflow
			return;
		}
		if (busy)
			return;
		busy = true;
		visible = false;
		enabled = false;
		updateCommandState(model);
		busy = false;
	}

	@Override
	public void release() {
		this.model.removePropertyChangeListener(this);
	}

	/**
	 * Constructs a new TranslationCommand given a model
	 */
	public TranslationCommand(ITranslationModel model) {
		if (null == model) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.model = model;
		this.model.addPropertyChangeListener(this);
		initializeTexts();
		updateCommandState(model);
	}

}
