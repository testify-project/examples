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

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import examples.resource.entity.GreetingEntity;

/**
 * A resource that removes an existing greeting.
 *
 * @author saden
 */
@Path("greetings")
@Transactional
public class RemoveGreetingResource {

    private final EntityManager entityManager;

    @Inject
    RemoveGreetingResource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @DELETE
    @Path("{id}")
    public Response removeGreeting(@NotNull @PathParam("id") UUID id) {
        GreetingEntity entity = entityManager.getReference(GreetingEntity.class, id);

        if (entity != null) {
            entityManager.remove(entity);
        }

        return Response.noContent().build();
    }
}
