package com.odcgroup.t24.mdf.generation.xls;

import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;

public class T24Class {
	
	private static String GROUP_SEPARTOR = "__";	
	
	private static String ATTRIBUTE_TYPE_COMMENT = 
			"Type can be either:\n- Regular attribute,\n- Primarey kes,\n- Forign key,\n- Enumeration";

	private static String BUSINESSTYPE_COMMENT =
			"A: alphanumeric characters, A to Z, a to z, 0 to 9, space or ! \" # $ % & ' ( ) * + , - . / [ \\ ]\n"+
			"AA: first character must be A to Z or a to z.\n"+ 
			"AAA: any character in the range A to Z or a to z.\n"+
			"AMTLCCY: amount in local currency. Amount will be formatted as per local currency.\n"+
			"AMTCCY: amount followed by currency code in the same line. Negative amounts are allowed\n"+ 
			"D: a valid date format with the year between 1950 and 9999.\n"+ 
			"DD: as for D, but with the year between 1000 and 2049.\n"+
			"R: Uu to 16 characters, in the range 0 to 9 but with a maximum of 6 integers or 9 decimals.\n"+
			"S: any character in the range A to Z, 0 to 9 or . ( ) +, - ( - cannot be the first character.)\n"+ 
			"SS: as for S, except the first character must be in the range A to Z.\n"+ 
			"SSS: any Character in the range A to Z only.\n"+
			"blank: any Character in the range 0 to 9 or '.' only.\n"+
			"TIME:  valid time in 24-hour format with hours and minutes separated by a colon (:).\n"+
			"TIMES: as above but also with seconds displayed.\n"+
			"TIMEH: as for D in 12 hours format and also a suffix of 'am' or 'pm'.\n"+
			"TIMEHS: As above but also with seconds displayed.";

	private static String GROUP_NAME_COMMENT =
			"Multi-value field(s) that are grouped\nunder the same name.";
	
	private static String SUBGROUP_NAME_COMMENT =
			"Sub-value field(s) that are grouped\nunder the same name.";

	private MdfClass clazz;
	
	public String getQualifiedName() {
		return clazz.getQualifiedName().getQualifiedName();
	}
	
	public String getName() {
		return clazz.getName();
	}

	public String getDescription() {
		return clazz.getDocumentation();
	}
	
	public String getType() {
		return "Application";
	}
	
	private static String getGroupName(MdfEntity entity) {
		String group = "";
		String[] parts = entity.getName().split(GROUP_SEPARTOR);
		if (parts.length > 1) {
			group = parts[1];
		}
		return group;
	}

	private static String getSubGroupName(MdfEntity entity) {
		String subgroup = "";
		String[] parts = entity.getName().split(GROUP_SEPARTOR);
		if (parts.length > 2) {
			subgroup = parts[2];
		}
		return subgroup;
	}

	@SuppressWarnings("unchecked")
	private static void findProperties(MdfClass cls,  String group, String subGroup, List<T24Property> list) {
		for (MdfProperty property : (List<MdfProperty>)cls.getProperties()) {
			if (property instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation)property;
				if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
					MdfClass type = (MdfClass)association.getType();
					findProperties(type, getGroupName(type), getSubGroupName(type), list);
				} else {
					list.add(new T24Property(property, group, subGroup));
				}
			} else {
				list.add(new T24Property(property, group, subGroup));
			}
		}
	}
	
	private List<T24Property> getProperties() {
		List<T24Property> list = new ArrayList<T24Property>();		
		findProperties(this.clazz, "", "", list);		
		return list;
	}
	
	public void writeTo(ExcelContext ctx) throws RowsExceededException, WriteException {
		ctx.setMainClass(getQualifiedName(), getDescription());
		ctx.createSheet(getQualifiedName());
		ctx.writeSheetHyperlink(clazz.getParentDomain().getQualifiedName().getQualifiedName(), "Back to domain");
		ctx.writeln();
		ctx.writeln();
		ctx.write(getType(), ctx.getTitleCellFormat());
		ctx.writeln(getQualifiedName(), ctx.getDefaultCellFormat());
		ctx.writeln();
		ctx.createColumns(new String[] {"Property Name", "Attribute Type", "Data Type", "Business Type", "Group Name", "Sub group Name", "Meta Data ", "Description"});
		ctx.setComment(1, ATTRIBUTE_TYPE_COMMENT, 2.0, 1.5);
		ctx.setComment(3, BUSINESSTYPE_COMMENT, 4.0, 3.0);
		ctx.setComment(4, GROUP_NAME_COMMENT, 2.0, 2.0);
		ctx.setComment(5, SUBGROUP_NAME_COMMENT, 2.0, 2.0);
		ctx.writeln();
		for (T24Property property : getProperties()) {
			property.writeTo(ctx);
		}
		ctx.updateColumnsSize();
	}
	
	public T24Class(MdfClass c) {
		this.clazz = c;
	}
	
}
