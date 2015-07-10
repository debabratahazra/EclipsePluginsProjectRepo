# Plugin build specification

PLUGIN_NAME = com.ose.chart.tests

PLUGIN_VERSION = 1.2.2

JAR_NAME = chart_tests.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

DEPS = com.ose.chart

XDEPS = org.eclipse.core.commands \
        org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.equinox.registry \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui.workbench \
        org.junit
