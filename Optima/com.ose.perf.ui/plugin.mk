# Plugin build specification

PLUGIN_NAME = com.ose.perf.ui

PLUGIN_VERSION = 1.2.1

JAR_NAME = perfui.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

DEPS = com.ose.gateway com.ose.plugin.control com.ose.pmd com.ose.system com.ose.system.ui

XDEPS = org.eclipse.cdt.core \
        org.eclipse.cdt.debug.core \
        org.eclipse.core.commands \
        org.eclipse.core.filesystem \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.equinox.common \
        org.eclipse.equinox.registry \
        org.eclipse.jface \
        org.eclipse.jface.text \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.text \
        org.eclipse.ui \
        org.eclipse.ui.forms \
        org.eclipse.ui.ide \
        org.eclipse.ui.views \
        org.eclipse.ui.workbench \
        org.eclipse.ui.workbench.texteditor
