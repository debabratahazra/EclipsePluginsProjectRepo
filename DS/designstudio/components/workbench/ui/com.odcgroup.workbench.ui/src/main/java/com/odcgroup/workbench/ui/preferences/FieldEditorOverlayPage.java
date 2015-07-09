package com.odcgroup.workbench.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.resources.OfsProject;

/** This class is the base class for all OFS Workbench configuration pages.
 *  It is used for project specific settings (propertyPages). <p>
 *  Subclasses should either override the createContents() method (if a complex
 *  layout is needed) or the createFieldEditors() method (if a simple row layout
 *  suffices). Furthermore the methods getPluginId() must be implemented, so that
 *  the settings can be associated to a certain plugin. 
 */
public abstract class FieldEditorOverlayPage 
extends FieldEditorPreferencePage 
implements IWorkbenchPropertyPage
{
    /**
     * * Name of resource property for the selection of workbench or project
     * settings **
     */
    // Stores all created field editors
    protected final List<FieldEditor> editors = new ArrayList<FieldEditor>();
    // Stores owning element of properties
    private IAdaptable element;
    // The image descriptor of this pages title image
    private ImageDescriptor image;
    // Cache for page id
    private String pageID;

    public FieldEditorOverlayPage( final String title )
    {
        super( title, FieldEditorPreferencePage.GRID );
    }
    public FieldEditorOverlayPage( final String title, 
                                   final ImageDescriptor image)
    {
        super( title, image, FieldEditorPreferencePage.GRID );
        this.image = image;
    }

    protected abstract String getPluginId();

    /**
     * Receives the object that owns the properties shown in this property page.
     * 
     * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
     */
    public void setElement( final IAdaptable element )
    {
        this.element = element;
    }
    /**
     * Delivers the object that owns the properties shown in this property page.
     * 
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     */
    public IAdaptable getElement()
    {
        return element;
    }
    
	/**
	 * @return
	 */
	protected IProject getProject() {
	    IAdaptable adaptable = getElement();
	    
	    if (adaptable instanceof IProject) {
	        return (IProject) adaptable;
	    } else if (adaptable instanceof OfsProject) {
	    	return ((OfsProject)adaptable).getProject();
	    } else {
	        IProject project =  (IProject) adaptable.getAdapter(IProject.class);
	        if (project == null){
	        	OfsProject oProj = (OfsProject)adaptable.getAdapter(OfsProject.class);
	        	project = oProj.getProject();
	        }
	        return project;
	    }
	}	

    /**
     * We override the addField method. This allows us to store each field
     * editor added by subclasses in a list for later processing.
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#addField(org.eclipse.jface.preference.FieldEditor)
     */
    protected void addField( final FieldEditor editor )
    {
        editors.add(editor);
        super.addField( editor );
    }
    /**
     * We override the createControl method. In case of property pages we create
     * a new PropertyStore as local preference store.
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createControl()
     */
    public void createControl( final Composite parent )
    {
        setPreferenceStore(new ScopedPreferenceStore(new InstanceScope(), getPluginId()));
        super.createControl( parent );
    }
    
    /* (non-Javadoc)
     * Method declared on PreferencePage.
     */
    protected Control createContents(Composite parent) {
        initialize();
        checkState();
        return parent;
    }
    
	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
	}
    
    /**
     * Creates a new preferences page and opens it
     * 
     * @see com.bdaum.SpellChecker.preferences.SpellCheckerPreferencePage#configureWorkspaceSettings()
     */
    protected void configureWorkspaceSettings()
    {
        try
        {
            // create a new instance of the current class
            final IPreferencePage page = ( IPreferencePage )this.getClass().newInstance();
            page.setTitle( getTitle() );
            page.setImageDescriptor( image );
            // and show it
            showPreferencePage( pageID, page );
        }
        catch( final InstantiationException e )
        {
			OfsCore.getDefault().logError("Could not instantiate preference page", e);
        }
        catch( final IllegalAccessException e )
        {
			OfsCore.getDefault().logError("Could not access preference page", e);
        }
    }
    /**
     * Show a single preference pages
     * 
     * @param id -
     *            the preference page identification
     * @param page -
     *            the preference page
     */
    protected void showPreferencePage( final String id, 
                                       final IPreferencePage page )
    {
        final IPreferenceNode targetNode = new PreferenceNode( id, page );
        final PreferenceManager manager = new PreferenceManager();
        manager.addToRoot( targetNode );
        final PreferenceDialog dialog = new PreferenceDialog( getControl().getShell(), manager );
        BusyIndicator.showWhile( getControl().getDisplay(), new Runnable()
        {
            public void run()
            {
                dialog.create();
                dialog.setMessage( targetNode.getLabelText() );
                dialog.open();
            }
        } );
    }
}