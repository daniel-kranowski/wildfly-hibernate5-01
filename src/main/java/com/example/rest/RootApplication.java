package com.example.rest;

import com.example.util.H2Console;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * The RootApplication is initialized on the first restful request received.
 *
 * A class with the ApplicationPath is required for Resteasy endpoint routing.
 */
@ApplicationPath("/")
public class RootApplication extends Application {

    /**
     * The tcp server console offers external access to the in-memory H2 instance.
     */
    @Inject
    private H2Console h2Console;

}
