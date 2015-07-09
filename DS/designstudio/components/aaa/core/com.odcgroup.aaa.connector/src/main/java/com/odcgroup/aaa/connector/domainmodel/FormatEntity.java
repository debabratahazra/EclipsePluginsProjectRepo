package com.odcgroup.aaa.connector.domainmodel;

import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumn;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumns;

/**
 * T'A Format.
 * 
 * This file has been originally been automatically @generated,
 * but then manually modified (awaiting an adapted generator).
 * 
 * <p>[dict_id=1202,name=Format,sqlname_c=format,attributes=...]</p>
 * 
 * @author Michael Vorburger (MVO)
 */
@Entity(name = "Format")
@Table(name = "format_vw")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuppressWarnings("serial")
public class FormatEntity implements DenominableWithNameAndNotepads
{
	//-------------------------------------------------------------------------
	// Fields

	/* The <em>identifier</em> property.
	 * <p>[dict_id=1202001,name=Identifier,datatype=id_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
	</p>* @primaryKey	 */
	@Id
	@Column(name = "id")
	private long id;

	/* The <em>blockFontSize</em> property.
	 * <p>[dict_id=1202046,name=Block Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=39]
	</p>	 */
	@Column(name = "header_font_size_n")
	private Integer blockFontSize;

	/* The <em>riskView</em> property.
	 * <p>[dict_id=1202021,name=Risk View,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=risk_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3837,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=8]
	</p>	 */
	@Column(name = "risk_f")
	private Integer riskView;

//	/* The <em>totalTitle</em> property.
//	 * <p>[dict_id=1202011,name=Total Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=total_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "total_title_msg_id")
//	private MessageEntity totalTitle;

	/* The <em>graphHeight</em> property.
	 * <p>[dict_id=1202064,name=Graph Height,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_height_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=62]
	</p>	 */
	@Column(name = "graph_height_n")
	private Integer graphHeight;

	/* The <em>grayLines</em> property.
	 * <p>[dict_id=1202036,name=Gray Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=34]
	</p>	 */
	@Column(name = "gray_lines_n")
	private Integer grayLines;

	/* The <em>overlap</em> property.
	 * <p>[dict_id=1202028,name=Overlap,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=overlap_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=13]
	</p>	 */
	@Column(name = "overlap_n")
	private Integer overlap;

//	/* The <em>tableContentTitle</em> property.
//	 * <p>[dict_id=1202010,name=Table Content Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=toc_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "toc_title_msg_id")
//	private MessageEntity tableContentTitle;

	/* The <em>graphWidth</em> property.
	 * <p>[dict_id=1202063,name=Graph Width,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=61]
	</p>	 */
	@Column(name = "graph_width_n")
	private Integer graphWidth;

	/* The <em>columnTitleGrayForce</em> property.
	 * <p>[dict_id=1202041,name=Column Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=41]
	</p>	 */
	@Column(name = "header_gray_force_n")
	private Integer columnTitleGrayForce;

//	/* The <em>denomination</em> property.
//	 * <p>[dict_id=1202076,name=Denomination,datatype=id_t,dictEntity=Format,referencedDictEntity=Denomination,parentAttribute=<null>,sqlname_c=denomination,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=18]
//	</p>	 */
//	@OneToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "denomination")
//	private java.util.Set<DenominationEntity> denomination;

	/* The <em>name</em> property.
	 * <p>[dict_id=1202003,name=Name,datatype=name_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]
	</p>	 */
	@Column(name = "name")
	private String name;

	/* The <em>columnTitleAfterLines</em> property.
	 * <p>[dict_id=1202044,name=Column Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=44]
	</p>	 */
	@Column(name = "header_aft_lines_n")
	private Integer columnTitleAfterLines;

	/* The <em>graphHorizontalGrid</em> property.
	 * <p>[dict_id=1202068,name=Graph Horizontal Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_horz_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=66]
	</p>	 */
	@Column(name = "graph_horz_grid_f")
	private Integer graphHorizontalGrid;

	/* The <em>modificationDate</em> property.
	 * <p>[dict_id=1202029,name=Modification Date,datatype=datetime_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=modif_d,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=13]
	</p>	 */
	@Column(name = "modif_d")
	private java.util.Date modificationDate;

	/* The <em>titleFrame</em> property.
	 * <p>[dict_id=1202052,name=Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=50]
	</p>	 */
	@Column(name = "title_frame_e")
	private Integer titleFrame;

	/* The <em>totalGrayForce</em> property.
	 * <p>[dict_id=1202058,name=Total Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=56]
	</p>	 */
	@Column(name = "total_gray_force_n")
	private Integer totalGrayForce;

	/* The <em>graphVerticalGrid</em> property.
	 * <p>[dict_id=1202069,name=Graph Vertical Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_vert_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=67]
	</p>	 */
	@Column(name = "graph_vert_grid_f")
	private Integer graphVerticalGrid;

//	/* The <em>graphTitle</em> property.
//	 * <p>[dict_id=1202014,name=Graph Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "graph_title_msg_id")
//	private MessageEntity graphTitle;

	/* The <em>nature</em> property.
	 * <p>[dict_id=1202022,name=Nature,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=nature_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=9]
	</p>	 */
	@Column(name = "nature_e")
	private Integer nature;

	/* The <em>besideColumn</em> property.
	 * <p>[dict_id=1202038,name=Beside Column,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=beside_col_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=36]
	</p>	 */
	@Column(name = "beside_col_n")
	private Integer besideColumn;

	/* The <em>graphView</em> property.
	 * <p>[dict_id=1202027,name=Graph View,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_view_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>	 */
	@Column(name = "graph_view_e")
	private Integer graphView;

	/* The <em>afterLines</em> property.
	 * <p>[dict_id=1202039,name=After Lines,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=after_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=37]
	</p>	 */
	@Column(name = "after_lines_n")
	private Integer afterLines;

	/* The <em>totalAfterLines</em> property.
	 * <p>[dict_id=1202060,name=Total After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=33,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=58]
	</p>	 */
	@Column(name = "total_aft_lines_n")
	private Integer totalAfterLines;

//	/* The <em>rowFormat</em> property.
//	 * <p>[dict_id=1202078,name=Row Format,datatype=id_t,dictEntity=Format,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=row_fmt_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=75]
//	</p>	 */
//	@OneToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "row_fmt_definition")
//	private java.util.Set<ScriptDefinitionEntity> rowFormat;

	/* The <em>matrixVertLineThickness</em> property.
	 * <p>[dict_id=1202061,name=Matrix Vert. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_vert_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=59]
	</p>	 */
	@Column(name = "matrix_vert_thick_n")
	private Integer matrixVertLineThickness;

	/* The <em>titleAfterLines</em> property.
	 * <p>[dict_id=1202051,name=Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=49]
	</p>	 */
	@Column(name = "title_aft_lines_n")
	private Integer titleAfterLines;

//	/* The <em>title</em> property.
//	 * <p>[dict_id=1202008,name=Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "title_msg_id")
//	private MessageEntity title;

//	/* The <em>parentFormat</em> property.
//	 * <p>[dict_id=1202006,name=Parent Format,datatype=id_t,dictEntity=Format,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=parent_format_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "parent_format_id")
//	private DenominableWithNameAndNotepads parentFormat;

	/* The <em>totalThickness</em> property.
	 * <p>[dict_id=1202057,name=Total Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=55]
	</p>	 */
	@Column(name = "total_thick_n")
	private Integer totalThickness;

	/* The <em>titleGrayForce</em> property.
	 * <p>[dict_id=1202050,name=Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=48]
	</p>	 */
	@Column(name = "title_gray_force_n")
	private Integer titleGrayForce;

	/* The <em>titleFontSize</em> property.
	 * <p>[dict_id=1202049,name=Title Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=47]
	</p>	 */
	@Column(name = "title_font_size_n")
	private Integer titleFontSize;

	/* The <em>totalFontSize</em> property.
	 * <p>[dict_id=1202055,name=Total Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=53]
	</p>	 */
	@Column(name = "total_font_size_n")
	private Integer totalFontSize;

	/* The <em>graphType</em> property.
	 * <p>[dict_id=1202026,name=Graph Type,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_type_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>	 */
	@Column(name = "graph_type_e")
	private Integer graphType;

	/* The <em>grayForce</em> property.
	 * <p>[dict_id=1202035,name=Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=33]
	</p>	 */
	@Column(name = "gray_force_n")
	private Integer grayForce;

	/* The <em>columnTitleBeforeLines</em> property.
	 * <p>[dict_id=1202043,name=Column Title Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=43]
	</p>	 */
	@Column(name = "header_bef_lines_n")
	private Integer columnTitleBeforeLines;

