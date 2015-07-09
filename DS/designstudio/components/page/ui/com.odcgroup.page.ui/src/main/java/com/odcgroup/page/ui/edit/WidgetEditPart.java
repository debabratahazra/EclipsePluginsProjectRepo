package com.odcgroup.page.ui.edit;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.views.properties.IPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.TranslationSupport;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;
import com.odcgroup.page.model.impl.EventImpl;
import com.odcgroup.page.model.impl.TranslationImpl;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.edit.handler.DomainObjectRequestHandler;
import com.odcgroup.page.ui.edit.handler.WidgetRequestHandler;
import com.odcgroup.page.ui.figure.FigureFactory;
import com.odcgroup.page.ui.figure.IWidgetFigure;
import com.odcgroup.page.ui.figure.table.CompartmentFigure;
import com.odcgroup.page.ui.figure.table.TableColumnFigure;
import com.odcgroup.page.ui.figure.table.TableGroupFigure;
import com.odcgroup.page.ui.properties.IWidgetPropertySource;
import com.odcgroup.page.uimodel.EditorMode;
import com.odcgroup.page.uimodel.RendererInfo;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;
import com.odcgroup.page.util.ClassUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.richtext.RichTextUtils;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * The EditPart for Widgets in design mode.
 * 
 * @author atr
 */
public class WidgetEditPart extends AbstractGraphicalEditPart implements Adapter {
	
	private static Logger logger = LoggerFactory.getLogger(WidgetEditPart.class);

	/** The target of notifications. */
	private Notifier target;

	/** The PropertySource from which we receive notifications. */
	private IWidgetPropertySource propertySource = null;

	/** The FigureFactory used to create figures. */
	private FigureFactory figureFactory;

	/** The command stack */
	private CommandStack commandStack;
	
	/** Flag indicating if we are in design mode. */
	private boolean designMode;

	/** the ofs project that owns the edited model */
	private IOfsProject ofsProject = null;
	
	/** The translation manager */
	private ITranslationManager translationManager = null; 
	
	/** The widget's translations, can be null if not supported */
	private ITranslation translation;
	
	private Object lock = new Object();
	
	
	/**
	 * A property change occurs.
	 * 
	 * @param property
	 */
	private void handleWidgetPropertyChange(final Property property) {
		Runnable r = new Runnable() {
			public void run() {
				
				IWidgetFigure figure = (IWidgetFigure)getFigure();
				
				String propName = property.getTypeName();

				// Update the figure visibility only if it has been changed in the model
				if (PropertyTypeConstants.IS_WIDGET_VISIBLE.equals(propName)) {
					boolean isVisible = getWidget().isVisible();
					if (isVisible != figure.isVisible()) {
						figure.setVisible(isVisible);
					}
				// Update other figure properties
				} else if(figure instanceof TableGroupFigure) {
					IWidgetFigure parentFigure = null;
					if("group-rank".equals(propName)) {
						if(figure instanceof TableGroupFigure) {	
							CompartmentFigure fig = (CompartmentFigure)figure.getParent();
							if(fig != null) {
								parentFigure = (TableColumnFigure)fig.getParent();
							}
						}
						if(parentFigure != null) {
							parentFigure.notifyPropertyChange(propName);
						}
						
					} else {
						figure.notifyPropertyChange(propName);
					}
				} 
				else {
					figure.notifyPropertyChange(propName);
				}
			}		
		};
		
		if (Display.getCurrent() != null) {
			r.run();
		} else {
			Display.getDefault().asyncExec(r);
		}

	}
	
	
	private void handleWidgetChange(final Widget widget) {
		Runnable r = new Runnable() {
			public void run() {
				IWidgetFigure figure = (IWidgetFigure)getFigure();
				//figure.notifyChange();
			}		
		};
		
		if (Display.getCurrent() != null) {
			r.run();
		} else {
			Display.getDefault().asyncExec(r);
		}
	}
	
	/**
	 * This listener help to manage dynamic key handlers for each concrete edit part. Key handlers are adapted when
	 * selection of an edit part changes
	 */
	private EditPartListener editPartListener = new EditPartListener.Stub() {
		public void selectedStateChanged(EditPart editpart) {
			if (editpart instanceof WidgetEditPart) {
				WidgetEditPart wep = (WidgetEditPart) editpart;
				if (editpart.getSelected() == EditPart.SELECTED_PRIMARY) {
					WidgetEditPartContextKeyHandler.activateKeyHandlers(wep,getViewer());
				} else if (editpart.getSelected() == EditPart.SELECTED_NONE) {
					WidgetEditPartContextKeyHandler.deactivateKeyHandlers(wep);
				}
			}
		}
	};
	
