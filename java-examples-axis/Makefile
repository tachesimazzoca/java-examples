.PHONY: wsdl2java
wsdl2java:
	@rm -rf target/wsdl2java
	@mvn exec:java -Dexec.mainClass="org.apache.axis.wsdl.WSDL2Java" -Dexec.args="-p com.github.tachesimazzoca.java.examples.axis.client -o target/wsdl2java etc/hello.wsdl"

.PHONY: deploy-server
deploy-server:
	@mvn exec:java -Dexec.mainClass="org.apache.axis.client.AdminClient" -Dexec.args="-lhttp://localhost:9000/java-examples-axis/servlet/AxisServlet src/main/webapp/WEB-INF/deploy.wsdd"
