package com.odcgroup.aaa.connector.domainmodel;

// import com.odcgroup.ofs.model.ReferenceableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumn;
import org.apache.openjpa.persistence.jdbc.ElementJoinColumns;

import com.odcgroup.otf.jpa.internal.openjpa.OpenJPAPerformanceHelper;



/**
 * T'A FormatElement.
 * 
 * This file has been originally been automatically @generated,
 * but then manually modified (awaiting an adapted generator).
 * 
 * <p>[mmlClass=<null>,dict_id=1203,name=Format Element,sqlname_c=format_element,attributes=...]</p>
 * 
 * @author Michael Vorburger (MVO)
 */
@Entity(name = "FormatElement")
@Table(name = "format_element_vw")
@SuppressWarnings("serial")
public class FormatElementEntity implements DenominableWithNameAndNotepads 
{
    // -------------------------------------------------------------------------
    // Fields

    /*
     * The <em>format</em> property. <p>[dict_id=1203006,name=Format,datatype=id_t,dictEntity=Format
     * Element,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=format_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=1]
     * </p>
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "format_id", nullable=false, updatable=false)
    private /*com.odcgroup.tangij.domain.*/FormatEntity format;

    /*
     * The <em>justification</em> property. <p>[dict_id=1203032,name=Justification,datatype=enum_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justification_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=25]
     * </p>
     */
    @Column(name = "justification_e")
    private Integer justification;

    /*
     * The <em>totaliser</em> property. <p>[dict_id=1203021,name=Totaliser,datatype=info_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=totaliser_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=14]
     * </p>
     */
    @Column(name = "totaliser_c")
    private String totaliser;

    /*
     * The <em>multiLine</em> property. <p>[dict_id=1203038,name=Multi-Line,datatype=tinyint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=multi_line_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=36]
     * </p>
     */
    @Column(name = "multi_line_n")
    private Integer multiLine;

    /*
     * The <em>identifier</em> property. <p>[dict_id=1203001,name=Identifier,datatype=id_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
     * </p>* @primaryKey
     */
    @Id
    @Column(name = "id")
    private long identifier;

    /*
     * The <em>columnSeparator</em> property. <p>[dict_id=1203037,name=Column
     * Separator,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=separator_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=35]
     * </p>
     */
    @Column(name = "separator_e")
    private Integer columnSeparator;

    /*
     * The <em>fontSize</em> property. <p>[dict_id=1203035,name=Font Size,datatype=tinyint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=33]
     * </p>
     */
    @Column(name = "font_size_n")
    private Integer fontSize;

    /*
     * The <em>backgroundColour</em> property. <p>[dict_id=1203028,name=Background
     * Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=backgr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=21]
     * </p>
     */
    @Column(name = "backgr_colour_c")
    private String backgroundColour;

    /*
     * The <em>breakGrayForce</em> property. <p>[dict_id=1203043,name=Break Gray
     * Force,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=41]
     * </p>
     */
    @Column(name = "break_gray_force_n")
    private Integer breakGrayForce;

    /*
     * The <em>displayFormat</em> property. <p>[dict_id=1203014,name=Display Format,datatype=info_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_format_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
     * </p>
     */
    @Column(name = "display_format_c")
    private String displayFormat;

    /*
     * The <em>rank</em> property. <p>[dict_id=1203003,name=Rank,datatype=smallint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=rank_n,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     */
    @Column(name = "rank_n")
    private Integer rank;

    /*
     * The <em>breakThickness</em> property. <p>[dict_id=1203042,name=Break
     * Thickness,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=40]
     * </p>
     */
    @Column(name = "break_thick_n")
    private Integer breakThickness;

    /*
     * The <em>sQLName</em> property. <p>[dict_id=1203002,name=SQL Name,datatype=sysname_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sqlname_c,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     */
    @Column(name = "sqlname_c")
    private String sqlName;

    /*
     * The <em>zoom</em> property. <p>[dict_id=1203027,name=Zoom,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=zoom_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=20]
     * </p>
     */
    @Column(name = "zoom_f")
    private Integer zoom;

//    /*
//     * The <em>definition</em> property. <p>[dict_id=1203047,name=Definition,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=script_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=27]
//     * </p>
//     */
//    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "script_definition")
//    private java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> definition;

    @OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @ElementJoinColumns({
        @ElementJoinColumn(name="object_id", referencedColumnName="id"),
        @ElementJoinColumn(name="attribute_dict_id", referencedColumnName="1203054")
        })
    @OrderBy("rank")
    private List<ScriptDefinitionEntity> scriptDefinitions;
    private List<ScriptDefinitionEntity> getScriptDefinitions() {
        return scriptDefinitions;
    }

    @Transient String scriptDefinition;
    public String getScriptDefinition() {
        if (scriptDefinition == null) {
            if (getScriptDefinitions() == null) {
                scriptDefinition = null;
            } else {
                StringBuffer sb = new StringBuffer();
                for (ScriptDefinitionEntity script : getScriptDefinitions()) {
                    sb.append(script.getDefinition());
                }
                scriptDefinition = sb.toString();
            }
        }
        return scriptDefinition;
    }
    
    // TODO attribute_dict_id is probably wrong...
    @OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @ElementJoinColumns({
        @ElementJoinColumn(name="object_id", referencedColumnName="id"),
        @ElementJoinColumn(name="attribute_dict_id", referencedColumnName="1203048")
        })
    @OrderBy("rank")
    private List<ScriptDefinitionEntity> cellFmtDefinitions;
    private List<ScriptDefinitionEntity> getCellFmtDefinitions() {
        return cellFmtDefinitions;
    }
    
    @Transient String cellFmtDefinition;
    public String getCellFmtDefinition() {
        if (cellFmtDefinition == null) {
            if (getCellFmtDefinitions() == null) {
                cellFmtDefinition = null;
            } else {
                StringBuffer sb = new StringBuffer();
                for (ScriptDefinitionEntity script : getCellFmtDefinitions()) {
                    sb.append(script.getDefinition());
                }
                cellFmtDefinition = sb.toString();
            }
        }
        return cellFmtDefinition;
    }
    
    
    /*
     * The <em>breakBeforeLines</em> property. <p>[dict_id=1203044,name=Break Before
     * Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=42]
     * </p>
     */
    @Column(name = "break_bef_lines_n")
    private Integer breakBeforeLines;

