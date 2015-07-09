package com.odcgroup.process.diagram.custom.policies;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

public class ProcessItemSelectionEditPolicy extends SelectionEditPolicy {
	
	protected static final int WIDTH = 1;
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#hideSelection()
	 */
	protected void hideSelection() {
		if (getHost() instanceof GraphicalEditPart){
			GraphicalEditPart ep = (GraphicalEditPart)getHost();
			ep.getContentPane().setBorder(null);
			List list = ep.getChildren();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if (obj instanceof LabelEditPart){
					LabelEditPart lep = (LabelEditPart)obj;
					lep.getContentPane().setBorder(null);
				}
			}
		} else if (getHost()instanceof ConnectionNodeEditPart){
			ConnectionNodeEditPart cnep = (ConnectionNodeEditPart)getHost();
			PolylineConnectionEx line = (PolylineConnectionEx)cnep.getConnectionFigure();
			RGB color = PreferenceConverter.getColor(getPreferenceStore(), ProcessPreferenceConstants.PREF_TRN_LINE_FILL_COLOR);
			line.setForegroundColor(new Color(null, color));
			line.setLineWidth(WIDTH);
			List list =line.getChildren();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if (obj instanceof PolylineDecoration){
					PolylineDecoration deco = (PolylineDecoration)obj;
					deco.setLineWidth(WIDTH);
				}else if (obj instanceof WrapLabel){
					WrapLabel label = (WrapLabel)obj;
					label.setBorder(null);
				}
			}
		}	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#showSelection()
	 */	
	protected void showSelection() {
		if (getHost() instanceof GraphicalEditPart){
			GraphicalEditPart ep = (GraphicalEditPart)getHost();
			ep.getContentPane().setBorder(new LineBorder(ColorConstants.black,2));
			List list = ep.getChildren();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if (obj instanceof LabelEditPart){
					LabelEditPart lep = (LabelEditPart)obj;
					lep.getContentPane().setBorder(new LineBorder(ColorConstants.black,2));
				}
			}
		} else if (getHost()instanceof ConnectionNodeEditPart){
			ConnectionNodeEditPart cnep = (ConnectionNodeEditPart)getHost();
			PolylineConnectionEx line = (PolylineConnectionEx)cnep.getConnectionFigure();
			RGB color = PreferenceConverter.getColor(getPreferenceStore(), ProcessPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR);
			line.setForegroundColor(new Color(null, color));
			line.setLineWidth(2);
			List list =line.getChildren();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if (obj instanceof PolylineDecoration){
					PolylineDecoration deco = (PolylineDecoration)obj;
					deco.setLineWidth(2);
				}else if (obj instanceof WrapLabel){
					WrapLabel label = (WrapLabel)obj;
					LineBorder border = new LineBorder(2);
					border.setColor(new Color(null, color));
					label.setBorder(border);
				}
			}
		}
	}
	
	private IPreferenceStore getPreferenceStore(){
		return ProcessDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
	

	
}
