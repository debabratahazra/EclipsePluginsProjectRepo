package com.odcgroup.page.ui.command;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.impl.TranslationImpl;
import com.odcgroup.page.model.snippets.ISnippetFactory;

/**
 * Replaces a Widget Event with another one. The other Event
 * is based on a copy of the original one. Note that it is important
 * to conserve the xmi:id's otherwise merging files will become extremely
 * difficult.
 * 
 * @author Gary Hayes
 */
public class ReplaceWidgetEventCommand extends BaseCommand {

    /** The widget to add the event to. */
    private Widget widget;
    
    /** The event to remove. */
    private Event oldEvent;
    
    /** The event to add. */
    private Event newEvent;
    
    /** The mapping between the ids and the old EObjects. */
    private Map<EObject, String> oldIdsMap = new IdentityHashMap<EObject, String>();
    
    /** The mapping between the ids and the new EObjects. */
    private Map<EObject, String> newIdsMap = new IdentityHashMap<EObject, String>();  
    
    /**
     * Creates a new ReplaceWidgetEventCommand.
     *   
     * @param oldEvent
     *          The old event
     * @param newEvent The new event
     */
    public ReplaceWidgetEventCommand(Event oldEvent, Event newEvent) {
        Assert.isNotNull(oldEvent);
        Assert.isNotNull(newEvent);
        
        this.widget = oldEvent.getWidget();
        this.oldEvent = oldEvent;
        this.newEvent = newEvent;
        
        // Create the id's map for the old event. We do this now because the id's are
        // removed from the resource when the Event is detached from the Widget
        if(oldEvent.eResource() instanceof XMLResource) {
	        createOldIdsMap(); 
	        createNewIdsMap();
        }
        
        setLabel("Replace "+ oldEvent.getEventName()+" Event");
    }    
    
    /**
     * Executes the Command.
     */
    public void execute() {
    	int index = widget.getEvents().indexOf(oldEvent);
        widget.getEvents().remove(oldEvent);
        widget.getEvents().add(index, newEvent);
        
        // Update the xmi:id's of each newEvent
        if(oldEvent.eResource() instanceof XMLResource) {
        	updateIds(newIdsMap);
        }
    }

    /**
     * Undoes the Command.
     */
    public void undo() {
        widget.getEvents().add(oldEvent);
        widget.getEvents().remove(newEvent);
        
        // Update the xmi:id's of each newEvent
        if(oldEvent.eResource() instanceof XMLResource) {
        	updateIds(oldIdsMap);
        }
    } 
    
    /**
     * Creates the mapping between the old EObject's and the id's.
     */
    private void createOldIdsMap() {  
        // First map the event
        XMLResource r = (XMLResource) oldEvent.eResource();
        String id = r.getID(oldEvent);
        oldIdsMap.put(oldEvent, id);
        
        // Next map the parameters and properties
        Iterator<EObject> it = oldEvent.eAllContents();
        while (it.hasNext()) {
            EObject e = it.next();
            r = (XMLResource) e.eResource();
            id = r.getID(e);
            oldIdsMap.put(e, id);
        }
    }
    
    /**
     * Creates the mapping between the new EObject's and the id's.
     */
    private void createNewIdsMap() {
        // First map the event - The new event has the same id as the old event
        XMLResource r = (XMLResource) oldEvent.eResource();
        String id = r.getID(oldEvent);
        newIdsMap.put(newEvent, id);
        
        // Next map the parameters and properties
        Iterator<EObject> it = newEvent.eAllContents();
        while (it.hasNext()) {
            EObject e = it.next();
            if (e instanceof Parameter) {
                Parameter np = (Parameter) e;
                Parameter op = oldEvent.findParameter(np.getName());
                if (op != null) {
                    id = r.getID(op);
                    newIdsMap.put(np, id);
                }
            } else if (e instanceof Property) {
                Property np = (Property) e;
                Property op = null;
                EObject container = np.eContainer();
                if (container instanceof Snippet) {
                	Snippet oldSnip = fetchOldSnippet((Snippet) container);
                	if (oldSnip != null) {
                		op = oldSnip.findProperty(np.getTypeName());
                	}
                } else if (container instanceof Event) {
                    op = oldEvent.findProperty(np.getTypeName());
                }
                if (op != null) {
                    id = r.getID(op);
                    newIdsMap.put(np, id);
                }
            } else if (e instanceof Snippet) {
            	Snippet snip = (Snippet) e;
            	Snippet oldSnip = fetchOldSnippet(snip);
            	if (oldSnip != null) {
            		id = r.getID(oldSnip);
            		newIdsMap.put(snip, id);
            	}
            } else if (e instanceof TranslationImpl){
            	TranslationImpl trans = (TranslationImpl) e;
            	id = r.getID(trans);
            	newIdsMap.put(trans, id);
            } else {
                throw new PageException("Unknown EObject: " + e.getClass().getName());
            }
        }
    }
    
    /**
     * @param snippet
     * @return snippet
     */
    private Snippet fetchOldSnippet(Snippet snippet) {
    	String typeName = snippet.getTypeName();
    	if (typeName.equals(ISnippetFactory.QUERY_SNIPPETTYPE)) {
    		//only one query exists per event
    		for (Snippet oldsnippet : oldEvent.getSnippets()) {
    			if (oldsnippet.getTypeName().equals(typeName)) {
    				return oldsnippet;
    			}
    		}
    	} else if (typeName.equals(ISnippetFactory.FILTERSET_SNIPPETTYPE)) {
    		// multiple filtersets exists in an event, fetch by index
    		int index = newEvent.getSnippets().indexOf(snippet);
    		if (oldEvent.getSnippets().size() > index) {
    			Snippet old =  oldEvent.getSnippets().get(index);
    			// fetch by ID
				String id = PropertyTypeConstants.ID;
				String newId = snippet.findProperty(id).getValue();
				String oldId = old.findProperty(id).getValue();
				if (newId.equals(oldId)) {
					return old;
				}    
    		}
    	} else if (typeName.equals(ISnippetFactory.FILTER_SNIPPETTYPE)) {
    		// multiple filters per filterset
    		Snippet fs = (Snippet) snippet.eContainer();
    		int filterIndex = fs.getContents().indexOf(snippet);
    		int index = newEvent.getSnippets().indexOf(fs);
    		if (oldEvent.getSnippets().size()> index) {
    			Snippet fssnip = oldEvent.getSnippets().get(index);   
    			if (fssnip.getContents().size() > filterIndex) {
    				return fssnip.getContents().get(filterIndex);
    			}
    		}
    	} else if (typeName.equals(ISnippetFactory.QUERYTABDISPLAY_SNIPPETTYPE)) {
    		Snippet querySnippet = (Snippet) snippet.eContainer();
    		Snippet oldquery = fetchOldSnippet(querySnippet);
    		if (!oldquery.getContents().isEmpty()) {
    			return oldquery.getContents().get(0);
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Updates the id's of each EObject in the map.
     * 
     * @param map The map of EObject's to update
     */
    private void updateIds(Map<EObject, String> map) {
        Iterator<Map.Entry<EObject, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<EObject, String> entry = it.next();
            EObject e = entry.getKey();
            String id = entry.getValue();
            XMLResource r = (XMLResource) e.eResource();
            r.setID(e, id);
            
        }
    }
}