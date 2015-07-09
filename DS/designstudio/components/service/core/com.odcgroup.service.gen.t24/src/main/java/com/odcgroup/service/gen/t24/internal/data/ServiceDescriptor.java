package com.odcgroup.service.gen.t24.internal.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.odcgroup.service.gen.t24.internal.exceptions.InvalidModelException;
import com.odcgroup.service.gen.t24.internal.utils.Constants;
import com.odcgroup.service.gen.t24.internal.utils.JBCSubroutineNameUtils;
import com.odcgroup.service.gen.t24.internal.utils.StringUtils;
import com.odcgroup.service.model.component.AccessSpecifier;

public class ServiceDescriptor extends ElementDescriptor {
	private String componentName;
	private String packageName;
	private String dataPackageName;
	private String packageDirectory;
	private String dataPackageDirectory;
	private String operationDataResponsePackageName;
	private String operationDataResponsePackageDirectory;
	private String wsPackageName;
	private String wsDataPackageName;
	private String wsPackageDirectory;
	private String wsDataPackageDirectory;
	private String metaModelVersion;
	private List<OperationDescriptor> operations;
	private List<OperationDescriptor> genOperations;
	private List<ClassDefDescriptor> classDefDescriptors;
	

	private Logger logger = Logger.getLogger(ServiceDescriptor.class.getName());
	
	public ServiceDescriptor()
	{
		super("");
		this.componentName = "";
	}

	public ServiceDescriptor(String name, List<OperationDescriptor> operations, List<ClassDefDescriptor> classDefDescriptors)
	{
		super(name);
		init (name,operations,classDefDescriptors,"1.30.6");
	}

	public ServiceDescriptor(String name, List<OperationDescriptor> operations, List<ClassDefDescriptor> classDefDescriptors,String metaModelVersion)
	{
		super(name);
		init (name,operations,classDefDescriptors,metaModelVersion);
	}

	public void init (String name, List<OperationDescriptor> operations, List<ClassDefDescriptor> classDefDescriptors,String metaModelVersion) {
			this.componentName = name;
			this.packageName = generatePackageName(getName());
			this.packageDirectory = generatePackageDirectory(packageName);
			this.dataPackageName = generateDataPackageName(packageName);
			this.dataPackageDirectory = generateDataPackageDirectory(dataPackageName);
			this.operationDataResponsePackageName = generateOperationDataResponsePackageName(dataPackageName);
			this.operationDataResponsePackageDirectory = generateOperationDataResponsePackageDirectory(operationDataResponsePackageName);
			this.wsPackageName = generateWsPackageName(getName());
			this.wsPackageDirectory = generatePackageDirectory(wsPackageName);
			this.wsDataPackageName = Constants.WEB_SERVICE_DATA_PACKAGE_PREFIX;
			this.wsDataPackageDirectory = generatePackageDirectory(wsDataPackageName);
			this.metaModelVersion = metaModelVersion;
			this.operations = operations;
			this.classDefDescriptors = classDefDescriptors;
	 }

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentName() {
		return this.componentName;
	}
	public String getPackageName() {
		return packageName;
	}
	
	public String getDataPackageName() {
		return dataPackageName;
	}
	
	public String getPackageDirectory() {
		return packageDirectory;
	}
	
	public String getDataPackageDirectory() {
		return dataPackageDirectory;
	}
	
	public String getDataResponsePackageDirectory(){
		return operationDataResponsePackageDirectory;
	}
	
	public String getOperationDataResponsePackageName(){
		return operationDataResponsePackageName;
	}
	
	public String getWsPackageName() {
		return wsPackageName;
	}
	
	public String getWsDataPackageName() {
		return wsDataPackageName;
	}
	
	public String getWsPackageDirectory() {
		return wsPackageDirectory;
	}
	
	public String getWsDataPackageDirectory() {
		return wsDataPackageDirectory;
	}
	
	public List<OperationDescriptor> getOperations() {
		return operations;
	}

	public List<ClassDefDescriptor> getClassDefDescriptors() {
		return classDefDescriptors;
	}
	
