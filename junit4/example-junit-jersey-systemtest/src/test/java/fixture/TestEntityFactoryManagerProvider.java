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
package fixture;

import static javax.persistence.Persistence.createEntityManagerFactory;

import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceLocator;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

/**
 * A provider of a JDBC PostgreSQL test DataSource. Note that we do not annotate this class with
 * {@literal @Service}because we don't want it to be discovered and used every time.
 *
 * @author saden
 */
@Rank(Integer.MAX_VALUE)
public class TestEntityFactoryManagerProvider implements Factory<EntityManagerFactory> {

    private final DataSource dataSource;
    private final ServiceLocator serviceLocator;

    @Inject
    TestEntityFactoryManagerProvider(DataSource dataSource, ServiceLocator serviceLocator) {
        this.dataSource = dataSource;
        this.serviceLocator = serviceLocator;
    }

    @Singleton
    @Override
    public EntityManagerFactory provide() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);
        properties.put(PHYSICAL_NAMING_STRATEGY, new PhysicalNamingStrategyStandardImpl());
        properties.put(IMPLICIT_NAMING_STRATEGY, new ImplicitNamingStrategyComponentPathImpl());
        properties.put("hibernate.ejb.entitymanager_factory_name", serviceLocator.getName());

        return createEntityManagerFactory("test.example.greeter", properties);
    }

    @Override
    public void dispose(EntityManagerFactory instance) {
        instance.close();
    }

}
