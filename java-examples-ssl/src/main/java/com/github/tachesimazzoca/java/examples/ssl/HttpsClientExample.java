package com.github.tachesimazzoca.java.examples.ssl;

import org.apache.commons.io.IOUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.FileInputStream;
import java.net.URL;
import java.security.Provider;
import java.security.Security;

public class HttpsClientExample {
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

            System.out.println("-- Create HttpsURLConnection");
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    System.out.println("Protocol: " + sslSession.getProtocol());
                    System.out.println("CipherSuite: " + sslSession.getCipherSuite());
                    return true;
                }
            });
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL("https://localhost:" + SERVER_PORT);
            System.out.println(IOUtils.toString(url.openStream()));

        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
