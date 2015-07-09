package com.odcgroup.page.transformmodel.table;

/**
 * Provide the XML script of the navigation bar below the table/tree
 * 
 * @author atr
 */
public final class TableTreeNavigationBar {

	/** */
	private static final String NAVIGATION_BAR =
			"<xgui:hbox class=\"tablefooter\">"
			+ "<xgui:label nowrap=\"true\">"
			+ 	"<xgui:text><i18n:text>general.results</i18n:text>&amp;#160;<xsp:expr><udp:first-visible-index/>+1</xsp:expr>&amp;#160;<i18n:text>general.to</i18n:text>&amp;#160;<xsp:expr><udp:last-visible-index/>+1</xsp:expr>&amp;#160;<i18n:text>general.of</i18n:text>&amp;#160;<udp:grouped-row-count/>&amp;#160;</xgui:text>"
			+ 	"<xsp:logic>"
			+ 		"String params = \" \";"
			+ 		"if (!<scope:get-property key=\"infra.module.params\" name=\"value\" defaultValue=\" \"/>.equals(\" \")) {"
			+ 			"params = (String)<scope:get-property key=\"infra.module.params\" name=\"value\" defaultValue=\" \"/>;"
			+ 		"}"
			+ 		"String widget = \" \";"
			+ 		"if (!<scope:get-property key=\"infra.module.widget\" name=\"value\" defaultValue=\" \"/>.equals(\" \")) {"
			+ 			"widget = (String)<scope:get-property key=\"infra.module.widget\" name=\"value\" defaultValue=\" \"/>;"
			+ 		"}"
			+ 	"</xsp:logic>"
			+ "</xgui:label>"
			+ "<xsp:logic>"
			+ 	"if(<udp:page-count/> > 1){"
			//+ 		"<!-- Pagination footer: Appears only if the result has more than 1 page.-->"
			+ 		"<xsp:content>"
			+ 			"<xgui:button>"
			+ 				"<xsp:attribute name=\"id\">btn_tid.nav.first</xsp:attribute>"
			+ 				"<xsp:logic>if(<udp:current-page/> == <udp:first-page/>){<xsp:attribute name=\"enabled\">false</xsp:attribute>}</xsp:logic>"
			+ 				"<xgui:onevent>"
			+					"<xgui:paginate><xsp:attribute name=\"page\"><udp:first-page/></xsp:attribute></xgui:paginate>"
			+ 				"</xgui:onevent>"
			+ 				"<xgui:text><i18n:text>general.nav.first</i18n:text></xgui:text>"
			+ 				"<xgui:tooltip><i18n:text>general.nav.first.text</i18n:text></xgui:tooltip>"
			+ 			"</xgui:button>"
			+ 			"<xgui:button>"
			+ 				"<xsp:attribute name=\"id\">btn_tid.nav.prev</xsp:attribute>"
			+ 				"<xsp:logic>if(<udp:has-previous-page/> == false){<xsp:attribute name=\"enabled\">false</xsp:attribute>}</xsp:logic>"
			+ 				"<xgui:onevent>"
			+					"<xgui:paginate><xsp:attribute name=\"page\"><udp:previous-page/></xsp:attribute></xgui:paginate>"
			+ 				"</xgui:onevent>"
			+ 				"<xgui:text><i18n:text>general.nav.prev</i18n:text></xgui:text>"
			+ 				"<xgui:tooltip><i18n:text>general.nav.prev.text</i18n:text></xgui:tooltip>"
			+ 			"</xgui:button>"
			+ 			"<xgui:label><xgui:text>&amp;#160;&amp;#160;&amp;#160;<i18n:text>general.page</i18n:text>&amp;#160;</xgui:text></xgui:label>"
			+ 			"<xsp:logic>if(<udp:last-page/> &gt; 3){</xsp:logic>"
			+ 			"<xgui:textfield columns=\"4\">"
			+ 				"<xsp:attribute name=\"id\">tid.nav.page</xsp:attribute>"
			+ 				"<xgui:value><udp:current-page/></xgui:value>"
			+ 				"<xgui:tooltip><i18n:text>general.page</i18n:text></xgui:tooltip>"
			+ 				"<xgui:onevent type=\"focus\">"
			+ 					"<xgui:clearWidget/>"
			+ 				"</xgui:onevent>"
			+ 				"<xgui:onevent type=\"change\">"
			+ 					"<xgui:checkNumber>"
			+ 						"<xsp:attribute name=\"min\"><udp:first-page/></xsp:attribute>"
			+ 						"<xsp:attribute name=\"max\"><udp:last-page/></xsp:attribute>"
			+ 					"</xgui:checkNumber>"
			+                   "<xgui:paginate><xsp:attribute name=\"page\">this.value</xsp:attribute></xgui:paginate>"
			+ 				"</xgui:onevent>"
			+ 			"</xgui:textfield>"
			+ 			"<xsp:logic>}else{</xsp:logic>"
			+ 			"<xgui:label><xgui:text><udp:current-page/></xgui:text></xgui:label>"
			+ 			"<xsp:logic>}</xsp:logic>"
			+ 			"<xgui:label>"
			+ 				"<xgui:text>&amp;#160;<i18n:text>general.of</i18n:text>&amp;#160;<udp:last-page/>&amp;#160;&amp;#160;&amp;#160;</xgui:text>"
			+ 			"</xgui:label>"
			+			"<xgui:button>"
			+ 				"<xsp:attribute name=\"id\">btn_tid.nav.next</xsp:attribute>"
			+ 				"<xsp:logic>if(<udp:has-next-page/> == false){<xsp:attribute name=\"enabled\">false</xsp:attribute>}</xsp:logic>"
			+ 				"<xgui:onevent>"
			+					"<xgui:paginate><xsp:attribute name=\"page\"><udp:next-page/></xsp:attribute></xgui:paginate>"
			+ 				"</xgui:onevent>"
			+ 				"<xgui:text><i18n:text>general.nav.next</i18n:text></xgui:text>"
			+ 				"<xgui:tooltip><i18n:text>general.nav.next.text</i18n:text></xgui:tooltip>"
			+ 			"</xgui:button>"
			+ 			"<xgui:button>"
			+ 				"<xsp:attribute name=\"id\">btn_tid.nav.last</xsp:attribute>"
			+ 				"<xsp:logic>if(<udp:current-page/> == <udp:last-page/>){<xsp:attribute name=\"enabled\">false</xsp:attribute>}</xsp:logic>"
			+ 				"<xgui:onevent>"
			+					"<xgui:paginate><xsp:attribute name=\"page\"><udp:last-page/></xsp:attribute></xgui:paginate>"
			+ 				"</xgui:onevent>"
			+ 				"<xgui:text><i18n:text>general.nav.last</i18n:text></xgui:text>"
			+ 				"<xgui:tooltip><i18n:text>general.nav.last.text</i18n:text></xgui:tooltip>"
			+ 			"</xgui:button>"
			+ 		"</xsp:content>"
			+ 	"}" 
			+ 	"</xsp:logic>"
			+ "</xgui:hbox>";
	
	/**
	 * @return the navigation bar
	 */
	public static String getNavigationBarScript() {
		return NAVIGATION_BAR;
	}

}
