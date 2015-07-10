# Plugin build specification

PLUGIN_NAME = com.ose.pmd.ui

PLUGIN_VERSION = 1.3.1

JAR_NAME = pmdui.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons blockFormatters.exsd

DEPS = com.ose.pmd com.ose.system com.ose.system.ui

XDEPS = org.eclipse.core.commands \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
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
