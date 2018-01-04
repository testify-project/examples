/*
 * Copyright 2016-2017 Testify Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package examples;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

/**
 * Greetings Main Application.
 *
 * @author saden
 */
public class GreetingsMain {
    // Base URI the Grizzly HTTP server will listen on

    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Main method.
     *
     * @param args application arguments
     * @throws IOException thrown in the vent of IOException.
     */
    public static void main(String[] args) throws IOException {
        // create a resource config that scans for JAX-RS resources and
        // providers in com.example package
        GreetingsResourceConfig resourceConfig = new GreetingsResourceConfig();

        // create uri for the Jersey application from the BASE_URI
        URI uri = URI.create(BASE_URI);

        // create and start a new instance of grizzly http server
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);

        // display application info and listen for keystroke to shutdown
        System.out.println(format("Greetings Application started (%s)", BASE_URI));
        System.out.println("Hit enter to stop the application.");
        System.in.read();
        server.shutdown();

        System.exit(0);
    }

    private GreetingsMain() {
    }
}
