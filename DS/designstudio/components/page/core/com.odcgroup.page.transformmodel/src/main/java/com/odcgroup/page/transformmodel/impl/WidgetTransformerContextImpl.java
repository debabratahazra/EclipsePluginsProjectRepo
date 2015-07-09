package com.odcgroup.page.transformmodel.impl;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;

/**
 * The TransformerContext is used during the transformation process to pass information between the different
 * Transformers.
 * 
 * @author Gary Hayes
 */
public class WidgetTransformerContextImpl extends AbstractTransformerContext implements WidgetTransformerContext {
	
	/** The Document. */
	private Document document;
	
	/** The parent Element. */
	private Element parentElement;
	
	/** The root Element. */
	private Element rootElement;
	
	/** The eclipse project. */
	private IProject project;
	
	/** The parent Widgets. */
	private List<Widget> parentWidgets;
	
	/** The corportate design */
	private CorporateDesign corporateDesign;
	
	/** The translation manager */
	private ITranslationManager manager;
	
	/** 
	 * This variable should be true whenever a transformer output
	 * a root UDP element. 
	 */
	private boolean rootUdpEnabled = false;
	
	private boolean dynamicColumnLeafNode = false;
	
	private boolean reactToMainCheckbox = false;
	
	private boolean editableTableTree = false;
	private boolean editableTableTreeLeafNode = true;
	private boolean editableTableTreeHierarchyNonLeafNode = false;
	private boolean inXTooltipFragment = false;
	private String editableDataset;
	private String editableDatasetAssociation;
	private String editableDatasetAttribute;

	/**
	 * Gets the Corporate Design to use during this transformation process.
	 * 
	 * @return CorporateDesign The Corporate Design
	 */
	public final CorporateDesign getCorporateDesign() {
		return corporateDesign;
	}

	/**
	 * Gets the Document used to create Elements etc.
	 * 
	 * @return Document The Document used to create Elements etc.
	 */
	public final Document getDocument() {
		return document;
	}
	
	/**
	 * Gets the parent Element
	 * 
	 * @return Element The parent Element
	 */
	public final Element getParentElement() {
		return parentElement;
	}

	/**
	 * Sets the parent Element.
	 * 
	 * @param parentElement The parent Element
	 * @return Element The old parent Element
	 */
	public Element setParentElement(Element parentElement) {
		Element returnElement = this.parentElement;
		this.parentElement = parentElement;
		if (rootElement == null) {
			rootElement = parentElement;
		}
		return returnElement;
	}
	
	/**
	 * Gets the root Element. This is considered to be the first non-null
	 * parent Element added to the context.
	 * 
	 * @return Element The root Element
	 */
	public final Element getRootElement() {
		return rootElement;
	}
	
	/**
	 * Gets the parent Widgets. Some Widgets, for example the Include and Attribute Widgets
	 * create or load extra models which are not directly part of this EMF model. In this case
	 * calling getParent() directly on the widget eventually returns null, when it should
	 * really return the Include Widget and its parents.
	 * 
	 * @return List The parent Widget
	 */
	public final List<Widget> getParentWidgets() {
		return parentWidgets;
	}

	@Override
	public final IProject getProject() {
		return this.project;
	}

	@Override
	public ITranslation getTranslation(Widget widget) {
		return manager.getTranslation(widget);
	}
	
	@Override
	public ITranslation getTranslation(Event event) {
		return manager.getTranslation(event);
	}
	
	@Override
	public ITranslationKey getTranslationKey(ITranslation translation) {
		return TranslationGenerationCore.getTranslationKey(project, translation);
	}

	@Override
	public String getTranslationKey(ITranslation translation, ITranslationKind kind) {
		String key = null;
		try {
			ITranslationKey tkey = TranslationGenerationCore.getTranslationKey(project, translation);
			if (tkey != null) {
				key = tkey.getKey(kind);
			}
		} finally {
		}
		return key;
	}

