# Plugin build specification

PLUGIN_NAME = com.ose.httpd

PLUGIN_VERSION = 1.1.0

JAR_NAME = httpd.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))