	/* The <em>iconName</em> property.
	 * <p>[dict_id=1202031,name=Icon Name,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=icon_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>	 */
	@Column(name = "icon_name")
	private String iconName;

	/* The <em>blankLines</em> property.
	 * <p>[dict_id=1202037,name=Blank Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=blank_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=35]
	</p>	 */
	@Column(name = "blank_lines_n")
	private Integer blankLines;

	/* The <em>totalFrame</em> property.
	 * <p>[dict_id=1202056,name=Total Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=54]
	</p>	 */
	@Column(name = "total_frame_e")
	private Integer totalFrame;

	/* The <em>filter</em> property.
	 * <p>[dict_id=1202025,name=Filter,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=filter_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
	</p>	 */
	@Column(name = "filter_c")
	private String filter;

	/* The <em>graph3D</em> property.
	 * <p>[dict_id=1202065,name=Graph 3D,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_3d_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=63]
	</p>	 */
	@Column(name = "graph_3d_f")
	private Integer graph3D;

	/* The <em>columnTitleFrame</em> property.
	 * <p>[dict_id=1202040,name=Column Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=40]
	</p>	 */
	@Column(name = "header_frame_e")
	private Integer columnTitleFrame;

//	/* The <em>matrixGroupColumnTitle</em> property.
//	 * <p>[dict_id=1202013,name=Matrix Group Column Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_grp_col_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "matrix_grp_col_msg_id")
//	private MessageEntity matrixGroupColumnTitle;

	/* The <em>maxBreakNumber</em> property.
	 * <p>[dict_id=1202032,name=Max. Break Number,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=max_break_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=113,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=20]
	</p>	 */
	@Column(name = "max_break_n")
	private Integer maxBreakNumber;

	/* The <em>breakValue</em> property.
	 * <p>[dict_id=1202024,name=Break Value,datatype=longname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_value_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>	 */
	@Column(name = "break_value_c")
	private String breakValue;

//	/* The <em>report</em> property.
//	 * <p>[dict_id=1202017,name=Report,datatype=id_t,dictEntity=Format,referencedDictEntity=Report,parentAttribute=<null>,sqlname_c=report_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=11]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "report_id")
//	private ReportEntity report;

//	/* The <em>nextPageTitle</em> property.
//	 * <p>[dict_id=1202009,name=Next Page Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=next_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "next_title_msg_id")
//	private MessageEntity nextPageTitle;

	/* The <em>titleFont</em> property.
	 * <p>[dict_id=1202048,name=Title Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=title_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=46]
	</p>	 */
	@Column(name = "title_font_e")
	private Integer titleFont;

	/* The <em>graphLegendPosition</em> property.
	 * <p>[dict_id=1202067,name=Graph Legend Position,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_legend_pos_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=65]
	</p>	 */
	@Column(name = "graph_legend_pos_e")
	private Integer graphLegendPosition;

	/* The <em>columnTitleThickness</em> property.
	 * <p>[dict_id=1202042,name=Column Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=42]
	</p>	 */
	@Column(name = "header_thick_n")
	private Integer columnTitleThickness;

	/* The <em>code</em> property.
	 * <p>[dict_id=1202002,name=Code,datatype=code_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=code,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
	</p>	 */
    @Basic(optional = false)
    @Column(name = "code", nullable=false)
	private String code;

	/* The <em>formatElement</em> property.
	 * <p>[dict_id=1202075,name=Format Element,datatype=id_t,dictEntity=Format,referencedDictEntity=Format Element,parentAttribute=<null>,sqlname_c=format_element,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=17]
	</p>	 */
	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@ElementJoinColumn(name="format_id", referencedColumnName="id")
	private java.util.Set<FormatElementEntity> formatElements;
	
    /**
     * Returns the <em>formatElement</em> property.
     * <p>[dict_id=1202075,name=Format Element,datatype=id_t,dictEntity=Format,referencedDictEntity=Format Element,parentAttribute=<null>,sqlname_c=format_element,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=17]</p>
     * @return the <em>formatElement</em> property.
     */
    public java.util.Set<FormatElementEntity> getFormatElements() {
        return this.formatElements;
    }

	/* The <em>matrixHorzLineThickness</em> property.
	 * <p>[dict_id=1202062,name=Matrix Horz. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_horz_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=60]
	</p>	 */
	@Column(name = "matrix_horz_thick_n")
	private Integer matrixHorzLineThickness;

//	/* The <em>graphSubTitle</em> property.
//	 * <p>[dict_id=1202015,name=Graph Sub-Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_subtitle_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "graph_subtitle_msg_id")
//	private MessageEntity graphSubTitle;

	@ManyToOne(cascade=PERSIST, fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="entity_dict_id", updatable=false, nullable=false)
	private DictEntity dictEntity;
	
    /* The <em>type</em> property.
     * <p>[dict_id=1202018,name=Type,datatype=id_t,dictEntity=Format,referencedDictEntity=Type,parentAttribute=<null>,sqlname_c=type_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]</p>  
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "type_id", referencedColumnName = "id"),
        @JoinColumn(name = "type_vw.attribute_dict_id", referencedColumnName = TypeEntity.FORMAT_TYPE_DICTID_STRING)
    })
    private TypeEntity type;

//	/* The <em>notepad</em> property.
//	 * <p>[dict_id=1202074,name=Notepad,datatype=id_t,dictEntity=Format,referencedDictEntity=Notepad,parentAttribute=<null>,sqlname_c=notepad,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=19]
//	</p>	 */
//	@OneToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "notepad")
//	private java.util.Set<NotepadEntity> notepad;

	/* The <em>graphExplode</em> property.
	 * <p>[dict_id=1202072,name=Graph Explode,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_explode_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=70]
	</p>	 */
	@Column(name = "graph_explode_e")
	private Integer graphExplode;

	/* The <em>graphDisplayPercentage</em> property.
	 * <p>[dict_id=1202070,name=Graph Display Percentage,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_perc_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=68]
	</p>	 */
	@Column(name = "graph_disp_perc_f")
	private Integer graphDisplayPercentage;

	/* The <em>totalBeforeLines</em> property.
	 * <p>[dict_id=1202059,name=Total Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=57]
	</p>	 */
	@Column(name = "total_bef_lines_n")
	private Integer totalBeforeLines;

//	/* The <em>matrixTitle</em> property.
//	 * <p>[dict_id=1202012,name=Matrix Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "matrix_title_msg_id")
//	private MessageEntity matrixTitle;
//
//	/* The <em>graphLegendTitle</em> property.
//	 * <p>[dict_id=1202016,name=Graph Legend Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_legend_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=16]
//	</p>	 */
//	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@Column(name = "graph_legend_msg_id")
//	private MessageEntity graphLegendTitle;

	/* The <em>titleJustification</em> property.
	 * <p>[dict_id=1202047,name=Title Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=45]
	</p>	 */
	@Column(name = "title_justif_e")
	private Integer titleJustification;

	/* The <em>denom</em> property.
	 * <p>[dict_id=1202004,name=Denom,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]
	</p>	 */
	@Column(name = "denom")
	private String denom;

	/* The <em>blockFont</em> property.
	 * <p>[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38]
	</p>	 */
	@Column(name = "header_font_e")
	private Integer blockFont;

	/* The <em>borderThickness</em> property.
	 * <p>[dict_id=1202034,name=Border Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=border_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=32]
	</p>	 */
	@Column(name = "border_thick_n")
	private Integer borderThickness;

	/* The <em>breakCriteria</em> property.
	 * <p>[dict_id=1202023,name=Break Criteria,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_criteria_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>	 */
	@Column(name = "break_criteria_name")
	private String breakCriteria;

	/* The <em>function</em> property.
	 * <p>[dict_id=1202005,name=Function,datatype=dict_t,dictEntity=Format,referencedDictEntity=Function,parentAttribute=<null>,sqlname_c=function_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=5]
	</p>	 */
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name = "function_dict_id", nullable=false, updatable=false)
	private DictFunctionEntity function;

	/* The <em>graphPattern</em> property.
	 * <p>[dict_id=1202066,name=Graph Pattern,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_pattern_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=64]
	</p>	 */
	@Column(name = "graph_pattern_e")
	private Integer graphPattern;

