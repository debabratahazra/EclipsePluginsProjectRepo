package com.odcgroup.t24.version.editor.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.odcgroup.mdf.editor.ui.dialogs.MdfAttributeWithRef;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;

/**
 *
 * @author phanikumark
 *
 */
public class VersionFieldHelper {
	
	private EditingDomain editingDomain;

	private Version version;
	
	public VersionFieldHelper(Version version, EditingDomain editingDomain) {
		this.version = version;
		this.editingDomain = editingDomain;
	}
	
	private boolean isGroup(MdfAssociation association) {
		if (association.getContainment() == MdfContainment.BY_VALUE) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public int getGroupType(MdfProperty property, MdfClass application) {
		List<MdfProperty> properties = application.getProperties();
		Map<MdfClass, List<MdfProperty>> MV_GROUP = new HashMap<MdfClass, List<MdfProperty>>();
		Map<MdfClass, List<MdfProperty>> SV_GROUP = new HashMap<MdfClass, List<MdfProperty>>();
		for (MdfProperty mdfproperty : properties) {
			if (mdfproperty instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation) mdfproperty;
				if (isGroup(association)) {
					List<MdfProperty> mproperties = ((MdfClass)association.getType()).getProperties();
					MV_GROUP.put((MdfClass)association.getType(), mproperties);
					for (MdfProperty prop : mproperties) {
						if (prop instanceof MdfAssociation) {
							MdfAssociation sassc = (MdfAssociation) prop;
							List<MdfProperty> sproperties = ((MdfClass)sassc.getType()).getProperties();
							SV_GROUP.put((MdfClass)sassc.getType(), sproperties);							
						}
					}
				}
			} 
		}
		MdfClass parentClass = property.getParentClass();
		if (parentClass == null) {
			return 0;
		}
		MdfName qname = property.getParentClass().getQualifiedName();		
		if (qname.equals(application.getQualifiedName())) {
			return 0;
		}
		Set<MdfClass> groups = MV_GROUP.keySet();
		for (MdfClass mdfClass : groups) {
			if (qname.equals(mdfClass.getQualifiedName())) {
				return 1;
			}
		}
		Set<MdfClass> sgroups = SV_GROUP.keySet();
		for (MdfClass mdfClass : sgroups) {
			if (qname.equals(mdfClass.getQualifiedName())) {
				return 2;
			}
		}		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public static MdfProperty getGroupWithSingleProperty(MdfAssociation association) {
		if (association.getContainment() == MdfContainment.BY_VALUE) {
			List<MdfProperty> mproperties = ((MdfClass)association.getType()).getProperties();
			if (mproperties.size() == 1) {
				return mproperties.get(0);
			}
		}
		return null;
	}
	
	public void manageFields(List<MdfProperty> in, List<MdfProperty> out) {
		final Version version = getVersion();
		MdfClass application = version.getForApplication();
		Field field = null;
		final List<Field> fields = new ArrayList<Field>();
		for(MdfProperty property :in){
			field = getField(property);
			int group = getGroupType(property, application);
			if (field == null) {
				field = VersionDSLFactory.eINSTANCE.createField();
				//DS-8137 : conversion from '.' to '_' is required for xml generation to work.
				//DS-8473 : conversion from '-1' to '_1' is required for xml generation to work.
				field.setName(getFieldName(property).replace('.', '_').replace("-1", "_1"));
				if(property instanceof MdfAttributeWithRef){
					((MdfAttributeWithRef) property).setRefObj(field);
				}
				MdfProperty prop = null;
				if (property instanceof MdfAssociation) {
					MdfAssociation association = (MdfAssociation) property;
					MdfProperty sing = getGroupWithSingleProperty(association);
					if (sing != null) {
						prop = sing;
						group = 1;
					} else {
						prop = property;
					}
				} else {
					prop = property;					
				}
				if (prop.getAnnotations().size() > 0) {
					Integer maxLength = T24Aspect.getMaxLength(prop);
					field.setMaxLength(maxLength);
					field.setEnrichmentLength(maxLength);
					field.setColumn(0);
					field.setRow(0);
					if (group == 1) {
						field.setMV(new Integer(1));
					} else if (group == 2) {
						field.setMV(new Integer(1));
						field.setSV(new Integer(1));
					}
				}
			} 
			fields.add(field);
		}
		
		TransactionalEditingDomain tdomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain();		
		RecordingCommand cmd = new RecordingCommand(tdomain) {			
			@Override
			protected void doExecute() {
				version.getFields().clear();
				if (!fields.isEmpty()) {
					version.getFields().addAll(fields);
				}
			}
		};
		
		getEditingDomain().getCommandStack().execute(cmd);
	}
	
	private String getFieldName(MdfProperty property) {
		String name = T24Aspect.getT24Name(property);
		if (name == null) {
			name = property.getName().replace("_", ".");
		} 
		return name;
	}
	
	public Field getField(MdfModelElement property) {
		if (property != null) {
			String name = T24Aspect.getT24Name(property);
			if (name == null) {
				name = property.getName();
			}
			if(name.equals("*")){
				return (Field) ((MdfAttributeWithRef)property).getRefObj();
			}
			else{
			List<Field> fields = getVersion().getFields();
			
			for (Field field : fields) {
				String fname = field.getName().replace('_', '.');
				if (name.equals(fname)) {
					return field;
				}
			}
			}
		}
		return null;
	}
	
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	public Version getVersion() {
		return version;
	}

}
