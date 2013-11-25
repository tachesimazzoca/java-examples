all:
	@echo "make (compile|checkstyle|exec-httpserver)"

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
	@mvn exec:java -Dexec.mainClass="com.github.tachesimazzoca.java.example.HttpServerExample"