	public void setClassDefDescriptors(List<ClassDefDescriptor> classDefDescriptors) {
		this.classDefDescriptors = classDefDescriptors;
	}
	
	public void setOperations(List<OperationDescriptor> operations) {
		this.operations = operations;
	}

	public String getServiceName() {
		// Get the service name from super, convert it into Lower case and remove unwanted bit
		return getName().toLowerCase().replace("service", "");
	}
	
	private String generatePackageName( String serviceName )
	{
		// Generate package name by removing the word "Service" from the name (if present),
		// convert to lower case, and add the package prefix
		String result = serviceName.toLowerCase();
		result = result.replace("service", "");
		result = Constants.SERVICE_PACKAGE_PREFIX + result;
		return result;
	}
	
	private String generatePackageDirectory( String pkg )
	{
		// Generate directory name from the package name by changing "." characters to "/" characters
		return pkg.replace(".", "/");
	}
	
	private String generateDataPackageName(String pkg) {
		return pkg + ".data";
	}
	
	private String generateDataPackageDirectory( String pkg )
	{
		// Generate directory name from the package name by changing "." characters to "/" characters
		return pkg.replace(".", "/");
	}
	
	private String generateOperationDataResponsePackageName ( String dataPkgName )
	{
		// Generate directory name from the package name by changing "." characters to "/" characters
		return dataPkgName + ".response";
	}
	
	private String generateOperationDataResponsePackageDirectory( String dataResponsePkgName)
	{
		// Generate directory name from the package name by changing "." characters to "/" characters
		return dataResponsePkgName.replace(".", "/");
	}
	
	
	
	private String generateWsPackageName( String serviceName )
	{
		// Generate web services package name by removing the word "Service" from the name (if present),
		// convert to lower case, and add the package prefix
		String result = serviceName.toLowerCase();
		result = result.replace("service", "");
		result = Constants.WEB_SERVICE_PACKAGE_PREFIX + result;
		return result;
	}
	
	public String getRestResourcePackageDirectory()
	{
		String pkg = Constants.REST_WEB_SERVICE_RESOURCE_PACKAGE;
		return pkg.replace(".", "/");
	}
	
	public String getRestResourcePackageName()
	{
		return Constants.REST_WEB_SERVICE_RESOURCE_PACKAGE;
	}
	
	public String getRestApplicationPackageDirectory()
	{
		String pkg = Constants.REST_WEB_SERVICE_APP_PACKAGE;
		return pkg.replace(".", "/");
	}
	
	public String getRestApplicationPackageName()
	{
		return Constants.REST_WEB_SERVICE_APP_PACKAGE;
	}
	
	public String getRestDataPackageDirectory()
	{
		String pkg = Constants.REST_WEB_SERVICE_DATA_PACKAGE;
		return pkg.replace(".", "/");
	}
	
	public String getRestDataPackageName()
	{
		return Constants.REST_WEB_SERVICE_DATA_PACKAGE;
	}
	
	public String getRestAdaptorPackageDirectory()
	{
		String pkg = Constants.REST_WEB_SERVICE_ADAPTORS_PACKAGE;
		return pkg.replace(".", "/");
	}
	
	public String getRestAdaptorPackageName()
	{
		return Constants.REST_WEB_SERVICE_ADAPTORS_PACKAGE;
	}
	
	public String getRestRepresentationPackageDirectory()
	{
		String pkg = Constants.REST_WEB_SERVICE_REPRESENTATION_PACKAGE;
		return pkg.replace(".", "/");
	}
	
	public String getRestRepresentationPackageName()
	{
		return Constants.REST_WEB_SERVICE_REPRESENTATION_PACKAGE;
	}
	
	public String getClassIdByName( String className )
	{
		Iterator<ClassDefDescriptor> iterator = classDefDescriptors.iterator();
		while ( iterator.hasNext() )
		{
			ClassDefDescriptor classDescriptor = iterator.next();
			
			if ( classDescriptor.getName().equals( className ) )
			{
				return classDescriptor.getId();
			}
		}

		return "";
	}
	