    /*
     * The <em>displayContext</em> property. <p>[dict_id=1203017,name=Display
     * Context,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_context_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=10]
     * </p>
     */
    @Column(name = "display_context_e")
    private Integer displayContext;

    /*
     * The <em>currentMapFMT</em> property. <p>[dict_id=1203011,name=Current Map FMT,datatype=id_t,dictEntity=Format
     * Element,referencedDictEntity=Format Element,parentAttribute=<null>,sqlname_c=curr_map_fmt_elt_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Column(name = "curr_map_fmt_elt_id")
    private /*com.odcgroup.tangij.domain.*/FormatElementEntity currentMapFMT;

//    /*
//     * The <em>columnTitle</em> property. <p>[dict_id=1203009,name=Column Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=header_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=35]
//     * </p>
//     */
//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @Column(name = "header_title_msg_id")
//    private com.odcgroup.tangij.domain.MessageEntity columnTitle;

    /*
     * The <em>attribute</em> property. <p>[dict_id=1203008,name=Attribute,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Attribute,parentAttribute=<null>,sqlname_c=attribute_dict_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=17]
     * </p>
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional=true)
    @JoinColumn(name = "attribute_dict_id", nullable=true)
    private /*com.odcgroup.tangij.domain.AttributeEntity*/DictAttribute attribute;

    /*
     * The <em>breakFrame</em> property. <p>[dict_id=1203041,name=Break Frame,datatype=enum_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=39]
     * </p>
     */
    @Column(name = "break_frame_e")
    private Integer breakFrame;

    /*
     * The <em>denom</em> property. <p>[dict_id=1203005,name=Denom,datatype=info_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]
     * </p>
     */
    @Column(name = "denom")
    private String denom;

    /*
     * The <em>sortingRule</em> property. <p>[dict_id=1203023,name=Sorting Rule,datatype=enum_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rule_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=16]
     * </p>
     */
    @Column(name = "sorting_rule_e")
    private Integer sortingRule;

    /*
     * The <em>consolidation</em> property. <p>[dict_id=1203019,name=Consolidation,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=cons_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=12]
     * </p>
     */
    @Column(name = "cons_f")
    private Integer consolidation;

//    /*
//     * The <em>breakTitle</em> property. <p>[dict_id=1203010,name=Break Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=break_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=37]
//     * </p>
//     */
//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @Column(name = "break_title_msg_id")
//    private com.odcgroup.tangij.domain.MessageEntity breakTitle;
//
//    /*
//     * The <em>denomination</em> property. <p>[dict_id=1203049,name=Denomination,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Denomination,parentAttribute=<null>,sqlname_c=denomination,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=28]
//     * </p>
//     */
//    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "denomination")
//    private java.util.Set<com.odcgroup.tangij.domain.DenominationEntity> denomination;

    /*
     * The <em>verticalCoordinate</em> property. <p>[dict_id=1203025,name=Vertical
     * Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=vert_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     */
    @Column(name = "vert_coord_n")
    private Integer verticalCoordinate;

    /*
     * The <em>displayRow</em> property. <p>[dict_id=1203016,name=Display Row,datatype=smallint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_row_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     */
    @Column(name = "display_row_n")
    private Integer displayRow;

    /*
     * The <em>horizontalCoordinate</em> property. <p>[dict_id=1203024,name=Horizontal
     * Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=horiz_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     */
    @Column(name = "horiz_coord_n")
    private Integer horizontalCoordinate;

//    /*
//     * The <em>cellFormat</em> property. <p>[dict_id=1203048,name=Cell Format,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=cell_fmt_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=28]
//     * </p>
//     */
//    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "cell_fmt_definition")
//    private java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> cellFormat;

    /*
     * The <em>grayForce</em> property. <p>[dict_id=1203036,name=Gray Force,datatype=tinyint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=34]
     * </p>
     */
    @Column(name = "gray_force_n")
    private Integer grayForce;

    /*
     * The <em>name</em> property. <p>[dict_id=1203004,name=Name,datatype=name_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]
     * </p>
     */
    @Column(name = "name")
    private String name;

    /*
     * The <em>font</em> property. <p>[dict_id=1203034,name=Font,datatype=enum_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=32]
     * </p>
     */
    @Column(name = "font_e")
    private Integer font;

    /*
     * The <em>breakFontSize</em> property. <p>[dict_id=1203040,name=Break Font
     * Size,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     */
    @Column(name = "break_font_size_n")
    private Integer breakFontSize;

    /*
     * The <em>fixedColumn</em> property. <p>[dict_id=1203018,name=Fixed Column,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=fixed_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=11]
     * </p>
     */
    @Column(name = "fixed_f")
    private Integer fixedColumn;

    /*
     * The <em>hierarchyNature</em> property. <p>[dict_id=1203046,name=Hierarchy
     * Nature,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=hierarchy_nat_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=45]
     * </p>
     */
    @Column(name = "hierarchy_nat_e")
    private Integer hierarchyNature;

    /*
     * The <em>displayColumn</em> property. <p>[dict_id=1203015,name=Display
     * Column,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_column_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     */
    @Column(name = "display_column_n")
    private Integer displayColumn;

    /*
     * The <em>italic</em> property. <p>[dict_id=1203030,name=Italic,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=italic_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=23]
     * </p>
     */
    @Column(name = "italic_f")
    private Integer italic;

//    /*
//     * The <em>control</em> property. <p>[dict_id=1203051,name=Control,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Script Definition,parentAttribute=<null>,sqlname_c=script_control,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
//     * </p>
//     */
//    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "script_control")
//    private java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> control;

    /*
     * The <em>edit</em> property. <p>[dict_id=1203033,name=Edit,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=edit_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=17]
     * </p>
     */
    @Column(name = "edit_f")
    private Integer edit;

    /*
     * The <em>bold</em> property. <p>[dict_id=1203031,name=Bold,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=bold_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=24]
     * </p>
     */
    @Column(name = "bold_f")
    private Integer bold;

    /*
     * The <em>breakAfterLines</em> property. <p>[dict_id=1203045,name=Break After
     * Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=43]
     * </p>
     */
    @Column(name = "break_aft_lines_n")
    private Integer breakAfterLines;

//    /*
//     * The <em>notepad</em> property. <p>[dict_id=1203050,name=Notepad,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Notepad,parentAttribute=<null>,sqlname_c=notepad,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=29]
//     * </p>
//     */
//    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "notepad")
//    private java.util.Set<com.odcgroup.tangij.domain.NotepadEntity> notepad;

