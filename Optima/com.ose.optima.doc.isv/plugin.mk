# Plugin build specification

PLUGIN_NAME = com.ose.optima.doc.isv

PLUGIN_VERSION = 1.1.1

RSRCS = toc.xml doc.zip

DEPS = com.ose.plugin.control com.ose.sigdb

XDEPS = org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui.ide \
        org.eclipse.ui.workbench

DOC_SOURCEPATH = -sourcepath $(ECLIPSEROOT)/com.ose.gateway/src:$(ECLIPSEROOT)/com.ose.system/src:$(ECLIPSEROOT)/com.ose.event.ui/src:$(ECLIPSEROOT)/com.ose.prof.ui/src:$(ECLIPSEROOT)/com.ose.pmd/src

DOC_PACKAGES = com.ose.gateway com.ose.gateway.server com.ose.system com.ose.event.format com.ose.prof.format com.ose.pmd.editor

build-doc: doc.zip toc.xml doc/doc-check

doc.zip:
	$(VERB)$(RM) doc
	$(VERB)$(JAVADOC) $(CLASSPATH) $(DOC_SOURCEPATH) -quiet -d doc/topics -use -splitIndex -breakiterator -doctitle "Optima API Specification" $(DOC_PACKAGES)
	$(VERB)$(JAR) cf0M doc.zip -C doc topics  -C src .

toc.xml:
	$(VERB)$(JAVADOC) $(CLASSPATH) $(DOC_SOURCEPATH) -quiet -docletpath $(ECLIPSEROOT)/archive/TOCNavDoclet.jar -doclet com.ibm.malup.doclet.config.TOCDoclet -d doc -notree -pluginid $(PLUGIN_NAME) -plugintitle "Enea&#x00AE; Optima Plug-in Developer Guide" -version $(PLUGIN_VERSION) -provider "Enea Software AB" $(DOC_PACKAGES)
	$(VERB)$(GREP) -v '</toc>' doc/$(PLUGIN_NAME).toc.xml | $(SED) 's/label="Overview"/label="API Specification"/' > toc.xml
	$(VERB)$(ECHO) "" >> toc.xml
	$(VERB)$(ECHO) "   <topic label=\"Extension Points\" href=\"schema_overview.html\">" >> toc.xml
	$(VERB)$(ECHO) "      <topic label=\"com.ose.pmd.ui.blockFormatters\" href=\"schema_com-ose-pmd-ui-blockFormatters.html\"/>" >> toc.xml
	$(VERB)$(ECHO) "   </topic>" >> toc.xml
	$(VERB)$(ECHO) "" >> toc.xml
	$(VERB)$(ECHO) "   <topic label=\"Action Contributions\" href=\"action_contributions.html\"/>" >> toc.xml
	$(VERB)$(ECHO) "</toc>" >> toc.xml

doc/doc-check:
	$(VERB)$(MKDIR) $@
	$(VERB)$(JAVADOC) $(CLASSPATH) $(DOC_SOURCEPATH) -quiet -docletpath $(ECLIPSEROOT)/archive/doccheck.jar -doclet com.sun.tools.doclets.doccheck.DocCheck -d $@ -execDepth 2 $(DOC_PACKAGES)

clean-doc:
	$(VERB)$(RM) doc
	$(VERB)$(RM) doc.zip
	$(VERB)$(RM) toc.xml
	$(VERB)$(RM) book-optima-plugin-devguide

docbook:
	$(VERB)$(MKDIR) book-optima-plugin-devguide
	$(VERB)$(JAVADOC) $(CLASSPATH) $(DOC_SOURCEPATH) -docletpath $(ECLIPSEROOT)/archive/dbdoclet.jar -doclet org.dbdoclet.doclet.docbook.DocBookDoclet -d book-optima-plugin-devguide  -doctitle "Optima API Specification" $(DOC_PACKAGES)
	$(VERB)$(MKDIR) $(ECLIPSEROOT)/../tmp
	$(VERB)$(MKDIR) $(ECLIPSEROOT)/../tmp/japi
	$(CP) -R book-optima-plugin-devguide $(ECLIPSEROOT)/../tmp/japi
