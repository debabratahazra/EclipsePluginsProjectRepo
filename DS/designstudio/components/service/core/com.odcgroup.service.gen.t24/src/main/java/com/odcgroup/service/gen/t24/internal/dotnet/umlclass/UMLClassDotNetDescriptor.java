/*
 * This class contains the information how to generate
 * business object and business object handler for one
 * user defined UML complex type, called UML class.
 */
package com.odcgroup.service.gen.t24.internal.dotnet.umlclass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.odcgroup.service.gen.t24.internal.categories.dotnet.api.UMLElementDotNetFactory;
import com.odcgroup.service.gen.t24.internal.data.AttributeDescriptor;
import com.odcgroup.service.gen.t24.internal.data.ClassDefDescriptor;
import com.odcgroup.service.gen.t24.internal.dotnet.umlservice.UMLServiceDotNetDescriptor;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.Formatter;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;

public class UMLClassDotNetDescriptor {
	//uml class name defined in UML model
	private String m_umlName = null;
	
	//member list of UML class
	private ArrayList<UMLClassAttrDotNetDescriptor> attrDescriptorList = null;
		
	//.NET framework class reference list in .NET business object class
	private Set<String> dotNetStdClsRefListInBO = null;
	//.NET user class reference list in .NET Business object class .h
	private Set<String> dotNetUserClsRefListInBO = null;
	
	//.NET user class reference list in helper marshal class .cpp
	private Set<String> dotNetUserClsRefListInHelperCpp = null;
	//c++ Temenos user class reference list in  helper marshal class .cpp
	private Set<String> cppTemenosUserClsRefListInHelperCpp = null;			
	//C++ user class reference list in helper marshal class .cpp
	private Set<String> cppUserClsRefListInHelperCpp = null;
	
	private UMLServiceDotNetDescriptor m_parent = null;
	
	public UMLClassDotNetDescriptor(ClassDefDescriptor classDefDesc,
									UMLServiceDotNetDescriptor parent) {
	  m_umlName = classDefDesc.getName();
	  attrDescriptorList = new ArrayList<UMLClassAttrDotNetDescriptor>();
	  m_parent = parent;	  		
		
	  dotNetStdClsRefListInBO = new HashSet<String>();
		dotNetUserClsRefListInBO = new HashSet<String>();
		
		dotNetUserClsRefListInHelperCpp = new HashSet<String>();				
		cppTemenosUserClsRefListInHelperCpp = new HashSet<String>();
		cppUserClsRefListInHelperCpp = new HashSet<String>();
		
		initClassAttr(classDefDesc);		
	}	

	

	//construct UMLClassMemeber object list for members 
	//defined in the UML class
	private void initClassAttr(ClassDefDescriptor classDefDesc) {
		if(classDefDesc != null) {
			List<AttributeDescriptor> attrs = classDefDesc.getAttributes();
			Iterator<AttributeDescriptor> it = attrs.iterator();
			
			//index of first member in JBC VAR will be 1, not 0 
			int index = 1;
			while(it.hasNext()) {
				AttributeDescriptor attr = it.next();
				
				UMLClassAttrDotNetDescriptor member = 
					UMLElementDotNetFactory.createUMLClassMember(attr, index, this);
				
				attrDescriptorList.add(member);
				
				index++;
			}
		}		
	}
	//.NET standard class reference in business object class .h 
	public String[] getDotNetStdClsRefListInBO() {
		return dotNetStdClsRefListInBO.toArray(
				new String[dotNetStdClsRefListInBO.size()]);
	}

	public void addDotNetStdClsRefIntoBO(String dotNetStdClsRef) {
		dotNetStdClsRefListInBO.add(dotNetStdClsRef);
	}

	//.NET user class reference in business object class .h
	public String[] getDotNetUserClsRefListInBO() {
		return dotNetUserClsRefListInBO.toArray(
				new String[dotNetUserClsRefListInBO.size()]);
	}

	public void addDotNetUserClsRefIntoBO(String dotNetUserClsRef) {
		dotNetUserClsRefListInBO.add(dotNetUserClsRef);
	}
	
