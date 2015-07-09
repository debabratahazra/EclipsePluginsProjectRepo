package com.odcgroup.pageflow.editor.diagram.custom.policies;

import java.util.List;

import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;

import com.odcgroup.pageflow.editor.diagram.custom.util.PageflowPreferenceConstants;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class PageflowConnectionSelectionEditPolicy extends ConnectionEndpointEditPolicy {	
	
	public PageflowConnectionSelectionEditPolicy(){
               initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#hideSelection()
	 */
	protected void hideSelection() {
		 if (getHost()instanceof ConnectionNodeEditPart){
				ConnectionNodeEditPart cnep = (ConnectionNodeEditPart)getHost();
				PolylineConnectionEx line = (PolylineConnectionEx)cnep.getConnectionFigure();
				line.setForegroundColor(PageflowDiagramEditorPlugin.getColor(PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR));
				line.setLineWidth(1);
				List list =line.getChildren();
				for(int i=0;i<list.size();i++){
					Object obj = list.get(i);
					if (obj instanceof PolylineDecoration){
						PolylineDecoration deco = (PolylineDecoration)obj;
						deco.setLineWidth(1);
					} else if (obj instanceof WrapLabel){
						WrapLabel label = (WrapLabel)obj;
						label.setBorder(null);
					}
				}
			}	  
			removeSelectionHandles();
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#showSelection()
	 */
	protected void showSelection() {
		addSelectionHandles();
		if (getHost()instanceof ConnectionNodeEditPart){
			ConnectionNodeEditPart cnep = (ConnectionNodeEditPart)getHost();
			PolylineConnectionEx line = (PolylineConnectionEx)cnep.getConnectionFigure();
			Color color= PageflowDiagramEditorPlugin.getColor(PageflowPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR);
			line.setForegroundColor(color);
			line.setLineWidth(2);
			List list =line.getChildren();
			for(int i=0;i<list.size();i++){
				Object obj = list.get(i);
				if (obj instanceof PolylineDecoration){
					PolylineDecoration deco = (PolylineDecoration)obj;
					deco.setLineWidth(2);
				} else if (obj instanceof WrapLabel){
					WrapLabel label = (WrapLabel)obj;
					LineBorder border = new LineBorder(2);
					border.setColor(color);
					label.setBorder(border);
				}
			}
		}
	}
	
	
	/**
	 * @return
	 */
	private IPreferenceStore getPreferenceStore(){
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
        
	/**
	 * initialize method to register color.
	 */
	private void initialize(){
	    PageflowDiagramEditorPlugin.setColorInRegistry(
			PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR,
			PreferenceConverter.getColor(getPreferenceStore(),PageflowPreferenceConstants.PREF_TRN_LINE_FILL_COLOR));
	    PageflowDiagramEditorPlugin.setColorInRegistry(
			PageflowPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR,
			PreferenceConverter.getColor(getPreferenceStore(),PageflowPreferenceConstants.PREF_TRN_LINE_HIGHLIGHT_COLOR));
	}

}
