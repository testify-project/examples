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
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * A service that creates a new greeting.
 *
 * @author saden
 */
@Transactional
public class CreateGreeting {

    private final EntityManager entityManager;

    @Inject
    CreateGreeting(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a new greeting.
     *
     * @param model the greeting entity that will be created
     */
    public void createGreeting(GreetingEntity model) {
        entityManager.persist(model);

    }
}