  //.NET user class reference in marshal helper class .cpp
	public String[] getDotNetUserClsRefListInHelperCpp() {
		return dotNetUserClsRefListInHelperCpp.toArray(
				new String[dotNetUserClsRefListInHelperCpp.size()]);
	}

	public void addDotNetUserClsRefIntoHelperCpp(String dotNetUserClsRef) {
		dotNetUserClsRefListInHelperCpp.add(dotNetUserClsRef);
	}
	
	//c++ temenos user class reference in marshal helper class .cpp
	public String[] getCppTemenosUserClsRefListInHelperCpp() {
		return cppTemenosUserClsRefListInHelperCpp.toArray(
				new String[cppTemenosUserClsRefListInHelperCpp.size()]);
	}

	public void addCppTemenosUserClsRefIntoHelperCpp(String cppUserClsRef) {
		cppTemenosUserClsRefListInHelperCpp.add(cppUserClsRef);
	}	
	
	//c++ user class reference in marshal helper class .cpp
	public String[] getCppUserClsRefListInHelperCpp() {
		return cppUserClsRefListInHelperCpp.toArray(
				new String[cppUserClsRefListInHelperCpp.size()]);
	}

	public void addCppUserClsRefIntoHelperCpp(String cppUserClsRef) {
		cppUserClsRefListInHelperCpp.add(cppUserClsRef);
	}	
	
	//construct c++ business object class name for this UML class
	public String getCppClassName() {
		return StringUtils.upperInitialCharacter(getUMLName());
	}
	//construct .NET business object class name for this UML class
	public String getDotNetClassName() {
		return "Managed" + StringUtils.upperInitialCharacter(getUMLName());
	}
	
	public UMLServiceDotNetDescriptor getParent() {
		return m_parent;
	}
	
	public String getLowerCamelCaseName() {
		return StringUtils.lowerInitialCharacter(getUMLName());
	}
	
	public String getUMLName() {
		return m_umlName;
	}		
	
	//get all the members defined in this UML Class
	//each member is represented with UMLClassMember object
	public UMLClassAttrDotNetDescriptor[] getMembers() {
		return attrDescriptorList.toArray(new UMLClassAttrDotNetDescriptor[attrDescriptorList.size()]);
	}  
  
  //forward declare for .net user class reference 
  public String declareUserClassRefInManagedBO() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	for(String userClsRef : dotNetUserClsRefListInBO ) {
  		sb.append(formatter.currentIndent()
  				+ "ref class " + userClsRef + ";\n");
  	}
  	
