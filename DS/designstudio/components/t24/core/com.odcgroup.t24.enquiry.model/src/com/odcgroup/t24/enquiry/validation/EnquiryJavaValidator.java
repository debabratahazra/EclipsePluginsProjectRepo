package com.odcgroup.t24.enquiry.validation;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.odcgroup.t24.enquiry.enquiry.AndOr;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType;
import com.odcgroup.t24.enquiry.enquiry.SortOrder;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.translation.EnquiryModelTranlsationUtill;
import com.odcgroup.translation.TranslationDslUtill;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;
 

public class EnquiryJavaValidator extends AbstractEnquiryJavaValidator {
	
    @Inject
    private ResourceDescriptionsProvider resourceDescriptionsProvider;
    
    @Inject
    private IQualifiedNameProvider qualifiedNameProvider;

    /**
     * check if the description contain CR, LF characters.
     * @param version
     */
	@Check(CheckType.FAST)
	public void checkTranslationDoesNotContainCROrLF(Enquiry enquiry) {
		for(Translations translations: EnquiryModelTranlsationUtill.getAllEnqiryTranslations(enquiry)) {
			List<LocalTranslation> localTranslationList = ((LocalTranslations)translations).getTranslations();
			for (LocalTranslation localTranslation : localTranslationList) {
				if (!TranslationDslUtill.isValidTranslation(localTranslation)) {
					error(TranslationDslUtill.getTranslationError(localTranslation), 
						  localTranslationList.get(0).eContainer(), 
						  null, 
						  ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
				}
			}		
		}
	}
	
    @Check(CheckType.NORMAL) 
    public void checkEnquiryDuplicate(Enquiry enquiry) {
    	
    	QualifiedName enquiryQN = qualifiedNameProvider.getFullyQualifiedName(enquiry); 
        IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(enquiry.eResource());
        
        Iterable<IEObjectDescription> objects = resourceDescriptions.getExportedObjects(EnquiryPackage.Literals.ENQUIRY, enquiryQN, true);
        if (IterableExtensions.size(objects) > 1) {
       		 error("An enquiry with the same name already exists", EnquiryPackage.Literals.ENQUIRY__NAME);
        }

    }
 
    @Check(CheckType.NORMAL)
    public void checkShouldBeChangedDrillDownType(Enquiry enquiry){
    	EList<DrillDown> drillDowns = enquiry.getDrillDowns();
    	for(DrillDown drillDown : drillDowns){
    		if(drillDown.getType() instanceof ShouldBeChangedType){
    			String value = ((ShouldBeChangedType)drillDown.getType()).getValue();
    			error("A drillDown contains shouldbeChanged :" + value, 
    					drillDown.getType(), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
    		}
    	}
    }
    
    @Check(CheckType.NORMAL)
    public void checkIntrospectionMessage(Enquiry enquiry){
    	EList<String> introspectionMessages = enquiry.getIntrospectionMessages();
    	for(String introspectionMessage : introspectionMessages){
    			error("Introspection message :" + introspectionMessage, EnquiryPackage.Literals.ENQUIRY__INTROSPECTION_MESSAGES);
    	}
    }
    
    
	/**
	 * Check if the physical file name of resource match the "name" in the model
	 * (of the "root" content EObject).
	 * 
	 * @param enquiry
	 */
	@SuppressWarnings("deprecation")
	@Check(CheckType.FAST)
	public void checkFileNameAndRootModelNameAreSame(Enquiry enquiry) {
		String enquiryName = enquiry.eResource().getURI().lastSegment();
		enquiryName = URLDecoder.decode(enquiryName);
		String enquiryFileName = enquiryName.substring(0, enquiryName.lastIndexOf("."));
		String enqName = enquiry.getName();

		if (StringUtils.isNotBlank(enquiryFileName) && StringUtils.isNotBlank(enqName)
				&& !enquiryFileName.equals(enqName)) {
			error("Enquiry file name and root model name are not same.", EnquiryPackage.Literals.ENQUIRY__NAME);
		}
	}
	
	@Check(CheckType.FAST)
	public void checkTargetMandatoryFieldsInEnquiry(Enquiry enquiry) {
		Target target = enquiry.getTarget();
		if (target != null) {
			String application = target.getApplication();
			EList<TargetMapping> mappings = target.getMappings();
			if (application == null) {
				error("Enquiry" + "'" + enquiry.getName() + "'" + ":for Target Feature Target.application must be set.",
						target, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
			if (mappings.isEmpty()) {
				error("Enquiry" + "'" + enquiry.getName() + "'"
						+ ":for Target at least 1 Feature Target.mappings required.", target, null,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}
	
	
	@Check(CheckType.FAST)
	public void checkCompaniesMandatoryFields(Enquiry enquiry) {
		Companies companies = enquiry.getCompanies();
		if (companies != null) {
			Boolean all = companies.getAll();
			EList<String> codes = companies.getCode();
			if (all == null & codes == null) {
				error("Enquiry"
						+ "'"+ enquiry.getName()+ "'"
						+ ":for companies at least 1 Feature Companies.code required or Feature Companies.all must be set.",
						companies, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}
	
	@Check(CheckType.FAST)
	public void checkFixedSortMandatoryFields(Enquiry enquiry) {
		EList<FixedSort> fixedSortList = enquiry.getFixedSorts();
		for (FixedSort fixedSort : fixedSortList) {
			String field = fixedSort.getField();
			 SortOrder sortOrder = fixedSort.getOrder();
			if (field == null ) {
				error("Enquiry" + "'" + enquiry.getName() + "'"
						+ ":for FixedSort Feature FixedSort.field must be set.", fixedSort, null,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
			if (sortOrder == null )  {
				error("Enquiry" + "'" + enquiry.getName() + "'"
						+ ":for FixedSort Feature FixedSort.sortOrder field must be set.", fixedSort, null,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}
	
	@Check(CheckType.FAST)
	public void checkSelectionExpressionMandatoryFields(Enquiry enquiry) {
		SelectionExpression selectionExpression = enquiry.getCustomSelection();
		EList<Selection> selectionList = selectionExpression.getSelection();
		for (Selection selection : selectionList) {
			AndOr operator = selection.getOperator();
			if (operator == null) {
				error("Enquiry" + "'" + enquiry.getName() + "'" + ":for Selection Feature Selection.andOr must be set.",
						selection, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}
	
	@Check(CheckType.FAST)
	public void checkEnquiryFieldMandatoryFields(Enquiry enquiry) {
		EList<Field> fields = enquiry.getFields();
		for(Field field: fields){
			BreakCondition breakCondition = field.getBreakCondition();
			if(breakCondition !=null) {
				BreakKind kind = breakCondition.getBreak();
				String fieldName =breakCondition.getField();
				if(kind == null & fieldName == null){
					error("Enquiry" + "'" + enquiry.getName() + "'" + ":for BreakConditon Feature BreakCondition.breakKind or BreakCondition.field must be set.",
								field, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
					
				}
			}
		}
	}
	
	@Check(CheckType.FAST)
	public void checkEnquiryFieldNameDuplicate(Enquiry enquiry) {
		Map<String, Integer> duplicates = new HashMap<String, Integer>();
		List<Field> fields = enquiry.getFields();
		for (Field field : fields) {
			String key = field.getName();
			Integer n = duplicates.get(key);
			n = (n == null) ? 1 : ++n;
			duplicates.put(key, n);
		}
		for (Field field : fields) {
			String key = field.getName();
			if (duplicates.get(key) > 1) {
				error("Enquiry " + "'" + enquiry.getName() + "'" + ": has duplicate field name: '" + field.getName()
						+ "'", field, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}
}
