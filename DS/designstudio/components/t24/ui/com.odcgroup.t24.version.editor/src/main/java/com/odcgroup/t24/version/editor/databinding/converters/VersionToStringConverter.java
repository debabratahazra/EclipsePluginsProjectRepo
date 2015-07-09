package com.odcgroup.t24.version.editor.databinding.converters;

import java.util.List;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.t24.version.naming.VersionNameProvider;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * @author arajeshwari
 *
 */
public class VersionToStringConverter implements IConverter {

	private ComboViewer comboViewer;

	public VersionToStringConverter(ComboViewer comboViewer) {
		if(comboViewer != null){
			this.comboViewer = comboViewer;
		}
	}

	@Override
	public Object getFromType() {
		return Version.class;
	}

	@Override
	public Object getToType() {
		return String.class;
	}

	@Override
	public Object convert(Object fromObject) {
		if(getFromType() == Version.class){
			String fullName = null;
			if(fromObject == null && comboViewer != null){
		    	comboViewer.getCombo().setEnabled(false);
		    }else if(comboViewer != null){
		    	comboViewer.getCombo().setEnabled(true);
		    }
		    if(fromObject instanceof Version){
		    VersionNameProvider vnp = new VersionNameProvider();
		    QualifiedName fullyQualifiedName = vnp.getFullyQualifiedName(((Version)fromObject));
			if(fullyQualifiedName != null){
			List<String> segments = fullyQualifiedName.getSegments();
			if(segments.size() == 3)
				fullName = segments.get(0).concat(":").concat(segments.get(1)).concat(",").concat(segments.get(2));
			else{
				fullName = segments.get(0).concat(":").concat(segments.get(1));
				}
		    return fullName.toString();
		    }
		    }
		}
		return null;
	}

}
