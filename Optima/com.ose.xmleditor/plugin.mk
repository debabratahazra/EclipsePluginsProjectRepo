# Plugin build specification

PLUGIN_NAME = com.ose.xmleditor

PLUGIN_VERSION = 1.1.1

JAR_NAME = xmleditor.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))