//	/* The <em>control</em> property.
//	 * <p>[dict_id=1202077,name=Control,datatype=id_t,dictEntity=Format,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=script_control,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
//	</p>	 */
//	@OneToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "script_control")
//	private java.util.Set<ScriptDefinitionEntity> control;

	/* The <em>level</em> property.
	 * <p>[dict_id=1202030,name=Level,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=level_num_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1117,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>	 */
	@Column(name = "level_num_n")
	private Integer level;

	/* The <em>titleThickness</em> property.
	 * <p>[dict_id=1202053,name=Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=51]
	</p>	 */
	@Column(name = "title_thick_n")
	private Integer titleThickness;

	/* The <em>justification</em> property.
	 * <p>[dict_id=1202033,name=Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3821,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=31]
	</p>	 */
	@Column(name = "justif_e")
	private Integer justification;

	/* The <em>graphDisplayValue</em> property.
	 * <p>[dict_id=1202071,name=Graph Display Value,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_val_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=69]
	</p>	 */
	@Column(name = "graph_disp_val_f")
	private Integer graphDisplayValue;

	/* The <em>totalFont</em> property.
	 * <p>[dict_id=1202054,name=Total Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=total_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3197,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=52]
	</p>	 */
	@Column(name = "total_font_e")
	private Integer totalFont;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ElementJoinColumns( { 
        @ElementJoinColumn(name = "object_id", referencedColumnName = "id"),
        @ElementJoinColumn(name = "entity_dict_id", referencedColumnName = "1202") })
    private Set<DenominationEntity> denoms;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ElementJoinColumns( { 
        @ElementJoinColumn(name = "object_id", referencedColumnName = "id"),
        @ElementJoinColumn(name = "entity_dict_id", referencedColumnName = "1202") })
    private List<NotepadEntity> notepads;


	//-------------------------------------------------------------------------
	// Constructor

	
    /**
	 * @generated
	 */
	public FormatEntity() {
		super();
		try {

			this.riskView = 0;

//			this.denomination = new java.util.HashSet<DenominationEntity>();

			this.graphHorizontalGrid = 0;

			this.titleFrame = 0;

			this.graphVerticalGrid = 0;

			this.nature = 1;

			this.graphView = 0;

//			this.rowFormat = new java.util.HashSet<ScriptDefinitionEntity>();

			this.graphType = 0;

			this.totalFrame = 0;

			this.graph3D = 0;

			this.columnTitleFrame = 0;

			this.titleFont = 0;

			this.graphLegendPosition = 0;

			this.formatElements = new java.util.HashSet<FormatElementEntity>();

//			this.notepad = new java.util.HashSet<NotepadEntity>();

			this.graphExplode = 0;

			this.graphDisplayPercentage = 0;

			this.titleJustification = 0;

			this.blockFont = 0;

			this.graphPattern = 0;

//			this.control = new java.util.HashSet<ScriptDefinitionEntity>();

			this.justification = 0;

			this.graphDisplayValue = 0;

			this.totalFont = 0;

		} catch (Exception e) {
			throw (IllegalArgumentException) new IllegalArgumentException(e
					.getMessage()).initCause(e);
		}
	}

	//-------------------------------------------------------------------------
	// Accessors

	/**
	 * Returns the <em>identifier</em> property.
	 * <p>[dict_id=1202001,name=Identifier,datatype=id_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
	</p>
	 * @return the <em>identifier</em> property.

	 * @primaryKey     */
	public long getID() {
		return this.id;
	}

	/**
	 * Sets the <em>identifier</em> property.
	 * <p>[dict_id=1202001,name=Identifier,datatype=id_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
	</p>
	 * @param identifier the new value of the <em>identifier</em> property.

	 * @primaryKey	 */
	public void setID(long identifier) {
		this.id = identifier;
	}

	/**
	 * Returns the <em>blockFontSize</em> property.
	 * <p>[dict_id=1202046,name=Block Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=39]
	</p>
	 * @return the <em>blockFontSize</em> property.

	 */
	public Integer getBlockFontSize() {
		return this.blockFontSize;
	}

	/**
	 * Sets the <em>blockFontSize</em> property.
	 * <p>[dict_id=1202046,name=Block Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=39]
	</p>
	 * @param blockFontSize the new value of the <em>blockFontSize</em> property.

	 */
	public void setBlockFontSize(Integer blockFontSize) {
		this.blockFontSize = blockFontSize;
	}

	/**
	 * Returns the <em>riskView</em> property.
	 * <p>[dict_id=1202021,name=Risk View,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=risk_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3837,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=8]
	</p>
	 * @return the <em>riskView</em> property.

	 */
	public Integer getRiskView() {
		return this.riskView;
	}

	/**
	 * Sets the <em>riskView</em> property.
	 * <p>[dict_id=1202021,name=Risk View,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=risk_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3837,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=8]
	</p>
	 * @param riskView the new value of the <em>riskView</em> property.

	 */
	public void setRiskView(Integer riskView) {
		this.riskView = riskView;
	}

//	/**
//	 * Returns the <em>totalTitle</em> property.
//	 * <p>[dict_id=1202011,name=Total Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=total_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>totalTitle</em> property.
//
//	 */
//	public MessageEntity getTotalTitle() {
//		return this.totalTitle;
//	}
//
//	/**
//	 * Sets the <em>totalTitle</em> property.
//	 * <p>[dict_id=1202011,name=Total Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=total_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param totalTitle the new value of the <em>totalTitle</em> property.
//
//	 */
//	public void setTotalTitle(
//			MessageEntity totalTitle) {
//		this.totalTitle = totalTitle;
//	}

	/**
	 * Returns the <em>graphHeight</em> property.
	 * <p>[dict_id=1202064,name=Graph Height,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_height_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=62]
	</p>
	 * @return the <em>graphHeight</em> property.

	 */
	public Integer getGraphHeight() {
		return this.graphHeight;
	}

	/**
	 * Sets the <em>graphHeight</em> property.
	 * <p>[dict_id=1202064,name=Graph Height,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_height_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=62]
	</p>
	 * @param graphHeight the new value of the <em>graphHeight</em> property.

	 */
	public void setGraphHeight(Integer graphHeight) {
		this.graphHeight = graphHeight;
	}

	/**
	 * Returns the <em>grayLines</em> property.
	 * <p>[dict_id=1202036,name=Gray Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=34]
	</p>
	 * @return the <em>grayLines</em> property.

	 */
	public Integer getGrayLines() {
		return this.grayLines;
	}

	/**
	 * Sets the <em>grayLines</em> property.
	 * <p>[dict_id=1202036,name=Gray Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=34]
	</p>
	 * @param grayLines the new value of the <em>grayLines</em> property.

	 */
	public void setGrayLines(Integer grayLines) {
		this.grayLines = grayLines;
	}

	/**
	 * Returns the <em>overlap</em> property.
	 * <p>[dict_id=1202028,name=Overlap,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=overlap_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=13]
	</p>
	 * @return the <em>overlap</em> property.

	 */
	public Integer getOverlap() {
		return this.overlap;
	}

	/**
	 * Sets the <em>overlap</em> property.
	 * <p>[dict_id=1202028,name=Overlap,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=overlap_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=13]
	</p>
	 * @param overlap the new value of the <em>overlap</em> property.

	 */
	public void setOverlap(Integer overlap) {
		this.overlap = overlap;
	}

//	/**
//	 * Returns the <em>tableContentTitle</em> property.
//	 * <p>[dict_id=1202010,name=Table Content Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=toc_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>tableContentTitle</em> property.
//
//	 */
//	public MessageEntity getTableContentTitle() {
//		return this.tableContentTitle;
//	}
//
//	/**
//	 * Sets the <em>tableContentTitle</em> property.
//	 * <p>[dict_id=1202010,name=Table Content Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=toc_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param tableContentTitle the new value of the <em>tableContentTitle</em> property.
//
//	 */
//	public void setTableContentTitle(
//			MessageEntity tableContentTitle) {
//		this.tableContentTitle = tableContentTitle;
//	}

	/**
	 * Returns the <em>graphWidth</em> property.
	 * <p>[dict_id=1202063,name=Graph Width,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=61]
	</p>
	 * @return the <em>graphWidth</em> property.

	 */
	public Integer getGraphWidth() {
		return this.graphWidth;
	}

	/**
	 * Sets the <em>graphWidth</em> property.
	 * <p>[dict_id=1202063,name=Graph Width,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=61]
	</p>
	 * @param graphWidth the new value of the <em>graphWidth</em> property.

	 */
	public void setGraphWidth(Integer graphWidth) {
		this.graphWidth = graphWidth;
	}

	/**
	 * Returns the <em>columnTitleGrayForce</em> property.
	 * <p>[dict_id=1202041,name=Column Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=41]
	</p>
	 * @return the <em>columnTitleGrayForce</em> property.

	 */
	public Integer getColumnTitleGrayForce() {
		return this.columnTitleGrayForce;
	}

	/**
	 * Sets the <em>columnTitleGrayForce</em> property.
	 * <p>[dict_id=1202041,name=Column Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=41]
	</p>
	 * @param columnTitleGrayForce the new value of the <em>columnTitleGrayForce</em> property.

	 */
	public void setColumnTitleGrayForce(Integer columnTitleGrayForce) {
		this.columnTitleGrayForce = columnTitleGrayForce;
	}

