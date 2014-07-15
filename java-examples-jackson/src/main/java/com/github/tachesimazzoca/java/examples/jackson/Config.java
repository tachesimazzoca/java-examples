package com.github.tachesimazzoca.java.examples.jackson;

import java.util.List;
import java.net.InetAddress;

public class Config {
    private Mode mode;
    private String name;
    private List<String> resources;
    private Server server;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public enum Mode {
        PRODUCTION, STAGING, DEVELOPMENT
    }

    public class Server {
        private int port;
        private InetAddress[] host;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public InetAddress[] getHost() {
            return host;
        }

        public void setHost(InetAddress[] host) {
            this.host = host;
        }
    }
}
