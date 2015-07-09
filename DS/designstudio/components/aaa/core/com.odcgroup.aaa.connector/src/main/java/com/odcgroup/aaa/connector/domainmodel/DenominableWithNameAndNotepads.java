package com.odcgroup.aaa.connector.domainmodel;

import java.util.List;
import java.util.Set;

/**
 * Entity which has a name, denom and notepads.
 *
 * @author Michael Vorburger
 */
public interface DenominableWithNameAndNotepads {

	/**
	 * Returns the <em>name</em> property.
	 * <p>[dict_id=1202003,name=Name,datatype=name_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]</p>
	 * @return the <em>name</em> property.
	 */
	String getName();

	/**
	 * Returns the <em>denom</em> property.
	 * <p>[dict_id=1202004,name=Denom,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]</p>
	 * @return the <em>denom</em> property.
	 */
	String getDenom();

	Set<DenominationEntity> getDenoms();
	
	List<NotepadEntity> getNotepads();
}