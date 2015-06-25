.PHONY: client
client:
	@rm -rf target/jaxws
	@mkdir -p target/jaxws/src
	@mkdir -p target/jaxws/bin
	@wsimport -target 2.1 -clientjar java-examples-jaxws-client.jar -p com.github.tachesimazzoca.java.examples.jaxws.client -s target/jaxws/src -d target/jaxws/bin http://localhost:9000/java-examples-jaxws/hello.ws?wsdl
	@cp target/jaxws/bin/java-examples-jaxws-client.jar lib/.

