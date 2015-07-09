package com.odcgroup.page.transformmodel;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * The TransformerContext is used during the transformation process to pass
 * information between the different Transformers.
 * 
 * @author Gary Hayes
 */
public interface WidgetTransformerContext extends TransformerContext {

	/**
	 * Gets the Corporate Design to use during this transformation process.
	 * 
	 * @return CorporateDesign The Corporate Design
	 */
	CorporateDesign getCorporateDesign();

	/**
	 * Gets the Document used to create Elements etc.
	 * 
	 * @return Document The Document used to create Elements etc.
	 */
	Document getDocument();

	/**
	 * Gets the parent Element
	 * 
	 * @return Element The parent Element
	 */
	Element getParentElement();

	/**
	 * Sets the parent Element.
	 * 
	 * @param parentElement
	 *            The parent Element
	 * @return Element The old parent Element
	 */
	Element setParentElement(Element parentElement);

	/**
	 * Gets the project being generated.
	 * 
	 * @return IProject The eclipse project
	 */
	IProject getProject();

	/**
	 * Gets the root Element. This is considered to be the first non-null parent
	 * Element added to the context.
	 * 
	 * @return Element The root Element
	 */
	Element getRootElement();
	
	/**
	 * @return <tt>true</tt> if there is an element 'udp' currently open, otherwise
	 * it return <tt>false</tt>.  
	 */
	boolean isRootUDPOpen();
	
	/**
	 * This method must be call by transformer that produce a root UDP
	 */
	void openRootUDP();
	
	/**
	 * This method must be call by transformers that close a root UDP
	 */
	void closeRootUDP();
	
	void setDynamicColumnLeafNode(boolean isLeaf);
	boolean isDynamicColumnLeafNode();
	
	void setReactToMainCheckbox(boolean react);
	boolean reactToMainCheckbox();
	
	void setInXTooltipFragment(boolean in);
	boolean isInXTooltipFragment();
	
	void setEditableTableTree(boolean editable);
	boolean isEditableTableTree();
	void setEditableTableTreeLeafNode(boolean nonleafNode);
	boolean getEditableTableTreeLeafNode();
	/**
	 * should be false when a grouping node is generated, it should be true when a hierarchy node is generated
	 * @param hierarchy true when a grouping node is generated
	 */
	void setHierarchyNonLeafNode(boolean hierarchy);
	boolean isHierarchyNonLeafNode();
	void setEditableDataset(String datasetName);
	String getEditableDataset();
	void setEditableDatasetAssociation(String associationName);
	String getEditableDatasetAssociation();
	void setEditableDatasetAttribute(String datasetName);
	String getEditableDatasetAttribute();

	/**
	 * Gets for the parent Widgets. Some Widgets, for example the Include and
	 * Attribute Widgets create or load extra models which are not directly part
	 * of this EMF model. In this case calling getParent() directly on the
	 * widget eventually returns null, when it should really return the Include
	 * Widget and its parents.
	 * 
	 * @return Widget The parent Widget
	 */
	@SuppressWarnings("rawtypes")
	List getParentWidgets();

	/**
	 * @returns the translation given a widget.
	 */
	ITranslation getTranslation(Widget widget);
	
	/**
	 * @returns the translation given an event.
	 */
	ITranslation getTranslation(Event event);

	/** 
	 * @return the translation key given a translation
	 */
	ITranslationKey getTranslationKey(ITranslation translation);

	/** 
	 * @return the translation key given a translation and its kind.
	 */
	String getTranslationKey(ITranslation translation, ITranslationKind kind);
	
	/** 
	 * @return the translation key given an event and a kind of translation.
	 */
	String getTranslationKey(Event event, ITranslationKind kind);
	
	/** 
	 * @return the translation key given a widget and a kind of translation.
	 */
	String getTranslationKey(Widget widget, ITranslationKind kind);

	/** 
	 * @return the translation key given a domain element and a kind of translation.
	 */
	String getTranslationKey(MdfModelElement element, ITranslationKind kind);

}
