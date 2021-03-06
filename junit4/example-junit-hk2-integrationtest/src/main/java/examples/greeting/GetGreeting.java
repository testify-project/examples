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

import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.jvnet.hk2.annotations.Service;

import examples.greeting.entity.GreetingEntity;

/**
 * A service that retrieve an existing greeting.
 *
 * @author saden
 */
@Transactional
@Service
public class GetGreeting {

    private final EntityManager entityManager;

    @Inject
    GetGreeting(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get a greeting with the given id.
     *
     * @param id the greeting id
     * @return the optional containing the greeting, empty otherwise
     */
    public Optional<GreetingEntity> getGreeting(UUID id) {
        GreetingEntity result = entityManager.find(GreetingEntity.class, id);

        return ofNullable(result);
    }

}
