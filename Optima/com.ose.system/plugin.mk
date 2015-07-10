# Plugin build specification

PLUGIN_NAME = com.ose.system

PLUGIN_VERSION = 1.9.0

JAR_NAME = system.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.gateway

XDEPS = org.eclipse.core.runtime org.eclipse.equinox.common org.eclipse.osgi
