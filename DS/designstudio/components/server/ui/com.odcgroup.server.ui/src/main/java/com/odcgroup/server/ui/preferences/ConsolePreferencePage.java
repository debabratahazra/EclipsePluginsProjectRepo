package com.odcgroup.server.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.server.preferences.RGBColor;
import com.odcgroup.server.ui.Messages;

public abstract class ConsolePreferencePage extends AbstractServerPreferencesPage {

	protected ColorFieldEditor debugEditor;
	protected ColorFieldEditor errorEditor;
	protected ColorFieldEditor infoEditor;
	protected ColorFieldEditor warningEditor;

	protected int autoshow;
	protected boolean wrapEnabled;
	protected boolean limitEnabled;
	protected int wrapWidth;
	protected int limitValue;
	
	protected Button showInfoButton;
	protected Button showDebugButton;
	protected Button showErrorButton;
	protected Button showWarningButton;
	protected Button wrapEnabledButton;
	protected Button limitEnabledButton;
	protected Text wrapWidthText;
	protected Text limitValueText;

	public ConsolePreferencePage() {
		setPreferenceStore(
				new ScopedPreferenceStore(InstanceScope.INSTANCE, getScopeQualifier())); 
	}
	
	protected abstract String getScopeQualifier();
	
	protected void saveValues(IPreferenceStore store) {
		this.debugEditor.store();
		this.errorEditor.store();
		this.infoEditor.store();
		this.warningEditor.store();

		ServerUIPreferences.setAutoShow(store, this.autoshow);
		ServerUIPreferences.setWrapEnabled(store, this.wrapEnabled);
		ServerUIPreferences.setWrapWidth(store, this.wrapWidth);
		ServerUIPreferences.setLimitEnabled(store, this.limitEnabled);
		ServerUIPreferences.setLimitValue(store, this.limitValue);
	}
	
	protected RGB toRGB(RGBColor color) {
		return new RGB(color.red(), color.green(), color.blue());
	}

	protected void loadDefaultValues(IPreferenceStore store) {
		
		ServerUIPreferences.resetToDefaultConsoleValues(store);

		this.autoshow = ServerUIPreferences.getAutoShow(store, true);
		this.wrapEnabled = ServerUIPreferences.isWrapEnabled(store, true);
		this.wrapWidth = ServerUIPreferences.getWrapWidth(store, true);
		this.limitEnabled = ServerUIPreferences.isLimitEnabled(store, true);
		this.limitValue = ServerUIPreferences.getLimitValue(store, true);

		this.debugEditor.loadDefault();
		this.errorEditor.loadDefault();
		this.infoEditor.loadDefault();
		this.warningEditor.loadDefault();

	}

	protected void loadValues(IPreferenceStore store) {
		
		this.autoshow = ServerUIPreferences.getAutoShow(store);
		this.wrapEnabled = ServerUIPreferences.isWrapEnabled(store);
		this.wrapWidth = ServerUIPreferences.getWrapWidth(store);
		this.limitEnabled = ServerUIPreferences.isLimitEnabled(store);
		this.limitValue = ServerUIPreferences.getLimitValue(store);

		this.debugEditor.load();
		this.errorEditor.load();
		this.infoEditor.load();
		this.warningEditor.load();
	}

	protected void initializeControls() {
		this.showInfoButton.setSelection((this.autoshow & ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_INFO) != 0);
		this.showDebugButton.setSelection((this.autoshow & ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_DEBUG) != 0);
		this.showErrorButton.setSelection((this.autoshow & ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR) != 0);
		this.showWarningButton.setSelection((this.autoshow & ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_WARNING) != 0);

		this.wrapEnabledButton.setSelection(this.wrapEnabled);
		this.wrapWidthText.setEnabled(this.wrapEnabled);
		this.wrapWidthText.setText(String.valueOf(this.wrapWidth));

		this.limitEnabledButton.setSelection(this.limitEnabled);
		this.limitValueText.setEnabled(this.limitEnabled);
		this.limitValueText.setText(String.valueOf(this.limitValue));
	}

