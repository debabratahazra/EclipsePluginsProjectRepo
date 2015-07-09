package com.temenos.t24.tools.eclipse.basic.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Decorator class for an IAction.
 * 
 * @author lfernandez
 *
 */
public class PrintActionDecorator implements IAction {
    
    private IAction delegate;
    
    public PrintActionDecorator(IAction delegate){
        this.delegate = delegate;
    }

    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        delegate.addPropertyChangeListener(listener);        
    }

    public int getAccelerator() {
        return delegate.getAccelerator();
    }

    public String getActionDefinitionId() {
        return delegate.getActionDefinitionId();
    }

    public String getDescription() {
        return delegate.getDescription();
    }

    public ImageDescriptor getDisabledImageDescriptor() {
        return delegate.getDisabledImageDescriptor();
    }

    public HelpListener getHelpListener() {
        return delegate.getHelpListener();
    }

    public ImageDescriptor getHoverImageDescriptor() {
        return delegate.getHoverImageDescriptor();
    }

    public String getId() {
        return delegate.getId();
    }

    public ImageDescriptor getImageDescriptor() {
        return delegate.getImageDescriptor();
    }

    public IMenuCreator getMenuCreator() {
        return delegate.getMenuCreator();
    }

    public int getStyle() {
        return delegate.getStyle();
    }

    public String getText() {
        return delegate.getText();
    }

    public String getToolTipText() {
        return delegate.getToolTipText();
    }

    public boolean isChecked() {
        return delegate.isChecked();
    }

    public boolean isEnabled() {
        return delegate.isEnabled();
    }

    public boolean isHandled() {
        return delegate.isHandled();        
    }

    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        delegate.removePropertyChangeListener(listener);
    }

    public void run() {
        delegate.run();
    }

    /**
     * This is a decorator method for the <code>IAction</code> delegate.
     * It expands all the collapsed regions before printing takes place,
     * then prints the document and finally return the document to its original
     * structure.
     */
    @SuppressWarnings("unchecked")
    public void runWithEvent(Event event) {
        /*
         * Expand all regions before printing the document (this is the default behaviour).
         * Then the document will be returned to its original structure, i.e. those regions
         * that were not expanded originally will be left collapsed.
         */
        ProjectionAnnotationModel model = ((T24BasicEditor)EditorDocumentUtil.getActiveEditor()).getProjectionAnnotationModel();
        List<ProjectionAnnotation> notExpAnList = new ArrayList<ProjectionAnnotation>();
        
        Iterator<ProjectionAnnotation> it = model.getAnnotationIterator();
        while(it.hasNext()){
            ProjectionAnnotation an = (ProjectionAnnotation)it.next();
            if(an.isCollapsed()){
                /** the annotation is not collapsed, so expand it now, and keep track of it in the list */
                notExpAnList.add(an);
                model.expand(an);
            }
        }
        
        /** Runs this action, passing the triggering SWT event. */
        delegate.runWithEvent(event);
        
        /** Restore collapsed annotations */
        for(ProjectionAnnotation an : notExpAnList){
            model.collapse(an);
        }
    }

    public void setAccelerator(int keycode) {
        delegate.setAccelerator(keycode);        
    }

    public void setActionDefinitionId(String id) {
        delegate.setActionDefinitionId(id);        
    }

    public void setChecked(boolean checked) {
        delegate.setChecked(checked);
    }

    public void setDescription(String text) {
        delegate.setDescription(text);
    }

    public void setDisabledImageDescriptor(ImageDescriptor newImage) {
        delegate.setDisabledImageDescriptor(newImage);
    }

    public void setEnabled(boolean enabled) {
        delegate.setEnabled(enabled);
    }

    public void setHelpListener(HelpListener listener) {
        delegate.setHelpListener(listener);
    }

    public void setHoverImageDescriptor(ImageDescriptor newImage) {
        delegate.setHoverImageDescriptor(newImage);        
    }

    public void setId(String id) {
        delegate.setId(id);        
    }

    public void setImageDescriptor(ImageDescriptor newImage) {
        delegate.setImageDescriptor(newImage);        
    }

    public void setMenuCreator(IMenuCreator creator) {
        delegate.setMenuCreator(creator);        
    }

    public void setText(String text) {
        delegate.setText(text);
    }

    public void setToolTipText(String text) {
        delegate.setToolTipText(text);        
    }
    
}