	/** The IPropertyChangeListener listens essentially to changes to the CorporateDesign. */
	 private IPreferenceChangeListener corporateDesignListener = new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				Runnable r = new Runnable() {
					public void run() {
						((IWidgetFigure) getFigure()).preferenceChange();
					}		
				};
				if (Display.getCurrent() != null) {
					r.run();
				} else {
					Display.getDefault().asyncExec(r);
				}				
			}
		};
		
	/** The IPropertyChangeListener listens essentially to changes to the CorporateImages. */
	 private IPreferenceChangeListener corporateImagesListener = new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				Runnable r = new Runnable() {
					public void run() {
						((IWidgetFigure) getFigure()).preferenceChange();
					}		
				};
				if (Display.getCurrent() != null) {
					r.run();
				} else {
					Display.getDefault().asyncExec(r);
				}				
			}
		};
		
	/** The delta visitor (used with the ResourceChangeListener. */	
	private IResourceDeltaVisitor deltaVisitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) throws CoreException {
				IResource res = delta.getResource();
				IProject project = res.getProject();
				if (project != null && project.isOpen() && !project.hasNature(OfsCore.NATURE_ID)) {
					return false;
				}				
				((IWidgetFigure) getFigure()).resourceChange();
				return true;
			}
	};
		
	/** The resource change listener. */	
	 private IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
    	public void resourceChanged(IResourceChangeEvent event) {
    		try {
    			IResourceDelta delta = event.getDelta();
    			if (delta != null) {
    				delta.accept(deltaVisitor);
    			}
    		} catch(CoreException ce) {
    			// Ignored
    		}	
    	}
    };	
    
    /** Translation change listener */
	final boolean[] busy = {false};
    private ITranslationChangeListener translationChangeListener = new ITranslationChangeListener(){
		public void notifyChange(final ITranslationChangeEvent event) {
			if (translation == null) return; // 
			if (busy[0]) return;
			busy[0] = true;
			Runnable r = new Runnable() {
				public void run() {
					synchronized (lock) {
						if (translation == null) {
							return;
						}
						
						if (ITranslationKind.NAME.equals(event.getKind())) {
							String text = "";
							try {
								text = translation.getText(ITranslationKind.NAME, translationManager.getPreferences().getDefaultLocale());
							} catch (TranslationException ex) {
								// In general the cause is that the dataset/attribue that owns the translation cannot be found
								logger.warn("Widget ["+getWidget().getTypeName()+"] has no label, check dataset/attribute"/*, ex*/);
							} finally {
								IWidgetFigure wFigure = (IWidgetFigure)getFigure();
								if (getWidget().getType().isRichtext()) {
									try {
										text = RichTextUtils.removeRichTextDecorator(text);
									} catch (Exception ex) {
										logger.warn("Widget ["+getWidget().getTypeName()+"] cannot filter rich text decorators."/*, ex*/);
									}
								}
								wFigure.setText(text);
								busy[0] = false;
							}
						} else if (ITranslationKind.TEXT.equals(event.getKind())) {
							String toolTip = "";
							try {
								toolTip = translation.getText(ITranslationKind.TEXT, translationManager.getPreferences().getDefaultLocale());
							} catch (TranslationException ex) {
								// In general the cause is that the dataset/attribue that owns the translation cannot be found
								logger.warn("Widget ["+getWidget().getTypeName()+"] has no tooltip, check dataset/attribute"/*, ex*/);
							} finally {
								IWidgetFigure wFigure = (IWidgetFigure)getFigure();
								wFigure.setToolTip(toolTip);
								busy[0] = false;
							}
						}
						busy[0] = false;
					}
				}
			};
			if (Display.getCurrent() != null) {
				r.run();
			} else {
				Display.getDefault().asyncExec(r);
			}				
		}
	};
	
	/**
	 * Creates the figure (view) controlled by this EditPart.
	 * 
	 * @return IFigure The figure (view) controlled by this EditPart
	 */
    @Override
	protected IFigure createFigure() {
		Widget widget = getWidget();
		IWidgetFigure figure = getFigureFactory().create(widget);
		if (null == figure) {
			String message = "No RendererInfo defined for this Widget: " + widget.getTypeName();
			logger.error(message);
			throw new PageException(message);
		}
		return figure;
	}
    
	@Override
	public IFigure getFigure() {
		if (super.figure == null) {
			figure = createFigure();
		}

		return figure;
	}

	/**
	 * Gets the offset to apply to the Location caused by the fact that the scrollbar is active.
	 * 
	 * @return Point
	 */
	public Point getScrollbarOffset() {
		ScrollingGraphicalViewer sgv = (ScrollingGraphicalViewer) getViewer();
		FigureCanvas fc = (FigureCanvas) sgv.getControl();
		int hValue = fc.getHorizontalBar().getSelection();
		int vValue = fc.getVerticalBar().getSelection();
		return new Point(hValue, vValue);
	}

	/**
	 * Sets the command stack
	 * 
	 * @param commandStack
	 *            The command stack
	 */
	public final void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	/**
	 * @return the current command stack
	 */
	public final CommandStack getCommandStack() {
		return this.commandStack;
	}

	/**
	 * Gets the FigureFactory used to create figures.
	 * 
	 * @return FigureFactory
	 */
	public final FigureFactory getFigureFactory() {
		return figureFactory;
	}

	/**
	 * Sets the FigureFactory used to create figures.
	 * 
	 * @param figureFactory
	 *            The FigureFactory used to create figures
	 */
	public final void setFigureFactory(FigureFactory figureFactory) {
		this.figureFactory = figureFactory;
	}

	/**
	 * Upon activation, attach to the model element as an adapter.
	 */
	public void activate() {
		if (!isActive()) {
			Widget w = getWidget();
			WidgetUtils.addWidgetPropertiesObserver(w, this);
			w.eAdapters().add(this);
			
			activateTranslations();
			
			addEditPartListener(editPartListener);
			
			if (ofsProject != null) {
				CorporateDesignUtils.addPreferenceChangeListener(ofsProject, corporateDesignListener);
				CorporateImagesUtils.addPreferenceChangeListener(ofsProject, corporateImagesListener);
			}			
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
			
			super.activate();
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() {
		if (isActive()) {
			Widget w = getWidget();
			WidgetUtils.removeWidgetPropertiesObserver(w, this);
			w.eAdapters().remove(this);
			removeEditPartListener(editPartListener);
			
			if (ofsProject != null) {
				CorporateDesignUtils.removePreferenceChangeListener(ofsProject, corporateDesignListener);
				CorporateImagesUtils.removePreferenceChangeListener(ofsProject, corporateImagesListener);
			}
			
			synchronized (lock) {
				if (translation != null) {
					translation.removeTranslationChangeListener(this.translationChangeListener);
					translation = null;
				}
			}

			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
			
			super.deactivate();
		}
	}

	/**
	 * Handle requests.
	 * 
	 * @param request
	 *            The Request to handle
	 */
	public void performRequest(Request request) {
		// Direct edit requests
		if (request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEditRequest(request);
		}
	}
	
	/**
	 * Performs a direct edit request.
	 * 
	 * @param request
	 *            The Request to handle
	 */
	public void performDirectEditRequest(Request request) {
		UIModel model = UIModelRegistry.getUIModel();
		Widget widget = getWidget();
		RendererInfo ri = model.getRenderers().findRendererInfo(widget.getType());
		EditorMode em = ri.getDirectEditMode();
		boolean directEditingEnabled = em == EditorMode.ALL
								    || em == EditorMode.DESIGN_MODE && isDesignMode()
								    || em == EditorMode.PREVIEW_MODE && ! isDesignMode(); 
		if ( directEditingEnabled && !widget.isDomainWidget() ) {
			LocationRequest lr = (LocationRequest) request;
			DirectEditManager dem = null;
			if (StringUtils.isEmpty(ri.getDirectEditManager())) {
				dem = new WidgetDirectEditManager(this, lr);
			} else {
				dem = (DirectEditManager) ClassUtils.newInstance(getClass().getClassLoader(), ri.getDirectEditManager(), new Object[]{this, lr});
			}
			dem.show();
		} else {
			// Other requests defined in the metamodel
			
			WidgetRequestHandler handler = null;
			String className = ri.getRequestHandler();
			if (! StringUtils.isEmpty(className)) {
				handler = (WidgetRequestHandler) 
					ClassUtils.newInstance(getClass().getClassLoader(), className);
			} else {
			    // Try to see if the Widget is linked to a Domain Model object
			    handler = new DomainObjectRequestHandler();
			}
			if (handler != null) {
				handler.handle(ofsProject, getCommandStack(), getWidget());
			}
		}
	}

	/**
	 * Gets the parent model cast as a Widget.
	 * 
	 * @return Widget The parent model cast as a Widget
	 */
	public Widget getParentWidget() {
		return (Widget) getParent().getModel();
	}

	/**
	 * Gets the children of this Widget.
	 * 
	 * @return List The children of this Widget
	 */
	@SuppressWarnings("rawtypes")
	protected List getModelChildren() {
		return getWidget().getContents();
	}

	/**
	 * Gets the model cast as a Widget.
	 * 
	 * @return Widget The model cast as a Widget
	 */
	public Widget getWidget() {
		return (Widget) getModel();
	}
	
	/**
	 * @return ITranslation
	 */
	public ITranslation getTranslation() {
		synchronized (lock) {
			if (null == translation) {
				translation = translationManager.getTranslation(getWidget());
				if (translation != null) {
					translation.addTranslationChangeListener(translationChangeListener);
				}
			} 
			return translation;
		}
	}
	
	/**
	 * @return ITranslationModel
	 */
	public ITranslationModel getTranslationModel() {
		synchronized (lock) {
			if (translation == null) {
				translation = getTranslation();
			} 
			ITranslationModel model = null;
			if (translation != null) {
				ITranslationManager manager = TranslationCore.getTranslationManager(ofsProject.getProject());
				model = new TranslationModel(manager.getPreferences(), translation);
			}
			return model;
		}
	}

	/**
	 * Returns true if this WidgetPart is the root WidgetEditPart.
	 * 
	 * @return boolean True if this WidgetPart is the root WdigetEditPart
	 */
	public boolean isRootEditPart() {
		return getWidget().getParent() == null;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class) override the default behavior defined in
	 *      AbstractEditPart which would expect the model to be a property sourced. instead the editpart can provide a
	 *      property source
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			return getPropertySource();
		} else if (IWidgetPropertySource.class == key) {
			return getPropertySource();
		} else if (Widget.class == key) {
			return getWidget();
		} else if (IProject.class == key) {
			return ofsProject != null ? ofsProject.getProject() : null;
		}
		return super.getAdapter(key);
	}

	/**
	 * Creates the EditPolicies for this EditPart. We don't install any edit policies by default. Instead the
	 * EditPartFactory installs them. This is because the WidgetEditPart can be used both in design mode and preview
	 * mode.
	 */
	protected void createEditPolicies() {
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
	 */
	public Notifier getTarget() {
		return target;
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification notification) {
		// notify the figure of any widget's property change
		final IFigure fig = getFigure();
		if (fig != null && fig instanceof IWidgetFigure) {
			Object notifier = notification.getNotifier();
			if (notifier instanceof Property) {
				handleWidgetPropertyChange((Property)notifier);
			}
			Object event = notification.getNewValue();
			Object oldEvent = notification.getOldValue();
			if(event instanceof EventImpl || event instanceof TranslationImpl) {
				handleWidgetChange((Widget)notifier);
			}
			if(oldEvent != null && oldEvent instanceof EventImpl) {
				handleWidgetChange((Widget)notifier);
			}
		} 

		//WidgetEditPartContextKeyHandler.updateActions(this);
		refreshAll();
	}
	


	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#registerModel()
	 */
	@Override
	protected void registerModel() {
		super.registerModel();
		if (getViewer().getControl() == null) {
			return;
		}

		// add a listener to dispose properly allocated resource by figures
		getViewer().getControl().addListener(SWT.Dispose, new Listener(){
			public void handleEvent(Event event) {
				
				IFigure fig = getFigure();
				
				if (fig instanceof IWidgetFigure) {
					((IWidgetFigure)fig).dispose();
				}
			}
		});
	}
	
	private void activateTranslations() {
		
		Widget widget = getWidget();
		if (widget != null) {
		
			IFigure fig = getFigure();
			if (fig instanceof IWidgetFigure) {
				IWidgetFigure wFigure = (IWidgetFigure)fig;
				
				if (widget.getType().translationSupported()) {
					/*
					 * Transfer translations from the widget to the figure
					 */
					Locale defLocale = TranslationCore.getTranslationManager(ofsProject.getProject()).getPreferences()
							.getDefaultLocale();

					ITranslation translation = getTranslation();
					if (translation != null) {
						TranslationSupport ts = widget.getType().getTranslationSupport();
						if (TranslationSupport.NAME.equals(ts) || TranslationSupport.NAME_AND_TEXT.equals(ts)) {
							try {
								String text = translation.getText(ITranslationKind.NAME, defLocale);
								if (text != null && getWidget().getType().isRichtext()) {
									try {
										text = RichTextUtils.removeRichTextDecorator(text);
									} catch (Exception ex) {
										logger.warn("Widget [" + getWidget().getTypeName()
												+ "] cannot filter rich text decorators."/*
																						 * ,
																						 * ex
																						 */);
									}
								}
								wFigure.setText(text);
							} catch (TranslationException e) {
								wFigure.setText("");
							}
						}
						if (TranslationSupport.TEXT.equals(ts) || TranslationSupport.NAME_AND_TEXT.equals(ts)) {
							try {
								wFigure.setToolTip(translation.getText(ITranslationKind.TEXT, defLocale));
							} catch (TranslationException e) {
								wFigure.setToolTip("");
							}
						}
					} else {
						wFigure.setText("");
						wFigure.setToolTip("");
					}
				}
			}
		}
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#setModel(java.lang.Object)
	 */
	@Override
	public void setModel(Object model) {
		super.setModel(model);
		if (ofsProject == null) {
			Widget widget = getWidget();
			if (widget != null) {
				ofsProject = OfsResourceHelper.getOfsProject(widget.getRootWidget().eResource());
			}
		}
		if (translationManager == null) {
		  translationManager = TranslationCore.getTranslationManager(ofsProject.getProject());
		}
	}
		

	/**
	 * Refresh All.
	 * This is needed because sometimes when the bounds
	 * get larger some of the last Widgets in the figure are not painted correctly 
	 */
	public void refreshAll() {
		// Refresh this EditPart
		refreshVisuals();

		// Refresh the children since this Widget may contain other Widgets
		refreshChildren();

		// Refresh the root figure. This is needed because sometimes when the bounds
		// get larger some of the last Widgets in the figure are not painted correctly
		WidgetEditPart rootWep = this;
		EditPart ep = rootWep;
		while (true) {
			if (ep instanceof WidgetEditPart) {
				rootWep = (WidgetEditPart) ep;
				ep = ep.getParent();
			} else {
				break;
			}
		}

		final IFigure fig = rootWep.getFigure();
		if (fig != null) {
			Runnable r = new Runnable() {
				public void run() {
					fig.revalidate();
					fig.repaint();
				}		
			};
			if (Display.getCurrent() != null) {
				r.run();
			} else {
				Display.getDefault().asyncExec(r);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}

	/**
	 * Gets the PropertySource.
	 * 
	 * @return IPropertySource
	 */
	protected IWidgetPropertySource getPropertySource() {
		if (propertySource == null) {
			propertySource = (IWidgetPropertySource)
				Platform.getAdapterManager().getAdapter(this, IWidgetPropertySource.class);
		}
		return propertySource;
	}

	/**
	 * Returns true if we are in design mode.
	 * 
	 * @return boolean
	 */
	public boolean isDesignMode() {
		return designMode;
	}

	/**
	 * Sets the design mode flag.
	 * 
	 * @param designMode True if we are in design mode
	 */
	public void setDesignMode(boolean designMode) {
		this.designMode = designMode;
	}
	
	/**
	 * Overridden to allow the DragTracker to be specified in the metamodel.
	 * 
	 * @param request The request
	 * @return DragTracker
	 */
	public DragTracker getDragTracker(Request request) {
		DragTracker tracker = null;
		WidgetType wType = getWidget().getType();
		UIModel uimodel = UIModelRegistry.getUIModel();
		RendererInfo ri = uimodel.getRenderers().findRendererInfo(wType);
		String className = ri.getDragTracker();
		if (StringUtils.isEmpty(className)) {
			tracker = super.getDragTracker(request);
		} else {
			tracker = (DragTracker) ClassUtils.newInstance(getClass().getClassLoader(), className, this);
		}
		return tracker;
	}

}