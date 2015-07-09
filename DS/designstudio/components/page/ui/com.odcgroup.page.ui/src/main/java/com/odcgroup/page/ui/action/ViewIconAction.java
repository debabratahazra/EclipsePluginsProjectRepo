package com.odcgroup.page.ui.action;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;

public class ViewIconAction extends AbstractGenericAction {
    public static boolean eventsChecked =true;
    public static boolean xtooltipChecked =true;
    public static boolean tooltipChecked =false;
    
    public ViewIconAction(ActionParameters parameters) {
	super(parameters);
	setEnabled(calculateEnabled());
	init();
    }
    private void init(){
	if(isViewEventIconAction()){
	    setChecked(eventsChecked);
	}else if(isViewXtooltipIconAction()){
	    setChecked(xtooltipChecked);
	} else if(isViewTooltipIconAction()){
	    setChecked(tooltipChecked);
	}
    }
    protected boolean calculateEnabled() {
	Widget w = getSelectedWidget();
	return (w != null);
    }

    /**
     * Runs the action. 
     */
    public void run() {
	if(!isChecked()){
	    setChecked(false);
	} else {
	    setChecked(true);
	}	
	refresh();
    }	

    /**
     * refresh the Editor. 
     */
    private void refresh(){
	IEditorPart part=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	if(part instanceof MultiPageEditorPart){
	    MultiPageEditorPart mep= ((MultiPageEditorPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor());
	    DesignEditor editor = (DesignEditor) mep.getSelectedPage();
	    ((WidgetEditPart)(editor.getViewer().getRootEditPart().getChildren().get(0))).refreshAll();
	}
    }

    private boolean isViewEventIconAction(){
	if(getParameters().getId().equals("com.odcgroup.page.ui.action.ViewEventsIconAction")){
	    return true;
	}
	return false;
    }

    private boolean isViewXtooltipIconAction(){
	if(getParameters().getId().equals("com.odcgroup.page.ui.action.ViewXtooltipsIconAction")){
	    return true;
	}
	return false;
    }

    private boolean isViewTooltipIconAction(){
	if(getParameters().getId().equals("com.odcgroup.page.ui.action.ViewTooltipsIconAction")){
	    return true;
	}
	return false;
    }
    
    public void setChecked(boolean checked) {
	super.setChecked(checked);
	if(isViewEventIconAction()){
	    eventsChecked =checked;        
	} else if (isViewXtooltipIconAction()){
	    xtooltipChecked =checked; 
	}else if(isViewTooltipIconAction()){
	    tooltipChecked =checked;        
	}

    }
}