	public String jbcToTAFJClassName(String operationName) {
		return JBCSubroutineNameUtils.toTAFJClassName(getName() + "." + operationName);
	}
	
	// EJB Properties
	public String getEJBPackageName() {
		return getPackageName() + ".ejb";
	}

	public String getEJBPackageDir() {
		return getEJBPackageName().replace(".", File.separator);
	}

	public String getServiceEJBName() {
		return StringUtils.upperInitialCharacter(getName()) + "Bean";
	}
	
	public String getServiceEJBAPIName(String interfaceName) {
		return getServiceEJBName() + interfaceName;
	}
	
	public String getServiceEJBImplName(String frameworkName) {
		return getServiceEJBName() + frameworkName;
	}
	
	@Override
	public String toString() {
		return String.format("Service Descriptor: name=%s, packageName=%s, wsPackageName=%s, operations=%s, classDefDescriptors=%s", getName(), packageName, wsPackageName, operations, classDefDescriptors);
	}
	
	/**
	 * This method will return the ClassDefDescriptor schema in nested form
	 * @param classDesc
	 * @return
	 */
	public String getClassDefDescSchema(ClassDefDescriptor classDesc) {
		StringBuilder sb = new StringBuilder();
		sb.append(getSchemaHeader(classDesc));		// Schema Header
		
		// Process the Class Attributes Here
		// TODO : See RTC We need to enhance code generator to handle schema generation for huge classes (972644)
		if (classDesc.getAttributes().size() <= 50 ) {
			for (AttributeDescriptor attr : classDesc.getAttributes()) {
				sb.append(getElementFromAttr(attr));	// Elements
			}
		} else {
			sb.append("<xsd:error>Too many attributes. Schema Generation Skipped</xsd:error>");	// Elements
			logger.log(Level.SEVERE, "Skipping schema generation as too many attributes found! Please refactor the object...");
		}
		sb.append(getSchemaFooter());				// Schema Footer
		return sb.toString();
	}
	
	/**
	 * This method will return the ClassDefDescriptor structure in nested form
	 * @param classDesc
	 * @param level
	 * @return
	 */
	public String getComplexTypeStructure(ClassDefDescriptor classDesc, int level) throws InvalidModelException {
		StringBuilder sb = new StringBuilder();
		// Process the Class Attributes Here
		List<AttributeDescriptor> attributes = classDesc.getAttributes();
		for (int i = 0; i < attributes.size(); i++) {
			AttributeDescriptor attr = attributes.get(i);
			if (attr.getComplexity().equals(Complexity.PRIMITIVE)) {
				if (attr.getCardinality().equals(Cardinality.SINGLE)) {
					// primitive type Single
					sb.append("'").append(attr.getName()).append("'");
				} else {
					//Primitive type List
					sb.append("'PRIMITIVE_LIST':").append(Markers.getMarker(level+1)).append(":");
					sb.append("'").append(attr.getName()).append("'");
				}
			} else {
				if (attr.getCardinality().equals(Cardinality.SINGLE)) {
					//ComplexType Single
					sb.append("'COMPLEX_SINGLE':").append(Markers.getMarker(level+1)).append(":");
					sb.append("'").append(attr.getName()).append("':").append(Markers.getMarker(level+1)).append(":");
					sb.append(getComplexTypeStructure(getClassDefDecriptorByName(attr.getTypeName()), level+1));		
     			} else {
					//Complex Type List
     				sb.append("'COMPLEX_LIST':").append(Markers.getMarker(level+1)).append(":");
     				sb.append("'").append(attr.getName()).append("':").append(Markers.getMarker(level+1)).append(":");
					sb.append(getComplexTypeStructure(getClassDefDecriptorByName(attr.getTypeName()), level+2));
				}
			}
			if ( i < attributes.size() -1 ) {
				sb.append(":").append(Markers.getMarker(level)).append(":");
			}
		}
		return sb.toString();
	}
	
