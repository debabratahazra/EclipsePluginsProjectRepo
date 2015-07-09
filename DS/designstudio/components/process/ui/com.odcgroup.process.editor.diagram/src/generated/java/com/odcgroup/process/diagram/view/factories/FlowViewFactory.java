/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.ConnectorStyle;
import org.eclipse.gmf.runtime.notation.JumpLinkStatus;
import org.eclipse.gmf.runtime.notation.JumpLinkType;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.edit.parts.FlowEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowNameEditPart;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;

/**
 * @generated
 */
public class FlowViewFactory extends ConnectionViewFactory {

	/**
	 * @generated
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		return styles;
	}

	/**
	 * @generated NOT
	 */
	protected void decorateView(View containerView, View view, IAdaptable semanticAdapter, String semanticHint,
			int index, boolean persisted) {
		if (semanticHint == null) {
			semanticHint = ProcessVisualIDRegistry.getType(FlowEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);
		IPreferenceStore preferenceStore = (IPreferenceStore) super.getPreferencesHint().getPreferenceStore();

		// transition routing style
		ConnectorStyle rstyle = (ConnectorStyle) view.getStyle(NotationPackage.eINSTANCE.getConnectorStyle());

		String routingV = preferenceStore.getString(ProcessPreferenceConstants.PREF_TRN_LINE_STYLE);
		Routing routing = Routing.getByName(routingV);
		if (routing == null)
			routing = Routing.MANUAL_LITERAL;
		rstyle.setRouting(routing);

		boolean closestDistance = preferenceStore.getBoolean(ProcessPreferenceConstants.PREF_TRN_ROUTE_SHORTEST);
		rstyle.setClosestDistance(closestDistance);

		String smoothStr = preferenceStore.getString(ProcessPreferenceConstants.PREF_TRN_ROUTE_SMOOTH_FACTOR);
		Smoothness smoothness = Smoothness.get(smoothStr);
		if (smoothness == null)
			smoothness = Smoothness.NORMAL_LITERAL;
		rstyle.setSmoothness(smoothness);

		rstyle.setJumpLinkStatus(JumpLinkStatus.ABOVE_LITERAL);
		rstyle.setJumpLinkType(JumpLinkType.SEMICIRCLE_LITERAL);

		RGB fillColor = PreferenceConverter.getColor(preferenceStore,
				ProcessPreferenceConstants.PREF_TRN_LINE_FILL_COLOR);
		if (fillColor == null)
			rstyle.setLineColor(FigureUtilities.colorToInteger(ColorConstants.black));

		rstyle.setLineColor(FigureUtilities.RGBToInteger(fillColor));

		IAdaptable eObjectAdapter = null;
		EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			eObjectAdapter = new EObjectAdapter(eObject);
		}
		getViewService().createNode(eObjectAdapter, view, ProcessVisualIDRegistry.getType(FlowNameEditPart.VISUAL_ID),
				ViewUtil.APPEND, true, getPreferencesHint());
	}
}
