package com.odcgroup.page.ui.dialog;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredList;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.provider.PageModelEditPlugin;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.ui.repository.ModelSelectionDialog;
import com.odcgroup.workbench.ui.repository.StringMatcher;

/**
 * The selection dialog for page models.
 * 
 * @author atr
 */
public class PageModelSelectionDialog extends ModelSelectionDialog {
    
    /** The allowed file extensions. */
    private Set<String> fileExtensions;
    
    /**  */
    private int filter;
    
    public static final int SEARCH_OUTPUT_FILTER = 1;
    public static final int AUTOCOMPLETE_DESIGN_FILTER = 2;
    public static final int XTOOLTIP_FRAGMENT_FILTER = 3;
  
	
    /**
     * Constructs a page model selection dialog.
     * 
     * @param parent The parent shell.
     * @param elementRenderer The element label provider
     * @param qualifierRenderer The qualifier label provider
     */
    public PageModelSelectionDialog(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer) {
    	super(parent, elementRenderer, qualifierRenderer);
    }
    
    /**
     * Constructs a page model selection dialog.
     * 
     * @param parent The parent shell.
     * @param elementRenderer The element label provider
     * @param qualifierRenderer The qualifier label provider
     * @param fileExtensions The allowed file extensions
     */
    public PageModelSelectionDialog(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer, Set<String> fileExtensions) {
        super(parent, elementRenderer, qualifierRenderer);
        this.fileExtensions = fileExtensions;
    }    
    
    /**
     * Constructs a page model selection dialog.
     * 
     * @param parent The parent shell.
     * @param elementRenderer The element label provider
     * @param qualifierRenderer The qualifier label provider
     * @param fileExtensions The allowed file extensions
     * @param filter
     */
    public PageModelSelectionDialog(Shell parent, ILabelProvider elementRenderer,
            ILabelProvider qualifierRenderer, Set<String> fileExtensions, int filter) {
        super(parent, elementRenderer, qualifierRenderer);
        this.fileExtensions = fileExtensions;
    	this.filter = filter;
    }   
    
    /**
     * Gets the allowed file extensions.
     * 
     * @return Set of String's
     */
    private Set<String> getFileExtensions() {
        if (fileExtensions == null) {
            fileExtensions = new HashSet<String>();
        }
        return fileExtensions;
    }
    
    /**
     * @see com.odcgroup.workbench.ui.repository.ModelSelectionDialog#createFilteredList(org.eclipse.swt.widgets.Composite)
     */
    protected FilteredList createFilteredList(Composite parent) {
        FilteredList list = super.createFilteredList(parent);
    	list.setFilterMatcher(new FileNameFilterMatcher());
        return list;
    } 
    
    /**
     * @param fileExtensions
     * @return dialog
     */
    public static PageModelSelectionDialog createDialog(Set<String> fileExtensions) {
    	return createDialog(fileExtensions, 0);
    }
    
    /**
     * Creates the PageModelSelectionDialog.
     * 
     * @param fileExtensions The allowed file extensions
     * @param filter 
     * @return PageModelSelectionDialog The dialog
     */
    public static PageModelSelectionDialog createDialog(Set<String> fileExtensions, int filter) {
    	
    	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    	
        PageModelSelectionDialog dialog = 
        	new PageModelSelectionDialog(
        			shell,
        			new OfsModelNameLabelProvider(),
        			new OfsModelPathLabelProvider(), 
        			fileExtensions, 
        			filter);

    	IOfsProject ofsProject = EclipseUtils.findCurrentProject();
        ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, fileExtensions.toArray(new String[]{}));
    	Set<IOfsModelResource> modelResources = lookup.getAllOfsModelResources();
    	
        dialog.setElements(modelResources.toArray());
        return dialog; 	
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider
     * @author atr
     */
    private static class OfsModelPathLabelProvider extends LabelProvider {
    	