  	return sb.toString();
  }  
  
  /************************************************************
   *.NET business object 
   ************************************************************/	
  //construct .NET business object .h file name for this UML class
  public String getDotNetClassHeaderFileName() {
  	return getDotNetClassName() + Constants.CPP_HEADER_FILE_EXT;
  }
	
  //construct .NET business object .cpp file name for this UML class
  public String getDotNetClassCppFileName() {
  	return getDotNetClassName() + Constants.CPP_FILE_EXT;
  }  
  
  //construct .NET business object .cpp file name for this UML class
  public String getDotNetConverterClassCsFileName() {
  	return getDotNetClassName() + "Converter" + Constants.CS_FILE_EXT;
  }  
  
  //declare .NET business object default constructor
  public String declareDotNetDefaultConstrutor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "();\n");
  	
  	return sb.toString();
  }
  
  //define .NET business object default constructor
  public String defineDotNetDefaultConstructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent() 
				+ getDotNetClassName() + "::" + getDotNetClassName() + "()\n");
		
		formatter.indent();
		formatter.indent();
		
		//initialize each member with default value
		int index = 0;
		for(UMLClassAttrDotNetDescriptor member : attrDescriptorList) {
			sb.append(formatter.currentIndent());
			if(index == 0)
				sb.append(":");
			sb.append(member.getDotNetVarName() + "(");
			sb.append(member.getDotNetDefaultValue());
			sb.append(")");
			
			if(index < (attrDescriptorList.size() - 1))
				sb.append(",\n");
			else
				sb.append(" {\n");
			
			index++;
		}
		
		formatter.outdent();
		formatter.outdent();
		
		sb.append(formatter.currentIndent() + "}\n");
  	return sb.toString();
  }
  
  //declare constructor of .NET business object
  public String declareDotNetConstructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent() 
  			+ getDotNetClassName()
  	    + "(\n");
  	    
  	formatter.indent();
  	formatter.indent();
  	
  	//construct parameter list for constructor
  	int index = 0;
  	for(UMLClassAttrDotNetDescriptor member : attrDescriptorList) {
  		sb.append(formatter.currentIndent()
  				+ member.getDotNetType() + " "  		
  				+ member.getDotNetParaVarName());
  		
  		if(index < (attrDescriptorList.size() - 1))
  			sb.append(",\n");
  		else
  			sb.append(");\n");
  		index++;
  	}  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();
  }
  
  //define constructor of .NET business object
  public String defineDotNetConstructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "::" + getDotNetClassName()
  	    + "(\n");
  	
  	formatter.indent();
  	formatter.indent();
  	
  	//construct parameter list for constructor
  	int index = 0;
  	for(UMLClassAttrDotNetDescriptor member : attrDescriptorList) {
  		sb.append(formatter.currentIndent() 
  				+ member.getDotNetType()
  		    + " " + member.getDotNetParaVarName());
  		
  		if(index < (attrDescriptorList.size() - 1))
  			sb.append(",\n");
  		else
  			sb.append(")\n");
  		index++;
  	}
  	
  	//initialize each member with corresponding input parameter
  	index = 0;
  	for(UMLClassAttrDotNetDescriptor member : attrDescriptorList) {
			sb.append(formatter.currentIndent());
			
			if(index == 0)
				sb.append(":");
			
			sb.append(member.getDotNetVarName() 
					+ "(" 
					+ member.getDotNetParaVarName()
					+")");
			
			if(index < (attrDescriptorList.size() - 1))
				sb.append(",\n");
			else
				sb.append(" {\n");
			
			index++;
		}		
  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent() + "}\n");
  	
  	return sb.toString();
  }
  
  //declare deconstructor of .NET business object
  public String declareDotNetDestructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ "~" + getDotNetClassName() + "();\n");
  	
  	return sb.toString();
  }
  
  
  //define deconstructor of .NET business object
  public String defineDotNetDestructor() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "::~" + getDotNetClassName() + "() {\n");
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	return sb.toString();
  } 
  
  /************************************************************
   * business object marshal helper class 
   ************************************************************/
  //the name of not-collection parameter for c++ business object 
  public String getCppParaName() {
  	return "unmanaged" + StringUtils.upperInitialCharacter(getUMLName());
  }
  //the name of collection parameter for c++ business object
  public String getCppCollectionParaName() {
  	return getCppParaName() + "List";
  }
  //the name of not-collection parameter for .NET business object
  public String getDotNetParaName() {
  	return "managed" + StringUtils.upperInitialCharacter(getUMLName());
  }
  //the name of collection parameter for .NET business object
  public String getDotNetCollectionParaName() {
  	return getDotNetParaName() + "List";
  }
  //the name of handle variable to .NET non-collection parameter 
  public String getDotNetHandleParaName() {
  	return getDotNetParaName() + "Handle";
  }
  //the name of handle varaible to .NET collection parameter
  public String getDotNetCollectionHandleParaName() {
  	return getDotNetCollectionParaName() + "Handle";
  }
  
  //construct business object marshal helper class name for this UML class 
  public String getMarshalClassName() {
  	return getCppClassName() + "Marshal";
  }
	
  //file name of marshal helper class .h
  public String getMarshalClassHeaderFileName() {
	return getMarshalClassName() + Constants.CPP_HEADER_FILE_EXT;
  }
	
  //file name of marshal helper class .cpp
  public String getMarshalClassCppFileName() {
  	return getMarshalClassName() + Constants.CPP_FILE_EXT;
  }
  
  //declare method to marshal single .NET business object
  public String declareMarshalBO() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfMarshalBO(false) + ";\n");  	
  	
  	return sb.toString();
  }    
  
  //define method to marshal single .NET business object
  public String defineMarshalBO() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfMarshalBO(true) + " {\n");  	
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "^ " + getDotNetParaName()
  			+ " = " + getDotNetHandleParaName() + ";\n\n");
  	  	
  	sb.append(formatter.currentIndent()
  			+ "if(nullptr != " + getDotNetParaName() + ") {\n");
  	formatter.indent();
  	
  	for(UMLClassAttrDotNetDescriptor member : getMembers()) {
  		sb.append(formatter.currentIndent()
  				+ "//marshal member " + member.getUMLName()
  				+ "\n");
  		sb.append(member.marshal());
  		sb.append("\n");
  	}
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	formatter.outdent();  	
  	sb.append(formatter.currentIndent() 
  			+ "}\n");
  	
  	return sb.toString();
  }  
  
  //signature of the method to marshal single .net business object
  private String getSignatureOfMarshalBO(boolean inCpp) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	
  	sb.append(formatter.currentIndent());
  	if(inCpp)
  		sb.append("void " + getMarshalClassName() + "::Marshal(\n");
  	else
  		sb.append("static void Marshal(\n");
  	
  	formatter.indent();
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ "const gcroot<" + getDotNetClassName() + "^> " 
  			+ getDotNetHandleParaName() + ",\n");
  	sb.append(formatter.currentIndent()
  			+ getCppClassName() + "& " + getCppParaName() + ")");
  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();
  	
  }
  
  //declare method to marshal collection of .NET business object
  public String declareMarshalBOCollection() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfMarshalBOCollection(false) + ";\n");
  	
  	return sb.toString();
  }
  
  //define method to marshal collection of .NET business object
  public String defineMarshalBOCollection() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfMarshalBOCollection(true) + " {\n");
  	
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ "List<" + getDotNetClassName() + "^>^ " + getDotNetCollectionParaName()
  			+ " = " + getDotNetCollectionHandleParaName() + ";\n\n");
  	sb.append(formatter.currentIndent()
  			+ getCppCollectionParaName() + ".clear();\n\n");
  	
  	sb.append(formatter.currentIndent()
  			+ "if(nullptr != " + getDotNetCollectionParaName() + ") {\n");  	
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ "for each(" + getDotNetClassName() + "^ " + getDotNetParaName()
  			+ " in " + getDotNetCollectionParaName() + ") {\n");  	
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ getCppClassName() + " " + getCppParaName() + ";\n");
  	sb.append(formatter.currentIndent()
  			+ "gcroot<" + getDotNetClassName() + "^> " + getDotNetHandleParaName()
  			+ " = " + getDotNetParaName() + ";\n");
  	
  	sb.append(formatter.currentIndent()
  			+ getMarshalClassName() + "::Marshal(" 
  			+ getDotNetHandleParaName() + ", "
  			+ getCppParaName() +");\n");
  	sb.append(formatter.currentIndent()
  			+ getCppCollectionParaName() + ".push_back(" 
  			+ getCppParaName()+");\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  			
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent() + "}\n");
  	
  	return sb.toString();
  }
  
  //signature of the method to marshal collection of .net business object
  private String getSignatureOfMarshalBOCollection(boolean inCpp) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	
  	sb.append(formatter.currentIndent());
  	if(inCpp)
  		sb.append("void " + getMarshalClassName() + "::Marshal(\n");
  	else
  		sb.append("static void Marshal(\n");
  	
  	formatter.indent();
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ "const gcroot<List<" + getDotNetClassName() + "^>^> " 
  			+ getDotNetCollectionHandleParaName() + ",\n");
  	sb.append(formatter.currentIndent()
  			+ "std::vector<" + getCppClassName() + ">& " 
  			+ getCppCollectionParaName() + ")");
  	
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();  	
  }
  
  //declare method to unmarshal single c++ business object
  public String declareUnmarshalBO() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfUnmarshalBO(false) + ";\n");
  	
  	return sb.toString();
  }
  //define method to unmarshal single c++ business object
  public String defineUnmarshalBO() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfUnmarshalBO(true) + " {\n");
  	
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "^ " + getDotNetParaName()
  			+ " = gcnew " + getDotNetClassName() + "();\n\n");
  	for(UMLClassAttrDotNetDescriptor member : getMembers()) {
  		sb.append(formatter.currentIndent()
  				+ "//unmarshal member " + member.getUMLName()
  				+ "\n");
  		sb.append(member.unmarshal());
  		sb.append("\n");
  	}
  	
  	sb.append(formatter.currentIndent()
  			+ "return " + getDotNetParaName() + ";\n");
  	formatter.outdent();
  	
  	sb.append(formatter.currentIndent() + "}\n");
  	
  	return sb.toString();
  }
  
  //signature of the method to unmarshal single c++ business object
  private String getSignatureOfUnmarshalBO(boolean inCpp) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	
  	sb.append(formatter.currentIndent());
  	if(inCpp)
  		sb.append("gcroot<" + getDotNetClassName() + "^> " 
  				+ getMarshalClassName() + "::Unmarshal(\n");  				
  	else
  		sb.append("static gcroot<" + getDotNetClassName() + "^> " 
  				+ "Unmarshal(\n");
  				
  	
  	formatter.indent();
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "const "+ getCppClassName() + "& " 
  			+ getCppParaName()+ ")");
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();
  	
  }
  
  //declare method to unmarshal collection of c++ business object
  public String declareUnmarshalBOCollection() {
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfUnmarshalBOCollection(false) + ";\n");
  	
  	return sb.toString();
  } 
  
  //define method to unmarshal collection of c++ business object
  public String defineUnmarshalBOCollection() {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	
  	sb.append(getSignatureOfUnmarshalBOCollection(true) + " {\n");
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ "List<" + getDotNetClassName() + "^>^ " + getDotNetCollectionParaName()
  			+ " = gcnew List<" + getDotNetClassName() + "^>();\n\n");
  	
  	sb.append(formatter.currentIndent()
  			+ "for(std::vector<" + getCppClassName() + ">::const_iterator it =  "
  			+ getCppCollectionParaName() + ".begin(); it != "
  			+ getCppCollectionParaName() + ".end(); ++it) {\n");
  	formatter.indent();
  	
  	sb.append(formatter.currentIndent()
  			+ getDotNetClassName() + "^ " + getDotNetParaName() 
  			+ " = " + getMarshalClassName() + "::Unmarshal(*it);\n");
  	sb.append(formatter.currentIndent()
  			+ getDotNetCollectionParaName() + "->Add("
  			+ getDotNetParaName() + ");\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n\n");
  	
  	sb.append(formatter.currentIndent()
  			+ "return " + getDotNetCollectionParaName() + ";\n");
  	
  	formatter.outdent();
  	sb.append(formatter.currentIndent()
  			+ "}\n");
  	
  	return sb.toString();
  } 
  
  //signature of the method to unmarshal collection of c++ business object
  private String getSignatureOfUnmarshalBOCollection(boolean inCpp) {
  	Formatter formatter = Formatter.getInstance();
  	StringBuilder sb = new StringBuilder();
  	  	
  	sb.append(formatter.currentIndent());
  	if(inCpp)
  		sb.append("gcroot<List<" + getDotNetClassName() + "^>^> " 
  				+ getMarshalClassName() + "::Unmarshal(\n");  				
  	else
  		sb.append("static gcroot<List<" + getDotNetClassName() + "^>^> " 
  				+ "Unmarshal(\n");
  				 
  	
  	formatter.indent();
  	formatter.indent();
  	sb.append(formatter.currentIndent()
  			+ "const std::vector<"+ getCppClassName() + ">& " 
  			+ getCppCollectionParaName() +")");
  	formatter.outdent();
  	formatter.outdent();
  	
  	return sb.toString();  	
  } 
}
