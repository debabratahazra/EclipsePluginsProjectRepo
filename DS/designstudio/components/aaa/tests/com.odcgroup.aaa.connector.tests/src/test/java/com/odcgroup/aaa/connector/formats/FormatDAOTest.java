package com.odcgroup.aaa.connector.formats;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.aaa.connector.domainmodel.DenominationEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatElementEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatEntity;
import com.odcgroup.aaa.connector.internal.util.FormatVO;
import com.odcgroup.aaa.connector.internal.util.FunctionVO;
import com.odcgroup.aaa.connector.tests.JUnit4TstJPA;
import com.odcgroup.otf.jpa.test.openjpa.RememberingAllStatementJDBCListenerHelper;
import com.odcgroup.otf.jpa.utils.BootstrapJPA;
import com.odcgroup.otf.jpa.utils.CreateDB;


/**
 * Test for FormatDAO.
 * 
 * @author Michael Vorburger (MVO)
 */
public class FormatDAOTest extends JUnit4TstJPA {

	private static final int NSQL = 9; 

    public FormatDAOTest() throws IOException, SQLException {
        super();
       	connectionProperties = BootstrapJPA.getPropertiesFromClasspathResource("ta-db.properties");
        CreateDB.addNormalLoggingProperties(connectionProperties);
        persistenceUnitName = "aaa";
    }