    	/** 
         * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         */
        public Image getImage(Object element) {
            IOfsModelResource ofsModel = (IOfsModelResource) element;
            String fe = ofsModel.getURI().fileExtension();
            return PageModelEditPlugin.getImageFromFileExtension(fe);
        }

        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        public String getText(Object element) {
            IOfsModelResource ofsModel = (IOfsModelResource) element;
            return ofsModel.getURI().path();
        }
    };
    
    /**
     * Just return the name of the ofs model resource
     * @author atr
     */
    private static class OfsModelNameLabelProvider extends OfsModelPathLabelProvider {
        /**
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        public String getText(Object element) {
            IOfsModelResource ofsModel = (IOfsModelResource) element;
    		String name = ofsModel.getURI().lastSegment();
    		int pos = name.indexOf('.');
    		if (pos != -1) {
    			name = name.substring(0,pos);
    		}
    		return name;
        }
    }    
     
    /**
     * Performs the match based on the file name (minus the file extension).
     * This matches works in the same way as that of the pageflow.
     * 
     * @author Gary Hayes
     */
    private class FileNameFilterMatcher implements FilteredList.FilterMatcher {

    	/** The end symbol. */
        private static final char END_SYMBOL = '<';
        
        /** The any String. */
        private static final char ANY_STRING = '*';
        
        /** The matcher. */
        private StringMatcher matcher;

        /**
         * Sets the filter.
         * 
         * @param pattern The pattern
         * @param ignoreCase True if we should ignore the case
         * @param ignoreWildCards True if we should ignore wildcards
         */
        public void setFilter(String pattern, boolean ignoreCase,
                boolean ignoreWildCards) {
            matcher = new StringMatcher(adjustPattern(pattern), ignoreCase,
                    ignoreWildCards);
        }

        /**
         * Returns true if the element matches.
         * 
         * @param element The element to match
         * @return boolean True if the element matches
         */
        public boolean match(Object element) {
        	IOfsModelResource modelResource = (IOfsModelResource) element;

        	URI uri = modelResource.getURI();

        	if (! getFileExtensions().contains(uri.fileExtension())) {
        	    return false;
        	}
        	try {
            Model model;
        	model = (Model)modelResource.getEMFModel().get(0);
        	Widget root = model.getWidget();
        	String fragmenttype=root.getPropertyValue(PropertyTypeConstants.FRAGMENT_TYPE);
            if (filter > 0) {
        	       if(fragmenttype!=null&&filter==XTOOLTIP_FRAGMENT_FILTER){
		        		if(!fragmenttype.equals("xtooltip")){
		        			return false;
		        		}
		        	}
		        	String search = root.getPropertyValue("search");
		        	if(search!=null){
		        	if (filter == SEARCH_OUTPUT_FILTER) {
			        	if (!search.equals("container")) {
			        		return false;
			        	}
		        	} else if (filter == AUTOCOMPLETE_DESIGN_FILTER) {
		        		if (!search.equals("autoComplete")) {
			        		return false;
			        	}
		        	}
		          }
               }
				
        	//filter the xtooltip type fragment not to include in the fragments/module/pages.
            if(filter==0&&fragmenttype!=null&&fragmenttype.equals("xtooltip")){
        		return false;
        	}
        	} catch (IOException e) {
				return false;
			} catch (InvalidMetamodelVersionException e) {
				return false;
			}
        	// The name of the model
        	String s = uri.trimFileExtension().lastSegment();
        	
            return matcher.match(s);
        }

        /**
         * Adjusts the pattern to take into account wildcards.
         * 
         * @param pattern The pattern to adjust
         * @return String The adjusted pattern
         */
        private String adjustPattern(String pattern) {
            int length = pattern.length();

            if (length > 0) {
                switch (pattern.charAt(length - 1)) {
                    case END_SYMBOL:
                        pattern = pattern.substring(0, length - 1);
                        break;

                    case ANY_STRING:
                        break;

                    default:
                        pattern = pattern + ANY_STRING;
                }
            }
            
            return pattern;
        }
    } 
}