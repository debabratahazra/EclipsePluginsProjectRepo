package com.odcgroup.t24.version.editor.databinding.converters;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.jface.viewers.ComboViewer;

import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

/**
 * @author arajeshwari
 *
 */
public class StringToVersionConverter implements IConverter {

	private ComboViewer comboViewer;

	public StringToVersionConverter(ComboViewer comboViewer) {
		if(comboViewer != null){
			this.comboViewer = comboViewer;
		}
	}

	@Override
	public Object getFromType() {
		return String.class;
	}

	@Override
	public Object getToType() {
		return Version.class;
	}

	@Override
	public Object convert(Object fromObject) {
		if(getFromType()== String.class){
	    return createVersion((String)fromObject);
	}
	return null;}

	private Object createVersion(String name) {
		if(name.isEmpty()){
			name = "dummyName";
			if(comboViewer != null)
			comboViewer.getCombo().setEnabled(false);
		}else if(comboViewer != null){
			comboViewer.getCombo().setEnabled(true);
		}
		Version version = VersionDSLFactoryImpl.eINSTANCE.createVersion();
		version.setGroup(name);
		return version;
	}

}
