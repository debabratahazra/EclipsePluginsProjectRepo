package com.odcgroup.t24.mdf.generation.xls;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

public class T24Property {

	private final MdfProperty property;
	private final String group;
	private final String subgroup;
	private String dataType;

	private String getPropertyName() {
		return property.getName();
	}
	
	private String getType() { 
		MdfEntityImpl type = (MdfEntityImpl) property.getType();
		// incase of unresolved proxies
		if (type == null || (type.eIsProxy() && type.getQualifiedName() == null)) {
			return "";
		} else {
			return type.getQualifiedName().getQualifiedName();			
		}
	}
	
	private String getGroupName() {
		return this.group;
	}
	
	private String getSubGroupName() {
		return this.subgroup;
	}

	private String getDescription() {
		return property.getDocumentation();		
	}
	
	private String getBusinessType() {
		return T24Aspect.getBusinessType(this.property);
	}

	private String getDataType() {
		return dataType;
	}

	
	private void writePropertyType(ExcelContext ctx) throws RowsExceededException, WriteException {
		MdfEntity type = property.getType();
		if (type != null) {
			MdfDomain parentDomain = type.getParentDomain();
			if (parentDomain != null) {
				String domainName = parentDomain.getName();
				if (type instanceof MdfEnumeration) {
					if (ctx.getDomain().equals(domainName)) {
						// put an enumeration link in the same workbook.
						String label = property.getType().getName();
						int pos = label.indexOf("__");
						if (pos != -1) {
							label = label.substring(pos+2);
						}
						ctx.writeSheetHyperlink(type.getQualifiedName().getQualifiedName(), "Enumeration: "+label);
					} else {
						ctx.write(getType());
					}
				} else if (type instanceof MdfPrimitive) {
					// simply write the primitive type's name
					this.dataType = getType().replaceFirst("mml:", ""); 
					ctx.write("");
				} else if (ctx.getDomain().equals(domainName)) {
					// put an hyperlink in the current workbook.
					String label = getType();
					if (property.isPrimaryKey()) {
						ctx.write("Primary Key");
					} else {
						ctx.writeSheetHyperlink(type.getQualifiedName().getQualifiedName(), label);
					}
				} else {
					// put an hyperlink to another workbook.
					ctx.writeFileHyperLink(type.getQualifiedName().getQualifiedName(), "Foreign Key: "+getType());
				}
			} else {
				ctx.write(getType());
			}
		} else {
			ctx.write("");
		}
	}
	
	private String getMetaData() {
		StringBuffer b = new StringBuffer();
		b.append("Justification: ");
		b.append(T24Aspect.getAlignment(this.property));
		b.append("\n");
		b.append("Input Behaviour: ");
		b.append(T24Aspect.getInputBehaviour(this.property));
		b.append("\n");
		b.append("Length: ");
		b.append(T24Aspect.getMaxLengthAsString(this.property));
		b.append("\n");
		b.append("Field Number: ");
		String fieldNumber = T24Aspect.getSysNumberAsString(this.property);
		b.append(StringUtils.substringBefore(fieldNumber, "."));
		b.append("\n");
		b.append("T24 Name: ");
		b.append(T24Aspect.getT24Name(this.property));
		b.append("\n");
		b.append("core: ");
		b.append(T24Aspect.isCore(this.property));
		b.append("\n");
		b.append("mandatory: ");
		b.append(T24Aspect.isMandatory(this.property));
		b.append("\n");
		b.append("multi-language: ");
		b.append(T24Aspect.isMultiLanguage(this.property));
		return b.toString();
	}
	
	public void writeTo(ExcelContext ctx) throws RowsExceededException, WriteException {		
		ctx.write(getPropertyName());
		writePropertyType(ctx);
		ctx.write(getDataType());
		ctx.write(getBusinessType());  
		ctx.write(getGroupName());
		ctx.write(getSubGroupName());
		ctx.write(getMetaData(), ctx.getItalicAndWrapCellFormat());
		ctx.write(getDescription(), ctx.getItalicAndWrapCellFormat());
		ctx.writeln();
	}

	public T24Property(MdfProperty property, String group, String subgroup) {
		this.property = property;
		this.group = group;
		this.subgroup = subgroup;
	}

}
