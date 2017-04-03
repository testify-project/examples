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
package examples.resource;

import examples.GreetingsResourceConfig;
import examples.resource.entity.GreetingEntity;
import fixture.TestModule;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.ClientInstance;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Cut;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.junit4.system.Jersey2SystemTest;

/**
 *
 * @author saden
 */
@Application(GreetingsResourceConfig.class)
@Module(TestModule.class)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(Jersey2SystemTest.class)
public class GetGreetingResourceST {

    @Cut
    ClientInstance<WebTarget> cut;

    @ConfigHandler
    public void configureClient(ClientBuilder clientBuilder) {
        clientBuilder.register(JacksonFeature.class);
    }

    @Test
    public void givenNoneExistentGreetingIdGetGreetingShouldReturn404() {
        //Act
        Response response = cut.getInstance()
                .path("greetings")
                .path("aa216415-1b8e-4ab9-8531-fcbd25d5966f")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenExistingGreetingIdGetGreetingShouldReturnFoundGreeting() {
        //Act
        Response response = cut.getInstance()
                .path("greetings")
                .path("0d216415-1b8e-4ab9-8531-fcbd25d5966f")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        GreetingEntity result = response.readEntity(GreetingEntity.class);
        assertThat(result).isNotNull();
    }
}
