# Plugin build specification

PLUGIN_NAME = com.ose.event.ui

PLUGIN_VERSION = 1.6.0

JAR_NAME = eventui.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons eventaction.dtd event.dtd event.xsl

DEPS = com.ose.gateway com.ose.plugin.control com.ose.pmd com.ose.sigdb com.ose.system com.ose.system.ui com.ose.xmleditor

XDEPS = org.eclipse.core.commands \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.equinox.preferences \
        org.eclipse.equinox.registry \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.text \
        org.eclipse.ui \
        org.eclipse.ui.forms \
        org.eclipse.ui.ide \
        org.eclipse.ui.views \
        org.eclipse.ui.workbench \
        org.eclipse.ui.workbench.texteditor