//	/**
//	 * Returns the <em>denomination</em> property.
//	 * <p>[dict_id=1202076,name=Denomination,datatype=id_t,dictEntity=Format,referencedDictEntity=Denomination,parentAttribute=<null>,sqlname_c=denomination,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=18]
//	</p>
//	 * @return the <em>denomination</em> property.
//
//	 */
//	public java.util.Set<DenominationEntity> getDenomination() {
//		return this.denomination;
//	}

	/* (non-Javadoc)
	 * @see com.odcgroup.tangij.domainmodel.DenominableWithNameAndNotepads#getName()
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the <em>name</em> property.
	 * <p>[dict_id=1202003,name=Name,datatype=name_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]
	</p>
	 * @param name the new value of the <em>name</em> property.

	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the <em>columnTitleAfterLines</em> property.
	 * <p>[dict_id=1202044,name=Column Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=44]
	</p>
	 * @return the <em>columnTitleAfterLines</em> property.

	 */
	public Integer getColumnTitleAfterLines() {
		return this.columnTitleAfterLines;
	}

	/**
	 * Sets the <em>columnTitleAfterLines</em> property.
	 * <p>[dict_id=1202044,name=Column Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=44]
	</p>
	 * @param columnTitleAfterLines the new value of the <em>columnTitleAfterLines</em> property.

	 */
	public void setColumnTitleAfterLines(Integer columnTitleAfterLines) {
		this.columnTitleAfterLines = columnTitleAfterLines;
	}

	/**
	 * Returns the <em>graphHorizontalGrid</em> property.
	 * <p>[dict_id=1202068,name=Graph Horizontal Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_horz_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=66]
	</p>
	 * @return the <em>graphHorizontalGrid</em> property.

	 */
	public Integer getGraphHorizontalGrid() {
		return this.graphHorizontalGrid;
	}

	/**
	 * Sets the <em>graphHorizontalGrid</em> property.
	 * <p>[dict_id=1202068,name=Graph Horizontal Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_horz_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=66]
	</p>
	 * @param graphHorizontalGrid the new value of the <em>graphHorizontalGrid</em> property.

	 */
	public void setGraphHorizontalGrid(Integer graphHorizontalGrid) {
		this.graphHorizontalGrid = graphHorizontalGrid;
	}

	/**
	 * Returns the <em>modificationDate</em> property.
	 * <p>[dict_id=1202029,name=Modification Date,datatype=datetime_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=modif_d,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=13]
	</p>
	 * @return the <em>modificationDate</em> property.

	 */
	public java.util.Date getModificationDate() {
		return (modificationDate == null) ? null : new Date(this.modificationDate.getTime());
	}

	/**
	 * Sets the <em>modificationDate</em> property.
	 * <p>[dict_id=1202029,name=Modification Date,datatype=datetime_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=modif_d,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=13]
	</p>
	 * @param modificationDate the new value of the <em>modificationDate</em> property.

	 */
	public void setModificationDate(java.util.Date modificationDate) {
		this.modificationDate = (modificationDate == null) ? null : new Date(modificationDate.getTime());
	}

	/**
	 * Returns the <em>titleFrame</em> property.
	 * <p>[dict_id=1202052,name=Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=50]
	</p>
	 * @return the <em>titleFrame</em> property.

	 */
	public Integer getTitleFrame() {
		return this.titleFrame;
	}

	/**
	 * Sets the <em>titleFrame</em> property.
	 * <p>[dict_id=1202052,name=Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=50]
	</p>
	 * @param titleFrame the new value of the <em>titleFrame</em> property.

	 */
	public void setTitleFrame(Integer titleFrame) {
		this.titleFrame = titleFrame;
	}

	/**
	 * Returns the <em>totalGrayForce</em> property.
	 * <p>[dict_id=1202058,name=Total Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=56]
	</p>
	 * @return the <em>totalGrayForce</em> property.

	 */
	public Integer getTotalGrayForce() {
		return this.totalGrayForce;
	}

	/**
	 * Sets the <em>totalGrayForce</em> property.
	 * <p>[dict_id=1202058,name=Total Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=56]
	</p>
	 * @param totalGrayForce the new value of the <em>totalGrayForce</em> property.

	 */
	public void setTotalGrayForce(Integer totalGrayForce) {
		this.totalGrayForce = totalGrayForce;
	}

	/**
	 * Returns the <em>graphVerticalGrid</em> property.
	 * <p>[dict_id=1202069,name=Graph Vertical Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_vert_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=67]
	</p>
	 * @return the <em>graphVerticalGrid</em> property.

	 */
	public Integer getGraphVerticalGrid() {
		return this.graphVerticalGrid;
	}

	/**
	 * Sets the <em>graphVerticalGrid</em> property.
	 * <p>[dict_id=1202069,name=Graph Vertical Grid,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_vert_grid_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=67]
	</p>
	 * @param graphVerticalGrid the new value of the <em>graphVerticalGrid</em> property.

	 */
	public void setGraphVerticalGrid(Integer graphVerticalGrid) {
		this.graphVerticalGrid = graphVerticalGrid;
	}

//	/**
//	 * Returns the <em>graphTitle</em> property.
//	 * <p>[dict_id=1202014,name=Graph Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>graphTitle</em> property.
//
//	 */
//	public MessageEntity getGraphTitle() {
//		return this.graphTitle;
//	}
//
//	/**
//	 * Sets the <em>graphTitle</em> property.
//	 * <p>[dict_id=1202014,name=Graph Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param graphTitle the new value of the <em>graphTitle</em> property.
//
//	 */
//	public void setGraphTitle(
//			MessageEntity graphTitle) {
//		this.graphTitle = graphTitle;
//	}

	/**
	 * Returns the <em>nature</em> property.
	 * <p>[dict_id=1202022,name=Nature,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=nature_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=9]
	</p>
	 * @return the <em>nature</em> property.

	 */
	public Integer getNature() {
		return this.nature;
	}

	/**
	 * Sets the <em>nature</em> property.
	 * <p>[dict_id=1202022,name=Nature,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=nature_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=9]
	</p>
	 * @param nature the new value of the <em>nature</em> property.

	 */
	public void setNature(Integer nature) {
		this.nature = nature;
	}

	/**
	 * Returns the <em>besideColumn</em> property.
	 * <p>[dict_id=1202038,name=Beside Column,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=beside_col_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=36]
	</p>
	 * @return the <em>besideColumn</em> property.

	 */
	public Integer getBesideColumn() {
		return this.besideColumn;
	}

	/**
	 * Sets the <em>besideColumn</em> property.
	 * <p>[dict_id=1202038,name=Beside Column,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=beside_col_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=36]
	</p>
	 * @param besideColumn the new value of the <em>besideColumn</em> property.

	 */
	public void setBesideColumn(Integer besideColumn) {
		this.besideColumn = besideColumn;
	}

	/**
	 * Returns the <em>graphView</em> property.
	 * <p>[dict_id=1202027,name=Graph View,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_view_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>
	 * @return the <em>graphView</em> property.

	 */
	public Integer getGraphView() {
		return this.graphView;
	}

	/**
	 * Sets the <em>graphView</em> property.
	 * <p>[dict_id=1202027,name=Graph View,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_view_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>
	 * @param graphView the new value of the <em>graphView</em> property.

	 */
	public void setGraphView(Integer graphView) {
		this.graphView = graphView;
	}

	/**
	 * Returns the <em>afterLines</em> property.
	 * <p>[dict_id=1202039,name=After Lines,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=after_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=37]
	</p>
	 * @return the <em>afterLines</em> property.

	 */
	public Integer getAfterLines() {
		return this.afterLines;
	}

	/**
	 * Sets the <em>afterLines</em> property.
	 * <p>[dict_id=1202039,name=After Lines,datatype=smallint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=after_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=37]
	</p>
	 * @param afterLines the new value of the <em>afterLines</em> property.

	 */
	public void setAfterLines(Integer afterLines) {
		this.afterLines = afterLines;
	}

	/**
	 * Returns the <em>totalAfterLines</em> property.
	 * <p>[dict_id=1202060,name=Total After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=33,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=58]
	</p>
	 * @return the <em>totalAfterLines</em> property.

	 */
	public Integer getTotalAfterLines() {
		return this.totalAfterLines;
	}

	/**
	 * Sets the <em>totalAfterLines</em> property.
	 * <p>[dict_id=1202060,name=Total After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=33,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=58]
	</p>
	 * @param totalAfterLines the new value of the <em>totalAfterLines</em> property.

	 */
	public void setTotalAfterLines(Integer totalAfterLines) {
		this.totalAfterLines = totalAfterLines;
	}

