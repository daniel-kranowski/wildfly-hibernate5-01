package com.example.util;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.sql.SQLException;

@ApplicationScoped
public class H2Console {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2Console.class);

    /**
     * Creating an H2 TCP server allows us to remotely connect to the in-memory H2 instance, for diagnostic purposes.
     * When connecting, use the same connection-url as in our *-ds.xml, but change 'mem' to tcp://localhost:9092/mem.
     */
    public H2Console() throws SQLException {
        LOGGER.info("Starting H2 TCP Server");
        Server.createTcpServer().start();
    }

}
