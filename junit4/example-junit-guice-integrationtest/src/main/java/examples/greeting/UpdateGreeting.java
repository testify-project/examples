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
package examples.greeting;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;

import examples.greeting.entity.GreetingEntity;

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
     * @param entity the greeting entity
     */
    public void updateGreeting(UUID id, GreetingEntity entity) {
        GreetingEntity existingEntity = entityManager.getReference(GreetingEntity.class, id);
        existingEntity.setPhrase(entity.getPhrase());

        entityManager.persist(entity);
    }
}
