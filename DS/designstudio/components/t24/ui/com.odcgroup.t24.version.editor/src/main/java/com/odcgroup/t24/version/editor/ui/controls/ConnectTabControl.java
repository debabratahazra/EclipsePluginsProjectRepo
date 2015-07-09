package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;

public class ConnectTabControl extends AbstractVersionTabControl{

	private Button button;
	private EditingDomain edomain;

	public ConnectTabControl(Composite parent, VersionDesignerEditor editor,
			DataBindingContext context) {
		super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {
		final Composite rootComp = new Composite(body, SWT.NONE);
		rootComp.setLayout(new GridLayout(2, true));
		rootComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(rootComp);
		toolkit.paintBordersFor(rootComp);

		Composite scrCtrlComp = new Composite(rootComp, SWT.NONE);
		scrCtrlComp.setLayout(new RowLayout(SWT.HORIZONTAL));
		toolkit.adapt(scrCtrlComp);
		toolkit.paintBordersFor(scrCtrlComp);

		button = new Button(scrCtrlComp, SWT.CHECK);
		
		toolkit.adapt(button, true, true);
		button.setSelection(true);

		Label scrCtrlLbl = new Label(scrCtrlComp, SWT.NONE);
		scrCtrlLbl.setText("Do NOT generate Connect *.ifp (for taken over/adopted artifacts)");
		toolkit.adapt(scrCtrlLbl, true, true);
		scrCtrlLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_EdgeGeneration"));

		
	}

	@Override
	protected void bindData() {
		// TODO Auto-generated method stub
		edomain = getEditingDomain();
		Version version = getEditedVersion();

		// include version control
		UpdateValueStrategy booleanToScript = new UpdateValueStrategy().setConverter(new Converter(Boolean.class, String.class) {
					@Override
					public Object convert(Object value) {
						if (!Boolean.parseBoolean(value.toString())) {
							return (Object) YesNo.YES;
						}
						return (Object) YesNo.NO;
					}
				});

		UpdateValueStrategy scriptToBoolean = new UpdateValueStrategy().setConverter(new Converter(String.class, Boolean.class) {

					public Object convert(Object fromObject) {
						if (YesNo.NO.equals(fromObject)) {
							return Boolean.TRUE;
						}
						return Boolean.FALSE;
					}
				});
		
		//version control
		IObservableValue includeVersionContrlWidget = WidgetProperties.selection().observe(button);
		IObservableValue includeVersionContrlValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__GENERATE_IFP);
		getBindingContext().bindValue(includeVersionContrlWidget, includeVersionContrlValue, booleanToScript, scriptToBoolean);

	}


}
