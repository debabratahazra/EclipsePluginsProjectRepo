package com.odcgroup.t24.mdf.generation.xls;

import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

public class T24Enumeration {

	private MdfEnumeration enumeration;
	
	public String getQualifiedName() {
		return enumeration.getQualifiedName().getQualifiedName();
	}
	
	public String getName() {
		return enumeration.getName();
	}
	
	@SuppressWarnings("unchecked")
	public void writeTo(ExcelContext ctx) throws RowsExceededException, WriteException {
		ctx.createSheet(getQualifiedName());
		ctx.writeSheetHyperlink(enumeration.getParentDomain().getQualifiedName().getQualifiedName(), "Back to domain");
		ctx.writeln();
		ctx.writeln();
		ctx.write("Enumeration", ctx.getTitleCellFormat());
		ctx.writeln(getName(), ctx.getDefaultCellFormat());
		ctx.writeln();
		ctx.createColumns(new String[] {"Name","Value","Description"});
		ctx.writeln();
		for (MdfEnumValue value : (List<MdfEnumValue>)enumeration.getValues()) {
			ctx.write(value.getName());
			ctx.write(value.getValue());
			ctx.writeln(value.getDocumentation(), ctx.getItalicAndWrapCellFormat());
		}
		ctx.updateColumnsSize();
	}
	
	public T24Enumeration(MdfEnumeration enumeration) {
		this.enumeration = enumeration;
	}
	
}
