package com.odcgroup.iris.rim.generation.streams.mappers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.IRISDomainMapper;
import com.odcgroup.iris.rim.generation.mappers.ResourceMapperCommon;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Generate a rim for the versions based on the MdfClasses (domain) This is not
 * using the standart object structure as the modelLoader is always expecting
 * the resource to be in a file (which is a shame) and fail with such errors :
 * The context object [com.odcgroup.t24.version.versionDSL.impl.VersionImpl]
 * must be stored in a resource
 * 
 * @author taubert
 * 
 */
public class ApplicationVersion2StreamMapper implements StreamMapper<Void> {

	private final Logger logger = LoggerFactory.getLogger(ApplicationVersion2StreamMapper.class);

	@Override
	public List<Void> map(EObject source, RimWriter destination, String resourceName) {
		Version version = (Version) source;
		MdfClass mdfClass = version.getForApplication();

		/*
		 * Look for the primary key field.
		 */
		String primaryKey = "{Id}";
		List<MdfProperty> fields = mdfClass.getProperties();
		for (MdfProperty oneField : fields) {
			if (oneField.isPrimaryKey()) {
				primaryKey = "{" + EMUtils.camelCaseName(oneField.getName()) + "}";
				break;
			}
		}

		boolean isAAArr = IRISDomainMapper.isAAResource(mdfClass);

		String applicationType = ""; // Default means no History, no Unauth.
		ApplicationType type = T24Aspect.getType(mdfClass);
		if (type != null) {
			applicationType = type.name();
		} else {
			logger.error("No Application type found for " + mdfClass.getName());
		}

		boolean isUnauthFile = ResourceMapperCommon.isUnauthFile(applicationType);
		boolean isHistFile = ResourceMapperCommon.isHistFile(applicationType);

		List<String> template = getTemplate(isAAArr, isUnauthFile, isHistFile);

		String shortEntityName = resourceName.substring(3); // (verCurrency ->
															// Currency)

		for (String sLine : template) {
			sLine = sLine.replace("{EntityName}", resourceName);
			sLine = sLine.replace("{PrimaryKey}", primaryKey);
			sLine = sLine.replace("{ShortEntityName}", shortEntityName);
			destination.println(sLine);
		}

		return null;
	}

