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
package examples.database;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;

import examples.database.transaction.PerTransaction;

/**
 * A factory that provides an entity manager.
 *
 * @author saden
 */
@Service
public class EntityManagerProvider implements Factory<EntityManager> {

    private final EntityManagerFactory entityManagerFactory;

    @Inject
    EntityManagerProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @PerTransaction
    @Override
    public EntityManager provide() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void dispose(EntityManager instance) {
        instance.close();
    }
}
