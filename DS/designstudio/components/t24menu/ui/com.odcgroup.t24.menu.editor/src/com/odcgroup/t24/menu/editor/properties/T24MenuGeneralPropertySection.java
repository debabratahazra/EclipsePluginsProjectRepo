package com.odcgroup.t24.menu.editor.properties;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.menu.editor.dialog.ResourceSelectionDialogCreator;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuPackage;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

public class T24MenuGeneralPropertySection extends MenuGeneralPropertySection {
	
	private static Logger logger = LoggerFactory.getLogger(T24MenuGeneralPropertySection.class);

	@Inject
	private IXtextEObjectSearch eObjectSearch;
	
	@Inject 
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;
	
	@Inject 
	private IResourceServiceProvider.Registry reg;
	
	private BrowsePropertyWidget[] browseWidgets = {};

	private EReference enquiryReference = MenuPackage.eINSTANCE.getMenuItem_Enquiry();
	private EReference versionReference = MenuPackage.eINSTANCE.getMenuItem_Version();
	private EReference compositeScreenReference = MenuPackage.eINSTANCE.getMenuItem_CompositeScreen();
	private EReference includedMenuReference = MenuPackage.eINSTANCE.getMenuItem_IncludedMenu();
	private EReference applicationReference = MenuPackage.eINSTANCE.getMenuItem_Application();
	
	@Override
	protected void configureProperties() {
		super.configureProperties();
		
		
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify the location of a resource :") {
			
			@Override
			protected Group createParentGroup(Composite parent, int columns, String label) {
				Group group = new Group(parent, SWT.NONE);
				group.setBackground(parent.getBackground());
				if (label != null)
					group.setText(label);
				GridLayout gridLayout = new GridLayout(columns, false);
				gridLayout.marginWidth = 1;
				gridLayout.marginHeight = 1;
				group.setLayout(gridLayout);
				group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				return group;			
			}
			
			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				if(widget.getSelection()) {
					int currentIndex = getRadioButtonIndex(widget);
					if (currentIndex != getSelectionIndex()) {
						browseWidgets[currentIndex].notifyPropertyFeatureChange(null);
					}
					setSelectionIndex(currentIndex);
					setDefaultIndex(currentIndex);
					toggleGroup(true);
				}
			}
			
			@Override
			protected int getSelection(EObject element) {
				if (element != null) {
					if (element.eGet(compositeScreenReference) != null) {
						return 0;
					}
					if (element.eGet(versionReference) != null) {
						return 1;
					}
					if (element.eGet(enquiryReference) != null) {
						return 2;
					}
					if (element.eGet(includedMenuReference) != null) {
						return 3;
					}
					if (element.eGet(applicationReference) != null) {
						return 4;
					}
				}
				return -1;
			}