	private List<String> getTemplate(boolean isAAArr, boolean isUnauthFile, boolean isHistFile) {
		List<String> template = new ArrayList<String>();
		template.add("domain T24 {");
		template.add("	use common.CoreCommands.*");
		template.add("	use common.HTTPEvents.*");
		template.add("	use common.ODataCommands.*");
		template.add("	use common.T24Commands.*");
		template.add("	use common.T24Events.*");
		template.add("	use common.NextState.*");
		template.add("	use common.Errors.*");
		template.add("	use T24.ContextEnquiry.*");
		template.add("	rim {EntityName} {");
		template.add("		command GETEntities");
		template.add("		command GetLiveEntity");
		template.add("		command GetIauthEntity");
		template.add("		command GetHauthEntity");
		template.add("		command NoopGET");
		template.add("		command T24FieldMetadata");
		template.add("		command GetIauthEntities");
		template.add("		command CreateEntity");
		template.add("		command InputEntity");
		template.add("		command HoldEntity");
		template.add("		command AuthoriseEntity");
		template.add("		command ReviewEntity");
		template.add("		command ReverseEntity");
		template.add("		command ValidateEntity");
		template.add("		command RestoreEntity");
		template.add("		command DeleteEntity");
		template.add("		command T24Enrichment");
		template.add("		command CheckAutoIdSupport");
		template.add("		command GetHauthEntities");
		template.add("		command T24NextState");
		template.add("		basepath: \"/{companyid}\"");
		template.add("	");
		template.add("		resource {EntityName}s {");
		template.add("			type: collection");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : GETEntities {");
		template.add("				properties [ filter = \"{filter}\" ]");
		template.add("				} ]");
		template.add("			path: \"/{EntityName}s()\"");
		template.add("			GET *-> {EntityName} {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("			}");
		template.add("			GET *-> {EntityName}_see {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"see record\"");
		template.add("			}");
		template.add("			POST -> {EntityName}_new {");
		template.add("				title: \"create new deal\"");
		template.add("				condition: OK({EntityName}_autoId)");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			POST -> {EntityName}_populate {");
			template.add("				title: \"populate existing deal\"");
			template.add("			}");
		}
		template.add("			PUT *-> {EntityName}_input {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"input deal\"");
		template.add("			}");
		template.add("			POST *-> {EntityName}_audit {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"audit deal\"");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			POST *-> {EntityName}_reverse {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"reverse deal\"");
			template.add("			}");
		}
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName} {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : GetLiveEntity ]");
		template.add("			path: \"/{EntityName}s('{id}')\"");
		template.add("			POST +-> {EntityName}_metadata {");
		template.add("				title: \"metadata\"");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			GET --> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
		}
		template.add("			POST -> {EntityName}_validate {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"validate deal\"");
		template.add("			}");
		template.add("			PUT -> {EntityName}_input {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"input deal\"");
		template.add("			}");
		template.add("			POST -> {EntityName}_audit {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"audit deal\"");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			POST -> {EntityName}_reverse {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"reverse deal\"");
			template.add("			}");
		}
		template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
		template.add("				parameters [ entity = \"{EntityName}\" ]");
		template.add("				title: \"Context Enquiries\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}_see {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : GetLiveEntity ]");
		template.add("          relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}\", \"http://temenostech.temenos.com/rels/see\" ]");
		template.add("			path: \"/{EntityName}s('{id}')/see\"");
		template.add("			POST +-> {EntityName}_metadata {");
		template.add("				title: \"metadata\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			template.add("		resource {EntityName}_IAuth_see {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ GET : GetIauthEntity ]");
			template.add("          relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}_IAuth\", \"http://temenostech.temenos.com/rels/see\" ]");
			template.add("			path: \"/{EntityName}s_IAuth('{id}')/see\"");
			template.add("		}");
			template.add("	");
			if (isHistFile) {
				template.add("		resource {EntityName}_HAuth_see {");
				template.add("			type: item");
				template.add("			entity: {EntityName}");
				template.add("			commands [ GET : GetHauthEntity ]");
				template.add("          relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}_HAuth\", \"http://temenostech.temenos.com/rels/see\" ]");
				template.add("			path: \"/{EntityName}s_HAuth('{id}')/see\"");
				template.add("		}");
				template.add("	");
			}
		}
		template.add("		resource {EntityName}_ContextEnquiries {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : NoopGET ]");
		template.add("			path: \"/{EntityName}/ContextEnquiries\"");
		if (!isAAArr) {
			template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}s {");
			template.add("				parameters [ param = \"list\" ]");
			template.add("				title: \"List live deals\"");
			template.add("			}");

			if (isUnauthFile) {
				template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}sUnauth {");
				template.add("				parameters [ param = \"list\" ]");
				template.add("				title: \"List unauthorised deals\"");
				template.add("			}");
				if (isHistFile) {
					template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}sHist {");
					template.add("				parameters [ param = \"list\" ]");
					template.add("				title: \"List history deals\"");
					template.add("			}");
				}
			}
			template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}s {");
			template.add("				title: \"Search live deals\"");
			template.add("			}");
			if (isUnauthFile) {
				template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}sUnauth {");
				template.add("				title: \"Search unauthorised deals\"");
				template.add("			}");
				if (isHistFile) {
					template.add("			GET -> T24.enqlist{ShortEntityName}.enqlist{ShortEntityName}sHist {");
					template.add("				title: \"Search history deals\"");
					template.add("			}");
				}
			}
		}
		template.add("			GET +-> ContextEnquiryList {");
		template.add("				parameters [ entity = \"{EntityName}\" ]");
		template.add("				title: \"Other context Enquiries\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}_metadata {");
		template.add("			type: collection");
		template.add("			entity: T24FieldMetadata");
		template.add("			commands [ POST : T24FieldMetadata ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/metadata\" ]");
		template.add("			path: \"/{EntityName}s()/metadata\"");
		template.add("		}");
		template.add("	");

		if (isUnauthFile) {
			template.add("		resource {EntityName}s_IAuth {");
			template.add("			type: collection");
			template.add("			entity: {EntityName}");
			template.add("			commands [ GET : GetIauthEntities {");
			template.add("				properties [ filter = \"{filter}\" ]");
			template.add("				} ]");
			template.add("			path: \"/{EntityName}s_IAuth()\"");
			template.add("			GET *-> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("			}");
			template.add("			POST -> {EntityName}_new {");
			template.add("				title: \"create new deal\"");
			template.add("				condition: OK({EntityName}_autoId)");
			template.add("			}");
			template.add("			POST -> {EntityName}_populate {");
			template.add("				title: \"populate existing deal\"");
			template.add("			}");
			template.add("			PUT *-> {EntityName}_input {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"input deal\"");
			template.add("			}");
			template.add("			DELETE *-> {EntityName}_delete {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"delete\"");
			template.add("			}");
			template.add("			PUT *-> {EntityName}_authorise {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"authorise deal\"");
			template.add("			}");
			template.add("		}");
			template.add("	");
		}
		template.add("		resource {EntityName}_new {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ POST : CreateEntity ]");
		template.add("			relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}\",\"http://temenostech.temenos.com/rels/new\" ]");
		template.add("			path: \"/{EntityName}s()/new\"");
		template.add("			POST +-> {EntityName}_metadata {");
		template.add("				title: \"metadata\"");
		template.add("			}");
		template.add("			POST -> {EntityName}_validate {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"validate deal\"");
		template.add("			}");
		template.add("			PUT -> {EntityName}_input {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"input deal\"");
		template.add("			}");
		template.add("			POST -> {EntityName}_hold {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"hold deal\"");
		template.add("			}");
		template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
		template.add("				parameters [ entity = \"{EntityName}\" ]");
		template.add("				title: \"Context Enquiries\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}_input {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ PUT : InputEntity, POST : InputEntity ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/input\" ]");
		template.add("			path: \"/{EntityName}s('{id}')\"");
		template.add("			GET +-> ProcessErrors {");
		template.add("				title: \"errors\"");
		template.add("				condition: OK(CheckIfError)");
		template.add("			}");
		template.add("			GET --> nextState {");
		template.add("				condition: OK(nextState)");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			GET --> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
		}
		template.add("			GET --> {EntityName} {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				condition: OK({EntityName})");
		template.add("			}");
		template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
		template.add("				parameters [ entity = \"{EntityName}\" ]");
		template.add("				title: \"Context Enquiries\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}_hold {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ POST : HoldEntity ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/hold\" ]");
		template.add("			path: \"/{EntityName}s('{id}')/hold\"");
		template.add("			PUT -> {EntityName}_input {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"input deal\"");
		template.add("			}");
		template.add("			DELETE -> {EntityName}_delete {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"delete\"");
		template.add("			}");
		template.add("			onerror --> common.Errors.Errors");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			template.add("		resource {EntityName}_authorise {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ PUT : AuthoriseEntity ]");
			template.add("			relations [ \"http://temenostech.temenos.com/rels/authorise\" ]");
			template.add("			path: \"/{EntityName}s('{id}')/authorise\"");
			template.add("			GET --> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
			template.add("			GET --> {EntityName} {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName})");
			template.add("			}");
			template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
			template.add("				parameters [ entity = \"{EntityName}\" ]");
			template.add("				title: \"Context Enquiries\"");
			template.add("			}");
			template.add("			onerror --> common.Errors.Errors");
			template.add("		}");
			template.add("	");
		}
		template.add("		resource {EntityName}_audit {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ POST : ReviewEntity ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/review\" ]");
		template.add("			path: \"/{EntityName}s('{id}')/review\"");
		template.add("			GET -> {EntityName} {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("			}");
		template.add("			onerror --> common.Errors.Errors");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			template.add("		resource {EntityName}_reverse {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ PUT : ReverseEntity ]");
			template.add("			relations [ \"http://temenostech.temenos.com/rels/reverse\" ]");
			template.add("			path: \"/{EntityName}s('{id}')/reverse\"");
			template.add("			GET +-> ProcessErrors {");
			template.add("				title: \"errors\"");
			template.add("				condition: OK(CheckIfError)");
			template.add("			}");
			template.add("			GET --> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
			if (isHistFile) {
				template.add("			GET --> {EntityName}_HAuth {");
				template.add("				parameters [ id = \"{PrimaryKey}\" ]");
				template.add("				condition: OK({EntityName}_HAuth)");
				template.add("			}");
			}
			template.add("		}");
			template.add("	");
		}

		template.add("		resource {EntityName}_validate {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ POST : ValidateEntity ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/validate\" ]");
		template.add("			path: \"/{EntityName}s('{id}')/validate\"");
		template.add("			GET +-> ProcessErrors {");
		template.add("				title: \"errors\"");
		template.add("				condition: OK(CheckIfError)");
		template.add("			}");
		template.add("			POST +-> {EntityName}_metadata {");
		template.add("				title: \"metadata\"");
		template.add("			}");
		template.add("			PUT -> {EntityName}_input {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"input deal\"");
		template.add("			}");
		template.add("			POST -> {EntityName}_hold {");
		template.add("				parameters [ id = \"{PrimaryKey}\" ]");
		template.add("				title: \"hold deal\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			template.add("		resource {EntityName}_IAuth {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ GET : GetIauthEntity ]");
			template.add("			path: \"/{EntityName}s_IAuth('{id}')\"");
			template.add("			POST +-> {EntityName}_metadata {");
			template.add("				title: \"metadata\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_validate {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"validate deal\"");
			template.add("			}");
			template.add("			PUT -> {EntityName}_input {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"input deal\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_hold {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"hold deal\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_authorise {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"authorise deal\"");
			template.add("			}");
			template.add("			DELETE -> {EntityName}_delete {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"delete\"");
			template.add("			}");
			template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
			template.add("				parameters [ entity = \"{EntityName}\" ]");
			template.add("				title: \"Context Enquiries\"");
			template.add("			}");
			template.add("		}");
			template.add("	");
		}
		template.add("		resource {EntityName}_delete {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ DELETE : DeleteEntity ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/delete\" ]");
		template.add("			path: \"/{EntityName}s('{id}')/delete\"");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}Entry {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : NoopGET ]");
		template.add("			relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}\",\"http://temenostech.temenos.com/rels/contract\" ]");
		template.add("			path: \"/{EntityName}Entry\"");
		template.add("			POST -> {EntityName}_new {");
		template.add("				title: \"create new deal\"");
		template.add("				condition: OK({EntityName}_autoId)");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			POST -> {EntityName}_populate {");
			template.add("				title: \"populate existing deal\"");
			template.add("			}");
		}
		template.add("			GET +-> {EntityName}_see {");
		template.add("				parameters [ id = \"@\" ]");
		template.add("				title: \"See transaction\"");
		template.add("			}");
		if (isUnauthFile) {
			template.add("			GET +-> {EntityName}_IAuth_see {");
			template.add("				parameters [ id = \"@\" ]");
			template.add("				title: \"GetIauthEntity\"");
			template.add("			}");
			if (isHistFile) {
				template.add("			GET +-> {EntityName}_HAuth_see {");
				template.add("				parameters [ id = \"@\" ]");
				template.add("				title: \"GetHauthEntity\"");
				template.add("			}");
			}
			template.add("			GET +-> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"@\" ]");
			template.add("				title: \"GetIauthEntity\"");
			template.add("			}");
			if (isHistFile) {
				template.add("			GET +-> {EntityName}_HAuth {");
				template.add("				parameters [ id = \"@\" ]");
				template.add("				title: \"GetHauthEntity\"");
				template.add("			}");
			}
		}
		template.add("			GET +-> {EntityName}_ContextEnquiries {");
		template.add("				title: \"All context Enquiries\"");
		template.add("			}");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			/*
			 * Definitly not sure why we are not generating the _populate is not
			 * an Unauth file (Thierry)
			 */
			template.add("		resource {EntityName}_populate {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ POST : CreateEntity ]");
			template.add("			relations [ \"http://schemas.microsoft.com/ado/2007/08/dataservices/related/{EntityName}\",\"http://temenostech.temenos.com/rels/populate\" ]");
			template.add("			path: \"/{EntityName}s()/populate\"");
			template.add("			POST -> {EntityName}_validate {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"validate deal\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_input {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"input deal\"");
			template.add("			}");
			template.add("			DELETE -> {EntityName}_delete {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"delete\"");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
			template.add("			POST -> {EntityName}_metadata {");
			template.add("				title: \"metadata\"");
			template.add("			}");
			template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
			template.add("				parameters [ entity = \"{EntityName}\" ]");
			template.add("				title: \"Context Enquiries\"");
			template.add("			}");
			template.add("		}");
			template.add("	");
		}
		template.add("	");
		template.add("		resource {EntityName}s_enrichment {");
		template.add("			type: item");
		template.add("			entity: Enrichment");
		template.add("			commands [ GET : T24Enrichment {");
		template.add("				properties [ entity = \"{EntityName}\" ]");
		template.add("				} ]");
		template.add("			relations [ \"http://temenostech.temenos.com/rels/enrichment\" ]");
		template.add("			path: \"/{EntityName}s/enrichment\"");
		template.add("		}");
		template.add("	");
		template.add("		resource {EntityName}_autoId {");
		template.add("			type: item");
		template.add("			entity: {EntityName}");
		template.add("			commands [ GET : CheckAutoIdSupport ]");
		template.add("		}");
		template.add("	");
		if (isUnauthFile) {
			template.add("		resource {EntityName}_restore {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ POST : RestoreEntity ]");
			template.add("			relations [ \"http://temenostech.temenos.com/rels/restore\" ]");
			template.add("			path: \"/{EntityName}s('{id}')/restore\"");
			template.add("			GET --> {EntityName}_IAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName}_IAuth)");
			template.add("			}");
			template.add("			GET --> {EntityName} {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				condition: OK({EntityName})");
			template.add("			}");
			template.add("			POST -> {EntityName}_metadata {");
			template.add("				title: \"metadata\"");
			template.add("			}");
			template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
			template.add("				parameters [ entity = \"{EntityName}\" ]");
			template.add("				title: \"Context Enquiries\"");
			template.add("			}");
			template.add("			onerror --> common.Errors.Errors");
			template.add("		}");
			template.add("	");
		}
		if (isHistFile) {
			template.add("		resource {EntityName}s_HAuth {");
			template.add("			type: collection");
			template.add("			entity: {EntityName}");
			template.add("			commands [ GET : GetHauthEntities {");
			template.add("				properties [ filter = \"{filter}\" ]");
			template.add("				} ]");
			template.add("			path: \"/{EntityName}s_HAuth()\"");
			template.add("			GET *-> {EntityName}_HAuth {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("			}");
			template.add("			POST -> {EntityName}_new {");
			template.add("				title: \"create new deal\"");
			template.add("				condition: OK({EntityName}_autoId)");
			template.add("			}");
			template.add("			POST -> {EntityName}_populate {");
			template.add("				title: \"populate existing deal\"");
			template.add("			}");
			template.add("			POST *-> {EntityName}_restore {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"restore deal\"");
			template.add("			}");
			template.add("		}");
			template.add("	");
			template.add("		resource {EntityName}_HAuth {");
			template.add("			type: item");
			template.add("			entity: {EntityName}");
			template.add("			commands [ GET : GetHauthEntity ]");
			template.add("			path: \"/{EntityName}s_HAuth('{id}')\"");
			template.add("			POST +-> {EntityName}_metadata {");
			template.add("				title: \"metadata\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_validate {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"validate deal\"");
			template.add("			}");
			template.add("			PUT -> {EntityName}_input {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"input deal\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_hold {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"hold deal\"");
			template.add("			}");
			template.add("			POST -> {EntityName}_authorise {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"authorise deal\"");
			template.add("			}");
			template.add("			DELETE -> {EntityName}_delete {");
			template.add("				parameters [ id = \"{PrimaryKey}\" ]");
			template.add("				title: \"delete\"");
			template.add("			}");
			template.add("			GET -> \"T24.ContextEnquiry.ContextEnquiryList\" {");
			template.add("				parameters [ entity = \"{EntityName}\" ]");
			template.add("				title: \"Context Enquiries\"");
			template.add("			}");
			template.add("		}");
			template.add("	");
		}
		template.add("	}");
		template.add("	");
		template.add("}");
		return template;
	}

}
