# Plugin build specification

PLUGIN_NAME = com.ose.ui.tests

PLUGIN_VERSION = 1.3.0

JAR_NAME = tests.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = test.xml testdata testlogs

DEPS = com.ose.system com.ose.system.ui com.ose.event.ui com.ose.prof.ui

XDEPS = org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.junit \
        com.windowtester.runtime \
        com.windowtester.swt.runtime
