# Plugin build specification

PLUGIN_NAME = com.ose.cdt.debug

PLUGIN_VERSION = 1.7.4

JAR_NAME = debug.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.launch com.ose.system com.ose.plugin.control

XDEPS = org.eclipse.cdt.core \
        org.eclipse.cdt.debug.core \
        org.eclipse.cdt.debug.mi.core \
        org.eclipse.cdt.debug.ui \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.equinox.common \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui.workbench
