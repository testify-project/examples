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
package examples;

import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import examples.database.PostgresDataSourceProvider;
import examples.greeting.CreateGreeting;
import examples.greeting.GetGreeting;
import examples.greeting.ListGreetings;
import examples.greeting.RemoveGreeting;
import examples.greeting.UpdateGreeting;

/**
 * A module that defines Greetings bindings.
 *
 * @author saden
 */
public class GreetingsModule extends AbstractModule {

    private Map<String, Object> jpaProperties;

    @Override
    protected void configure() {
        jpaProperties = new HashMap<>();
        JpaPersistModule persistModule = new JpaPersistModule("example.greetings");
        persistModule.properties(jpaProperties);
        install(persistModule);

        bind(DataSource.class)
                .toProvider(PostgresDataSourceProvider.class)
                .in(Singleton.class);

        bind(CreateGreeting.class).in(Singleton.class);
        bind(GetGreeting.class).in(Singleton.class);
        bind(ListGreetings.class).in(Singleton.class);
        bind(RemoveGreeting.class).in(Singleton.class);
        bind(UpdateGreeting.class).in(Singleton.class);

        requestInjection(this);
    }

    @Inject
    void startPersistService(PersistService service, DataSource dataSource) {
        jpaProperties.put(DATASOURCE, dataSource);
        jpaProperties.put(PHYSICAL_NAMING_STRATEGY, new PhysicalNamingStrategyStandardImpl());
        jpaProperties.put(IMPLICIT_NAMING_STRATEGY,
                new ImplicitNamingStrategyComponentPathImpl());
        service.start();
    }

}
