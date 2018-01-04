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

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;

import examples.greeting.entity.GreetingEntity;

/**
 * A service that retrieves all greetings.
 *
 * @author saden
 */
@Transactional
public class ListGreetings {

    private final EntityManager entityManager;

    @Inject
    ListGreetings(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get all greetings.
     *
     * @return a collection of all the greetings, empty collection otherwise
     */
    public Collection<GreetingEntity> listGreetings() {
        return entityManager.createQuery("SELECT g FROM GreetingEntity g").getResultList();

    }
}