	@Override
	public String getTranslationKey(Event event, ITranslationKind kind) {
		String key = null;
		ITranslation translation = manager.getTranslation(event);
		if (translation != null) {
			key = getTranslationKey(translation, kind);
		}
		return key;
	}

	@Override
	public String getTranslationKey(Widget widget, ITranslationKind kind) {
		String key = null;
		ITranslation translation = manager.getTranslation(widget);
		if (translation != null) {
			key = getTranslationKey(translation, kind);
		}
		return key;
	}

	@Override
	public String getTranslationKey(MdfModelElement element, ITranslationKind kind) {
		String key = null;
		ITranslation translation = manager.getTranslation(element);
		if (translation != null) {
			key = getTranslationKey(translation, kind);
		}
		return key;
	}

	/**
	 * Creates a new WidgetTransformerContextImpl.
	 * 
	 * @param metaModel The MetaModel to use
	 * @param transformModel The TransformModel to use
	 * @param document
	 *            The Document used to create Elements etc.
	 * @param project The eclipse project
	 */
	public WidgetTransformerContextImpl(MetaModel metaModel, TransformModel transformModel, Document document, IProject project) {
		super(metaModel, transformModel);
		this.document = document;
		this.project = project;
		this.parentWidgets = new LinkedList<Widget>();
		this.corporateDesign = CorporateDesignUtils.getCorporateDesign(project);
		this.manager = TranslationCore.getTranslationManager(getProject());

	}

	@Override
	public void closeRootUDP() {
		rootUdpEnabled = false;
	}

	@Override
	public boolean isRootUDPOpen() {
		return rootUdpEnabled;
	}

	@Override
	public void openRootUDP() {
		rootUdpEnabled = true;
	}

	@Override
	public boolean isDynamicColumnLeafNode() {
		return dynamicColumnLeafNode;
	}

	@Override
	public void setDynamicColumnLeafNode(boolean isLeafNode) {
		dynamicColumnLeafNode = isLeafNode;
	}

	@Override
	public void setReactToMainCheckbox(boolean react) {
		this.reactToMainCheckbox = react;		
	}

	@Override
	public boolean reactToMainCheckbox() {
		return reactToMainCheckbox;
	}

	@Override
	public void setEditableTableTree(boolean editable) {
		this.editableTableTree = editable;
	}

	@Override
	public boolean isEditableTableTree() {
		return editableTableTree;
	}

	@Override
	public void setEditableDataset(String datasetName) {
		this.editableDataset = datasetName;
	}

	@Override
	public String getEditableDataset() {
		return editableDataset;
	}

	@Override
	public void setEditableDatasetAttribute(String datasetAttributeName) {
		this.editableDatasetAttribute = datasetAttributeName;
	}

	@Override
	public String getEditableDatasetAttribute() {
		return editableDatasetAttribute;
	}

	@Override
	public void setEditableDatasetAssociation(String associationName) {
		this.editableDatasetAssociation = associationName;
	}

	@Override
	public String getEditableDatasetAssociation() {
		return editableDatasetAssociation;
	}

	@Override
	public void setEditableTableTreeLeafNode(boolean leafNode) {
		this.editableTableTreeLeafNode = leafNode;
	}

	@Override
	public boolean getEditableTableTreeLeafNode() {
		return this.editableTableTreeLeafNode;
	}

	@Override
	public void setHierarchyNonLeafNode(boolean grouping) {
		this.editableTableTreeHierarchyNonLeafNode = grouping;
	}

	@Override
	public boolean isHierarchyNonLeafNode() {
		return editableTableTreeHierarchyNonLeafNode;
	}

	@Override
	public void setInXTooltipFragment(boolean in) {
		this.inXTooltipFragment = in;
		
	}

	@Override
	public boolean isInXTooltipFragment() {
		return inXTooltipFragment;
	}

}
