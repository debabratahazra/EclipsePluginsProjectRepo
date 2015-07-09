package com.odcgroup.t24.enquiry.properties.sources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FileVersion;
import com.odcgroup.t24.enquiry.enquiry.FileVersionOption;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.JBCRoutine;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFileTypePropertyDescriptor;
import com.odcgroup.t24.enquiry.ui.actions.NewEnquiryWizardPage;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 * @author satishnangi
 */
public class EnquiryPropertySourceWrapper implements IPropertySource {
	
	private IPropertySource source;
	private Companies companies = null;
	private Enquiry enquiry = null;
	private FileVersion fileVersion = null;
	private IPropertySource fileVersionPropertySource;
	private IPropertySource companyPropertySource ;
	private IPropertySourceProvider sourceProvider;
	private Boolean generateIFP = false;
	private String fileType = "";
	EnquiryPackage pack = EnquiryPackage.eINSTANCE;
	
	public EnquiryPropertySourceWrapper(IPropertySource source, IPropertySourceProvider sourceProvider, Enquiry enquiry) {
		this.source = source;
		this.enquiry = enquiry;
		this.sourceProvider = sourceProvider;
		initPropertySources();
		
	}
    public void initPropertySources(){
    	if (enquiry.getFileVersion() != null && enquiry.getFileVersion().size() != 0) {
			fileVersion = enquiry.getFileVersion().get(0);
		} else {
			fileVersion = EnquiryFactory.eINSTANCE.createFileVersion();
			fileVersion.getValues().add(FileVersionOption.NONE);
		}
		if (enquiry.getCompanies() != null) {
			companies = enquiry.getCompanies();
			companyPropertySource = sourceProvider.getPropertySource(companies);
		} else {
			companies = EnquiryFactory.eINSTANCE.createCompanies();
			companyPropertySource = sourceProvider.getPropertySource(sourceProvider.getPropertySource(companies));
		}
		resetFileType();
		fileVersionPropertySource = sourceProvider.getPropertySource(fileVersion);
    }
	@Override
	public Object getEditableValue() {
		return source.getEditableValue();
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return source.getPropertyDescriptors();
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Tool) {
			return getCommandName((Tool) id);
		}
		if (id instanceof FixedSort) {
			return ((FixedSort) id).getOrder();
		}
		if (id.equals(pack.getFileVersion_Values().getName())) {
			return fileVersionPropertySource.getPropertyValue(id);
		}
		if (id.equals("Companies")) {
			return getCompaniesPropertyValue();
		}
		if (id.equals(pack.getEnquiry_FileName().getName())) {
			Object obj = source.getPropertyValue(id);
			MdfName name = EnquiryUtil.getMdfName((String) obj);
			if (name != null) {
				return name.getLocalName().replace('_', '.');
			}
		}
		if (id instanceof Routine) {
			return getRoutineType(id);
		}
		if(id.equals(EnquiryFileTypePropertyDescriptor.ID)){
			return fileType;
		}
		if(isBooleanType(id)){
			if(source.getPropertyValue(id) == null)
				return false;
		}
		return source.getPropertyValue(id);
	}

	/**
	 * This method is used to identify the properties which are of type Boolean 
	 * and used to set a default value of false if tag is missing in the DSL
	 * 
	 * @param id
	 * @return
	 */
	private boolean isBooleanType(Object id) {
		return (id.equals(pack.getEnquiry_GenerateIFP().getName()) || id.equals(pack.getEnquiry_NoSelection().getName())
				|| id.equals(pack.getEnquiry_ZeroRecordsDisplay().getName()) || id.equals(pack.getWebService_PublishWebService().getName()));
	}

	@Override
	public boolean isPropertySet(Object id) {
		if(id instanceof Tool || id instanceof Routine){
			return  true;
		}
		if(id instanceof FixedSort){
			FixedSort fixedSort = (FixedSort)id;
			return sourceProvider.getPropertySource(fixedSort).isPropertySet(pack.getFixedSort_Order().getName());
		}
		if (id.equals("values")) {
			return fileVersionPropertySource.isPropertySet(id);
		}
		if (id.equals("Companies")) {
			return false;
		}
		if (id.equals(EnquiryFileTypePropertyDescriptor.ID)) {
			return false;
		}
		return source.isPropertySet(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
		if (id.equals("values")) {
			fileVersionPropertySource.resetPropertyValue(id);
		} else if (id.equals(EnquiryFileTypePropertyDescriptor.ID)) {
			fileType = "";
		} else {
			source.resetPropertyValue(id);
		}
        
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id instanceof Routine) {
			setRoutineName(id, value);
		} else if (id instanceof Tool) {
			setTool((Tool) id, (List<String>) value);
		} else if (id instanceof FixedSort) {
			setFixedSortProperties((FixedSort) id, value);
		} else if (id.equals(pack.getFileVersion_Values().getName())) {
			setFileVersionValues(value);
		} else if (id.equals("Companies")) {
			setCompaniesProperties(((ArrayList<String>) value));
		} else if (id.equals(pack.getEnquiry_FileName().getName())) {
			setApplicaiton(id, value);
			fileType = "";
		} else if (id.equals(EnquiryFileTypePropertyDescriptor.ID)) {
			fileType = (String) value;
			updateApplication();
		} else {
			source.setPropertyValue(id, value);
		}

	}

	/**
	 * @param arrayList
	 */
	private void setCompaniesProperties(ArrayList<String> arrayList) {
		ArrayList<String> codeList = new ArrayList<String>();
		for(String str : arrayList){
			if(str.equals("All Companies")){
				companyPropertySource.setPropertyValue(pack.getCompanies_All().getName(), Boolean.TRUE);
				companyPropertySource.setPropertyValue(pack.getCompanies_Code().getName(), codeList);
				enquiry.setCompanies(companies);
			}else if(str.equals("All Books")){
				source.setPropertyValue(pack.getEnquiry_ShowAllBooks().getName(), Boolean.TRUE);
			} else {
				codeList.add(str);
			}
		}
		if(!arrayList.contains("All Books")){
			source.setPropertyValue(pack.getEnquiry_ShowAllBooks().getName(), Boolean.FALSE);
		}
		if(!arrayList.contains("All Companies")){
			companyPropertySource.setPropertyValue(pack.getCompanies_All().getName(), Boolean.FALSE);
			enquiry.setCompanies(companies);
		}
		if(!codeList.isEmpty()){
			companyPropertySource.setPropertyValue(pack.getCompanies_Code().getName(), codeList);
			companyPropertySource.setPropertyValue(pack.getCompanies_All().getName(), null);
			enquiry.setCompanies(companies);
		}
	}
    private void setFileVersionValues(Object values){
    	ArrayList<FileVersionOption> options = ((ArrayList<FileVersionOption>) values);
    	if (fileVersionPropertySource != null) {
    		if(options.isEmpty()){
    			options.add(FileVersionOption.NONE);
    		}
			fileVersionPropertySource.setPropertyValue(pack.getFileVersion_Values().getName(), (ArrayList<FileVersionOption>) values);
			if(enquiry.getFileVersion() ==null || enquiry.getFileVersion().isEmpty()){
				enquiry.getFileVersion().add(fileVersion);
			}
		}
    }
	private ArrayList<String> getCompaniesPropertyValue() {
		ArrayList<String> propertyValues = new ArrayList<String>();
		if(enquiry.getShowAllBooks()!=null && enquiry.getShowAllBooks().booleanValue()){
			propertyValues.add("All Books");
		}
		if(companies !=null){
			if(companies.getAll() !=null && companies.getAll().booleanValue()){
				propertyValues.add("All Companies");
			}else if(!companies.getCode().isEmpty()){
					propertyValues.addAll(companies.getCode());
			}
		}
		
		return propertyValues;
	}
	
	private void resetFileType() {
		String applicationName = (String) source.getPropertyValue(pack.getEnquiry_FileName().getName());
		if (applicationName.endsWith("#")) {
			applicationName = applicationName.substring(0, applicationName.length() - 1);
		}
		for (int i = 1; i < NewEnquiryWizardPage.APPLICATION_FILE_TYPES.length; i++) {
			if(applicationName.endsWith(NewEnquiryWizardPage.APPLICATION_FILE_TYPES[i])) {
				fileType = applicationName.substring(applicationName.length()
						- NewEnquiryWizardPage.APPLICATION_FILE_TYPES[i].length());
				break;
			}
		}
	}

	private void updateApplication() {
		String applicationName = (String) source.getPropertyValue(pack.getEnquiry_FileName().getName());
		if (applicationName.endsWith("#")) {
			applicationName = applicationName.substring(0, applicationName.length() - 1);
		}
		for (int i = 1; i < NewEnquiryWizardPage.APPLICATION_FILE_TYPES.length; i++) {
			if (applicationName.endsWith(NewEnquiryWizardPage.APPLICATION_FILE_TYPES[i])) {
				applicationName = applicationName.substring(0, applicationName.length()
						- NewEnquiryWizardPage.APPLICATION_FILE_TYPES[i].length() - 1);
				break;
			}
		}
		if (!fileType.isEmpty()) {
			applicationName = applicationName + "$" + fileType;
		}
		applicationName += "#";
		setApplicaiton(pack.getEnquiry_FileName().getName(), applicationName);
	}

   private void setApplicaiton(Object id, Object value) {
	   String application = (String)value;
	   if(!application.isEmpty()){
		   if(application.startsWith("name:/")){
			   source.setPropertyValue(id, value);
		   } else {
			   application = "name:/"+application+"#";
			   source.setPropertyValue(id, application);
		   }
	   }
   }
   
   private void setFixedSortProperties(FixedSort fixedsort,Object value) {
	    IPropertySource fixedSortPropertySource = sourceProvider.getPropertySource(fixedsort);
	    fixedSortPropertySource.setPropertyValue(pack.getFixedSort_Order().getName(), value);
   }

  
  private List<String> getCommandName(Tool tool){
	  return tool.getCommand();
  }
  
  private void setTool(Tool tool,List<String> values){
	  IPropertySource toolSource = sourceProvider.getPropertySource(tool);
	  toolSource.setPropertyValue(pack.getTool_Command().getName(),values);
	   
  }
  
    /**
	 * @param value
	 */
	private void setRoutineName(Object routine , Object value) {
		IPropertySource source = sourceProvider.getPropertySource(routine);
		source.setPropertyValue(pack.getRoutine_Name(), value);
	}
	private Object getRoutineType(Object routine){
		if(routine instanceof JBCRoutine){
			return "JBC" ;
		}else {
			return "Java" ;
		}
	}
}
