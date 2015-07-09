/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated
 */
public class PageflowElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private PageflowElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Pageflow_79 = getElementType("com.odcgroup.pageflow.editor.diagram.Pageflow_79"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InitState_1001 = getElementType("com.odcgroup.pageflow.editor.diagram.InitState_1001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DecisionState_1002 = getElementType("com.odcgroup.pageflow.editor.diagram.DecisionState_1002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType EndState_1003 = getElementType("com.odcgroup.pageflow.editor.diagram.EndState_1003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ViewState_1004 = getElementType("com.odcgroup.pageflow.editor.diagram.ViewState_1004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SubPageflowState_1005 = getElementType("com.odcgroup.pageflow.editor.diagram.SubPageflowState_1005"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Transition_3001 = getElementType("com.odcgroup.pageflow.editor.diagram.Transition_3001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	private static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
		}
		return imageRegistry;
	}

	/**
	 * @generated
	 */
	private static String getImageRegistryKey(ENamedElement element) {
		return element.getName();
	}

	/**
	 * @generated
	 */
	private static ImageDescriptor getProvidedImageDescriptor(ENamedElement element) {
		if (element instanceof EStructuralFeature) {
			EStructuralFeature feature = ((EStructuralFeature) element);
			EClass eContainingClass = feature.getEContainingClass();
			EClassifier eType = feature.getEType();
			if (eContainingClass != null && !eContainingClass.isAbstract()) {
				element = eContainingClass;
			} else if (eType instanceof EClass && !((EClass) eType).isAbstract()) {
				element = eType;
			}
		}
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (!eClass.isAbstract()) {
				return PageflowDiagramEditorPlugin.getInstance().getItemImageDescriptor(
						eClass.getEPackage().getEFactoryInstance().create(eClass));
			}
		}
		// TODO : support structural features
		return null;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		String key = getImageRegistryKey(element);
		ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
		if (imageDescriptor == null) {
			imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
		}
		return imageDescriptor;
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		String key = getImageRegistryKey(element);
		Image image = getImageRegistry().get(key);
		if (image == null) {
			ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
			image = getImageRegistry().get(key);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImage(element);
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap();

			elements.put(Pageflow_79, PageflowPackage.eINSTANCE.getPageflow());

			elements.put(InitState_1001, PageflowPackage.eINSTANCE.getInitState());

			elements.put(DecisionState_1002, PageflowPackage.eINSTANCE.getDecisionState());

			elements.put(EndState_1003, PageflowPackage.eINSTANCE.getEndState());

			elements.put(ViewState_1004, PageflowPackage.eINSTANCE.getViewState());

			elements.put(SubPageflowState_1005, PageflowPackage.eINSTANCE.getSubPageflowState());

			elements.put(Transition_3001, PageflowPackage.eINSTANCE.getTransition());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet();
			KNOWN_ELEMENT_TYPES.add(Pageflow_79);
			KNOWN_ELEMENT_TYPES.add(InitState_1001);
			KNOWN_ELEMENT_TYPES.add(DecisionState_1002);
			KNOWN_ELEMENT_TYPES.add(EndState_1003);
			KNOWN_ELEMENT_TYPES.add(ViewState_1004);
			KNOWN_ELEMENT_TYPES.add(SubPageflowState_1005);
			KNOWN_ELEMENT_TYPES.add(Transition_3001);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

}
