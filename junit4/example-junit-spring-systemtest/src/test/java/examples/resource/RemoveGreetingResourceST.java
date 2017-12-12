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

import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.junit4.SystemTest;

import examples.GreetingApplication;
import fixture.TestModule;

/**
 *
 * @author saden
 */
@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(SystemTest.class)
public class RemoveGreetingResourceST {

    @Sut
    WebTarget sut;

    @Test
    public void givenGreetingIdDeleteGreetingShouldDeleteGreeting() {
        //Act
        Response response = sut
                .path("greetings")
                .path("0d216415-1b8e-4ab9-8531-fcbd25d5966f")
                .request()
                .delete();

        //Assert
        assertThat(response.getStatus()).isEqualTo(NO_CONTENT.getStatusCode());
        assertThat(response.hasEntity()).isFalse();
    }

}
