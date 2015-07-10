# Plugin build specification

PLUGIN_NAME = com.ose.gateway.serial

PLUGIN_VERSION = 1.1.0

JAR_NAME = gateway.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = icons

SOURCE_CODE = true

DEPS = com.ose.gateway com.ose.system gnu.io.rxtx

XDEPS = org.eclipse.core.runtime \
        org.eclipse.debug.core \
        org.eclipse.debug.ui \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.osgi \
        org.eclipse.swt.gtk.linux.x86 \
        org.eclipse.ui \
        org.eclipse.ui.workbench
