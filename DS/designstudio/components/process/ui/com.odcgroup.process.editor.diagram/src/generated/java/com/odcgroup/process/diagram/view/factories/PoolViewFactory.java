/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.ShapeStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.edit.parts.PoolEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolNameEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;

/**
 * @generated
 */
public class PoolViewFactory extends AbstractShapeViewFactory {

	/**
	 * @generated
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createShapeStyle());
		return styles;
	}

	/**
	 * @generated NOT
	 */
	protected void decorateView(View containerView, View view, IAdaptable semanticAdapter, String semanticHint,
			int index, boolean persisted) {
		if (semanticHint == null) {
			semanticHint = ProcessVisualIDRegistry.getType(PoolEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);

		//non-generated stuff

		IPreferenceStore preferenceStore = (IPreferenceStore) super.getPreferencesHint().getPreferenceStore();

		ShapeStyle fstyle = (ShapeStyle) view.getStyle(NotationPackage.eINSTANCE.getShapeStyle());
		RGB fillColor = PreferenceConverter.getColor(preferenceStore,
				ProcessPreferenceConstants.PREF_POOL_SHAPE_FILL_COLOR);
		if (fillColor == null)
			fstyle.setFillColor(FigureUtilities.RGBToInteger(new RGB(248, 228, 152)));
		fstyle.setFillColor(FigureUtilities.RGBToInteger(fillColor));
		RGB lineColor = PreferenceConverter.getColor(preferenceStore,
				ProcessPreferenceConstants.PREF_PROCESS_SHAPE_LINE_COLOR);
		if (lineColor == null)
			fstyle.setLineColor(FigureUtilities.colorToInteger(ColorConstants.black));
		fstyle.setLineColor(FigureUtilities.RGBToInteger(lineColor));

		//end
		if (!ProcessEditPart.MODEL_ID.equals(ProcessVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", ProcessEditPart.MODEL_ID); //$NON-NLS-1$
			view.getEAnnotations().add(shortcutAnnotation);
		}
		IAdaptable eObjectAdapter = null;
		EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			eObjectAdapter = new EObjectAdapter(eObject);
		}
		getViewService().createNode(eObjectAdapter, view, ProcessVisualIDRegistry.getType(PoolNameEditPart.VISUAL_ID),
				ViewUtil.APPEND, true, getPreferencesHint());
		getViewService().createNode(eObjectAdapter, view,
				ProcessVisualIDRegistry.getType(PoolProcessItemCompartmentEditPart.VISUAL_ID), ViewUtil.APPEND, true,
				getPreferencesHint());
	}
}
