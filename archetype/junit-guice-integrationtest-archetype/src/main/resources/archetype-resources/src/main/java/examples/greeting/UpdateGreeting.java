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
package examples.greeting;

import com.google.inject.persist.Transactional;
import examples.greeting.entity.GreetingEntity;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * A service that updates an existing greeting.
 *
 * @author saden
 */
@Transactional
public class UpdateGreeting {

    private final EntityManager entityManager;

    @Inject
    UpdateGreeting(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Update an existing greeting.
     *
     * @param id the id of the existing greeting
     * @param model the greeting entity
     */
    public void updateGreeting(UUID id, GreetingEntity model) {
        GreetingEntity entity = entityManager.getReference(GreetingEntity.class, id);
        entity.setPhrase(model.getPhrase());

        entityManager.persist(model);
    }
}
