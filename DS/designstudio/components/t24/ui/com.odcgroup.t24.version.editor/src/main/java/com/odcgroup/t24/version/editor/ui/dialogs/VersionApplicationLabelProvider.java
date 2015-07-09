package com.odcgroup.t24.version.editor.ui.dialogs;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeLabelProvider;
import com.odcgroup.t24.version.editor.utils.VersionFieldHelper;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 *
 * @author phanikumark
 *
 */
public class VersionApplicationLabelProvider extends ApplicationTreeLabelProvider {
	
	private Version version;
	
	public VersionApplicationLabelProvider(MdfClass application, Version version) {
		super(application);
		this.version = version;
	}
	
	@Override
    public String getText(Object item) {
		if (item instanceof MdfProperty) {
			MdfProperty property = (MdfProperty) item;
			List<Field> fields = version.getFields();
			String t24Name = T24Aspect.getT24Name(property);
			if (property instanceof MdfAssociation) {
				MdfProperty  prop = VersionFieldHelper.getGroupWithSingleProperty((MdfAssociation) property);
				if (prop != null) {
					t24Name = T24Aspect.getT24Name(prop);
				}
			}
			if (StringUtils.isEmpty(t24Name)) {
				t24Name = property.getName();
			}
			for (Field field : fields) {
				String fname = field.getName().replace("_", ".");
				if (fname.equals(t24Name)) {
					Integer in2 = field.getSV();
					if (in2 != null && in2.intValue() == 1) {
						return t24Name+"-1.1";
					}
					Integer in = field.getMV();
					if (in != null && in.intValue() == 1) {
						return t24Name+"-1";
					}
					return t24Name;
				}
			}
		}
		return super.getText(item);
	}

}
