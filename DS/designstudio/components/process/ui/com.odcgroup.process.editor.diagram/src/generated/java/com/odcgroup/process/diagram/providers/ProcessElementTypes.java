/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.providers;

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

import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class ProcessElementTypes extends ElementInitializers {

	/**
	 * @generated
	 */
	private ProcessElementTypes() {
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
	public static final IElementType Process_79 = getElementType("com.odcgroup.process.editor.diagram.Process_79"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Pool_1001 = getElementType("com.odcgroup.process.editor.diagram.Pool_1001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UserTask_2001 = getElementType("com.odcgroup.process.editor.diagram.UserTask_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ServiceTask_2002 = getElementType("com.odcgroup.process.editor.diagram.ServiceTask_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ComplexGateway_2003 = getElementType("com.odcgroup.process.editor.diagram.ComplexGateway_2003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExclusiveMerge_2004 = getElementType("com.odcgroup.process.editor.diagram.ExclusiveMerge_2004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ParallelFork_2005 = getElementType("com.odcgroup.process.editor.diagram.ParallelFork_2005"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType ParallelMerge_2006 = getElementType("com.odcgroup.process.editor.diagram.ParallelMerge_2006"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType StartEvent_2007 = getElementType("com.odcgroup.process.editor.diagram.StartEvent_2007"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType EndEvent_2008 = getElementType("com.odcgroup.process.editor.diagram.EndEvent_2008"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Flow_3001 = getElementType("com.odcgroup.process.editor.diagram.Flow_3001"); //$NON-NLS-1$

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
				return ProcessDiagramEditorPlugin.getInstance().getItemImageDescriptor(
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

			elements.put(Process_79, ProcessPackage.eINSTANCE.getProcess());

			elements.put(Pool_1001, ProcessPackage.eINSTANCE.getPool());

			elements.put(UserTask_2001, ProcessPackage.eINSTANCE.getUserTask());

			elements.put(ServiceTask_2002, ProcessPackage.eINSTANCE.getServiceTask());

			elements.put(ComplexGateway_2003, ProcessPackage.eINSTANCE.getComplexGateway());

			elements.put(ExclusiveMerge_2004, ProcessPackage.eINSTANCE.getExclusiveMerge());

			elements.put(ParallelFork_2005, ProcessPackage.eINSTANCE.getParallelFork());

			elements.put(ParallelMerge_2006, ProcessPackage.eINSTANCE.getParallelMerge());

			elements.put(StartEvent_2007, ProcessPackage.eINSTANCE.getStartEvent());

			elements.put(EndEvent_2008, ProcessPackage.eINSTANCE.getEndEvent());

			elements.put(Flow_3001, ProcessPackage.eINSTANCE.getFlow());
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
			KNOWN_ELEMENT_TYPES.add(Process_79);
			KNOWN_ELEMENT_TYPES.add(Pool_1001);
			KNOWN_ELEMENT_TYPES.add(UserTask_2001);
			KNOWN_ELEMENT_TYPES.add(ServiceTask_2002);
			KNOWN_ELEMENT_TYPES.add(ComplexGateway_2003);
			KNOWN_ELEMENT_TYPES.add(ExclusiveMerge_2004);
			KNOWN_ELEMENT_TYPES.add(ParallelFork_2005);
			KNOWN_ELEMENT_TYPES.add(ParallelMerge_2006);
			KNOWN_ELEMENT_TYPES.add(StartEvent_2007);
			KNOWN_ELEMENT_TYPES.add(EndEvent_2008);
			KNOWN_ELEMENT_TYPES.add(Flow_3001);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

}