//	/**
//	 * Returns the <em>rowFormat</em> property.
//	 * <p>[dict_id=1202078,name=Row Format,datatype=id_t,dictEntity=Format,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=row_fmt_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=75]
//	</p>
//	 * @return the <em>rowFormat</em> property.
//
//	 */
//	public java.util.Set<ScriptDefinitionEntity> getRowFormat() {
//		return this.rowFormat;
//	}

	/**
	 * Returns the <em>matrixVertLineThickness</em> property.
	 * <p>[dict_id=1202061,name=Matrix Vert. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_vert_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=59]
	</p>
	 * @return the <em>matrixVertLineThickness</em> property.

	 */
	public Integer getMatrixVertLineThickness() {
		return this.matrixVertLineThickness;
	}

	/**
	 * Sets the <em>matrixVertLineThickness</em> property.
	 * <p>[dict_id=1202061,name=Matrix Vert. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_vert_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=59]
	</p>
	 * @param matrixVertLineThickness the new value of the <em>matrixVertLineThickness</em> property.

	 */
	public void setMatrixVertLineThickness(Integer matrixVertLineThickness) {
		this.matrixVertLineThickness = matrixVertLineThickness;
	}

	/**
	 * Returns the <em>titleAfterLines</em> property.
	 * <p>[dict_id=1202051,name=Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=49]
	</p>
	 * @return the <em>titleAfterLines</em> property.

	 */
	public Integer getTitleAfterLines() {
		return this.titleAfterLines;
	}

	/**
	 * Sets the <em>titleAfterLines</em> property.
	 * <p>[dict_id=1202051,name=Title After Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=49]
	</p>
	 * @param titleAfterLines the new value of the <em>titleAfterLines</em> property.

	 */
	public void setTitleAfterLines(Integer titleAfterLines) {
		this.titleAfterLines = titleAfterLines;
	}

//	/**
//	 * Returns the <em>title</em> property.
//	 * <p>[dict_id=1202008,name=Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>title</em> property.
//
//	 */
//	public MessageEntity getTitle() {
//		return this.title;
//	}
//
//	/**
//	 * Sets the <em>title</em> property.
//	 * <p>[dict_id=1202008,name=Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param title the new value of the <em>title</em> property.
//
//	 */
//	public void setTitle(MessageEntity title) {
//		this.title = title;
//	}
//
//	/**
//	 * Returns the <em>parentFormat</em> property.
//	 * <p>[dict_id=1202006,name=Parent Format,datatype=id_t,dictEntity=Format,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=parent_format_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
//	</p>
//	 * @return the <em>parentFormat</em> property.
//
//	 */
//	public FormatEntity getParentFormat() {
//		return this.parentFormat;
//	}