    /*
     * The <em>breakFont</em> property. <p>[dict_id=1203039,name=Break Font,datatype=enum_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=break_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=37]
     * </p>
     */
    @Column(name = "break_font_e")
    private Integer breakFont;

    /*
     * The <em>datatype</em> property. <p>[dict_id=1203007,name=Datatype,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Datatype,parentAttribute=<null>,sqlname_c=datatype_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE_FILTER(3),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=6]
     * </p>
     */
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional=false) // NOT LAZY!
    @JoinColumn(name = "datatype_dict_id", nullable=false, updatable=false)
    private /*com.odcgroup.tangij.domain.DatatypeEntity*/DictDataType datatype;

    /*
     * The <em>columnWidth</em> property. <p>[dict_id=1203026,name=Column Width,datatype=smallint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=col_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=19]
     * </p>
     */
    @Column(name = "col_width_n")
    private Integer columnWidth;

    /*
     * The <em>sortingRank</em> property. <p>[dict_id=1203022,name=Sorting Rank,datatype=smallint_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rank_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=15]
     * </p>
     */
    @Column(name = "sorting_rank_n")
    private Integer sortingRank;

    /*
     * The <em>subTotal</em> property. <p>[dict_id=1203020,name=Sub-Total,datatype=flag_t,dictEntity=Format
     * Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sub_total_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=13]
     * </p>
     */
    @Column(name = "sub_total_f")
    private Integer subTotal;

    /*
     * The <em>foregroundColour</em> property. <p>[dict_id=1203029,name=Foreground
     * Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=foregr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=22]
     * </p>
     */
    @Column(name = "foregr_colour_c")
    private String foregroundColour;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ElementJoinColumns( { @ElementJoinColumn(name = "object_id", referencedColumnName = "id"),
            @ElementJoinColumn(name = "entity_dict_id", referencedColumnName = "1203") })
    private Set<DenominationEntity> denoms;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ElementJoinColumns( { 
        @ElementJoinColumn(name = "object_id", referencedColumnName = "id"),
        @ElementJoinColumn(name = "entity_dict_id", referencedColumnName = "1203") })
    private Set<NotepadEntity> notepads;
    
    
    @Column(name = "tsl_multilingual_e")
    private Integer tslMultilingual;

    // -------------------------------------------------------------------------
    // Constructor

    /**
     * @generated
     */
    public FormatElementEntity() {
        super();
        try {

            this.justification = 0;

            this.columnSeparator = 0;

            this.zoom = 0;

//            this.definition = new java.util.HashSet<com.odcgroup.tangij.domain.ScriptDefinitionEntity>();

            this.displayContext = 0;

            this.breakFrame = 0;

            this.sortingRule = 1;

            this.consolidation = 0;

//            this.denomination = new java.util.HashSet<com.odcgroup.tangij.domain.DenominationEntity>();

//            this.cellFormat = new java.util.HashSet<com.odcgroup.tangij.domain.ScriptDefinitionEntity>();

            this.font = 0;

            this.fixedColumn = 0;

            this.hierarchyNature = 0;

            this.italic = 0;

//            this.control = new java.util.HashSet<com.odcgroup.tangij.domain.ScriptDefinitionEntity>();

            this.edit = 0;

            this.bold = 0;

//            this.notepad = new java.util.HashSet<com.odcgroup.tangij.domain.NotepadEntity>();

            this.breakFont = 0;

            this.subTotal = 0;

        } catch (Exception e) {
            throw (IllegalArgumentException) new IllegalArgumentException(e.getMessage()).initCause(e);
        }
    }

    // -------------------------------------------------------------------------
    // Accessors

    /**
     * Returns the <em>format</em> property.
     * <p>
     * [dict_id=1203006,name=Format,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=format_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=1]
     * </p>
     * 
     * @return the <em>format</em> property.
     * 
     */
    public /*com.odcgroup.tangij.domain.*/FormatEntity getFormat() {
        return this.format;
    }

    /**
     * Sets the <em>format</em> property.
     * <p>
     * [dict_id=1203006,name=Format,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Format,parentAttribute=<null>,sqlname_c=format_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=1]
     * </p>
     * 
     * @param format the new value of the <em>format</em> property.
     * 
     */
    public void setFormat(FormatEntity format) {
        this.format = format;
    }

    /**
     * Returns the <em>justification</em> property.
     * <p>
     * [dict_id=1203032,name=Justification,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justification_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=25]
     * </p>
     * 
     * @return the <em>justification</em> property.
     * 
     */
    public Integer getJustification() {
        return this.justification;
    }

    /**
     * Sets the <em>justification</em> property.
     * <p>
     * [dict_id=1203032,name=Justification,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=justification_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=25]
     * </p>
     * 
     * @param justification the new value of the <em>justification</em> property.
     * 
     */
    public void setJustification(Integer justification) {
        this.justification = justification;
    }

    /**
     * Returns the <em>totaliser</em> property.
     * <p>
     * [dict_id=1203021,name=Totaliser,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=totaliser_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=14]
     * </p>
     * 
     * @return the <em>totaliser</em> property.
     * 
     */
    public String getTotaliser() {
        return this.totaliser;
    }

    /**
     * Sets the <em>totaliser</em> property.
     * <p>
     * [dict_id=1203021,name=Totaliser,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=totaliser_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=14]
     * </p>
     * 
     * @param totaliser the new value of the <em>totaliser</em> property.
     * 
     */
    public void setTotaliser(String totaliser) {
        this.totaliser = totaliser;
    }

    /**
     * Returns the <em>multiLine</em> property.
     * <p>
     * [dict_id=1203038,name=Multi-Line,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=multi_line_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=36]
     * </p>
     * 
     * @return the <em>multiLine</em> property.
     * 
     */
    public Integer getMultiLine() {
        return this.multiLine;
    }

    /**
     * Sets the <em>multiLine</em> property.
     * <p>
     * [dict_id=1203038,name=Multi-Line,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=multi_line_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=36]
     * </p>
     * 
     * @param multiLine the new value of the <em>multiLine</em> property.
     * 
     */
    public void setMultiLine(Integer multiLine) {
        this.multiLine = multiLine;
    }

    /**
     * Returns the <em>identifier</em> property.
     * <p>
     * [dict_id=1203001,name=Identifier,datatype=id_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
     * </p>
     * 
     * @return the <em>identifier</em> property.
     * 
     * @primaryKey
     */
    public long getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets the <em>identifier</em> property.
     * <p>
     * [dict_id=1203001,name=Identifier,datatype=id_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=id,primary_f=true,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
     * </p>
     * 
     * @param identifier the new value of the <em>identifier</em> property.
     * 
     * @primaryKey
     */
    public void setID(long identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the <em>columnSeparator</em> property.
     * <p>
     * [dict_id=1203037,name=Column Separator,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=separator_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=35]
     * </p>
     * 
     * @return the <em>columnSeparator</em> property.
     * 
     */
    public Integer getColumnSeparator() {
        return this.columnSeparator;
    }

    /**
     * Sets the <em>columnSeparator</em> property.
     * <p>
     * [dict_id=1203037,name=Column Separator,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=separator_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=35]
     * </p>
     * 
     * @param columnSeparator the new value of the <em>columnSeparator</em> property.
     * 
     */
    public void setColumnSeparator(Integer columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    /**
     * Returns the <em>fontSize</em> property.
     * <p>
     * [dict_id=1203035,name=Font Size,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=33]
     * </p>
     * 
     * @return the <em>fontSize</em> property.
     * 
     */
    public Integer getFontSize() {
        return this.fontSize;
    }

    /**
     * Sets the <em>fontSize</em> property.
     * <p>
     * [dict_id=1203035,name=Font Size,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=33]
     * </p>
     * 
     * @param fontSize the new value of the <em>fontSize</em> property.
     * 
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Returns the <em>backgroundColour</em> property.
     * <p>
     * [dict_id=1203028,name=Background Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=backgr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=21]
     * </p>
     * 
     * @return the <em>backgroundColour</em> property.
     * 
     */
    public String getBackgroundColour() {
        return this.backgroundColour;
    }

    /**
     * Sets the <em>backgroundColour</em> property.
     * <p>
     * [dict_id=1203028,name=Background Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=backgr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=21]
     * </p>
     * 
     * @param backgroundColour the new value of the <em>backgroundColour</em> property.
     * 
     */
    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    /**
     * Returns the <em>breakGrayForce</em> property.
     * <p>
     * [dict_id=1203043,name=Break Gray Force,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=41]
     * </p>
     * 
     * @return the <em>breakGrayForce</em> property.
     * 
     */
    public Integer getBreakGrayForce() {
        return this.breakGrayForce;
    }

    /**
     * Sets the <em>breakGrayForce</em> property.
     * <p>
     * [dict_id=1203043,name=Break Gray Force,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=41]
     * </p>
     * 
     * @param breakGrayForce the new value of the <em>breakGrayForce</em> property.
     * 
     */
    public void setBreakGrayForce(Integer breakGrayForce) {
        this.breakGrayForce = breakGrayForce;
    }

    /**
     * Returns the <em>displayFormat</em> property.
     * <p>
     * [dict_id=1203014,name=Display Format,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_format_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
     * </p>
     * 
     * @return the <em>displayFormat</em> property.
     * 
     */
    public String getDisplayFormat() {
        return this.displayFormat;
    }

    /**
     * Sets the <em>displayFormat</em> property.
     * <p>
     * [dict_id=1203014,name=Display Format,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_format_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=7]
     * </p>
     * 
     * @param displayFormat the new value of the <em>displayFormat</em> property.
     * 
     */
    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    /**
     * Returns the <em>rank</em> property.
     * <p>
     * [dict_id=1203003,name=Rank,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=rank_n,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     * 
     * @return the <em>rank</em> property.
     * 
     */
    public Integer getRank() {
        return this.rank;
    }

    /**
     * Sets the <em>rank</em> property.
     * <p>
     * [dict_id=1203003,name=Rank,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=rank_n,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     * 
     * @param rank the new value of the <em>rank</em> property.
     * 
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Returns the <em>breakThickness</em> property.
     * <p>
     * [dict_id=1203042,name=Break Thickness,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=40]
     * </p>
     * 
     * @return the <em>breakThickness</em> property.
     * 
     */
    public Integer getBreakThickness() {
        return this.breakThickness;
    }

    /**
     * Sets the <em>breakThickness</em> property.
     * <p>
     * [dict_id=1203042,name=Break Thickness,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_thick_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=40]
     * </p>
     * 
     * @param breakThickness the new value of the <em>breakThickness</em> property.
     * 
     */
    public void setBreakThickness(Integer breakThickness) {
        this.breakThickness = breakThickness;
    }

    /**
     * Returns the <em>sQLName</em> property.
     * <p>
     * [dict_id=1203002,name=SQL Name,datatype=sysname_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sqlname_c,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     * 
     * @return the <em>sQLName</em> property.
     * 
     */
    public String getSQLName() {
        return this.sqlName;
    }

    /**
     * Sets the <em>sQLName</em> property.
     * <p>
     * [dict_id=1203002,name=SQL Name,datatype=sysname_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sqlname_c,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=true,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=2]
     * </p>
     * 
     * @param sQLName the new value of the <em>sQLName</em> property.
     * 
     */
    public void setSQLName(String sQLName) {
        this.sqlName = sQLName;
    }

    /**
     * Returns the <em>zoom</em> property.
     * <p>
     * [dict_id=1203027,name=Zoom,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=zoom_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=20]
     * </p>
     * 
     * @return the <em>zoom</em> property.
     * 
     */
    public Integer getZoom() {
        return this.zoom;
    }

    /**
     * Sets the <em>zoom</em> property.
     * <p>
     * [dict_id=1203027,name=Zoom,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=zoom_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=20]
     * </p>
     * 
     * @param zoom the new value of the <em>zoom</em> property.
     * 
     */
    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

//    /**
//     * Returns the <em>definition</em> property.
//     * <p>
//     * [dict_id=1203047,name=Definition,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Script
//     * Definition,parentAttribute=<null>,sqlname_c=script_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=27]
//     * </p>
//     * 
//     * @return the <em>definition</em> property.
//     * 
//     */
//    public java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> getDefinition() {
//        return this.definition;
//    }

    /**
     * Returns the <em>breakBeforeLines</em> property.
     * <p>
     * [dict_id=1203044,name=Break Before Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=42]
     * </p>
     * 
     * @return the <em>breakBeforeLines</em> property.
     * 
     */
    public Integer getBreakBeforeLines() {
        return this.breakBeforeLines;
    }

    /**
     * Sets the <em>breakBeforeLines</em> property.
     * <p>
     * [dict_id=1203044,name=Break Before Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_bef_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=42]
     * </p>
     * 
     * @param breakBeforeLines the new value of the <em>breakBeforeLines</em> property.
     * 
     */
    public void setBreakBeforeLines(Integer breakBeforeLines) {
        this.breakBeforeLines = breakBeforeLines;
    }

    /**
     * Returns the <em>displayContext</em> property.
     * <p>
     * [dict_id=1203017,name=Display Context,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_context_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=10]
     * </p>
     * 
     * @return the <em>displayContext</em> property.
     * 
     */
    public Integer getDisplayContext() {
        return this.displayContext;
    }

    /**
     * Sets the <em>displayContext</em> property.
     * <p>
     * [dict_id=1203017,name=Display Context,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_context_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=10]
     * </p>
     * 
     * @param displayContext the new value of the <em>displayContext</em> property.
     * 
     */
    public void setDisplayContext(Integer displayContext) {
        this.displayContext = displayContext;
    }

    /**
     * Returns the <em>currentMapFMT</em> property.
     * <p>
     * [dict_id=1203011,name=Current Map FMT,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Format
     * Element,parentAttribute=<null>,sqlname_c=curr_map_fmt_elt_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     * 
     * @return the <em>currentMapFMT</em> property.
     * 
     */
    public /*com.odcgroup.tangij.domain.*/FormatElementEntity getCurrentMapFMT() {
        return this.currentMapFMT;
    }

    /**
     * Sets the <em>currentMapFMT</em> property.
     * <p>
     * [dict_id=1203011,name=Current Map FMT,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Format
     * Element,parentAttribute=<null>,sqlname_c=curr_map_fmt_elt_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     * 
     * @param currentMapFMT the new value of the <em>currentMapFMT</em> property.
     * 
     */
    public void setCurrentMapFMT(FormatElementEntity currentMapFMT) {
        this.currentMapFMT = currentMapFMT;
    }

//    /**
//     * Returns the <em>columnTitle</em> property.
//     * <p>
//     * [dict_id=1203009,name=Column Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=header_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=35]
//     * </p>
//     * 
//     * @return the <em>columnTitle</em> property.
//     * 
//     */
//    public com.odcgroup.tangij.domain.MessageEntity getColumnTitle() {
//        return this.columnTitle;
//    }
//
//    /**
//     * Sets the <em>columnTitle</em> property.
//     * <p>
//     * [dict_id=1203009,name=Column Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=header_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=35]
//     * </p>
//     * 
//     * @param columnTitle the new value of the <em>columnTitle</em> property.
//     * 
//     */
//    public void setColumnTitle(com.odcgroup.tangij.domain.MessageEntity columnTitle) {
//        this.columnTitle = columnTitle;
//    }

    /**
     * Returns the <em>attribute</em> property.
     * <p>
     * [dict_id=1203008,name=Attribute,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Attribute,parentAttribute=<null>,sqlname_c=attribute_dict_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=17]
     * </p>
     * 
     * @return the <em>attribute</em> property.
     * 
     */
    public DictAttribute getAttribute() {
        return this.attribute;
    }

    /**
     * Better-performing (never lazy-loads) shortcut to getAttribute().getDictId().
     * @return the dict_id of the attribute
     */
    public Long getAttributeDictID() {
        Long attribute_dict_id = (Long) OpenJPAPerformanceHelper.getAssociationRef(this, "attribute");
        if (attribute_dict_id != null) {
            return attribute_dict_id;
        } else {
            if (this.attribute != null) {
                return this.attribute.getDictID();
            } else {
                return null;
            }
        }
    }

    /**
     * Sets the <em>attribute</em> property.
     * <p>
     * [dict_id=1203008,name=Attribute,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Attribute,parentAttribute=<null>,sqlname_c=attribute_dict_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=17]
     * </p>
     * 
     * @param attribute the new value of the <em>attribute</em> property.
     * 
     */
    public void setAttribute(DictAttribute attribute) {
        this.attribute = attribute;
    }

    /**
     * Returns the <em>breakFrame</em> property.
     * <p>
     * [dict_id=1203041,name=Break Frame,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=39]
     * </p>
     * 
     * @return the <em>breakFrame</em> property.
     * 
     */
    public Integer getBreakFrame() {
        return this.breakFrame;
    }

    /**
     * Sets the <em>breakFrame</em> property.
     * <p>
     * [dict_id=1203041,name=Break Frame,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_frame_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=39]
     * </p>
     * 
     * @param breakFrame the new value of the <em>breakFrame</em> property.
     * 
     */
    public void setBreakFrame(Integer breakFrame) {
        this.breakFrame = breakFrame;
    }

    /**
     * Returns the <em>denom</em> property.
     * <p>
     * [dict_id=1203005,name=Denom,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]
     * </p>
     * 
     * @return the <em>denom</em> property.
     * 
     */
    public String getDenom() {
        return this.denom;
    }

    /**
     * Sets the <em>denom</em> property.
     * <p>
     * [dict_id=1203005,name=Denom,datatype=info_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=denom,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=4]
     * </p>
     * 
     * @param denom the new value of the <em>denom</em> property.
     * 
     */
    public void setDenom(String denom) {
        this.denom = denom;
    }

    /**
     * Returns the <em>sortingRule</em> property.
     * <p>
     * [dict_id=1203023,name=Sorting Rule,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rule_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=16]
     * </p>
     * 
     * @return the <em>sortingRule</em> property.
     * 
     */
    public Integer getSortingRule() {
        return this.sortingRule;
    }

    /**
     * Sets the <em>sortingRule</em> property.
     * <p>
     * [dict_id=1203023,name=Sorting Rule,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rule_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=1,disp_rank_n=16]
     * </p>
     * 
     * @param sortingRule the new value of the <em>sortingRule</em> property.
     * 
     */
    public void setSortingRule(Integer sortingRule) {
        this.sortingRule = sortingRule;
    }

    /**
     * Returns the <em>consolidation</em> property.
     * <p>
     * [dict_id=1203019,name=Consolidation,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=cons_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=12]
     * </p>
     * 
     * @return the <em>consolidation</em> property.
     * 
     */
    public Integer getConsolidation() {
        return this.consolidation;
    }

    /**
     * Sets the <em>consolidation</em> property.
     * <p>
     * [dict_id=1203019,name=Consolidation,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=cons_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=12]
     * </p>
     * 
     * @param consolidation the new value of the <em>consolidation</em> property.
     * 
     */
    public void setConsolidation(Integer consolidation) {
        this.consolidation = consolidation;
    }

//    /**
//     * Returns the <em>breakTitle</em> property.
//     * <p>
//     * [dict_id=1203010,name=Break Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=break_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=37]
//     * </p>
//     * 
//     * @return the <em>breakTitle</em> property.
//     * 
//     */
//    public com.odcgroup.tangij.domain.MessageEntity getBreakTitle() {
//        return this.breakTitle;
//    }

//    /**
//     * Sets the <em>breakTitle</em> property.
//     * <p>
//     * [dict_id=1203010,name=Break Title,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Message,parentAttribute=<null>,sqlname_c=break_title_msg_id,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=37]
//     * </p>
//     * 
//     * @param breakTitle the new value of the <em>breakTitle</em> property.
//     * 
//     */
//    public void setBreakTitle(com.odcgroup.tangij.domain.MessageEntity breakTitle) {
//        this.breakTitle = breakTitle;
//    }

//    /**
//     * Returns the <em>denomination</em> property.
//     * <p>
//     * [dict_id=1203049,name=Denomination,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Denomination,parentAttribute=<null>,sqlname_c=denomination,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=28]
//     * </p>
//     * 
//     * @return the <em>denomination</em> property.
//     * 
//     */
//    public java.util.Set<com.odcgroup.tangij.domain.DenominationEntity> getDenomination() {
//        return this.denomination;
//    }

    /**
     * Returns the <em>verticalCoordinate</em> property.
     * <p>
     * [dict_id=1203025,name=Vertical Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=vert_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     * 
     * @return the <em>verticalCoordinate</em> property.
     * 
     */
    public Integer getVerticalCoordinate() {
        return this.verticalCoordinate;
    }

    /**
     * Sets the <em>verticalCoordinate</em> property.
     * <p>
     * [dict_id=1203025,name=Vertical Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=vert_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     * 
     * @param verticalCoordinate the new value of the <em>verticalCoordinate</em> property.
     * 
     */
    public void setVerticalCoordinate(Integer verticalCoordinate) {
        this.verticalCoordinate = verticalCoordinate;
    }

    /**
     * Returns the <em>displayRow</em> property.
     * <p>
     * [dict_id=1203016,name=Display Row,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_row_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     * 
     * @return the <em>displayRow</em> property.
     * 
     */
    public Integer getDisplayRow() {
        return this.displayRow;
    }

    /**
     * Sets the <em>displayRow</em> property.
     * <p>
     * [dict_id=1203016,name=Display Row,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_row_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     * 
     * @param displayRow the new value of the <em>displayRow</em> property.
     * 
     */
    public void setDisplayRow(Integer displayRow) {
        this.displayRow = displayRow;
    }

    /**
     * Returns the <em>horizontalCoordinate</em> property.
     * <p>
     * [dict_id=1203024,name=Horizontal Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=horiz_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     * 
     * @return the <em>horizontalCoordinate</em> property.
     * 
     */
    public Integer getHorizontalCoordinate() {
        return this.horizontalCoordinate;
    }

    /**
     * Sets the <em>horizontalCoordinate</em> property.
     * <p>
     * [dict_id=1203024,name=Horizontal Coordinate,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=horiz_coord_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=18]
     * </p>
     * 
     * @param horizontalCoordinate the new value of the <em>horizontalCoordinate</em> property.
     * 
     */
    public void setHorizontalCoordinate(Integer horizontalCoordinate) {
        this.horizontalCoordinate = horizontalCoordinate;
    }

//    /**
//     * Returns the <em>cellFormat</em> property.
//     * <p>
//     * [dict_id=1203048,name=Cell Format,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Script
//     * Definition,parentAttribute=<null>,sqlname_c=cell_fmt_definition,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=28]
//     * </p>
//     * 
//     * @return the <em>cellFormat</em> property.
//     * 
//     */
//    public java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> getCellFormat() {
//        return this.cellFormat;
//    }

    /**
     * Returns the <em>grayForce</em> property.
     * <p>
     * [dict_id=1203036,name=Gray Force,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=34]
     * </p>
     * 
     * @return the <em>grayForce</em> property.
     * 
     */
    public Integer getGrayForce() {
        return this.grayForce;
    }

    /**
     * Sets the <em>grayForce</em> property.
     * <p>
     * [dict_id=1203036,name=Gray Force,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=gray_force_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=34]
     * </p>
     * 
     * @param grayForce the new value of the <em>grayForce</em> property.
     * 
     */
    public void setGrayForce(Integer grayForce) {
        this.grayForce = grayForce;
    }

    /**
     * Returns the <em>name</em> property.
     * <p>
     * [dict_id=1203004,name=Name,datatype=name_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]
     * </p>
     * 
     * @return the <em>name</em> property.
     * 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the <em>name</em> property.
     * <p>
     * [dict_id=1203004,name=Name,datatype=name_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=name,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=3]
     * </p>
     * 
     * @param name the new value of the <em>name</em> property.
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the <em>font</em> property.
     * <p>
     * [dict_id=1203034,name=Font,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=32]
     * </p>
     * 
     * @return the <em>font</em> property.
     * 
     */
    public Integer getFont() {
        return this.font;
    }

    /**
     * Sets the <em>font</em> property.
     * <p>
     * [dict_id=1203034,name=Font,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=32]
     * </p>
     * 
     * @param font the new value of the <em>font</em> property.
     * 
     */
    public void setFont(Integer font) {
        this.font = font;
    }

    /**
     * Returns the <em>breakFontSize</em> property.
     * <p>
     * [dict_id=1203040,name=Break Font Size,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     * 
     * @return the <em>breakFontSize</em> property.
     * 
     */
    public Integer getBreakFontSize() {
        return this.breakFontSize;
    }

    /**
     * Sets the <em>breakFontSize</em> property.
     * <p>
     * [dict_id=1203040,name=Break Font Size,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_font_size_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=38]
     * </p>
     * 
     * @param breakFontSize the new value of the <em>breakFontSize</em> property.
     * 
     */
    public void setBreakFontSize(Integer breakFontSize) {
        this.breakFontSize = breakFontSize;
    }

    /**
     * Returns the <em>fixedColumn</em> property.
     * <p>
     * [dict_id=1203018,name=Fixed Column,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=fixed_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=11]
     * </p>
     * 
     * @return the <em>fixedColumn</em> property.
     * 
     */
    public Integer getFixedColumn() {
        return this.fixedColumn;
    }

    /**
     * Sets the <em>fixedColumn</em> property.
     * <p>
     * [dict_id=1203018,name=Fixed Column,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=fixed_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=11]
     * </p>
     * 
     * @param fixedColumn the new value of the <em>fixedColumn</em> property.
     * 
     */
    public void setFixedColumn(Integer fixedColumn) {
        this.fixedColumn = fixedColumn;
    }

    /**
     * Returns the <em>hierarchyNature</em> property.
     * <p>
     * [dict_id=1203046,name=Hierarchy Nature,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=hierarchy_nat_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=45]
     * </p>
     * 
     * @return the <em>hierarchyNature</em> property.
     * 
     */
    public Integer getHierarchyNature() {
        return this.hierarchyNature;
    }

    /**
     * Sets the <em>hierarchyNature</em> property.
     * <p>
     * [dict_id=1203046,name=Hierarchy Nature,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=hierarchy_nat_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=45]
     * </p>
     * 
     * @param hierarchyNature the new value of the <em>hierarchyNature</em> property.
     * 
     */
    public void setHierarchyNature(Integer hierarchyNature) {
        this.hierarchyNature = hierarchyNature;
    }

    /**
     * Returns the <em>displayColumn</em> property.
     * <p>
     * [dict_id=1203015,name=Display Column,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_column_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     * 
     * @return the <em>displayColumn</em> property.
     * 
     */
    public Integer getDisplayColumn() {
        return this.displayColumn;
    }

    /**
     * Sets the <em>displayColumn</em> property.
     * <p>
     * [dict_id=1203015,name=Display Column,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=display_column_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=9]
     * </p>
     * 
     * @param displayColumn the new value of the <em>displayColumn</em> property.
     * 
     */
    public void setDisplayColumn(Integer displayColumn) {
        this.displayColumn = displayColumn;
    }

    /**
     * Returns the <em>italic</em> property.
     * <p>
     * [dict_id=1203030,name=Italic,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=italic_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=23]
     * </p>
     * 
     * @return the <em>italic</em> property.
     * 
     */
    public Integer getItalic() {
        return this.italic;
    }

    /**
     * Sets the <em>italic</em> property.
     * <p>
     * [dict_id=1203030,name=Italic,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=italic_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=23]
     * </p>
     * 
     * @param italic the new value of the <em>italic</em> property.
     * 
     */
    public void setItalic(Integer italic) {
        this.italic = italic;
    }

//    /**
//     * Returns the <em>control</em> property.
//     * <p>
//     * [dict_id=1203051,name=Control,datatype=id_t,dictEntity=Format Element,referencedDictEntity=Script
//     * Definition,parentAttribute=<null>,sqlname_c=script_control,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=0,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=0]
//     * </p>
//     * 
//     * @return the <em>control</em> property.
//     * 
//     */
//    public java.util.Set<com.odcgroup.tangij.domain.ScriptDefinitionEntity> getControl() {
//        return this.control;
//    }

    /**
     * Returns the <em>edit</em> property.
     * <p>
     * [dict_id=1203033,name=Edit,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=edit_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=17]
     * </p>
     * 
     * @return the <em>edit</em> property.
     * 
     */
    public Integer getEdit() {
        return this.edit;
    }

    /**
     * Sets the <em>edit</em> property.
     * <p>
     * [dict_id=1203033,name=Edit,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=edit_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=17]
     * </p>
     * 
     * @param edit the new value of the <em>edit</em> property.
     * 
     */
    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    /**
     * Returns the <em>bold</em> property.
     * <p>
     * [dict_id=1203031,name=Bold,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=bold_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=24]
     * </p>
     * 
     * @return the <em>bold</em> property.
     * 
     */
    public Integer getBold() {
        return this.bold;
    }

    /**
     * Sets the <em>bold</em> property.
     * <p>
     * [dict_id=1203031,name=Bold,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=bold_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=24]
     * </p>
     * 
     * @param bold the new value of the <em>bold</em> property.
     * 
     */
    public void setBold(Integer bold) {
        this.bold = bold;
    }

    /**
     * Returns the <em>breakAfterLines</em> property.
     * <p>
     * [dict_id=1203045,name=Break After Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=43]
     * </p>
     * 
     * @return the <em>breakAfterLines</em> property.
     * 
     */
    public Integer getBreakAfterLines() {
        return this.breakAfterLines;
    }

    /**
     * Sets the <em>breakAfterLines</em> property.
     * <p>
     * [dict_id=1203045,name=Break After Lines,datatype=tinyint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=break_aft_lines_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=43]
     * </p>
     * 
     * @param breakAfterLines the new value of the <em>breakAfterLines</em> property.
     * 
     */
    public void setBreakAfterLines(Integer breakAfterLines) {
        this.breakAfterLines = breakAfterLines;
    }

//    /**
//     * Returns the <em>notepad</em> property.
//     * <p>
//     * [dict_id=1203050,name=Notepad,datatype=id_t,dictEntity=Format
//     * Element,referencedDictEntity=Notepad,parentAttribute=<null>,sqlname_c=notepad,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=true,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=<null>,disp_rank_n=29]
//     * </p>
//     * 
//     * @return the <em>notepad</em> property.
//     * 
//     */
//    public java.util.Set<com.odcgroup.tangij.domain.NotepadEntity> getNotepad() {
//        return this.notepad;
//    }

    /**
     * Returns the <em>breakFont</em> property.
     * <p>
     * [dict_id=1203039,name=Break Font,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=break_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=37]
     * </p>
     * 
     * @return the <em>breakFont</em> property.
     * 
     */
    public Integer getBreakFont() {
        return this.breakFont;
    }

    /**
     * Sets the <em>breakFont</em> property.
     * <p>
     * [dict_id=1203039,name=Break Font,datatype=enum_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=[dict_id=1202045,name=Block
     * Font,datatype=enum_t,dictEntity=Format,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=header_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=3325,quick_search_mask=1,search_mask=0,default_c=0,disp_rank_n=38],sqlname_c=break_font_e,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=0,search_mask=0,default_c=0,disp_rank_n=37]
     * </p>
     * 
     * @param breakFont the new value of the <em>breakFont</em> property.
     * 
     */
    public void setBreakFont(Integer breakFont) {
        this.breakFont = breakFont;
    }

    /**
     * Returns the <em>datatype</em> property.
     * <p>
     * [dict_id=1203007,name=Datatype,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Datatype,parentAttribute=<null>,sqlname_c=datatype_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE_FILTER(3),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=6]
     * </p>
     * 
     * @return the <em>datatype</em> property.
     * 
     */
    public /*com.odcgroup.tangij.domain.DatatypeEntity*/DictDataType getDatatype() {
        return this.datatype;
    }

    /**
     * Sets the <em>datatype</em> property.
     * <p>
     * [dict_id=1203007,name=Datatype,datatype=dict_t,dictEntity=Format
     * Element,referencedDictEntity=Datatype,parentAttribute=<null>,sqlname_c=datatype_dict_id,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE_FILTER(3),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=6]
     * </p>
     * 
     * @param datatype the new value of the <em>datatype</em> property.
     * 
     */
    public void setDatatype(/*com.odcgroup.tangij.domain.DatatypeEntity*/DictDataType datatype) {
        this.datatype = datatype;
    }

    /**
     * Returns the <em>columnWidth</em> property.
     * <p>
     * [dict_id=1203026,name=Column Width,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=col_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=19]
     * </p>
     * 
     * @return the <em>columnWidth</em> property.
     * 
     */
    public Integer getColumnWidth() {
        return this.columnWidth;
    }

    /**
     * Sets the <em>columnWidth</em> property.
     * <p>
     * [dict_id=1203026,name=Column Width,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=col_width_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=19]
     * </p>
     * 
     * @param columnWidth the new value of the <em>columnWidth</em> property.
     * 
     */
    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * Returns the <em>sortingRank</em> property.
     * <p>
     * [dict_id=1203022,name=Sorting Rank,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rank_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=15]
     * </p>
     * 
     * @return the <em>sortingRank</em> property.
     * 
     */
    public Integer getSortingRank() {
        return this.sortingRank;
    }

    /**
     * Sets the <em>sortingRank</em> property.
     * <p>
     * [dict_id=1203022,name=Sorting Rank,datatype=smallint_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sorting_rank_n,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=15]
     * </p>
     * 
     * @param sortingRank the new value of the <em>sortingRank</em> property.
     * 
     */
    public void setSortingRank(Integer sortingRank) {
        this.sortingRank = sortingRank;
    }

    /**
     * Returns the <em>subTotal</em> property.
     * <p>
     * [dict_id=1203020,name=Sub-Total,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sub_total_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=13]
     * </p>
     * 
     * @return the <em>subTotal</em> property.
     * 
     */
    public Integer getSubTotal() {
        return this.subTotal;
    }

    /**
     * Sets the <em>subTotal</em> property.
     * <p>
     * [dict_id=1203020,name=Sub-Total,datatype=flag_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=sub_total_f,primary_f=false,mandatory_f=true,db_mandatory_f=true,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=true,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=0,disp_rank_n=13]
     * </p>
     * 
     * @param subTotal the new value of the <em>subTotal</em> property.
     * 
     */
    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Returns the <em>foregroundColour</em> property.
     * <p>
     * [dict_id=1203029,name=Foreground Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=foregr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=22]
     * </p>
     * 
     * @return the <em>foregroundColour</em> property.
     * 
     */
    public String getForegroundColour() {
        return this.foregroundColour;
    }

    /**
     * Sets the <em>foregroundColour</em> property.
     * <p>
     * [dict_id=1203029,name=Foreground Colour,datatype=code_t,dictEntity=Format Element,referencedDictEntity=<null>,parentAttribute=<null>,sqlname_c=foregr_colour_c,primary_f=false,mandatory_f=false,db_mandatory_f=false,business_key_f=false,logical_f=false,custom_f=false,perm_val_f=false,calculated_e=PHYSICAL(0),perm_auth_f=NO_USER_PERMITTED_VALUES_ALLOWED(0),edit_e=INSERT_UPDATE(1),widget_e=DATE_ONLY(0),subtype_mask=1,quick_search_mask=1,search_mask=1,default_c=<null>,disp_rank_n=22]
     * </p>
     * 
     * @param foregroundColour the new value of the <em>foregroundColour</em> property.
     * 
     */
    public void setForegroundColour(String foregroundColour) {
        this.foregroundColour = foregroundColour;
    }
    
    @SuppressWarnings("unchecked")
    public Set<DenominationEntity> getDenoms() {
        if (denoms != null) {
            return denoms;
        } else {
            return Collections.EMPTY_SET;
        }
    }

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


    // // //-------------------------------------------------------------------------
    // // com.odcgroup.mdf.core.ReferenceableObject
    //
    // /**
    // * @see com.odcgroup.ofs.model.ReferenceableObject#getObjectId()
    // */
    // public ObjectId getObjectId() {
    // return FormatElementId.createFormatElementId(
    // // this.getIdentifier()
    //		
    // );
    // }
    //	

    private static final ToStringStyle TSS = new ToStringStyle() {
        {
            // This is the constructor of this anonymous inner classes
            setUseShortClassName(false);
            setUseClassName(false);
            setUseIdentityHashCode(false);
        }

        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
            if (value instanceof FormatEntity) {
                value = ((FormatEntity) value).getCode();
            }
            if (value instanceof FormatElementEntity) {
                value = ((FormatElementEntity) value).getSQLName();
            }
//            if (value instanceof DictAttribute) {
//                
//            }
//            if (value instanceof DictDataType) {
//                
//            }
            buffer.append(value);
        }
    };
    
    public String toString() {
        // @see http://commons.apache.org/lang/api/org/apache/commons/lang/builder/ToStringBuilder.html
        return ToStringBuilder.reflectionToString(this, TSS);
    }

	public Integer getTslMultilingual() {
		return tslMultilingual;
	}

	public void setTslMultilingual(Integer tslMultilingual) {
		this.tslMultilingual = tslMultilingual;
	}

    // MVO: Not sure a 'new' is such a good idea here?? MdfName should be immutable objects existing only ONCE,
    // shouldn't they?!
    // public MdfName getMdfName() {
    // return new MdfName("AAA", "FormatElement");
    // }

}
