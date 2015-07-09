package com.odcgroup.page.ui.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.edit.WidgetEditPartUtils;
import com.odcgroup.page.ui.request.MultipleWidgetCreateRequest;
import com.odcgroup.page.ui.util.PaletteUtils;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * paste action - pastes the content from the clipboard
 * on to the selected widget
 * @author pkk 
 */
public class PasteWidgetAction extends SelectionAction {

	/**
	 * constructor for PasteWidgetAction 
	 * @param part
	 */
	public PasteWidgetAction(IWorkbenchPart part) {
		super(part);
		
	}
	

	/** 
	* Inits the action. 
	*/ 
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText("Paste");
		setAccelerator(SWT.CTRL | 'V'); 
		setDescription("Paste"); 
		setToolTipText("Paste"); 
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(
				ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	/**
	 * Calculates if the action is enables.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		boolean enabled = false;
		Object clipCont = Clipboard.getDefault().getContents();
		
		if (clipCont instanceof CutSelection) {
			clipCont = ((CutSelection)clipCont).getSelection();
		}
		List<Widget> children=null;
		Widget container=null;
		if (clipCont != null) {
			 children = getChildren(clipCont);
//			 if(children.get(0).getTypeName().equals("TableColumn")) {
//				 ITableColumn column = (ITableColumn)TableHelper.getTableColumn(children.get(0));
//				 if(column.getGroups().size() > 0) {
//					 return true;
//				 }
//			 }
			if (children.size() > 0) {
				container = getContainer();
				enabled = (container != null && WidgetUtils.canContainChildren(container, children));
			}
		}
		if(children!=null&&children.size()>0&&enabled){
		 enabled=PaletteUtils.isWidgetInPaletteList(children.get(0), container.getRootWidget());
		}
		return enabled;
	}
	
	/**
	 * Runs the action.
	 */
	public void run() {
		execute(createPasteCommand());
	}
	
	/**
	 * @return Command The Command
	 */
	@SuppressWarnings("rawtypes")
	protected Command createPasteCommand() {
		List selection = getSelectedObjects();
		
		Command result = null;
		if (selection != null && selection.size() == 1) {
			Object obj = selection.get(0);
			if (obj instanceof WidgetEditPart) {
				WidgetEditPart wep = (WidgetEditPart)obj;
				Object prototype = Clipboard.getDefault().getContents();
				if (prototype != null ) {					
					MultipleWidgetCreateRequest request = createMultipleWidgetCreateRequest(prototype);
					request.setLocation(getPasteLocation(wep));
					result = wep.getCommand(request);
				}
			}
		}
		return result;
	}
	
	/**
	 * @param template
	 * @return MultipleWidgetCreateRequest The Request
	 */
	@SuppressWarnings("rawtypes")
	protected MultipleWidgetCreateRequest createMultipleWidgetCreateRequest(Object prototype) {
		List list = null;
		boolean regenerateID = true;
		if (prototype instanceof CutSelection) {
			CutSelection cs = (CutSelection)prototype;
			regenerateID = cs.getCount() > 0;
			cs.incrCount();
			list = ((CutSelection)prototype).getSelection();
		} else {
			regenerateID = true;
			list = (List) prototype;
		}
		List<Widget> widgetList = new ArrayList<Widget>();
		for(java.util.Iterator iter = list.iterator();iter.hasNext();) {
			WidgetEditPart editPart = (WidgetEditPart)iter.next();
			Widget widget = (Widget) AdaptableUtils.getAdapter(editPart, Widget.class);			
			if (widget != null) {
				widgetList.add(WidgetCopier.copy(widget, regenerateID));
			}
		}
		MultipleWidgetCreateRequest r = new MultipleWidgetCreateRequest();
		r.setNewObjects(widgetList);
		return r;
	}
	
	/**
	 * @param container
	 * @return Point The paste location
	 */
	protected Point getPasteLocation(GraphicalEditPart container) {
		Point result = new Point(10, 10);
		IFigure fig = container.getContentPane();
		result.translate(fig.getClientArea(Rectangle.SINGLETON).getLocation());
		fig.translateToAbsolute(result);
		return result;
	}	
	
	/**
	 * @return the selected widget
	 */
	private Widget getContainer(){
		ISelection s = getSelection();
		Widget container = null;
		if (s != null && s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			if (ss.size() == 1) {
				Object obj = ss.getFirstElement();
				container = (Widget) AdaptableUtils.getAdapter(obj, Widget.class);				
			}
		}
		return container;
	}
	

	/**
	 * @param clipContents
	 * @return the list of widgets from the clipboard
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Widget> getChildren(Object clipContents){
		List<Widget> children = Collections.EMPTY_LIST;
		if (clipContents instanceof List) {
			List list = (List)clipContents;
			if (list.size() > 0) {
				if (list.get(0) instanceof WidgetEditPart) {
					return WidgetEditPartUtils.getWidgets((List<WidgetEditPart>)list);
				}
			}
		}
        return children;
	}

	
}
