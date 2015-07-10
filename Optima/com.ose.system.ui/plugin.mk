# Plugin build specification

PLUGIN_NAME = com.ose.system.ui

PLUGIN_VERSION = 1.9.2

JAR_NAME = systembrowser.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

DEPS = com.ose.httpd com.ose.system com.ose.plugin.control

XDEPS = com.ibm.icu \
        org.eclipse.birt.chart.device.extension \
        org.eclipse.birt.chart.engine \
        org.eclipse.birt.core \
        org.eclipse.core.commands \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.emf.common \
        org.eclipse.emf.ecore \
        org.eclipse.equinox.common \
        org.eclipse.equinox.preferences \
        org.eclipse.equinox.registry \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui \
        org.eclipse.ui.forms \
        org.eclipse.ui.views \
        org.eclipse.ui.workbench \
        org.mozilla.rhino
