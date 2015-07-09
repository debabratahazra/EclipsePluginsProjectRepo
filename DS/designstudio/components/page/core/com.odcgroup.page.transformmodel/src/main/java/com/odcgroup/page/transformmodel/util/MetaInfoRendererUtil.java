package com.odcgroup.page.transformmodel.util;

import static com.odcgroup.page.transformmodel.util.HeaderCommentTemplate.DESIGN_STUDIO_HEADER;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Utillity Class for rendering DS specific header comments
 * 
 * @author Phani Kumar Kotaprolu
 *
 */
public class MetaInfoRendererUtil {
	
	
	
	/**
	 * adds DS specific header comment to the element node specified
	 * 
	 * @param context
	 * @param widget
	 * @param element
	 */
	public static void addComment(WidgetTransformerContext context, Widget widget, Element element) {
	    Object[] args = getMetaDataInfo(context, widget);
		Comment attr = context.getDocument().createComment("DSHeader");
		attr.setTextContent(MessageFormat.format(DESIGN_STUDIO_HEADER, args));
		element.appendChild(attr);
	}
	
	/**
     * returns the array of arguments required for rendering the DS Header comment
     * 
     * @param context
     * @param widget
     * @return array of arguments
     */
    protected static Object[] getMetaDataInfo(WidgetTransformerContext context, Widget widget){
    	Object[] arguments = new Object[9];
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    	// projectName
    	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
    	arguments[0]=ofsProject.getName();
    	
    	// package
    	URI uri = widget.eResource().getURI();
        String modelName = uri.lastSegment();
        List<String> sl  = uri.segmentsList();
        String pkg = "/";
        for (int i = 0; i < sl.size() - 1; ++i) {
            pkg += sl.get(i);
            if (i < sl.size() - 2) {
                pkg += "/";
            }
        }
    	arguments[1]=pkg+"/";    	
    	// modelName
    	arguments[2]=modelName;    	
    	// last mod. user
    	// last mod. date
        	arguments[3]=System.getProperty("user.name");    
        	arguments[4]=dateFormat.format(Calendar.getInstance().getTime());		
    	// gen. user
    	arguments[5]=System.getProperty("user.name");
    	// gen. time
    	arguments[6]=dateFormat.format(Calendar.getInstance().getTime());
    	// DS version
    	arguments[7]=OfsCore.getVersionNumber();
    	// Description
    	arguments[8]=widget.getPropertyValue("documentation");
    	return arguments;
    }

}
