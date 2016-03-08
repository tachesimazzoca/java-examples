package com.github.tachesimazzoca.java.examples.ssl;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.Provider;
import java.security.Security;
import java.util.List;
import java.util.Map;

public class HttpsServerExample {
    private static final int SERVER_PORT = 8443;

    public static void main(String args[]) {
        for (Provider x : Security.getProviders()) {
            System.out.println(x.getName());
            for (Provider.Service y : x.getServices()) {
                System.out.println("- " + y.getAlgorithm());
            }
        }

        try {
            SSLContext sslContext = SSLContextUtils.createSSLContext(
                    new FileInputStream(args[0]), args[1],
                    new FileInputStream(args[0]), args[1]);

            final HttpsServer server = HttpsServer.create(
                    new InetSocketAddress(SERVER_PORT), 0);
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                @Override
                public void configure(HttpsParameters params) {
                    params.setProtocols(new String[]{"TLSv1.1", "TLSv1.2"});
                    params.setNeedClientAuth(false);
                }
            });

            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange httpExchange) throws IOException {
                    Headers headers = httpExchange.getRequestHeaders();
                    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                        for (String value : entry.getValue()) {
                            System.out.println(entry.getKey() + ":" + value);
                        }
                    }
                    String body = "OK";
                    httpExchange.sendResponseHeaders(200, body.length());
                    OutputStream out = httpExchange.getResponseBody();
                    IOUtils.write(body, out);
                }
            });

            server.setExecutor(null);
            server.start();
            System.out.println("-- Server started: " + server.getAddress());

            //server.stop(0);
            //System.out.println("-- Server stopped");

        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
