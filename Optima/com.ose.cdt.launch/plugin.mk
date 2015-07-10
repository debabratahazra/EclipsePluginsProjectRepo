# Plugin build specification

PLUGIN_NAME = com.ose.cdt.launch

PLUGIN_VERSION = 1.9.2

JAR_NAME = launch.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

DEPS = com.ose.boot \
       com.ose.gateway \
       com.ose.httpd \
       com.ose.launch \
       com.ose.system \
       com.ose.cdt.debug \
       com.ose.pmd \
       com.ose.plugin.control

XDEPS = org.eclipse.cdt.core \
        org.eclipse.cdt.debug.core \
        org.eclipse.cdt.debug.mi.core \
        org.eclipse.cdt.launch \
        org.eclipse.core.jobs \
        org.eclipse.core.resources \
        org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui.ide \
        org.eclipse.ui.workbench
