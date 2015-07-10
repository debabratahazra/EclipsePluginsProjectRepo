# Plugin build specification

PLUGIN_NAME = com.ose.system.tests

PLUGIN_VERSION = 1.3.2

JAR_NAME = systemtests.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.system com.ose.gateway

XDEPS = org.eclipse.equinox.common org.junit