	protected Control createContentsImpl(Composite parent) {
		Composite composite = new Composite(parent, SWT.FILL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.grabExcessVerticalSpace = false;
		composite.setLayoutData(data);

		Group showType = new Group(composite, SWT.FILL);
		showType.setText(Messages.ConsolePreferencePage_textShowOnGroup);
		layout = new GridLayout();
		layout.numColumns = 4;
		layout.horizontalSpacing = 10;
		showType.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		data.grabExcessVerticalSpace = false;
		showType.setLayoutData(data);
		
		this.showInfoButton = new Button(showType, SWT.CHECK);
		data = new GridData();
		this.showInfoButton.setLayoutData(data);
		this.showInfoButton
				.setText(Messages.ConsolePreferencePage_textShowInfo);
		this.showInfoButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (ConsolePreferencePage.this.showInfoButton.getSelection()) {
					ConsolePreferencePage.this.autoshow |= ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_INFO;
				} else {
					ConsolePreferencePage.this.autoshow &= ~ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_INFO;
				}
			}
		});

		this.showDebugButton = new Button(showType, SWT.CHECK);
		data = new GridData();
		this.showDebugButton.setLayoutData(data);
		this.showDebugButton
				.setText(Messages.ConsolePreferencePage_textShowDebug);
		this.showDebugButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (ConsolePreferencePage.this.showDebugButton.getSelection()) {
					ConsolePreferencePage.this.autoshow |= ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_DEBUG;
				} else {
					ConsolePreferencePage.this.autoshow &= ~ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_DEBUG;
				}
			}
		});

		this.showErrorButton = new Button(showType, SWT.CHECK);
		data = new GridData();
		this.showErrorButton.setLayoutData(data);
		this.showErrorButton
				.setText(Messages.ConsolePreferencePage_textShowError);
		this.showErrorButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (ConsolePreferencePage.this.showErrorButton.getSelection()) {
					ConsolePreferencePage.this.autoshow |= ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR;
				} else {
					ConsolePreferencePage.this.autoshow &= ~ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_ERROR;
				}
			}
		});

		this.showWarningButton = new Button(showType, SWT.CHECK);
		data = new GridData();
		this.showWarningButton.setLayoutData(data);
		this.showWarningButton
				.setText(Messages.ConsolePreferencePage_textShowWarning);
		this.showWarningButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (ConsolePreferencePage.this.showWarningButton.getSelection()) {
					ConsolePreferencePage.this.autoshow |= ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_WARNING;
				} else {
					ConsolePreferencePage.this.autoshow &= ~ServerUIPreferences.CONSOLE_AUTOSHOW_TYPE_WARNING;
				}
			}
		});

		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		separator.setLayoutData(data);
		separator.setVisible(false);

		this.wrapEnabledButton = new Button(composite, SWT.CHECK);
		data = new GridData();
		data.horizontalSpan = 2;
		this.wrapEnabledButton.setLayoutData(data);
		this.wrapEnabledButton
				.setText(Messages.ConsolePreferencePage_textWrapEnabled);
		this.wrapEnabledButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ConsolePreferencePage.this.wrapEnabled = ConsolePreferencePage.this.wrapEnabledButton
						.getSelection();
				ConsolePreferencePage.this.wrapWidthText
						.setEnabled(ConsolePreferencePage.this.wrapEnabled);
			}
		});

		Label label = new Label(composite, SWT.NULL);
		data = new GridData();
		label.setLayoutData(data);
		String labelText = Messages.ConsolePreferencePage_textWrapWidth;
		label.setText(labelText);

		this.wrapWidthText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		this.wrapWidthText.setLayoutData(data);
		// CompositeVerifier verifier = new CompositeVerifier();
		// verifier.add(new NonEmptyFieldVerifier(labelText));
		// verifier.add(new IntegerFieldVerifier(labelText, true));
		// this.attachTo(this.wrapWidthText, verifier);
		this.wrapWidthText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					ConsolePreferencePage.this.wrapWidth = Integer
							.parseInt(ConsolePreferencePage.this.wrapWidthText
									.getText());
				} catch (Exception ex) {
				}
			}
		});

		this.limitEnabledButton = new Button(composite, SWT.CHECK);
		data = new GridData();
		data.horizontalSpan = 2;
		this.limitEnabledButton.setLayoutData(data);
		this.limitEnabledButton
				.setText(Messages.ConsolePreferencePage_textLimitEnabled);
		this.limitEnabledButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ConsolePreferencePage.this.limitEnabled = ConsolePreferencePage.this.limitEnabledButton
						.getSelection();
				ConsolePreferencePage.this.limitValueText
						.setEnabled(ConsolePreferencePage.this.limitEnabled);
			}
		});

		label = new Label(composite, SWT.NULL);
		data = new GridData();
		label.setLayoutData(data);
		labelText = Messages.ConsolePreferencePage_textLimitValue;
		label.setText(labelText);

		this.limitValueText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		this.limitValueText.setLayoutData(data);
		// verifier = new CompositeVerifier();
		// verifier.add(new NonEmptyFieldVerifier(labelText));
		// verifier.add(new IntegerFieldVerifier(labelText, true));
		// this.attachTo(this.limitValueText, verifier);
		this.limitValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					ConsolePreferencePage.this.limitValue = Integer
							.parseInt(ConsolePreferencePage.this.limitValueText
									.getText());
				} catch (Exception ex) {
				}
			}
		});

		separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		separator.setLayoutData(data);
		separator.setVisible(false);

		label = new Label(composite, SWT.NULL);
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);
		label.setText(Messages.ConsolePreferencePage_textColorsGroup);

		this.debugEditor = new ColorFieldEditor(
				ServerUIPreferences
						.fullConsoleName(ServerUIPreferences.CONSOLE_DEBUG_COLOR_NAME),
				Messages.ConsolePreferencePage_textDebugMessage, composite);
		this.debugEditor.setPage(this);
		this.debugEditor.setPreferenceStore(this.getPreferenceStore());

		this.errorEditor = new ColorFieldEditor(
				ServerUIPreferences
						.fullConsoleName(ServerUIPreferences.CONSOLE_ERROR_COLOR_NAME),
				Messages.ConsolePreferencePage_textErrorMessage, composite);
		this.errorEditor.setPage(this);
		this.errorEditor.setPreferenceStore(this.getPreferenceStore());

		this.infoEditor = new ColorFieldEditor(
				ServerUIPreferences
						.fullConsoleName(ServerUIPreferences.CONSOLE_INFO_COLOR_NAME),
				Messages.ConsolePreferencePage_textInfoMessage, composite);
		this.infoEditor.setPage(this);
		this.infoEditor.setPreferenceStore(this.getPreferenceStore());

		this.warningEditor = new ColorFieldEditor(
				ServerUIPreferences
						.fullConsoleName(ServerUIPreferences.CONSOLE_WARNING_COLOR_NAME),
				Messages.ConsolePreferencePage_textWarningMessage, composite);
		this.warningEditor.setPage(this);
		this.warningEditor.setPreferenceStore(this.getPreferenceStore());

		// Setting context help
		//	PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, ""); //$NON-NLS-1$

		return composite;
	}

}
