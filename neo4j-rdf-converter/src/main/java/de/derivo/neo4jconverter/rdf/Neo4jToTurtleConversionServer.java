package de.derivo.neo4jconverter.rdf;

import de.derivo.neo4jconverter.rdf.config.ConversionConfig;
import de.derivo.neo4jconverter.util.ConsoleUtil;
import org.neo4j.kernel.impl.store.NeoStores;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Neo4jToTurtleConversionServer {

    private final int portNumber;
    private final NeoStores neoStores;
    private final Logger log = ConsoleUtil.getLogger();
    private final ConversionConfig config;

    public Neo4jToTurtleConversionServer(int portNumber, NeoStores neoStores, ConversionConfig config) {
        this.portNumber = portNumber;
        this.neoStores = neoStores;
        this.config = config;
    }

    private static int getFreePort() {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            int portNumber = serverSocket.getLocalPort();
            serverSocket.close();
            return portNumber;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not find a free port.", e);
        }
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            ExecutorService threadPool = Executors.newFixedThreadPool(1);
            log.info("Starting neo4j to RDF conversion server on port " + portNumber + "...");
            while (true) {
                Socket s = serverSocket.accept();
                log.info("New client connected: " + s);
                threadPool.submit(() -> {
                    try {
                        Neo4jDBToTurtle neo4jDBToTurtle = new Neo4jDBToTurtle(neoStores, config, s.getOutputStream());
                        neo4jDBToTurtle.startProcessing();
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
