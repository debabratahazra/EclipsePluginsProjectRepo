# Plugin build specification

PLUGIN_NAME = com.ose.gateway

PLUGIN_VERSION = 1.3.3

JAR_NAME = gateway.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))
