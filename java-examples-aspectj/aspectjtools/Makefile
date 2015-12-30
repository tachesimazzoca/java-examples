.PHONY: all 
all:
	@rm -rf target
	@mkdir target
	@ajc -1.5 -argfile example/example.lst -outjar target/app.jar
	@ajc -1.5 -inpath target/app.jar -argfile example/aspect.lst -outjar target/aspect.jar
	@ajc -1.5 -inpath target/app.jar -aspectpath target/aspect.jar -outjar target/aspectapp.jar

.PHONY: run
run:
	@java -cp ${ASPECTJ_HOME}/lib/aspectjrt.jar:target/aspectapp.jar:target/aspect.jar example.Main

.PHONY: clean 
clean:
	@rm -rf target

