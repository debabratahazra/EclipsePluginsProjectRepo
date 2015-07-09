package com.odcgroup.t24.mdf.editor.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.t24.mdf.editor.ui.actions.NewAttributeCommand;
import com.odcgroup.workbench.core.IOfsProject;

public class LocalRefSelectionDialog extends SelectionDialog {

	private static Logger LOGGER = LoggerFactory.getLogger(LocalRefSelectionDialog.class);
	private List<MdfProperty> localRefs = null;
	private IOfsProject ofsProject = null;
	private NewAttributeCommand command = null;
	private EditingDomain domain = null;
	private TableViewer tableViewer;
	private DomainRepository repo = null;

	public LocalRefSelectionDialog(Shell shell, IOfsProject ofsProject, NewAttributeCommand command, EditingDomain domain) {
		super(shell);
		this.ofsProject = ofsProject;
		this.command = command;
		this.domain = domain;
		setTitle("Select Local Field...");
		fetchLocalRefs();
	}
	
	public List<MdfProperty> getLocalRefs() {
		return localRefs;
	}

	@SuppressWarnings("unchecked")
	private void fetchLocalRefs() {
		localRefs = new ArrayList<MdfProperty>();
		if(repo == null) {
			repo = DomainRepository.getInstance(ofsProject);	
		}		
		MdfDomainImpl domain = (MdfDomainImpl)repo.getDomain("LocalFieldsDefinition");
		if(domain != null) {
			MdfClass lClass = domain.getClass("LocalFields");
			if(lClass != null) {
				List<MdfProperty> list = lClass.getProperties();
				for (MdfProperty mdfProperty : list) {
					localRefs.add(mdfProperty);
				}
			}
		}
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		return createDialogUI(parent);
	}

	/**
	 * @param layout
	 * @param header
	 * @param weight
	 */
	private void createColumn(TableColumnLayout layout, final int columnIndex, String header, int weight) {
		TableColumn tc = new TableColumn(tableViewer.getTable(),SWT.NONE);
		tc.setText(header);
		layout.setColumnData(tc, new ColumnWeightData(weight));
	}
	
	@Override
	protected void okPressed() {
		command.setSelection(tableViewer.getSelection());
		domain.getCommandStack().execute(command);
		super.okPressed();		
	}
	
	private Composite createDialogUI(Composite parent) {
		
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);

		// create page content
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		GridData gd = new GridData();
		gd.widthHint = 500;
		topLevel.setLayoutData(gd);
		
		final LocalRefFilter filter = new LocalRefFilter();
		
		Label label = new Label(topLevel, SWT.NULL);
		label.setText("Enter name prefix or pattern (* = any character)");
		final Text localref = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		localref.setLayoutData(fieldGridData);
		localref.setText("*");
		localref.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				try {
					if(localref.getText().equals("?")) {
						tableViewer.setInput(localRefs);
						tableViewer.refresh();
					} else {
		            	filter.setName(localref.getText());
						List<MdfProperty> filteredList = new ArrayList<MdfProperty>();
						for (MdfProperty property : localRefs) {
							String name = property.getName();
							if (filter.accept(name)) {
								filteredList.add(property);
							}
						}
						tableViewer.setInput(filteredList);
						tableViewer.refresh();
					}
				}catch(Exception ex) {				
					LOGGER.error(ex.getMessage());
				}
            }
        });
		
		Group previewGroup = new Group(topLevel, SWT.SHADOW_ETCHED_IN| SWT.RESIZE);
		previewGroup.setLayout(new GridLayout(1, false));
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gd2.heightHint = 220;
		previewGroup.setLayoutData(gd2);
		previewGroup.setText("Matching Items...");

		Composite composite = new Composite(previewGroup, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		int styleTableViewer = SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.BORDER;
		tableViewer = new TableViewer(composite, styleTableViewer);
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());

		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		int length = localRefs.size();
		createColumn(layout, 0, "Local Field ID", length);
		createColumn(layout, 1, "Short Name", length);

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						filter.setName(localref.getText());
						List<MdfProperty> filteredList = new ArrayList<MdfProperty>();
						for (MdfProperty property : localRefs) {
							if (filter.accept(property)) {
								filteredList.add(property);
							}
						}
						tableViewer.setInput(filteredList);
						tableViewer.refresh();
					}
				});

		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		Composite buttonComposite = new Composite(previewGroup, SWT.RESIZE);
		buttonComposite.setLayout(new GridLayout(3, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);
		
		Label label2 = new Label(buttonComposite, SWT.RIGHT);
		GridData gd1 = new GridData();
		gd1.widthHint = 150;
		label2.setLayoutData(gd1);
		
		tableViewer.setInput(localRefs);
		return parent;
	}

	private static class ContentProvider implements IStructuredContentProvider {
		public void dispose() {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<String>)inputElement).toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private static class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			MdfProperty localRef = (MdfProperty) element;
			String result = ""; //$NON-NLS-1$
			switch (columnIndex) {
			case 0: 
				String str = localRef.getName();
				result = StringUtils.isBlank(str) ? "" : str;
				break;				
			case 1:
				MdfAnnotation annot = localRef.getAnnotation(MdfTranslation.NAMESPACE_URI, MdfTranslation.TRANSLATION_LABEL);
				result = "";
				if(annot.getProperty("en") != null) {
					String value = annot.getProperty("en").getValue();				
					result = value;
				}
				break;
			default:
				// should not reach here
				result = "";  //$NON-NLS-1$
			}
			return result;
		}
	}
}
