package com.odcgroup.page.ui.edit;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY_PREFIX;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ASSOCIATION;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.IncludeabilityRule;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.AggregationUtils;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.command.CreateWidgetCommand;
import com.odcgroup.page.ui.command.DeleteWidgetCommand;
import com.odcgroup.page.ui.figure.CalculatingLayout;
import com.odcgroup.page.ui.request.IncludeRequest;
import com.odcgroup.page.ui.request.PageUIRequestConstants;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.ClassUtils;

/**
 * The ComponentEditPolicy for widgets.
 * 
 * @author Gary Hayes
 * 
 */
public class WidgetComponentEditPolicy extends ComponentEditPolicy {
	
	/**
	 * Factors the incoming Request into INCLUDE's.
	 * 
	 * @param request The Request to handle
	 * @return Command The command
	 */
	public Command getCommand(Request request) {
		if (PageUIRequestConstants.REQ_INCLUDE.equals(request.getType())) {
			return getIncludeCommand((IncludeRequest) request);
		}
		
		return super.getCommand(request);
	}	
	
	/**
	 * Gets the DeleteCommand for the Widget.
	 * 
	 * @param deleteRequest
	 *            The Request to delete the Widget
	 * @return Command The command to execute
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object obj1 =  getHost().getParent().getModel();
		Object obj2 =  getHost().getModel();
		if (! (obj1 instanceof Widget) || !(obj2 instanceof Widget)) {
			return null;
		}
		
		Widget parent = (Widget) obj1;
		Widget child = (Widget) obj2;
		
		if (parent == null) {
			// We cannot delete the root Widget
			return null;
		}

		if (! child.isDeletable()) {
			return null;
		}
		
		if (AggregationUtils.isInAggregation(child)) {
			// Cannot delete children which are part of Aggregations
			return null;
		}	
		
		// If a Widget-specific Delete Command has been added return it
		UIModel uiModel = UIModelRegistry.getUIModel();
		RendererInfo ri = uiModel.getRenderers().findRendererInfo(child.getType());
		String className = ri.getDeleteCommand();
		if (!StringUtils.isEmpty(className)) {
			return (Command) ClassUtils.newInstance(getClass().getClassLoader(), className, parent, child);	
		}
		
		// Use the default Command
		return new DeleteWidgetCommand((Widget) parent, (Widget) child);
	}
	
	/**
	 * Gets the IncludeCommand for this Widget.
	 * 
	 * @param request The Request
	 * @return Command The command to execute
	 */
	public Command getIncludeCommand(IncludeRequest request) {
		// Calculate the index of the new Widget
		Point location = WidgetEditPartUtils.getOffsetLocation(getWidgetEditPart(), request.getLocation());
		
		int index = calculateIndex(location);
		Widget includedWidget = request.getModel().getWidget();

		Widget parent = (Widget) getHost().getModel();		
		Widget rootWidget = parent.getRootWidget();
		if (WidgetUtils.canIncludeChild(rootWidget, includedWidget) == false) {
			return null;
		}
	
		WidgetType wt = getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.INCLUDE);		
		WidgetFactory wf = new WidgetFactory();
		Widget include = wf.create(wt);
		Property p = include.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
		p.setModel(request.getModel());
				
		String df = includedWidget.getPropertyValue(PropertyTypeConstants.DEFAULT_FRAGMENT);
        if ("true".equalsIgnoreCase(df)) {
            setBeanValues(parent, includedWidget, include);
        }
		
		return new CreateWidgetCommand(parent, include, index, location);
	}
	
	/**
	 * Sets the bean property values of the Widget to be created.
	 * If the Parent Widget is associated with a Domain Entity and the Child
	 * Widget is also associated with a Domain Entity we try to find the
	 * corresponding association in the Domain Model. Note that in reality there
	 * may be more than one, however this should happen very rarely.
	 * If we find the association we set the Bean Prefix and Bean Postfix in the
	 * same way that it would have been set had the Fragment been the default one.
	 * 
	 * @param parent The parent Widget
	 * @param child The child Widget
	 * @param include The include Widget
	 */
	private void setBeanValues(Widget parent, Widget child, Widget include) {
	    String datasetName = child.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
	    if(StringUtils.isNotEmpty(datasetName)) {
	    
		    String parentDatasetName = parent.findPropertyValueInParent(PropertyTypeConstants.DOMAIN_ENTITY);
		    if(StringUtils.isNotEmpty(parentDatasetName)) {
		    
			    DomainRepository repository = DomainRepository.getInstance(EclipseUtils.findCurrentProject());
			    MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
			    MdfDataset parentDataset = repository.getDataset(MdfNameFactory.createMdfName(parentDatasetName));
			    if (dataset != null) {
				    MdfName qName = MdfNameFactory.createMdfName(datasetName);
				    Iterator iter = parentDataset.getProperties().iterator();
				    while (iter.hasNext()) {
			            MdfDatasetProperty mp = (MdfDatasetProperty) iter.next();
			            if (mp.getType() instanceof MdfDataset) {
			            	MdfName mpQName = mp.getType().getQualifiedName();
			            	if (mpQName.getDomain().equals(qName.getDomain()) &&
				            	mpQName.getLocalName().equals(qName.getLocalName())) {
				        	    String beanPostfix = mp.getName();
				        	    if (mp.getMultiplicity() == MdfMultiplicity.ONE) {
				        	        beanPostfix += ".";
				        	    }
				        	    include.setPropertyValue(DOMAIN_ASSOCIATION, mp.getName());
				        	    include.setPropertyValue(BEAN_PROPERTY_PREFIX, beanPostfix);
				        	    break;
				            }
			            }
			        }
			    }
		    }
	    }

	}
	
	/**
	 * This method calculates the index of the IFigure in the parent IFigure collection
	 * which was clicked. If the user clicked between Widgets then the index
	 * after the previous IFigure is retuned.
	 * 
	 * @param location The location
	 * @return index The index
	 */
	private int calculateIndex(Point location) {
		IFigure container = getContainerFigure();		
		LayoutManager lm = container.getLayoutManager();
		if (lm instanceof CalculatingLayout) {
			CalculatingLayout cl = (CalculatingLayout) lm;
			return cl.calculateIndex(container, location);
		}
		
		 return 0;
	}
	
	/**
	 * Gets the Container (host) figure.
	 * 
	 * @return IFigure The Container (host) figure
	 */
	private IFigure getContainerFigure() {
		return ((GraphicalEditPart) getHost()).getFigure();
	}
	
	/**
	 * Gets the MetaModel.
	 * 
	 * @return MetaModel The MetaModel
	 */
	private MetaModel getMetaModel() {
		return MetaModelRegistry.getMetaModel();
	}
	
	/**
	 * Gets the host WidgetEditPart.
	 * 
	 * @return WidgetEditPart The host WidgetEditPart
	 */
	private WidgetEditPart getWidgetEditPart() {
		return (WidgetEditPart) getHost();
	}

}
