package com.odcgroup.translation.ui.views.table;

import java.beans.PropertyChangeListener;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.ui.command.ITranslationCommand;
import com.odcgroup.translation.ui.command.ITranslationCommandProvider;
import com.odcgroup.translation.ui.internal.TranslationTableManager;
import com.odcgroup.translation.ui.internal.views.TranslationLanguageContentProvider;
import com.odcgroup.translation.ui.internal.views.TranslationLanguageLabelProvider;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationText;

/**
 * TODO: Document me!
 *
 * @author ramapriyamn
 *
 */
public abstract class TranslationText implements ITranslationText, IPropertyChangeListener, PropertyChangeListener {

	private static final String EMPTY_STR = "";
	
	public TranslationText(IProject project, Composite parent) {
		this.project = project;
		createControls(parent);
	}

	@Override
	public void hide() {
		if (textPanel != null) {
			getTreeViewer().getTree().setVisible(false);
			getTextViewer().setVisible(false);
			((GridData)textPanel.getLayoutData()).exclude = true;
			textPanel.setVisible(false);
			textPanel.getLayout();
		}
	}

	@Override
	public void show() {
		if (null != textPanel) {
			getTreeViewer().getTree().setVisible(true);
			getTextViewer().setVisible(true);
			((GridData)textPanel.getLayoutData()).exclude = false;
			textPanel.setVisible(true);
			textPanel.getLayout();
		}
	}

	/** The symbolic translation font name */
	private static final String TRANSLATION_FONT_ID = "com.odcgroup.workbench.fontdefinition.translations";

	/** the project */
	private IProject project;

	/** The Text viewer */
	private Text textViewer;
	
	/** The translations */
	private ITranslationModel model;

	/** The control which contains the table and the button panel */
	private Composite textPanel;

	/** translation command provider */
	private ITranslationCommandProvider commandProvider;
	
	/** The edit command */
	private ITranslationCommand editCommand;

	/** Content provider */
	private TranslationLanguageContentProvider treeContentProvider;

	/** Label provider */
	private TranslationLanguageLabelProvider treeLabelProvider;

	private boolean richTextStatus = false;

	/** contains the different types of Languages */
	private TreeViewer treeViewer;

	/**
	 * @return FontRegistry
	 */
	private FontRegistry getFontRegistry() {
		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		ITheme theme = themeManager.getTheme(IThemeManager.DEFAULT_THEME);
		return theme.getFontRegistry();
	}

	/**
	 * @return the textViewer
	 */
	protected final Text getTextViewer() {
		return this.textViewer;
	}
	
	/**
	 * @return the treeViewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	
	/**
	 * @return The underlying translation model
	 */
	protected final ITranslationModel getModel() {
		return this.model;
	}
	
	/**
	 * @return Font
	 */
	protected Font getPreferredFont() {
		return getFontRegistry().get(TRANSLATION_FONT_ID);
	}
	
	/**
	 * @param parent
	 */
	private void createControls(Composite parent) {
		// create container for the text
		textPanel = new Composite(parent, SWT.NONE);
		textPanel.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		textPanel.setLayout(layout);
		textPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createDocTools(textPanel);
		getFontRegistry().addListener(this);

	}

	/**
	 * @param parent
	 * @return
	 */
	private Composite createDocTools(Composite parent) {
		Composite vbox = new Composite(parent, SWT.NONE);
		vbox.setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 2;
		layout.verticalSpacing = 2;
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		
		vbox.setLayout(layout);
		vbox.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createLanguageSelectorList(vbox);
		createTextViewer(vbox);
		return vbox;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite parent) {
		return new TreeViewer(parent);
	}
	
