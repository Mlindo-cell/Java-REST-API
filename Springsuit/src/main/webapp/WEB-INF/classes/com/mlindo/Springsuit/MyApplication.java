package com.mlindo.Springsuit;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
        packages("com.mlindo.Springsuit.resources");
    }
}
