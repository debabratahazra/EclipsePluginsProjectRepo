# Plugin build specification

PLUGIN_NAME = com.ose.ui.tests.linux

PLUGIN_VERSION = 1.0.0

JAR_NAME = linuxtests.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.system com.ose.system.ui com.ose.event.ui com.ose.prof.ui com.ose.ui.tests

XDEPS = org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.junit \
        org.eclipse.tm.terminal \
        com.windowtester.runtime \
        com.windowtester.swt.runtime
