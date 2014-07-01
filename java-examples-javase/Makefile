all:
	@echo "make (clean|compile|checkstyle|run-httpserver)"

.PHONY: clean
clean:
	@mvn clean

.PHONY: compile
compile:
	@mvn compile

.PHONY: checkstyle
checkstyle:
	@mvn checkstyle:checkstyle

.PHONY: run-httpserver
run-httpserver:
	@mvn exec:java -Dexec.mainClass="com.github.tachesimazzoca.java.examples.se.HttpServerExample"
