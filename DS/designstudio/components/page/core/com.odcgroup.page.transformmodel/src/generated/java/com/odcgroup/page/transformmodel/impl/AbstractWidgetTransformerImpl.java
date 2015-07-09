/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;


import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.AbstractWidgetTransformer;
import com.odcgroup.page.transformmodel.ElementEventTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.snippet.SnippetTransformer;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractWidgetTransformerImpl extends MinimalEObjectImpl.Container implements AbstractWidgetTransformer {
	
	/** The EventTransformer. */
	private ElementEventTransformer eventTransformer;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.ABSTRACT_WIDGET_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModel getModel() {
		if (eContainerFeatureID() != TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL) return null;
		return (TransformModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newModel
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModel(TransformModel newModel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newModel, TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(TransformModel newModel) {
		if (newModel != eInternalContainer() || (eContainerFeatureID() != TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL && newModel != null)) {
			if (EcoreUtil.isAncestor(this, newModel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newModel != null)
				msgs = ((InternalEObject)newModel).eInverseAdd(this, TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS, TransformModel.class, msgs);
			msgs = basicSetModel(newModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL, newModel, newModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetModel((TransformModel)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				return basicSetModel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				return eInternalContainer().eInverseRemove(this, TransformModelPackage.TRANSFORM_MODEL__WIDGET_TRANSFORMERS, TransformModel.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				return getModel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				setModel((TransformModel)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				setModel((TransformModel)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_WIDGET_TRANSFORMER__MODEL:
				return getModel() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		preTransform(context, widget);
		transformTranslations(context, widget);
		transformProperties(context, widget);
		transformEvents(context, widget);
		transformSnippets(context, widget);
		transformChildren(context, widget);
		postTransform(context, widget);
	}	
	
	/**
	 * @param context
	 * @param widget
	 */
	protected void transformSnippets(WidgetTransformerContext context, Widget widget) {
		SnippetTransformer transformer = new SnippetTransformer();        
        for (Snippet snippet : widget.getSnippets()) {
            transformer.transform(context, snippet, null, widget, false);			
		}
	}
	
	
	/**
	 * Pre-transforms the Widget. This is an ideal place to create the 
	 * Element / Attr and update the WidgetTransformerContext ready
	 * for the next child.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @exception CoreException
	 */
	protected void preTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		// Default implementation. Do nothing
	}
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	protected void transformTranslations(WidgetTransformerContext context, Event event) throws CoreException  {
		ITranslation translation = context.getTranslation(event);
		if (translation != null) {
			for (ITranslationKind kind : translation.getAllKinds()) {
				TransformUtils.transformTranslation(context, translation, kind);
			}
		}
	}
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	protected void transformTranslations(WidgetTransformerContext context, Widget widget) throws CoreException  {
		ITranslation translation = context.getTranslation(widget);
		if (translation != null) {
			for (ITranslationKind kind : translation.getAllKinds()) {
				TransformUtils.transformTranslation(context, translation, kind);
			}
		}
	}
	
	/**
	 * Transforms the Properties of the Widget.
	 *
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget whose properties are to be transformed
	 * @exception CoreException
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getProperties().iterator(); it.hasNext();) {
			Property p = (Property) it.next();
			// System.out.println(p.getTypeName());
			PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);
			pt.transform(context, p);			
		}	
	}
	
	/**
	 * Transforms the events.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Element whose children are to be transformed
	 * @exception CoreException
	 */
	protected void transformEvents(WidgetTransformerContext context, Widget widget) throws CoreException {
		getEventTransformer().transform(context, widget);
	}
	
	/**
	 * Transforms the child Widgets.
	 *
	 * @param context The WidgetTransformerContext
	 * @param widget The Element whose children are to be transformed
	 * @exception CoreException
	 */
	protected void transformChildren(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			wt.transform(context, child);
		}
	}
	
	/**
	 * Post-transforms the Widget. This is an ideal place to update the
	 * WidgetTransformerContext ready for the next-sibling.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @exception CoreException
	 */
	protected void postTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		// Default implementation. Do nothing
	}
	
	/**
	 * Gets the EventTransformer.
	 * 
	 * @return EventTransformer
	 */
	private ElementEventTransformer getEventTransformer() {
		if (eventTransformer == null) {
			eventTransformer = new ElementEventTransformer();
		}
		return eventTransformer;
	}
	
	/**
	 * Creates an Element of the specified namespace and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element appendElement(WidgetTransformerContext context, String namespace, String elementName) {
		Element e = createElement(context, namespace, elementName);
		context.getParentElement().appendChild(e);
		return e;
	}
	
	/**
	 * Creates an Element of the specified namespace and name.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Document d = context.getDocument();
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = d.createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}	

} //AbstractWidgetTransformerImpl