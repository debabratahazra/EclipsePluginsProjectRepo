# Plugin build specification

PLUGIN_NAME = com.ose.fmd.cdt.cdi.gdb

PLUGIN_VERSION = 1.0.1

JAR_NAME = fmd.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

DEPS = com.ose.fmm com.ose.plugin.control

XDEPS = org.eclipse.cdt.core \
        org.eclipse.cdt.debug.core \
        org.eclipse.cdt.debug.mi.core \
        org.eclipse.core.commands \
        org.eclipse.core.expressions \
        org.eclipse.core.jobs \
        org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui.workbench
