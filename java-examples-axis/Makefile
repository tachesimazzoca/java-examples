.PHONY: client
client:
	@rm -rf target/wsdl2java
	@rm -rf client/src/main/java/com
	@mvn exec:java -Dexec.mainClass="org.apache.axis.wsdl.WSDL2Java" -Dexec.args="-p com.github.tachesimazzoca.java.examples.axis.client -o target/wsdl2java etc/hello.wsdl"
	@cp -R target/wsdl2java/com client/src/main/java/.

.PHONY: deploy-server
deploy-server:
	@mvn exec:java -Dexec.mainClass="org.apache.axis.client.AdminClient" -Dexec.args="-lhttp://localhost:9000/java-examples-axis/servlet/AxisServlet src/main/webapp/WEB-INF/deploy.wsdd"
