# Plugin build specification

PLUGIN_NAME = com.ose.chart

PLUGIN_VERSION = 1.3.1

JAR_NAME = chart.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = javax.media.opengl com.ose.system.ui

XDEPS = org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui
