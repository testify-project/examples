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

import static javax.ws.rs.core.Response.Status.OK;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.ClientInstance;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.junit4.SystemTest;

import examples.GreetingApplication;
import examples.resource.repository.entity.GreetingEntity;
import fixture.TestModule;

/**
 *
 * @author saden
 */
@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(SystemTest.class)
public class ListGreetingsResourceST {

    @Sut
    ClientInstance<WebTarget, Client> sut;

    @ConfigHandler
    public void configureClient(ClientBuilder clientBuilder) {
        clientBuilder.register(JacksonFeature.class);
    }

    @Test
    public void callToListGreetingsShouldReturnListOfGreetings() {
        //Act
        Response response = sut.getClient().getValue()
                .path("greetings")
                .path("list")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        GenericType<List<GreetingEntity>> genericType =
                new GenericType<List<GreetingEntity>>() {
        };
        List<GreetingEntity> result = response.readEntity(genericType);
        assertThat(result).hasSize(1);
    }

}
