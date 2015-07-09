/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.JumpLinkStatus;
import org.eclipse.gmf.runtime.notation.JumpLinkType;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;
import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;

/**
 * @generated
 */
public class TransitionViewFactory extends ConnectionViewFactory {

	/**
	 * @generated NOT
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createRoutingStyle());
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		styles.add(NotationFactory.eINSTANCE.createLineStyle());
		return styles;
	}

	/**
	 * @generated NOT
	 */
	protected void decorateView(View containerView, View view, IAdaptable semanticAdapter, String semanticHint,
			int index, boolean persisted) {
		if (semanticHint == null) {
			semanticHint = PageflowVisualIDRegistry.getType(TransitionEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);

		IPreferenceStore preferenceStore = (IPreferenceStore) super.getPreferencesHint().getPreferenceStore();

		// transition routing style
		RoutingStyle rstyle = (RoutingStyle) view.getStyle(NotationPackage.eINSTANCE.getRoutingStyle());

		String routingV = preferenceStore.getString(PageflowPreferenceConstants.PREF_TRN_LINE_STYLE);
		Routing routing = Routing.getByName(routingV);
		if (routing == null)
			routing = Routing.MANUAL_LITERAL;
		rstyle.setRouting(routing);

		boolean closestDistance = preferenceStore.getBoolean(PageflowPreferenceConstants.PREF_TRN_ROUTE_SHORTEST);
		rstyle.setClosestDistance(closestDistance);

		String smoothStr = preferenceStore.getString(PageflowPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR);
		Smoothness smoothness = Smoothness.get(smoothStr);
		if (smoothness == null)
			smoothness = Smoothness.NORMAL_LITERAL;
		rstyle.setSmoothness(smoothness);

		rstyle.setJumpLinkStatus(JumpLinkStatus.ABOVE_LITERAL);
		rstyle.setJumpLinkType(JumpLinkType.SEMICIRCLE_LITERAL);

		// transition line fill style
		LineStyle lstyle = (LineStyle) view.getStyle(NotationPackage.eINSTANCE.getLineStyle());
		RGB fillColor = PreferenceConverter.getColor(preferenceStore,
				PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR);
		if (fillColor == null)
			lstyle.setLineColor(FigureUtilities.colorToInteger(ColorConstants.black));

		lstyle.setLineColor(FigureUtilities.RGBToInteger(fillColor));

		IAdaptable eObjectAdapter = null;
		EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			eObjectAdapter = new EObjectAdapter(eObject);
		}
		getViewService().createNode(eObjectAdapter, view,
				PageflowVisualIDRegistry.getType(TransitionDisplayNameEditPart.VISUAL_ID), ViewUtil.APPEND, true,
				getPreferencesHint());
	}
}