	/**
	 * This method will return element node prepared from AttributeDescriptor
	 * @param attr
	 * @return
	 */
	private String getElementFromAttr(AttributeDescriptor attr) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xsd:element name=\"").append(attr.getName()).append("\"");	// Start of <element>
		if (attr.getComplexity().equals(Complexity.PRIMITIVE)) {
			sb.append(" type=\"xsd:").append(attr.getTypeName().toLowerCase()).append("\"");// Simple Type
			if (attr.getCardinality().equals(Cardinality.SINGLE)) {
				sb.append(" minOccurs=\"0\" />");										// Single
			} else {
				sb.append(" minOccurs=\"0\" maxOccurs=\"unbounded\" />");	// Multiple
			}
		} else {
			// Now lets parse the Complex Type attribute
			if (attr.getCardinality().equals(Cardinality.SINGLE)) {
				sb.append(" minOccurs=\"0\">");
			} else {
				sb.append(" minOccurs=\"0\" maxOccurs=\"unbounded\">");
			}
			sb.append(getNestedComplexTypeSchema(attr.getTypeName()));	// Get the nested complex elements
			sb.append("</xsd:element>");	// Close of <element>
		}
		return sb.toString();
	}
	
	/**
	 * Return the nested complex type elements
	 * @param nestedTypeName
	 * @return
	 */
	private String getNestedComplexTypeSchema(String nestedTypeName) {
		StringBuilder sb = new StringBuilder("<xsd:complexType><xsd:sequence>");
		// Now lets find the Complex Type by name
		ClassDefDescriptor nestedClassDesc = getClassDefDecriptorByName(nestedTypeName);
		if (nestedClassDesc != null) {
			for (AttributeDescriptor attr : nestedClassDesc.getAttributes()) {
				sb.append(getElementFromAttr(attr));
			}
		} 
		sb.append("</xsd:sequence></xsd:complexType>");
		return sb.toString();
	}
	
	/**
	 * Method to generate the schema header
	 * @param classDesc Class Descriptor to process
	 * @return Header information with xmlns 
	 */
	private String getSchemaHeader(ClassDefDescriptor classDesc) {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<xsd:schema xmlns=\"http://www.temenos.com/T24/").append(getName()).append("/").append(classDesc.getName()).append("\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"http://www.temenos.com/T24/").append(getName()).append("/").append(classDesc.getName()).append("\" elementFormDefault=\"qualified\">");
		sb.append("<xsd:complexType name=\"").append(classDesc.getName()).append("\">");
		sb.append("<xsd:sequence>");
		return sb.toString();
	}
	
	/**
	 * Schema footer which will be constant
	 * @return
	 */
	private static String getSchemaFooter() {
		return "</xsd:sequence></xsd:complexType></xsd:schema>";
	}
	
	/**
	 * Retrieve ClassDefDecriptor by name
	 * @param className Class descriptor object name
	 * @return ClassDefDecriptor object from List
	 */
	private ClassDefDescriptor getClassDefDecriptorByName(String className) {
		// Now lets find the Complex Type by name
		for (ClassDefDescriptor nestedClassDesc : getClassDefDescriptors()) {
			if (nestedClassDesc.getName().equals(className)) {	// Found it, return
				return nestedClassDesc;
			}
		}
		return null;
	}

	/**
	 * @return the genOperations
	 */
	public List<OperationDescriptor> getGenOperations() {

		boolean isOldEnterpriseModel = this.metaModelVersion.equals("1.30.6");
		AccessSpecifier methodScope;

		if (genOperations == null) {
			genOperations = new ArrayList<OperationDescriptor>();
			for (OperationDescriptor operation:this.operations)
			{
				methodScope = operation.getAccessSpecifier();
				if ((isOldEnterpriseModel && (methodScope == AccessSpecifier.PUBLIC))||
					(!isOldEnterpriseModel && (methodScope == AccessSpecifier.EXTERNAL)))
				{
					//If Metamodel is 1.30.6, then consider only Public routines for Generator
					//If Metamodel greater than 1.30.6, then consider only External routines for Generator
					genOperations.add(operation);
				}
			}
		}
		return genOperations;
	}

}
