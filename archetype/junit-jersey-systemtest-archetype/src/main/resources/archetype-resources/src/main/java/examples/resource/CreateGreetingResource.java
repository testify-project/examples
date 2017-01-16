#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2016-2017 Sharmarke Aden.
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

import examples.resource.entity.GreetingEntity;
import java.net.URI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * A resource that creates a new greeting.
 *
 * @author saden
 */
@Transactional
@Path("greetings")
public class CreateGreetingResource {

    private final EntityManager entityManager;
    private final UriInfo uriInfo;

    @Inject
    CreateGreetingResource(EntityManager entityManager, UriInfo uriInfo) {
        this.entityManager = entityManager;
        this.uriInfo = uriInfo;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response create(@Valid GreetingEntity entity) {
        entityManager.persist(entity);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(entity.getId().toString())
                .build();

        return Response.created(location).build();
    }

}