    @Test
	public void testGetFormat() throws Exception {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        String formatCode = "PCK_DS_VALO";
        FormatDAO dao = new FormatDAO(em);
        FormatEntity format = dao.getFormat(formatCode);
        Assert.assertEquals("FormatDAO.getFormat() initially issued more (or less? good! ;) SQL statements than expected", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
        Assert.assertEquals("SELECT t0.id, t1.dict_id, t1.business_key_f, t1.calculated_e, t1.custom_f, t2.dict_id, t2.equiv_type_c, t2.name, t2.sqlname_c, t1.db_mandatory_f, t1.default_c, t1.default_display_len_n, t1.entity_dict_id, t1.disp_rank_n, t1.edit_e, t1.entity_attribute, t1.enum_attribute, t1.enum_value, t1.fk_presentation_e, t1.logical_f, t1.mandatory_f, t1.max_db_len_n, t1.multi_language_f, t1.name, t1.object_attribute, t1.parent_attribute_dict_id, t1.perm_auth_f, t1.perm_val_f, t1.primary_f, t1.quick_search_mask, t1.ref_entity_attribute_dict_id, t1.ref_entity_dict_id, t1.search_mask, t1.short_index_n, t1.sqlname_c, t1.subtype_mask, t1.tasc_view_e, t1.widget_e, t1.xd_status_e, t0.backgr_colour_c, t0.bold_f, t0.break_aft_lines_n, t0.break_bef_lines_n, t0.break_font_e, t0.break_font_size_n, t0.break_frame_e, t0.break_gray_force_n, t0.break_thick_n, t0.separator_e, t0.col_width_n, t0.cons_f, t0.curr_map_fmt_elt_id, t3.dict_id, t3.equiv_type_c, t3.name, t3.sqlname_c, t0.denom, t0.display_column_n, t0.display_context_e, t0.display_format_c, t0.display_row_n, t0.edit_f, t0.fixed_f, t0.font_e, t0.font_size_n, t0.foregr_colour_c, t0.format_id, t0.gray_force_n, t0.hierarchy_nat_e, t0.horiz_coord_n, t0.italic_f, t0.justification_e, t0.multi_line_n, t0.name, t0.rank_n, t0.sorting_rank_n, t0.sorting_rule_e, t0.sqlname_c, t0.sub_total_f, t0.totaliser_c, t0.tsl_multilingual_e, t0.vert_coord_n, t0.zoom_f FROM format_element_vw t0 LEFT OUTER JOIN dict_attribute_vw t1 ON t0.attribute_dict_id = t1.dict_id INNER JOIN dict_datatype_vw t3 ON t0.datatype_dict_id = t3.dict_id LEFT OUTER JOIN dict_datatype_vw t2 ON t1.datatype_dict_id = t2.dict_id WHERE t0.format_id = ?", RememberingAllStatementJDBCListenerHelper.getSQL(em, 5));
        Assert.assertEquals("SELECT t0.id, t0.after_lines_n, t0.beside_col_n, t0.blank_lines_n, t0.header_font_e, t0.header_font_size_n, t0.border_thick_n, t0.break_criteria_name, t0.break_value_c, t0.code, t0.header_aft_lines_n, t0.header_bef_lines_n, t0.header_frame_e, t0.header_gray_force_n, t0.header_thick_n, t0.denom, t1.dict_id, t1.logical_f, t1.name, t1.nature_e, t1.sqlname_c, t1.xd_status_e, t0.filter_c, t2.dict_id, t2.name, t2.proc_name, t0.graph_3d_f, t0.graph_disp_perc_f, t0.graph_disp_val_f, t0.graph_explode_e, t0.graph_height_n, t0.graph_horz_grid_f, t0.graph_legend_pos_e, t0.graph_pattern_e, t0.graph_type_e, t0.graph_vert_grid_f, t0.graph_view_e, t0.graph_width_n, t0.gray_force_n, t0.gray_lines_n, t0.icon_name, t0.justif_e, t0.level_num_n, t0.matrix_horz_thick_n, t0.matrix_vert_thick_n, t0.max_break_n, t0.modif_d, t0.name, t0.nature_e, t0.overlap_n, t0.risk_f, t0.title_aft_lines_n, t0.title_font_e, t0.title_font_size_n, t0.title_frame_e, t0.title_gray_force_n, t0.title_justif_e, t0.title_thick_n, t0.total_aft_lines_n, t0.total_bef_lines_n, t0.total_font_e, t0.total_font_size_n, t0.total_frame_e, t0.total_gray_force_n, t0.total_thick_n, t0.type_id FROM format_vw t0 INNER JOIN dict_entity_vw t1 ON t0.entity_dict_id = t1.dict_id INNER JOIN dict_function_vw t2 ON t0.function_dict_id = t2.dict_id WHERE (t0.code = ?)", RememberingAllStatementJDBCListenerHelper.getSQL(em, 7));

        Assert.assertEquals(formatCode, format.getCode());
        Assert.assertTrue(format.getEntity().getSQLName() != null);
        
        Assert.assertNotNull(format.getFunction());
        Assert.assertNotNull(format.getFunction().getName());
        Assert.assertNotNull(format.getFunction().getProcName());
        
        Set<DenominationEntity> formatDenoms = format.getDenoms();
        // NAH Assert.assertTrue("No denominations for this format?!", !formatDenoms.isEmpty());
        for (DenominationEntity denom : formatDenoms) {
            System.out.println("\tin " + denom.getLanguage().getCode() + " = " + denom.getDenom());
        }
        
        boolean scriptDefinitionFound = false;
        System.out.println("\n\n" + format.getCode());
        for (FormatElementEntity element : format.getFormatElements()) {
            Assert.assertNotNull(element.getSQLName());
            Assert.assertNotNull(element.getDatatype());
            
            Assert.assertNotNull(element.getScriptDefinition());
            System.out.println("\n\t" + element.getSQLName()+ " ScriptDefinition:\t" + element.getScriptDefinition());
            if (element.getScriptDefinition().length() > 0) {
            	scriptDefinitionFound = true;
            }
            
            Assert.assertNotNull(element.getCellFmtDefinition());
            System.out.println("\n\t" + element.getSQLName()+ " CellFmtDefinition:\t" + element.getCellFmtDefinition());
            
            Set<DenominationEntity> elementDenoms = element.getDenoms();
            for (DenominationEntity denom : elementDenoms) {
                System.out.println("\tin " + denom.getLanguage().getCode() + " = " + denom.getDenom());
            }
        }
        
        FormatElementEntity element = null;
        for (FormatElementEntity e : format.getFormatElements()) {
            if ("id".equals(e.getSQLName())) {
            	element = e;
            	break;
            }
        }
        Assert.assertNotNull(element);
        
        Assert.assertTrue("No Script Definition at all found... did the attribute_dict_id change again?! (@see DS-4696 & DS-6645)", scriptDefinitionFound);
        
      /*  Assert.assertEquals("ref_net_amount_m", element.getScriptDefinition());
        Assert.assertEquals("CELL_FORMAT(\"net_cost_value\",,,0,FALSE,,,2)", element.getCellFmtDefinition());
        
        Assert.assertEquals("Although FormatDAO.getFormat() seemed OK, there seems to have been (unexpected!) lazy loading occuring, because there are too many total SQL statements at the end", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));*/
    }
    
    @Test
	public void testGetFormats() {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        FormatDAO dao = new FormatDAO(em);
        List<FormatEntity> formats = dao.getFormats(new FormatCriteria("PCK_*"));
        Assert.assertEquals("FormatDAO.getFormatS() initially issued more (or less? good! ;) SQL statements than expected", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
        for (int i=0; i<NSQL; i++) {
            System.out.println("getSQL("+i+"):" + RememberingAllStatementJDBCListenerHelper.getSQL(em, i));
        }
        Assert.assertEquals("SELECT t0.id, t1.id, t2.dict_id, t2.business_key_f, t2.calculated_e, t2.custom_f, t3.dict_id, t3.equiv_type_c, t3.name, t3.sqlname_c, t2.db_mandatory_f, t2.default_c, t2.default_display_len_n, t2.entity_dict_id, t2.disp_rank_n, t2.edit_e, t2.entity_attribute, t2.enum_attribute, t2.enum_value, t2.fk_presentation_e, t2.logical_f, t2.mandatory_f, t2.max_db_len_n, t2.multi_language_f, t2.name, t2.object_attribute, t2.parent_attribute_dict_id, t2.perm_auth_f, t2.perm_val_f, t2.primary_f, t2.quick_search_mask, t2.ref_entity_attribute_dict_id, t2.ref_entity_dict_id, t2.search_mask, t2.short_index_n, t2.sqlname_c, t2.subtype_mask, t2.tasc_view_e, t2.widget_e, t2.xd_status_e, t1.backgr_colour_c, t1.bold_f, t1.break_aft_lines_n, t1.break_bef_lines_n, t1.break_font_e, t1.break_font_size_n, t1.break_frame_e, t1.break_gray_force_n, t1.break_thick_n, t1.separator_e, t1.col_width_n, t1.cons_f, t1.curr_map_fmt_elt_id, t4.dict_id, t4.equiv_type_c, t4.name, t4.sqlname_c, t1.denom, t1.display_column_n, t1.display_context_e, t1.display_format_c, t1.display_row_n, t1.edit_f, t1.fixed_f, t1.font_e, t1.font_size_n, t1.foregr_colour_c, t1.format_id, t1.gray_force_n, t1.hierarchy_nat_e, t1.horiz_coord_n, t1.italic_f, t1.justification_e, t1.multi_line_n, t1.name, t1.rank_n, t1.sorting_rank_n, t1.sorting_rule_e, t1.sqlname_c, t1.sub_total_f, t1.totaliser_c, t1.tsl_multilingual_e, t1.vert_coord_n, t1.zoom_f FROM format_vw t0 INNER JOIN format_element_vw t1 ON t0.id = t1.format_id LEFT OUTER JOIN dict_attribute_vw t2 ON t1.attribute_dict_id = t2.dict_id INNER JOIN dict_datatype_vw t4 ON t1.datatype_dict_id = t4.dict_id LEFT OUTER JOIN dict_datatype_vw t3 ON t2.datatype_dict_id = t3.dict_id WHERE (t0.code LIKE ? ESCAPE '\\') ORDER BY t0.id ASC", RememberingAllStatementJDBCListenerHelper.getSQL(em, 6));
        Assert.assertEquals("SELECT t0.id, t0.after_lines_n, t0.beside_col_n, t0.blank_lines_n, t0.header_font_e, t0.header_font_size_n, t0.border_thick_n, t0.break_criteria_name, t0.break_value_c, t0.code, t0.header_aft_lines_n, t0.header_bef_lines_n, t0.header_frame_e, t0.header_gray_force_n, t0.header_thick_n, t0.denom, t1.dict_id, t1.logical_f, t1.name, t1.nature_e, t1.sqlname_c, t1.xd_status_e, t0.filter_c, t2.dict_id, t2.name, t2.proc_name, t0.graph_3d_f, t0.graph_disp_perc_f, t0.graph_disp_val_f, t0.graph_explode_e, t0.graph_height_n, t0.graph_horz_grid_f, t0.graph_legend_pos_e, t0.graph_pattern_e, t0.graph_type_e, t0.graph_vert_grid_f, t0.graph_view_e, t0.graph_width_n, t0.gray_force_n, t0.gray_lines_n, t0.icon_name, t0.justif_e, t0.level_num_n, t0.matrix_horz_thick_n, t0.matrix_vert_thick_n, t0.max_break_n, t0.modif_d, t0.name, t0.nature_e, t0.overlap_n, t0.risk_f, t0.title_aft_lines_n, t0.title_font_e, t0.title_font_size_n, t0.title_frame_e, t0.title_gray_force_n, t0.title_justif_e, t0.title_thick_n, t0.total_aft_lines_n, t0.total_bef_lines_n, t0.total_font_e, t0.total_font_size_n, t0.total_frame_e, t0.total_gray_force_n, t0.total_thick_n, t0.type_id FROM format_vw t0 INNER JOIN dict_entity_vw t1 ON t0.entity_dict_id = t1.dict_id INNER JOIN dict_function_vw t2 ON t0.function_dict_id = t2.dict_id WHERE (t0.code LIKE ? ESCAPE '\\')", RememberingAllStatementJDBCListenerHelper.getSQL(em, 7));
        
        Assert.assertTrue("So few PCK_* formats... only " + formats.size(), formats.size() > 100);
        Assert.assertTrue("This Format's code doesn't actually start with PCK_: " + formats.get(0).getCode(), formats.get(0).getCode().startsWith("PCK_"));
        
        Assert.assertEquals("Although FormatDAO.getFormatS() seemed OK, there seems to have been (unexpected!) lazy loading occuring, because there are too many total SQL statements at the end", NSQL, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
    }
    
    @Test
	public void testGetFormatTypes() {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        FormatDAO dao = new FormatDAO(em);
        dao.getFormatTypes();
        Assert.assertEquals("FormatDAO.getFormatTypes() issued more (or less? good! ;) SQL statements than expected", 1, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
        Assert.assertEquals("FormatDAO.getFormatTypes() issued unexpected SQL", "SELECT t0.id, t0.attribute_dict_id, t0.autocreated_f, t0.code, t0.denom, t0.name, t0.parent_type_id, t0.rank_n FROM type_vw t0 WHERE (t0.attribute_dict_id = ?) ORDER BY t0.rank_n ASC", RememberingAllStatementJDBCListenerHelper.getSQL(em, 0));
    }
    
    @Test
    @Ignore
    public void xtestGetSelectedFormats() {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        FormatDAO dao = new FormatDAO(em);
        Set<String> codes = new HashSet<String>();
        codes.add("DEF_VALO_CATEG");
        codes.add("DEF_VALO_DOMAIN");
        FormatCriteria criteria = new FormatCriteria(codes);
        List<FormatEntity> result = dao.getFormats(criteria);
        Assert.assertEquals("Expected two formats", 2, result.size());
        Assert.assertTrue("Expected to find selected code in the returned formats", codes.contains(result.get(0).getCode()));
        Assert.assertTrue("Expected to find selected code in the returned formats", codes.contains(result.get(1).getCode()));
    }
    
    @Test
    @Ignore
    public void xtestGetAllSelectedFormats() {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        FormatDAO dao = new FormatDAO(em);
        List<FormatVO> formats = dao.getAllFormatCodes();
        Set<String> codes = new HashSet<String>();
        for (FormatVO format : formats) {
        	codes.add(format.getCode());
        }
        FormatCriteria criteria = new FormatCriteria(codes);
        long now = System.currentTimeMillis();
        List<FormatEntity> result = dao.getFormats(criteria);
        System.out.println("Duration : " + (System.currentTimeMillis()-now) + " ms, seconds :" + (System.currentTimeMillis()-now)/1000);
        Assert.assertEquals("Expected number of formats", codes.size(), result.size());
    }
    
    @Test
    @Ignore
    public void xtestGetAllFunctions() {
    	RememberingAllStatementJDBCListenerHelper.resetSQLQueries(em);
        FormatDAO dao = new FormatDAO(em);
        List<FunctionVO> functions = dao.getAllFunctions();
        Assert.assertEquals("FormatDAO.getAllFunctions() issued more SQL statements than expected", 1, RememberingAllStatementJDBCListenerHelper.countSQLQueries(em));
        Assert.assertTrue("At least one function should exist", functions.size() > 0);
    	for (FunctionVO function : functions) {
    		System.out.println("Function found : " + function.getCamelCaseProcNameFunction() + "\t" + function.getProcNameFunction());
    	}
    }
}

