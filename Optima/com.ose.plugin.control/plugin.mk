# Plugin build specification

PLUGIN_NAME = com.ose.plugin.control

PLUGIN_VERSION = 1.4.2

JAR_NAME = control.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = lm.jar

IDEPS = lm.jar

XDEPS = org.eclipse.core.runtime org.eclipse.osgi