			@Override
			public String getConfirmToggleMessage(EObject element) {
				return null;
			}

		};
		
		toggleWidget.setLabel("Resource: ");
		
		browseWidgets = new BrowsePropertyWidget[5];
		
		// Composite Screen
		GroupWidget cosGroup = new GroupWidget(null, "Composite Screen");
		cosGroup.setFillBoth(false);
		BrowsePropertyWidget cosBrowse = new MyBrowsePropertyWidget(compositeScreenReference, "Resource", "");
		browseWidgets[0] = cosBrowse;
		cosBrowse.setFillHorizontal(true);
		cosBrowse.setBrowseOnly(true);
		cosBrowse.setBrowseDefault(true);
		cosBrowse.setSelectionDialogCreator(
				new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, "CompositeScreen"));
		cosGroup.addPropertyFeature(cosBrowse);
		toggleWidget.addReferenceWidget(cosGroup, true);
		
		// Version
		GroupWidget versionGroup = new GroupWidget(null, "Version");
		versionGroup.setFillBoth(false);
		BrowsePropertyWidget versionBrowse = new MyBrowsePropertyWidget(versionReference, "Resource", "");
		browseWidgets[1] = versionBrowse;
		versionBrowse.setFillHorizontal(true);
		versionBrowse.setBrowseOnly(true);
		versionBrowse.setSelectionDialogCreator(
				new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, "Version"));
		versionGroup.addPropertyFeature(versionBrowse);
		toggleWidget.addReferenceWidget(versionGroup, false);

		// Enquiry
		GroupWidget enquiryGroup = new GroupWidget(null, "Enquiry");
		enquiryGroup.setFillBoth(false);
		BrowsePropertyWidget enquiryBrowse = new MyBrowsePropertyWidget(enquiryReference, "Resource", "");
		browseWidgets[2] = enquiryBrowse;
		enquiryBrowse.setFillHorizontal(true);
		enquiryBrowse.setBrowseOnly(true);
		enquiryBrowse.setSelectionDialogCreator(
				new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, "Enquiry"));
		enquiryGroup.addPropertyFeature(enquiryBrowse);
		toggleWidget.addReferenceWidget(enquiryGroup, false);

		// Included Menu
		GroupWidget includedMenuGroup = new GroupWidget(null, "Include Menu");
		includedMenuGroup.setFillBoth(false);
		BrowsePropertyWidget includedMenuBrowse = new MyBrowsePropertyWidget(includedMenuReference, "Resource", "");
		browseWidgets[3] = includedMenuBrowse;
		includedMenuBrowse.setFillHorizontal(true);
		includedMenuBrowse.setBrowseOnly(true);
		includedMenuBrowse.setSelectionDialogCreator(
				new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, "MenuRoot"));
		includedMenuGroup.addPropertyFeature(includedMenuBrowse);
		toggleWidget.addReferenceWidget(includedMenuGroup, false);
		
		// Application
		GroupWidget applicationGroup = new GroupWidget(null, "Application");
		applicationGroup.setFillBoth(false);
		BrowsePropertyWidget applicationBrowse = new MyBrowsePropertyWidget(applicationReference, "Resource", "");
		browseWidgets[4] = applicationBrowse;
		applicationBrowse.setFillHorizontal(true);
		applicationBrowse.setBrowseOnly(true);
		applicationBrowse.setSelectionDialogCreator(new ResourceSelectionDialogCreator(eObjectSearch, globalDescriptionLabelProvider, "MdfClass"));
		applicationGroup.addPropertyFeature(applicationBrowse);
		toggleWidget.addReferenceWidget(applicationGroup, false);
		
		this.addPropertyFeature(toggleWidget);
		
		// Parameters
		SimpleTextWidget parameters = new SimpleTextWidget(
				MenuPackage.eINSTANCE.getMenuItem_Parameters(), "Parameters");
		parameters.setFillHorizontal(true);
		this.addPropertyFeature(parameters);
	
	}
	
	@Override
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
		final EStructuralFeature structuralFeature = event.getPropertyFeature().getStructuralFeature();
		
		if (resetResourceReferences(structuralFeature)) {
			final IEditingDomainProvider provider = (IEditingDomainProvider) getPart().getAdapter(IEditingDomainProvider.class);
			if (provider != null) {
				final EditingDomain editingDomain = provider.getEditingDomain();
				
				final CompoundCommand compoundCommand = new CompoundCommand();
				compoundCommand.append(SetCommand.create(editingDomain, getInput(), compositeScreenReference, null));
				compoundCommand.append(SetCommand.create(editingDomain, getInput(), versionReference, null));
				compoundCommand.append(SetCommand.create(editingDomain, getInput(), enquiryReference, null));
				compoundCommand.append(SetCommand.create(editingDomain, getInput(), includedMenuReference, null));
				compoundCommand.append(SetCommand.create(editingDomain, getInput(), applicationReference, null));
				
				editingDomain.getCommandStack().execute(compoundCommand);
			}
		}
		super.propertyChanged(event);
	}
	
	/**
	 * Checks if the user has selected one of the (Composite Screen/Version/Enquiry/Include Menu) resource options
	 * 
	 * @param structuralFeature
	 * @return true if the user has selected any one of the resource options
	 */
	private boolean resetResourceReferences(final EStructuralFeature structuralFeature) {
		return compositeScreenReference.equals(structuralFeature) || versionReference.equals(structuralFeature)
				|| enquiryReference.equals(structuralFeature) || includedMenuReference.equals(structuralFeature) 
				|| applicationReference.equals(structuralFeature);
	}
	
	public T24MenuGeneralPropertySection() {
		
	}
	
	private class MyBrowsePropertyWidget extends BrowsePropertyWidget {
		
		@SuppressWarnings("unused")
		private EStructuralFeature feature;
		
		private Link resourcelink;
		
//		@Override
//		public void widgetSelected(SelectionEvent e) {
//			IPropertySelectionDialogCreator selectionDialogCreator = getSelectionDialogCreator();
//			if (selectionDialogCreator != null) {
//				IPropertySelectionDialog dialog = selectionDialogCreator.createDialog(getShell(), getRootElement());
//				final int resultCode = dialog.open();
//				if (resultCode == IDialogConstants.OK_ID) {
//					Object obj = dialog.getSelection();
//					if (obj != null)
//						notifyPropertyFeatureChange(obj);
//				}
//			}
//		}

		@Override
		public String getResourceUri(EObject element) {
			
			// get the resource service provider for specific language
			IResourceServiceProvider rsp = null;
			if (element instanceof Enquiry) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.enquiry"));
			} else if (element instanceof Version) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.version"));
			} else if (element instanceof CompositeScreen) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.eson"));
			} else if (element instanceof MenuRoot) {
				rsp = reg.getResourceServiceProvider(URI.createURI("dummy.menu"));
			} else if (element instanceof MdfClass) {
				return ((MdfClassImpl)element).getQualifiedName().toString();
			}
			
			if (rsp != null) {
				// get the qualified for the received element
				IQualifiedNameProvider qualifiedNameProvider = rsp.get(IQualifiedNameProvider.class);
				if (qualifiedNameProvider != null) {
					QualifiedName qName = qualifiedNameProvider.getFullyQualifiedName(element);
					if (qName != null) {
						IQualifiedNameConverter qnc = rsp.get(IQualifiedNameConverter.class);
						return qnc.toString(qName);
					}
				}
			}
			
			// if the qualified cannot be found, simply return the uri of the resource
			// that contains the element.
			Resource resource = element.eResource();
			if (resource != null) {
				return resource.getURI().toString();
	 		}

			return "Cannot find the qualified name";
		}

		/**
		 * @param attribute
		 * @param label
		 */
		public MyBrowsePropertyWidget(EStructuralFeature attribute, String label) {
			super(attribute, label);
			this.feature = attribute;
		}
		
		/**
		 * @param attribute
		 * @param label
		 * @param tooltip
		 */
		public MyBrowsePropertyWidget(EStructuralFeature attribute, String label, String tooltip) {
			super(attribute, label, tooltip);
			this.feature = attribute;
		}
		
		/**
		 * Override the parent method as 'Resource' label needs to be made a
		 * reference link and when clicked on selected text respective
		 * composite/enquiry/version/menu editors to be opened.
		 */
		@Override
		public void createPropertyControl(Composite body) {
			// create label
			resourcelink = new Link(body, SWT.NONE);
			resourcelink.setLayoutData(new GridData(SWT.NONE, GridData.BEGINNING, false, false, 1, 1));
			resourcelink.setText("<a>" + getLabel() + " :</a>");

			resourcelink.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					if (!(textControl.getText() == null || textControl.getText().isEmpty())) {
						if (getInput() != null && getInput() instanceof MenuItem) {
							MenuItem item = (MenuItem) getInput();
							Resource resource = null;
							if (item.getCompositeScreen() != null) {
								resource = item.getCompositeScreen().eResource();
							} else if (item.getVersion() != null) {
								resource = item.getVersion().eResource();
							} else if (item.getEnquiry() != null) {
								resource = item.getEnquiry().eResource();
							} else if (item.getIncludedMenu() != null) {
								resource = item.getIncludedMenu().eResource();
							} else if(item.getApplication() != null){
								resource = ((MdfClassImpl)item.getApplication()).eResource();
							}
							if (resource != null) {
								IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
										.getDefaultEditor(resource.getURI().lastSegment());
								openEditor(resource, desc.getId());
							}
						}
					}
					
				}
				
				private void openEditor(Resource resource, String editorId) {
					if (resource != null) {
						IFile file = OfsResourceHelper.getFile(resource);
						try {
							if (file != null && file.exists())
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
										.openEditor(new FileEditorInput(file), editorId);
						} catch (PartInitException ex) {
							logger.error(ex.getLocalizedMessage(), ex);
						}
					}
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
				}
				
			});

			int columns = 3;
			if (isFillHorizontal()) {
				columns = 2;
			}
			Composite textComp = SWTWidgetFactory.createComposite(body, columns, false);
			this.textControl = SWTWidgetFactory.createTextField(textComp, false);
			this.textControl.setEditable(!isBrowseOnly());
			if (browseLabel == null) {
				browseLabel = "&Browse...";
			}
			this.browse = SWTWidgetFactory.createButton(textComp, browseLabel);
			if (browseTooltip != null) {
				this.browse.setToolTipText(browseTooltip);
			}
			if (!isFillHorizontal())
				SWTWidgetFactory.createFiller(textComp);

			if (!isBrowseOnly()) {
				this.textControl.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						if (isValidInput())
							notifyPropertyFeatureChange(textControl.getText());
					}

					@Override
					public void focusGained(FocusEvent e) {
						pfText = textControl.getText();
					}
				});

				if (isBrowseDefault()) {
					this.textControl.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent e) {
							if (e.getSource() instanceof Text) {
								if (textControl.getText().equals(""))
									textControl.setText("");
								else
									textControl.setText(pfText);
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
				}
			}
			this.browse.addSelectionListener(this);
		}
	}
}
