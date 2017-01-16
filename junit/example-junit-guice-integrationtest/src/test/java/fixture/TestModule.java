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
package fixture;

import org.testify.ContainerInstance;
import org.testify.TestContext;
import org.testify.annotation.Fixture;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.persist.jpa.JpaPersistModule;
import java.util.HashMap;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_LOAD_SCRIPT_SOURCE;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;
import org.postgresql.ds.PGSimpleDataSource;

/**
 *
 * @author saden
 */
@Fixture
public class TestModule extends AbstractModule {

    private HashMap<Object, Object> properties;

    @Override
    protected void configure() {
        properties = new HashMap<>();
        JpaPersistModule persistModule = new JpaPersistModule("example.greetings");

        persistModule.properties(properties);
        install(persistModule);
    }

    /**
     * A provider of a JDBC PostgreSQL test DataSource. Note that we do not
     * annotate this class with @Service because we don't want it to be
     * discovered and used every time.
     *
     * @param instance the container instance
     * @param testContext
     * @return a postgress data source
     */
    @Singleton
    @Provides
    public DataSource testDataSource(@Named("postgres") ContainerInstance instance, TestContext testContext) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(instance.getHost());
        dataSource.setPortNumber(instance.findFirstPort().get());
        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        properties.put(DATASOURCE, dataSource);
        properties.put("hibernate.ejb.entitymanager_factory_name", testContext.getName());
        properties.put(PHYSICAL_NAMING_STRATEGY, new PhysicalNamingStrategyStandardImpl());
        properties.put(IMPLICIT_NAMING_STRATEGY, new ImplicitNamingStrategyComponentPathImpl());
        properties.put(HBM2DDL_LOAD_SCRIPT_SOURCE, "META-INF/postgresql-test-data.sql");

        return dataSource;
    }

}
