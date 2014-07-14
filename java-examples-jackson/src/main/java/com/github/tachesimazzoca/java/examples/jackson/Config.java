package com.github.tachesimazzoca.java.examples.jackson;

public class Config {
    private Mode mode;
    private String name;
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

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
