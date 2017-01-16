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
package examples.database.transaction;

import java.util.Optional;
import static java.util.Optional.ofNullable;
import javax.persistence.EntityManager;

/**
 * A container object for setting, getting, and removing thread local Entity
 * Manager instance.
 *
 * @author saden
 */
public class TransactionalContext {

    private static final ThreadLocal<EntityManager> LOCAL_ENTITY_MANAGER = new ThreadLocal<>();

    private TransactionalContext() {
    }

    /**
     * Get the current thread local entity manager instance.
     *
     * @return an optional with thread local entity manager, empty otherwise
     */
    public static Optional<EntityManager> get() {
        return ofNullable(LOCAL_ENTITY_MANAGER.get());
    }

    /**
     * Set the thread local entity manager instance.
     *
     * @param entityManager the entity manager instance
     */
    public static void set(EntityManager entityManager) {
        LOCAL_ENTITY_MANAGER.set(entityManager);
    }

    /**
     * Remove the thread local entity manager.
     */
    public static void remove() {
        LOCAL_ENTITY_MANAGER.remove();
    }

}