	/**
	 * @param vbox
	 */
	protected void createLanguageSelectorList(Composite parent) {
		
		treeViewer = createTreeViewer(parent);
		GridData gridData = new GridData(GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING);
		treeViewer.getTree().setLayoutData(gridData);
		
		treeViewer.setContentProvider(new TranslationLanguageContentProvider());
		treeViewer.setLabelProvider(getTranslationLanguageLabelProvider());

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChange(event);
			}
		});
	}

	/**
	 * @param vbox
	 */
	private void createTextViewer(Composite vbox) {
		// Create the text viewer to display the translation
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.LINE_SOLID | SWT.MULTI | SWT.WRAP ;
		
		Text textCtrl = new Text(vbox,style);
		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
		textCtrl.setLayoutData(gridData);
		
		
		textCtrl.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getSource() instanceof Text) {
					Text st = (Text)e.getSource();
					if (model != null && null != st.getText()) {
						try {
							String text = st.getText();
							TranslationText.this.editCommand.execute(text);
						} catch (CoreException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		textCtrl.setLayoutData(new GridData(GridData.FILL_BOTH ));
		textCtrl.setText(EMPTY_STR);
		textViewer = textCtrl;
	}

	public boolean isRichText() {
		return richTextStatus;
	}

	protected final ITranslationCommandProvider getTranslationCommandProvider(ITranslation translation) {
		if (null == commandProvider) {
			commandProvider = new TranslationTableManager(project).getTranslationCommandProvider(translation);
		}
		return commandProvider;
	}
	
	protected final TranslationLanguageContentProvider getTranslationLanguageContentProvider() {
		if (null == treeContentProvider) {
			treeContentProvider = createTranslationLanguageContentProvider();
		}
		return treeContentProvider;
	}

	protected final TranslationLanguageLabelProvider getTranslationLanguageLabelProvider() {
		if (null == treeLabelProvider) {
			treeLabelProvider = createTranslationLanguageLabelProvider();
		}
		return treeLabelProvider;
	}

	protected abstract TranslationLanguageContentProvider createTranslationLanguageContentProvider();

	protected abstract TranslationLanguageLabelProvider createTranslationLanguageLabelProvider();

	/**
	 * @param event
	 */
	protected void handleSelectionChange(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		String text = EMPTY_STR;
		if (selection != null) {
			Locale locale = (Locale) selection.getFirstElement();
			getModel().selectLocale(locale);
			text = getModel().getText(locale);
			getTextViewer().setText(text!=null ? text : EMPTY_STR);
		}
		getTextViewer().update();
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(TRANSLATION_FONT_ID)) {
			Text tv = getTextViewer();
			if (tv != null && !tv.isDisposed()) {
				tv.setFont(getPreferredFont());
			}
		}
	}
	

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(java.beans.PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		if (ITranslationModel.TRANSLATION_KIND_PROPERTY.equals(propertyName)) {
			getTextViewer().update();
		}
	}

	@Override
	public void setTranslationModel(ITranslationModel newModel) {
		
		if (null == newModel) {
			throw new IllegalArgumentException("Argument [model] cannot be bull");
		}
		
		if (this.model != null) {
			this.model.removePropertyChangeListener(this);
		}
		this.model = newModel;
		
		ITranslation translation = this.model.getTranslation();
		
		if (this.editCommand != null) {
			this.editCommand.release();
		}
		this.editCommand = getTranslationCommandProvider(translation).getEditCommand(this.model);
		
		TranslationLanguageLabelProvider tlp = getTranslationLanguageLabelProvider();
		if (tlp != null) {
			tlp.setTranslationModel(getModel());
		}
		
		this.model.addPropertyChangeListener(this);
		getTreeViewer().setInput(this.model);
		if(getTreeViewer().getTree().getItemCount() > 0) {
			getTreeViewer().getTree().setSelection(getTreeViewer().getTree().getItem(0));
		}
		
		if(getTreeViewer().getSelection()!=null) {
			try {
				Locale locale = (Locale)((IStructuredSelection)getTreeViewer().getSelection()).getFirstElement();
				String text = this.model.getTranslation().getText(ITranslationKind.DOCUMENTATION, locale);
				getTextViewer().setText(text!=null ? text : EMPTY_STR);
			} catch (TranslationException e) {
				e.printStackTrace();
			}
		
		}
		getTextViewer().update();
	}

	@Override
	public void dispose() {
		
		getFontRegistry().removeListener(this);
		
		if (this.editCommand != null) {
			this.editCommand.release();
		}
		
		if (this.model != null) {
			this.model.removePropertyChangeListener(this);
		}
	}

}