//	/**
//	 * Sets the <em>parentFormat</em> property.
//	 * <p>[dict_id=1202006,name=Parent Format,datatype=id_t,dictEntity=Format,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=parent_format_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
//	</p>
//	 * @param parentFormat the new value of the <em>parentFormat</em> property.
//
//	 */
//	public void setParentFormat(
//			DenominableWithNameAndNotepads parentFormat) {
//		this.parentFormat = parentFormat;
//	}

	/**
	 * Returns the <em>totalThickness</em> property.
	 * <p>[dict_id=1202057,name=Total Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=55]
	</p>
	 * @return the <em>totalThickness</em> property.

	 */
	public Integer getTotalThickness() {
		return this.totalThickness;
	}

	/**
	 * Sets the <em>totalThickness</em> property.
	 * <p>[dict_id=1202057,name=Total Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=55]
	</p>
	 * @param totalThickness the new value of the <em>totalThickness</em> property.

	 */
	public void setTotalThickness(Integer totalThickness) {
		this.totalThickness = totalThickness;
	}

	/**
	 * Returns the <em>titleGrayForce</em> property.
	 * <p>[dict_id=1202050,name=Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=48]
	</p>
	 * @return the <em>titleGrayForce</em> property.

	 */
	public Integer getTitleGrayForce() {
		return this.titleGrayForce;
	}

	/**
	 * Sets the <em>titleGrayForce</em> property.
	 * <p>[dict_id=1202050,name=Title Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=48]
	</p>
	 * @param titleGrayForce the new value of the <em>titleGrayForce</em> property.

	 */
	public void setTitleGrayForce(Integer titleGrayForce) {
		this.titleGrayForce = titleGrayForce;
	}

	/**
	 * Returns the <em>titleFontSize</em> property.
	 * <p>[dict_id=1202049,name=Title Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=47]
	</p>
	 * @return the <em>titleFontSize</em> property.

	 */
	public Integer getTitleFontSize() {
		return this.titleFontSize;
	}

	/**
	 * Sets the <em>titleFontSize</em> property.
	 * <p>[dict_id=1202049,name=Title Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=47]
	</p>
	 * @param titleFontSize the new value of the <em>titleFontSize</em> property.

	 */
	public void setTitleFontSize(Integer titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * Returns the <em>totalFontSize</em> property.
	 * <p>[dict_id=1202055,name=Total Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=53]
	</p>
	 * @return the <em>totalFontSize</em> property.

	 */
	public Integer getTotalFontSize() {
		return this.totalFontSize;
	}

	/**
	 * Sets the <em>totalFontSize</em> property.
	 * <p>[dict_id=1202055,name=Total Font Size,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=53]
	</p>
	 * @param totalFontSize the new value of the <em>totalFontSize</em> property.

	 */
	public void setTotalFontSize(Integer totalFontSize) {
		this.totalFontSize = totalFontSize;
	}

	/**
	 * Returns the <em>graphType</em> property.
	 * <p>[dict_id=1202026,name=Graph Type,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_type_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>
	 * @return the <em>graphType</em> property.

	 */
	public Integer getGraphType() {
		return this.graphType;
	}

	/**
	 * Sets the <em>graphType</em> property.
	 * <p>[dict_id=1202026,name=Graph Type,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_type_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=14]
	</p>
	 * @param graphType the new value of the <em>graphType</em> property.

	 */
	public void setGraphType(Integer graphType) {
		this.graphType = graphType;
	}

	/**
	 * Returns the <em>grayForce</em> property.
	 * <p>[dict_id=1202035,name=Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=33]
	</p>
	 * @return the <em>grayForce</em> property.

	 */
	public Integer getGrayForce() {
		return this.grayForce;
	}

	/**
	 * Sets the <em>grayForce</em> property.
	 * <p>[dict_id=1202035,name=Gray Force,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=33]
	</p>
	 * @param grayForce the new value of the <em>grayForce</em> property.

	 */
	public void setGrayForce(Integer grayForce) {
		this.grayForce = grayForce;
	}

	/**
	 * Returns the <em>columnTitleBeforeLines</em> property.
	 * <p>[dict_id=1202043,name=Column Title Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=43]
	</p>
	 * @return the <em>columnTitleBeforeLines</em> property.

	 */
	public Integer getColumnTitleBeforeLines() {
		return this.columnTitleBeforeLines;
	}

	/**
	 * Sets the <em>columnTitleBeforeLines</em> property.
	 * <p>[dict_id=1202043,name=Column Title Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=43]
	</p>
	 * @param columnTitleBeforeLines the new value of the <em>columnTitleBeforeLines</em> property.

	 */
	public void setColumnTitleBeforeLines(Integer columnTitleBeforeLines) {
		this.columnTitleBeforeLines = columnTitleBeforeLines;
	}

	/**
	 * Returns the <em>iconName</em> property.
	 * <p>[dict_id=1202031,name=Icon Name,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=icon_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>
	 * @return the <em>iconName</em> property.

	 */
	public String getIconName() {
		return this.iconName;
	}

	/**
	 * Sets the <em>iconName</em> property.
	 * <p>[dict_id=1202031,name=Icon Name,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=icon_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>
	 * @param iconName the new value of the <em>iconName</em> property.

	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * Returns the <em>blankLines</em> property.
	 * <p>[dict_id=1202037,name=Blank Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=blank_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=35]
	</p>
	 * @return the <em>blankLines</em> property.

	 */
	public Integer getBlankLines() {
		return this.blankLines;
	}

	/**
	 * Sets the <em>blankLines</em> property.
	 * <p>[dict_id=1202037,name=Blank Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=blank_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=35]
	</p>
	 * @param blankLines the new value of the <em>blankLines</em> property.

	 */
	public void setBlankLines(Integer blankLines) {
		this.blankLines = blankLines;
	}

	/**
	 * Returns the <em>totalFrame</em> property.
	 * <p>[dict_id=1202056,name=Total Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=54]
	</p>
	 * @return the <em>totalFrame</em> property.

	 */
	public Integer getTotalFrame() {
		return this.totalFrame;
	}

	/**
	 * Sets the <em>totalFrame</em> property.
	 * <p>[dict_id=1202056,name=Total Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=54]
	</p>
	 * @param totalFrame the new value of the <em>totalFrame</em> property.

	 */
	public void setTotalFrame(Integer totalFrame) {
		this.totalFrame = totalFrame;
	}

	/**
	 * Returns the <em>filter</em> property.
	 * <p>[dict_id=1202025,name=Filter,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=filter_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
	</p>
	 * @return the <em>filter</em> property.

	 */
	public String getFilter() {
		return this.filter;
	}

	/**
	 * Sets the <em>filter</em> property.
	 * <p>[dict_id=1202025,name=Filter,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=filter_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4093,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
	</p>
	 * @param filter the new value of the <em>filter</em> property.

	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Returns the <em>graph3D</em> property.
	 * <p>[dict_id=1202065,name=Graph 3D,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_3d_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=63]
	</p>
	 * @return the <em>graph3D</em> property.

	 */
	public Integer getGraph3D() {
		return this.graph3D;
	}

	/**
	 * Sets the <em>graph3D</em> property.
	 * <p>[dict_id=1202065,name=Graph 3D,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_3d_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=63]
	</p>
	 * @param graph3D the new value of the <em>graph3D</em> property.

	 */
	public void setGraph3D(Integer graph3D) {
		this.graph3D = graph3D;
	}

	/**
	 * Returns the <em>columnTitleFrame</em> property.
	 * <p>[dict_id=1202040,name=Column Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=40]
	</p>
	 * @return the <em>columnTitleFrame</em> property.

	 */
	public Integer getColumnTitleFrame() {
		return this.columnTitleFrame;
	}

	/**
	 * Sets the <em>columnTitleFrame</em> property.
	 * <p>[dict_id=1202040,name=Column Title Frame,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=40]
	</p>
	 * @param columnTitleFrame the new value of the <em>columnTitleFrame</em> property.

	 */
	public void setColumnTitleFrame(Integer columnTitleFrame) {
		this.columnTitleFrame = columnTitleFrame;
	}

//	/**
//	 * Returns the <em>matrixGroupColumnTitle</em> property.
//	 * <p>[dict_id=1202013,name=Matrix Group Column Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_grp_col_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>matrixGroupColumnTitle</em> property.
//
//	 */
//	public MessageEntity getMatrixGroupColumnTitle() {
//		return this.matrixGroupColumnTitle;
//	}
//
//	/**
//	 * Sets the <em>matrixGroupColumnTitle</em> property.
//	 * <p>[dict_id=1202013,name=Matrix Group Column Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_grp_col_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param matrixGroupColumnTitle the new value of the <em>matrixGroupColumnTitle</em> property.
//
//	 */
//	public void setMatrixGroupColumnTitle(
//			MessageEntity matrixGroupColumnTitle) {
//		this.matrixGroupColumnTitle = matrixGroupColumnTitle;
//	}

	/**
	 * Returns the <em>maxBreakNumber</em> property.
	 * <p>[dict_id=1202032,name=Max. Break Number,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=max_break_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=113,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=20]
	</p>
	 * @return the <em>maxBreakNumber</em> property.

	 */
	public Integer getMaxBreakNumber() {
		return this.maxBreakNumber;
	}

	/**
	 * Sets the <em>maxBreakNumber</em> property.
	 * <p>[dict_id=1202032,name=Max. Break Number,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=max_break_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=113,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=20]
	</p>
	 * @param maxBreakNumber the new value of the <em>maxBreakNumber</em> property.

	 */
	public void setMaxBreakNumber(Integer maxBreakNumber) {
		this.maxBreakNumber = maxBreakNumber;
	}

	/**
	 * Returns the <em>breakValue</em> property.
	 * <p>[dict_id=1202024,name=Break Value,datatype=longname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_value_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>
	 * @return the <em>breakValue</em> property.

	 */
	public String getBreakValue() {
		return this.breakValue;
	}

	/**
	 * Sets the <em>breakValue</em> property.
	 * <p>[dict_id=1202024,name=Break Value,datatype=longname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_value_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>
	 * @param breakValue the new value of the <em>breakValue</em> property.

	 */
	public void setBreakValue(String breakValue) {
		this.breakValue = breakValue;
	}

//	/**
//	 * Returns the <em>report</em> property.
//	 * <p>[dict_id=1202017,name=Report,datatype=id_t,dictEntity=Format,referencedDictEntity=Report,parentAttribute=<null>,sqlname_c=report_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=11]
//	</p>
//	 * @return the <em>report</em> property.
//
//	 */
//	public ReportEntity getReport() {
//		return this.report;
//	}
//
//	/**
//	 * Sets the <em>report</em> property.
//	 * <p>[dict_id=1202017,name=Report,datatype=id_t,dictEntity=Format,referencedDictEntity=Report,parentAttribute=<null>,sqlname_c=report_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=11]
//	</p>
//	 * @param report the new value of the <em>report</em> property.
//
//	 */
//	public void setReport(ReportEntity report) {
//		this.report = report;
//	}
//
//	/**
//	 * Returns the <em>nextPageTitle</em> property.
//	 * <p>[dict_id=1202009,name=Next Page Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=next_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>nextPageTitle</em> property.
//
//	 */
//	public MessageEntity getNextPageTitle() {
//		return this.nextPageTitle;
//	}
//
//	/**
//	 * Sets the <em>nextPageTitle</em> property.
//	 * <p>[dict_id=1202009,name=Next Page Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=next_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param nextPageTitle the new value of the <em>nextPageTitle</em> property.
//
//	 */
//	public void setNextPageTitle(
//			MessageEntity nextPageTitle) {
//		this.nextPageTitle = nextPageTitle;
//	}

	/**
	 * Returns the <em>titleFont</em> property.
	 * <p>[dict_id=1202048,name=Title Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=title_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=46]
	</p>
	 * @return the <em>titleFont</em> property.

	 */
	public Integer getTitleFont() {
		return this.titleFont;
	}

	/**
	 * Sets the <em>titleFont</em> property.
	 * <p>[dict_id=1202048,name=Title Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=title_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=46]
	</p>
	 * @param titleFont the new value of the <em>titleFont</em> property.

	 */
	public void setTitleFont(Integer titleFont) {
		this.titleFont = titleFont;
	}

	/**
	 * Returns the <em>graphLegendPosition</em> property.
	 * <p>[dict_id=1202067,name=Graph Legend Position,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_legend_pos_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=65]
	</p>
	 * @return the <em>graphLegendPosition</em> property.

	 */
	public Integer getGraphLegendPosition() {
		return this.graphLegendPosition;
	}

	/**
	 * Sets the <em>graphLegendPosition</em> property.
	 * <p>[dict_id=1202067,name=Graph Legend Position,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_legend_pos_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=65]
	</p>
	 * @param graphLegendPosition the new value of the <em>graphLegendPosition</em> property.

	 */
	public void setGraphLegendPosition(Integer graphLegendPosition) {
		this.graphLegendPosition = graphLegendPosition;
	}

	/**
	 * Returns the <em>columnTitleThickness</em> property.
	 * <p>[dict_id=1202042,name=Column Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=42]
	</p>
	 * @return the <em>columnTitleThickness</em> property.

	 */
	public Integer getColumnTitleThickness() {
		return this.columnTitleThickness;
	}

	/**
	 * Sets the <em>columnTitleThickness</em> property.
	 * <p>[dict_id=1202042,name=Column Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=42]
	</p>
	 * @param columnTitleThickness the new value of the <em>columnTitleThickness</em> property.

	 */
	public void setColumnTitleThickness(Integer columnTitleThickness) {
		this.columnTitleThickness = columnTitleThickness;
	}

	/**
	 * Returns the <em>code</em> property.
	 * <p>[dict_id=1202002,name=Code,datatype=code_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=code,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
	</p>
	 * @return the <em>code</em> property.

	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the <em>code</em> property.
	 * <p>[dict_id=1202002,name=Code,datatype=code_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=code,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
	</p>
	 * @param code the new value of the <em>code</em> property.

	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the <em>matrixHorzLineThickness</em> property.
	 * <p>[dict_id=1202062,name=Matrix Horz. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_horz_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=60]
	</p>
	 * @return the <em>matrixHorzLineThickness</em> property.

	 */
	public Integer getMatrixHorzLineThickness() {
		return this.matrixHorzLineThickness;
	}

	/**
	 * Sets the <em>matrixHorzLineThickness</em> property.
	 * <p>[dict_id=1202062,name=Matrix Horz. Line Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=matrix_horz_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=60]
	</p>
	 * @param matrixHorzLineThickness the new value of the <em>matrixHorzLineThickness</em> property.

	 */
	public void setMatrixHorzLineThickness(Integer matrixHorzLineThickness) {
		this.matrixHorzLineThickness = matrixHorzLineThickness;
	}

//	/**
//	 * Returns the <em>graphSubTitle</em> property.
//	 * <p>[dict_id=1202015,name=Graph Sub-Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_subtitle_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>graphSubTitle</em> property.
//
//	 */
//	public MessageEntity getGraphSubTitle() {
//		return this.graphSubTitle;
//	}
//
//	/**
//	 * Sets the <em>graphSubTitle</em> property.
//	 * <p>[dict_id=1202015,name=Graph Sub-Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_subtitle_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param graphSubTitle the new value of the <em>graphSubTitle</em> property.
//
//	 */
//	public void setGraphSubTitle(
//			MessageEntity graphSubTitle) {
//		this.graphSubTitle = graphSubTitle;
//	}

	public DictEntity getEntity() {
		return dictEntity;
	}

	public void setEntity(DictEntity entity) {
		this.dictEntity = entity;
	}
	
//	DS-5136 Do not work with IDs anymore!
//    /**
//     * Better-performing (never lazy-loads) shortcut to getEntity().getDictId().
//     * @return the dict_id of the attribute
//     */
//    public long getEntityId() {
//        Long attribute_dict_id = (Long) OpenJPAPerformanceHelper.getAssociationRef(this, "dictEntity");
//        if (attribute_dict_id != null) {
//            return attribute_dict_id;
//        } else {
//            return this.dictEntity.getDictID();
//        }
//    }

//	/**
//	 * Returns the <em>notepad</em> property.
//	 * <p>[dict_id=1202074,name=Notepad,datatype=id_t,dictEntity=Format,referencedDictEntity=Notepad,parentAttribute=<null>,sqlname_c=notepad,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=19]
//	</p>
//	 * @return the <em>notepad</em> property.
//
//	 */
//	public java.util.Set<NotepadEntity> getNotepad() {
//		return this.notepad;
//	}

	/**
	 * Returns the <em>graphExplode</em> property.
	 * <p>[dict_id=1202072,name=Graph Explode,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_explode_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=70]
	</p>
	 * @return the <em>graphExplode</em> property.

	 */
	public Integer getGraphExplode() {
		return this.graphExplode;
	}

	/**
	 * Sets the <em>graphExplode</em> property.
	 * <p>[dict_id=1202072,name=Graph Explode,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_explode_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=70]
	</p>
	 * @param graphExplode the new value of the <em>graphExplode</em> property.

	 */
	public void setGraphExplode(Integer graphExplode) {
		this.graphExplode = graphExplode;
	}

	/**
	 * Returns the <em>graphDisplayPercentage</em> property.
	 * <p>[dict_id=1202070,name=Graph Display Percentage,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_perc_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=68]
	</p>
	 * @return the <em>graphDisplayPercentage</em> property.

	 */
	public Integer getGraphDisplayPercentage() {
		return this.graphDisplayPercentage;
	}

	/**
	 * Sets the <em>graphDisplayPercentage</em> property.
	 * <p>[dict_id=1202070,name=Graph Display Percentage,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_perc_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=68]
	</p>
	 * @param graphDisplayPercentage the new value of the <em>graphDisplayPercentage</em> property.

	 */
	public void setGraphDisplayPercentage(Integer graphDisplayPercentage) {
		this.graphDisplayPercentage = graphDisplayPercentage;
	}

	/**
	 * Returns the <em>totalBeforeLines</em> property.
	 * <p>[dict_id=1202059,name=Total Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=57]
	</p>
	 * @return the <em>totalBeforeLines</em> property.

	 */
	public Integer getTotalBeforeLines() {
		return this.totalBeforeLines;
	}

	/**
	 * Sets the <em>totalBeforeLines</em> property.
	 * <p>[dict_id=1202059,name=Total Before Lines,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=total_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3181,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=57]
	</p>
	 * @param totalBeforeLines the new value of the <em>totalBeforeLines</em> property.

	 */
	public void setTotalBeforeLines(Integer totalBeforeLines) {
		this.totalBeforeLines = totalBeforeLines;
	}

//	/**
//	 * Returns the <em>matrixTitle</em> property.
//	 * <p>[dict_id=1202012,name=Matrix Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @return the <em>matrixTitle</em> property.
//
//	 */
//	public MessageEntity getMatrixTitle() {
//		return this.matrixTitle;
//	}
//
//	/**
//	 * Sets the <em>matrixTitle</em> property.
//	 * <p>[dict_id=1202012,name=Matrix Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=matrix_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=97,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=14]
//	</p>
//	 * @param matrixTitle the new value of the <em>matrixTitle</em> property.
//
//	 */
//	public void setMatrixTitle(
//			MessageEntity matrixTitle) {
//		this.matrixTitle = matrixTitle;
//	}
//
//	/**
//	 * Returns the <em>graphLegendTitle</em> property.
//	 * <p>[dict_id=1202016,name=Graph Legend Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_legend_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=16]
//	</p>
//	 * @return the <em>graphLegendTitle</em> property.
//
//	 */
//	public MessageEntity getGraphLegendTitle() {
//		return this.graphLegendTitle;
//	}
//
//	/**
//	 * Sets the <em>graphLegendTitle</em> property.
//	 * <p>[dict_id=1202016,name=Graph Legend Title,datatype=id_t,dictEntity=Format,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=graph_legend_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=16]
//	</p>
//	 * @param graphLegendTitle the new value of the <em>graphLegendTitle</em> property.
//
//	 */
//	public void setGraphLegendTitle(
//			MessageEntity graphLegendTitle) {
//		this.graphLegendTitle = graphLegendTitle;
//	}

	/**
	 * Returns the <em>titleJustification</em> property.
	 * <p>[dict_id=1202047,name=Title Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=45]
	</p>
	 * @return the <em>titleJustification</em> property.

	 */
	public Integer getTitleJustification() {
		return this.titleJustification;
	}

	/**
	 * Sets the <em>titleJustification</em> property.
	 * <p>[dict_id=1202047,name=Title Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=45]
	</p>
	 * @param titleJustification the new value of the <em>titleJustification</em> property.

	 */
	public void setTitleJustification(Integer titleJustification) {
		this.titleJustification = titleJustification;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.tangij.domainmodel.DenominableWithNameAndNotepads#getDenom()
	 */
	public String getDenom() {
		return this.denom;
	}

	/**
	 * Sets the <em>denom</em> property.
	 * <p>[dict_id=1202004,name=Denom,datatype=info_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]
	</p>
	 * @param denom the new value of the <em>denom</em> property.

	 */
	public void setDenom(String denom) {
		this.denom = denom;
	}

	/**
	 * Returns the <em>blockFont</em> property.
	 * <p>[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38]
	</p>
	 * @return the <em>blockFont</em> property.

	 */
	public Integer getBlockFont() {
		return this.blockFont;
	}

	/**
	 * Sets the <em>blockFont</em> property.
	 * <p>[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38]
	</p>
	 * @param blockFont the new value of the <em>blockFont</em> property.

	 */
	public void setBlockFont(Integer blockFont) {
		this.blockFont = blockFont;
	}

	/**
	 * Returns the <em>borderThickness</em> property.
	 * <p>[dict_id=1202034,name=Border Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=border_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=32]
	</p>
	 * @return the <em>borderThickness</em> property.

	 */
	public Integer getBorderThickness() {
		return this.borderThickness;
	}

	/**
	 * Sets the <em>borderThickness</em> property.
	 * <p>[dict_id=1202034,name=Border Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=border_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=32]
	</p>
	 * @param borderThickness the new value of the <em>borderThickness</em> property.

	 */
	public void setBorderThickness(Integer borderThickness) {
		this.borderThickness = borderThickness;
	}

	/**
	 * Returns the <em>breakCriteria</em> property.
	 * <p>[dict_id=1202023,name=Break Criteria,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_criteria_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>
	 * @return the <em>breakCriteria</em> property.

	 */
	public String getBreakCriteria() {
		return this.breakCriteria;
	}

	/**
	 * Sets the <em>breakCriteria</em> property.
	 * <p>[dict_id=1202023,name=Break Criteria,datatype=sysname_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_criteria_name,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3693,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=10]
	</p>
	 * @param breakCriteria the new value of the <em>breakCriteria</em> property.

	 */
	public void setBreakCriteria(String breakCriteria) {
		this.breakCriteria = breakCriteria;
	}

	/**
	 * Returns the <em>function</em> property.
	 * <p>[dict_id=1202005,name=Function,datatype=dict_t,dictEntity=Format,referencedDictEntity=Function,parentAttribute=<null>,sqlname_c=function_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=5]</p>
	 * @return the <em>function</em> property.
	 */
	public DictFunctionEntity getFunction() {
		return this.function;
	}

	/**
	 * Sets the <em>function</em> property.
	 * <p>[dict_id=1202005,name=Function,datatype=dict_t,dictEntity=Format,referencedDictEntity=Function,parentAttribute=<null>,sqlname_c=function_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=5]
	</p>
	 * @param function the new value of the <em>function</em> property.

	 */
	public void setFunction(DictFunctionEntity function) {
		this.function = function;
	}

	/**
	 * Returns the <em>type</em> property.
	 * <p>[dict_id=1202018,name=Type,datatype=id_t,dictEntity=Format,referencedDictEntity=Type,parentAttribute=<null>,sqlname_c=type_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]</p>
	 * @return the <em>type</em> property.
	 */
	public TypeEntity getType() {
		return this.type;
	}

	/**
	 * Sets the <em>type</em> property.
	 * <p>[dict_id=1202018,name=Type,datatype=id_t,dictEntity=Format,referencedDictEntity=Type,parentAttribute=<null>,sqlname_c=type_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=4095,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]</p>
	 * @param type the new value of the <em>type</em> property.
	 */
	public void setType(TypeEntity type) {
		this.type = type;
	}

	/**
	 * Returns the <em>graphPattern</em> property.
	 * <p>[dict_id=1202066,name=Graph Pattern,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_pattern_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=64]
	</p>
	 * @return the <em>graphPattern</em> property.

	 */
	public Integer getGraphPattern() {
		return this.graphPattern;
	}

	/**
	 * Sets the <em>graphPattern</em> property.
	 * <p>[dict_id=1202066,name=Graph Pattern,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_pattern_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=64]
	</p>
	 * @param graphPattern the new value of the <em>graphPattern</em> property.

	 */
	public void setGraphPattern(Integer graphPattern) {
		this.graphPattern = graphPattern;
	}

//	/**
//	 * Returns the <em>control</em> property.
//	 * <p>[dict_id=1202077,name=Control,datatype=id_t,dictEntity=Format,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=script_control,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
//	</p>
//	 * @return the <em>control</em> property.
//
//	 */
//	public java.util.Set<ScriptDefinitionEntity> getControl() {
//		return this.control;
//	}

	/**
	 * Returns the <em>level</em> property.
	 * <p>[dict_id=1202030,name=Level,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=level_num_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1117,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>
	 * @return the <em>level</em> property.

	 */
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * Sets the <em>level</em> property.
	 * <p>[dict_id=1202030,name=Level,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=level_num_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1117,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=11]
	</p>
	 * @param level the new value of the <em>level</em> property.

	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * Returns the <em>titleThickness</em> property.
	 * <p>[dict_id=1202053,name=Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=51]
	</p>
	 * @return the <em>titleThickness</em> property.

	 */
	public Integer getTitleThickness() {
		return this.titleThickness;
	}

	/**
	 * Sets the <em>titleThickness</em> property.
	 * <p>[dict_id=1202053,name=Title Thickness,datatype=tinyint_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=title_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=51]
	</p>
	 * @param titleThickness the new value of the <em>titleThickness</em> property.

	 */
	public void setTitleThickness(Integer titleThickness) {
		this.titleThickness = titleThickness;
	}

	/**
	 * Returns the <em>justification</em> property.
	 * <p>[dict_id=1202033,name=Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3821,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=31]
	</p>
	 * @return the <em>justification</em> property.

	 */
	public Integer getJustification() {
		return this.justification;
	}

	/**
	 * Sets the <em>justification</em> property.
	 * <p>[dict_id=1202033,name=Justification,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justif_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3821,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=31]
	</p>
	 * @param justification the new value of the <em>justification</em> property.

	 */
	public void setJustification(Integer justification) {
		this.justification = justification;
	}

	/**
	 * Returns the <em>graphDisplayValue</em> property.
	 * <p>[dict_id=1202071,name=Graph Display Value,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_val_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=69]
	</p>
	 * @return the <em>graphDisplayValue</em> property.

	 */
	public Integer getGraphDisplayValue() {
		return this.graphDisplayValue;
	}

	/**
	 * Sets the <em>graphDisplayValue</em> property.
	 * <p>[dict_id=1202071,name=Graph Display Value,datatype=flag_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=graph_disp_val_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=81,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=69]
	</p>
	 * @param graphDisplayValue the new value of the <em>graphDisplayValue</em> property.

	 */
	public void setGraphDisplayValue(Integer graphDisplayValue) {
		this.graphDisplayValue = graphDisplayValue;
	}

	/**
	 * Returns the <em>totalFont</em> property.
	 * <p>[dict_id=1202054,name=Total Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=total_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3197,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=52]
	</p>
	 * @return the <em>totalFont</em> property.

	 */
	public Integer getTotalFont() {
		return this.totalFont;
	}

	/**
	 * Sets the <em>totalFont</em> property.
	 * <p>[dict_id=1202054,name=Total Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=total_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3197,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=52]
	</p>
	 * @param totalFont the new value of the <em>totalFont</em> property.

	 */
	public void setTotalFont(Integer totalFont) {
		this.totalFont = totalFont;
	}

//	/**
//	 * Returns the <em>screen</em> property.
//	 * <p>[dict_id=1202073,name=Screen,datatype=dict_t,dictEntity=Format,referencedDictEntity=Screen,parentAttribute=<null>,sqlname_c=screen_dict_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=71]
//	</p>
//	 * @return the <em>screen</em> property.
//
//	 */
//	public ScreenEntity getScreen() {
//		return this.screen;
//	}
//
//	/**
//	 * Sets the <em>screen</em> property.
//	 * <p>[dict_id=1202073,name=Screen,datatype=dict_t,dictEntity=Format,referencedDictEntity=Screen,parentAttribute=<null>,sqlname_c=screen_dict_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3,quick_search_mask=1,search_mask=0,default_c=<null>,disp_rank_n=71]
//	</p>
//	 * @param screen the new value of the <em>screen</em> property.
//
//	 */
//	public void setScreen(ScreenEntity screen) {
//		this.screen = screen;
//	}
	
	public Set<DenominationEntity> getDenoms() {
        if (denoms != null) {
            return denoms;
        } else {
            return Collections.emptySet();
        }
    }
	
    /* (non-Javadoc)
	 * @see com.odcgroup.tangij.domainmodel.DenominableWithNameAndNotepads#getNotepads()
	 */
    @SuppressWarnings("unchecked")
    public List<NotepadEntity> getNotepads() {
        if (notepads != null) {
        	List<NotepadEntity> list = new ArrayList<NotepadEntity>(notepads);
        	Collections.sort(list, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					int val = 0;
					Date d1 = ((NotepadEntity)o1).getDate();
					Date d2 = ((NotepadEntity)o2).getDate();
					if ((d1 == null) && (d2 == null)) {  
				      val = 0;   // null == null
					} else if (d1 == null) {  
				      val = -1;  // null < d2
					} else if (d2 == null) {  
				      val = +1;  // d1 > null  
					} else {
						val = d1.compareTo(d2);					
					}
					if (val == 0) {
						String t1 = ((NotepadEntity)o1).getTitle();
						if (t1 == null) t1 = "";
						String t2 = ((NotepadEntity)o2).getTitle();
						if (t2 == null) t2 = "";
						val = t1.compareTo(t2);
					}
					return val;
				}
        	});
            return list;            
        } else {
            return Collections.EMPTY_LIST;
        }
    }


	//	//    //-------------------------------------------------------------------------
	//	// com.odcgroup.mdf.core.ReferenceableObject
	//
	//	/**
	//	 * @see com.odcgroup.ofs.model.ReferenceableObject#getObjectId()
	//	 */
	//	public ObjectId getObjectId() {
	//		return FormatId.createFormatId(
	//		//			this.getIdentifier()
	//		
	//		);
	//	}	
	//	

    private static final ToStringStyle TSS = new ToStringStyle() {
        {
            // This is the constructor of this anonymous inner classes
            setUseShortClassName(false);
            setUseClassName(false);
            setUseIdentityHashCode(false);
        }

        @SuppressWarnings({ "rawtypes" })
        protected void appendDetail(StringBuffer buffer, String fieldName, Collection value) {
            if (fieldName.equals("formatElements")) {
                buffer.append("...");
            }
        }
    };

	public String toString() {
        // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
        return ToStringBuilder.reflectionToString(this, TSS);
	}

	// MVO: Not sure a 'new' is such a good idea here??  MdfName should be immutable objects existing only ONCE, shouldn't they?!     
	//    public MdfName getMdfName() {
	//    	return new MdfName("AAA", "Format");
	//    }

}
