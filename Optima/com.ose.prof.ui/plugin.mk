# Plugin build specification

PLUGIN_NAME = com.ose.prof.ui

PLUGIN_VERSION = 1.7.1

JAR_NAME = profui.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons process.dtd report.dtd report.xsl

DEPS = com.ose.chart com.ose.gateway com.ose.plugin.control com.ose.pmd com.ose.system com.ose.system.ui com.ose.xmleditor

XDEPS = org.eclipse.core.commands \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.equinox.common \
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
