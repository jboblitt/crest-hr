package edu.vt.crest.hr.rest;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // If true, provide more informative error messages in responses
    public static final boolean IS_SERVER_DEBUG_MODE = false;
}