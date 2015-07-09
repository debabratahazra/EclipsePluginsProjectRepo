package com.odcgroup.workbench.editors.properties.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.odcgroup.workbench.editors.properties.controls.ListReferencePropertyControl;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

/**
 * @author pkk
 *
 */
public class SectionListReferencePropertyHelper implements SectionPropertyHelper {
	
	protected String label;
	protected ListReferencePropertyControl control;
	protected EFactory factory;
	protected EClass refClass;
	protected EReference reference;
	protected EStructuralFeature parentRef;
	protected String tooltip;
	protected boolean sorter = false;
	protected List<ListReferenceColumn> columns;
	protected String tab;
	protected boolean editable = true;
	protected boolean multiSelect = false;
	protected AbstractTitleAreaDialog popupDialog = null;
	protected List<ListMenuAction> actions = new ArrayList<ListMenuAction>();
	protected boolean copyAction = false;
	protected boolean displayLabel = true;
	
	/**
	 * @param label
	 * @param refObject
	 * @param reference
	 */
	public SectionListReferencePropertyHelper(String label, String tabName, EReference reference, EStructuralFeature parentRef, EFactory factory, EClass refClass){
		this(label, reference, parentRef, factory, refClass);
		this.tab = tabName;
	}
	
	/**
	 * @param label
	 * @param refObject
	 * @param reference
	 */
	public SectionListReferencePropertyHelper(String label, EReference reference, EStructuralFeature parentRef, EFactory factory, EClass refClass){
		this.label = label;
		this.factory = factory;
		this.refClass = refClass;
		this.reference = reference;
		this.parentRef = parentRef;	
	}	
	
	/**
	 * @param label
	 * @param reference
	 * @param parentRef
	 * @param factory
	 * @param refClass
	 * @param sorter
	 */
	public SectionListReferencePropertyHelper(String label, EReference reference, EStructuralFeature parentRef, EFactory factory, EClass refClass, boolean sorter){
		this(label, reference, parentRef, factory, refClass);
		this.sorter = sorter;
	}
	
	/**
	 * @param label
	 * @param reference
	 * @param parentRef
	 * @param factory
	 * @param refClass
	 * @param sorter
	 * @param displayLabel
	 */
	public SectionListReferencePropertyHelper(String label, EReference reference, EStructuralFeature parentRef, EFactory factory, EClass refClass, boolean sorter, boolean displayLabel){
		this(label, reference, parentRef, factory, refClass, sorter);
		this.displayLabel = displayLabel;
	}
	
	public ListReferencePropertyControl getControl() {
		return control;
	}
	
	public AbstractTitleAreaDialog getPopuopDialog() {
		return popupDialog;
	}
	/**
	 * @return
	 */
	public EObject getRefObject() {
		return factory.create(refClass);
	}
	/**
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	public EReference getReference() {
		return reference;
	}

	public EStructuralFeature getParentRef() {
		return parentRef;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isSorter() {
		return sorter;
	}

	public void setSorter(boolean sorter) {
		this.sorter = sorter;
	}

	public List<ListReferenceColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ListReferenceColumn> columns) {
		this.columns = columns;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public void setControl(ListReferencePropertyControl control) {
		this.control = control;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setPopupDialog(AbstractTitleAreaDialog popupDialog) {
		this.popupDialog = popupDialog;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}
	
	/**
	 * @param action
	 */
	public void addContextMenuAction(ListMenuAction action){
		actions.add(action);
	}
	
	/**
	 * @return
	 */
	public List<ListMenuAction> getContextMenuActions(){
		return actions;
	}
	
	public void addCopyAction(){
		this.copyAction = true;
	}
	
	public boolean hookCopyAction(){
		return this.copyAction;
	}

	/**
	 * @return the displayLabel
	 */
	public boolean isDisplayLabel() {
		return displayLabel;
	}

}
